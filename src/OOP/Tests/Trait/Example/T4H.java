package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_CONFLICT;
import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_MISSING_IMPL;

/**
 * Created by danie_000 on 6/12/2017.
 */
public interface T4H extends T4F,T4G {

    @OOPTraitMethod(modifier = INTER_CONFLICT) //
    @OOPTraitConflictResolver(resolve = T4F.class)
    default String startDrivingFerrari() throws OOPTraitException{
        return "Forgot to fuel the ferrari :(";
    }
    @OOPTraitMethod(modifier = INTER_MISSING_IMPL)
        // Abstract method :)
    String parkFerrari() throws OOPTraitException;
}
