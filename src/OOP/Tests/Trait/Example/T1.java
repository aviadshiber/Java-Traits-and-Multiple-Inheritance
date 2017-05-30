package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;
import static OOP.Tests.Trait.Example.Example.obj;

@OOPTraitBehaviour
public interface T1 {

    @OOPTraitMethod     //no implementation here
    void add(Integer v) throws OOPTraitException;

    @OOPTraitMethod(modifier = INTER_IMPL)
    default void inc() throws OOPTraitException {
        obj.add(1);
    }

}
