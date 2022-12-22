package model;

import javax.swing.table.DefaultTableModel;

import view.GUI;


public class InvoicesLineTableModel {

    public static DefaultTableModel setInvoicesLineTableModel(GUI GUIView) {
        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) GUIView.getInvoicesLineTable().getModel();
        GUIView.getInvoicesLineTable().setDefaultEditor(Object.class, null);
        return newTable;
    }
}
