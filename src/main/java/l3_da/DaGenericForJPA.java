package l3_da;

import l4_dm.DmAufgabe;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Christoph on 02.12.2015.
 */
public class DaGenericForJPA<E extends DmAufgabe> implements DaGeneric<E> {
    protected final Class<E> managedClass;
    protected final EntityManager entityManager;

    public DaGenericForJPA(Class<E> managedClass, EntityManager entityManager) {
        this.managedClass = managedClass;
        this.entityManager = entityManager;
    }

    @Override
    public boolean save(E entity) {
        if(entityManager.contains(entity)){
            entityManager.refresh(entity);
            return true;
        }else {
            entityManager.persist(entity);
            return true;
        }
    }

    @Override
    public void delete(E entity){
        if(entityManager.contains(entity)){
            entityManager.remove(entity);
        }else {
            throw new PersistenceException("No object to delete!");
        }
    }

    @Override
    public E find(long id) throws IdNotFoundExc {
        if(entityManager.find(managedClass, id) == null){
            throw new IdNotFoundExc();
        }
        return entityManager.find(managedClass, id);
    }

    @Override
    public List findAll() {
        return entityManager.createNativeQuery("SELECT t FROM :table t")
                .setParameter("table", managedClass.getSimpleName())
                .getResultList();
    }

    @Override
    public List findByField(String fieldName, Object fieldValue) {
        Query q = entityManager.createQuery("SELECT " + fieldName + " FROM " + managedClass.getSimpleName() + " "
                + fieldName + " WHERE " + fieldName + "= " + fieldValue);
        return q.getResultList();
    }

    @Override
    public List findByWhere(String whereClause, Object... args) {
        Query q = entityManager.createQuery("SELECT * FROM :table t WHERE " + whereClause)
                .setParameter("table", managedClass.getSimpleName());
        for(int i = 0; i < args.length; i++) {
            q.setParameter(i, args[i]);
        }
        return q.getResultList();
    }


    //@TODO: crap
    @Override
    public List<E> findByExample(E example) {
        entityManager.find(managedClass.getClass() ,example);
        List<E> stuff = entityManager.createQuery("Select a From" + managedClass.getSimpleName() + "a").getResultList();
        return stuff;
    }


}
