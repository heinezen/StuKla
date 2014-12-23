package heinezen.stukla.werkzeuge.einleser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Liest Text aus einer Text-Datei in einen String ein. \n wird vernachl�ssigt, sodass eine
 * Formatierung in gew�nschter Form mit \n m�glich ist.
 */
public class TextEinleser
{
    private String _text;
    private String _titel;

    public TextEinleser(String datei)
    {
        _text = "";
        _titel = "";

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

    public String getTitel()
    {
        return _titel;
    }

    public String toString()
    {
        return _text;
    }

    public static String getText(File datei)
    {
        String text = "";

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

    public static String getTitel(File datei)
    {
        return datei.getName();
    }
}
