package OOP.Solution.Multiple;

import OOP.Provided.Multiple.OOPInherentAmbiguity;
import OOP.Provided.Multiple.OOPMultipleException;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

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
                    final List<Method> annotatedMethods=allMethods.stream().filter(method-> method.isAnnotationPresent(methodAnnotation)).collect(Collectors.toList());
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
