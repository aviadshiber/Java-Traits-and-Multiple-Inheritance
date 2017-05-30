package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPCoincidentalAmbiguity;
import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import javafx.util.Pair;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OOPMultipleControl {

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> interfaceClass;
    private File sourceFile;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here : need to scan for common parent and throw execption if there is one.
    public void validateInheritanceGraph() throws OOPMultipleException {
        Set<Class<?>> interfaceSet= new HashSet<>();
        validateForCommonParent(interfaceClass,interfaceSet,OOPMultipleInterface.class,OOPMultipleMethod.class);
    }

    /**
     * the method validate for common Parent with at least one method which exist using DFS traverse.
     * if such parent was found an exception will be thrown.
     * @param interfaceClass the interface to start the search.
     * @param interfaceSet the set to check of collisions.
     * @throws OOPInherentAmbiguity is a structural collision exception.
     */
    private void validateForCommonParent(Class<?> interfaceClass, Set<Class<?>> interfaceSet, Class<? extends  Annotation> classAnnotation,Class<? extends  Annotation> methodAnnotation) throws OOPInherentAmbiguity {
        Class<?>[] superClasses=interfaceClass.getInterfaces();
        for (Class<?> superClass: superClasses  ) {
            boolean isSuperClassAnnotated=superClass.isAnnotationPresent(classAnnotation);
            if(isSuperClassAnnotated) {
                boolean superClassHasAnyMethod=superClass.getDeclaredMethods().length > 0;
                if(superClassHasAnyMethod){
                    final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(superClass.getDeclaredMethods()));
                    final List<Method> annotatedMethods=allMethods.stream().filter(method-> method.isAccessible() && method.isAnnotationPresent(methodAnnotation)).collect(Collectors.toList());
                    boolean annotatedMethodExist=annotatedMethods.size()>0;
                    if (annotatedMethodExist) {
                        boolean structuralCollisionExist = interfaceSet.contains(superClass);
                        if(structuralCollisionExist)
                            throw new OOPInherentAmbiguity(interfaceClass, superClass, annotatedMethods.get(0));
                    }

                }
                interfaceSet.add(superClass);
                validateForCommonParent(superClass, interfaceSet,classAnnotation,methodAnnotation);
            }
        }
    }


    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {


        Set<Method> methodSet= new HashSet<>();
        validateCoincidentalAmbiguity(interfaceClass,methodName,args);
        //TODO: no collisions were found so we need to find the best match. what is a best match?


        return null;
    }

    private void validateCoincidentalAmbiguity(Class<?> interfaceClass
            ,String methodName,Object... args) throws OOPCoincidentalAmbiguity {
        Class<?>[] superClasses=interfaceClass.getInterfaces();
        for(Class<?> superClass : superClasses){
            final List<Method> superClassMethods = new ArrayList<>(Arrays.asList(superClass.getMethods()));
            Stream<Method>  filteredByName= filterByMethodName(methodName,superClassMethods);
            final List<Method> filteredByNameAndArguments=filterByArguments(filteredByName,args);
            final Set<Method> collisions=getCollidedMethods(filteredByNameAndArguments);

               if(collisions.size()>0) {
                      Collection<Pair<Class<?>,Method>> pairs=new ArrayList<>();
                      //warp it as pairs
                      collisions.stream().forEach(c-> pairs.add(new Pair<>(superClass,c)));
                      throw new OOPCoincidentalAmbiguity(pairs);
               }
        }

    }

    private Set<Method> getCollidedMethods(List<Method> methodList) {
        /**
         * the class was made to make a set of only
         */
        class MethodComparator{
            Method method;

            public MethodComparator(Method method) {
                this.method = method;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                MethodComparator that = (MethodComparator) o;

                return method != null ? methodsHaveSameArguments(method,that.method) : that.method == null;
            }

            @Override
            public int hashCode() {
                return method != null ? method.hashCode() : 0;
            }
        }

        //we create special set to compare between methods by their arguments (a sub Set of methodList)
        Set<MethodComparator> uniqeMethods=new HashSet<>();
        methodList.forEach( method ->uniqeMethods.add(new MethodComparator(method)) );
        //unwrap it to regular Set
        Set<Method> regularUniqueMethods =uniqeMethods.stream().map(mc -> mc.method).collect(Collectors.toSet());
        Set<Method> allMethods=new HashSet<>();
        //convert methodList to Set
        methodList.stream().forEach(m->allMethods.add(m));

        //now we can subtract with the regular equal method which is not by arguments but by class
        allMethods.removeAll(regularUniqueMethods);
        return allMethods;
    }

    private Stream<Method> filterByMethodName(String methodName, List<Method> superClassMethods) {
        return superClassMethods.stream().filter(superClassMethod -> superClassMethod.getName().equals(methodName));
    }

    private List<Method> filterByArguments( Stream<Method> filteredByName, Object[] args) {
        return filteredByName.filter(m -> checkForArgsEquality(m,args) ).collect(Collectors.toList());
    }

    private boolean checkForArgsEquality(Method m, Object[] args) {
        Type[] types=m.getParameterTypes();
        if(args.length!=types.length)
            return false;
        for(int i=0;i<types.length;i++){
            Class<?> type=types[i].getClass();
            Object object=args[i];
            if(!type.isInstance(object))
                return false;
        }
        return true;
    }


    /**
     * the method checks if two Methods have the same arguments.
     * since java is no-variance they must have the same size, and the same types.
     * @param methodOne
     * @param methodTwo
     * @return
     */
    private static boolean methodsHaveSameArguments(Method methodOne, Method methodTwo) {
        Type[] methodOneTypes=methodOne.getGenericParameterTypes();
        Type[] methodTwoTypes=methodTwo.getParameterTypes();
        if(methodOneTypes.length!=methodTwoTypes.length){
            return false;
        }else{
            for(int i=0;i<methodOneTypes.length;i++){
                if(!methodOneTypes[i].getTypeName().equals(methodTwoTypes[i].getTypeName()))
                    return false;
            }

        }
        return true;
    }

    //TODO: add more of your code :


    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
