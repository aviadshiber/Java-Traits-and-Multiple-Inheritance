package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/16/2017.
 */
public interface T5E extends T5A,T5B,T5C,T5D {

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    default void advance(Integer i) throws OOPTraitException {

    }

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    default void doubleMoveUp() throws OOPTraitException {

    }

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_CONFLICT)
    @OOPTraitConflictResolver(resolve = T5B.class)
    default void moveUp() throws OOPTraitException {

    }

}
