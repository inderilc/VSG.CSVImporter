package com.ilc.models;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by Owner on 4/10/2016.
 */
public class SalesOrder {
    public String Flag;
    public String SONum;
    public String Status;
    public String CustomerName;
    public String CustomerContact;
    public Address BillToAddress;
    public Address ShipToAddress;
    public String CarrierName;
    public String TaxRateName;
    public String PriorityId;
    public String PONum;
    public String VendorPONum;
    public String Date;
    public String Salesman;
    public String ShippingTerms;
    public String PaymentTerms;
    public String FOB;
    public String Note;
    public String QuickBooksClassName;
    public String LocationGroupName;
    public String FulfillmentDate;
    public String URL;
    public String CarrierService;
    public String DateExpired;
    public String Phone;
    public String Email;
    public List<SalesOrderItem> Items;

    public SalesOrderItem shippingItem;
    public SalesOrderItem salesTaxItem;
    public SalesOrderItem discountItem;

    public int XLarge;
    public int Large;
    public int Medium;
    public int XSmall;
    public int Small;
    public int Envolpe;
    public boolean isPrinted;

    public String discount;
    public String salesTax;
    public String shipping;

    public SalesOrder(){
        BillToAddress = new Address();
        ShipToAddress=new Address();};

    public String printOverStock_SoHeader() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);

        //Write header
        String strHeader [] = new String[]{"Flag", "SONum", "Status", "CustomerName", "CustomerContact", "BillToName", "BillToAddress", "BillToCity", "BillToState", "BillToZip", "BillToCountry", "ShipToName", "ShipToAddress", "ShipToCity", "ShipToState", "ShipToZip", "ShipToCountry", "CarrierName", "TaxRateName", "PriorityId", "PONum", "VendorPONum", "Date", "Salesman", "ShippingTerms", "PaymentTerms", "FOB", "Note", "QuickBooksClassName", "LocationGroupName", "FulfillmentDate", "URL", "CarrierService", "DateExpired", "Phone", "Email"};

        writer.writeNext(strHeader);
        writer.close();
        return sw.toString();
    }

    public String printOverStock_SoData() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
        //Write data
        String [] data;
        data = new String [] {Flag, SONum, Status, CustomerName, CustomerContact, BillToAddress.name, BillToAddress.stAddress, BillToAddress.city, BillToAddress.state, BillToAddress.zip, BillToAddress.country, ShipToAddress.name, ShipToAddress.stAddress, ShipToAddress.city, ShipToAddress.state, ShipToAddress.zip, ShipToAddress.country, CarrierName, TaxRateName, PriorityId, PONum, VendorPONum, Date, Salesman, ShippingTerms, PaymentTerms, FOB, Note, QuickBooksClassName, LocationGroupName, FulfillmentDate, URL, CarrierService, DateExpired, "", Email};
        writer.writeNext(data);
        writer.close();
        return sw.toString();
    }
    public String printTemplate_SoHeader() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);

        //Write header
        String strHeader [] = new String[]{"Flag", "SONum", "Status", "CustomerName", "CustomerContact", "BillToName", "BillToAddress", "BillToCity", "BillToState", "BillToZip", "BillToCountry", "ShipToName", "ShipToAddress", "ShipToCity", "ShipToState", "ShipToZip", "ShipToCountry", "CarrierName", "TaxRateName", "PriorityId", "PONum", "VendorPONum", "Date", "Salesman", "ShippingTerms", "PaymentTerms", "FOB", "Note", "QuickBooksClassName", "LocationGroupName", "FulfillmentDate", "URL", "CarrierService", "DateExpired", "Phone", "Email"};
        //String strHeader [] = new String[]{"Flag", "SONum", "Status", "CustomerName", "CustomerContact", "BillToName", "BillToAddress", "BillToCity", "BillToState", "BillToZip", "BillToCountry", "ShipToName", "ShipToAddress", "ShipToCity", "ShipToState", "ShipToZip", "ShipToCountry", "TaxRateName", "PriorityId", "PONum", "VendorPONum", "Date", "Salesman", "ShippingTerms", "PaymentTerms", "FOB", "Note", "QuickBooksClassName", "LocationGroupName", "FulfillmentDate", "URL", "DateExpired", "Phone", "Email","CF-Ship To Phone","CF-Ship To Email"};

        writer.writeNext(strHeader);
        writer.close();
        return sw.toString();
    }

    public String printTemplate_SoData() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
        //Write data
        String [] data;
        data = new String [] {Flag, SONum, Status, CustomerName, CustomerContact, BillToAddress.name, BillToAddress.stAddress, BillToAddress.city, BillToAddress.state, BillToAddress.zip, BillToAddress.country, ShipToAddress.name, ShipToAddress.stAddress, ShipToAddress.city, ShipToAddress.state, ShipToAddress.zip, ShipToAddress.country, CarrierName, TaxRateName, PriorityId, PONum, VendorPONum, Date, Salesman, ShippingTerms, PaymentTerms, FOB, Note, QuickBooksClassName, LocationGroupName, FulfillmentDate, URL, CarrierService, DateExpired, "","",Phone, Email};
        //data = new String [] {Flag, SONum, Status, CustomerName, CustomerContact, BillToName, BillToAddress, BillToCity, BillToState, BillToZip, BillToCountry, ShipToName, ShipToAddress, ShipToCity, ShipToState, ShipToZip, ShipToCountry, TaxRateName, PriorityId, PONum, VendorPONum, Date, Salesman, ShippingTerms, PaymentTerms, FOB, Note, QuickBooksClassName, LocationGroupName, FulfillmentDate, URL, DateExpired, "","",Phone, Email};
        writer.writeNext(data);
        writer.close();
        return sw.toString();
    }
}