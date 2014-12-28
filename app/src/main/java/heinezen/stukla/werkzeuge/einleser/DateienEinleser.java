package heinezen.stukla.werkzeuge.einleser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Der DateienEinleser findet alle relevanten Dateien für das Testprogramm. Dabei durchsucht er
 * Ordner nach Dateien und überprüft ob sie Testdateien sind.
 */
public class DateienEinleser
{
    /**
     * Der Ordner in dem gesucht wird.
     */
    private final File ORDNER;

    /**
     * Kennzeichnung für Testdateien.
     */
    private final String KENNZEICHNUNG = "###STUKLA###";

    /**
     * Liste mit den Pfaden zu den akzeptierten Dateien.
     */
    private List<String> _testDateien;

    /**
     * Erzeugt einen DateienEinleser der in einem bestimmten Ordner einliest.
     *
     * @param ordner Der Ordner in dem gesucht werden soll.
     */
    public DateienEinleser(File ordner)
    {
        ORDNER = ordner;

        _testDateien = new ArrayList<String>();

        getAlleDateienInOrdner(ORDNER);

        for(String s : _testDateien)
        {
            System.out.println(s);
        }
    }

    /**
     * Sucht alle Dateien die keine Ordner sind im angegebenen Ordner.
     *
     * @param ordner Der Ordner in dem gesucht werden soll.
     */
    private void getAlleDateienInOrdner(File ordner)
    {
        if(ordner.exists())
        {
            for(File datei : ordner.listFiles())
            {
                if(datei.isDirectory())
                {
                    getAlleDateienInOrdner(datei);
                }
                else if(testeObZulaessig(datei.getPath()))
                {
                    _testDateien.add(datei.getPath());
                }
            }
        }
    }

    /**
     * Prüft, ob die Datei mit dem angegebenen Namen zulässig ist.
     *
     * @param name Dateiname der Datei die überprüft werden soll.
     *
     * @return Gibt zurück, ob die Datei eine Testdatei ist.
     */
    private boolean testeObZulaessig(String name)
    {
        String endung = name.substring(name.length() - 4, name.length());

        if(!endung.equals(".txt"))
        {
            return false;
        }
        else if(ersteZeileZulaessig(name))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Liest die erste Zeile der Datei aus und prüft ob diese eine Testdatei ist.
     *
     * @param name Name der zu überorüfenden Datei.
     *
     * @return Gibt zurück, ob die erste Zeile dem String einer Testdatei entspricht.
     */
    private boolean ersteZeileZulaessig(String name)
    {
        try
        {
            InputStream ips = new FileInputStream(name);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            String ausdruck = br.readLine();

            br.close();

            if(ausdruck.contains(KENNZEICHNUNG))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return false;

    }

    /**
     * Gibt eine Liste mit den Pfaden der Fragedateien zurück.
     *
     * @return Pfade der zulässigen Testdateien.
     */
    public List<String> getFrageDateien()
    {
        return _testDateien;
    }
}
