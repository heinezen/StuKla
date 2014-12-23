package heinezen.stukla.materialien.format;

public class UebersichtFormattierer
{
    private static final String TITEL = "###STUKLA###";

    private static final String UEBERBLICK_TRENNER = "###UEB###";

    /**
     * Erstellt eine Uebersicht aus einem String.
     *
     * @param ueberblick Der String der ausgelesen werden soll.
     *
     * @return Eine Array mit 3 Strings (Modul, Datum, Zeit)
     */
    public static String[] parseUeberblick(String ueberblick)
    {
        String rohUeberblick = ueberblick.replace("\n", "");
        rohUeberblick = rohUeberblick.replace(TITEL, "");
        rohUeberblick = rohUeberblick.replaceFirst(UEBERBLICK_TRENNER, "");

        String[] rohUeberlickInformationen = rohUeberblick.split(UEBERBLICK_TRENNER);

        return rohUeberlickInformationen;
    }

    /**
     * Formattiert einen auslesbaren String aus einer �bersicht.
     *
     * @param ueberblick Der einzulesende �berblick.
     *
     * @return Ein formattierter String.
     */
    public static String formattiereUeberblick(String[] ueberblick)
    {
        String schreibUeberblick = "";

        schreibUeberblick += TITEL + "\n";

        for(int i = 0; i < ueberblick.length; i++)
        {
            schreibUeberblick += UEBERBLICK_TRENNER + ueberblick[i] + "\n";
        }

        schreibUeberblick += "\n";

        return schreibUeberblick;
    }
}
