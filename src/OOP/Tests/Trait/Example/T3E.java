package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/12/2017.
 */
public interface T3E extends T3C,T3D {

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    String startTalking() throws OOPTraitException;

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_MISSING_IMPL)
    String finishTalking() throws OOPTraitException;

}
