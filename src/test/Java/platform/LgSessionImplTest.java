package platform;

import l2_lg.LgSessionImpl;
import l3_da.DaFactoryForJPA;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Christoph on 10.12.2015.
 */
public class LgSessionImplTest {
    DaFactoryForJPA factory;
    LgSessionImpl lgs;

    public LgSessionImplTest(){
        factory = new DaFactoryForJPA();
        lgs = new LgSessionImpl(factory);
    }

    @org.junit.Test
    public void testTransienteDatenFuellen() throws Exception {

    }

    @org.junit.Test
    public void testSpeichern() throws Exception {
    }

    @org.junit.Test
    public void testSchrittErledigen() throws Exception {

    }

    @org.junit.Test
    public void testAlleOberstenAufgabenLiefern() throws Exception {

    }

    @org.junit.Test
    public void testTransaction() throws Exception {
        assertFalse(factory.isActive());
        lgs.beginTransaction();
        assertTrue(factory.isActive());
        lgs.endTransaction(true);
        assertFalse(factory.isActive());
        lgs.beginTransaction();
        lgs.endTransaction(false);
        assertFalse(factory.isActive());
    }
}