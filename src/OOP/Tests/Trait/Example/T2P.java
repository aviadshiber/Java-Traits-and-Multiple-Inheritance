package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

import static OOP.Tests.Trait.Example.TestTrait.obj2R;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T2P extends T2N,T2O {

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    String goodbye() throws OOPTraitException;

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    String hello() throws OOPTraitException;

    @OOPTraitMethod // Abstract :)
    String startTalking() throws OOPTraitException;
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default String finishTalking() throws OOPTraitException{
        return obj2R.finishTalking();
    }
}
