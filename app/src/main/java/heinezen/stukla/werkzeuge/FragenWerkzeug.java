package heinezen.stukla.werkzeuge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import heinezen.stukla.R;
import heinezen.stukla.materialien.Frage;
import heinezen.stukla.materialien.views.CountDownTimerView;
import heinezen.stukla.services.FragenService;
import heinezen.stukla.services.FragenServiceImpl;

/**
 * Das FragenWerkzeug benutzt die GUI, um Fragen anzuzeigen und den Test abzugeben. Es ist
 * zus�tzlich ein Observer der GUI.
 *
 * @author Christophad
 */
public class FragenWerkzeug extends ActionBarActivity
{
    private static final String ARG_ENDERGEBNIS = "Endergebnis";
    private static final String ARG_MAXERGEBNIS = "MaxErgebnis";
    private static final String ARG_FRAGEN = "Fragen";
    private static final String ARG_ZEIT = "Zeit";
    private static final String ARG_VERGANGENE_ZEIT = "Vergangene Zeit";

    /**
     * Der FragenService.
     */
    private FragenService _fragenService;

    /**
     * Der Pager mit UI zum Wechseln der Fragen.
     */
    private ViewPager _fragenPager;

    /**
     * Die Anzeige für den Countdown.
     */
    private CountDownTimerView _countdownView;

    /**
     * Der Timer des Werkzeugs;
     */
    private CountDownTimer _countdown;

    /**
     * Vergangene Zeit seit dem Start in Millisekunden.
     */
    private long _vergangeneZeit;

    /**
     * Die Zeit für den Test in Millisekunden.
     */
    private long _testZeit;

    private boolean _testBeendet;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragen_beantworter);

        Parcelable[] parc = getIntent().getParcelableArrayExtra(ARG_FRAGEN);
        Frage[] fragen = new Frage[parc.length];
        System.arraycopy(parc, 0, fragen, 0, parc.length);

        _countdownView = (CountDownTimerView) findViewById(R.id._countdown);

        erzeugeCountdown();

        _fragenService = new FragenServiceImpl(fragen, true);

        _fragenPager = (ViewPager) findViewById(R.id._pager);
        _fragenPager.setAdapter(new FragenPagerAdapter(getSupportFragmentManager(), false));

        registrierePager();
    }

    private void erzeugeCountdown()
    {
        int minuten = getIntent().getIntExtra(ARG_ZEIT, 60);
        _testZeit = minuten * 60 * 1000;
        _vergangeneZeit = 0;

        _countdownView = (CountDownTimerView) findViewById(R.id._countdown);

        _countdownView.setMaxZeit(_testZeit);

        setCountdown(_testZeit);
    }

    /**
     * Registriert die Listener des Pagers.
     */
    private void registrierePager()
    {
        _fragenPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int pagePosition)
            {
                invalidateOptionsMenu();
            }
        });
    }

    private void setCountdown(long zeit)
    {
        _countdown = new CountDownTimer(zeit, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                _vergangeneZeit = _testZeit - millisUntilFinished;

                _countdownView.setFortschritt(millisUntilFinished);
                _countdownView.invalidate();
            }

            @Override
            public void onFinish()
            {
                testAbgeben();
            }
        }.start();
    }

    /**
     * Gibt den Test ab und nennt die Gesamtpunktzahl. Au�erdem werden alle AntwortElemente
     * gesperrt.
     */
    private void testAbgeben()
    {
        _testBeendet = true;

        int position = _fragenPager.getCurrentItem();
        _fragenPager.setAdapter(new FragenPagerAdapter(getSupportFragmentManager(), _testBeendet));
        registrierePager();
        _fragenPager.setCurrentItem(position);

        _countdown.cancel();
        _countdownView.stopCountdown();

        erzeugeTestErgebnisWerkzeug();
    }

    /**
     * Startet die Anzeige für das Ergebnis des Tests.
     */
    private void erzeugeTestErgebnisWerkzeug()
    {
        Intent intent = new Intent(this, TestErgebnisWerkzeug.class);

        intent.putExtra(ARG_ENDERGEBNIS, _fragenService.berechneGesamtpunktzahl());
        intent.putExtra(ARG_MAXERGEBNIS, _fragenService.getMaxPunktzahl());
        intent.putExtra(ARG_VERGANGENE_ZEIT, _vergangeneZeit);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_fragen_beantworter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_beenden:
                item.setTitle("Ergebnis anzeigen");
                testAbgeben();
                return true;
            case R.id.action_abbrechen:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Test abbrechen und zur Auswahl zurückkehren?");
        builder.setTitle("Abbrechen");

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                returnToStart();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void returnToStart()
    {
        super.onBackPressed();
    }

    @Override
    public void onPause()
    {
        super.onPause();

        if(!_testBeendet)
        {
            _countdown.cancel();
            _countdownView.stopCountdown();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(!_testBeendet && _vergangeneZeit != 0)
        {
            long restzeit = _testZeit - _vergangeneZeit;
            setCountdown(restzeit);
            _countdownView.resumeCountdown(restzeit);
        }
    }

    /**
     * Implementiert einen PagerAdapter der FragenFragments erzeugt.
     */
    private class FragenPagerAdapter extends FragmentStatePagerAdapter
    {
        private final boolean _istBeendet;

        /**
         * Erzeugt einen PagerAdapter, der FragenFragments verwaltet.
         *
         * @param fragmentManager Der FragmentManager der Activity.
         * @param beendet Gibt an, ob der Test beendet ist.
         */
        public FragenPagerAdapter(FragmentManager fragmentManager, boolean beendet)
        {
            super(fragmentManager);
            _istBeendet = beendet;
        }

        @Override
        public Fragment getItem(int fragenIndex)
        {
            return FragenFragment.newInstance(_fragenService.getFrage(fragenIndex), _istBeendet);
        }

        @Override
        public int getCount()
        {
            return _fragenService.getAnzahl();
        }
    }
}
