package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitClassGenerator;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Solution.Trait.OOPTraitBehaviour;
import OOP.Solution.Trait.OOPTraitConflictResolver;
import OOP.Solution.Trait.OOPTraitMethod;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by danie_000 on 6/12/2017.
 */
public class TestTrait {
    private static OOPTraitClassGenerator generator = new OOPTraitClassGenerator();
    static TraitCollector obj = null;
    static T2H obj2H = null;
    static T2M obj2M = null;
    static T2R obj2R = null;
    static T3E obj3E = null;
    static T3J obj3J = null;
    static T5E obj5E = null;
    private Class[] types = new Class[]{String.class, Object[].class};


    @Test
    public void TestExample() {
        try {
            obj = (TraitCollector) generator.generateTraitClassObject(TraitCollector.class);
            obj.add(3);
            obj.inc();
            Assert.assertEquals(4, obj.getValue());

        } catch (OOPTraitException e) {
            e.printStackTrace();
        } finally {
            generator.removeSourceFile();
        }
    }


    //********************************************TESTS :)****************************************************************//


    //********************************************LEVEL 0 :)*******************************************************/

    private boolean testOOPTraitBehaviourAnnotations() {
        Assert.assertEquals("@java.lang.annotation.Target(value=[TYPE])", OOPTraitBehaviour.class.getAnnotation(Target.class).toString());
        Assert.assertEquals("@java.lang.annotation.Retention(value=RUNTIME)", OOPTraitBehaviour.class.getAnnotation(Retention.class).toString());

        return true;
    }

    private boolean testOOPTraitMethodAnnotations() {
        Assert.assertEquals("@java.lang.annotation.Target(value=[METHOD])", OOPTraitMethod.class.getAnnotation(Target.class).toString());
        Assert.assertEquals("@java.lang.annotation.Retention(value=RUNTIME)", OOPTraitMethod.class.getAnnotation(Retention.class).toString());

        return true;
    }

    private boolean testOOPTraitConflictResolverAnnotation() {
        Assert.assertEquals("@java.lang.annotation.Target(value=[METHOD])", OOPTraitConflictResolver.class.getAnnotation(Target.class).toString());
        Assert.assertEquals("@java.lang.annotation.Retention(value=RUNTIME)", OOPTraitConflictResolver.class.getAnnotation(Retention.class).toString());

        return true;
    }


    //********************************************LEVEL 0 :)*******************************************************/


    //********************************************LEVEL 1 :)*******************************************************/
    private boolean testOOPBadClassGraphBaseUntaggedMethod() {
        /*try {
            T1C obj1C = (T1C) generator.generateTraitClassObject(T1C.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T1C : add is Corrupted!", e.getMessage());

        }finally {
            generator.removeSourceFile();
        }*/
       /* try {
            T1D obj1D = (T1D) generator.generateTraitClassObject(T1D.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T1D : conf is Corrupted!", e.getMessage());
        } finally {
            generator.removeSourceFile();
        }*/
        return true;
    }
    //********************************************LEVEL 1 :)*******************************************************/


