package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;

@OOPMultipleInterface
public interface I1D extends I1A, I1B {

    String h() throws OOPMultipleException;

}
