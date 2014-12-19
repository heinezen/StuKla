package heinezen.stukla.materialien;

/**
 * Definiert die grundlegenden Methoden einer ChoiceAntwort. Antworten m�ssen
 * sowohl ihren Antworttext als auch ihre Punktzahl zur�ckgeben k�nnen.
 * 
 * @author Christophad
 *
 */
public abstract class AbstractAntwort 
{
	/**
	 * Der Text der Antwort.
	 */
	protected final String _antwortText;
	
	/**
	 * Erzeugt eine Antwort mit gegebenem Text und Wahrheitswert. Au�erdem
	 * werden zu der Antwort ihre Punkte gespeichert.
	 * 
	 * @param antwortText Der Text der Antwort.
	 * 
	 * @require !antwortText.isEmpty()
	 */
	public AbstractAntwort(String antwortText) 
	{
		assert !antwortText.isEmpty() : "Vorbedingung verletzt : !antwortText.isEmpty()";
		
		_antwortText = antwortText;
	}
	
	/**
	 * Gibt den Text der Antwort als String zur�ck.
	 * 
	 * @return String mit dem Antworttext.
	 */
	public final String getAntwortText() 
	{
		return _antwortText;
	}
}
