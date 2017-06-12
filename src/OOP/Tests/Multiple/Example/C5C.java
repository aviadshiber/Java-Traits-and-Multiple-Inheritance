package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/7/2017.
 */
public class C5C implements I5C {
    public void f(C p1,B p2,C p3,A p4) throws OOPMultipleException{
        p4.advance3();
    }
    public String g(C p1,B p2,C p3) throws OOPMultipleException{
        return "I5C";
    }
}
