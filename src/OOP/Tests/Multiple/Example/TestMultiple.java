package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPBadClass;
import OOP.Provided.Multiple.OOPMultipleClassGenerator;
import OOP.Provided.Multiple.OOPMultipleException;
import OOP.Solution.Multiple.OOPMultipleInterface;
import OOP.Solution.Multiple.OOPMultipleMethod;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


public class TestMultiple {

    private OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();
    private Class[] types = new Class[]{String.class, Object[].class};

    //********************************************LEVEL 1 :)**************************************************************//


    private boolean testOOPMultipleInterfaceAnnotations(){
        Assert.assertEquals("@java.lang.annotation.Target(value=[TYPE])",OOPMultipleInterface.class.getAnnotation(Target.class).toString());
        Assert.assertEquals("@java.lang.annotation.Retention(value=RUNTIME)",OOPMultipleInterface.class.getAnnotation(Retention.class).toString());
        return true;
    }

    private boolean testOOPMultipleMethodAnnotations(){
        Assert.assertEquals("@java.lang.annotation.Target(value=[METHOD])",OOPMultipleMethod.class.getAnnotation(Target.class).toString());
        Assert.assertEquals("@java.lang.annotation.Retention(value=RUNTIME)",OOPMultipleMethod.class.getAnnotation(Retention.class).toString());

        return true;
    }

    private boolean testOOPMultipleAnnotations(){
        Assert.assertTrue(testOOPMultipleInterfaceAnnotations());
        Assert.assertTrue(testOOPMultipleMethodAnnotations());

        return true;
    }

