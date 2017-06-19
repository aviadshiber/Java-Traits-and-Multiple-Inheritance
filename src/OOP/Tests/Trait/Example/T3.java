package OOP.Tests.Trait.Example;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;
import static OOP.Tests.Trait.Example.Example.obj;
/**
 * Created by Rani on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T3 {
    @OOPTraitMethod     //no implementation here
    int getValue() throws OOPTraitException;
    @OOPTraitMethod(modifier = INTER_IMPL)
    default void ambuigTest(String s,String s2)throws OOPTraitException {

    }
}
