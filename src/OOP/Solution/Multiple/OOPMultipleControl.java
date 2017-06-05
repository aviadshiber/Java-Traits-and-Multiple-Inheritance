package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPBadClass;
import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.ReflectionUtils.ReflectionHelper;
import javafx.util.Pair;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static OOP.Solution.ReflectionUtils.ReflectionHelper.*;

public class OOPMultipleControl {

    // DO NOT CHANGE !!!!!!
    private Class<?> interfaceClass;
    private File sourceFile;

    Map<Class<?>, Object> interfaceToObjectMapper;

    // DO NOT CHANGE !!!!!!
    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //need to scan for common parent and throw exception if there is one.
    public void validateInheritanceGraph() throws OOPMultipleException {

        validateTags(OOPMultipleInterface.class, OOPMultipleMethod.class);
        validateForCommonParent();
        //we need to map each interface to it's instance
        fillMapInterfaceToObject();
    }

    private void validateTags(Class<? extends Annotation> typeAnnotation, Class<? extends Annotation> methodAnnotation) throws OOPBadClass {
        List<Method> allMethods = getAllOurMethods(interfaceClass);
        List<Method> notAnnotatedMethods = allMethods.stream().filter(m -> !m.isAnnotationPresent(methodAnnotation)).collect(Collectors.toList());
        if (notAnnotatedMethods.size() > 0)
            throw new OOPBadClass(notAnnotatedMethods.get(0));
        List<Class<?>> notAnnotatedClasses = getAllOurTypes(interfaceClass).stream().filter(c -> c != null && !c.isAnnotationPresent(typeAnnotation)).collect(Collectors.toList());
        if (notAnnotatedClasses.size() > 0)
            throw new OOPBadClass(notAnnotatedClasses.get(0));
    }

    private void fillMapInterfaceToObject() {
        interfaceToObjectMapper = new Hashtable<>();
        fillMapInterfaceToObject(interfaceToObjectMapper);
    }

    private void fillMapInterfaceToObject(Map<Class<?>, Object> interfaceToObject) {
        Map<Method, Class<?>> classMap = mapMethodToClass(interfaceClass.getInterfaces());
        Collection<Class<?>> allClasses = classMap.values();
        List<Class<?>> annotatedClasses = allClasses.stream().filter(c -> c.isAnnotationPresent(OOPMultipleInterface.class)).collect(Collectors.toList());
        annotatedClasses.forEach(clazz -> interfaceToObject.put(clazz, getInstanceByConvention(clazz)));
    }

    private void validateForCommonParent() throws OOPInherentAmbiguity {
        Set<Class<?>> interfaceSet = new HashSet<>();
        validateForCommonParent(interfaceClass, interfaceSet
                , OOPMultipleInterface.class, OOPMultipleMethod.class);
    }

    /**
     * the method validate for common Parent with at least one method which exist using DFS traverse.
     * if such parent was found an exception will be thrown.
     *
     * @param interfaceClass the interface to start the search.
     * @param interfaceSet   the set to check of collisions.
     * @throws OOPInherentAmbiguity is a structural collision exception.
     */
    private void validateForCommonParent(Class<?> interfaceClass, Set<Class<?>> interfaceSet, Class<? extends Annotation> classAnnotation, Class<? extends Annotation> methodAnnotation) throws OOPInherentAmbiguity {
        Class<?>[] superClasses = interfaceClass.getInterfaces();
        for (Class<?> superClass : superClasses) {
            boolean isSuperClassAnnotated = superClass.isAnnotationPresent(classAnnotation);
            if (isSuperClassAnnotated) {
                boolean superClassHasAnyMethod = superClass.getDeclaredMethods().length > 0;
                if (superClassHasAnyMethod) {
                    final List<Method> allMethods = new ArrayList<>(Arrays.asList(superClass.getDeclaredMethods()));
                    final List<Method> annotatedMethods = allMethods.stream().filter(method -> !Modifier.isPrivate(method.getModifiers()) && method.isAnnotationPresent(methodAnnotation)).collect(Collectors.toList());
                    boolean annotatedMethodExist = annotatedMethods.size() > 0;
                    if (annotatedMethodExist) {
                        boolean structuralCollisionExist = interfaceSet.contains(superClass);
                        if (structuralCollisionExist)
                            throw new OOPInherentAmbiguity(interfaceClass, superClass, annotatedMethods.get(0));
                    }

                }
                interfaceSet.add(superClass);
                validateForCommonParent(superClass, interfaceSet, classAnnotation, methodAnnotation);
            }
        }
    }


    /**
     * the method look for CoincidentalAmbiguity of methodName with args, if none exist
     * find the best match to it, and invokes it!
     *
     * @param methodName the method name
     * @param args       the arguments
     * @return the object that we invoke on
     * @throws OOPMultipleException the exception of multiple inheritance
     */
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {
        //we map every method to a it's class for later use
        Map<Method, Class<?>> classMap = ReflectionHelper.mapMethodToClass(interfaceClass.getInterfaces());

        List<Method> filteredMethods = validateCoincidentalAmbiguity(interfaceClass, classMap, methodName
                , args);
        //we search for the best method that match, the method throws exception if Coincidental Ambiguity exist
        Method bestMatch = getBestMatch(filteredMethods, classMap, args);
        Class<?> methodInClass = classMap.get(bestMatch);
        Object obj = getInstanceFromClass(methodInClass);
        Object returnValue = invokeMethod( obj,bestMatch, args);
        return bestMatch.getReturnType().equals(Void.class) ? null : returnValue;
    }

    private Object getInstanceFromClass(Class<?> methodInClass) {
       return interfaceToObjectMapper!=null ? interfaceToObjectMapper.get(methodInClass) : getInstanceByConvention(methodInClass);
    }


    private List<Method> validateCoincidentalAmbiguity(Class<?> interfaceClass,
                                                       Map<Method, Class<?>> classMap, String methodName, Object... args) throws OOPCoincidentalAmbiguity {
        Class<?>[] superClasses = interfaceClass.getInterfaces();
        List<Method> filteredByNameAndArguments = new ArrayList<>();

        //we iterate on all super classes and we collect all methods which are equal by name and possible arguments
        for (Class<?> superClass : superClasses) {
            final List<Method> superClassMethods = new ArrayList<>(Arrays.asList(superClass.getMethods()));
            Stream<Method> filteredByName = filterByMethodName(methodName, superClassMethods);
            filteredByNameAndArguments.addAll(filterByArguments(filteredByName, args));
        }
        //now we have collected all the methods, so we search for collisions
        final Set<Method> collisions = getCollidedMethods(filteredByNameAndArguments);
        //if we found one by now then we throw an exception
        if (collisions.size() > 0) {
            Collection<Pair<Class<?>, Method>> pairs = new HashSet<>();
            //we warp it as a pair before throwing
            collisions.forEach(m -> pairs.add(new Pair<>(classMap.get(m), m)));
            throw new OOPCoincidentalAmbiguity(pairs);
        }
        //no collisions were found so we return what we found so far
        return filteredByNameAndArguments;
    }

    //DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
