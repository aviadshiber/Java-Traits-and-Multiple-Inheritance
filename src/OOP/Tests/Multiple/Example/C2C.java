package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/6/2017.
 */

public class C2C implements I2C {

    @Override
    public Integer f() throws OOPMultipleException {
        Integer i = new Integer(3);
        return i;
    }
}
