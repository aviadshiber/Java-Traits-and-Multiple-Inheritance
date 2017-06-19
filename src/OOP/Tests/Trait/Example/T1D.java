package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_MISSING_IMPL;

public interface T1D extends T1A, T1B {

    @OOPTraitMethod(modifier = INTER_MISSING_IMPL)   //resolves two decelerations in T1 and T2 - should not run!
    void add(Integer v) throws OOPTraitException;

    void conf() throws OOPTraitException;
}
