package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
@OOPTraitBehaviour
public interface T8M {
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public void f(A p1,Object p2) throws OOPTraitException; // up-cast METHOD compared to (B p1,B p2),
                                                            // Therefore, valid implementation :)
    @OOPTraitMethod
    public String g() throws OOPTraitException;

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default String h() throws OOPTraitException{
        return "Works as impl :)";
    }
}
