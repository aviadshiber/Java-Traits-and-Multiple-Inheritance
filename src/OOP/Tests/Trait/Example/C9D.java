package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
public class C9D implements T9D {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public void f() throws OOPTraitException{}
}
