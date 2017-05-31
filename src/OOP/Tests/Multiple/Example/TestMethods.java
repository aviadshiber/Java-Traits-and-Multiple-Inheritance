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
    public class A{public void f(I1 a,I2 b){}}
    public class B extends A{public void f(I1 a,I2 b){}}
    @Test
    public void testMethods(){
        Method[] methods=B.class.getMethods();
        Method method=methods[0];
        Object[] args=new Object[]{new C3(),new C2()};
        ReflectionHelper.calculateMethodPath(method,args);
    }
}
