package OOP.Solution.Trait;

import OOP.Provided.Trait.OOPBadClass;
import OOP.Provided.Trait.OOPTraitConflict;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Provided.Trait.OOPTraitMissingImpl;
import OOP.Solution.Logger;
import OOP.Tests.Trait.Example.TraitCollector;
import javafx.util.Pair;


import java.io.File;
import java.lang.annotation.Annotation;
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
        validateTags(OOPTraitBehaviour.class,OOPTraitMethod.class);

        Pair<Map<Class<?>, Object>, Map<Method, Class<?>>> pair = getInitMaps(true, traitCollector,OOPTraitMethod.class, OOPTraitBehaviour.class);
        interfaceToObjectMapper = pair.getKey();
        methodToClassMapper = pair.getValue();
        TraitClassMapper(methodToClassMapper);
        List<Method> allMethods = new ArrayList<>(methodToClassMapper.keySet());
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        List<Method> TraitMethods = getAllOurMethods(traitCollector).stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        for (Method method : allMethods) {
            //DOES IT HAVE TO BE ABSTRACT?????????????????????? THROW THE ABSTRACT METHOD?
            if (isAnnotatedBy(method,OOPTraitMethod.class,OOPTraitMethodModifier.INTER_ABS)&&(filterByArgumentsTypes(filterByMethodName(method.getName(),TraitMethods),method.getParameterTypes()).size()==0)) {
                Class<?> claz = getClassByConvention(methodToClassMapper.get(method));
                List<Method> clazImplMethods = null;
                if(claz !=null)
                    clazImplMethods = Arrays.stream(claz.getDeclaredMethods()).filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
                if(claz==null)
                    throw new OOPTraitMissingImpl(method);
                if(!filterByMethodName(method.getName(),clazImplMethods).anyMatch(M -> methodsHaveSameArguments(M,method))){
                    throw new OOPTraitMissingImpl(method);
                }
            }
        }
        for (Method method : allMethods) {
            List<Method> conflictedList = new ArrayList<>();
            List<Method> matches = implemented.stream().filter(otherMethod -> method.getParameterCount()== otherMethod.getParameterCount() && otherMethod.getName().equals(method.getName())).collect(Collectors.toList());
            List<Pair<Method,Method>> conflictedPairs = methodPairAmbiguity(matches);
            if(conflictedPairs!=null) {
                for(Pair<Method,Method> P : conflictedPairs) {
                    validateResolvedConflicts(P, methodToClassMapper);
                    conflictedList.clear();
                }
            }
        }


    }


    private void validateTags(Class<? extends Annotation> typeAnnotation, Class<? extends Annotation> methodAnnotation) throws OOPBadClass {
        Class<?>[] superInterfaces = traitCollector.getInterfaces();
        List<Method> allMethods;
        for (Class<?> interFace : superInterfaces) {
            if(!interFace.isAnnotationPresent(typeAnnotation)){
                throw new OOPBadClass(interFace);
            }
            allMethods = getAllOurMethods(interFace);
            List<Method> notAnnotatedMethods = allMethods.stream().filter(m -> !m.isAnnotationPresent(methodAnnotation)).collect(Collectors.toList());
            if (notAnnotatedMethods.size() > 0)
                throw new OOPBadClass(notAnnotatedMethods.get(0));

            List<Class<?>> notAnnotatedClasses = getAllOurTypes(interFace).stream().filter(c -> c != null && !c.isAnnotationPresent(typeAnnotation)).collect(Collectors.toList());
            if (notAnnotatedClasses.size() > 0)
                throw new OOPBadClass(notAnnotatedClasses.get(0));

            Class<?> implClass = getClassByConvention(interFace);
            if (implClass != null) {
                //if there is such a implementing class
                notAnnotatedMethods = Arrays.stream(implClass.getDeclaredMethods()).filter(m -> !m.isAnnotationPresent(methodAnnotation)).collect(Collectors.toList());
                if (notAnnotatedMethods.size() > 0)
                    throw new OOPBadClass(notAnnotatedMethods.get(0));

            }
        }

    }



    private void validateResolvedConflicts(Pair<Method,Method> conflicts, Map<Method, Class<?>> classMap) throws OOPTraitConflict {
        Method conflictedMethodOne = conflicts.getKey();
        Method conflictedMethodTwo = conflicts.getValue();
        List<Method> firstList = filterByMethodName(conflictedMethodOne.getName(), Arrays.stream(traitCollector.getDeclaredMethods()).collect(Collectors.toList())).filter(M -> checkForTypesEquality(conflictedMethodOne,M.getParameterTypes())).collect(Collectors.toList());
        List<Method> secondList = filterByMethodName(conflictedMethodTwo.getName(), Arrays.stream(traitCollector.getDeclaredMethods()).collect(Collectors.toList())).filter(M -> checkForTypesEquality(conflictedMethodTwo,M.getParameterTypes())).collect(Collectors.toList());
        Method resolver = null;
        for(Method M : firstList) {
            for(Method M2 : secondList) {
                if(M.equals(M2)&&M.isAnnotationPresent(OOPTraitConflictResolver.class))
                    resolver = M;
            }
        }
        if(resolver == null)
            throw new OOPTraitConflict(conflictedMethodOne);
        Logger.log("the conflicted method in trait collector is:"+resolver);
    }


    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPTraitException {
        Logger.log("trying to invoke "+methodName+"with args:"+ Arrays.toString(args));

        List<Method> allMethods = new ArrayList<>(methodToClassMapper.keySet());
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        int argsLength;
        if(args==null)
            argsLength=0;
        else
            argsLength=args.length;
        List<Method> matches = filterByArguments((filterByMethodName(methodName, implemented)).filter(M -> M.getParameterCount()==argsLength),args);
        Logger.log("matches:"+matches);
        List<Method> candidates = getClosestMethods(matches, methodToClassMapper, args);
        List<Pair<Method,Method>> ambiguity = methodPairAmbiguity(matches);
        if(ambiguity !=null) {
            for (Pair<Method, Method> P : ambiguity) {
                if (candidates.contains(P.getKey()) && !candidates.contains(P.getValue()))
                    candidates.add(P.getValue());
                if (!candidates.contains(P.getKey()) && candidates.contains(P.getValue()))
                    candidates.add(P.getKey());
            }
        }
        Logger.log("candidates:"+candidates);
        if(candidates.size() == 1){// no conflicts so just invoke
            Method toInvoke = candidates.get(0);
            Logger.log("no conflicts, only one candidate, trying to invoke:"+toInvoke);
            return invokeTraitMethod(toInvoke,args);

        }
        Logger.log("there are conflicts, so search for a resolve");
        return invokeInTraitMethodConflict(candidates, args);

    }
    private Method findResolver(List<Method> matches, Object[] args){
        for(Method M2 : traitCollector.getDeclaredMethods()) {
            if (M2.isAnnotationPresent(OOPTraitConflictResolver.class)&&matches.stream().anyMatch(X -> methodToClassMapper.get(X) ==M2.getAnnotation(OOPTraitConflictResolver.class).resolve() || (getClassByConvention(M2.getAnnotation(OOPTraitConflictResolver.class).resolve())
                    !=null)&&methodToClassMapper.get(X) == getClassByConvention(M2.getAnnotation(OOPTraitConflictResolver.class).resolve())) ) {
                boolean foundResolver = matches.stream().allMatch(M -> (M.getName().equals(M2.getName()) && M.getParameterCount() == M2.getParameterCount() && (checkForTypesEquality(M, M2.getParameterTypes()))));
                if (foundResolver) {
                    return M2;
                }
            }
        }
        return null;
    }
    private Object invokeInTraitMethodConflict(List<Method> matches, Object[] args) {
        Method toInvoke = findResolver(matches, args);
        OOPTraitConflictResolver annotation = toInvoke.getAnnotation(OOPTraitConflictResolver.class);
        Logger.log("annotation value:" + annotation);
        Logger.log("annotation was found with resolve value:" + annotation.resolve());
        Class<?> resolveTrait = annotation.resolve();
        List<Method> resolves = Arrays.stream(resolveTrait.getDeclaredMethods()).filter(M -> matches.contains(M)).collect(Collectors.toList());
        if (resolves.size() > 0) {
            toInvoke = resolves.get(0);
            return invokeTraitMethod(toInvoke, args);
        }
        if(getClassByConvention(resolveTrait)!=null) {
            resolves = Arrays.stream(getClassByConvention(resolveTrait).getDeclaredMethods()).filter(M -> matches.contains(M)).collect(Collectors.toList());
            if (resolves.size() > 0)
                toInvoke = resolves.get(0);
            return invokeTraitMethod(toInvoke, args);
        }
        return null;
    }

    private Object invokeTraitMethod(Method toInvoke, Object[] args) {
        Class<?> InvokerClass = methodToClassMapper.get(toInvoke);
        Object obj = interfaceToObjectMapper.get(InvokerClass);
        return  invokeMethod(obj, toInvoke, args);
    }
    public List<Method> methodsAbove(Class<?> interfaceStart){
        return methodToClassMapper.keySet().stream().filter(M -> getAllOurTypes(interfaceStart).contains(methodToClassMapper.get(M))).collect(Collectors.toList());
    }
    //TODO: add more of your code :
    public Method overrideConflictCheck(){ //No Need
        List<Method> allMethods = new ArrayList<>(methodToClassMapper.keySet());
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        for(Method M:implemented){
            List<Method> above = methodsAbove(methodToClassMapper.get(M));
            List<Method> aboveImplemented = above.stream().filter(M2 -> isAnnotatedBy(M2, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
            for(Method M2:aboveImplemented) {
                if (M2.getName().equals(M.getName()) && methodsHaveSameArguments(M, M2)) {
                    return M;
                }
            }
        }
        return null;
    }
    public Set<Class<?>> getALLClassesAndInterfaces(Class<?> clazz) {
        Set<Class<?>> allInterfaces = getAllOurTypes(clazz);
        Set<Class<?>> allInterfacesAndClasses = new HashSet<>();
        for(Class<?> currInterface : allInterfaces){
            Collections.addAll(allInterfacesAndClasses, currInterface);
            if(getClassByConvention(currInterface)!=null)
                Collections.addAll(allInterfacesAndClasses, getClassByConvention(currInterface));
        }
        return allInterfacesAndClasses;
    }
    public Set<Method> getALLMethods(Class<?> clazz) {
        Set<Class<?>> allInterfacesAndClasses = getALLClassesAndInterfaces(clazz);
        Set<Method> allMethods = new HashSet<>();
        for(Class<?> currInterfaceOrClass : allInterfacesAndClasses){
            Collections.addAll(allMethods, currInterfaceOrClass.getDeclaredMethods());
        }
        return allMethods;
    }

    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
