package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
@OOPTraitBehaviour
public interface T8G {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public void f(D p1,D p2) throws OOPTraitException; // Has no effect :)
}
