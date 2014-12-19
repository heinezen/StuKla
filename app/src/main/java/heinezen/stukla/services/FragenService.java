package heinezen.stukla.services;

import heinezen.stukla.materialien.Frage;

/**
 * Ein Fragenservice zum Beantworten der Fragen und Berechnen der
 * Gesamtpunktzahl.
 * 
 * @author Christophad
 *
 */
public interface FragenService 
{	
	/**
	 * Berechnet die Gesamtpunktzahl f�r alle Fragen.
	 * 
	 * @return Erreichte Gesamtpunktzahl.
	 */
	public int berechneGesamtpunktzahl();

	/**
	 * L�sst den Klienten zur n�chsten Frage springen. Falls die letzte Frage
	 * schon erreicht ist, wird die erste Frage zur aktuellen Frage.
	 * 
	 */
	public Frage zurNaechstenFrage();

	/**
	 * L�sst den Klienten zur vorherigen Frage springen. Falls die erste Frage
	 * schon erreicht ist, wird die letzte Frage zur aktuellen Frage.
	 * 
	 */
	public Frage zurVorherigenFrage();
	
	/**
	 * Lasst den Klienten zu einer bestimmten Frage springen.
	 * 
	 * @param index Index der angew�hlten Frage.
	 * 
	 * @require index < _fragensatz.length
	 * @require index >= 0
	 */
	public Frage zuFrage(int index);
	
	/**
	 * Bringt die Fragen aus dem Fragensatz in eine zuf�llige
	 * Reihenfolge.
	 */
	public Frage[] mischeFragensatz(Frage[] fragensatz);
	
	/**
	 * Gibt die maximale Punktzahl f�r alle Fragen zur�ck.
	 * 
	 * @return Maximal erreichbare Punktzahl aller Fragen.
	 */
	public int getMaxPunktzahl();
	
	/**
	 * Gibt die aktuelle Frage zur�ck.
	 * 
	 * @return Die aktuelle Frage im Fragenservice
	 */
	public Frage getAktuelleFrage();
}
