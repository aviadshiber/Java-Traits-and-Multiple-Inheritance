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
        try {
            T1C obj1C = (T1C) generator.generateTraitClassObject(T1C.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T1C : add is Corrupted!", e.getMessage());
            generator.removeSourceFile();
        }
        try {
            T1D obj1D = (T1D) generator.generateTraitClassObject(T1D.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T1D : conf is Corrupted!", e.getMessage());
        } finally {
            generator.removeSourceFile();
        }
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
            generator.removeSourceFile();
        }
        try {
            obj2H = (T2H) generator.generateTraitClassObject(T2H.class);
            return false;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.T2E is Corrupted!", e.getMessage());
            generator.removeSourceFile();
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
            generator.removeSourceFile();
        }
        try {
            obj2R = (T2R) generator.generateTraitClassObject(T2R.class);
            return true;
        } catch (Exception e) {
            Assert.assertEquals("OOP.Provided.Trait.OOPTraitException : \n" +
                    "OOP.Tests.Trait.Example.C2O : hello is Corrupted!", e.getMessage());
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
            generator.removeSourceFile();
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
            Assert.assertEquals("T6B:O",obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "f", new Object[]{a, b, o}));


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
            obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "f", new Object[]{d, d, a});
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
            generator.removeSourceFile();
        }

        try {
            T6D obj6D = (T6D) generator.generateTraitClassObject(T6D.class);
            obj6D.getClass().getMethod("invokeTest", types).invoke(obj6D, "f", new Object[]{e, e, c});
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
            generator.removeSourceFile();
        }

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
            generator.removeSourceFile();
        }

        finally {
            generator.removeSourceFile();
        }




        return true;
    }
    //********************************************LEVEL 6 :)*******************************************************/

    //********************************************LEVEL 7 :)*******************************************************/

    boolean testOOPShurikenTwentyVoidReturnsNull(){
        try {
            obj5E = (T5E) generator.generateTraitClassObject(T5E.class);
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "doubleMoveUp",null)); // 4
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "doubleMoveUp",null)); // 8
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "doubleMoveUp",null)); // 12
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "moveUp",null)); // 14
            Assert.assertNull(obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "advance",new Object[]{new Integer(6)})); // 20
            Assert.assertEquals("Current floor is:20",obj5E.getClass().getMethod("invokeTest", types).invoke(obj5E, "getFloor",null));


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        finally {
            generator.removeSourceFile();
        }

        return true;
    }

    //********************************************LEVEL 7 :)*******************************************************/

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
        Assert.assertTrue(testOOPElevatorSynchronized());
        Assert.assertTrue(testOOPElevatorUnsynchronized());
        //********************************************LEVEL 5 :)*******************************************************/

    }

    @Test
    public void testOOPConflictGladiators() {
        //********************************************LEVEL 6 :)*******************************************************/
        Assert.assertTrue(testOOPConflictGladiatorVictorious()); //overriding conflict
        Assert.assertTrue(testOOPConflictGladiatorTie());
        //********************************************LEVEL 6 :)*******************************************************/


        //********************************************LEVEL 7 :)*******************************************************/
          Assert.assertTrue(testOOPShurikenTwentyVoidReturnsNull());
        //********************************************LEVEL 7 :)*******************************************************/
    }


//********************************************TESTS :)****************************************************************//


}



