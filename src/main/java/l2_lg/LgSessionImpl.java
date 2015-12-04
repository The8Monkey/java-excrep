package l2_lg;

import l3_da.DaFactory;
import l3_da.DaVorhaben;
import l4_dm.DmAufgabe;
import l4_dm.DmSchritt;

import java.util.List;

public class LgSessionImpl implements LgSession{
    final DaFactory da;

    public LgSessionImpl(DaFactory da){
        this.da = da;
    }

    public void transienteDatenFuellen(final DmAufgabe aufgabe,
                                       final List<DmAufgabe> alleAufgaben){
        int stunden=0;
        if(aufgabe.getClass()== DaVorhaben.class){
            for(DmAufgabe dAuf: alleAufgaben){
                if(dAuf.getGanzes().equals(aufgabe)){
                    stunden+=aufgabe.getRestStunden()+aufgabe.getIstStunden();
                }
            }
        }
    }

    @Override
    public <A extends DmAufgabe> A speichern(A aufgabe) throws TitelExc, RestStundenExc,
            IstStundenExc, EndTerminExc, VorhabenRekursionExc {
        if(aufgabe.getTitel().isEmpty()){
            throw new TitelExc();
        }
        if(aufgabe.getRestStunden()<0){
            throw new RestStundenExc();
        }
        if(aufgabe.getIstStunden()<0){
            throw new IstStundenExc();
        }
        return null;
    }

    @Override
    public DmSchritt schrittErledigen(DmSchritt schritt) throws TitelExc, IstStundenExc {
        return null;
    }

    @Override
    public List<DmAufgabe> alleOberstenAufgabenLiefern() {
        return null;
    }

    @Override
    public void beginTransaction() {
        da.beginTransaction();
    }

    @Override
    public void endTransaction(boolean ok) {
        da.endTransaction(ok);
    }
}
