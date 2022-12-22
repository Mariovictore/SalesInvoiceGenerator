package model;

import javax.swing.table.DefaultTableModel;

import view.GUI;


public class InvoicesHeaderTableModel {

    public static DefaultTableModel setInvoicesHeaderTableModel(GUI GUIView) {
        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) GUIView.getInvoiceTable().getModel();
        GUIView.getInvoiceTable().setDefaultEditor(Object.class, null);
        return newTable;
    }

}
