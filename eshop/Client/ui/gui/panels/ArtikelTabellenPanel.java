package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.models.ArtikelTabellenModel;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

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

        setAutoCreateRowSorter(true);

        TableRowSorter<TableModel> sortierer = new TableRowSorter<>(getModel());
        setRowSorter(sortierer);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int spaltenIndexZuSortieren = 0;
        sortKeys.add(new RowSorter.SortKey(spaltenIndexZuSortieren, SortOrder.ASCENDING));

        sortierer.setSortKeys(sortKeys);
        sortierer.sort();

    }
}