    //********************************************LEVEL 2 :)*******************************************************/
    boolean testOOPBadClassGraphTreeUntaggedInterface() {
        try {
            T2C obj2C = (T2C) generator.generateTraitClassObject(T2C.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T2A is Corrupted!", e.getMessage());

        }finally {
            generator.removeSourceFile();
        }
        try {
            obj2H = (T2H) generator.generateTraitClassObject(T2H.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T2E is Corrupted!", e.getMessage());
            //generator.removeSourceFile();
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    boolean testOOPBadClassGraphTreeUntaggedMethod() {
        try {
            obj2M = (T2M) generator.generateTraitClassObject(T2M.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T2I : goodbye is Corrupted!", e.getMessage());

        }finally {
            generator.removeSourceFile();
        }

        try {
            obj2R = (T2R) generator.generateTraitClassObject(T2R.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T2O : hello is Corrupted!", e.getMessage());
            generator.removeSourceFile();
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    //********************************************LEVEL 2 :)*******************************************************/


    //********************************************LEVEL 3 :)*******************************************************/

    boolean testOOPMissingImplementationBasicHello() {
        try {
            obj3J = (T3J) generator.generateTraitClassObject(T3J.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T3I : finishTalking is missing!", e.getMessage());
            //generator.removeSourceFile();
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    boolean testOOPMissingImplementationBasicConversation() {
        try {
            obj3E = (T3E) generator.generateTraitClassObject(T3E.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T3B : hello is missing!", e.getMessage());
            generator.removeSourceFile();
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    //********************************************LEVEL 3 :)*******************************************************/


    //********************************************LEVEL 4 :)*******************************************************/
    boolean testOOPConflictDiamondInherentValidate() {
        try {
            T4D obj4D = (T4D) generator.generateTraitClassObject(T4D.class);
            return false;
        } catch (Exception e) {
            Assert.assertTrue(
                    e.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                            "OOP.Tests.Trait.Example.T4A : startDrivingFerrari is conflicting!")
                            ||
                            e.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                                    "OOP.Tests.Trait.Example.T4B : startDrivingFerrari is conflicting!")
                            ||
                            e.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                                    "OOP.Tests.Trait.Example.T4C : startDrivingFerrari is conflicting!")
                            ||
                            e.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                                    "OOP.Tests.Trait.Example.T4D : startDrivingFerrari is conflicting!")
            );
            generator.removeSourceFile();
        }
        try {
            T4H obj4H = (T4H) generator.generateTraitClassObject(T4H.class);
            Assert.assertEquals("Cool car :)", obj4H.startDrivingFerrari());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Should not get here :( , you drove in the wrong road :(
            return false;

        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    boolean testOOPConflictResolverInheretedMethodIsChosen() {
        try {
            T4K obj4K = (T4K) generator.generateTraitClassObject(T4K.class);
            Assert.assertEquals("Buying Ferrari 488 , only $334,409... not so expensive :D", obj4K.buyFerrari());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Should not get here :( , you drove in the wrong road :(
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;

    }

    boolean testOOPConflictResolverInheretingMethodIsChosen() {
        try {
            T4L obj4L = (T4L) generator.generateTraitClassObject(T4L.class);
            Assert.assertEquals("Buying Ferrari 488 , only $666,666... not so expensive :D", obj4L.buyFerrari());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Should not get here :( , you drove in the wrong road :(
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;

    }

    //********************************************LEVEL 4 :)*******************************************************/

    //********************************************LEVEL 5 :)*******************************************************/
    boolean testOOPElevatorSynchronized() {
        try {
            obj5E = (T5E) generator.generateTraitClassObject(T5E.class);

            Assert.assertEquals("Current floor is:0", obj5E.getFloor());
            obj5E.moveUp();
            Assert.assertEquals("Current floor is:2", obj5E.getFloor());
            obj5E.moveUp();
            Assert.assertEquals("Current floor is:4", obj5E.getFloor());
            obj5E.doubleMoveUp();
            Assert.assertEquals("Current floor is:8", obj5E.getFloor());
            obj5E.doubleMoveUp();
            Assert.assertEquals("Current floor is:12", obj5E.getFloor());
            obj5E.doubleMoveUp();
            Assert.assertEquals("Current floor is:16", obj5E.getFloor());


        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Should not get here :( , you drove in the wrong road :(
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    boolean testOOPElevatorUnsynchronized() {
        try {
            obj5E = (T5E) generator.generateTraitClassObject(T5E.class);

            Assert.assertEquals("Current floor is:0", obj5E.getFloor());
            obj5E.doubleMoveUp();
            Assert.assertEquals("Current floor is:4", obj5E.getFloor());

            generator.removeSourceFile();

            obj5E = (T5E) generator.generateTraitClassObject(T5E.class);

            Assert.assertEquals("Current floor is:0", obj5E.getFloor()); // newInstance, because new generated tree :)


        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Should not get here :( , you drove in the wrong road :(
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }
    //********************************************LEVEL 5 :)*******************************************************/

    //********************************************LEVEL 6 :)*******************************************************/

    boolean testOOPConflictGladiatorVictorious() {
        try {
            T6D obj6D = (T6D) generator.generateTraitClassObject(T6D.class);
            A a = new A();
            B b = new B();
            C c = new C();
            D d = new D();
            E e = new E();
            Object o = new Object();

            Assert.assertEquals("T6B:O", obj6D.f(d, a, o));
            Assert.assertEquals("T6B:O", obj6D.f(d, d, o));
            Assert.assertEquals("T6B:O", obj6D.f(a, d, o));
            Assert.assertEquals("T6B:O", obj6D.f(o, o, o));
            Assert.assertEquals("T6B:O", obj6D.f(a, o, d));
            Assert.assertEquals("T6B:O", obj6D.f(c, o, d));
            Assert.assertEquals("T6B:O", obj6D.f(c, a, d));
            Assert.assertEquals("T6B:O", obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "f", new Object[]{a, b, o}));


            Assert.assertEquals("T6A", obj6D.f(d, a, a));
            Assert.assertEquals("T6A", obj6D.f(d, a, b));
            Assert.assertEquals("T6A", obj6D.f(d, c, c));
            Assert.assertEquals("T6A", obj6D.f(d, c, e));
            Assert.assertEquals("T6A", obj6D.f(e, a, a));
            Assert.assertEquals("T6A", obj6D.f(e, a, b));
            Assert.assertEquals("T6A", obj6D.f(e, c, c));
            Assert.assertEquals("T6A", obj6D.f(e, c, e));

            Assert.assertEquals("C6B", obj6D.f(a, d, a));
            Assert.assertEquals("C6B", obj6D.f(a, d, b));
            Assert.assertEquals("C6B", obj6D.f(c, d, c));
            Assert.assertEquals("C6B", obj6D.f(c, d, e));
            Assert.assertEquals("C6B", obj6D.f(a, e, a));
            Assert.assertEquals("C6B", obj6D.f(a, e, b));
            Assert.assertEquals("C6B", obj6D.f(c, e, c));
            Assert.assertEquals("C6B", obj6D.f(c, e, e));


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }
    //********************************************LEVEL 6 :)*******************************************************/

    boolean testOOPConflictGladiatorTie() {
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        E e = new E();
        Object o = new Object();

        try {
            T6D obj6D = (T6D) generator.generateTraitClassObject(T6D.class);
            obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "f", new Object[]{e, d, c});
            return false;
        } catch (Exception ex) {
            Assert.assertTrue(
                    ex.getCause().getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                            "OOP.Tests.Trait.Example.T6B : f is conflicting!") // C6B is better, but conflict regards
                            // a gladiator fight... so mainly the other checks (C6B or T6A) is thrown :)
                            ||
                            ex.getCause().getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                                    "OOP.Tests.Trait.Example.C6B : f is conflicting!")
                            ||
                            ex.getCause().getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                                    "OOP.Tests.Trait.Example.T6A : f is conflicting!")

            );
        } finally {
            generator.removeSourceFile();
        }


        return true;
    }
    //********************************************LEVEL 6 :)*******************************************************/

    //********************************************LEVEL 7 :)*******************************************************/

    boolean testOOPShurikenTwentyVoidReturnsNull() {
        try {
            obj5E = (T5E) generator.generateTraitClassObject(T5E.class);
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "doubleMoveUp", null)); // 4
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "doubleMoveUp", null)); // 8
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "doubleMoveUp", null)); // 12
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "moveUp", null)); // 14
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "advance", new Object[]{new Integer(6)})); // 20
            Assert.assertEquals("Current floor is:20", obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "getFloor", null));


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }

        return true;
    }
    //********************************************LEVEL 7 :)*******************************************************/

    //********************************************LEVEL 8 :)*******************************************************/

    boolean testOOPNoUpcastImplementationForAbstractMethodAux() {
        try {
            T8C obj8C = (T8C) generator.generateTraitClassObject(T8C.class);
            return false;
        } catch (Exception ex) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T8A : f is missing!", ex.getMessage());
            generator.removeSourceFile();
        }
        try {
            T8E obj8E = (T8E) generator.generateTraitClassObject(T8E.class);
            return false;
        } catch (Exception ex) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T8A : f is missing!", ex.getMessage());
            generator.removeSourceFile();
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    boolean testOOPUpcastImplementationForAbstractMethodExistsAux() {
        try {
            T8H obj8H = (T8H) generator.generateTraitClassObject(T8H.class);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }


        try {
            T8J obj8H = (T8J) generator.generateTraitClassObject(T8J.class);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }

        try {
            T8L obj8L = (T8L) generator.generateTraitClassObject(T8L.class);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }


        try {
            T8O obj8O = (T8O) generator.generateTraitClassObject(T8O.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }
    //********************************************LEVEL 8 :)*******************************************************/

    //********************************************LEVEL 9 :)*******************************************************/
    boolean testOOPMissingConflictResolverAux() {
        try {
            T9C obj9C = (T9C) generator.generateTraitClassObject(T9C.class);
            return false;
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T9A : f is conflicting!")
                    ||
                    ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                            "OOP.Tests.Trait.Example.T9B : f is conflicting!")
            ); // We need to throw one of the f's, so both options to throw are possible :)
            // generator.removeSourceFile();
        } finally {
            generator.removeSourceFile();
        }

        try {
            T9H obj9H = (T9H) generator.generateTraitClassObject(T9H.class);
            return false;
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T9F : smile is conflicting!")
                    ||
                    ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                            "OOP.Tests.Trait.Example.T9G : smile is conflicting!")
            ); // We need to throw one of the f's, so both options to throw are possible :)
            // Notice that C9D has the implementation here, not T9D :)

        } finally {
            generator.removeSourceFile();
        }

        try {
            T9I obj9I = (T9I) generator.generateTraitClassObject(T9I.class);
            return false;
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T9F : smile is conflicting!")
                    ||
                    ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                            "OOP.Tests.Trait.Example.T9G : smile is conflicting!")
            ); // We need to throw one of the f's, so both options to throw are possible :)
            // Notice that C9D has the implementation here, not T9D :)

        } finally {
            generator.removeSourceFile();
        }

        try {
            T9J obj9J = (T9J) generator.generateTraitClassObject(T9J.class);
            return false;
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T9F : smile is conflicting!")
                    ||
                    ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                            "OOP.Tests.Trait.Example.T9G : smile is conflicting!")
            ); // We need to throw one of the f's, so both options to throw are possible :)
            // Notice that C9D has the implementation here, not T9D :)

        } finally {
            generator.removeSourceFile();
        }

        try {
            T9K obj9K = (T9K) generator.generateTraitClassObject(T9K.class);
            return false;
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T9F : smile is conflicting!")
                    ||
                    ex.getMessage().equals("OOP.Provided.Trait.OOPTraitException : \n" +
                            "OOP.Tests.Trait.Example.T9G : smile is conflicting!")
            ); // We need to throw one of the f's, so both options to throw are possible :)
            // Notice that C9D has the implementation here, not T9D :)
            //generator.removeSourceFile();
        } finally {
            generator.removeSourceFile();
        }

        return true;
    }

    boolean testOOPDownCastMethodIsNotConflictIssue() {
        try {
            Object o = new Object();
            A a = new A();
            B b = new B();
            C c = new C();
            D d = new D();
            E e = new E();

            /*
             * T9L: smile(Object,A,A)
             * T9M: smile(A,B,A)
             * T9N: smile(B,C,A)
             * C9O: smile(C,D,C)
             */


            T9P obj9P = (T9P) generator.generateTraitClassObject(T9P.class);
            Assert.assertEquals("T9L :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{o, a, a}));
            Assert.assertEquals("T9L :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{o, b, a}));
            Assert.assertEquals("T9L :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{a, a, a}));


            Assert.assertEquals("T9M :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{a, b, a}));
            Assert.assertEquals("T9M :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{a, c, a}));
            Assert.assertEquals("T9M :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{b, b, a}));


            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{b, c, a}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{b, c, b}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{b, c, c}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{b, d, a}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{b, d, b}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{b, d, c}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, c, a}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, c, b}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, c, c}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, d, a}));
            Assert.assertEquals("T9N :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, d, b}));


            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, d, c}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, d, d}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, d, e}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, e, c}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, e, d}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{c, e, e}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{d, d, c}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{d, d, d}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{d, d, e}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{d, e, c}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{d, e, d}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{d, e, e}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{e, d, c}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{e, d, d}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{e, d, e}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{e, e, c}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{e, e, d}));
            Assert.assertEquals("T9O :)", obj9P.getClass().getMethod("invokeTest", types).invoke(obj9P, "smile", new Object[]{e, e, e}));


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    //********************************************LEVEL 9 :)*******************************************************/


    //********************************************LEVEL 10 :)*******************************************************/

    boolean testOOPUltimateConflictResolversInvokeSelectionAux() {
        Object o = new Object();
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        E e = new E();

        /*
         *T10A: winUltimate(Object,Object) ,throwShuriken()
         *T10B: winUltimate(Object,Object) ,throwShuriken()
         *T10C: winUltimate(Object,A)
         *C10D: winUltimate(A,Object)
         *T10E: winUltimate(A,B)
         *T10F: winUltimate(A,C)
         *T10G: winUltimate(C,B)

         *T10H: winUltimate(Object,Object) abstract
         *T10I: winUltimate(Object,Object) abstract
         *T10J: winUltimate(Object,Object) abstract

         *T10K (King) will decide all conflicts ^^

         *Lets pick the resolvers! :)

         *throwSuriken(), we'll pick T10B as the resolver :)
         *winUltimate(Object,object), we'll pick T10H as the resolver :)
         *winUltimate(A,A), we'll pick T10I as the resolver :)
         *winUltimate(D,C), we'll pick T10J as the resolver :)

         *We'll have the next C (Class) files: C10B,C10C,C10D,C10E,C10F,C10G,C10H,C10I,C10J
         */
        try {
            T10K obj10K = (T10K) generator.generateTraitClassObject(T10K.class);

            /**************************************CONFLICTERS :)******************************************************/
            Assert.assertEquals("T10B :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "throwShuriken", null));
            Assert.assertEquals("T10A :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{o, o}));
            /**************************************CONFLICTERS :)******************************************************/

            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{o, a}));
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{o, b}));
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{o, c}));
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{o, d}));
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{o, e}));

            Assert.assertEquals("T10D :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{a, o}));
            Assert.assertEquals("T10D :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{b, o}));
            Assert.assertEquals("T10D :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{c, o}));
            Assert.assertEquals("T10D :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{d, o}));
            Assert.assertEquals("T10D :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{e, o}));

            /**************************************CONFLICTERS :)******************************************************/
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{a, a}));
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{c, a}));
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{d, a}));
            Assert.assertEquals("T10C :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{e, a}));
            /**************************************CONFLICTERS :)******************************************************/


            Assert.assertEquals("T10E :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{a, b}));
            Assert.assertEquals("T10E :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{b, b}));

            Assert.assertEquals("T10F :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{a, c}));
            Assert.assertEquals("T10F :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{a, d}));
            Assert.assertEquals("T10F :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{a, e}));
            Assert.assertEquals("T10F :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{b, c}));
            Assert.assertEquals("T10F :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{b, d}));
            Assert.assertEquals("T10F :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{b, e}));

            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{c, b}));
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{d, b}));
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{e, b}));

            /**************************************CONFLICTERS :)******************************************************/
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{d, c}));
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{d, d}));
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{d, e}));
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{e, c}));
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{e, d}));
            Assert.assertEquals("T10G :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{e, e}));
            /**************************************CONFLICTERS :)******************************************************/

            /*
             * DO NOT TEST THESE CASES, As there is no solution for them (The 3 below ) :)
             */
            //   Assert.assertEquals("T10J :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{c, c}));
            //   Assert.assertEquals("T10J :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{c, c}));
            //   Assert.assertEquals("T10J :)", obj10K.getClass().getMethod("invokeTest", types).invoke(obj10K, "winUltimate", new Object[]{c, c}));


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            generator.removeSourceFile();
        }
        return true;
    }

    //********************************************LEVEL 10 :)*******************************************************/

    @Test
    public void testOOPTraitAnnotations() {
        //********************************************LEVEL 0 :)*******************************************************/

        Assert.assertTrue(testOOPTraitBehaviourAnnotations());
        Assert.assertTrue(testOOPTraitMethodAnnotations());
        Assert.assertTrue(testOOPTraitConflictResolverAnnotation());

        //********************************************LEVEL 0 :)*******************************************************/

    }

    @Test
    public void testOOPBadClass() {
        //********************************************LEVEL 1 :)*******************************************************/
        assert (testOOPBadClassGraphBaseUntaggedMethod());

        // Base should not include an annotation of interface
        //********************************************LEVEL 1 :)*******************************************************/

        //********************************************LEVEL 2 :)*******************************************************/
        Assert.assertTrue(testOOPBadClassGraphTreeUntaggedInterface());
        Assert.assertTrue(testOOPBadClassGraphTreeUntaggedMethod());

        // No two cases of methods/interfaces/ method and interface who are not tagged
        //********************************************LEVEL 2 :)*******************************************************/

    }


    @Test
    public void testOOPMissingImplementation() {
        //********************************************LEVEL 3 :)*******************************************************/
        Assert.assertTrue(testOOPMissingImplementationBasicHello());
        Assert.assertTrue(testOOPMissingImplementationBasicConversation());
        //********************************************LEVEL 3 :)*******************************************************/
    }

    @Test
    public void testOOPCoincidentalAmbiguityBasic() {
        //********************************************LEVEL 4 :)*******************************************************/
        Assert.assertTrue(testOOPConflictDiamondInherentValidate()); // Allow diamond :)
        Assert.assertTrue(testOOPConflictResolverInheretedMethodIsChosen());
        Assert.assertTrue(testOOPConflictResolverInheretingMethodIsChosen());
        //********************************************LEVEL 4 :)*******************************************************/

    }

    @Test
    public void testOOPElevator() {
        //********************************************LEVEL 5 :)*******************************************************/
//        Assert.assertTrue(testOOPElevatorSynchronized());
        Assert.assertTrue(testOOPElevatorUnsynchronized());
        //********************************************LEVEL 5 :)*******************************************************/

    }

    @Test
    public void testOOPVoidReturnsNull() {
        //********************************************LEVEL 6 :)*******************************************************/
                Assert.assertTrue(testOOPConflictGladiatorVictorious());
               Assert.assertTrue(testOOPConflictGladiatorTie());
        //********************************************LEVEL 6 :)*******************************************************/


        //********************************************LEVEL 7 :)*******************************************************/
        Assert.assertTrue(testOOPShurikenTwentyVoidReturnsNull());
        //********************************************LEVEL 7 :)*******************************************************/
    }

    @Test
    public void testOOPNoUpcastImplementationForAbstractMethod() {
        //********************************************LEVEL 8 :)*******************************************************/
        Assert.assertTrue(testOOPNoUpcastImplementationForAbstractMethodAux());
        //********************************************LEVEL 8 :)*******************************************************/

    }

    @Test
    public void testOOPUpcastImplementationForAbstractMethodExists() {
        //********************************************LEVEL 8 :)*******************************************************/
        Assert.assertTrue(testOOPUpcastImplementationForAbstractMethodExistsAux());
        //********************************************LEVEL 8 :)*******************************************************/
    }

    @Test
    public void testOOPMissingConflictResolver() {
        //********************************************LEVEL 9 :)*******************************************************/
        Assert.assertTrue(testOOPMissingConflictResolverAux());
        Assert.assertTrue(testOOPDownCastMethodIsNotConflictIssue());
        //********************************************LEVEL 9 :)*******************************************************/
    }

    @Test
    public void testOOPUltimateConflictResolversInvokeSelection() {
        Assert.assertTrue(testOOPUltimateConflictResolversInvokeSelectionAux());
    }

//********************************************TESTS :)****************************************************************//


}



