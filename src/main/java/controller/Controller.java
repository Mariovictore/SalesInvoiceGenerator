package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.GUI;

/**
 *
 * @author Ola Galal
 */
public class Controller implements ActionListener, KeyListener {

    private InvoiceHeader invoiceHeader;
    private InvoiceLine invoiceLine;
    private GUI GUIView;
    private FileOperations fileOperations;
    private TablesController loadTablesContents;

    public volatile static ArrayList<InvoiceHeader> invoices = new ArrayList<>();
    public volatile static int selectedRow = 0;
    public volatile static int maxNumberOfExistedInvoices = 0;

    private InvoiceTableListener invoiceTableListener;
    private InvoicesLineTableListener invoicesLineTableListener;
    private FileMenuItemsListener fileMenuItemsListener;
    private MainFrameWindowListener mainFrameWindowListener;
    private AddItemDialogWindowListener addItemDialogWindowListener;
    private InvoiceDateTextFieldListener invoiceDateTextFieldListener;
    private CustomerNameTextFieldListener customerNameTextFieldListener;

    public Controller(InvoiceHeader invoiceHeader, InvoiceLine invoiceLine, GUI gui) {
        this.invoiceHeader = invoiceHeader;
        this.invoiceLine = invoiceLine;
        GUIView = gui;
        fileOperations = new FileOperations(GUIView);

        invoicesLineTableListener = new InvoicesLineTableListener(gui);
        invoiceTableListener = new InvoiceTableListener(gui, fileOperations, invoicesLineTableListener);

        fileMenuItemsListener = new FileMenuItemsListener(gui, fileOperations, invoiceTableListener);
        mainFrameWindowListener = new MainFrameWindowListener(gui, fileOperations, invoiceTableListener);

        addItemDialogWindowListener = new AddItemDialogWindowListener(gui);

        invoiceDateTextFieldListener = new InvoiceDateTextFieldListener(gui);
        customerNameTextFieldListener = new CustomerNameTextFieldListener(gui);

        turnOnAllActionListerners(gui);

        loadTablesContents = new TablesController();
        
    }

    private void turnOnAllActionListerners(GUI GUIView) {
        GUIView.getLoadFile().addActionListener(fileMenuItemsListener);
        GUIView.getLoadFile().setActionCommand("Load Files");

        GUIView.getSaveFile().addActionListener(fileMenuItemsListener);
        GUIView.getSaveFile().setActionCommand("Save File");

        GUIView.getQuit().addActionListener(fileMenuItemsListener);
        GUIView.getQuit().setActionCommand("Quit");

        GUIView.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
        GUIView.getInvoicesLineTable().getSelectionModel().addListSelectionListener(invoicesLineTableListener);

        GUIView.addWindowListener(mainFrameWindowListener);

        GUI.getAddItemDialog().addWindowListener(addItemDialogWindowListener);

        GUIView.getInvoiceDateTextField().addActionListener(invoiceDateTextFieldListener);
        GUIView.getInvoiceDateTextField().addFocusListener(invoiceDateTextFieldListener);

        GUIView.getCustomerNameTextField().addActionListener(customerNameTextFieldListener);
        GUIView.getCustomerNameTextField().addFocusListener(customerNameTextFieldListener);

        GUIView.getCreatNewInvoiceButton().addActionListener(this);
        GUIView.getCreatNewInvoiceButton().setActionCommand("Creat New Invoice");

        GUIView.getCreatNewInvoiceOK().addActionListener(this);
        GUIView.getCreatNewInvoiceOK().setActionCommand("Creat New Invoice OK");

        GUIView.getCreatNewInvoiceCancel().addActionListener(this);
        GUIView.getCreatNewInvoiceCancel().setActionCommand("Creat New Invoice Cancel");

        GUIView.getDeleteInvoiceButton().addActionListener(this);
        GUIView.getDeleteInvoiceButton().setActionCommand("Delete Invoice");

        GUIView.getAddItemButton().addActionListener(this);
        GUIView.getAddItemButton().setActionCommand("Add Item");

        GUIView.getNewItemPrice().addKeyListener(this);
        
        GUIView.getAddItemDialogCancel().addActionListener(this);
        GUIView.getAddItemDialogCancel().setActionCommand("Add Item Dialog Cancel");

        GUIView.getAddItemDialogOK().addActionListener(this);
        GUIView.getAddItemDialogOK().setActionCommand("Add Item Dialog OK");

        GUIView.getDeleteItemButton().addActionListener(this);
        GUIView.getDeleteItemButton().setActionCommand("Delete Item");

    }

