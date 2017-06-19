package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;

/**
 * Created by danie_000 on 6/16/2017.
 */
@OOPTraitBehaviour
public interface T4J extends T4I{
    @OOPTraitMethod(modifier = INTER_IMPL)
    default String startDrivingFerrari() throws OOPTraitException{
        return "Driving my ferrari :)";
    }

    @OOPTraitMethod(modifier = INTER_IMPL)
    default String parkFerrari() throws OOPTraitException{
        return "Parking my ferrari :)";
    }

    @OOPTraitMethod(modifier = INTER_IMPL)
    default String buyFerrari() throws OOPTraitException {
        return "Buying Ferrari 488 , only $666,666... not so expensive :D";
    }
}
