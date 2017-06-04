package OOP.Solution.Trait;

import OOP.Provided.Trait.OOPBadClass;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Provided.Trait.OOPTraitMissingImpl;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static OOP.Solution.ReflectionUtils.ReflectionHelper.getAllOurMethods;
import static OOP.Solution.ReflectionUtils.ReflectionHelper.mapMethodToClass;


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
        HashMap<Method,Class<?>> classMap=mapMethodToClass(traitCollector.getInterfaces());
        List<Method> notAnnotatedMethods = allMethods.stream().filter(M -> ! (M.isAnnotationPresent(OOPTraitMethod.class))).collect(Collectors.toList());
        if(notAnnotatedMethods.size() > 0)
            throw new OOPBadClass(notAnnotatedMethods.get(0));
        List<Class<?>> notAnnotatedClass = allMethods.stream().map(M -> classMap.get(M)).filter(C -> C.isAnnotationPresent(OOPTraitBehaviour.class)).collect(Collectors.toList());
        if(notAnnotatedClass.size() > 0)
            throw new OOPBadClass(notAnnotatedClass.get(0));
        List<Method> implemented = allMethods.stream().filter(M -> isAnnotatedBy(M, OOPTraitMethod.class, OOPTraitMethodModifier.INTER_IMPL)).collect(Collectors.toList());
        for (Method M : allMethods) {
            if (!(implemented.stream().anyMatch(M2 -> M2.getName().equals(M.getName())))) {
                throw new OOPTraitMissingImpl(M);
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
