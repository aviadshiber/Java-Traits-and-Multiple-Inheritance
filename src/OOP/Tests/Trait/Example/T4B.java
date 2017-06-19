package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_IMPL;

/**
 * Created by danie_000 on 6/12/2017.
 */
@OOPTraitBehaviour
public interface T4B extends T4A {
    @OOPTraitMethod(modifier = INTER_IMPL)
    default String startDrivingFerrari() throws OOPTraitException{
        return "Cool car :)";
    }
    @OOPTraitMethod
        // Abstract method :)
    String parkFerrari() throws OOPTraitException;

}
