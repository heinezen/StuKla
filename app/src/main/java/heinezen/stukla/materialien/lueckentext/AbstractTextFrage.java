package heinezen.stukla.materialien.lueckentext;

import android.os.Parcel;

import heinezen.stukla.materialien.AbstractFrage;

/**
 * Definiert die grundlegenden Methoden einer Frage. Fragen m�ssen ihren Fragetext und ihre
 * Antworttexte ausgeben k�nnen. Au�erdem kann die Punktzahl einer Frage errechnet werden und ihre
 * Antworten k�nnen verglichen werden.
 *
 * @author Christophad
 */
public abstract class AbstractTextFrage extends AbstractFrage
{
    /**
     * Das Array mit den Antworten der Frage.
     */
    protected final String[] _antwortMoeglichkeiten;

    /**
     * Antworten des Spielers.
     */
    protected String _spielerAntwort;

    /**
     * Erzeugt eine Frage mit Fragetext und mehreren Multiple-Choice-Antworten.
     *
     * @param fragetext Der Fragetext.
     * @param fragepunkte
     * @param antwortMoeglichkeiten Die möglichen Antworten der Frage.
     */
    public AbstractTextFrage(String fragetext, int fragepunkte, int abzugpunkte, String[] antwortMoeglichkeiten)
    {
        super(fragetext, fragepunkte, abzugpunkte);

        _antwortMoeglichkeiten = antwortMoeglichkeiten;

        _spielerAntwort = "";
    }

    /**
     * Gibt die Antworttexte als Array von Strings aus.
     *
     * @return Die Antworttexte.
     */
    public String[] getAntworttexte()
    {
        return _antwortMoeglichkeiten;
    }

    /**
     * Gibt die maximal zu erreichende Punktzahl der Frage zur�ck.
     *
     * @return Die maximal erreichbare Punktzahl.
     */
    public int getMaxPunktzahl()
    {
        return _antwortPunkte;
    }

    /**
     * Gibt die Antworten des Testers als Object zurück.
     *
     * @return Die Antworten des Testers
     */
    public Object getTesterAntworten()
    {
        return _spielerAntwort;
    }

    /**
     * Gibt die richtigen Antworten der Frage zur�ck.
     *
     * @return Die richtigen Antworten.
     */
    public Object getAntwortenWerte()
    {
        return _antwortMoeglichkeiten;
    }

    /**
     * Vergleicht die Antworten des Spielers mit den tats�chlichen Antworten. Zur�ckgegeben wird
     * eine Punktzahl, die die erreichten Punkte f�r die Frage darstellt.
     *
     * @return Das Punktzahl f�r alle Antworten.
     */
    public abstract int vergleicheAntworten();

    /**
     * Aktualisiert die Antworten des Testers.
     *
     * @param neueAntwort Die neue(n) Antwort(en) des Testers.
     */
    public abstract void aktualisiereTesterAntworten(Object neueAntwort);

    /**
     * Implementation von Parcelable
     */

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        super.writeToParcel(dest, flags);

        dest.writeStringArray(_antwortMoeglichkeiten);
    }
}
