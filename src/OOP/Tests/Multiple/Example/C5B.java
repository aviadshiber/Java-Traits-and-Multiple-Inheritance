package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/7/2017.
 */
public class C5B implements I5B {
    public void f(B p1,A p2,A p3,A p4) throws OOPMultipleException{
        p4.advance2();
    }
    public String g(B p1,A p2,A p3) throws OOPMultipleException{
        return "I5B";
    }

}
