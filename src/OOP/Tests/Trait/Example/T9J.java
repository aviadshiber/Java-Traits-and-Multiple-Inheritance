package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
public interface T9J extends T9F,T9G {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_CONFLICT) // Not suitable resolver :( :( :(
    @OOPTraitConflictResolver(resolve = T9F.class)
    public void smile(B p1,A p2) throws OOPTraitException; // This resolve is half upcast of smile(A,B) and smile(B,A)
    // What we need is a downcast of smile(B,B)
    // So smile(B,C),smile(D,B),smile(E,E) would work too
    // of course, smile(B,B) works too (CHECK FAQ :) )

}