    private boolean testOOPBadClassGraphBaseTagged() {
        try {
            I1A obj1A = (I1A) generator.generateMultipleClass(I1A.class);
            generator.removeSourceFile();
            I1B obj1B = (I1B) generator.generateMultipleClass(I1B.class);
            generator.removeSourceFile();
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    private boolean testOOPBadClassGraphBaseUntaggedInterface() {
        try {
            I1C obj1C = (I1C) generator.generateMultipleClass(I1C.class);
        } catch (OOPMultipleException e) {
            return e.getMessage().equals("OOPMultipleException : \nOOP.Tests.Multiple.Example.I1C is Corrupted!");
        } finally {
            generator.removeSourceFile();
        }
        return false;
    }


    private boolean testOOPBadClassGraphBaseUntaggedMethod() {
        try {
            I1D obj1D = (I1D) generator.generateMultipleClass(I1D.class);
        } catch (OOPMultipleException e) {
            return e.getMessage().equals("OOPMultipleException : \nOOP.Tests.Multiple.Example.I1D : h is Corrupted!");
        } finally {
            generator.removeSourceFile();
        }
        return false;
    }

    private boolean testOOPBadClassGraphBaseUntaggedInterfaceAndMethod() {
        try {
            I1E obj1E = (I1E) generator.generateMultipleClass(I1E.class);
        } catch (OOPMultipleException e) {
            return e instanceof OOPBadClass;
        } finally {
            generator.removeSourceFile();
        }
        return false;
    }
//********************************************LEVEL 1 :)**************************************************************//

    //********************************************LEVEL 2 :)**************************************************************//
    private boolean testOOPBadClassGraphTreeTagged() {
        try {
            I2A obj2A = (I2A) generator.generateMultipleClass(I2A.class);
            generator.removeSourceFile();
            I2B obj2B = (I2B) generator.generateMultipleClass(I2B.class);
            generator.removeSourceFile();
            I2C obj2C = (I2C) generator.generateMultipleClass(I2C.class);
            generator.removeSourceFile();
            I2D obj2D = (I2D) generator.generateMultipleClass(I2D.class);
            generator.removeSourceFile();

        } catch (OOPMultipleException e) {
            System.out.println(e.getCause());
            generator.removeSourceFile();
            e.printStackTrace();
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    private boolean testOOPBadClassGraphTreeUntaggedInterface() {
        try {
            I2G obj2G = (I2G) generator.generateMultipleClass(I2G.class);
        } catch (OOPMultipleException e) { //I2E didn't follow the law :( , where did his annotation fade?
            generator.removeSourceFile();
            return e.getMessage().equals("OOPMultipleException : \nOOP.Tests.Multiple.Example.I2E is Corrupted!");
        } finally {
            generator.removeSourceFile();
        }
        return false;
    }


    private boolean testOOPBadClassGraphTreeUntaggedMethod() {
        try {
            I2J obj2J = (I2J) generator.generateMultipleClass(I2J.class);
        } catch (OOPMultipleException e) { //I2H didn't follow the law :( , where did his annotation fade?
            generator.removeSourceFile();
            return e.getMessage().equals("OOPMultipleException : \nOOP.Tests.Multiple.Example.I2H : g is Corrupted!");
        } finally {
            generator.removeSourceFile();
        }
        return false;
    }

    private boolean testOOPBadClassGraphTreeUntaggedInterfaceAndMethod() {
        try {
            I2M obj2M = (I2M) generator.generateMultipleClass(I2M.class);
        } catch (OOPMultipleException e) { //I2L didn't follow the law :( , where did his annotations fade?
            generator.removeSourceFile();
            return e instanceof OOPBadClass &&
                    (e.getMessage().equals("OOPMultipleException : \nOOP.Tests.Multiple.Example.I2L is Corrupted!")
                            || e.getMessage().equals("OOPMultipleException : \nOOP.Tests.Multiple.Example.I2L : g is Corrupted!")

                    ) // An exception shuriken should be of type Interface or Method
                    ;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }
//********************************************LEVEL 2 :)**************************************************************//

//********************************************LEVEL 3 :)**************************************************************//

    private boolean testOOPInherentAmbiguityFakeDiamond() {
        try {
            I3D obj3D = (I3D) generator.generateMultipleClass(I3D.class);
        } catch (OOPMultipleException e) {
            System.out.println(e.getCause());
            generator.removeSourceFile();
            return false; // Inter3A isn't a diamond, it's just an empty base :(
        } finally {
            generator.removeSourceFile();
        }
        // If you got here, it means you have detected Inter3A as not a diamond :)
        return true;
    }

    private boolean testOOPInherentAmbiguityBasicDiamond() {
        try {
            I3J obj3J = (I3J) generator.generateMultipleClass(I3J.class);
        } catch (OOPMultipleException e) {
            generator.removeSourceFile();
            return e.getMessage().equals("OOP.Tests.Multiple.Example.C3J Could not be generated \n" +
                    "because of Inherent Ambiguity, caused by inheriting method: f\n" +
                    "which is first defined in : OOP.Tests.Multiple.Example.C3F"); // I3F is a diamond :)
        } finally {
            generator.removeSourceFile();
        }
        // If you got here, it means you haven't detected Inter3F as diamond :(
        return false;
    }
//********************************************LEVEL 3 :)**************************************************************//

    //********************************************LEVEL 4 :)**************************************************************//
    private boolean testOOPInherentAmbiguityComplexDiamond() {
        try {
            I4N obj4N = (I4N) generator.generateMultipleClass(I4N.class);
        } catch (OOPMultipleException e) {
            generator.removeSourceFile();
            return e.getMessage().equals("OOP.Tests.Multiple.Example.C4N Could not be generated \n" +
                    "because of Inherent Ambiguity, caused by inheriting method: g\n" +
                    "which is first defined in : OOP.Tests.Multiple.Example.C4H"); // I4H is a diamond :)
        } finally {
            generator.removeSourceFile();
        }
        // If you got here, it means you haven't detected Inter3F as diamond :(
        return false;
    }
//********************************************LEVEL 4 :)**************************************************************//

    //********************************************LEVEL 5 :)**************************************************************//
    private boolean testOOPCoincidentalAmbiguityFindSuitable() {
        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            /*
             * I5A (A,B,A)
             * I5B (B,A,A)
             * I5C (C,B,C)
             *
             * A
             * |
             * B
             * |
             * C
             * |
             * D
             * |
             * E
             */


            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new B(), new A()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new B(), new B()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new B(), new C()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new B(), new D()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new B(), new E()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new C(), new A()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new C(), new B()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new C(), new C()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new C(), new D()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new C(), new E()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new D(), new A()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new D(), new B()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new D(), new C()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new D(), new D()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new E(), new E()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new E(), new A()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new E(), new B()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new E(), new C()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new E(), new D()));
            Assert.assertEquals("I5A", (String) obj5D.g(new A(), new E(), new E()));

            Assert.assertEquals("I5B", (String) obj5D.g(new B(), new A(), new A()));
            Assert.assertEquals("I5B", (String) obj5D.g(new B(), new A(), new B()));
            Assert.assertEquals("I5B", (String) obj5D.g(new B(), new A(), new C()));
            Assert.assertEquals("I5B", (String) obj5D.g(new B(), new A(), new D()));
            Assert.assertEquals("I5B", (String) obj5D.g(new B(), new A(), new E()));
            Assert.assertEquals("I5B", (String) obj5D.g(new C(), new A(), new A()));
            Assert.assertEquals("I5B", (String) obj5D.g(new C(), new A(), new B()));
            Assert.assertEquals("I5B", (String) obj5D.g(new C(), new A(), new C()));
            Assert.assertEquals("I5B", (String) obj5D.g(new C(), new A(), new D()));
            Assert.assertEquals("I5B", (String) obj5D.g(new C(), new A(), new E()));
            Assert.assertEquals("I5B", (String) obj5D.g(new D(), new A(), new A()));
            Assert.assertEquals("I5B", (String) obj5D.g(new D(), new A(), new B()));
            Assert.assertEquals("I5B", (String) obj5D.g(new D(), new A(), new C()));
            Assert.assertEquals("I5B", (String) obj5D.g(new D(), new A(), new D()));
            Assert.assertEquals("I5B", (String) obj5D.g(new D(), new A(), new E()));
            Assert.assertEquals("I5B", (String) obj5D.g(new E(), new A(), new A()));
            Assert.assertEquals("I5B", (String) obj5D.g(new E(), new A(), new B()));
            Assert.assertEquals("I5B", (String) obj5D.g(new E(), new A(), new C()));
            Assert.assertEquals("I5B", (String) obj5D.g(new E(), new A(), new D()));
            Assert.assertEquals("I5B", (String) obj5D.g(new E(), new A(), new E()));

            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new B(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new B(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new B(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new C(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new C(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new C(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new D(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new D(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new D(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new E(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new E(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new C(), new E(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new B(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new B(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new B(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new C(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new C(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new C(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new D(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new D(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new D(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new E(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new E(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new D(), new E(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new B(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new B(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new B(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new C(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new C(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new C(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new D(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new D(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new D(), new E()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new E(), new C()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new E(), new D()));
            Assert.assertEquals("I5C", (String) obj5D.g(new E(), new E(), new E()));

            A a = new A();
            obj5D.f(new A(), new B(), new B(), a);
            Assert.assertEquals(1, a.getValue());
            obj5D.f(new A(), new B(), new C(), a);
            Assert.assertEquals(2, a.getValue());
            obj5D.f(new A(), new B(), new D(), a);
            Assert.assertEquals(3, a.getValue());
            obj5D.f(new A(), new B(), new E(), a);
            Assert.assertEquals(4, a.getValue());
            obj5D.f(new A(), new C(), new A(), a);
            Assert.assertEquals(5, a.getValue());
            obj5D.f(new A(), new C(), new B(), a);
            Assert.assertEquals(6, a.getValue());
            obj5D.f(new A(), new C(), new C(), a);
            Assert.assertEquals(7, a.getValue());
            obj5D.f(new A(), new C(), new D(), a);
            Assert.assertEquals(8, a.getValue());
            obj5D.f(new A(), new C(), new E(), a);
            Assert.assertEquals(9, a.getValue());
            obj5D.f(new A(), new D(), new A(), a);
            Assert.assertEquals(10, a.getValue());
            obj5D.f(new A(), new D(), new B(), a);
            Assert.assertEquals(11, a.getValue());
            obj5D.f(new A(), new D(), new C(), a);
            Assert.assertEquals(12, a.getValue());
            obj5D.f(new A(), new D(), new D(), a);
            Assert.assertEquals(13, a.getValue());
            obj5D.f(new A(), new E(), new E(), a);
            Assert.assertEquals(14, a.getValue());
            obj5D.f(new A(), new E(), new A(), a);
            Assert.assertEquals(15, a.getValue());
            obj5D.f(new A(), new E(), new B(), a);
            Assert.assertEquals(16, a.getValue());
            obj5D.f(new A(), new E(), new C(), a);
            Assert.assertEquals(17, a.getValue());
            obj5D.f(new A(), new E(), new D(), a);
            Assert.assertEquals(18, a.getValue());
            obj5D.f(new A(), new E(), new E(), a);
            Assert.assertEquals(19, a.getValue());

            obj5D.f(new B(), new A(), new A(), a);
            Assert.assertEquals(21, a.getValue());
            obj5D.f(new B(), new A(), new B(), a);
            Assert.assertEquals(23, a.getValue());
            obj5D.f(new B(), new A(), new C(), a);
            Assert.assertEquals(25, a.getValue());
            obj5D.f(new B(), new A(), new D(), a);
            Assert.assertEquals(27, a.getValue());
            obj5D.f(new B(), new A(), new E(), a);
            Assert.assertEquals(29, a.getValue());
            obj5D.f(new C(), new A(), new A(), a);
            Assert.assertEquals(31, a.getValue());
            obj5D.f(new C(), new A(), new B(), a);
            Assert.assertEquals(33, a.getValue());
            obj5D.f(new C(), new A(), new C(), a);
            Assert.assertEquals(35, a.getValue());
            obj5D.f(new C(), new A(), new D(), a);
            Assert.assertEquals(37, a.getValue());
            obj5D.f(new C(), new A(), new E(), a);
            Assert.assertEquals(39, a.getValue());
            obj5D.f(new D(), new A(), new A(), a);
            Assert.assertEquals(41, a.getValue());
            obj5D.f(new D(), new A(), new B(), a);
            Assert.assertEquals(43, a.getValue());
            obj5D.f(new D(), new A(), new C(), a);
            Assert.assertEquals(45, a.getValue());
            obj5D.f(new D(), new A(), new D(), a);
            Assert.assertEquals(47, a.getValue());
            obj5D.f(new D(), new A(), new E(), a);
            Assert.assertEquals(49, a.getValue());
            obj5D.f(new E(), new A(), new A(), a);
            Assert.assertEquals(51, a.getValue());
            obj5D.f(new E(), new A(), new B(), a);
            Assert.assertEquals(53, a.getValue());
            obj5D.f(new E(), new A(), new C(), a);
            Assert.assertEquals(55, a.getValue());
            obj5D.f(new E(), new A(), new D(), a);
            Assert.assertEquals(57, a.getValue());
            obj5D.f(new E(), new A(), new E(), a);
            Assert.assertEquals(59, a.getValue());

            obj5D.f(new C(), new B(), new C(), a);
            Assert.assertEquals(62, a.getValue());
            obj5D.f(new C(), new B(), new D(), a);
            Assert.assertEquals(65, a.getValue());
            obj5D.f(new C(), new B(), new E(), a);
            Assert.assertEquals(68, a.getValue());
            obj5D.f(new C(), new C(), new C(), a);
            Assert.assertEquals(71, a.getValue());
            obj5D.f(new C(), new C(), new D(), a);
            Assert.assertEquals(74, a.getValue());
            obj5D.f(new C(), new C(), new E(), a);
            Assert.assertEquals(77, a.getValue());
            obj5D.f(new C(), new D(), new C(), a);
            Assert.assertEquals(80, a.getValue());
            obj5D.f(new C(), new D(), new D(), a);
            Assert.assertEquals(83, a.getValue());
            obj5D.f(new C(), new D(), new E(), a);
            Assert.assertEquals(86, a.getValue());
            obj5D.f(new C(), new E(), new C(), a);
            Assert.assertEquals(89, a.getValue());
            obj5D.f(new C(), new E(), new D(), a);
            Assert.assertEquals(92, a.getValue());
            obj5D.f(new C(), new E(), new E(), a);
            Assert.assertEquals(95, a.getValue());
            obj5D.f(new D(), new B(), new C(), a);
            Assert.assertEquals(98, a.getValue());
            obj5D.f(new D(), new B(), new D(), a);
            Assert.assertEquals(101, a.getValue());
            obj5D.f(new D(), new B(), new E(), a);
            Assert.assertEquals(104, a.getValue());
            obj5D.f(new D(), new C(), new C(), a);
            Assert.assertEquals(107, a.getValue());
            obj5D.f(new D(), new C(), new D(), a);
            Assert.assertEquals(110, a.getValue());
            obj5D.f(new D(), new C(), new E(), a);
            Assert.assertEquals(113, a.getValue());
            obj5D.f(new D(), new D(), new C(), a);
            Assert.assertEquals(116, a.getValue());
            obj5D.f(new D(), new D(), new D(), a);
            Assert.assertEquals(119, a.getValue());
            obj5D.f(new D(), new D(), new E(), a);
            Assert.assertEquals(122, a.getValue());
            obj5D.f(new D(), new E(), new C(), a);
            Assert.assertEquals(125, a.getValue());
            obj5D.f(new D(), new E(), new D(), a);
            Assert.assertEquals(128, a.getValue());
            obj5D.f(new D(), new E(), new E(), a);
            Assert.assertEquals(131, a.getValue());
            obj5D.f(new E(), new B(), new C(), a);
            Assert.assertEquals(134, a.getValue());
            obj5D.f(new E(), new B(), new D(), a);
            Assert.assertEquals(137, a.getValue());
            obj5D.f(new E(), new B(), new E(), a);
            Assert.assertEquals(140, a.getValue());
            obj5D.f(new E(), new C(), new C(), a);
            Assert.assertEquals(143, a.getValue());
            obj5D.f(new E(), new C(), new D(), a);
            Assert.assertEquals(146, a.getValue());
            obj5D.f(new E(), new C(), new E(), a);
            Assert.assertEquals(149, a.getValue());
            obj5D.f(new E(), new D(), new C(), a);
            Assert.assertEquals(152, a.getValue());
            obj5D.f(new E(), new D(), new D(), a);
            Assert.assertEquals(155, a.getValue());
            obj5D.f(new E(), new D(), new E(), a);
            Assert.assertEquals(158, a.getValue());
            obj5D.f(new E(), new E(), new C(), a);
            Assert.assertEquals(161, a.getValue());
            obj5D.f(new E(), new E(), new D(), a);
            Assert.assertEquals(164, a.getValue());
            obj5D.f(new E(), new E(), new E(), a);
            Assert.assertEquals(167, a.getValue());

            /*
             * Test invokeTest on method's return type, in this case, of return type void :) (null)
             */
            Assert.assertEquals(null, obj5D.getClass().getMethod("invokeTest", types).
                    invoke(obj5D, "f", new Object[]{new E(), new E(), new E(), a}));
            /*
             * Test invokeTest on method's return type, in this case, of return type void :) (null)
             */


        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            generator.removeSourceFile();
            return false;
            // Should not get here :(
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    private boolean testOOPCoincidentalAmbiguityCheckCandidates() {
        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            /*
             * I5A (A,B,A)
             * I5B (B,A,A)
             * I5C (C,B,C)
             *
             * A
             * |
             * B
             * |
             * C
             * |
             * D
             * |
             * E
             */

            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new B(), new A()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new B(), new B()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new B(), new C()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new B(), new D()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new B(), new E()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new C(), new A()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new C(), new B()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new C(), new C()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new C(), new D()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new C(), new E()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new D(), new A()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new D(), new B()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new D(), new C()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new D(), new E()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new E(), new A()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new E(), new B()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new E(), new C()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new E(), new D()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new B(), new E(), new E()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new C(), new B(), new A()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        }

        try {
            I5D obj5D = (I5D) generator.generateMultipleClass(I5D.class);
            obj5D.getClass().getMethod("invokeTest", types).invoke(obj5D, "g", new Object[]{new C(), new B(), new B()});
            return false; // Should not get here :( Coincidental Ambiguity case :)
        } catch (Exception e) {
            generator.removeSourceFile();
            Assert.assertEquals(true,
                    e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I5B : g\n" +
                            "OOP.Tests.Multiple.Example.I5A : g\n")
                            ||
                            e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I5A : g\n" +
                                    "OOP.Tests.Multiple.Example.I5B : g\n"));
        } finally {
            generator.removeSourceFile();
        }

        return true;
    }
//********************************************LEVEL 5 :)**************************************************************//

//********************************************LEVEL 6 :)**************************************************************//

    boolean testOOPCoincidentalAmbiguityZeroArugments() {
        try {
            I6D obj6D = (I6D) generator.generateMultipleClass(I6D.class);
            Assert.assertEquals(null,obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "f_override", null)); //Return type=void
            generator.removeSourceFile();
        } catch (Exception e) {
            System.out.println(e.getCause());
            generator.removeSourceFile();
            return false;
//            Assert.assertTrue(
//                    (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                            "OOP.Tests.Multiple.Example.I6A : f\n" +
//                            "OOP.Tests.Multiple.Example.I6B : f\n" +
//                            "OOP.Tests.Multiple.Example.I6C : f\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6A : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6C : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6B : f\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6B : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6C : f\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6B : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6C : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : f\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6C : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6B : f\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6C : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6B : f\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : f\n"))
//            );
//
        }
        try {
            I6D obj6D = (I6D) generator.generateMultipleClass(I6D.class);
            Assert.assertEquals("I6C",obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "g", null)); // Return type=String
            generator.removeSourceFile();
        } catch (Exception e) {
            generator.removeSourceFile();
            return false;
//            Assert.assertTrue(
//                    (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                            "OOP.Tests.Multiple.Example.I6A : g\n" +
//                            "OOP.Tests.Multiple.Example.I6B : g\n" +
//                            "OOP.Tests.Multiple.Example.I6C : g\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6A : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6C : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6B : g\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6B : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6C : g\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6B : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6C : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : g\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6C : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6B : g\n"))
//                            ||
//                            (e.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
//                                    "OOP.Tests.Multiple.Example.I6C : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6B : g\n" +
//                                    "OOP.Tests.Multiple.Example.I6A : g\n"))
//            );
        }

        try {
            I6D obj6D = (I6D) generator.generateMultipleClass(I6D.class);
            // Return type=String
            Assert.assertEquals("Summoned h :)", obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "h", null));
            // Return type=String

            // Return type=void (null)
            Assert.assertEquals(null, obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "k", null));
            // Return type=void (null)
        } catch (Exception e) {
            generator.removeSourceFile();
            System.out.println(e.getCause());
            return false; // Should not get here :( One h() and One k() indeed exist!
        }
        finally {
            generator.removeSourceFile();
        }
        return true;
    }

//********************************************LEVEL 6 :)**************************************************************//

//********************************************LEVEL 7 :)**************************************************************//

    boolean testOOPCoincidentalAmbiguityMassiveExtensionAndGenericShuriken() {
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        E e = new E();
        Object o=new Object();
        String s = "seven";
        Integer i = new Integer(7);

        // Only one suitable per invoke :)
        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{s, s, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{s, i, a}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, i, a}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, b, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{s, b, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, b, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{i, b, c}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, e, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, s, d}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, e, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, i, a}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, d, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, i, i}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{i, i, i}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{i, d, c}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{s, s, d}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{s, o, s}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{o, b, c}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, o, c}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, o, c}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, o, d}));
            Assert.assertEquals("007:C7D :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{o, o, o}));

            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, a, a}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, a, b}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, a, c}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, b, a}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, b, b}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, b, c}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, c, a}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, c, b}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, c, c}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, a, a}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, a, b}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, a, c}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, b, a}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, b, b}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, b, c}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, c, a}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, c, b}));
            Assert.assertEquals("007:C7A :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, c, c}));

            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, d, a}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, d, b}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, d, c}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, d, a}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, d, b}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, d, c}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, d, a}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, d, b}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, d, c}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, e, a}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, e, b}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, e, c}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, e, a}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, e, b}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, e, c}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, e, a}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, e, b}));
            Assert.assertEquals("007:C7B :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, e, c}));

            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, a, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, a, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, a, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, b, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, b, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, b, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, c, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, c, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, c, d}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, a, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, a, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, a, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, b, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, b, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, b, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, c, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, c, e}));
            Assert.assertEquals("007:C7C :)", obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, c, e}));
                generator.removeSourceFile(); // The weird issue of test working/failing
        } catch (Exception ex) {
            generator.removeSourceFile();
            System.out.println(ex.getCause());
            return false; // Should not get here :(

        }
        // Only one suitable per invoke :)

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, d, a});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                    ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, d, b});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, d, c});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }


        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, e, a});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, e, b});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, e, c});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, d, a});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, d, b});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, d, c});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, e, a});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, e, b});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, e, c});
            return false; // Shouldn't get here :(
        }

        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, d, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, d, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, d, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }


        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, e, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, e, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, e, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, d, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, d, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, d, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{a, e, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{b, e, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{c, e, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, a, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, b, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, c, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }


        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, a, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, b, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, c, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, a, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, b, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, c, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, a, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, b, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, c, e});
            return false; // Shouldn't get here :(
        }

        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, d, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }

        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, d, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }
        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, e, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }
        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{d, e, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }
        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, d, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }
        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, d, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }
        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, e, d});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }
        try {
            I7E obj7E = (I7E) generator.generateMultipleClass(I7E.class);
            obj7E.getClass().getMethod("invokeTest", types).invoke(obj7E, "f", new Object[]{e, e, e});
            return false; // Shouldn't get here :(
        }
        catch (Exception ex) {
            generator.removeSourceFile();
            Assert.assertTrue(
                    (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                            "OOP.Tests.Multiple.Example.I7A : f\n" +
                            "OOP.Tests.Multiple.Example.I7B : f\n" +
                            "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n"))
                            ||
                            (ex.getCause().getMessage().equals("Coincidental Ambiguous Method Call. candidates are : \n" +
                                    "OOP.Tests.Multiple.Example.I7C : f\n" +
                                    "OOP.Tests.Multiple.Example.I7B : f\n" +
                                    "OOP.Tests.Multiple.Example.I7A : f\n"))

            );
        }

        finally {
            generator.removeSourceFile();
        }
        return true;
    }

