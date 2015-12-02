package test.Java.platform;

import l3_da.DaGeneric;
import l3_da.DaGenericForJPA;
import l4_dm.DmSchritt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christoph on 02.12.2015.
 */
public class JpaTest {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("FPA");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    protected final static DaGenericForJPA<DmSchritt> generic = new DaGenericForJPA<DmSchritt>(DmSchritt.class, entityManager);
    protected EntityTransaction transaction = entityManager.getTransaction();
    protected DmSchritt schritt = new DmSchritt();

    public JpaTest() {
        schritt.setTitel("test1");
        transaction.begin();
    }

    @Test
    public void testSave() throws Exception {
        generic.save(schritt);
        assertTrue(entityManager.contains(schritt));
    }

    @Test(expected = PersistenceException.class)
    public void testDelete() throws Exception {
        generic.save(schritt);
        DmSchritt notPersisted = new DmSchritt();
        notPersisted.setTitel("test2");
        generic.delete(schritt);
        assertFalse(entityManager.contains(schritt));
        generic.delete(notPersisted);
    }

    @Test
    public void testFind() throws Exception {
        generic.save(schritt);
        assertNotNull(generic.find(schritt.getId()));
        DmSchritt notPersisted = new DmSchritt();
        notPersisted.setTitel("test3");
        generic.save(notPersisted);
        Long npid = notPersisted.getId();
        generic.delete(notPersisted);
        try {
            generic.find(npid);
        } catch (DaGeneric.IdNotFoundExc e) {
            return;
        }
        fail();
    }

    @Parameterized.Parameters
    public static List<DmSchritt> data() {
        List<DmSchritt> candidates = new ArrayList<DmSchritt>();
        for(int i = 0 ; i < 10; i++) {
            DmSchritt generated = new DmSchritt();
            generated.setTitel("test4");
            generic.save(generated);
            candidates.add(generated);
        }
        return candidates;
    }

    @Test
    public void testFindAll() throws Exception {
        List<DmSchritt> candidates = data();
        List<DmSchritt> persisted = generic.findAll();
        for(DmSchritt s: candidates) {
            if (!persisted.contains(s)) fail();
        }
    }

    @Test
    public void testFindByField() throws Exception {
        List<DmSchritt> candidates = data();
        for(DmSchritt s: candidates) {
            generic.findByField("id", s.getId().toString());
        }
    }

    @Test
    public void testFindByWhere() throws Exception {

    }

    @Test
    public void testFindByExample() throws Exception {

    }
}
