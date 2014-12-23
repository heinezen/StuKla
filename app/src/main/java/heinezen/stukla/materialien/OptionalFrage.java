package heinezen.stukla.materialien;

import java.io.File;

public interface OptionalFrage extends Frage
{
    /**
     * Gibt den Quelltext der Frage aus.
     *
     * @return Der Quelltext.
     */
    public String getQuelltext();

    /**
     * Gibt das Bild der Frage aus.
     *
     * @return Das Bild.
     */
    public File getBild();

    /**
     * Gibt zur�ck, ob die Frage Quelltext enth�lt.
     *
     * @return true, falls Quelltext vorhanden ist.
     */
    public boolean hatQuelltext();

    /**
     * Gibt zur�ck, ob die Frage ein Bild enth�lt.
     *
     * @return true, falls ein Bild vorhanden ist.
     */
    public boolean hatBild();
}
