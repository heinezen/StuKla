package heinezen.stukla.werkzeuge;

import android.widget.LinearLayout;

import heinezen.stukla.fachwerte.enums.Fragetyp;

/**
 *
 */
public class AntwortenWerkzeug
{
    /**
     * Das UI des AntwortenWerkzeugs.
     */
    private AntwortenUI _uiAntworten;

    /**
     * Der Fragetyp der angezeigten Frage.
     */
    private Fragetyp _aktuellerFragetyp;

    /**
     * Erzeugt ein Antwortenwerkzeug für den gegebenen Antwortenbereich.
     *
     * @param antwortenBereich Der Antwortenbereich der genutzt werden soll.
     */
    public AntwortenWerkzeug(LinearLayout antwortenBereich)
    {
        _uiAntworten = new AntwortenUI(antwortenBereich);
    }

    /**
     * Aktualisiert den Antwortenbereich. Bedienelemente passen sich dem jeweiligen Fragetyp an.
     * Bei Beendigung des Tests werden alle Antworten für Interaktion deaktiviert und farbig nach
     * ihrer Richtigkeit markiert.
     *
     * @param antwortTexte Die Texte der Antworten.
     * @param antwortenWerte Die tatsächlichen Werte der Antworten.
     * @param testAntwortenWerte Die im Test erhaltenen Werte der Antworten.
     */
    public void aktualisiereAntworten(String[] antwortTexte, Object antwortenWerte, Object testAntwortenWerte)
    {
        _uiAntworten.aktualisiereAntworten(antwortTexte, antwortenWerte,
                testAntwortenWerte, _aktuellerFragetyp);
    }

    /**
     * Gibt die Eingaben des Spielers zur derzeitigen Antwort zurück. dabei wird je nach Fragetyp
     * ein unterschiedlicher Rückgabetyp verwendet. Bei Umwandlung ist auf den Fragetyp zu achten.
     *
     * @return Ein Object mit den derzeitigen Antwortwerten.
     */
    public Object getEingaben()
    {
        return _uiAntworten.getEingaben(_aktuellerFragetyp);
    }

    /**
     * Setzt den aktuellen Fragetyp.
     *
     * @param fragetyp Der Fragetyp als String.
     */
    public void setAktuellenFragetyp(Fragetyp fragetyp)
    {
        _aktuellerFragetyp = fragetyp;
    }

    /**
     * Setzt den Status des Antwortenbereichs auf beendet. Dadurch werden Bedienelemente im
     * Antwortenbereich gesperrt.
     */
    public void setzeBeendet()
    {
        _uiAntworten.setzeBeendet();
    }
}
