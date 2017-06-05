package OOP.Solution.ReflectionUtils;

import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Solution.Multiple.OOPMultipleControl;
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

    private static final String CLASS_NAME_CONVENTION="C";
    private static final String INTERFACE_NAME_CONVENTION="I";
    private static final String PACKAGE_DELIMITER=".";


    /*public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = type;
        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    Annotation annotInstance = method.getAnnotation(annotation);
                    // TODO process annotInstance
                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }*/
    public static Map<Method,Class<?>> mapMethodToClass(Class<?>[] superClasses){
        Map<Method,Class<?>> classMap=new Hashtable<>();
        //we iterate on all super classes and we collect all methods which are equal by name and possible arguments
        for(Class<?> superClass : superClasses){
            final List<Method> superClassMethods = new ArrayList<>(Arrays.asList(superClass.getMethods()));
            //for later use we need to map each method to it's class
            superClassMethods.forEach(m-> classMap.put(m,superClass));
        }
        return classMap;
    }

    public static Object invokeMethod(Method method,Object obj,Object... args){
        try {
            if(args!=null)
                return method.invoke(obj,args);
            else
                return method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
           // e.printStackTrace();
        }
        return null;
    }

    public static Object getInstanceByConvention(Class<?> clazz) {
        Object obj=null;
        String packageName=clazz.getPackage().getName();
        String className=clazz.getSimpleName();
        if(clazz.isInterface() && className.startsWith(INTERFACE_NAME_CONVENTION)){
            //extracting the interface number
            String interfaceNumber=className.substring(1);
            Class<?> klass=ReflectionHelper.getClassByConvention(packageName+PACKAGE_DELIMITER,interfaceNumber);
            try {
                if(klass!=null)
                    obj=klass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                //e.printStackTrace();
            }

        }else if(clazz.getName().startsWith(CLASS_NAME_CONVENTION)){
            //TODO: WHAT IF THE CLASS IS ABSTRACT?? do we care about it?
            try {
                obj=clazz.newInstance();
            } catch (InstantiationException e) {
               // e.printStackTrace();
            } catch (IllegalAccessException e) {
               // e.printStackTrace();
            }

        }
        return obj;
    }

    public static Class<?> getClassByConvention(String prefix, String sufix){
        Class<?> klass=null;
        try {
           klass=Class.forName(prefix+CLASS_NAME_CONVENTION+sufix);

        } catch (ClassNotFoundException e) {

        }
        return klass;
    }

    /**
     * the method calculates the total distance of each argument from the method actual types.
     * @param method the method to calculate the distance from args
     * @param args the arguments
     * @return the distance
     */
    public static int calculateMethodPath(Method method,Object ...args){
        if(args==null)
            return 0;
        int totalDistance=0;
        Class<?>[] methodTypes=method.getParameterTypes();

        for(int i=0;i<args.length;i++){
            Object argument=args[i];
            Class<?> type=methodTypes[i];
            if(type.isInstance(argument)){
                Class<?> argumentClass=argument.getClass();

                if(type.isInterface()){
                    boolean wasTypeBeenFound=false;
                    while(!wasTypeBeenFound){
                        List<Class<?>> foundedInterfaces=Arrays.stream(argumentClass.getInterfaces()).filter(type::isAssignableFrom).collect(Collectors.toList());
                        while(foundedInterfaces.size()>0){
                            //only one can be found
                            Class<?> foundedInterface=foundedInterfaces.get(0);
                            if(foundedInterface.equals(type))
                                wasTypeBeenFound=true;
                            totalDistance++;
                            foundedInterfaces=Arrays.stream(foundedInterface.getInterfaces()).filter(type::isAssignableFrom).collect(Collectors.toList());
                        }
                        if(foundedInterfaces.size()==0 && !wasTypeBeenFound){
                            argumentClass=argumentClass.getSuperclass();
                        }
                    }

                }else{ //the type is a class , so we need to search only in class path
                    while(argumentClass.getSuperclass()!=null && argumentClass.getSuperclass()!=Object.class && !argumentClass.equals(type) ){
                        totalDistance++;
                        argumentClass=argumentClass.getSuperclass();
                    }
                }

            }
        }
        return totalDistance;
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
    private static class MethodDistance {
        int distance;
        Method method;

        MethodDistance(int distance, Method method) {
            this.distance = distance;
            this.method = method;
        }

    }



    private static MethodDistance createMethodDistanceObject(Method method, Object[] args) {
        int distance = ReflectionHelper.calculateMethodPath(method, args);
        return new MethodDistance(distance, method);
    }

    public static Method getBestMatch(List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) throws OOPCoincidentalAmbiguity {
        return getBestMatch(false,filteredMethods,classMap,args);
    }

    /**
     * the method get the best match from the filtered methods which have the shortest path from args to method types.
     *
     * @param filteredMethods the method which were filtered to be by name and arguments.
     * @param args            the actual arguments
     * @return the method which have the best match
     */
    public static Method getBestMatch(boolean skipExceptionCheck,List<Method> filteredMethods, Map<Method, Class<?>> classMap, Object... args) throws OOPCoincidentalAmbiguity {
        PriorityQueue<MethodDistance> queue = new PriorityQueue<>(Comparator.comparingInt(m -> m.distance));
        filteredMethods.forEach(method -> queue.add(createMethodDistanceObject(method, args)));
        MethodDistance bestMatch = queue.poll();
        //if there is more than one match we need to see if there are equals matches, if there are some then there is Coincidental Ambiguity
        if (!skipExceptionCheck && !queue.isEmpty()) {
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
        methodList.forEach(m -> allMethods.add(m));

        //now we can subtract with the regular equal method which is not by arguments but by class
        allMethods.removeAll(regularUniqueMethods);
        return allMethods;
    }

    public static Stream<Method> filterByMethodName(String methodName, List<Method> superClassMethods) {
        return superClassMethods.stream().filter(superClassMethod -> superClassMethod.getName().equals(methodName));
    }

    public static List<Method> filterByArguments(Stream<Method> filteredByName, Object[] args) {
        return filteredByName.filter(m -> checkForArgsEquality(m, args)).collect(Collectors.toList());
    }

    public static boolean checkForArgsEquality(Method m, Object[] args) {
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
    }


    public  static Set<Class<?>> getAllOurTypes(Class<?> clazz){
        Set<Class<?>> allClasses=new HashSet<>();
        if(clazz!=null && clazz!=Object.class){
            allClasses.addAll(Arrays.asList(clazz.getInterfaces()));
            Class<?> superClass=clazz.getSuperclass();
            if(superClass!=null && superClass!=Object.class){
                allClasses.add(superClass);
                allClasses.addAll(getAllOurTypes(superClass));
            }
            for (Class<?> interFace : clazz.getInterfaces()){
                allClasses.addAll(getAllOurTypes(interFace));
            }

        }
        return allClasses;
    }

   public static List<Method> getAllOurMethods(Class<?> klass){
       final List<Method> methods = new ArrayList<Method>();
       if (klass!=null && klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
           // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
           final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
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
           for(Class<?> interFace:klass.getInterfaces())
               methods.addAll(getAllOurMethods(interFace));
       }
       return methods;
   }

}
