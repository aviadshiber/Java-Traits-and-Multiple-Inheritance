package OOP.Solution.ReflectionUtils;

import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Trait.OOPTraitConflict;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;
import javafx.util.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


    public static Map<Method, Class<?>> mapMethodToClass(Class<?>[] superClasses) {
        Map<Method, Class<?>> classMap = new Hashtable<>();
        //we iterate on all super classes and we collect all methods which are equal by name and possible arguments
        for (Class<?> superInterfaceClass : superClasses) {
            final List<Method> superClassMethods = new ArrayList<>(Arrays.asList(superInterfaceClass.getMethods()));
            //for later use we need to map each method to it's class
            superClassMethods.forEach(m -> classMap.put(m, superInterfaceClass));
            //adding methods of imp classes
             Class<?> implClass= getClassByConvention(superInterfaceClass);
             if(implClass!=null) { //if there is such a implementing class
                 final List<Method> superClassMethodsOfImpClass = new ArrayList<>(Arrays.asList(implClass.getMethods()));
                 superClassMethodsOfImpClass.forEach(m -> {if(!classMap.containsKey(m))classMap.put(m, implClass);});
             }

        }

        return classMap;
    }

    private static Class<?> getClassByConvention(Class<?> clazz) {
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
       Object returnValue=null;
        try {
            if (args != null)
                returnValue= method.invoke(obj, args);
            else
                returnValue= method.invoke(obj);
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
    public static Object getInstanceByConvention(boolean isTrait,Class<?> clazz) {
        Object obj = null;
        String className = clazz.getSimpleName();
        String nameConvention=isTrait? TRAIT_NAME_CONVENTION :INTERFACE_NAME_CONVENTION;
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
    public static int findProperMethod(List<Method> allMethods, Object... args) throws OOPTraitConflict{
        Method closestMethod;
        Integer closestDistance = calculateMethodPath(allMethods.get(0),args);
        for(Method M : allMethods){
            if(calculateMethodPath(M,args)<closestDistance){
                closestDistance = calculateMethodPath(M,args);
                closestMethod = M;
            }
        }


    }
    public static Integer distanceFromObject(Class<?> type){

    }
    public static boolean methodAmbiguity(Method one,Method two){
        Class<?>[] oneTypes = one.getParameterTypes();
        Class<?>[] twoTypes = two.getParameterTypes();
        if(oneTypes.length != twoTypes.length)
            return false;
        boolean oneIsLower = false, twoIsLower = false;
        for(int i = 0; i < oneTypes.length; i++){

        }
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
        Class<?>[] methodTypes = method.getParameterTypes();

        for (int i = 0; i < args.length; i++) {
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
        }
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
     * @param filteredMethods
     * @param classMap
     * @param args
     * @return
     */
    public static  PriorityQueue<MethodDistance> getAllBestMatches(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args){
        PriorityQueue<MethodDistance> queue = new PriorityQueue<>(Comparator.comparingInt(m -> m.distance));
        filteredMethods.forEach(method -> queue.add(createMethodDistanceObject(method, args)));
        return queue;
    }
    private static  Collection<Pair<Class<?>, Method>> getClosestMethodsAsPairs(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args){
        Collection<Pair<Class<?>, Method>> pairs = new HashSet<>();
        PriorityQueue<MethodDistance> queue=getAllBestMatches(filteredMethods,classMap,args);
        MethodDistance bestMatch = queue.poll();
        pairs.add(new Pair<>(classMap.get(bestMatch.method), bestMatch.method));
        if(!queue.isEmpty()){
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

    public static Pair<Map<Class<?>, Object>, Map<Method, Class<?>>> getInitMaps(boolean isTrait,Class<?> interfaceClass, Class<? extends Annotation> annotation) {
        //fills the method to class map
        Map<Method, Class<?>> methodToClassMapper =mapMethodToClass(interfaceClass.getInterfaces());
        if(isTrait){
            Map<Method, Class<?>> traitMethodToClassMapper = new Hashtable<>();
            List<Method> implemented = methodToClassMapper.keySet().stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
            for(Method m : implemented){
                traitMethodToClassMapper.put(m,methodToClassMapper.get(m));
            }
            methodToClassMapper = traitMethodToClassMapper;
        }
        Map<Class<?>, Object> interfaceToObjectMapper = new Hashtable<>();
        //fills the interface to object map
        Collection<Class<?>> allClasses = methodToClassMapper.values();
        List<Class<?>> annotatedClasses = allClasses.stream().filter(c -> c.isAnnotationPresent(annotation)).collect(Collectors.toList());
        for(Class<?> clazz : annotatedClasses){
            Object objectInstance=getInstanceByConvention(isTrait,clazz);
            //map interface to object
            interfaceToObjectMapper.put(clazz,objectInstance );
            //map class to object
            interfaceToObjectMapper.put(objectInstance.getClass(),objectInstance );
        }
        return new Pair<>(interfaceToObjectMapper,methodToClassMapper);
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

        Collection<Pair<Class<?>, Method>> pairs=getClosestMethodsAsPairs(filteredMethods,classMap,args);
        //if there is more than one match we need to see if there are equals matches, if there are some then there is Coincidental Ambiguity
        if (pairs.size() > 1) {
                throw new OOPCoincidentalAmbiguity(pairs);
        }
        //return the minimal distance- the best match
        ArrayList<Pair<Class<?>, Method>> arrayPairs=new ArrayList<>(pairs);
        Method bestMatch=arrayPairs.get(0).getValue();
        return bestMatch;
    }
    public static List<Method> getClosestMethods(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args){
        return pairsToMethodList(getClosestMethodsAsPairs(filteredMethods,classMap,args));
    }
    private static List<Method> pairsToMethodList(Collection<Pair<Class<?>, Method>> pairs){
        return pairs.stream().map(pair-> pair.getValue()).collect(Collectors.toList());
    }
    public static boolean isAnnotatedBy(Method m, Class<OOPTraitMethod> oopTraitMethodClass, OOPTraitMethodModifier inter) {
        if (m.isAnnotationPresent(oopTraitMethodClass)) {
            OOPTraitMethod mod = m.getAnnotation(oopTraitMethodClass);
            return mod.modifier().equals(inter);
        }
        return false;
    }

    /**
     * return a set of the collided methods(methods which have the same name and arguments) from a list
     *
     * @param methodList method list
     * @return a set of the collided methods in a list
     */
    public static Set<Method> getCollidedMethods(List<Method> methodList) {
        /*
          the class was made to make a set of unique methods only the (comparing is between their arguments)
         */
        class MethodComparator {
            Method method;

            MethodComparator(Method method) {
                this.method = method;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                MethodComparator that = (MethodComparator) o;

                return method != null ? this.method.getName().equals(that.method.getName()) && ReflectionHelper.methodsHaveSameArguments(method, that.method) : that.method == null;
            }

            @Override
            public int hashCode() {
                return method != null ? method.hashCode() : 0;
            }
        }

        //we create special set to compare between methods by their arguments (a sub Set of methodList)
        Set<MethodComparator> uniqueMethods = new HashSet<>();
        methodList.forEach(method -> uniqueMethods.add(new MethodComparator(method)));
        //unwrap it to regular Set
        Set<Method> regularUniqueMethods = uniqueMethods.stream().map(mc -> mc.method).collect(Collectors.toSet());
        Set<Method> allMethods = new HashSet<>();
        //convert methodList to Set
        allMethods.addAll(methodList);

        //now we can subtract with the regular equal method which is not by arguments but by class
        allMethods.removeAll(regularUniqueMethods);
        return allMethods;
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
