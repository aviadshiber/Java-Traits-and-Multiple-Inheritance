package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

public class C2 implements T2 {     //like the example from the lecture.. in part.
    private int n;

    @Override
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public void setValue(Integer v) throws OOPTraitException {
        n = v;
    }

    @Override
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)   //actual implementation - should run!
    public int getValue() throws OOPTraitException {
        return n;
    }
}
