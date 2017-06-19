package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/17/2017.
 */
public class C6B implements T6B {

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public String f(A p1, D p2, A p3) throws OOPTraitException {
        return "C6B";
    }
}
