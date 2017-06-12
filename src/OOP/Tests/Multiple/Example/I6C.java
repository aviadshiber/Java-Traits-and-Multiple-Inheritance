package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Solution.Multiple.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/8/2017.
 */
@OOPMultipleInterface
public interface I6C extends I6A,I6B{
    @OOPMultipleMethod
    default void f_override() throws OOPMultipleException{}

    @OOPMultipleMethod
    default String g() throws OOPMultipleException{return "";}
    @OOPMultipleMethod
    default String h(Double d) throws OOPMultipleException{return "";}
    @OOPMultipleMethod
    default String h(Double d1,Double d2) throws OOPMultipleException{return "";}
    @OOPMultipleMethod
    default void k(String s1,String s2) throws OOPMultipleException{}


}
