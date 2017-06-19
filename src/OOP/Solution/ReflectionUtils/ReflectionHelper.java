package OOP.Solution.ReflectionUtils;

import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Trait.OOPTraitConflict;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;
import javafx.util.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ashiber on 30-May-17.
 */
public class ReflectionHelper {

    public static final String CLASS_NAME_CONVENTION = "C";
    public static final String INTERFACE_NAME_CONVENTION = "I";
    private static final String PACKAGE_DELIMITER = ".";
    public static final String TRAIT_NAME_CONVENTION = "T";

    public static void TraitClassMapper(Map<Method, Class<?>> oldMapper) {
        for (Method m : new HashSet<>(oldMapper.keySet())) {
            Class<?> superInterfaceClass = oldMapper.get(m);
            Class<?> implClass = getClassByConvention(superInterfaceClass);
            if (implClass != null) {

                //if there is such a implementing class
                try {
                    final Method sameMethod = implClass.getMethod(m.getName(), m.getParameterTypes());
                        oldMapper.put(sameMethod, implClass);
                } catch (NoSuchMethodException e) {

                }
            }
        }
    }

    private static void mapMethodToClass(Class<? extends Annotation> methodAnnotation,Map<Method, Class<?>> classMap,Class<?>[] superClasses) {

        //we iterate on all super classes and we collect all methods which are equal by name and possible arguments
        for (Class<?> superInterfaceClass : superClasses) {
            //Arrays.stream(superInterfaceClass.getDeclaredMethods()).filter(m-> !Modifier.isPrivate(m.getModifiers())).collect(Collectors.toList())
            final List<Method> superClassMethods = Arrays.stream(superInterfaceClass.getDeclaredMethods()).filter(m-> isAnnotatedAndNotPrivate(m,methodAnnotation)).collect(Collectors.toList());
            //for later use we need to map each method to it's class
            superClassMethods.forEach(m -> classMap.put(m, superInterfaceClass));
            final Class<?>[] extendsFromInterfaces=superInterfaceClass.getInterfaces();
            mapMethodToClass(methodAnnotation,classMap,extendsFromInterfaces);
        }
        //adding methods of imp classes
             /*Class<?> implClass= getClassByConvention(superInterfaceClass);
             if(implClass!=null) { //if there is such a implementing class
                 final List<Method> superClassMethodsOfImpClass = new ArrayList<>(Arrays.asList(implClass.getMethods()));
                 superClassMethodsOfImpClass.forEach(m -> {classMap.put(m, implClass);});
             }*/

    }

    public static boolean isAnnotatedAndNotPrivate(Method m, Class<? extends Annotation> methodAnnotation) {
        return  m.isAnnotationPresent(methodAnnotation) && !Modifier.isPrivate(m.getModifiers());
    }

    public static Class<?> getClassByConvention(Class<?> clazz) {
        String packageName = clazz.getPackage().getName();
        String className = clazz.getSimpleName();
        if (clazz.isInterface() && (className.startsWith(TRAIT_NAME_CONVENTION) || className.startsWith(INTERFACE_NAME_CONVENTION))) {
            //extracting the interface number
            String interfaceNumber = className.substring(1);
            Class<?> klass = getClassByConvention(packageName + PACKAGE_DELIMITER, interfaceNumber);
            return klass;
        }
        return null;
    }

