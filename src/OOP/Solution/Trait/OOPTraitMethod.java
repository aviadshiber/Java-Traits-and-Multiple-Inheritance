package OOP.Solution.Trait;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static OOP.Solution.Trait.OOPTraitMethodModifier.*;

/**
 * Created by Rani on 6/4/2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OOPTraitMethod {
    OOPTraitMethodModifier modifier() default INTER_ABS;
}

