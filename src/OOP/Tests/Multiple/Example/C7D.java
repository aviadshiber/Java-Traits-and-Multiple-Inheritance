package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/8/2017.
 */
public class C7D implements I7D {
    public String f(Object p1,Object p2,Object p3) throws OOPMultipleException {return "007:C7D :)";}
    public String f(Integer p1,Object p2,E p3) throws OOPMultipleException {return "007:C8D";} // Shouldn't be used :)

}
