package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;

import static OOP.Solution.Trait.OOPTraitMethodModifier.INTER_CONFLICT;

/**
 * Created by danie_000 on 6/16/2017.
 */
@OOPTraitBehaviour
public interface T4K extends T4J{


    @OOPTraitMethod(modifier = INTER_CONFLICT) //
    @OOPTraitConflictResolver(resolve = T4I.class)
    public String buyFerrari() throws OOPTraitException ;

}
