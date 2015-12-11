package l2_lg;

import l3_da.DaAufgabe;
import l3_da.DaFactory;
import l3_da.DaSchritt;
import l3_da.DaVorhaben;
import l4_dm.DmAufgabe;
import l4_dm.DmSchritt;
import l4_dm.DmVorhaben;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;



public class LgSessionImpl implements LgSession{
    final DaFactory dF;
    final DaAufgabe dA;
    final DaVorhaben dV;
    final DaSchritt dS;

    public LgSessionImpl(DaFactory dF){
        this.dF = dF;
        dA = dF.getAufgabeDA();
        dV = dF.getVorhabenDA();
        dS = dF.getSchrittDA();
    }

    public void transienteDatenFuellen(final DmAufgabe aufgabe,
                                       final List<DmAufgabe> alleAufgaben){
        int stunden=0;
        if(aufgabe instanceof DaVorhaben){
            for(DmAufgabe dAuf: alleAufgaben){
                if(dAuf.getGanzes().equals(aufgabe)){
                    //getrennt addieren
                    stunden+=aufgabe.getRestStunden()+aufgabe.getIstStunden();
                }
            }
        }
    }

    @Override
    public <A extends DmAufgabe> A speichern(A aufgabe) throws TitelExc, RestStundenExc,
            IstStundenExc, EndTerminExc, VorhabenRekursionExc {
        if(aufgabe.getTitel().isEmpty()){
            throw multex.MultexUtil.create(TitelExc.class,aufgabe.getTitel().length(),aufgabe.getTitel());
        }
        if(aufgabe.getRestStunden()<0){
            throw new RestStundenExc();
        }
        if(aufgabe.getIstStunden()<0){
            throw new IstStundenExc();
        }
        if(aufgabe instanceof DmVorhaben){
            if(((DmVorhaben) aufgabe).getEndTermin()==null){
                throw new EndTerminExc();
            }
        }
        //VorhabenRekusionExc ??? ka was das soll
        dF.getAufgabeDA().save(aufgabe);
        return aufgabe;
    }

    @Override
    public DmSchritt schrittErledigen(DmSchritt schritt) throws TitelExc, IstStundenExc {
        schritt.setRestStunden(0);
        schritt.setErledigtZeitpunkt(new Date(Calendar.getInstance().getTimeInMillis()));
        this.speichern(schritt);
        return schritt;
    }

    @Override
    public List<DmAufgabe> alleOberstenAufgabenLiefern() {
        List<DmAufgabe> list = dA.findAll();
        List<DmAufgabe> oberste = null;
        for(DmAufgabe dO: list) {
            if (dO.getGanzes() == null) {
                oberste.add(dO);
            }
        }
        //transientedatenn füllen
        return oberste;
    }

    @Override
    public void beginTransaction() {
        dF.beginTransaction();
    }

    @Override
    public void endTransaction(boolean ok) {
        dF.endTransaction(ok);
    }
}
