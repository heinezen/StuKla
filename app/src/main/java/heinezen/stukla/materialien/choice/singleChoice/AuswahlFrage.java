package heinezen.stukla.materialien.choice.singleChoice;

import android.os.Parcel;
import android.os.Parcelable;

import heinezen.stukla.fachwerte.enums.Fragetyp;
import heinezen.stukla.materialien.Frage;
import heinezen.stukla.materialien.choice.AbstractChoiceFrage;
import heinezen.stukla.materialien.choice.ChoiceAntwort;

/**
 * Eine Single-Choice-Frage, die mehrere Single-Choice-Antworten in einem Array h�lt. AuswahlFragen
 * werden verglichen, indem die Antworten des Klienten mit den tats�chlichen �ber Wahrheitswerte
 * verglichen werden.
 *
 * @author Christophad
 */
public class AuswahlFrage extends AbstractChoiceFrage implements Frage
{
    /**
     * Erzeugt eine Frage mit Fragetext und mehreren Single-Choice-Antworten.
     *
     * @param fragetext Der Fragetext.
     * @param fragepunkte Die Punkte f�r eine richtige Antwort.
     * @param antworten Die m�glichen Antworten der Frage.
     */
    public AuswahlFrage(String fragetext, int fragepunkte, int abzugpunkte,
                        ChoiceAntwort[] antworten)
    {
        super(fragetext, fragepunkte, abzugpunkte, antworten);
    }

    @Override
    public int vergleicheAntworten()
    {
        int wert = 0;

        for(int i = 0; i < _antwortArray.length; ++i)
        {
            ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i];
            if(antwort.istRichtig() && _spielerAntworten[i])
            {
                wert += getPunkteFuerAntwort();
            }
        }

        return wert;
    }

    public int getMaxPunktzahl()
    {
        return _antwortPunkte;
    }

    @Override
    public void aktualisiereSpielerAntworten(Object neueAntworten)
    {
        _spielerAntworten = (boolean[]) neueAntworten;
    }

    @Override
    public Fragetyp getFragetyp()
    {
        return Fragetyp.AUSWAHL;
    }

    /**
     * Implementation von Parcelable
     */

    public static final Parcelable.Creator<AuswahlFrage> CREATOR = new Creator<AuswahlFrage>()
    {
        @Override
        public AuswahlFrage createFromParcel(Parcel in)
        {
            return new AuswahlFrage(in);
        }

        @Override
        public AuswahlFrage[] newArray(int size)
        {
            return new AuswahlFrage[size];
        }
    };

    private AuswahlFrage(Parcel in)
    {
        super(in.readString(), in.readInt(), in.readInt(),
                ChoiceAntwort.getAntwortenAusParcelable(in.readParcelableArray(ChoiceAntwort
                        .class.getClassLoader())));
    }
}
