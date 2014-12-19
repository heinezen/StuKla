package heinezen.stukla.materialien;

/**
 * Eine Antwort h�lt ihren Antworttext und ihre Punktzahl zur�ckgeben.
 * Antworten werden zu ihren Fragen gespeichert, d.h. jede Frage greift auf
 * mehrere Antworten zu.
 * 
 * @author Christophad
 *
 */
public interface Antwort 
{
	/**
	 * Gibt den Text der Antwort als String zur�ck.
	 * 
	 * @return String mit dem Antworttext.
	 */
	public String getAntwortText();
}
