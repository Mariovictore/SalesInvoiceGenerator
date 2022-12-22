package controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.FileOperations;
import view.GUI;

public class InvoiceTableListener implements ListSelectionListener {

    private FileOperations fileOperations;
    private GUI GUIView = null;
    private InvoicesLineTableListener invoicesLineTableListener;

    public InvoiceTableListener(GUI view, FileOperations fileOperations, InvoicesLineTableListener invoicesLineTableListener) {
        GUIView = view;
        this.fileOperations = fileOperations;
        this.invoicesLineTableListener = invoicesLineTableListener;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (Controller.invoices.size() >= 1) {
            if (!e.getValueIsAdjusting()) {
                Controller.selectedRow = GUIView.getInvoiceTable().getSelectedRow();
                GUIView.getInvoicesLineTable().getSelectionModel().removeListSelectionListener(invoicesLineTableListener);
                TablesController.loadInvoicesLineTable(GUIView, Controller.invoices);
                InvoicesLineController.updater(GUIView, Controller.invoices, Controller.selectedRow);
                GUIView.getInvoicesLineTable().getSelectionModel().addListSelectionListener(invoicesLineTableListener);
            }
        }
    }
}
