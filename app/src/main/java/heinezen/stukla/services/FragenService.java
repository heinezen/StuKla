package heinezen.stukla.services;

import heinezen.stukla.materialien.Frage;

/**
 * Ein Fragenservice zum Beantworten der Fragen und Berechnen der Gesamtpunktzahl.
 *
 * @author Christophad
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
     */
    public Frage getFrage(int index);

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
