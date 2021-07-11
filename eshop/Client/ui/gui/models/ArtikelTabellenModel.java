package eshop.Client.ui.gui.models;

import eshop.valueobjects.Artikel;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ArtikelTabellenModel extends AbstractTableModel {
    private List<Artikel> artikel;
    private String[] spaltenNamen = { "Artikelnummer", "Artikelname", "Preis", "Bestand"};

    public ArtikelTabellenModel(List<Artikel> aktuelleArtikel) {
        super();
        artikel = new ArrayList<Artikel>();
        artikel.addAll(aktuelleArtikel);
    }

    public void setArtikel(List<Artikel> aktuelleArtikel) {
        artikel.clear();
        artikel.addAll(aktuelleArtikel);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return artikel.size();
    }

    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Artikel gewaehlterArtikel = artikel.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return gewaehlterArtikel.getProduktID();
            case 1:
                return gewaehlterArtikel.getName();
            case 2:
                return gewaehlterArtikel.getPreis() + " €";
            case 3:
                return gewaehlterArtikel.getBestand();
            default:
                return null;
        }
    }

}
