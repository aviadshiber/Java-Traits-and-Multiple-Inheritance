package OOP.Tests.Trait.Example;


import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitMethod;
import OOP.Solution.Trait.OOPTraitMethodModifier;

/**
 * Created by danie_000 on 6/16/2017.
 */
public class C5D implements T5D {
    int floor;
    public C5D(){
        floor=0;
    }

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public void advance(Integer i) throws OOPTraitException {
        floor+=i;

    }

    @OOPTraitMethod(modifier = OOPTraitMethodModifier.INTER_IMPL)
    public String getFloor() throws  OOPTraitException {
        return "Current floor is:"+floor;
    }


}
