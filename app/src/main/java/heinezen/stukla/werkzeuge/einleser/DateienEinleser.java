package heinezen.stukla.werkzeuge.einleser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Kommentare
 *
 * @author Christophad
 */
public class DateienEinleser
{
    private final File ORDNER;

    private final String KENNZEICHNUNG = "###STUKLA###";

    private List<String> _testDateien;

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
     * Pr�ft, ob die Datei mit dem angegebenen Namen zul�ssig ist.
     *
     * @param name Dateiname
     *
     * @return Gibt zur�ck, ob die Datei eine Testdatei ist.
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

    public List<String> getFrageDateien()
    {
        return _testDateien;
    }
}
