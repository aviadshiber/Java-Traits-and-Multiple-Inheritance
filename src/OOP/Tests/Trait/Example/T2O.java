package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T2O {

    @OOPTraitMethod(modifier = INTER_IMPL)
    default String goodbye() throws OOPTraitException{
        return "Goodbye :)";
    }

    default String hello() throws OOPTraitException {
        return "Error , should be tagged :(";
    }

}
