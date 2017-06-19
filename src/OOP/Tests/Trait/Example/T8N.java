package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/19/2017.
 */
@OOPTraitBehaviour
public interface T8N extends T8M{
    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public void f(E p1,E p2) throws OOPTraitException; // This is no up-cast :( to f(B,B)

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    default String g() throws OOPTraitException{
        return "Works as impl :)";
    }

    @OOPTraitMethod
    public String h() throws OOPTraitException;


}
