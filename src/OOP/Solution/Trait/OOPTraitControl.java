package OOP.Solution.Trait;

import OOP.Provided.Trait.OOPBadClass;
import OOP.Provided.Trait.OOPTraitConflict;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Provided.Trait.OOPTraitMissingImpl;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static OOP.Solution.ReflectionUtils.ReflectionHelper.getAllOurMethods;
import static OOP.Solution.ReflectionUtils.ReflectionHelper.mapMethodToClass;
import static OOP.Solution.ReflectionUtils.ReflectionHelper.methodsHaveSameArguments;


public class OOPTraitControl {

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> traitCollector;
    private File sourceFile;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPTraitControl(Class<?> traitCollector, File sourceFile) {
        this.traitCollector = traitCollector;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateTraitLayout() throws OOPTraitException {
        List<Method> allMethods = getAllOurMethods(traitCollector);
        Map<Method, Class<?>> classMap = mapMethodToClass(traitCollector.getInterfaces());
        List<Method> notAnnotatedMethods = allMethods.stream().filter(M -> !(M.isAnnotationPresent(OOPTraitMethod.class))).collect(Collectors.toList());
        if (notAnnotatedMethods.size() > 0)
            throw new OOPBadClass(notAnnotatedMethods.get(0));
        List<Class<?>> notAnnotatedClass = allMethods.stream().map(classMap::get).filter(C -> C.isAnnotationPresent(OOPTraitBehaviour.class)).collect(Collectors.toList());
        if (notAnnotatedClass.size() > 0)
            throw new OOPBadClass(notAnnotatedClass.get(0));
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        for (Method method : allMethods) {
            if (implemented.stream().noneMatch(M2 -> methodsHaveSameArguments(method, M2))) {
                throw new OOPTraitMissingImpl(method);
            }
        }
        for (Method method : allMethods) {
            List<Method> conflicts = allMethods.stream().filter(otherMethod -> methodsHaveSameArguments(method, otherMethod)).collect(Collectors.toList());
            validateResolvedConflicts(conflicts,classMap);
        }

    }

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

    private boolean isAnnotatedBy(Method m, Class<OOPTraitMethod> oopTraitMethodClass, OOPTraitMethodModifier inter) {
        if (m.isAnnotationPresent(oopTraitMethodClass)) {
            OOPTraitMethod mod = m.getAnnotation(oopTraitMethodClass);
            return mod.modifier().equals(inter);
        }
        return false;
    }


    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPTraitException {
        List<Method> allMethods = getAllOurMethods(traitCollector);
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        List<Method> matches = implemented.stream().filter(M -> M.getName().equals(methodName)).collect(Collectors.toList());

        return null;
    }

    //TODO: add more of your code :


    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
