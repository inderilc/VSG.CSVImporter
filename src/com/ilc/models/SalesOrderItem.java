package com.ilc.models;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Owner on 4/10/2016.
 */
public class SalesOrderItem {
    public String UniqueReference;
    public String Name;
    public String Address1;
    public String Address2;
    public String Address3;
    public String City;
    public String Zipcode;
    public String State;
    public String Country;
    public String ProductNumber;
    public String ProductQuantity;
    public String ProductDescription;
    public String Flag;
    public String SOItemTypeID;
    public String UOM;
    public String ProductPrice;
    public String Taxable;
    public String TaxCode;
    public String Note;
    public String QuickBooksClassName;
    public String FulfillmentDate;
    public String ShowItem;
    public String KitItem;
    public String RevisionLevel;

    public SalesOrderItem(){};

    public String printOverStock_SOIData() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
        //Write data
        String [] data;
        data = new String [] {Flag, SOItemTypeID, ProductNumber, ProductDescription, ProductQuantity, UOM, ProductPrice, Taxable, TaxCode, Note, QuickBooksClassName, FulfillmentDate, ShowItem, KitItem, RevisionLevel};
        writer.writeNext(data);
        writer.close();
        return sw.toString();
    }
    public String printOverStock_SOIHeader() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
        //Write header
        String strHeader [] = new String[]{"Flag", "SOItemTypeID", "ProductNumber", "ProductDescription", "ProductQuantity", "UOM", "ProductPrice", "Taxable", "TaxCode", "Note", "QuickBooksClassName", "FulfillmentDate", "ShowItem", "KitItem", "RevisionLevel"};
        writer.writeNext(strHeader);
        writer.close();
        return sw.toString();
    }
    public String printTemplate_SOIData() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
        //Write data
        String [] data;
        data = new String [] {Flag, SOItemTypeID, ProductNumber, ProductDescription, ProductQuantity, UOM, ProductPrice, Taxable, TaxCode, Note, QuickBooksClassName, FulfillmentDate, ShowItem, KitItem, RevisionLevel};
        writer.writeNext(data);
        writer.close();
        return sw.toString();
    }
    public String printTemplate_SOIHeader() throws IOException
    {
        StringWriter sw = new StringWriter();
        CSVWriter writer = new CSVWriter(sw, ',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
        //Write header
        String strHeader [] = new String[]{"Flag", "SOItemTypeID", "ProductNumber", "ProductDescription", "ProductQuantity", "UOM", "ProductPrice", "Taxable", "TaxCode", "Note", "QuickBooksClassName", "FulfillmentDate", "ShowItem", "KitItem", "RevisionLevel"};
        writer.writeNext(strHeader);
        writer.close();
        return sw.toString();
    }

}
