package l3_da;

import l4_dm.DmAufgabe;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Christoph on 02.12.2015.
 */
public class DaAufgabeForJPA implements DaAufgabe{
    protected DaGeneric<DmAufgabe> generic;

    public DaAufgabeForJPA(EntityManager em) {
        generic = new DaGenericForJPA<>(DmAufgabe.class, em);
    }

    @Override
    public boolean save(DmAufgabe entity) {
        return generic.save(entity);
    }

    @Override
    public void delete(DmAufgabe entity){
        try {
            generic.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DmAufgabe find(long id) throws IdNotFoundExc {
        return generic.find(id);
    }

    @Override
    public List<DmAufgabe> findAll() {
        return generic.findAll();
    }

    @Override
    public List<DmAufgabe> findByField(String fieldName, Object fieldValue) {
        return findByField(fieldName, fieldValue);
    }

    @Override
    public List<DmAufgabe> findByWhere(String whereClause, Object... args) {
        return findByWhere(whereClause, args);
    }

    @Override
    public List<DmAufgabe> findByExample(DmAufgabe example) {
        return findByExample(example);
    }
}
