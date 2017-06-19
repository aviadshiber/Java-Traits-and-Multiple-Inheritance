package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;


/**
 * Created by danie_000 on 6/16/2017.
 */
public interface T4L extends T4J{


    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_CONFLICT)
    @OOPTraitConflictResolver(resolve = T4J.class)
    public String buyFerrari() throws OOPTraitException ;

}
