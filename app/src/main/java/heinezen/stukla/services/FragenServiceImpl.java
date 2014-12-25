package heinezen.stukla.services;

import java.util.Random;

import heinezen.stukla.materialien.Frage;

/**
 * Implementiert einen Fragenservice.
 *
 * @author Christophad
 */
public class FragenServiceImpl implements FragenService
{
    /**
     * Der Fragensatz des Services.
     */
    private final Frage[] _fragensatz;

    public FragenServiceImpl(Frage[] fragensatz, boolean mischen)
    {
        if(mischen)
        {
            _fragensatz = mischeFragensatz(fragensatz);
        }
        else
        {
            _fragensatz = fragensatz;
        }
    }

    private Frage[] mischeFragensatz(Frage[] fragensatz)
    {
        Frage[] tempFragensatz = fragensatz.clone();

        for(int i = 0; i < tempFragensatz.length; ++i)
        {
            Random zufall = new Random();
            int random = zufall.nextInt(tempFragensatz.length);
            Frage tempFrage = tempFragensatz[i];
            tempFragensatz[i] = tempFragensatz[random];
            tempFragensatz[random] = tempFrage;
        }

        return tempFragensatz;
    }

    /**
     * Berechnet die Gesamtpunktzahl des Beantworters für alle Fragen.
     *
     * @return Endpunktzahl des Tests.
     */
    public int berechneGesamtpunktzahl()
    {
        int gesamtpunktzahl = 0;

        for(int i = 0; i < _fragensatz.length; ++i)
        {
            gesamtpunktzahl += berechnePunktzahlFuerFrage(i);
        }

        return gesamtpunktzahl;
    }

    /**
     * Berechnet die Punktzahl des Spielers f�r eine der Fragen im gespeicherten Fragensatz.
     *
     * @param index Index der Frage, f�r die die Punktzahl berechnet werden soll.
     *
     * @return Punktzahl f�r die Frage.
     */
    private int berechnePunktzahlFuerFrage(int index)
    {
        return _fragensatz[index].vergleicheAntworten();
    }

    @Override
    public Frage getFrage(int index)
    {
        assert index < _fragensatz.length : "Vorbedingung verletzt : index < _fragensatz.length";
        assert index >= 0 : "Vorbedingung verletzt : index >= 0";

        return _fragensatz[index];
    }

    @Override
    public int getMaxPunktzahl()
    {
        int maxPunktzahl = 0;

        for(Frage frage : _fragensatz)
        {
            maxPunktzahl += frage.getMaxPunktzahl();
        }
        return maxPunktzahl;
    }

    @Override
    public int getAnzahl()
    {
        return _fragensatz.length;
    }
}
