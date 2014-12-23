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
     * Lasst den Klienten zu einer bestimmten Frage springen.
     *
     * @param index Index der angew�hlten Frage.
     *
     * @require index < _fragensatz.length
     * @require index >= 0
     */
    public Frage getFrage(int index);

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
     * Gibt die Anzahl der enthaltenen Fragen zurück.
     *
     * @return Anzahl der Fragen im Fragenservice
     */
    public int getAnzahl();
}
