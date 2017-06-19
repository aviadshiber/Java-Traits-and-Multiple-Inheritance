package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_MISSING_IMPL;

public interface T1C extends T1A, T1B {

    void add(Integer v) throws OOPTraitException;

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_CONFLICT)
    @OOPTraitConflictResolver(resolve = T1A.class)
    void conf() throws OOPTraitException;   //resolves conflict for method "conf" in the layout
}
