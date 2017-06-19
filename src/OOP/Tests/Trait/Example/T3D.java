package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

import static OOP.Tests.Trait.Example.TestTrait.obj3E;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T3D {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default String startTalking() throws OOPTraitException{
        return obj3E.hello();
    }
    @OOPTraitMethod // Abstract :)
    String finishTalking() throws OOPTraitException;
}
