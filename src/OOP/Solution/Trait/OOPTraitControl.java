package OOP.Solution.Trait;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Provided.Trait.OOPTraitMissingImpl;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


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
        List<Method> allMethods = Arrays.stream(traitCollector.getMethods()).filter(m -> m.isAnnotationPresent(OOPTraitMethod.class)).collect(Collectors.toList());
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
