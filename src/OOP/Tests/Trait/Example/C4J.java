package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_MISSING_IMPL;

/**
 * Created by danie_000 on 6/16/2017.
 */
public class C4J implements T4J {

    @OOPTraitMethod(modifier = INTER_MISSING_IMPL)
    public String startDrivingFerrari() throws OOPTraitException {
        return null;
    }

    @OOPTraitMethod(modifier = INTER_MISSING_IMPL)
    public String parkFerrari() throws OOPTraitException {
        return null;
    }
}
