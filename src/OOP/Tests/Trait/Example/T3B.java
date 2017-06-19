package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T3B {

    @OOPTraitMethod(modifier = INTER_IMPL)
    default String goodbye() throws OOPTraitException{
        return "Goodbye :)";
    }

    @OOPTraitMethod     //no implementation here
    String hello() throws OOPTraitException ;

}
