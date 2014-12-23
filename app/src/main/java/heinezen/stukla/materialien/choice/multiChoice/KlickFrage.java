package heinezen.stukla.materialien.choice.multiChoice;

import android.os.Parcel;
import android.os.Parcelable;

import heinezen.stukla.fachwerte.enums.Fragetyp;
import heinezen.stukla.materialien.Frage;
import heinezen.stukla.materialien.choice.AbstractChoiceFrage;
import heinezen.stukla.materialien.choice.ChoiceAntwort;

/**
 * Eine Multiple-Choice-Frage, die mehrere Multiple-Choice-Antworten in
 * einem Array h�lt. KlickFragen werden verglichen, indem die Antworten
 * des Klienten mit den tats�chlichen �ber Wahrheitswerte verglichen 
 * werden.
 * 
 * @author Christophad
 *
 */
public class KlickFrage extends AbstractChoiceFrage implements Frage
{
	/**
	 * Erzeugt eine Frage mit Fragetext und mehreren Multiple-Choice-Antworten.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die m�glichen Antworten der Frage.
	 */
	public KlickFrage(String fragetext, int fragepunkte, int abzugpunkte, ChoiceAntwort[] antworten) 
	{
		super(fragetext, fragepunkte, abzugpunkte, antworten);
	}
	
	public int vergleicheAntworten()
	{
		boolean[] vergleich = new boolean[_antwortArray.length];
		
		int wert = 0;
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i];
			if(antwort.istRichtig() == _spielerAntworten[i])
			{
				vergleich[i] = true;
			}
			
			if(!vergleich[i])
			{
				wert -= getPunkteFuerAntwort();
			}
			else if(vergleich[i] && antwort.istRichtig())
			{
				wert += getPunkteFuerAntwort();
			}
		}
		
		if(wert < 0)
		{
			wert = 0;
		}
		
		return wert;
	}
	
	public int getMaxPunktzahl()
	{
		int wert = 0;
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i];
			if(antwort.istRichtig())
			{
				wert += _antwortPunkte;
			}
		}
		
		return wert;
	}

	@Override
	public void aktualisiereSpielerAntworten(Object antwortWerte) 
	{
		_spielerAntworten = (boolean[]) antwortWerte;
	}

	@Override
    public Fragetyp getFragetyp()
    {
	    return Fragetyp.KLICK;
    }

    /**
     * Implementation von Parcelable
     */

    public static final Parcelable.Creator<KlickFrage> CREATOR = new Creator<KlickFrage>()
    {
        @Override
        public KlickFrage createFromParcel(Parcel in)
        {
            return new KlickFrage(in);
        }

        @Override
        public KlickFrage[] newArray(int size)
        {
            return new KlickFrage[size];
        }
    };

    private KlickFrage(Parcel in)
    {
        super(in.readString(), in.readInt(), in.readInt(),
                ChoiceAntwort.getAntwortenAusParcelable(in.readParcelableArray(ChoiceAntwort
                        .class.getClassLoader())));
    }
}
