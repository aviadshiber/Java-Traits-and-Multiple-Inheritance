package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T4A {
    @OOPTraitMethod
        // Abstract method :)
    String startDrivingFerrari() throws OOPTraitException;

    @OOPTraitMethod(modifier = INTER_IMPL)
    default String parkFerrari() throws OOPTraitException{
        return "Parking my ferrari :)";
    }

    @OOPTraitMethod(modifier = INTER_IMPL)
    default String buyFerrari() throws OOPTraitException {
        return "Buying Ferrari 488 , only $334,409... not so expensive :D";
    }
}
