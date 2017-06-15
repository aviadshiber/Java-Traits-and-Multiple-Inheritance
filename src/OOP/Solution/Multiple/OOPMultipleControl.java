package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPBadClass;
import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
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

    //Additional fields
    private Map<Class<?>, Object> interfaceToObjectMapper;
    private Map<Method, Class<?>> methodToClassMapper;

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
        Pair<Map<Class<?>, Object>, Map<Method, Class<?>>> pair= getInitMaps(false,interfaceClass,OOPMultipleMethod.class,OOPMultipleInterface.class);
        interfaceToObjectMapper=pair.getKey();
        methodToClassMapper=pair.getValue();
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
                    final List<Method> annotatedMethods = allMethods.stream().filter(method -> isAnnotatedAndNotPrivate(method,methodAnnotation)).collect(Collectors.toList());
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
        List<Method> filteredMethods = validateCoincidentalAmbiguity(interfaceClass, methodName, args);
        //we search for the best method that match, the method throws exception if Coincidental Ambiguity exist
        Method bestMatch = getBestMatch(filteredMethods, methodToClassMapper, args);
        Class<?> methodInClass = methodToClassMapper.get(bestMatch);
        Object obj = getInstanceFromClass(methodInClass);

        return invokeMethod( obj,bestMatch, args);
    }

    /**
     * the method will create an instance of the class, based on the interfaceToObjectMapper if can,
     * if not it will create new.
     * @param clazz the class/interface
     * @return object instance of the clazz
     */
    private Object getInstanceFromClass(Class<?> clazz) {
       return interfaceToObjectMapper!=null ? interfaceToObjectMapper.get(clazz) : getInstanceByConvention(false,clazz);
    }


    /**
     * the method validates for Coincidental Ambiguity in the superClasses(& super interfaces) of interfaceClass
     * with methodName and arguments
     * @param interfaceClass the interface class
     * @param methodName the method name
     * @param args the arguments
     * @return a list of methods which were filtered by method name and arguments
     * @throws OOPCoincidentalAmbiguity if there is ambiguity
     */
    private List<Method> validateCoincidentalAmbiguity(Class<?> interfaceClass
                                                       , String methodName, Object... args) throws OOPCoincidentalAmbiguity {
        Class<?>[] superClasses = interfaceClass.getInterfaces();
        List<Method> filteredByNameAndArguments = new ArrayList<>();

        //we iterate on all super classes and we collect all methods which are equal by name and possible arguments
        for (Class<?> superClass : superClasses) {
            final List<Method> superClassMethods = new ArrayList<>(Arrays.asList(superClass.getMethods()));
            Stream<Method> filteredByName = filterByMethodName(methodName, superClassMethods);
            filteredByNameAndArguments.addAll(filterByArguments(filteredByName, args));
        }
        if(filteredByNameAndArguments.size()>1) {
            //now we have collected all the methods, so we search for collisions
            final Set<Method> collisions = getCollidedMethods(filteredByNameAndArguments);
            //if we found one by now then we throw an exception
            if (collisions.size() > 0) {
                Collection<Pair<Class<?>, Method>> pairs = new HashSet<>();
                //we warp it as a pair before throwing
                collisions.forEach(m -> pairs.add(new Pair<>(methodToClassMapper.get(m), m)));
                throw new OOPCoincidentalAmbiguity(pairs);
            }
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
