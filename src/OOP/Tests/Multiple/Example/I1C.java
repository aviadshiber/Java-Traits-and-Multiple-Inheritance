package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleMethod;

public interface I1C extends I1A, I1B {


    @OOPMultipleMethod
    String h() throws OOPMultipleException;

}
