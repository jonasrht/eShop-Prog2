package eshop.Client.ui.gui.models;

import eshop.valueobjects.Artikel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ArtikelTabellenModel extends AbstractTableModel {

    private JTable table;

    private static final int SPALTEN_NR = 0;
    private static final int SPALTEN_NAME = 1;
    private static final int SPALTEN_PRS = 2;
    private static final int SPALTEN_BST = 3;
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
            case SPALTEN_NR:
                return gewaehlterArtikel.getProduktID();
            case SPALTEN_NAME:
                return gewaehlterArtikel.getName();
            case SPALTEN_PRS:
                return gewaehlterArtikel.getPreis() + " €";
            case SPALTEN_BST:
                return gewaehlterArtikel.getBestand();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (artikel.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

}
