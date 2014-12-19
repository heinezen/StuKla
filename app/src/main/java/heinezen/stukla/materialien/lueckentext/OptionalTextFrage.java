package heinezen.stukla.materialien.lueckentext;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

import heinezen.stukla.materialien.OptionalFrage;

/**
 * Eine Textfrage die zus�tzlich noch Quelltext enth�lt.
 * Diese Klasse erweitert die Textfragen.
 *
 * @author Christophad
 */
public class OptionalTextFrage extends TextFrage implements OptionalFrage
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
     * Erstellt eine Text Frage, die mehrere Antwortenm�glichkeiten
     * und au�erdem Quelltext und ein Bild enth�lt.
     *
     * @param fragetext             Der Fragetext.
     * @param fragepunkte           Die Punkte f�r eine richtige Antwort.
     * @param antwortMoeglichkeiten Die Antwortm�glichkeiten der Frage.
     * @param quelltext             Der Quelltext der Frage.
     * @param bild                  Das Bild der Frage.
     * @require !quelltext.isEmpty()
     * @require bild != null
     * @require bild.exists()
     * @require bild.canRead()
     */
    public OptionalTextFrage(String fragetext, int fragepunkte, int abzugpunkte,
                             String[] antwortMoeglichkeiten, String quelltext, File bild)
    {
        super(fragetext, fragepunkte, abzugpunkte, antwortMoeglichkeiten);

        assert !quelltext.isEmpty() : "Vorbedingung verletzt : !quelltext.isEmpty()";
        assert bild != null : "Vorbedingung verletzt : bild != null";
        assert bild.exists() : "Vorbedingung verletzt : bild.exists()";
        assert bild.canRead() : "Vorbedingung verletzt : bild.canRead()";

        _quelltext = quelltext;
        _bild = bild;
    }

    /**
     * Erstellt eine Text Frage, die mehrere Antwortenm�glichkeiten
     * und au�erdem Quelltext enth�lt.
     *
     * @param fragetext             Der Fragetext.
     * @param fragepunkte           Die Punkte f�r eine richtige Antwort.
     * @param antwortMoeglichkeiten Die Antwortm�glichkeiten der Frage.
     * @param quelltext             Der Quelltext der Frage.
     * @require !quelltext.isEmpty()
     * @require bild != null
     * @require bild.exists()
     * @require bild.canRead()
     */
    public OptionalTextFrage(String fragetext, int fragepunkte, int abzugpunkte,
                             String[] antwortMoeglichkeiten, String quelltext)
    {
        super(fragetext, fragepunkte, abzugpunkte, antwortMoeglichkeiten);

        assert !quelltext.isEmpty() : "Vorbedingung verletzt : !quelltext.isEmpty()";

        _quelltext = quelltext;
        _bild = null;
    }

    /**
     * Erstellt eine Text Frage, die mehrere Antwortenm�glichkeiten
     * und au�erdem ein Bild enth�lt.
     *
     * @param fragetext             Der Fragetext.
     * @param fragepunkte           Die Punkte f�r eine richtige Antwort.
     * @param antwortMoeglichkeiten Die Antwortm�glichkeiten der Frage.
     * @param bild                  Das Bild der Frage.
     * @require !quelltext.isEmpty()
     * @require bild != null
     * @require bild.exists()
     * @require bild.canRead()
     */
    public OptionalTextFrage(String fragetext, int fragepunkte, int abzugpunkte,
                             String[] antwortMoeglichkeiten, File bild)
    {
        super(fragetext, fragepunkte, abzugpunkte, antwortMoeglichkeiten);

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
     * @return Das Bild.
     * @require hatBild()
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

    public static final Parcelable.Creator<OptionalTextFrage> CREATOR = new Creator<OptionalTextFrage>()
    {
        @Override
        public OptionalTextFrage createFromParcel(Parcel in)
        {
            return new OptionalTextFrage(in);
        }

        @Override
        public OptionalTextFrage[] newArray(int size)
        {
            return new OptionalTextFrage[size];
        }
    };

    private OptionalTextFrage(Parcel in)
    {
        super(in.readString(), in.readInt(), in.readInt(), in.createStringArray());

        _quelltext = in.readString();
        _bild = (File) in.readValue(File.class.getClassLoader());
    }
}
