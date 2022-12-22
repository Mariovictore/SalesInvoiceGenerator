package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.*;
import view.GUI;


public class TablesController {

    public static void loadInvoicesHeaderTable(GUI gui, ArrayList<InvoiceHeader> invoicesList) {
        
        while (InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui).getRowCount() > 0)
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui).removeRow(0);
        
        Object rowData[] = new Object[4];
        for (int i = 0; i < invoicesList.size(); i++) {
            rowData[0] = invoicesList.get(i).getInoviceNumber();
            
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(invoicesList.get(i).getInoviceDate());
            
            rowData[1] = date;
            rowData[2] = invoicesList.get(i).getInoviceCustomerName();
            rowData[3] = invoicesList.get(i).getInoviceTotal();
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(gui).addRow(rowData);
        }
    }

    public static void loadInvoicesLineTable(GUI gui, ArrayList<InvoiceHeader> invoicesList) {
        float total;
        Object rowData[] = new Object[5];
        int selectedRow = gui.getInvoiceTable().getSelectedRow();

        if (selectedRow == -1) {
            while (InvoicesLineTableModel.setInvoicesLineTableModel(gui).getRowCount() > 0)
                InvoicesLineTableModel.setInvoicesLineTableModel(gui).removeRow(0);
        
        } else {
            while (InvoicesLineTableModel.setInvoicesLineTableModel(gui).getRowCount() > 0) {
                InvoicesLineTableModel.setInvoicesLineTableModel(gui).removeRow(0);
            }
            
            InvoicesLineTableModel.setInvoicesLineTableModel(gui).setRowCount(0);
            for (int i = 0; i < invoicesList.get(selectedRow).getInvoicerow().size(); i++) {
                total = ((invoicesList.get(selectedRow).getInvoicerow().get(i).getItemPrice()) * (invoicesList.get(selectedRow).getInvoicerow().get(i).getItemCount()));
                invoicesList.get(selectedRow).getInvoicerow().get(i).setItemTotal(total);
                rowData[0] = invoicesList.get(selectedRow).getInvoicerow().get(i).getMainInvoice().getInoviceNumber();
                rowData[1] = invoicesList.get(selectedRow).getInvoicerow().get(i).getItemName();
                rowData[2] = invoicesList.get(selectedRow).getInvoicerow().get(i).getItemPrice();
                rowData[3] = invoicesList.get(selectedRow).getInvoicerow().get(i).getItemCount();
                rowData[4] = invoicesList.get(selectedRow).getInvoicerow().get(i).getItemTotal();
                InvoicesLineTableModel.setInvoicesLineTableModel(gui).addRow(rowData);
                InvoicesLineController.updater(gui, invoicesList, selectedRow);
            }
        }
    }
}
