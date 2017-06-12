package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/6/2017.
 */

public class C2B implements I2B {

    @Override
    public Integer f() throws OOPMultipleException {
        Integer i = new Integer(2);
        return i;
    }
}
