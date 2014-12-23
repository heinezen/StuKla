package heinezen.stukla.materialien.choice;

import android.os.Parcel;

import java.util.Random;

import heinezen.stukla.materialien.AbstractFrage;
import heinezen.stukla.materialien.Antwort;

/**
 * Definiert die grundlegenden Methoden einer Frage. Fragen m�ssen
 * ihren Fragetext und ihre Antworttexte ausgeben k�nnen. Au�erdem
 * kann die Punktzahl einer Frage errechnet werden und ihre Antworten
 * k�nnen verglichen werden.
 * 
 * @author Christophad
 *
 */
public abstract class AbstractChoiceFrage extends AbstractFrage
{	
	/**
	 * Das Array mit den Antworten der Frage.
	 */
	protected final Antwort[] _antwortArray;
	
	/**
	 * Antworten des Spielers.
	 */
	protected boolean[] _spielerAntworten;
	
	/**
	 * Erzeugt eine Frage mit Fragetext und mehreren Multiple-Choice-Antworten.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte 
	 * @param antworten Die m�glichen Antworten der Frage.
	 */
	public AbstractChoiceFrage(String fragetext, int fragepunkte, int abzugpunkte, Antwort[] antworten) 
	{
		super(fragetext, fragepunkte, abzugpunkte);
		
		_antwortArray = mischeAntworten(antworten);
		
		_spielerAntworten = new boolean[_antwortArray.length];
	}
	
	/**
	 * Mischt die Antworten, um eine zuf�llige Reihenfolge zu erhalten.
	 * 
	 * @param antworten Die zu mischenden Antworten
	 * @return Die eingegebenen Antworten in neuer Reihenfolge
	 */
	private Antwort[] mischeAntworten(Antwort[] antworten)
    {
		Antwort[] tempAntworten = antworten.clone();
	    
	    for(int i = 0; i < tempAntworten.length; ++i)
	    {
            Random zufall = new Random();
	    	int random = zufall.nextInt(tempAntworten.length);
	    	Antwort tempAntwort = tempAntworten[i];
	    	tempAntworten[i] = tempAntworten[random];
	    	tempAntworten[random] = tempAntwort;
	    }
	    
	    return tempAntworten;
    }
	
	/**
	 * Gibt die Antworttexte als Array von Strings aus.
	 * 
	 * @return Die Antworttexte.
	 */
	public String[] getAntworttexte()
	{
		String[] antworttexte = new String[_antwortArray.length];
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			antworttexte[i] = _antwortArray[i].getAntwortText();
		}
		
		return antworttexte;
	}
	
	/**
	 * Gibt die Antworten des Spielers als Object zur�ck.
	 * 
	 * @return Die Antworten des Spielers
	 */
	public Object getSpielerAntworten()
	{
		return _spielerAntworten;
	}
	
	/**
	 * Gibt die richtigen Antworten der Frage zur�ck.
	 * 
	 * @return Die richtigen Antworten.
	 */
	public Object getAntwortenWerte()
	{
		boolean[] antwortWerte = new boolean[_antwortArray.length];
		
		for(int i = 0; i < antwortWerte.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i]; 
			antwortWerte[i] = antwort.istRichtig();
		}
		
		return antwortWerte;
	}
	
	/**
	 * Gibt die maximal zu erreichende Punktzahl der Frage zur�ck.
	 * 
	 * @return Die maximal erreichbare Punktzahl.
	 */
	public abstract int getMaxPunktzahl();
	
	/**
	 * Vergleicht die Antworten des Spielers mit den tats�chlichen
	 * Antworten. Zur�ckgegeben wird eine Punktzahl, die die
	 * erreichten Punkte f�r die Frage darstellt.
	 * 
	 * @return Das Punktzahl f�r alle Antworten.
	 */
	public abstract int vergleicheAntworten();
	
	/**
	 * Aktualisiert die Antworten des Spielers.
	 * 
	 * @param neueAntworten Die neue(n) Antwort(en) des Spielers.
	 */
	public abstract void aktualisiereSpielerAntworten(Object neueAntworten);

    /**
     * Implementation von Parcelable
     */

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        super.writeToParcel(dest, flags);

        dest.writeParcelableArray((ChoiceAntwort[]) _antwortArray, 0);
    }
}
