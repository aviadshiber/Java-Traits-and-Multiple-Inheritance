package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;

/**
 * Created by danie_000 on 6/16/2017.
 */
@OOPTraitBehaviour
public interface T4I {
    @OOPTraitMethod
        // Abstract method :)
    String startDrivingFerrari() throws OOPTraitException;

    @OOPTraitMethod
    // Abstract method :)

     String parkFerrari() throws OOPTraitException;

    @OOPTraitMethod(modifier = INTER_IMPL)
    default String buyFerrari() throws OOPTraitException {
        return "Buying Ferrari 488 , only $334,409... not so expensive :D";
    }
}
