package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Solution.Multiple.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/8/2017.
 */
@OOPMultipleInterface
public interface I7C {
    @OOPMultipleMethod
    default String f(A p1,A p2,D p3) throws OOPMultipleException {return "";}
}
