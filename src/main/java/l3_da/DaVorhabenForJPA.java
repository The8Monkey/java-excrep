package l3_da;

import l4_dm.DmVorhaben;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Christoph on 02.12.2015.
 */
public class DaVorhabenForJPA implements DaVorhaben{
    protected DaGeneric<DmVorhaben> generic;

    public DaVorhabenForJPA(EntityManager em) {
        generic = new DaGenericForJPA<>(DmVorhaben.class, em);
    }

    @Override
    public boolean save(DmVorhaben entity) {
        return generic.save(entity);
    }

    @Override
    public void delete(DmVorhaben entity) {
        try {
            generic.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DmVorhaben find(long id) throws IdNotFoundExc {
        return generic.find(id);
    }

    @Override
    public List<DmVorhaben> findAll() {
        return generic.findAll();
    }

    @Override
    public List<DmVorhaben> findByField(String fieldName, Object fieldValue) {
        return findByField(fieldName, fieldValue);
    }

    @Override
    public List<DmVorhaben> findByWhere(String whereClause, Object... args) {
        return findByWhere(whereClause, args);
    }

    @Override
    public List<DmVorhaben> findByExample(DmVorhaben example) {
        return findByExample(example);
    }
}
