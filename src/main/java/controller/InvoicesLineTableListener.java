package controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.GUI;


public class InvoicesLineTableListener implements ListSelectionListener {

    private GUI GUIView = null;

    public InvoicesLineTableListener(GUI view) {
        GUIView = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
