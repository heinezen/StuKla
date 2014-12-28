package heinezen.stukla.materialien.choice;

import android.os.Parcel;

import java.util.Random;

import heinezen.stukla.materialien.AbstractFrage;
import heinezen.stukla.materialien.Antwort;

/**
 * Definiert die grundlegenden Methoden einer Frage. Fragen m�ssen ihren Fragetext und ihre
 * Antworttexte ausgeben k�nnen. Au�erdem kann die Punktzahl einer Frage errechnet werden und ihre
 * Antworten k�nnen verglichen werden.
 *
 * @author Christophad
 */
public abstract class AbstractChoiceFrage extends AbstractFrage
{
    /**
     * Das Array mit den Antworten der Frage.
     */
    protected final Antwort[] _antwortArray;

    /**
     * Antworten des Testers.
     */
    protected boolean[] _testerAntworten;

    /**
     * Erzeugt eine Frage mit Fragetext und mehreren Multiple-Choice-Antworten.
     *
     * @param fragetext Der Fragetext.
     * @param fragepunkte Die Punkte für eine richtige Antwort.
     * @param abzugpunkte Die Punkte, die für eine falsche Antwort abgezogen werden.
     * @param antworten Die möglichen Antworten der Frage.
     */
    public AbstractChoiceFrage(String fragetext, int fragepunkte, int abzugpunkte, Antwort[] antworten)
    {
        super(fragetext, fragepunkte, abzugpunkte);

        _antwortArray = mischeAntworten(antworten);

        _testerAntworten = new boolean[_antwortArray.length];
    }

    /**
     * Mischt die Antworten, um eine zuf�llige Reihenfolge zu erhalten.
     *
     * @param antworten Die zu mischenden Antworten
     *
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
     * Gibt die Antworten des Testers als Object zurück.
     *
     * @return Die Antworten des Testers
     */
    public Object getTesterAntworten()
    {
        return _testerAntworten;
    }

    /**
     * Gibt die richtigen Antworten der Frage zurück.
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
     * Gibt die maximal zu erreichende Punktzahl der Frage zurück.
     *
     * @return Die maximal erreichbare Punktzahl.
     */
    public abstract int getMaxPunktzahl();

    /**
     * Vergleicht die Antworten des Testers mit den tatsächlichen Antworten. Zurückgegeben wird eine
     * Punktzahl, die die erreichten Punkte für die Frage darstellt.
     *
     * @return Das Punktzahl f�r alle Antworten.
     */
    public abstract int vergleicheAntworten();

    /**
     * Aktualisiert die Antworten des Testers.
     *
     * @param neueAntworten Die neue(n) Antwort(en) des Testers.
     */
    public abstract void aktualisiereTesterAntworten(Object neueAntworten);

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
