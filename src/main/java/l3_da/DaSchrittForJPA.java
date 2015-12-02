package l3_da;

import l4_dm.DmAufgabe;
import l4_dm.DmSchritt;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Christoph on 02.12.2015.
 */
public class DaSchrittForJPA implements DaSchritt {

    protected DaGeneric<DmSchritt> generic;

    public DaSchrittForJPA(EntityManager em) {
        generic = new DaGenericForJPA<>(DmSchritt.class, em);
    }

    @Override
    public boolean save(DmSchritt entity) {
        return generic.save(entity);
    }

    @Override
    public void delete(DmSchritt entity) {
        try {
            generic.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DmSchritt find(long id) throws IdNotFoundExc {
        return generic.find(id);
    }

    @Override
    public List<DmSchritt> findAll() {
        return generic.findAll();
    }

    @Override
    public List<DmSchritt> findByField(String fieldName, Object fieldValue) {
        return findByField(fieldName, fieldValue);
    }

    @Override
    public List<DmSchritt> findByWhere(String whereClause, Object... args) {
        return findByWhere(whereClause, args);
    }

    @Override
    public List<DmSchritt> findByExample(DmSchritt example) {
        return findByExample(example);
    }
}
