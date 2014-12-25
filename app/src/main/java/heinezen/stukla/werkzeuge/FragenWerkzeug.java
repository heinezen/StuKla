package heinezen.stukla.werkzeuge;

import android.content.Intent;
import android.os.Bundle;
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
    private final String ARG_FRAGEN = "Fragen";
    /**
     * Der FragenService.
     */
    private FragenService _fragenService;

    /**
     * Der Pager mit UI zum Wehseln der Fragen.
     */
    private ViewPager _fragenPager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragen_beantworter);

        Parcelable[] parc = getIntent().getParcelableArrayExtra(ARG_FRAGEN);
        Frage[] fragen = new Frage[parc.length];
        System.arraycopy(parc, 0, fragen, 0, parc.length);

        _fragenService = new FragenServiceImpl(fragen, false);

        _fragenPager = (ViewPager) findViewById(R.id._pager);
        _fragenPager.setAdapter(new FragenPagerAdapter(getSupportFragmentManager(), false));

        registrierePager();
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
                item.setEnabled(false);
                testAbgeben();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Gibt den Test ab und nennt die Gesamtpunktzahl. Au�erdem werden alle AntwortElemente
     * gesperrt.
     */
    private void testAbgeben()
    {
        int position = _fragenPager.getCurrentItem();
        _fragenPager.setAdapter(new FragenPagerAdapter(getSupportFragmentManager(), true));
        registrierePager();
        _fragenPager.setCurrentItem(position);

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

        startActivity(intent);
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
