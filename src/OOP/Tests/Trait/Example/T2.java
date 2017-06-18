package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;
import static OOP.Tests.Trait.Example.TestTrait.obj;

@OOPTraitBehaviour
public interface T2 {

    @OOPTraitMethod     //no implementation here
    int getValue() throws OOPTraitException;

    @OOPTraitMethod     //no implementation here
    void setValue(Integer v) throws OOPTraitException;

    @OOPTraitMethod(modifier = INTER_IMPL)
    default void add(Integer v) throws OOPTraitException {
        obj.setValue(obj.getValue() + v);
    }
}
