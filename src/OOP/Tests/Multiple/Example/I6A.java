package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Solution.Multiple.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/8/2017.
 */
@OOPMultipleInterface
public interface I6A {
    @OOPMultipleMethod
    default void f_override() throws OOPMultipleException{}

    @OOPMultipleMethod
    default String g() throws OOPMultipleException{return "";}
    @OOPMultipleMethod
    default String h() throws OOPMultipleException{return "";}
    @OOPMultipleMethod
    default void k() throws OOPMultipleException{}


}
