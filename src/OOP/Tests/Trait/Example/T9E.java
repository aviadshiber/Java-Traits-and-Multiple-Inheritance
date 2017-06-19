package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
public interface T9E extends T9A,T9D {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    public void f() throws OOPTraitException ;
}
