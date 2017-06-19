package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
public class C10B implements T10B {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public String throwShuriken() throws OOPTraitException {
        return "T10B :)";
    }
}
