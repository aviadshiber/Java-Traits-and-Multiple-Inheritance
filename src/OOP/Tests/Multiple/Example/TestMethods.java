package OOP.Tests.Multiple.Example;

import OOP.Solution.Multiple.OOPMultipleControl;
import OOP.Solution.Multiple.OOPMultipleMethod;
import OOP.Solution.ReflectionUtils.ReflectionHelper;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by ashiber on 31-May-17.
 */

public class TestMethods {
    public class A{public void f(Object a,Object b){}}
    public class B extends A{public void f(CharSequence a,Object b){}}
    public class C extends B{public void f(String a,I2 b){}}
    @Test
    public void testMethods(){
        Method[] methods=B.class.getMethods();
        Method method=methods[0];
        Object[] args=new Object[]{"this is a String",new C2()};
        System.out.println(ReflectionHelper.calculateMethodPath(method,args));


    }
}
