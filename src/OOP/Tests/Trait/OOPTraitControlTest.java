package OOP.Tests.Trait;

import OOP.Provided.Trait.OOPTraitClassGenerator;
import OOP.Provided.Trait.OOPTraitException;
import OOP.Tests.Trait.Example.TraitCollector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ashiber on 07-Jun-17.
 */
public class OOPTraitControlTest {

    private static OOPTraitClassGenerator generator = new OOPTraitClassGenerator();
    static TraitCollector obj = null;

    @Before
    public void init() throws OOPTraitException {

        obj = (TraitCollector) generator.generateTraitClassObject(TraitCollector.class);

    }

    @Test
    public void invoke() throws Exception {

        obj.add(3);
        obj.inc();
        Assert.assertEquals(4, obj.getValue());
        obj.add(6);
        Assert.assertEquals(10, obj.getValue());
    }

    @After
    public void end(){
        generator.removeSourceFile();
    }

}