    /**
     * the method will do object.method(args) in reflection
     *
     * @param obj    object to do the invoke on
     * @param method the method to invoke
     * @param args   the arguments to invoke (null to use no args)
     * @return the return value of the invoke, or null if IllegalAccessException or InvocationTargetException
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object returnValue = null;
        try {
            if (args != null)
                returnValue = method.invoke(obj, args);
            else
                returnValue = method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // e.printStackTrace();
        }
        return method.getReturnType().equals(Void.class) ? null : returnValue;
    }

    /**
     * return the instance of the interface/class by the hw convention .
     *
     * @param clazz the class
     * @return the instance of that class
     */
    public static Object getInstanceByConvention(boolean isTrait, Class<?> clazz) {
        Object obj = null;
        String className = clazz.getSimpleName();
        String nameConvention = isTrait ? TRAIT_NAME_CONVENTION : INTERFACE_NAME_CONVENTION;
        if (clazz.isInterface() && className.startsWith(nameConvention)) {
            Class<?> klass = getClassByConvention(clazz);
            try {
                if (klass != null)
                    obj = klass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                //e.printStackTrace();
            }

        } else if (clazz.getName().startsWith(CLASS_NAME_CONVENTION)) {
            //TODO: WHAT IF THE CLASS IS ABSTRACT?? do we care about it?
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                // e.printStackTrace();
            }

        }
        return obj;
    }

    /**
     * return the Class by convention
     *
     * @param prefix the package
     * @param suffix the number
     * @return the class which was found, if no such class was found null will be returned.
     */
    public static Class<?> getClassByConvention(String prefix, String suffix) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(prefix + CLASS_NAME_CONVENTION + suffix);

        } catch (ClassNotFoundException ignored) {

        }
        return clazz;
    }

    //Work in progress
    public static Method findProperMethod(List<Method> allMethods, Object... args) throws OOPTraitConflict {
        Method closestMethod = null;
        if (methodAmbiguity(allMethods, args) != null)
            throw new OOPTraitConflict(methodAmbiguity(allMethods, args));
        Integer closestDistance = calculateMethodPath(allMethods.get(0), args);
        for (Method M : allMethods) {
            if (calculateMethodPath(M, args) < closestDistance) {
                closestDistance = calculateMethodPath(M, args);
                closestMethod = M;
            }
        }

        return closestMethod;
    }

    public static int calculateIthArgDistance(Method M, int i, Object... args) {
        if (args == null)
            return 0;
        int totalDistance = 0;
        Class<?>[] methodTypes = M.getParameterTypes();
        Object argument = args[i];
        Class<?> type = methodTypes[i];
        Class<?> argumentClass = argument.getClass();
        //if argument class is type than distance is 0 or
        // the argument is not instance of type we skip to next argument
        if (!type.equals(argumentClass) && type.isInstance(argument)) {
            boolean wasTypeBeenFound = false;
            while (!wasTypeBeenFound) {
                List<Class<?>> foundedInterfaces = getInterfacesAssignableFrom(argumentClass, type);
                while (isThereInterfacesToScan(foundedInterfaces)) {

                    //only one can be found
                    Class<?> foundedInterface = foundedInterfaces.get(0);
                    if (foundedInterface.equals(type)) {
                        wasTypeBeenFound = true;
                    }
                    totalDistance++;
                    foundedInterfaces = getInterfacesAssignableFrom(foundedInterface, type);
                }
                //if not interfaces were found and we still have not found it, search in in class hierarchy
                if (!isThereInterfacesToScan(foundedInterfaces) && !wasTypeBeenFound) {
                    argumentClass = argumentClass.getSuperclass();
                    totalDistance++;
                }
                if (argumentClass.equals(type))
                    wasTypeBeenFound = true;
            }

        }
        return totalDistance;
    }


    //returns true if there are atleast two methods that cause ambiguity, and false otherwise
    public static Method methodAmbiguity(List<Method> allMethods, Object... args) {
        if (args == null)
            return null;
        boolean possibleNewMin = false;
        boolean forSureNotNewMin = false;
        int[] minDist = new int[args.length];
        for (int i = 0; i < minDist.length; i++)
            minDist[i] = -1;
        for (Method M : allMethods) {
            for (int i = 0; i < args.length; i++) {
                int dist = calculateIthArgDistance(M, i, args);
                if (dist < minDist[i] || minDist[i] == -1) {
                    possibleNewMin = true;
                    minDist[i] = dist;
                }
                if (possibleNewMin && dist > minDist[i] && minDist[i] != -1)
                    return M;
                if (dist > minDist[i] && minDist[i] != -1)
                    forSureNotNewMin = true;
                if (forSureNotNewMin && dist < minDist[i])
                    return M;
            }
            possibleNewMin = false;
            forSureNotNewMin = false;
        }
        return null;
    }
    public static List<Pair<Method,Method>> methodPairAmbiguity(List<Method> allMethods, Object... args){
        List<Method> methodPair = new ArrayList<>();
        List<Pair<Method,Method>> conflictedPairs = new ArrayList<>();
        for(Method M :allMethods){
            for(Method M2 :allMethods){
                methodPair.add(M);
                methodPair.add(M2);
                if(methodAmbiguity(methodPair,M.getParameterTypes())!=null)
                    conflictedPairs.add(new Pair<>(M,M2));
                methodPair.clear();
            }
        }
        if(conflictedPairs.size()!=0)
            return conflictedPairs;
        return null;
    }

    /**
     * the method calculates the total distance of each argument from the method actual types.
     * ******the method assumes that the method have co-variance conformance with the args at least.****
     *
     * @param method the method to calculate the distance from args
     * @param args   the arguments
     * @return the distance
     */
    public static int calculateMethodPath(Method method, Object... args) {
        if (args == null)
            return 0;
        int totalDistance = 0;
        for (int i = 0; i < args.length; i++)
            totalDistance += calculateIthArgDistance(method, i, args);
        return totalDistance;
    }

    /**
     * gets all the interfaces which argumentClass inherent from type
     *
     * @param argumentClass the argument class
     * @param type          the type
     * @return interfaces
     */
    private static List<Class<?>> getInterfacesAssignableFrom(Class<?> argumentClass, Class<?> type) {
        Class<?>[] interfaces = argumentClass.getInterfaces();
        return (interfaces != null) ? Arrays.stream(interfaces).filter(type::isAssignableFrom).collect(Collectors.toList()) : null;
    }

    /**
     * the method return true if is there any interfaces to scan in the list.
     *
     * @param foundedInterfaces the interfaces
     * @return true if is there any interfaces to scan in the list, false o.w
     */
    private static boolean isThereInterfacesToScan(List<Class<?>> foundedInterfaces) {
        return (foundedInterfaces != null && foundedInterfaces.size() > 0);
    }


    /**
     * the method checks if two Methods have the same arguments.
     * since java is no-variance they must have the same size, and the same types.
     *
     * @param methodOne method one
     * @param methodTwo method two
     * @return true if two method have the same name and same arguments
     */
    public static boolean methodsHaveSameArguments(Method methodOne, Method methodTwo) {
        Type[] methodOneTypes = methodOne.getGenericParameterTypes();
        Type[] methodTwoTypes = methodTwo.getParameterTypes();
        if (methodOneTypes.length != methodTwoTypes.length) {
            return false;
        } else {
            for (int i = 0; i < methodOneTypes.length; i++) {
                if (!methodOneTypes[i].getTypeName().equals(methodTwoTypes[i].getTypeName()))
                    return false;
            }

        }
        return true;
    }


    /**
     * a wrapper class to save the methods by their distance
     */
    public static class MethodDistance {
        int distance;
        Method method;

        MethodDistance(int distance, Method method) {
            this.distance = distance;
            this.method = method;
        }

    }


    /**
     * the method create a MethodDistance object based on the distance from his arguments.
     *
     * @param method the method
     * @param args   arguments to compute the distance from
     * @return MethodDistance object
     */
    private static MethodDistance createMethodDistanceObject(Method method, Object[] args) {
        int distance = ReflectionHelper.calculateMethodPath(method, args);
        return new MethodDistance(distance, method);
    }

    /**
     * the method get the best match from the filtered methods which have the shortest path from args to method types.
     *
     * @param filteredMethods the method which were filtered to be by name and arguments.
     * @param classMap        the actual arguments
     * @param args            the arguments
     * @return the method which have the best match
     * @throws OOPCoincidentalAmbiguity
     */
    public static Method getBestMatch(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) throws OOPCoincidentalAmbiguity {
        return getBestMatch(false, filteredMethods, classMap, args);
    }

    /**
     * return the
     *
     * @param filteredMethods
     * @param classMap
     * @param args
     * @return
     */
    public static PriorityQueue<MethodDistance> getAllBestMatches(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) {
        PriorityQueue<MethodDistance> queue = new PriorityQueue<>(Comparator.comparingInt(m -> m.distance));
        filteredMethods.forEach(method -> queue.add(createMethodDistanceObject(method, args)));
        return queue;
    }

    private static Collection<Pair<Class<?>, Method>> getClosestMethodsAsPairs(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) {
        Collection<Pair<Class<?>, Method>> pairs = new HashSet<>();
        PriorityQueue<MethodDistance> queue = getAllBestMatches(filteredMethods, classMap, args);
        MethodDistance bestMatch = queue.poll();
        pairs.add(new Pair<>(classMap.get(bestMatch.method), bestMatch.method));
        if (!queue.isEmpty()) {
            MethodDistance nextMatch = queue.poll();
            while (nextMatch.distance == bestMatch.distance) {
                pairs.add(new Pair<>(classMap.get(nextMatch.method), nextMatch.method));
                if (queue.isEmpty())
                    break;
                nextMatch = queue.poll();
            }
        }
        return pairs;
    }

    public static Pair<Map<Class<?>, Object>, Map<Method, Class<?>>> getInitMaps(boolean isTrait, Class<?> interfaceClass, Class<? extends Annotation> methodAnnotation,Class<? extends Annotation> classAnnotation) {

        Map<Method, Class<?>> methodToClassMapper = new Hashtable<>();
        //fills the method to class map
        mapMethodToClass(methodAnnotation,methodToClassMapper, interfaceClass.getInterfaces());
        /*if(isTrait){
            Map<Method, Class<?>> traitMethodToClassMapper = new Hashtable<>();
            List<Method> implemented = methodToClassMapper.keySet().stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
            for(Method m : implemented){
                traitMethodToClassMapper.put(m,methodToClassMapper.get(m));
            }
            methodToClassMapper = traitMethodToClassMapper;
        }*/
        Map<Class<?>, Object> interfaceToObjectMapper = new Hashtable<>();
        //fills the interface to object map
        Collection<Class<?>> allClasses = methodToClassMapper.values();
        List<Class<?>> annotatedClasses = allClasses.stream().filter(c -> c.isAnnotationPresent(classAnnotation)).collect(Collectors.toList());
        for (Class<?> clazz : annotatedClasses) {
            Object objectInstance = getInstanceByConvention(isTrait, clazz);
            if(objectInstance!=null) { //could be null when dealing with traits
                //map interface to object
                interfaceToObjectMapper.put(clazz, objectInstance);
                //map class to object
                interfaceToObjectMapper.put(objectInstance.getClass(), objectInstance);
            }
        }
        return new Pair<>(interfaceToObjectMapper, methodToClassMapper);
    }


    /**
     * the method get the best match from the filtered methods which have the shortest path from args to method types.
     *
     * @param skipExceptionCheck skips the exception check if you don't want the method will really throw the exception, and check will be skipped
     * @param filteredMethods    the method which were filtered to be by name and arguments.
     * @param args               the actual arguments
     * @return the method which have the best match
     */
    public static Method getBestMatch(boolean skipExceptionCheck, List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) throws OOPCoincidentalAmbiguity {

        Collection<Pair<Class<?>, Method>> pairs = getClosestMethodsAsPairs(filteredMethods, classMap, args);
        //if there is more than one match we need to see if there are equals matches, if there are some then there is Coincidental Ambiguity
        if (pairs.size() > 1) {
            throw new OOPCoincidentalAmbiguity(pairs);
        }
        //return the minimal distance- the best match
        ArrayList<Pair<Class<?>, Method>> arrayPairs = new ArrayList<>(pairs);
        Method bestMatch = arrayPairs.get(0).getValue();
        return bestMatch;
    }

    public static List<Method> getClosestMethods(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) {
        return pairsToMethodList(getClosestMethodsAsPairs(filteredMethods, classMap, args));
    }

    private static List<Method> pairsToMethodList(Collection<Pair<Class<?>, Method>> pairs) {
        return pairs.stream().map(pair -> pair.getValue()).collect(Collectors.toList());
    }

    public static boolean isAnnotatedBy(Method m, Class<OOPTraitMethod> oopTraitMethodClass, OOPTraitMethodModifier inter) {
        if (m.isAnnotationPresent(oopTraitMethodClass)) {
            OOPTraitMethod mod = m.getAnnotation(oopTraitMethodClass);
            return mod.modifier().equals(inter);
        }
        return false;
    }

    /**
     * the method return a vector which represent on the ith place the number of upcasting needed
     * to do from args to method types.
     * @param method the method to check- must have the same number elements as args
     * @param args the actual arguments
     * @return
     */
    public static ArrayList<Integer> getUpcastingVector(Method method,Object...args){
        if(args!=null) {
            ArrayList<Integer> vector = new ArrayList<>(args.length);
            for (int i = 0; i < args.length; i++) {
                vector.set(i, calculateIthArgDistance(method, i, args));
            }
            return vector;
        }
        return new ArrayList<>();
    }

    /**
     * method check if there is ambiguity between the two methods.
     * the method assumes that the two methods have the same name and same number of arguments.
     * @param one method one
     * @param two method two
     * @param args the actual arguments
     * @return
     */
    public static boolean pairAmbiguity(Method one,Method two,Object... args) {
        if (args == null)
            return true;
        ArrayList<Integer> firstArgsDist = getUpcastingVector(one,args);
        ArrayList<Integer> secondArgsDist = getUpcastingVector(two,args);
        if(firstArgsDist.size()==0 || secondArgsDist.size()==0) return false;
        boolean isMinBeenSwapped;
        int num1=firstArgsDist.get(0);
        int num2=secondArgsDist.get(0);
        int min= num1<num2 ?num1 :num2;
        boolean lastMinWasOne = min==num1;
        for (int i = 0; i < args.length; i++) {
            num1=firstArgsDist.get(i);
            num2=secondArgsDist.get(i);
            min= num1<num2 ?num1 :num2;
            boolean currentIsOne= min==num1;
            isMinBeenSwapped=currentIsOne!=lastMinWasOne;
            if(isMinBeenSwapped)
                return true;
            lastMinWasOne=currentIsOne;
        }
        return false;
    }

    /**
     * return a set of the collided methods from a list
     * the method assumes that all methods in methodList have the same name & num of arguments
     *
     * @param methodList method list
     * @return a set of the collided methods in a list
     */
    public static Set<Method> getCollidedMethods(List<Method> methodList) {
       Set<Method> collided = new HashSet<>();
       for(Method one:methodList){
           for(Method two:methodList)
               if(pairAmbiguity(one,two)) {
                   collided.add(one);
                   collided.add(two);
               }
       }
       return collided;
    }

    /**
     * the method filter from superClassMethods all the method with methodName
     *
     * @param methodName        method name
     * @param superClassMethods super class methods
     * @return a stream of all methods with method name in superClassMethods
     */
    public static Stream<Method> filterByMethodName(String methodName, List<Method> superClassMethods) {
        return superClassMethods.stream().filter(superClassMethod -> superClassMethod.getName().equals(methodName));
    }

    /**
     * the method filter all the Method stream which was filtered by name by their arguments equality
     * (inheritance equality)
     *
     * @param filteredByName the methods
     * @param args           the argument to filter by
     * @return a list of all the filtered methods
     */
    public static List<Method> filterByArguments(Stream<Method> filteredByName, Object[] args) {
        return filteredByName.filter(m -> checkForArgsEquality(m, args)).collect(Collectors.toList());
    }

    public static List<Method> filterByArgumentsTypes(Stream<Method> filteredByName, Class<?>[] otherTypes) {
        return filteredByName.filter(m -> checkForTypesEquality(m, otherTypes)).collect(Collectors.toList());
    }
    /**
     * the method if args can be possibly match with the method types (inheritance equality=co-variance equality)
     *
     * @param m    the method
     * @param args the arguments
     * @return true if they are equal, false o.w
     */
    private static boolean checkForArgsEquality(Method m, Object[] args) {
        Class<?>[] types = m.getParameterTypes();
        if (types.length == 0)
            return args == null;
        if(args==null && types.length!=0)
            return false;
        if (args.length != types.length)
            return false;
        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i];
            Object object = args[i];
            if (!type.isInstance(object))
                return false;
        }
        return true;
    }

    private static boolean checkForTypesEquality(Method m, Class<?>[] otherTypes) {
        Class<?>[] types = m.getParameterTypes();
        if(types==null || otherTypes==null)
            return types==otherTypes;
        if (types.length == 0)
            return otherTypes.length==0;

        if (otherTypes.length != types.length)
            return false;
        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i];
            Class<?> otherType = otherTypes[i];
            if (!type.isAssignableFrom(otherType))
                return false;
        }
        return true;
    }



    /**
     * the method return all the classes and interfaces until (not include) the Object class.
     *
     * @param clazz the class
     * @return a set of all the candidates
     */
    public static Set<Class<?>> getAllOurTypes(Class<?> clazz) {
        Set<Class<?>> allClasses = new HashSet<>();
        if (clazz != null && clazz != Object.class) {
            allClasses.addAll(Arrays.asList(clazz.getInterfaces()));
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                allClasses.add(superClass);
                allClasses.addAll(getAllOurTypes(superClass));
            }
            for (Class<?> interFace : clazz.getInterfaces()) {
                allClasses.addAll(getAllOurTypes(interFace));
            }

        }
        return allClasses;
    }

    /**
     * return all the methods until (not include) the Object class.
     *
     * @param klass the class
     * @return list of methods
     */
    public static List<Method> getAllOurMethods(Class<?> klass) {
        final List<Method> methods = new ArrayList<>();
        if (klass != null && klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<>(Arrays.asList(klass.getDeclaredMethods()));
            methods.addAll(allMethods);
            // move to the upper class in the hierarchy in search for more methods
           /*if(klass.isInterface()){
               String packageName=klass.getPackage().getName();
               String className=klass.getSimpleName();
               String interfaceNumber=className.substring(1);
               Class<?> classImp=getClassByConvention(packageName,interfaceNumber);
               if(classImp!=null){
                   methods.addAll(Arrays.asList(classImp.getDeclaredMethods()));
               }

           }*/
            for (Class<?> interFace : klass.getInterfaces())
                methods.addAll(getAllOurMethods(interFace));
        }
        return methods;
    }

}
