package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitException;

/**
 * Created by danie_000 on 6/12/2017.
 */
public class C2O implements T2O {
    // Untagged method alert, I repeat: UNTAGGED METHOD ALERT :(
    public String hello() throws OOPTraitException{
        return "ERROR, We need oxygen :)";
    }
}
