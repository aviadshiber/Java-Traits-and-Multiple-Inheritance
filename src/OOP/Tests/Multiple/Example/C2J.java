package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/6/2017.
 */

public class C2J implements I2J {

    @Override
    public Integer f() throws OOPMultipleException {
        Integer i = new Integer(10);
        return i;
    }

    @Override
    public void g() throws OOPMultipleException {
    }
}
