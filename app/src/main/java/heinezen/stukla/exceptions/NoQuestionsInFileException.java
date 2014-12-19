package heinezen.stukla.exceptions;

public class NoQuestionsInFileException extends Exception
{
	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public NoQuestionsInFileException()
    {
        super("Keine Fragen in Datei gefunden.");
    }
}
