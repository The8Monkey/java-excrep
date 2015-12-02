package platform;

import l3_da.DaGeneric;
import l3_da.DaGenericForJPA;
import l4_dm.DmSchritt;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

/**
 * Created by Christoph on 02.12.2015.
 */
public class JpaTest {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("aufgabenplaner");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    protected final static DaGenericForJPA<DmSchritt> generic = new DaGenericForJPA<>(DmSchritt.class, entityManager);
    protected EntityTransaction transaction = entityManager.getTransaction();
    protected DmSchritt schritt = new DmSchritt();

    public JpaTest() {
        schritt.setTitel("test1");
        if (transaction != null && !transaction.isActive()){
            transaction.begin();
        }
    }

    @Test
    public void testSave() throws Exception {
        generic.save(schritt);
        assertTrue(entityManager.contains(schritt));
    }

    @Test
    public void testDelete() throws Exception {
        generic.save(schritt);
        DmSchritt deletestepp = new DmSchritt();
        deletestepp.setTitel("test2");
        generic.delete(schritt);
        assertFalse(entityManager.contains(schritt));
    }

    @Test
    public void testFind() throws Exception {
        generic.save(schritt);
        assertNotNull(generic.find(schritt.getId()));
        DmSchritt findtest = new DmSchritt();
        findtest.setTitel("test3");
        generic.save(findtest);
        Long id = findtest.getId();
        generic.delete(findtest);
        try {
            generic.find(id);
        } catch (DaGeneric.IdNotFoundExc e) {
            return;
        }
        fail();
    }

    @Test
    public void testFindAll() throws Exception {
        DmSchritt schritt1 = new DmSchritt();
        schritt1.setTitel("test5");
        generic.save(schritt1);
        DmSchritt schritt2 = new DmSchritt();
        schritt2.setTitel("test6");
        generic.save(schritt2);
        DmSchritt schritt3 = new DmSchritt();
        schritt3.setTitel("test7");
        generic.save(schritt3);
        List<DmSchritt> list = generic.findAll();
        list.get(3);
    }

    @Test
    public void testFindByField() throws Exception {
    }

    @Test
    public void testFindByWhere() throws Exception {

    }

    @Test
    public void testFindByExample() throws Exception {

    }
}
