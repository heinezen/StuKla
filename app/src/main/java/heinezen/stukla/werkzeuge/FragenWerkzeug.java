package heinezen.stukla.werkzeuge;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import heinezen.stukla.R;
import heinezen.stukla.materialien.Frage;
import heinezen.stukla.materialien.OptionalFrage;
import heinezen.stukla.services.FragenService;
import heinezen.stukla.services.FragenServiceImpl;

/**
 * Das FragenWerkzeug benutzt die GUI, um Fragen anzuzeigen und den Test
 * abzugeben. Es ist zus�tzlich ein Observer der GUI.
 *
 * @author Christophad
 */
public class FragenWerkzeug extends ActionBarActivity
{
    /**
     * Der FragenService.
     */
    private FragenService _fragenService;

    /**
     * Die aktuelle Frage.
     */
    private Frage _aktuelleFrage;

    /**
     * Das UI des FragenWerkzeugs.
     */
    private FragenWerkzeugUI _uiFragenWerkzeug;

    /**
     * Das AntwortenWerkzeug.
     */
    private AntwortenWerkzeug _antwortenWerkzeug;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragen_beantworter);

        Parcelable[] parc = getIntent().getParcelableArrayExtra("Fragen");
        Frage[] fragen = new Frage[parc.length];
        System.arraycopy(parc, 0, fragen, 0, parc.length);

        _fragenService = new FragenServiceImpl(fragen);

        _aktuelleFrage = _fragenService.getAktuelleFrage();

        _antwortenWerkzeug = new AntwortenWerkzeug(
                (LinearLayout) findViewById(R.id._antwortenBereich));

        _uiFragenWerkzeug = new FragenWerkzeugUI((LinearLayout) findViewById(R.id._anzeige),
                _antwortenWerkzeug.getPanel());

        _antwortenWerkzeug.setAktuellenFragetyp(_aktuelleFrage.getFragetyp());

        registriereButtons();

        aendereElemente();
    }

    private void registriereButtons()
    {
        _uiFragenWerkzeug.getAbgabeButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                testAbgeben();
            }
        });
        _uiFragenWerkzeug.getVorwaertsButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                naechsteFrage();
            }
        });
        _uiFragenWerkzeug.getRueckwaertsButton().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                vorherigeFrage();
            }
        });
    }

    /**
     * �ndert die GUI-Elemente und passt sie an die Frage an.
     */
    private void aendereElemente()
    {
        aendereFrage();
        aendereQuelltext();
        aendereBild();
        aendereAntworten();
    }

    private void aendereBild()
    {
        if(_aktuelleFrage instanceof OptionalFrage)
        {
            if(((OptionalFrage) _aktuelleFrage).hatBild())
            {
                _uiFragenWerkzeug.aktualisiereBild(((OptionalFrage) _aktuelleFrage).getBild());
            }
            else
            {
                _uiFragenWerkzeug.entferneBild();
            }
        }
        else
        {
            _uiFragenWerkzeug.entferneBild();
        }
    }

    /**
     * �ndert den angezeigten Fragebereich.
     */
    private void aendereFrage()
    {
        _uiFragenWerkzeug.aktualisiereFrage(_aktuelleFrage.getFragetext());
    }

    /**
     * �ndert den angezeigten Quelltext
     */
    private void aendereQuelltext()
    {
        if(_aktuelleFrage instanceof OptionalFrage)
        {
            _uiFragenWerkzeug.aktualisiereQuelltext(
                    ((OptionalFrage) _aktuelleFrage).getQuelltext());
        }
        else
        {
            _uiFragenWerkzeug.aktualisiereQuelltext("");
        }
    }

    /**
     * �ndert den angezeigten Antwortenbereich.
     */
    private void aendereAntworten()
    {
        _antwortenWerkzeug.aktualisiereAntworten(_aktuelleFrage.getAntworttexte(),
                _aktuelleFrage.getSpielerAntworten());
    }

    /**
     * Springt zur n�chsten Frage im aktuellen Test.
     */
    private void naechsteFrage()
    {
        _aktuelleFrage.aktualisiereSpielerAntworten(_antwortenWerkzeug.getEingaben());

        _aktuelleFrage = _fragenService.zurNaechstenFrage();
        _antwortenWerkzeug.setAktuellenFragetyp(_aktuelleFrage.getFragetyp());

        aendereElemente();
    }

    /**
     * Springt zur vorherigen Frage im aktuellen Test.
     */
    private void vorherigeFrage()
    {
        _aktuelleFrage.aktualisiereSpielerAntworten(_antwortenWerkzeug.getEingaben());

        _aktuelleFrage = _fragenService.zurVorherigenFrage();
        _antwortenWerkzeug.setAktuellenFragetyp(_aktuelleFrage.getFragetyp());

        aendereElemente();
    }

    /**
     * Gibt den Test ab und nennt die Gesamtpunktzahl. Au�erdem werden alle
     * AntwortElemente gesperrt.
     */
    private void testAbgeben()
    {
        _aktuelleFrage.aktualisiereSpielerAntworten(_antwortenWerkzeug.getEingaben());

        int endergebnis = _fragenService.berechneGesamtpunktzahl();
        int maxErgebnis = _fragenService.getMaxPunktzahl();

        _antwortenWerkzeug.setzeBeendet();
        _uiFragenWerkzeug.beendeTest(endergebnis, maxErgebnis);
    }
}
