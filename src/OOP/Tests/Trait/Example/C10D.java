package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
public class C10D implements T10D {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public String winUltimate(A p1,Object p2) throws OOPTraitException {
        return "T10D :)";
    }
}
