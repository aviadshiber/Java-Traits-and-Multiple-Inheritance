package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.ReflectionUtils.ReflectionHelper;
import javafx.util.Pair;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static OOP.Solution.ReflectionUtils.ReflectionHelper.filterByArguments;
import static OOP.Solution.ReflectionUtils.ReflectionHelper.filterByMethodName;
import static OOP.Solution.ReflectionUtils.ReflectionHelper.getCollidedMethods;

public class OOPMultipleControl {

    // DO NOT CHANGE !!!!!!
    private Class<?> interfaceClass;
    private File sourceFile;


    // DO NOT CHANGE !!!!!!
    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //need to scan for common parent and throw exception if there is one.
    public void validateInheritanceGraph() throws OOPMultipleException {
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
                    final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(superClass.getDeclaredMethods()));
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
     * @param methodName
     * @param args
     * @return the object that we invoke on
     * @throws OOPMultipleException
     */
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {
        //we map every method to a it's class for later use
        Map<Method, Class<?>> classMap = ReflectionHelper.mapMethodToClass(interfaceClass.getInterfaces());

        List<Method> filteredMethods = validateCoincidentalAmbiguity(interfaceClass, classMap, methodName
                , args);
        Method bestMatch = getBestMatch(filteredMethods, classMap, args);
        Class<?> methodInClass = classMap.get(bestMatch);
        Object obj = ReflectionHelper.getInstanceByConvention(methodInClass);
        Object returnValue= ReflectionHelper.invokeMethod(bestMatch, obj, args);
        return bestMatch.getReturnType().equals(Void.class) ? null : returnValue;
    }


    /**
     * a wrapper class to save the methods by their distance
     */
    private class MethodDistance {
        int distance;
        Method method;

        public MethodDistance(int distance, Method method) {
            this.distance = distance;
            this.method = method;
        }

    }

    /**
     * the method get the best match from the filtered methods which have the shortest path from args to method types.
     *
     * @param filteredMethods the method which were filtered to be by name and arguments.
     * @param args            the actual arguments
     * @return the method which have the best match
     */
    private Method getBestMatch(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) throws OOPCoincidentalAmbiguity {
        PriorityQueue<MethodDistance> queue = new PriorityQueue<>(Comparator.comparingInt(m -> m.distance));
        filteredMethods.forEach(method -> queue.add(createMethodDistanceObject(method, args)));
        MethodDistance bestMatch = queue.poll();
        //if there is more than one match we need to see if there are equals matches, if there are some then there is Coincidental Ambiguity
        if (!queue.isEmpty()) {
            MethodDistance nextMatch = queue.poll();
            Collection<Pair<Class<?>, Method>> pairs = new HashSet<>();
            pairs.add(new Pair<>(classMap.get(bestMatch.method), bestMatch.method));
            while (nextMatch.distance == bestMatch.distance) {
                pairs.add(new Pair<>(classMap.get(nextMatch.method), nextMatch.method));
                if (queue.isEmpty())
                    break;
                nextMatch = queue.poll();
            }
            if (pairs.size() > 1) {
                throw new OOPCoincidentalAmbiguity(pairs);
            }
        }
        //return the minimal distance- the best match
        return bestMatch.method;
    }

    private MethodDistance createMethodDistanceObject(Method method, Object[] args) {
        int distance = ReflectionHelper.calculateMethodPath(method, args);
        return new MethodDistance(distance, method);
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
            collisions.stream().forEach(m -> pairs.add(new Pair<>(classMap.get(m), m)));
            throw new OOPCoincidentalAmbiguity(pairs);
        }
        //no collisions were found so we return what we found so far
        return filteredByNameAndArguments;
    }


    /*private Set<Method> getCollidedMethods(List<Method> methodList) {
        *//**
         * the class was made to make a set of unique methods only the (comparing is between their arguments)
         *//*
        class MethodComparator {
            Method method;

            public MethodComparator(Method method) {
                this.method = method;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                MethodComparator that = (MethodComparator) o;

                return method != null ? ReflectionHelper.methodsHaveSameArguments(method, that.method) : that.method == null;
            }

            @Override
            public int hashCode() {
                return method != null ? method.hashCode() : 0;
            }
        }

        //we create special set to compare between methods by their arguments (a sub Set of methodList)
        Set<MethodComparator> uniqeMethods = new HashSet<>();
        methodList.forEach(method -> uniqeMethods.add(new MethodComparator(method)));
        //unwrap it to regular Set
        Set<Method> regularUniqueMethods = uniqeMethods.stream().map(mc -> mc.method).collect(Collectors.toSet());
        Set<Method> allMethods = new HashSet<>();
        //convert methodList to Set
        methodList.stream().forEach(m -> allMethods.add(m));

        //now we can subtract with the regular equal method which is not by arguments but by class
        allMethods.removeAll(regularUniqueMethods);
        return allMethods;
    }

    private Stream<Method> filterByMethodName(String methodName, List<Method> superClassMethods) {
        return superClassMethods.stream().filter(superClassMethod -> superClassMethod.getName().equals(methodName));
    }

    private List<Method> filterByArguments(Stream<Method> filteredByName, Object[] args) {
        return filteredByName.filter(m -> checkForArgsEquality(m, args)).collect(Collectors.toList());
    }

    private boolean checkForArgsEquality(Method m, Object[] args) {
        Type[] types = m.getParameterTypes();
        if(types.length==0 )
            return args==null;
        if (args.length != types.length)
            return false;
        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i].getClass();
            Object object = args[i];
            if (!type.isInstance(object))
                return false;
        }
        return true;
    }*/





    //DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
