package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/7/2017.
 */
public class C5A implements I5A {
    public void f(A p1,B p2,A p3,A p4) throws OOPMultipleException{
        p4.advance1();
    }
    public String g(A p1,B p2,A p3) throws OOPMultipleException{
        return "I5A";
    }
}
