package platform;

import l4_dm.DmSchritt;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class JpaLoesungTest {
    private final String persistenceUnitName = "aufgabenplaner";
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);

    @Test
    public void entityPersistieren() throws Exception {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final DmSchritt schritt = new DmSchritt();
        schritt.setTitel("Post abholen");
        entityManager.persist(schritt);
        assertEquals(true, entityManager.contains(schritt));
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void rollback() throws Exception{
        final String title="Ein problematischer Schritt";
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final DmSchritt schritt = new DmSchritt();
        schritt.setTitel(title);
        entityManager.persist(schritt);
        final Long idOfFailed = schritt.getId();
        transaction.rollback();
        entityManager.close();
        final EMTransaction tx = new EMTransaction();
        final DmSchritt schritt2 = tx.em.find(DmSchritt.class, idOfFailed);
        assertNotEquals(title, schritt2);
        tx.close(true);
    }

    @Test
    public void merge() throws Exception{
        final DmSchritt schritt;
        final String title="New testcase";
        final EMTransaction txMain = new EMTransaction();
        final DmSchritt idSchritt = new DmSchritt();
        idSchritt.setTitel(title);
        txMain.em.persist(idSchritt);
        txMain.close(true);
        final Long id = idSchritt.getId();
        {
            final EMTransaction tx = new EMTransaction();
            schritt = tx.em.find(DmSchritt.class, id);
            tx.close(true);
        }
        schritt.setIstStunden(3);
        {
            final EMTransaction tx = new EMTransaction();
            final DmSchritt testSchritt = tx.em.find(DmSchritt.class, id);
            assertNotEquals(3,testSchritt.getIstStunden());
            tx.close(true);
        }
        {
            final EMTransaction tx = new EMTransaction();
            tx.em.merge(schritt);
            tx.close(true);
        }
        {
            final EMTransaction tx = new EMTransaction();
            final DmSchritt testSchritt = tx.em.find(DmSchritt.class, id);
            assertEquals(3, testSchritt.getIstStunden());
            tx.close(true);
        }
    }

    @Test
    public void change() throws Exception{
        final String title="Testschritt";
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        final DmSchritt mainSchritt = new DmSchritt();
        mainSchritt.setTitel(title);
        entityManager.persist(mainSchritt);
        transaction.commit();
        entityManager.close();
        final Long mainSchrittId = mainSchritt.getId();
        final EMTransaction tx = new EMTransaction();
        final DmSchritt schritt = tx.em.find(DmSchritt.class, mainSchrittId);
        schritt.setRestStunden(0);
        schritt.setIstStunden(2);
        tx.close(true);
        schritt.setIstStunden(1000);
        final EMTransaction tx2 = new EMTransaction();
        final DmSchritt testSchritt = tx2.em.find(DmSchritt.class, mainSchrittId);
        assertEquals(title, testSchritt.getTitel());
        assertEquals(0, testSchritt.getRestStunden());
        assertEquals(2, testSchritt.getIstStunden());
    }

    @Test
    public void delete() throws Exception{
        final String title="New deletetestcase";
        final EMTransaction txMain = new EMTransaction();
        final DmSchritt idSchritt = new DmSchritt();
        idSchritt.setTitel(title);
        txMain.em.persist(idSchritt);
        txMain.close(true);
        final Long id = idSchritt.getId();
        final EMTransaction tx = new EMTransaction();
        final DmSchritt schritt = tx.em.find(DmSchritt.class, id);
        tx.em.remove(schritt);
        assertEquals(null, tx.em.find(DmSchritt.class, id));
        tx.close(true);
    }

    private class EMTransaction {
        /**Der {@link EntityManager} mit Transaction Scope für die weiteren Datenzugriffsoperationen.*/
        public final EntityManager em = entityManagerFactory.createEntityManager();
        private final EntityTransaction transaction = em.getTransaction();
        {
            transaction.begin();
        }
        /**Schließt die {@link EMTransaction} mit entweder commit oder rollback.
         * @param ok die Transaktion war bis zum Ende fehlerfrei => commit, andernfalls rollback.
         */
        public void close(final boolean ok){
            try{
                if(!transaction.isActive())return;
                if(ok){
                    transaction.commit();
                } else {
                    transaction.rollback();
                }
            }finally{ //wird auch bei Erfolg oder return ausgeführt!
                em.close();
            }
        }
    }
}