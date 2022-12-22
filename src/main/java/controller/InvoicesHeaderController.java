package controller;

import java.text.ParseException;
import java.util.ArrayList;

import model.*;
import view.GUI;


public class InvoicesHeaderController {

    TablesController loadTablesContents = new TablesController();

    static void updateTableDate(GUI GUIView, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(GUIView)
                .setValueAt(invoices.get(GUIView.getInvoiceTable().getSelectedRow()).getInoviceDate(), GUIView.getInvoiceTable().getSelectedRow(), 1);
    }

    static void updateTableCustomerName(GUI GUIView, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(GUIView)
                .setValueAt(invoices.get(GUIView.getInvoiceTable().getSelectedRow()).getInoviceCustomerName(), GUIView.getInvoiceTable().getSelectedRow(), 2);
    }

    static void updateTableTotal(GUI GUIView, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(GUIView)
                .setValueAt(invoices.get(GUIView.getInvoiceTable().getSelectedRow()).getInoviceTotal(), GUIView.getInvoiceTable().getSelectedRow(), 3);
    }

    static void showCreatNewInvoiceDialog(GUI GUIView) {
        GUIView.setLocations();
        GUIView.getNewInvoiceDateField().setText("");
        GUI.getNewInvoiceDialog().setVisible(true);
    }

    static void addNewInvoice(GUI GUIView, ArrayList<InvoiceHeader> invoices) {
        if (GUIView.getNewCustomerName().getText().equalsIgnoreCase("")) {
            GUI.getNewInvoiceDialog().setModal(false);
            GUI.setJOptionPaneMessagMessage(GUIView.getInvoicesTablePanel(), "Please Enter A Name For The Customer", "Empty Name Entered", "ERROR_MESSAGE");
            GUI.getNewInvoiceDialog().setModal(true);
            showCreatNewInvoiceDialog(GUIView);
        } else {
            try {
                Controller.maxNumberOfExistedInvoices++;

                InvoiceHeader newRow = new InvoiceHeader((Controller.maxNumberOfExistedInvoices), (GUIView.getDate().parse(GUIView.getNewInvoiceDateField().getText())), (GUIView.getNewCustomerName().getText()));
                invoices.add(newRow);
                TablesController.loadInvoicesHeaderTable(GUIView, invoices);
                GUI.getNewInvoiceDialog().setVisible(false);
                GUIView.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                GUIView.getNewCustomerName().setText("");
                GUIView.getNewInvoiceDateField().setText("");
                InvoicesLineController.updater(GUIView, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(GUIView, invoices);
            } catch (ParseException exception) {
                GUI.getNewInvoiceDialog().setModal(false);
                GUI.setJOptionPaneMessagMessage(GUIView.getInvoicesTablePanel(), "Please Enter A Valid Date", "Invalid Date Entered", "ERROR_MESSAGE");
                GUI.getNewInvoiceDialog().setModal(true);
                showCreatNewInvoiceDialog(GUIView);
            }

        }
    }

    static void deleteSelectedInvoice(GUI GUIView, ArrayList<InvoiceHeader> invoices) {
        int invoiceToBeDeleted = GUIView.getInvoiceTable().getSelectedRow();

        if (invoiceToBeDeleted == -1) {
            GUI.setJOptionPaneMessagMessage(GUIView.getInvoicesTablePanel(), "Select Invoice Row First", "Error", "ERROR_MESSAGE");
        } else {
            invoices.remove(invoiceToBeDeleted);
            TablesController.loadInvoicesHeaderTable(GUIView, invoices);
            if (!invoices.isEmpty()) {
                GUIView.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                InvoicesLineController.updater(GUIView, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(GUIView, invoices);
            } else {
                while (InvoicesLineTableModel.setInvoicesLineTableModel(GUIView).getRowCount() > 0) {
                    InvoicesLineTableModel.setInvoicesLineTableModel(GUIView).removeRow(0);
                }
            }
        }
    }

    public static void calculateInvoiceTableTotal(ArrayList<InvoiceHeader> invoices) {
        float sum;
        for (int i = 0; i < invoices.size(); i++) {
            sum = 0;
            for (int j = 0; j < invoices.get(i).getInvoicerow().size(); j++) {
                sum = sum + ((invoices.get(i).getInvoicerow().get(j).getItemPrice()) * (invoices.get(i).getInvoicerow().get(j).getItemCount()));
            }
            invoices.get(i).setInoviceTotal(sum);
        }
    }
}
