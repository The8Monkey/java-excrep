package l3_da;

import l4_dm.DmAufgabe;
import multex.MultexUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
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
            entityManager.merge(entity);
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
        final E e = entityManager.find(managedClass, id);
        if(e == null){
            throw MultexUtil.create(IdNotFoundExc.class, managedClass.getSimpleName(), id);
        }
        return e;
    }

    @Override
    public List<E> findAll() {
        final TypedQuery<E> query = entityManager.createQuery("SELECT t FROM " + managedClass.getName() + " t", managedClass);
        return query.getResultList();
    }

    @Override
    public List<E> findByField(String fieldName, Object fieldValue) {
        final TypedQuery<E> q = entityManager.createQuery("SELECT t FROM " + managedClass.getName() + " t"
                +" WHERE " + fieldName + "= " + fieldValue, managedClass);
        return q.getResultList();
    }

    @Override
    public List<E> findByWhere(String whereClause, Object... args) {
        final TypedQuery<E> q = entityManager.createQuery("SELECT * FROM :table t WHERE " + whereClause, managedClass)
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
        List<E> stuff = entityManager.createQuery("Select a From" + managedClass.getSimpleName() + "a")
                .getResultList();
        return stuff;
    }


}
