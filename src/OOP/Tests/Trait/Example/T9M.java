package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
@OOPTraitBehaviour
public interface T9M {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default String smile(A p1, B p2, A p3) throws OOPTraitException {
        return "T9M :)";
    }
}
