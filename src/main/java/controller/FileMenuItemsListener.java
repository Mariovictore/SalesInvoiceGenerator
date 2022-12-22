package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.*;
import view.GUI;


public class FileMenuItemsListener implements ActionListener{

    private FileOperations fileOperations;
    private InvoiceTableListener invoiceTableListener;
    private GUI GUIView;

    public FileMenuItemsListener(GUI gui, FileOperations fileOperations, InvoiceTableListener invoiceTableListener) {
        GUIView = gui;
        this.fileOperations = fileOperations;
        this.invoiceTableListener = invoiceTableListener;
    }

    @Override
    public void actionPerformed(ActionEvent eventAction) {
        switch (eventAction.getActionCommand()) {
            case "Load Files" -> {
                while (InvoicesHeaderTableModel.setInvoicesHeaderTableModel(GUIView).getRowCount() > 0) {
                    InvoicesHeaderTableModel.setInvoicesHeaderTableModel(GUIView).removeRow(0);
                }

                while (InvoicesLineTableModel.setInvoicesLineTableModel(GUIView).getRowCount() > 0) {
                    InvoicesLineTableModel.setInvoicesLineTableModel(GUIView).removeRow(0);
                }

                GUIView.getInvoiceTotalLabel().setText("");
                fileOperations.getFilesPaths();
                if ((FileOperations.selectedInvoiceHeader != null) && (FileOperations.selectedInvoiceLine != null)) {
                    Controller.invoices = fileOperations.readFile();
                    fileOperations.main(Controller.invoices);
                    InvoicesHeaderController.calculateInvoiceTableTotal(Controller.invoices);
                    TablesController.loadInvoicesHeaderTable(GUIView, Controller.invoices);
                    fileOperations.getMaxNumberOfExistedInvoices(Controller.maxNumberOfExistedInvoices, Controller.invoices);
                }
            }
            case "Save File" -> {
                try {
                    fileOperations.writeFile(Controller.invoices);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            case "Quit" -> {
                System.exit(0);
            }
        }
    }

}
