package heinezen.stukla.services;

import heinezen.stukla.materialien.Frage;

/**
 * Implementiert einen Fragenservice.
 * 
 * @author Christophad
 *
 */
public class FragenServiceImpl implements FragenService
{
	/**
	 * Der Fragensatz des Services.
	 */
	private final Frage[] _fragensatz;
	
	private int _aktuellerIndex;
	
	public FragenServiceImpl(Frage[] fragensatz)
	{
		_fragensatz = mischeFragensatz(fragensatz);
		_aktuellerIndex = 0;
	}
	
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
	 * Berechnet die Punktzahl des Spielers f�r eine der Fragen 
	 * im gespeicherten Fragensatz.
	 * 
	 * @param index Index der Frage, f�r die die Punktzahl berechnet werden soll.
	 * @return Punktzahl f�r die Frage.
	 */
	private int berechnePunktzahlFuerFrage(int index)
	{
		return _fragensatz[index].vergleicheAntworten();
	}

	@Override
	public Frage zurNaechstenFrage() 
	{
		if((_aktuellerIndex + 1) < _fragensatz.length)
		{
			++_aktuellerIndex;
		}
		else
		{
			_aktuellerIndex = 0;
		}
		
		return getAktuelleFrage();
	}

	@Override
	public Frage zurVorherigenFrage() 
	{
		if((_aktuellerIndex - 1) >= 0)
		{
			--_aktuellerIndex;
		}
		else
		{
			_aktuellerIndex = _fragensatz.length - 1;
		}
		
		return getAktuelleFrage();
	}

	@Override
	public Frage zuFrage(int index)
	{
		assert index < _fragensatz.length : "Vorbedingung verletzt : index < _fragensatz.length";
		assert index >= 0 : "Vorbedingung verletzt : index >= 0";
		
		_aktuellerIndex = index;
		
		return getAktuelleFrage();
	}

	@Override
    public Frage[] mischeFragensatz(Frage[] fragensatz)
    {
		Frage[] tempFragensatz = fragensatz.clone();
	    
	    for(int i = 0; i < tempFragensatz.length; ++i)
	    {
	    	int random = (int) (Math.random() * (tempFragensatz.length - 1));
	    	Frage tempFrage = tempFragensatz[i];
	    	tempFragensatz[i] = tempFragensatz[random];
	    	tempFragensatz[random] = tempFrage;
	    }
	    
	    return tempFragensatz;
    }

	@Override
    public int getMaxPunktzahl()
    {
	    int maxPunktzahl = 0;
	    
	    for(int i = 0; i < _fragensatz.length; ++i)
	    {
	    	maxPunktzahl += _fragensatz[i].getMaxPunktzahl();
	    }
	    return maxPunktzahl;
    }

	@Override
    public Frage getAktuelleFrage()
    {
	    return _fragensatz[_aktuellerIndex];
    }
}
