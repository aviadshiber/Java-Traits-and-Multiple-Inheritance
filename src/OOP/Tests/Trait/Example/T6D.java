package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/17/2017.
 */
public interface T6D extends T6A,T6B,T6C {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_CONFLICT)
    @OOPTraitConflictResolver(resolve = C6B.class)
    default String f(A p1, D p2, A p3) throws OOPTraitException {
        return "Abstract method :)";
    }
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_CONFLICT)
    @OOPTraitConflictResolver(resolve = T6A.class)
    default String f(D p1, A p2, A p3) throws OOPTraitException{
        return "Abstract method :)";
    }

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    default String f(Object p1, Object p2, Object p3) throws OOPTraitException{
        return "Abstract method :)";
    }

}
