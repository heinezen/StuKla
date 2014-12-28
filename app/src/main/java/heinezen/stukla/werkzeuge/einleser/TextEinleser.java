package heinezen.stukla.werkzeuge.einleser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Liest Text aus einer Textdatei in einen String ein. Line Seperators werden vernachlässigt.
 */
public class TextEinleser
{
    /**
     * Eingelesener Text.
     */
    private String _text;

    /**
     * Liest den Text einer Datei ein.
     *
     * @param datei Pfad der Datei die eingelesen werden soll.
     */
    public TextEinleser(String datei)
    {
        _text = "";

        try
        {
            InputStream ips = new FileInputStream(datei);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            // Erste Zeile �berspringen.
            br.readLine();
            // Inhalt einlesen
            String line = br.readLine();

            while(line != null)
            {
                _text = _text + line;
                line = br.readLine();
            }

            br.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    /**
     * Liest den Text einer Datei ein.
     *
     * @param datei Datei die eingelesen werden soll.
     *
     * @return Der eingelesene Text.
     */
    public static String getText(File datei)
    {
        String text = "";

        try
        {
            InputStream ips = new FileInputStream(datei);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            br.readLine();

            String line = br.readLine();

            while(line != null)
            {
                text = text + line;
                line = br.readLine();
            }

            br.close();

            return text;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        return text;
    }

    /**
     * Gibt den eingelesenen Text aus.
     *
     * @return Der eingelesene Text.
     */
    public String toString()
    {
        return _text;
    }
}
