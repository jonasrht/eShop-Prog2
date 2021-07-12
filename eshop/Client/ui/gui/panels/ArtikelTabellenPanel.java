package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.models.ArtikelTabellenModel;
import eshop.valueobjects.Artikel;

import javax.swing.*;

public class ArtikelTabellenPanel extends JTable {

    private ArtikelTabellenModel tabellenModel;

    public ArtikelTabellenPanel(java.util.List<Artikel> artikel) {
        super();
        tabellenModel = new ArtikelTabellenModel(artikel);
        setModel(tabellenModel);
        aktualisiereArtikelListe(artikel);
    }

    public void aktualisiereArtikelListe(java.util.List<Artikel> artikel) {
        // TableModel von JTable holen und ...
        tabellenModel = (ArtikelTabellenModel) getModel();
//		// ... Inhalt aktualisieren
        tabellenModel.setArtikel(artikel);
    }
}

