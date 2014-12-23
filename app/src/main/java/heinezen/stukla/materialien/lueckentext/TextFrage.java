package heinezen.stukla.materialien.lueckentext;

import android.os.Parcel;
import android.os.Parcelable;

import heinezen.stukla.materialien.Frage;

/**
 * Eine Textfrage, die mehrere Antwortm�glichkeiten in einem Array h�lt. TextFragen werden
 * verglichen, indem die Antworten des Klienten mit den M�glichkeiten verglichen werden.
 *
 * @author Christophad
 */
public class TextFrage extends AbstractTextFrage implements Frage
{

    public TextFrage(String fragetext, int fragepunkte, int abzugpunkte, String[] antwortMoeglichkeiten)
    {
        super(fragetext, fragepunkte, abzugpunkte, antwortMoeglichkeiten);
    }

    @Override
    public int vergleicheAntworten()
    {
        int wert = 0;

        for(String moeglichkeit : _antwortMoeglichkeiten)
        {
            if(moeglichkeit.toLowerCase().equals(_spielerAntwort.toLowerCase()))
            {
                wert += getPunkteFuerAntwort();
            }
        }

        return wert;
    }

    @Override
    public void aktualisiereSpielerAntworten(Object neueAntwort)
    {
        _spielerAntwort = (String) neueAntwort;
    }

    @Override
    public String getFragetyp()
    {
        return "Text";
    }

    /**
     * Implementation von Parcelable
     */

    public static final Parcelable.Creator<TextFrage> CREATOR = new Creator<TextFrage>()
    {
        @Override
        public TextFrage createFromParcel(Parcel in)
        {
            return new TextFrage(in);
        }

        @Override
        public TextFrage[] newArray(int size)
        {
            return new TextFrage[size];
        }
    };

    private TextFrage(Parcel in)
    {
        super(in.readString(), in.readInt(), in.readInt(), in.createStringArray());
    }
}
