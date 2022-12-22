package view;

import controller.Controller;
import java.io.FileNotFoundException;
import model.InvoiceHeader;
import model.InvoiceLine;

//Mario
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        GUI gui = new GUI();
        gui.setVisible(true);
        gui.setLocations();
        gui.setResizable(false);
        gui.loadFiles();

        new Controller(new InvoiceHeader(), new InvoiceLine(), gui);
    }
}