    /**
     *
     * Implement All Listeners
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Creat New Invoice" -> {
                if (GUIView.getFocusOwner() != null) {
                    InvoicesHeaderController.showCreatNewInvoiceDialog(GUIView);
                }
            }

            case "Creat New Invoice OK" -> {
                GUIView.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                InvoicesHeaderController.addNewInvoice(GUIView, invoices);
                GUIView.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
            }


            case "Creat New Invoice Cancel" -> {
                GUIView.getNewCustomerName().setText("");
                GUI.getNewInvoiceDialog().setVisible(false);
            }

            case "Delete Invoice" -> {
                if (GUIView.getFocusOwner() != null) {
                    GUIView.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    InvoicesHeaderController.deleteSelectedInvoice(GUIView, invoices);
                    GUIView.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
                }
            }

            case "Add Item" -> {
                if (GUIView.getFocusOwner() != null) {
                    InvoicesLineController.showNewItemDialog(GUIView);
                }
            }
            
            case "Add Item Dialog OK" -> {
                InvoicesLineController.addNewItem(GUIView, invoices);
                InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                InvoicesHeaderController.updateTableTotal(GUIView, invoices);
                InvoicesLineController.updater(GUIView, invoices, selectedRow);
                TablesController.loadInvoicesLineTable(GUIView, invoices);

                int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(GUIView.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                GUIView.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                GUI.getAddItemDialog().setVisible(false);
            }

            case "Add Item Dialog Cancel" -> {
                GUI.getAddItemDialog().setVisible(false);
                GUIView.getNewItemName().setText("");
                GUIView.getNewItemPrice().setText("");
                GUIView.getNewItemPriceSpinner().setValue((Object) 1);
            }

            case "Delete Item" -> {
                if (GUIView.getFocusOwner() != null) {
                    InvoicesLineController.deleteItem(GUIView, invoices);
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    InvoicesHeaderController.updateTableTotal(GUIView, invoices);
                    InvoicesLineController.updater(GUIView, invoices, selectedRow);
                    TablesController.loadInvoicesLineTable(GUIView, invoices);

                    int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(GUIView.getInvoiceTable().getSelectedRow()).getInvoicerow().size();

                    if (sizeOfinvoicesLinesForTheSelectedInvoice > 0) {
                        GUIView.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                    }
                }
            }

            case "Cancel Any Changes" -> {
                if ((FileOperations.selectedInvoiceHeader != null) && (FileOperations.selectedInvoiceLine != null)) {
                    GUIView.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    invoices = fileOperations.readFile();
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    TablesController.loadInvoicesHeaderTable(GUIView, invoices);

                    maxNumberOfExistedInvoices = 0;
                    fileOperations.getMaxNumberOfExistedInvoices(maxNumberOfExistedInvoices, invoices);
                    GUIView.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);

                    if (invoices.size() >= 1) {
                        GUIView.getInvoiceTable().setRowSelectionInterval(0, 0);
                    }
                }
            }
            
            case "Save Items" -> {
                 try {
                    fileOperations.writeFile(Controller.invoices);
                } catch (IOException ex) {
                    ex.getMessage();
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent pressedKeyEvent) {
        char price = pressedKeyEvent.getKeyChar();
        if (Character.isLetter(price) && !pressedKeyEvent.isAltDown() && pressedKeyEvent.isShiftDown() && pressedKeyEvent.isControlDown()) {
            pressedKeyEvent.consume();
        }
        
        if ((price == 'f') || (price == 'd')) {
            pressedKeyEvent.consume();
        }
        
        try {
            Float.parseFloat(GUIView.getNewItemPrice().getText() + price);
        } catch (NumberFormatException e) {
            pressedKeyEvent.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
