package OOP.Solution.Trait;

import OOP.Provided.Trait.OOPBadClass;
import OOP.Provided.Trait.OOPTraitConflict;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Provided.Trait.OOPTraitMissingImpl;
import OOP.Solution.Logger;
import OOP.Tests.Trait.Example.TraitCollector;
import javafx.util.Pair;


import java.io.File;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static OOP.Solution.ReflectionUtils.ReflectionHelper.*;


public class OOPTraitControl {

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> traitCollector;
    private File sourceFile;

    //Additional fields
    private Map<Class<?>, Object> interfaceToObjectMapper;
    private Map<Method, Class<?>> methodToClassMapper;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPTraitControl(Class<?> traitCollector, File sourceFile) {
        this.traitCollector = traitCollector;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateTraitLayout() throws OOPTraitException {
        Pair<Map<Class<?>, Object>, Map<Method, Class<?>>> pair = getInitMaps(true, traitCollector, OOPTraitBehaviour.class);
        interfaceToObjectMapper = pair.getKey();
        methodToClassMapper = pair.getValue();
        TraitClassMapper(methodToClassMapper);
        //List<Method> allMethods = getAllOurMethods(traitCollector);
        List<Method> allMethods = new ArrayList<>(methodToClassMapper.keySet());

        List<Method> notAnnotatedMethods = allMethods.stream().filter(M -> !(M.isAnnotationPresent(OOPTraitMethod.class))).collect(Collectors.toList());
        if (notAnnotatedMethods.size() > 0)
            throw new OOPBadClass(notAnnotatedMethods.get(0));
        List<Class<?>> notAnnotatedClass = allMethods.stream().map(methodToClassMapper::get).filter(C ->
                C != null && !C.getSimpleName().startsWith(CLASS_NAME_CONVENTION) && !C.isAnnotationPresent(OOPTraitBehaviour.class)
        ).collect(Collectors.toList());
        if (notAnnotatedClass.size() > 0) {
            throw new OOPBadClass(notAnnotatedClass.get(0));
        }
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        for (Method method : allMethods) {
            if (implemented.stream().noneMatch(M2 -> methodsHaveSameArguments(method, M2))) {
                throw new OOPTraitMissingImpl(method);
            }
        }
        for (Method method : allMethods) {
            List<Method> conflicts = implemented.stream().filter(otherMethod -> methodsHaveSameArguments(method, otherMethod) && otherMethod.getName().equals(method.getName())).collect(Collectors.toList());
            validateResolvedConflicts(conflicts, methodToClassMapper);
        }


    }


   /* private void fillMaps(Class<? extends  Annotation> annotation) {
        //fills the method to class map
        methodToClassMapper =mapMethodToClass(traitCollector.getInterfaces());

        interfaceToObjectMapper = new Hashtable<>();
        //fills the interface to object map
        Collection<Class<?>> allClasses = methodToClassMapper.values();
        List<Class<?>> annotatedClasses = allClasses.stream().filter(c -> c.isAnnotationPresent(annotation)).collect(Collectors.toList());
        annotatedClasses.forEach(clazz -> interfaceToObjectMapper.put(clazz, getInstanceByConvention(clazz)));
    }*/

    private void validateResolvedConflicts(List<Method> conflicts, Map<Method, Class<?>> classMap) throws OOPTraitConflict {
        if (conflicts.size() > 1) {
            Logger.log("Conflict was found:"+conflicts);
            Method conflictedMethod = conflicts.get(0);
            try {
                conflictedMethod = traitCollector.getMethod(conflictedMethod.getName(), conflictedMethod.getParameterTypes());
                Logger.log("the conflicted method in trait collector is:"+conflictedMethod);
                if (conflictedMethod.isAnnotationPresent(OOPTraitConflictResolver.class)) {
                    Logger.log("OOPTraitConflictResolver annotation was present");
                    OOPTraitConflictResolver annotation = conflictedMethod.getAnnotation(OOPTraitConflictResolver.class);
                    if (conflicts.stream().noneMatch(m -> classMap.get(m).equals(annotation.resolve()))) {
                        Logger.log("no resolve was found");
                        throw new OOPTraitConflict(conflictedMethod);
                    }
                } else {
                    Logger.log("OOPTraitConflictResolver annotation not was present");
                    throw new OOPTraitConflict(conflictedMethod);
                }
            } catch (NoSuchMethodException ignored) {
                Logger.log("could not find method "+conflictedMethod);
            }
        }
    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPTraitException {
        Logger.log("trying to invoke "+methodName+"with args:"+ Arrays.toString(args));

        //List<Method> allMethods = getAllOurMethods(traitCollector);
        List<Method> allMethods = new ArrayList<>(methodToClassMapper.keySet());
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        List<Method> matches = filterByArguments(filterByMethodName(methodName, implemented), args);
        Logger.log("matches:"+matches);
        if(methodAmbiguity(matches,args)!=null)
            throw new OOPTraitConflict(methodAmbiguity(matches,args));
       List<Method> candidates = getClosestMethods(matches, methodToClassMapper, args);
        Logger.log("candidates:"+candidates);
        Method randMethod = candidates.get(0);
        if (!candidates.stream().allMatch(M -> (M.getName().equals(randMethod.getName()) && methodsHaveSameArguments(M, randMethod)))) {
            Logger.log("more than one candidate was found->Conflict.. throwing exception,SHOULD NOT GET HERE");
            throw new OOPTraitConflict(randMethod);
        }
       if(candidates.size() == 1){// no conflicts so just invoke
            Method toInvoke = candidates.get(0);
            Logger.log("no conflicts, only one candidate, trying to invoke:"+toInvoke);
            return invokeTraitMethod(toInvoke,args);

        }
        Logger.log("there are conflicts, so search for a resolve");
        return invokeInTraitMethodConflict(matches, randMethod, args);

    }

    private Object invokeInTraitMethodConflict(List<Method> matches, Method randMethod, Object[] args) {

        Method toInvoke;
        try {
            toInvoke = TraitCollector.class.getMethod(randMethod.getName(), randMethod.getParameterTypes());
        } catch (NoSuchMethodException e) {
            //is it possible?
            Logger.log("should not get here , could not get method from trait collector (invokeInTraitMethodConflict)");
            return null;
        }

        OOPTraitConflictResolver annotation = toInvoke.getAnnotation(OOPTraitConflictResolver.class);
        Logger.log("annotation value:"+annotation);
        if (annotation != null) { //there is a resolve annotation
            Logger.log("annotation was found with resolve value:"+annotation.resolve());
           //toInvoke = matches.stream().filter(m -> methodToClassMapper.get(m).equals(annotation.resolve())).collect(Collectors.toList()).get(0);
            List<Method> resolves= matches.stream().filter(m-> methodToClassMapper.get(m).equals(annotation.resolve())).collect(Collectors.toList());
            toInvoke=resolves.get(0);
            Logger.log("toInvoke value (after taking the resolve Trait):"+toInvoke);
        }else{
            Logger.log("the annotation was not found in method"+toInvoke);
            toInvoke=randMethod;

            Logger.log("invoking "+toInvoke+" as default");
        }
        return invokeTraitMethod(toInvoke,args);

    }

    private Object invokeTraitMethod(Method toInvoke, Object[] args) {
        Class<?> InvokerClass = methodToClassMapper.get(toInvoke);
        Object obj = interfaceToObjectMapper.get(InvokerClass);
       return  invokeMethod(obj, toInvoke, args);
    }

    //TODO: add more of your code :


    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
