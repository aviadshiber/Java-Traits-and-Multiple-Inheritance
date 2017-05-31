package OOP.Solution.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ashiber on 30-May-17.
 */
public class ReflectionHelper {

    public static final String CLASS_NAME_CONVENTION="C";
    public static final String INTERFACE_NAME_CONVENTION="I";


    public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
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
    }
    public static HashMap<Method,Class<?>> mapMethodToClass(Class<?>[] superClasses){
        HashMap<Method,Class<?>> classMap=new HashMap<>();
        //we iterate on all super classes and we collect all methods which are equal by name and possible arguments
        for(Class<?> superClass : superClasses){
            final List<Method> superClassMethods = new ArrayList<>(Arrays.asList(superClass.getMethods()));
            //for later use we need to map each method to it's class
            superClassMethods.forEach(m-> classMap.put(m,superClass));
        }
        return classMap;
    }

    public static void invokeMethod(Method method,Object obj,Object... args){
        try {
            method.invoke(obj,args);
        } catch (IllegalAccessException e) {
           // e.printStackTrace();
        } catch (InvocationTargetException e) {
           // e.printStackTrace();
        }
    }

    public static Object getInstanceByConvention(Class<?> Clazz) {
        Object obj=null;
        if(Clazz.isInterface() && Clazz.getName().startsWith(INTERFACE_NAME_CONVENTION)){
            //extracting the interface number
            String interfaceNumber=Clazz.getName().substring(1);
            Class<?> klass=ReflectionHelper.getClassWithSufix(interfaceNumber);
            try {
                if(klass!=null)
                    obj=klass.newInstance();
            } catch (InstantiationException e) {
                //e.printStackTrace();
            } catch (IllegalAccessException e) {
                // e.printStackTrace();
            }

        }else if(Clazz.getName().startsWith(CLASS_NAME_CONVENTION)){
            //TODO: WHAT IF THE CLASS IS ABSTRACT?? do we care about it?
            try {
                obj=Clazz.newInstance();
            } catch (InstantiationException e) {
               // e.printStackTrace();
            } catch (IllegalAccessException e) {
               // e.printStackTrace();
            }

        }
        return obj;
    }

    public static Class<?> getClassWithSufix(String sufix){
        Class<?> klass=null;
        try {
           klass=Class.forName(CLASS_NAME_CONVENTION+sufix);

        } catch (ClassNotFoundException e) {

        }
        return klass;
    }

    /**
     * the method calculates the total distance of each argument from the method actual types.
     * @param method
     * @param args
     * @return
     */
    public static int calculateMethodPath(Method method,Object ...args){
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
                        List<Class<?>> foundedInterfaces=Arrays.stream(argumentClass.getInterfaces()).filter(argInterface-> type.isAssignableFrom(argInterface)).collect(Collectors.toList());
                        while(foundedInterfaces.size()>0){
                            //only one can be found
                            Class<?> foundedInterface=foundedInterfaces.get(0);
                            if(foundedInterface.equals(type))
                                wasTypeBeenFound=true;
                            totalDistance++;
                            foundedInterfaces=Arrays.stream(foundedInterface.getInterfaces()).filter(interfaze-> type.isAssignableFrom(interfaze)).collect(Collectors.toList());
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

}
