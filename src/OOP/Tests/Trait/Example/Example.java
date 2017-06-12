package OOP.Tests.Trait.Example;

import OOP.Provided.Trait.OOPTraitClassGenerator;
import OOP.Provided.Trait.OOPTraitException;
import org.junit.Assert;
import org.junit.Test;

public class Example {

    private static OOPTraitClassGenerator generator = new OOPTraitClassGenerator();
    static TraitCollector obj = null;

    @Test
    public void main() {
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
}
