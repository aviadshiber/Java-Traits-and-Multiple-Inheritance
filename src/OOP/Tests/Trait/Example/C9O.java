package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
public class C9O implements T9O {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public String smile(C p1, D p2, C p3) throws OOPTraitException {
        return "T9O :)";
    }
}
