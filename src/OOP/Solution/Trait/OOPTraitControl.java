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
        Pair<Map<Class<?>, Object>, Map<Method, Class<?>>> pair = getInitMaps(true, traitCollector,OOPTraitMethod.class, OOPTraitBehaviour.class);
        interfaceToObjectMapper = pair.getKey();
        methodToClassMapper = pair.getValue();
        TraitClassMapper(methodToClassMapper);
        List<Method> allMethods2 = getAllOurMethods(traitCollector);
        List<Method> allMethods = new ArrayList<>(methodToClassMapper.keySet());

        List<Method> notAnnotatedMethods = allMethods2.stream().filter(M -> !(M.isAnnotationPresent(OOPTraitMethod.class))).collect(Collectors.toList());
        if (notAnnotatedMethods.size() > 0)
            throw new OOPBadClass(notAnnotatedMethods.get(0));
       // List<Class<?>> notAnnotatedClass = allMethods2.stream().map(methodToClassMapper::get).filter(C ->
        //        C != null && !C.getSimpleName().startsWith(CLASS_NAME_CONVENTION) && !C.isAnnotationPresent(OOPTraitBehaviour.class)
        //).collect(Collectors.toList());
        List<Class<?>> notAnnotatedClass = getAllOurTypes(traitCollector).stream().filter(C ->
                C != null && !C.getSimpleName().startsWith(CLASS_NAME_CONVENTION) && !C.isAnnotationPresent(OOPTraitBehaviour.class)
                 ).collect(Collectors.toList());
        if (notAnnotatedClass.size() > 0) {
            throw new OOPBadClass(notAnnotatedClass.get(0));
        }
      //  if(overrideConflictCheck()!=null)
      //      throw new OOPTraitConflict(overrideConflictCheck());
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        for (Method method : allMethods) {
            //DOES IT HAVE TO BE ABSTRACT?????????????????????? THROW THE ABSTRACT METHOD?
            if (isAnnotatedBy(method,OOPTraitMethod.class,OOPTraitMethodModifier.INTER_ABS)&&implemented.stream().noneMatch(M2 -> methodsHaveSameArguments(method, M2)&&method.getName().equals(M2.getName()))) {
                throw new OOPTraitMissingImpl(method);
            }
        }
        for (Method method : allMethods) {
            List<Method> conflictedList = new ArrayList<>();
            List<Method> matches = implemented.stream().filter(otherMethod -> method.getParameterCount()== otherMethod.getParameterCount() && otherMethod.getName().equals(method.getName())).collect(Collectors.toList());
            List<Pair<Method,Method>> conflictedPairs = methodPairAmbiguity(matches,method.getParameterTypes());
            if(conflictedPairs!=null) {
                for(Pair<Method,Method> P : conflictedPairs) {
                    validateResolvedConflicts(P, methodToClassMapper);
                    conflictedList.clear();
                }
            }
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

    private void validateResolvedConflicts(Pair<Method,Method> conflicts, Map<Method, Class<?>> classMap) throws OOPTraitConflict {
        Method conflictedMethodOne = conflicts.getKey();
        Method conflictedMethodTwo = conflicts.getValue();
        List<Method> firstList = filterByArguments(filterByMethodName(conflictedMethodOne.getName(), Arrays.stream(traitCollector.getDeclaredMethods()).collect(Collectors.toList())),conflictedMethodOne.getParameterTypes());
        List<Method> secondList = filterByArguments(filterByMethodName(conflictedMethodTwo.getName(), Arrays.stream(traitCollector.getDeclaredMethods()).collect(Collectors.toList())),conflictedMethodTwo.getParameterTypes());
        Method resolver = null;
        for(Method M : firstList) {
            for(Method M2 : secondList) {
                if(M.equals(M2))
                    resolver = M;
            }
        }
        if(resolver == null)
            throw new OOPTraitConflict(conflictedMethodOne);
        Logger.log("the conflicted method in trait collector is:"+resolver);
        if (resolver.isAnnotationPresent(OOPTraitConflictResolver.class)) {
            Logger.log("OOPTraitConflictResolver annotation was present");
            OOPTraitConflictResolver annotation = resolver.getAnnotation(OOPTraitConflictResolver.class);
            if ((classMap.get(conflictedMethodOne).equals(annotation.resolve())||(getClassByConvention(classMap.get(conflictedMethodOne))!=null&&getClassByConvention(classMap.get(conflictedMethodOne)).equals(annotation.resolve())))&&
                    (classMap.get(conflictedMethodTwo).equals(annotation.resolve())||(getClassByConvention(classMap.get(conflictedMethodTwo))!=null&&getClassByConvention(classMap.get(conflictedMethodTwo)).equals(annotation.resolve())))) {
                Logger.log("no resolve was found");
                throw new OOPTraitConflict(conflictedMethodOne);
            }
        } else {
            Logger.log("OOPTraitConflictResolver annotation not was present");
            throw new OOPTraitConflict(conflictedMethodOne);
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
        return invokeInTraitMethodConflict(candidates, randMethod, args);

    }

    private Object invokeInTraitMethodConflict(List<Method> matches, Method randMethod, Object[] args) {

        Method toInvoke;
        try {
            toInvoke = traitCollector.getMethod(randMethod.getName(), randMethod.getParameterTypes());
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
            List<Method> resolves= matches.stream().filter(m-> methodToClassMapper.get(m).equals(annotation.resolve())||(getClassByConvention(methodToClassMapper.get(m))!=null&&getClassByConvention(methodToClassMapper.get(m)).equals(annotation.resolve()))).collect(Collectors.toList());
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
    public List<Method> methodsAbove(Class<?> interfaceStart){
        return methodToClassMapper.keySet().stream().filter(M -> getAllOurTypes(interfaceStart).contains(methodToClassMapper.get(M))).collect(Collectors.toList());
    }
    //TODO: add more of your code :
    public Method overrideConflictCheck(){
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