//********************************************LEVEL 7 :)**************************************************************//


    //********************************************TESTS :)****************************************************************//
    @Test
    public void testOOPBadClass() {
        //********************************************LEVEL 1 :)*******************************************************/

        assert(testOOPMultipleAnnotations()); // Checking if annotations are defined correctly :)

        // Test annotations, as they should be runtime+ their suitable types


//        assert (testOOPBadClassGraphBaseTagged());
//        assert (testOOPBadClassGraphBaseUntaggedInterface());
//        assert (testOOPBadClassGraphBaseUntaggedMethod());
//        assert (testOOPBadClassGraphBaseUntaggedInterfaceAndMethod());

        // Base should not include annotations of interface/method...
        //********************************************LEVEL 1 :)*******************************************************/

        //********************************************LEVEL 2 :)*******************************************************/
        Assert.assertTrue(testOOPBadClassGraphTreeTagged());
        Assert.assertTrue(testOOPBadClassGraphTreeUntaggedInterface());
        Assert.assertTrue(testOOPBadClassGraphTreeUntaggedMethod());
//        assert (testOOPBadClassGraphTreeUntaggedInterfaceAndMethod());

        // No two cases of methods/interfaces/method and interface who are not tagged
        //********************************************LEVEL 2 :)*******************************************************/

    }


    @Test
    public void testOOPInherentAmbiguity() {
        //********************************************LEVEL 3 :)*******************************************************/
        Assert.assertTrue(testOOPInherentAmbiguityFakeDiamond());
        Assert.assertTrue(testOOPInherentAmbiguityBasicDiamond());
        //********************************************LEVEL 3 :)*******************************************************/

        //********************************************LEVEL 4 :)*******************************************************/
        Assert.assertTrue(testOOPInherentAmbiguityComplexDiamond());
        //********************************************LEVEL 4 :)*******************************************************/


    }

    @Test
    public void testOOPCoincidentalAmbiguity() {
        //********************************************LEVEL 5 :)*******************************************************/
        Assert.assertTrue(testOOPCoincidentalAmbiguityFindSuitable());
        Assert.assertTrue(testOOPCoincidentalAmbiguityCheckCandidates());
        //********************************************LEVEL 5 :)*******************************************************/

        //********************************************LEVEL 6 :)*******************************************************/
        Assert.assertTrue(testOOPCoincidentalAmbiguityZeroArugments());
        //********************************************LEVEL 6 :)*******************************************************/

        //********************************************LEVEL 7 :)*******************************************************/
        Assert.assertTrue(testOOPCoincidentalAmbiguityMassiveExtensionAndGenericShuriken());
        //********************************************LEVEL 7 :)*******************************************************/

    }


//********************************************TESTS :)****************************************************************//


}

