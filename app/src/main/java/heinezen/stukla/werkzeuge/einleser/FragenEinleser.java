package heinezen.stukla.werkzeuge.einleser;

import java.util.LinkedList;
import java.util.List;

import heinezen.stukla.exceptions.CorruptQuestionException;
import heinezen.stukla.exceptions.NoQuestionsInFileException;
import heinezen.stukla.materialien.Frage;
import heinezen.stukla.materialien.format.FragenFormattierer;
import heinezen.stukla.materialien.format.UebersichtFormattierer;

/**
 * Zerteilt den vom TextEinleser eingelesenen Text in Bl�cke mit denen dann Fragen und Antworten
 * erzeugt werden. Gespeichert wird das Ergebnis in einer (Fragen-)Liste. Die Fragen und Antworten
 * enstehen indem der String aus dem Texteinleser gesplittet wird. Dies geschieht nach folgenden
 * Regeln:
 * 
 * "<<##>>" am Ende eines Fragenblockes.
 * "::" am Ende eines Antwortenblockes.
 * "<<QQ>>" am Ende eines Quelltextblockes.
 * "<<!!>>" am Ende eines Antwortenwertes.
 * 
 * Die Bl�cke enthalten Frage und Antwortenwerte in Rohform.
 * 
 * @Heine 
 * @03/03/14
 */
public class FragenEinleser
{	
    private TextEinleser _texteinleser;
    private String _rohText;
    
    private List<Frage> _fragenliste;
    private String[] _uebersicht;
    
    /**
     * Erstellt einen neuen Fragenzerteiler der die Textdatei aus dem String datei einliest.
     */
    public FragenEinleser(String datei)
    {
        _texteinleser = new TextEinleser(datei);
        _rohText = _texteinleser.toString();
        
        _fragenliste = new LinkedList<Frage>();
        
        liesFragenEin(datei);
        liesUebersichtEin();
    }
    
    /**
     * @return Die aus dem Textdokument eingelesenen Fragen und Antworten
     * 
     * @throws NoQuestionsInFileException
     */
    public Frage[] gibFragenAus() throws NoQuestionsInFileException
    {
    	if(!_fragenliste.isEmpty())
    	{	
    		Frage[] fragen = new Frage[_fragenliste.size()];
            
            for(int i = 0; i < _fragenliste.size(); ++i)
            {
            	fragen[i] = (Frage) _fragenliste.toArray()[i];
            }
            
            return fragen;
    	}
    	else
    	{
    		throw new NoQuestionsInFileException();
    	}
    }

    /**
     * @return Die aus dem Textdokument eingelesene Übersicht.
     *
     * @throws NoQuestionsInFileException
     */
    public String[] gibUeberblickAus() throws NoQuestionsInFileException
    {
        if(_uebersicht.length == 4)
        {
            return _uebersicht;
        }
        else
        {
            throw new NoQuestionsInFileException();
        }
    }

    /**
     * Zerteilt den rohen Text aus dem Quelldokument in eine Übersicht.
     */
    private void liesUebersichtEin()
    {
        String[] rohFragen = _rohText.split(FragenFormattierer.FRAGEN_TRENNER);
        String rohUeberblick = rohFragen[0];

        _uebersicht = UebersichtFormattierer.parseUeberblick(rohUeberblick);
    }
    
    /**
     * Zerteilt den rohen Text aus dem Quelldokument in einzelne Fragebl�cke.
     * Die Methode liesFrageEin gibt eine Frage zur�ck, die der Frageliste hinzugef�gt wird.
     * 
     * Wichtig: Der erste String des Array rohFragen ist immer der Header der Textdatei, also
     * irrelevant f�r das Auslesen.
     */
    private void liesFragenEin(String datei)
    {
        String[] rohFragen = _rohText.split(FragenFormattierer.FRAGEN_TRENNER);
        
        for(int i = 1; i < rohFragen.length; ++i)
        {
            try
            {
            	Frage frage = FragenFormattierer.parseFrage(rohFragen[i], datei);
            	
	            _fragenliste.add(frage);
            }
            catch (CorruptQuestionException e)
            {
                e.printStackTrace();
            }
        }
    }
}
