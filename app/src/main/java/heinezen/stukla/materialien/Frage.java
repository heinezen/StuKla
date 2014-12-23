package heinezen.stukla.materialien;

import android.os.Parcelable;

import heinezen.stukla.fachwerte.enums.Fragetyp;

/**
 * Stellt eine Frage bereit, die ihren Fragetext ausgeben kann, sowie
 * die Texte ihrer Antworten. Au�erdem k�nnen Antworten vom Klienten verglichen
 * werden.
 * 
 * @author Christophad
 *
 */

public interface Frage extends Parcelable
{
	/**
	 * Gibt den Fragetext f�r die Frage als String zur�ck.
	 * 
	 * @return Ein String mit dem Fragetext als Inhalt.
	 */
	public String getFragetext();
	
	/**
	 * Gibt die Antworttexte als Array von Strings zur�ck.
	 * 
	 * @return Die Strings mit Antworttexten.
	 */
	public String[] getAntworttexte();
	
	/**
	 * Gibt die Punkte f�r eine richtige Antwort aus.
	 * 
	 * @return Punkte pro richtiger Antwort.
	 */
	public int getPunkteFuerAntwort();
	
	/**
	 * Gibt die Punkte f�r eine falsche Antwort aus.
	 * 
	 * @return Abzug f�r falsche Antwort.
	 */
	public int getAbzugsPunkte();
	
	/**
	 * Vergleicht die Antworten des Spielers mit den tats�chlichen
	 * Antworten. Zur�ckgegeben wird eine Punktzahl, die die
	 * erreichten Punkte f�r die Frage darstellt.
	 * 
	 * @return Das Punktzahl f�r alle Antworten.
	 */
	public int vergleicheAntworten();
	
	/**
	 * Aktualisiert die Antworten des Spielers.
	 * 
	 * @param antwortWerte Die neue(n) Antwort(en) des Spielers.
	 */
	public void aktualisiereSpielerAntworten(Object antwortWerte);

	/**
	 * Gibt die bisherigen Antworten des Spielers zur�ck.
	 * 
	 * @return Die bisherigen Antworten des Spielers.
	 */
	public Object getSpielerAntworten();
	
	/**
	 * Gibt die richtigen Antworten der Frage zur�ck.
	 * 
	 * @return Die richtigen Antworten.
	 */
	public Object getAntwortenWerte();
	
	/**
	 * Gibt die maximal zu erreichende Punktzahl der Frage zur�ck.
	 * 
	 * @return Die maximal erreichbare Punktzahl.
	 */
	public int getMaxPunktzahl();
	
	/**
	 * Gibt den Fragetyp der Frage zur�ck.
	 * 
	 * @return Der Fragetyp
	 */
	public Fragetyp getFragetyp();
}
