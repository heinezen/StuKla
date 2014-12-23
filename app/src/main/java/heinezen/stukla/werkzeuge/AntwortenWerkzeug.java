package heinezen.stukla.werkzeuge;

import android.widget.LinearLayout;

public class AntwortenWerkzeug
{
	/**
	 * Das UI des AntwortenWerkzeugs.
	 */
	private AntwortenUI _uiAntworten;

	/**
	 * Der Fragetyp der angezeigten Frage.
	 */
	private String _aktuellerFragetyp;

	public AntwortenWerkzeug(LinearLayout antwortenBereich)
	{
		_uiAntworten = new AntwortenUI(antwortenBereich);
	}

	/**
	 * Gibt das Panel des Antwortebbereiches zur�ck.
	 *
	 * @return Der Panel des Antwortenbereichs
	 */
	public LinearLayout getPanel()
    {
	    return _uiAntworten.getAntwortenPanel();
    }

	/**
	 * Aktualisiert den Antwortenbereich. Bedienelemente passen sich dem
	 * jeweiligen Fragetyp an.
	 *
	 * @param antwortTexte Die neuen Texte der Antworten.
	 * @param antwortWerte Die neuen Werte der Antworten.
	 */
	public void aktualisiereAntworten(String[] antwortTexte, Object antwortWerte)
    {
	    _uiAntworten.aktualisiereAntworten(antwortTexte, antwortWerte, _aktuellerFragetyp);
    }

	/**
	 * Gibt die Eingaben des Spielers zur derzeitigen Antwort zur�ck.
	 * dabei wird je nach Fragetyp ein unterschiedlicher R�ckgabetyp
	 * verwendet. Beim casten ist auf den Fragetyp zu achten.
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
	public void setAktuellenFragetyp(String fragetyp)
	{
		_aktuellerFragetyp = fragetyp;
	}

	/**
	 * Setzt den Status des Antwortenbereichs auf beendet. Dadurch werden
	 * Bedienelemente im Antwortenbereich gesperrt.
	 */
	public void setzeBeendet()
	{
		_uiAntworten.setzeBeendet();
	}
}
