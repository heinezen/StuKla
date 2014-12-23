package heinezen.stukla.werkzeuge;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import heinezen.stukla.R;
import heinezen.stukla.materialien.Frage;
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
    private final String ARG_FRAGEN = "Fragen";

    /**
     * Der FragenService.
     */
    private FragenService _fragenService;

    /**
     * Die UI des FragenWerkzeugs.
     */
    private FragenWerkzeugUI _uiFragenWerkzeug;

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
        _fragenPager.setAdapter(new FragenPagerAdapter(getSupportFragmentManager()));

        _uiFragenWerkzeug = new FragenWerkzeugUI(
                (android.widget.LinearLayout) findViewById(R.id._punkteAnzeige));

        registrierePager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_fragen_beantworter, menu);
        return true;
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

    /**
     * Gibt den Test ab und nennt die Gesamtpunktzahl. Au�erdem werden alle
     * AntwortElemente gesperrt.
     */
    private void testAbgeben()
    {
        int endergebnis = _fragenService.berechneGesamtpunktzahl();
        int maxErgebnis = _fragenService.getMaxPunktzahl();

        _uiFragenWerkzeug.beendeTest(endergebnis, maxErgebnis);
    }

    /**
     * Implementiert einen PagerAdapter der FragenFragments erzeugt.
     */
    private class FragenPagerAdapter extends FragmentStatePagerAdapter
    {
        public FragenPagerAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int fragenIndex)
        {
            return FragenFragment.newInstance(_fragenService.getFrage(fragenIndex));
        }

        @Override
        public int getCount()
        {
            return _fragenService.getAnzahl();
        }
    }
}
