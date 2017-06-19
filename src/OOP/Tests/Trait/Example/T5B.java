package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;
import static OOP.Tests.Trait.Example.TestTrait.obj5E;

/**
 * Created by danie_000 on 6/16/2017.
 */
@OOPTraitBehaviour
public interface T5B {
    @OOPTraitMethod
    default void advance(Integer i) throws  OOPTraitException{}

    @OOPTraitMethod
    default void doubleMoveUp() throws OOPTraitException{}

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default void moveUp() throws OOPTraitException{
        obj5E.advance(new Integer(2));
    }


}
