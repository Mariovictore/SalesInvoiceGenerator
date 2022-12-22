package controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GUI;


public class InvoiceDateTextFieldListener implements FocusListener, ActionListener {

    private GUI GUIView = null;

    public InvoiceDateTextFieldListener(GUI view) {
        GUIView = view;
    }

    @Override
    public void focusLost(FocusEvent e) {
        if ((!(Controller.invoices.isEmpty())) && (Controller.selectedRow >= 0)) {
            if (!((GUIView.getDate().format(Controller.invoices.get(Controller.selectedRow).getInoviceDate())).equals((GUIView.getInvoiceDateTextField().getText())))) {
                InvoicesLineController.dateValidator(GUIView, Controller.invoices);
                GUIView.getInvoiceTable().setRowSelectionInterval(Controller.selectedRow, Controller.selectedRow);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent eventAction) {
        if (!((GUIView.getDate().format(Controller.invoices.get(Controller.selectedRow).getInoviceDate())).equals((GUIView.getInvoiceDateTextField().getText())))) {
            InvoicesLineController.dateValidator(GUIView, Controller.invoices);
        }
    }

    @Override
    public void focusGained(FocusEvent f) {
    }
}
