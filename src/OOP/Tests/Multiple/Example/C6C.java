package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleException;

/**
 * Created by danie_000 on 6/8/2017.
 */
public class C6C implements I6C {
    public void f_override() throws OOPMultipleException{}
    public String g() throws OOPMultipleException{return "I6C";}
    public String h(Double d) throws OOPMultipleException{return "Only double :)";}
    public String h(Double d1,Double d2) throws OOPMultipleException{return "Only two doubles :)";}
    public void k(String s1,String s2) throws OOPMultipleException{}
}
