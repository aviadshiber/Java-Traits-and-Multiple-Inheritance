package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_MISSING_IMPL;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T3A {

    @OOPTraitMethod // Abstract method :)
    String goodbye() throws OOPTraitException;

    @OOPTraitMethod(modifier = INTER_MISSING_IMPL) // Hint hint!, missing is not an implementation :)
    default String hello() throws OOPTraitException {
        return "Hello :)";
    }
}
