package heinezen.stukla.werkzeuge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import heinezen.stukla.R;
import heinezen.stukla.exceptions.NoQuestionsInFileException;
import heinezen.stukla.werkzeuge.einleser.DateienEinleser;
import heinezen.stukla.werkzeuge.einleser.FragenEinleser;

public class TestAuswahlWerkzeug extends ActionBarActivity
{
    private static final String ARG_ZEIT = "Zeit";
    private static final String ARG_FRAGEN = "Fragen";

    private File TEST_DATEIEN_ORDNER;

    private Spinner _testAuswahl;
    private Button _testStartenButton;

    private String[] _testDateien;
    private int _aktuellePosition;

    private DateienEinleser _dateienEinleser;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_auswahl);

        TEST_DATEIEN_ORDNER = getApplicationContext().getExternalFilesDir(null);

        _dateienEinleser = new DateienEinleser(TEST_DATEIEN_ORDNER);

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

        registriereKomponenten();
    }

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

    private void aktualisiereUebersicht(int dateiIndex)
    {
        String datei = _testDateien[dateiIndex];

        FragenEinleser leser = new FragenEinleser(datei);

        try
        {
            String[] uebersicht = leser.gibUeberblickAus();

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

    private void erzeugeFragenWerkzeug(int testFragen)
    {
        FragenEinleser fragenEinleser = new FragenEinleser(_testDateien[testFragen]);

        Intent intent = new Intent(this, FragenWerkzeug.class);

        try
        {
            intent.putExtra(ARG_FRAGEN, fragenEinleser.gibFragenAus());

            String[] uebersicht = fragenEinleser.gibUeberblickAus();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
