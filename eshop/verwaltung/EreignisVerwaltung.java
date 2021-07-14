package eshop.verwaltung;

import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;
import eshop.valueobjects.Ereignis;
import eshop.valueobjects.Kunden;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EreignisVerwaltung {

    private PersistenceManager pm = new FilePersistenceManager();
    private static List<Ereignis> ereignisList = new ArrayList<Ereignis>();

    public EreignisVerwaltung() {
        try {
            liesDaten();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode, die ein Ereignis in die EreignisListe einfügt
     * @param artikelId
     * @param anzahl
     * @param ereignisMsg
     * @param persId
     */
    public static void ereignisEinfuegen(int artikelId, int anzahl, String ereignisMsg, int persId) {
        Ereignis ereignis = new Ereignis(artikelId, anzahl, ereignisMsg, persId);
        ereignisList.add(ereignis);
    }

    public List<Ereignis> getEreignisList() {
        return ereignisList;
    }

    public void liesDaten() throws IOException {
        pm.openForReading("Log.txt");

        Ereignis ereignis;
        do {
            ereignis = pm.ladeEreignis();
            if (ereignis != null) {
                ereignisList.add(ereignis);
            }
        } while (ereignis != null);

        pm.close();
    }

    public void schreibeDaten()  {
        // PersistenzManager für Schreibvorgänge öffnen
        try {
            pm.openForWriting("Log.txt");
            for (Ereignis ereignis : ereignisList) {
                pm.speichereEreignis(ereignis);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Mitarbeiter wird im PersistenzManager gespeichert

        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

}
