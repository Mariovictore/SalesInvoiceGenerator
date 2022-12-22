package model;

import java.util.ArrayList;
import java.util.Date;


public class InvoiceHeader {

    private int invoiceNumber;
    private Date invoiceDate;
    private String invoiceCustomerName;
    private float inoviceTotal;
    private ArrayList<InvoiceLine> invoicerow; //composition 

    public InvoiceHeader() {
    }

    public ArrayList<InvoiceLine> getInvoicerow() {
        if (invoicerow == null)
            invoicerow = new ArrayList<>();
        return invoicerow;
    }

    public void setInvoicerow(ArrayList<InvoiceLine> invoicerow) {
        this.invoicerow = invoicerow;
    }

    public InvoiceHeader(int inoviceNumber, Date inoviceDate, String inoviceCustomerName) {
        this.invoiceNumber = inoviceNumber;
        this.invoiceDate = inoviceDate;
        this.invoiceCustomerName = inoviceCustomerName;
    }

    public int getInoviceNumber() {
        return invoiceNumber;
    }

    public void setInoviceNumber(int inoviceNumber) {
        this.invoiceNumber = inoviceNumber;
    }

    public Date getInoviceDate() {
        return invoiceDate;
    }

    public void setInoviceDate(Date inoviceDate) {
        this.invoiceDate = inoviceDate;
    }

    public String getInoviceCustomerName() {
        return invoiceCustomerName;
    }

    public void setInoviceCustomerName(String inoviceCustomerName) {
        this.invoiceCustomerName = inoviceCustomerName;
    }

    public float getInoviceTotal() {
        return inoviceTotal;
    }

    public void setInoviceTotal(float inoviceTotal) {
        this.inoviceTotal = inoviceTotal;
    }

}
