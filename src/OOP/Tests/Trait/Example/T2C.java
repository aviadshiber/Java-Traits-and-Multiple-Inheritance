package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_MISSING_IMPL;

public interface T2C extends T2A, T2B {

    @OOPTraitMethod(modifier = INTER_MISSING_IMPL)   //resolves two decelerations in T1 and T2 - should not run!
    void add(Integer v) throws OOPTraitException;

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_CONFLICT)
    @OOPTraitConflictResolver(resolve = T1.class)
    void conf() throws OOPTraitException;   //resolves conflict for method "conf" in the layout
}
