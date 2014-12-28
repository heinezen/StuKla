package heinezen.stukla.werkzeuge;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import heinezen.stukla.R;
import heinezen.stukla.exceptions.NoQuestionsInFileException;
import heinezen.stukla.werkzeuge.einleser.DateienEinleser;
import heinezen.stukla.werkzeuge.einleser.FragenEinleser;

/**
 * Werkzeug zur Auswahl eines Tests aus dem Applikationsordner.
 */
public class TestAuswahlWerkzeug extends ActionBarActivity
{
    /**
     * Erkennungsstrings für das Weitergeben von Informationen an andere Prozesse
     */
    private static final String ARG_ZEIT = "Zeit";
    private static final String ARG_FRAGEN = "Fragen";
    private static final String ERSTER_START = "ERSTER_START";

    /**
     * Ordner in dem die Testdateien liegen.
     */
    private File TEST_DATEIEN_ORDNER;

    /**
     * Der Spinner zur Auswahl von Fragensätzen.
     */
    private Spinner _testAuswahl;

    /**
     * Button zum Starten des ausgewählten Tests.
     */
    private Button _testStartenButton;

    /**
     * Array der Testdateien.
     */
    private String[] _testDateien;

    /**
     * Index des aktuell ausgewählten Tests.
     */
    private int _aktuellePosition;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_auswahl);

        File speicher = this.getExternalFilesDir(null);
        File testsOrdner = new File(speicher, "tests");

        if(!testsOrdner.exists())
        {
            kopiereTestsAusAssets();
        }

        sucheTestDateien();

        registriereKomponenten();
    }

    /**
     * Kopiert die in den Assets enthaltenen Tests in das Verzeichnis der Apllikation.
     */
    private void kopiereTestsAusAssets()
    {
        AssetManager manager = getResources().getAssets();
        File speicher = this.getExternalFilesDir(null);
        File testsOrdner = new File(speicher, "tests");
        testsOrdner.mkdir();
        String[] unterordner;
        List<String> dateien = new ArrayList<>();

        try
        {
            unterordner = manager.list("tests");
            for(String datei : unterordner)
            {
                File testOrdner = new File(testsOrdner, datei);
                testOrdner.mkdir();

                String[] ordner = manager.list("tests" + File.separator + datei);
                for(String dat : ordner)
                {
                    dateien.add(dat);

                    InputStream inputstream = manager.open("tests" + File.separator + datei + File
                            .separator + dat);

                    FileOutputStream outputstream = null;
                    try
                    {
                        File file = new File(testOrdner, dat);
                        outputstream = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int length;
                        while((length = inputstream.read(buffer)) > 0)
                        {
                            outputstream.write(buffer, 0, length);
                        }
                    }
                    finally
                    {
                        if(inputstream != null)
                        {
                            inputstream.close();
                        }
                        if(outputstream != null)
                        {
                            outputstream.close();
                        }
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sucht Testdateien in dem Tesdateienordner.
     */
    private void sucheTestDateien()
    {
        TEST_DATEIEN_ORDNER = this.getExternalFilesDir(null);

        DateienEinleser _dateienEinleser = new DateienEinleser(TEST_DATEIEN_ORDNER);

        _testAuswahl = (Spinner) findViewById(R.id.spinner);
        _testStartenButton = (Button) findViewById(R.id._testStartenButton);

        List<String> testDateien = _dateienEinleser.getFrageDateien();

        if(testDateien.isEmpty())
        {
            testDateien.add("Keine Dateien vorhanden");
        }

        String[] array = new String[testDateien.size()];
        array = testDateien.toArray(array);

        _testDateien = array;

        erzeugeAuswahlBereich();
    }

    /**
     * Registriert die Listener der Komponenten.
     */
    private void registriereKomponenten()
    {
        _testAuswahl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                _aktuellePosition = position;
                aktualisiereUebersicht(_aktuellePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        _testStartenButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                erzeugeFragenWerkzeug(_aktuellePosition);
            }
        });
    }

    /**
     * Erzeugt den Bereich in dem die Tests ausgewählt werden können.
     */
    private void erzeugeAuswahlBereich()
    {
        List<String> testDateienNamen = new ArrayList<>();

        for(String pfad : _testDateien)
        {
            File datei = new File(pfad);
            String dateiname = datei.getName().replace(".txt", "");
            testDateienNamen.add(dateiname);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, testDateienNamen);

        _testAuswahl.setAdapter(adapter);
    }

    /**
     * Aktualisiert die Übersicht für den ausgewählten Test.
     *
     * @param dateiIndex Index des Tests in der Testliste.
     */
    private void aktualisiereUebersicht(int dateiIndex)
    {
        String datei = _testDateien[dateiIndex];

        FragenEinleser leser = new FragenEinleser(datei);

        try
        {
            String[] uebersicht = leser.gibUebersichtAus();

            TextView v = (TextView) findViewById(R.id._modulAngabeLabel);
            v.setText(uebersicht[1]);

            v = (TextView) findViewById(R.id._datumAngabeLabel);
            v.setText(uebersicht[2]);

            v = (TextView) findViewById(R.id._zeitAngabeLabel);
            v.setText(uebersicht[3] + " min");
        }
        catch(NoQuestionsInFileException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt ein FragenWerkzeug mit dem ein Test bearbeitet werden kann.
     *
     * @param testFragen Index des Tests in der Testliste.
     */
    private void erzeugeFragenWerkzeug(int testFragen)
    {
        FragenEinleser fragenEinleser = new FragenEinleser(_testDateien[testFragen]);

        Intent intent = new Intent(this, FragenWerkzeug.class);

        try
        {
            intent.putExtra(ARG_FRAGEN, fragenEinleser.gibFragenAus());

            String[] uebersicht = fragenEinleser.gibUebersichtAus();
            intent.putExtra(ARG_ZEIT, Integer.parseInt(uebersicht[3]));

            startActivity(intent);
        }
        catch(NoQuestionsInFileException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_auswahl, menu);
        return true;
    }
}
