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
public interface T5D {
    @OOPTraitMethod
    void advance(Integer i) throws  OOPTraitException;

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default void doubleMoveUp() throws OOPTraitException{
        obj5E.moveUp();
        obj5E.moveUp();
    }


    @OOPTraitMethod
    default String getFloor() throws  OOPTraitException {
    return "This is not the way to get the current floor :)";
    }


        @OOPTraitMethod
    default void moveUp() throws OOPTraitException{}


}
