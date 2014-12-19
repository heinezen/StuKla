package heinezen.stukla.materialien.choice;

import android.os.Parcel;
import android.os.Parcelable;

import heinezen.stukla.materialien.AbstractAntwort;
import heinezen.stukla.materialien.Antwort;

/**
 * Eine Antwort, die von Multiple- oder Single-Choice-Fragen
 * benutzt werden kann. ChoiceAntworten haben einen Wahrheitswert,
 * der angibt ob diese wahr oder falsch sind.
 * 
 * @author Christophad
 *
 */
public class ChoiceAntwort extends AbstractAntwort implements Antwort,Parcelable
{
	/**
	 * Wahrheitswert der Antwort.
	 */
	private final boolean _richtig;
	
	/**
	 * Erzeugt eine Antwort mit gegebenem Text und Wahrheitswert. 
	 * Au�erdem werden zu der Antwort ihre Punkte gespeichert.
	 * 
	 * @param antwortText Der Text der Antwort.
	 * @param antwortWert Der Wahrheitswert der Antwort.
	 */
	public ChoiceAntwort(String antwortText, boolean antwortWert) 
	{
		super(antwortText);

		_richtig = antwortWert;
	}
	
	/**
	 * Gibt zur�ck ob die Antwort wahr oder falsch ist.
	 * 
	 * @return Der Wahrheitswert der Antwort.
	 */
	public boolean istRichtig()
	{
		return _richtig;
	}

    /**
     * Implementation von Parcelable
     */

    @Override
    public int describeContents()
    {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(_antwortText);
        dest.writeByte((byte) (_richtig ? 1 : 0));
    }

    public static final Parcelable.Creator<ChoiceAntwort> CREATOR = new Creator<ChoiceAntwort>()
    {
        @Override
        public ChoiceAntwort createFromParcel(Parcel in)
        {
            return new ChoiceAntwort(in);
        }

        @Override
        public ChoiceAntwort[] newArray(int size)
        {
            return new ChoiceAntwort[size];
        }
    };

    private ChoiceAntwort(Parcel in)
    {
        super(in.readString());

        _richtig = in.readByte() != 0;
    }

    public static ChoiceAntwort[] getAntwortenAusParcelable(Parcelable[] parcAntworten)
    {
        ChoiceAntwort[] antworten = new ChoiceAntwort[parcAntworten.length];
        System.arraycopy(parcAntworten, 0, antworten, 0, parcAntworten.length);

        return antworten;
    }
}