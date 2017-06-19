package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_MISSING_IMPL;

/**
 * Created by danie_000 on 6/12/2017.
 */
public interface T4D extends T4B,T4C {

    @OOPTraitMethod(modifier = INTER_MISSING_IMPL)
    default String startDrivingFerrari() throws OOPTraitException{
        return "Forgot to fuel the ferrari :(";
    }
    @OOPTraitMethod(modifier = INTER_MISSING_IMPL)
        // Abstract method :)
    String parkFerrari() throws OOPTraitException;
}
