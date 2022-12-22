package controller;

import java.util.ArrayList;

import model.*;
import view.GUI;

public class InvoicesLineController {

    public static void updater(GUI gui, ArrayList<InvoiceHeader> invoices, int row) {
        if (row != -1) {
            gui.getInvoiceNumberLabel().setText(Integer.toString(invoices.get(row).getInoviceNumber()));
            gui.getInvoiceDateTextField().setText(gui.getDate().format(invoices.get(row).getInoviceDate()));
            gui.getCustomerNameTextField().setText(invoices.get(row).getInoviceCustomerName());
            gui.getInvoiceTotalLabel().setText(Float.toString(invoices.get(row).getInoviceTotal()));
        }
    }

    public static void dateValidator(GUI gui, ArrayList<InvoiceHeader> invoicesList) {
        int choice = gui.showYesNoCancelDialog(gui.getInvoicesItemsPanel(), "Do you want to save date changes?", "Confirmation");
        switch (choice) {
            case 0 -> {
                try {
                    if (!gui.getInvoiceDateTextField().getText().matches("^\\d{2}\\-\\d{2}\\-\\d{4}")) {
                        throw new Exception();
                    }

                    FileOperations.date.setLenient(false);
                    FileOperations.date.parse(gui.getInvoiceDateTextField().getText());
                    invoicesList.get(gui.getInvoiceTable().getSelectedRow()).setInoviceDate(gui.getDate().parse(gui.getInvoiceDateTextField().getText()));
                    InvoicesHeaderController.updateTableDate(gui, invoicesList);
                    gui.getInvoiceDateTextField().requestFocus();
                } catch (Exception e) {
                    GUI.setJOptionPaneMessagMessage(gui.getInvoicesItemsPanel(), "Please enter a valid date (e.g 06-06-2022)", "wrong date", "ERROR_MESSAGE");
                    gui.getInvoiceDateTextField().setText(gui.getDate().format(invoicesList.get(gui.getInvoiceTable().getSelectedRow()).getInoviceDate()));
                    gui.getInvoiceDateTextField().requestFocus();
                }
            }
            case 1 ->
                gui.getInvoiceDateTextField().requestFocus();
            default -> {
                gui.getInvoiceDateTextField().setText(gui.getDate().format(invoicesList.get(Controller.selectedRow).getInoviceDate()));
                gui.getInvoiceDateTextField().requestFocus();
            }
        }
    }

    public static void changeCustomerNameTextField(GUI gui, ArrayList<InvoiceHeader> invoicesList) {
        int choice = gui.showYesNoCancelDialog(gui.getInvoicesItemsPanel(), "Do you want to save new customer name?", "Confirmation");
        switch (choice) {
            case 0 -> {
                invoicesList.get(gui.getInvoiceTable().getSelectedRow()).setInoviceCustomerName(gui.getCustomerNameTextField().getText());
                InvoicesHeaderController.updateTableCustomerName(gui, invoicesList);
                gui.getCustomerNameTextField().requestFocus();
            }
            case 1 ->
                gui.getCustomerNameTextField().requestFocus();
            default -> {
                gui.getCustomerNameTextField().setText(invoicesList.get(Controller.selectedRow).getInoviceCustomerName());
                gui.getCustomerNameTextField().requestFocus();
            }
        }
    }

    static void addNewItem(GUI gui, ArrayList<InvoiceHeader> invoices) {
        String itemName;
        float price = 0;
        int count = 0;

        boolean flag = false;
        itemName = gui.getNewItemName().getText();

        if (itemName.equalsIgnoreCase("")) {
            GUI.getAddItemDialog().setVisible(false);
            GUI.setJOptionPaneMessagMessage(gui.getInvoicesItemsPanel(), "Please enter a valid name", "Empty Item Name", "ERROR_MESSAGE");
            showNewItemDialog(gui);
        } else if (gui.getNewItemPrice().getText().equalsIgnoreCase("")) {
            GUI.getAddItemDialog().setVisible(false);
            GUI.setJOptionPaneMessagMessage(gui.getInvoicesItemsPanel(), "Please enter a price", "Empty Price", "ERROR_MESSAGE");
            showNewItemDialog(gui);
        } else {
            
            try {
                price = Float.parseFloat(gui.getNewItemPrice().getText());
                gui.getNewItemPriceSpinner().commitEdit();
                count = (Integer) gui.getNewItemPriceSpinner().getValue();
            } catch (Exception e) {
                flag = true;
                e.printStackTrace();
            }

            if (!flag) {
                InvoiceHeader temp = invoices.get(gui.getInvoiceTable().getSelectedRow());
                InvoiceLine newItem = new InvoiceLine(itemName, price, count, temp);
                temp.getInvoicerow().add(newItem);
            }

            gui.getNewItemName().setText("");
            gui.getNewItemPrice().setText("");
            gui.getNewItemPriceSpinner().setValue((Object) 1);
        }
    }

    static void showNewItemDialog(GUI gui) {
        gui.setLocations();
        GUI.getAddItemDialog().setTitle("Add new item to invoice " + gui.getInvoiceNumberLabel().getText());
        GUI.getAddItemDialog().setVisible(true);
    }

    static void deleteItem(GUI gui, ArrayList<InvoiceHeader> invoices) {
        if (gui.getInvoicesLineTable().getSelectedRow() >= 0) {
            int rowToBeDeleted;
            rowToBeDeleted = gui.getInvoicesLineTable().getSelectedRow();
            invoices.get(gui.getInvoiceTable().getSelectedRow()).getInvoicerow().remove(rowToBeDeleted);
        }
    }
}
