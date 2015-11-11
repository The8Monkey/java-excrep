package l4_dm;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

/**Datenmodellklasse f�r ein Vorhaben, welches aus mehreren Aufgaben besteht. Ohne Pr�fungen und Folgeaktionen.*/
@Entity @Table(name="aufgabe")
public class DmVorhaben extends DmAufgabe {
	
	/**In der Datenbank zu speichernder End-Termin*/
	private Date endTermin;
	
	/**Enth�lt die Teil-Aufgaben, welche zu diesem Vorhaben geh�ren. 
	 * Wird nicht persistiert, sondern aus der inversen, persistierten Referenz ganzes berechnet.*/
	protected transient List<DmAufgabe> teile;
	
	/**Enth�lt die Reststunden, die voraussichtlich noch f�r dieses Vorhaben aufgewendet werden m�ssen.
	 * Wird nicht persistiert, sondern aus den Reststunden der Teil-Aufgaben berechnet.*/
	private transient int restStunden;
	
	/**Enth�lt die Iststunden, die bisher f�r dieses Vorhaben aufgewendet wurden.
	 * Wird nicht persistiert, sondern aus den Iststunden der Teil-Aufgaben berechnet.*/
	private transient int istStunden;
	
	/**Enth�lt den Status dieses Vorhabens.
	 * Wird nicht persistiert, sondern aus den Statuswerten der Teil-Aufgaben berechnet.*/
	private transient DmAufgabeStatus status;

	/**Liefert den Termin, bis zu dem diese Aufgabe erledigt werden soll.*/
	public Date getEndTermin() {
		return endTermin;
	}

	public void setEndTermin(Date endTermin) {
		this.endTermin = endTermin;
	}

	@Override
	public int getRestStunden() {
		return restStunden;
	}

	@Override
	public int getIstStunden() {
		return istStunden;
	}

	@Override
	public int getAnzahlTeile() {
		return teile==null ? 0 : teile.size();
	}

	@Override
	public DmAufgabeStatus getStatus() {
		return status;
	}
	
}
