package OOP.Solution.Trait;

import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Trait.OOPBadClass;
import OOP.Provided.Trait.OOPTraitConflict;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Provided.Trait.OOPTraitMissingImpl;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Tests.Trait.Example.TraitCollector;
import javafx.util.Pair;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<Method> allMethods = getAllOurMethods(traitCollector);


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
            List<Method> conflicts = implemented.stream().filter(otherMethod -> methodsHaveSameArguments(method, otherMethod)).collect(Collectors.toList());
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
            Method conflictedMethod = conflicts.get(0);
            try {
                conflictedMethod = traitCollector.getMethod(conflictedMethod.getName(), conflictedMethod.getParameterTypes());
                if (conflictedMethod.isAnnotationPresent(OOPTraitConflictResolver.class)) {
                    OOPTraitConflictResolver annotation = conflictedMethod.getAnnotation(OOPTraitConflictResolver.class);
                    if (conflicts.stream().noneMatch(m -> classMap.get(m).equals(annotation.resolve())))
                        throw new OOPTraitConflict(conflictedMethod);
                } else {
                    throw new OOPTraitConflict(conflictedMethod);
                }
            } catch (NoSuchMethodException ignored) {
            }
        }
    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPTraitException {


        //List<Method> allMethods = getAllOurMethods(traitCollector);
        List<Method> allMethods = methodToClassMapper.keySet().stream().collect(Collectors.toList());
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        List<Method> matches = filterByArguments(filterByMethodName(methodName, implemented), args);

        List<Method> candidates = getClosestMethods(matches, methodToClassMapper, args);
        Method randMethod = candidates.get(0);
        if (!candidates.stream().allMatch(M -> (M.getName().equals(randMethod.getName()) && methodsHaveSameArguments(M, randMethod))))
            throw new OOPTraitConflict(randMethod);

        return invokeTraitMethod(matches, randMethod, args);

    }

    private Object invokeTraitMethod(List<Method> matches, Method randMethod, Object[] args) {

        Method toInvoke;
        try {
            toInvoke = TraitCollector.class.getMethod(randMethod.getName(), randMethod.getParameterTypes());
        } catch (NoSuchMethodException e) {
            //is it possible?
            return null;
        }

        OOPTraitConflictResolver annotation = toInvoke.getAnnotation(OOPTraitConflictResolver.class);
        if (annotation != null) { //there is a resolve annotation
            toInvoke = matches.stream().filter(m -> methodToClassMapper.get(m).equals(annotation.resolve())).collect(Collectors.toList()).get(0);
        }else{
            toInvoke=randMethod;
        }
        Class<?> InvokerClass = methodToClassMapper.get(toInvoke);
        Object obj = interfaceToObjectMapper.get(InvokerClass);
        return invokeMethod(obj, toInvoke, args);

    }

    //TODO: add more of your code :


    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
