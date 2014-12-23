package heinezen.stukla.materialien;

import android.os.Parcel;
import android.os.Parcelable;

import heinezen.stukla.fachwerte.enums.Fragetyp;

public abstract class AbstractFrage implements Parcelable
{
    /**
     * Der Text der Frage.
     */
    protected final String _frageText;

    /**
     * Punkte f�r richtige Antworten.
     */
    protected final int _antwortPunkte;

    /**
     * Punktabzug f�r falsche/nicht angekreuzte Antworten.
     */
    protected final int _antwortAbzug;

    /**
     * Erzeugt eine Frage mit Fragetext.
     *
     * @param fragetext   Der Fragetext.
     * @param fragepunkte Die Punkte pro richtiger Antwort.
     * @require !fragetext.isEmpty()
     * @require fragepunkte > 0
     * @require abzugpunkte >= 0
     */
    public AbstractFrage(String fragetext, int fragepunkte, int abzugpunkte)
    {
        assert !fragetext.isEmpty() : "Vorbedingung verletzt : !fragetext.isEmpty()";
        assert fragepunkte > 0 : "Vorbedingung verletzt : fragepunkte > 0";
        assert abzugpunkte >= 0 : "Vorbedingung verletzt : abzugpunkte >= 0";

        _frageText = fragetext;

        _antwortPunkte = fragepunkte;

        _antwortAbzug = abzugpunkte;
    }

    /**
     * Gibt den Text der Frage als String zur�ck.
     *
     * @return Der Fragetext.
     */
    public final String getFragetext()
    {
        return _frageText;
    }

    /**
     * Gibt die Punkte f�r eine richtige Antwort aus.
     *
     * @return Punkte pro richtiger Antwort.
     */
    public int getPunkteFuerAntwort()
    {
        return _antwortPunkte;
    }

    /**
     * Gibt die Punkte f�r eine falsche Antwort aus.
     *
     * @return Abzug f�r falsche Antwort
     */
    public int getAbzugsPunkte()
    {
        return _antwortAbzug;
    }

    /**
     * Gibt die maximal zu erreichende Punktzahl der Frage zur�ck.
     *
     * @return Die maximal erreichbare Punktzahl.
     */
    public abstract int getMaxPunktzahl();

    /**
     * Gibt den Fragetyp der Frage zur�ck.
     *
     * @return Der Fragetyp
     */
    public abstract Fragetyp getFragetyp();

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
        dest.writeString(_frageText);
        dest.writeInt(_antwortPunkte);
        dest.writeInt(_antwortAbzug);
    }
}
