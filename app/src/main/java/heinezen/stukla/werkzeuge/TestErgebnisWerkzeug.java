package heinezen.stukla.werkzeuge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.TextView;

import heinezen.stukla.R;

public class TestErgebnisWerkzeug extends ActionBarActivity
{
    private static final String ARG_ENDERGEBNIS = "Endergebnis";
    private static final String ARG_MAXERGEBNIS = "MaxErgebnis";
    private int _ergebnis;
    private int _maxErgebnis;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ergebnis);

        _ergebnis = getIntent().getIntExtra(ARG_ENDERGEBNIS, 0);
        _maxErgebnis = getIntent().getIntExtra(ARG_MAXERGEBNIS, 0);
        int prozent = (int) (100 * ((double) _ergebnis / (double) _maxErgebnis));

        TextView view = (TextView) findViewById(R.id._ergebisAnzeigeLabel);
        view.setText(String.format("%d/%d Punkte (%d%%)", _ergebnis, _maxErgebnis, prozent));

        view = (TextView) findViewById(R.id._bestandenAngabeLabel);
        if(prozent >= 50)
        {
            view.setText("Ja");
            view.setTextColor(Color.GREEN);
        }
        else
        {
            view.setText("Nein");
            view.setTextColor(Color.RED);
        }

        view = (TextView) findViewById(R.id._zeitAngabeLabel);
        view.setText("Keine Angabe");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_ergebnis, menu);
        return true;
    }
}
