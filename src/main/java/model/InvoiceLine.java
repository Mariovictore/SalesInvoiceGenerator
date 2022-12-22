package model;

public class InvoiceLine {

    String itemName;
    float itemPrice;
    int itemCount;
    float itemTotal;
    InvoiceHeader mainInvoice;
    
    public InvoiceLine() {
    }

    public InvoiceLine(String itemName, float itemPrice, int itemCount, InvoiceHeader mainInvoice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.mainInvoice = mainInvoice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public float getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(float itemTotal) {
        this.itemTotal = itemTotal;
    }

    public InvoiceHeader getMainInvoice() {
        return mainInvoice;
    }

    public void setMainInvoice(InvoiceHeader mainInvoice) {
        this.mainInvoice = mainInvoice;
    }

}
