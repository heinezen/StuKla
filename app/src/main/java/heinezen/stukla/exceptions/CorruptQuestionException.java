package heinezen.stukla.exceptions;

public class CorruptQuestionException extends Exception
{

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    public CorruptQuestionException()
    {}
    
    public CorruptQuestionException(String fehlertext)
    {
    	super(fehlertext);
    }
}
