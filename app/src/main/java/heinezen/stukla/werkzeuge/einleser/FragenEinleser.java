package heinezen.stukla.werkzeuge.einleser;

import java.util.LinkedList;
import java.util.List;

import heinezen.stukla.exceptions.CorruptQuestionException;
import heinezen.stukla.exceptions.NoQuestionsInFileException;
import heinezen.stukla.materialien.Frage;
import heinezen.stukla.materialien.format.FragenFormattierer;
import heinezen.stukla.materialien.format.UebersichtFormattierer;

/**
 * Zerteilt den vom TextEinleser eingelesenen Text in Blöcke mit denen dann Fragen und Antworten
 * erzeugt werden. Gespeichert wird das Ergebnis in einer (Fragen-)Liste. Die Fragen und Antworten
 * enstehen indem der String aus dem Texteinleser gesplittet wird. Dazu benutzt diese Klasse den
 * FragenFormattierer.
 */
public class FragenEinleser
{
    /**
     * Unformattierter Text mit allen Fragen.
     */
    private String _rohText;

    /**
     * Liste der im Text enthaltenen Fragen.
     */
    private List<Frage> _fragenliste;

    /**
     * Übersicht der Testdatei.
     */
    private String[] _uebersicht;

    /**
     * Erstellt einen neuen Fragenzerteiler der die Textdatei aus dem String datei einliest.
     *
     * @param datei Datei die ausgelesen werden soll.
     */
    public FragenEinleser(String datei)
    {
        TextEinleser _texteinleser = new TextEinleser(datei);
        _rohText = _texteinleser.toString();

        _fragenliste = new LinkedList<>();

        liesFragenEin(datei);
        liesUebersichtEin();
    }

    /**
     * Zerteilt den unformattitierten Text aus dem Quelldokument in einzelne Fragen.
     *
     * @param datei Die Datei aus der ausgelesen wird.
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
            catch(CorruptQuestionException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Zerteilt den unformattierten Text aus dem Quelldokument in eine Übersicht.
     */
    private void liesUebersichtEin()
    {
        String[] rohFragen = _rohText.split(FragenFormattierer.FRAGEN_TRENNER);
        String rohUeberblick = rohFragen[0];

        _uebersicht = UebersichtFormattierer.parseUeberblick(rohUeberblick);
    }

    /**
     * Gibt die eingelesenen Fragen aus.
     *
     * @return Liste mit aus dem Textdokument eingelesenen Fragen.
     *
     * @throws NoQuestionsInFileException wenn keine Fragen vorhanden sind.
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
     * Gibt die eingelesene Übersicht aus.
     *
     * @return Die aus dem Textdokument eingelesene Übersicht.
     *
     * @throws NoQuestionsInFileException wenn Übersicht zu kurz.
     */
    public String[] gibUebersichtAus() throws NoQuestionsInFileException
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
}
