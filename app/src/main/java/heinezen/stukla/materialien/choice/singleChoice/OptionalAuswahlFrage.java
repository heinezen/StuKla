package heinezen.stukla.materialien.choice.singleChoice;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

import heinezen.stukla.materialien.OptionalFrage;
import heinezen.stukla.materialien.choice.ChoiceAntwort;

/**
 * Eine Single-Choice Frage die zus�tzlich noch Quelltext und/oder
 * ein Bild enth�lt.
 * Diese Klasse erweitert die Single-Choice Fragen.
 * 
 * @author Christophad
 */
public class OptionalAuswahlFrage extends AuswahlFrage implements OptionalFrage
{
	/**
	 * Der Quelltext der Frage.
	 */
	private final String _quelltext;
	
	/**
	 * Das Bild der Frage.
	 */
	private final File _bild;
	
	/**
	 * Erstellt eine Single-Choice Frage, die mehrere Single-Choice Antworten
	 * und au�erdem Quelltext und ein Bild enth�lt.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die Antworten der Frage.
	 * @param quelltext Der Quelltext der Frage.
	 * @param bild Das Bild der Frage.
	 * 
	 * @require !quelltext.isEmpty()
	 * @require bild != null
	 * @require bild.exists()
	 * @require bild.canRead()
	 */
	public OptionalAuswahlFrage(String fragetext, int fragepunkte, int abzugpunkte,
            ChoiceAntwort[] antworten, String quelltext, File bild)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antworten);
	    
	    assert !quelltext.isEmpty() : "Vorbedingung verletzt : !quelltext.isEmpty()";
	    assert bild != null : "Vorbedingung verletzt : bild != null";
	    assert bild.exists() : "Vorbedingung verletzt : bild.exists()";
	    assert bild.canRead() : "Vorbedingung verletzt : bild.canRead()";
	    
	    _quelltext = quelltext;
	    _bild = bild;
    }
	
	/**
	 * Erstellt eine Single-Choice Frage, die mehrere Single-Choice Antworten
	 * und au�erdem Quelltext enth�lt.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die Antworten der Frage.
	 * @param quelltext Der Quelltext der Frage.
	 * 
	 * @require !quelltext.isEmpty()
	 */
	public OptionalAuswahlFrage(String fragetext, int fragepunkte, int abzugpunkte,
            ChoiceAntwort[] antworten, String quelltext)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antworten);
	    
	    assert !quelltext.isEmpty() : "Vorbedingung verletzt : !quelltext.isEmpty()";
	    
	    _quelltext = quelltext;
	    _bild = null;
    }
	
	/**
	 * Erstellt eine Single-Choice Frage, die mehrere Single-Choice Antworten
	 * und au�erdem ein Bild enth�lt.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die Antworten der Frage.
	 * @param bild Das Bild der Frage.
	 * 
	 * @require bild != null
	 * @require bild.exists()
	 * @require bild.canRead()
	 */
	public OptionalAuswahlFrage(String fragetext, int fragepunkte, int abzugpunkte,
            ChoiceAntwort[] antworten, File bild)
    {
	    super(fragetext, fragepunkte, abzugpunkte, antworten);
	    
	    assert bild != null : "Vorbedingung verletzt : bild != null";
	    assert bild.exists() : "Vorbedingung verletzt : bild.exists()";
	    assert bild.canRead() : "Vorbedingung verletzt : bild.canRead()";
	    
	    _quelltext = "";
	    _bild = bild;
    }

	/**
	 * Gibt den Quelltext der Frage aus.
	 * 
	 * @return Der Quelltext.
	 */
	public String getQuelltext()
	{
		return _quelltext;
	}
	
	/**
	 * Gibt das Bild der Frage aus.
	 * 
	 * @require hatBild()
	 * 
	 * @return Das Bild.
	 */
	public File getBild()
	{
		assert hatBild() : "Vorbedingung verletzt : hatBild()";
		
		return _bild;
	}

	/**
	 * Gibt zur�ck, ob die Frage Quelltext enth�lt.
	 * 
	 * @return true, falls Quelltext vorhanden ist.
	 */
	public boolean hatQuelltext()
    {
	    return !_quelltext.isEmpty();
    }
	
	/**
	 * Gibt zur�ck, ob die Frage ein Bild enth�lt.
	 * 
	 * @return true, falls ein Bild vorhanden ist.
	 */
	public boolean hatBild()
	{
		return _bild != null;
	}

    /**
     * Implementation von Parcelable
     */

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        super.writeToParcel(dest, flags);

        dest.writeString(_quelltext);
        dest.writeValue(_bild);
    }

    public static final Parcelable.Creator<OptionalAuswahlFrage> CREATOR = new
            Creator<OptionalAuswahlFrage>()
    {
        @Override
        public OptionalAuswahlFrage createFromParcel(Parcel in)
        {
            return new OptionalAuswahlFrage(in);
        }

        @Override
        public OptionalAuswahlFrage[] newArray(int size)
        {
            return new OptionalAuswahlFrage[size];
        }
    };

    private OptionalAuswahlFrage(Parcel in)
    {
        super(in.readString(), in.readInt(), in.readInt(),
                ChoiceAntwort.getAntwortenAusParcelable(in.readParcelableArray(ChoiceAntwort
                        .class.getClassLoader())));

        _quelltext = in.readString();
        _bild = (File) in.readValue(File.class.getClassLoader());
    }
}
