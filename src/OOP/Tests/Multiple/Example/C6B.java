package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/8/2017.
 */
public class C6B implements I6B {
    public void f_override() throws OOPMultipleException{}
    public String g() throws OOPMultipleException{return "I6B";}
    public String h(Integer i) throws OOPMultipleException{return "Only integer :)";}
    public String h(Integer i1,Integer i2) throws OOPMultipleException{return "Only two integers :)";}
    public void k(String s1) throws OOPMultipleException{}
}
