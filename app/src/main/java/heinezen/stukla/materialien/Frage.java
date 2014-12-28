package heinezen.stukla.materialien;

import android.os.Parcelable;

import heinezen.stukla.fachwerte.enums.Fragetyp;

/**
 * Stellt eine Frage bereit, die ihren Fragetext ausgeben kann, sowie die Texte ihrer Antworten.
 * Außerdem können Antworten vom Tester verglichen werden.
 *
 * @author Christophad
 */

public interface Frage extends Parcelable
{
    /**
     * Gibt den Fragetext für die Frage als String zurück.
     *
     * @return Ein String mit dem Fragetext als Inhalt.
     */
    public String getFragetext();

    /**
     * Gibt die Antworttexte als Array von Strings zurück.
     *
     * @return Die Strings mit Antworttexten.
     */
    public String[] getAntworttexte();

    /**
     * Gibt die Punkte für eine richtige Antwort aus.
     *
     * @return Punkte pro richtiger Antwort.
     */
    public int getPunkteFuerAntwort();

    /**
     * Gibt die Punkte für eine falsche Antwort aus.
     *
     * @return Abzug für falsche Antwort.
     */
    public int getAbzugsPunkte();

    /**
     * Vergleicht die Antworten des Spielers mit den tatsächlichen Antworten. Zurückgegeben wird
     * eine Punktzahl, die die erreichten Punkte für die Frage darstellt.
     *
     * @return Das Punktzahl für alle Antworten.
     */
    public int vergleicheAntworten();

    /**
     * Aktualisiert die Antworten des Testers.
     *
     * @param antwortWerte Die neue(n) Antwort(en) des Testers.
     */
    public void aktualisiereTesterAntworten(Object antwortWerte);

    /**
     * Gibt die bisherigen Antworten des Testers zurück.
     *
     * @return Die bisherigen Antworten des Testers.
     */
    public Object getTesterAntworten();

    /**
     * Gibt die richtigen Antworten der Frage zurück.
     *
     * @return Die richtigen Antworten.
     */
    public Object getAntwortenWerte();

    /**
     * Gibt die maximal zu erreichende Punktzahl der Frage zurück.
     *
     * @return Die maximal erreichbare Punktzahl.
     */
    public int getMaxPunktzahl();

    /**
     * Gibt den Fragetyp der Frage zurück.
     *
     * @return Der Fragetyp
     */
    public Fragetyp getFragetyp();
}
