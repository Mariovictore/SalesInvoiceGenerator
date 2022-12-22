package controller;

import java.awt.event.*;

import view.GUI;

public class CustomerNameTextFieldListener implements FocusListener, ActionListener {

    private GUI GUIView = null;

    public CustomerNameTextFieldListener(GUI view) {
        GUIView = view;
    }

    @Override
    public void focusLost(FocusEvent eventFocus) {
        if (!((Controller.invoices.get(Controller.selectedRow).getInoviceCustomerName()).equals((GUIView.getCustomerNameTextField().getText())))) {
            InvoicesLineController.changeCustomerNameTextField(GUIView, Controller.invoices);
            GUIView.getInvoiceTable().setRowSelectionInterval(Controller.selectedRow, Controller.selectedRow);
        }
    }

    @Override
    public void actionPerformed(ActionEvent eventAction) {
        if (!((Controller.invoices.get(Controller.selectedRow).getInoviceCustomerName()).equals((GUIView.getCustomerNameTextField().getText())))) {
            InvoicesLineController.changeCustomerNameTextField(GUIView, Controller.invoices);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

}
