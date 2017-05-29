package OOP.Tests.Multiple.Example;

import OOP.Provided.Multiple.OOPMultipleClassGenerator;
import OOP.Provided.Multiple.OOPMultipleException;
import org.junit.Assert;
import org.junit.Test;

public class Example {

    private OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();

    @Test
    public void main() {
        try {
            I3 obj = (I3) generator.generateMultipleClass(I3.class);
            Assert.assertEquals("C1 : f", obj.f());
            obj.g();

        } catch (OOPMultipleException e) {
            e.printStackTrace();
        } finally {
            generator.removeSourceFile();
        }
    }
}
