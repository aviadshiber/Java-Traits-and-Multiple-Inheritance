package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

import static OOP.Tests.Trait.Example.TestTrait.obj3J;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T3H extends T3F,T3G {

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    String goodbye() throws OOPTraitException;

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    String hello() throws OOPTraitException;

    @OOPTraitMethod // Abstract :)
    String startTalking() throws OOPTraitException;
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL) // Hint hint, we can't finish a
                                                                          // conversation :)
    default String finishTalking() throws OOPTraitException{
        return obj3J.finishTalking();
    }
}
