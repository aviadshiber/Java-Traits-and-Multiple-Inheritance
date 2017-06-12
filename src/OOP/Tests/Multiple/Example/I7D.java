package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Solution.Multiple.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/8/2017.
 */
@OOPMultipleInterface
public interface I7D {
    @OOPMultipleMethod
    default String f(Object p1,Object p2,Object p3) throws OOPMultipleException {return "";}
    @OOPMultipleMethod
    default String f(Integer p1,Object p2,E p3) throws OOPMultipleException {return "";}
}
