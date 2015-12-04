package platform;

import l3_da.DaAufgabe;
import l3_da.DaFactoryForJPA;
import l3_da.DaSchritt;
import l3_da.DaVorhaben;
import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryTest {
    DaFactoryForJPA factory;

    public FactoryTest(){
        factory = new DaFactoryForJPA();
    }

    @Test
    public void testgetAufgabe() {
        DaAufgabe aufgabeB = factory.getAufgabeDA();
        assertNotNull(aufgabeB);
    }
    @Test
    public void testgetSchritt() {
        DaSchritt schrittA = factory.getSchrittDA();
        assertNotNull(schrittA);
    }

    @Test
    public void testgetVorhaben() {
        DaVorhaben vorhabenA = factory.getVorhabenDA();
        assertNotNull(vorhabenA);
    }

    @Test
    public void testTransaction() {
        assertFalse(factory.isActive());
        factory.beginTransaction();
        assertTrue(factory.isActive());
        factory.endTransaction(true);
        assertFalse(factory.isActive());
        factory = new DaFactoryForJPA();
        factory.beginTransaction();
        factory.endTransaction(false);
        assertFalse(factory.isActive());
    }

}
