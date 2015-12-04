package platform;

import l3_da.DaGenericForJPA;
import l4_dm.DmSchritt;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class GenericTest {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("aufgabenplaner");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    protected final static DaGenericForJPA<DmSchritt> generic = new DaGenericForJPA<>(DmSchritt.class, entityManager);
    protected EntityTransaction transaction = entityManager.getTransaction();
    protected DmSchritt schritt = new DmSchritt();

    public GenericTest() {
        schritt.setTitel("test1");
        if(!transaction.isActive()){
            transaction.begin();
        }
    }

    @Test
    public void testSave() {
        generic.save(schritt);
        assertEquals(entityManager.contains(schritt), true);
    }

    @Test
    public void testDelete() {
        generic.save(schritt);
        assertEquals(entityManager.contains(schritt), true);
        generic.delete(schritt);
        assertEquals(entityManager.contains(schritt), false);
    }

    @Test
    public void testFind() {
        generic.save(schritt);
        assertNotNull(generic.find(schritt.getId()));
        Long id = schritt.getId();
        DmSchritt schritt2 = generic.find(id);
        assertEquals(schritt, schritt2);
        transaction.rollback();

    }

    @Test
    public void testFindAll() {
        DmSchritt schritt= new DmSchritt();
        schritt.setTitel("test");
        generic.save(schritt);
        DmSchritt schritt2= new DmSchritt();
        schritt.setTitel("test2");
        generic.save(schritt2);
        DmSchritt schritt3= new DmSchritt();
        schritt.setTitel("test3");
        generic.save(schritt3);
        transaction.rollback();
        List<DmSchritt> alle = generic.findAll();
        assertNotNull(alle);
    }
}