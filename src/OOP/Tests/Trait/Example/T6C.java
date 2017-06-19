package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/17/2017.
 */
@OOPTraitBehaviour
public interface T6C {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default String f(A p1, D p2, A p3) throws OOPTraitException {
        return "T6C";
    }

    @OOPTraitMethod
    default String f(D p1, A p2, A p3) throws OOPTraitException {
        return "Abstract method :)";
    }

    @OOPTraitMethod
    default String f(Object p1, Object p2, Object p3) throws OOPTraitException {
        return "Abstract method :)";
    }
}
