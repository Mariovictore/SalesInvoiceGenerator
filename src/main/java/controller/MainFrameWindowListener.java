package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import view.GUI;
import model.FileOperations;


public class MainFrameWindowListener implements WindowListener {

    private FileOperations fileOperations;
    private InvoiceTableListener invoiceTableListener;
    private GUI GUIView = null;

    public MainFrameWindowListener(GUI gui, FileOperations fileOperations, InvoiceTableListener invoiceTableListener) {
        GUIView = gui;
        this.fileOperations = fileOperations;
        this.invoiceTableListener = invoiceTableListener;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
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
