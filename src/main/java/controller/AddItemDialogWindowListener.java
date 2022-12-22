package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import view.GUI;


public class AddItemDialogWindowListener implements WindowListener {

    private GUI view = null;

    public AddItemDialogWindowListener(GUI view) {
        this.view = view;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        view.getNewItemName().setText("");
        view.getNewItemPrice().setText("");
        view.getNewItemPriceSpinner().setValue((Object) 1);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
