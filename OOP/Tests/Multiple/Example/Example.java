package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleClassGenerator;
import OOP.Provided.Multiple.OOPMultipleException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class Example {

    private OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();

    @Test
    public void main() {
        try {
            I3 obj = (I3) generator.generateMultipleClass(I3.class);

           // Arrays.stream(I2.class.getAnnotations()).forEach(clazz-> System.out.println(clazz));

            Assert.assertEquals("C1 : f", obj.f());
            obj.g();

        } catch (OOPMultipleException e) {
            e.printStackTrace();
        } finally {
            generator.removeSourceFile();
        }
    }
}
