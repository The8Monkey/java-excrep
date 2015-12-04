package l3_da;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class DaFactoryForJPA implements DaFactory {
    private static final String persistenceUnitName = "aufgabenplaner";
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private EntityTransaction transaction;

    @Override
    public DaAufgabe getAufgabeDA() {

        return new DaAufgabeForJPA(entityManager);
    }

    @Override
    public DaSchritt getSchrittDA() {

        return new DaSchrittForJPA(entityManager);
    }

    @Override
    public DaVorhaben getVorhabenDA() {

        return new DaVorhabenForJPA(entityManager);
    }

    @Override
    public void beginTransaction() {
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @Override
    public void endTransaction(boolean ok) {
        if(!transaction.isActive()) return;
        try {
            if (ok) {
                transaction.commit();
            } else {
                transaction.rollback();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        entityManager.clear();
    }

    public boolean isActive() {

        return transaction != null && transaction.isActive();
    }
}