package com.ilc.worker;

import com.fbi.fbo.impl.dataexport.QueryRow;
import com.fbi.gui.util.UtilGui;
import com.ilc.execute.VSGCSVImporter;
import com.ilc.models.ImportOrder;
import com.ilc.models.OrdersStruct;
import com.ilc.models.SalesOrder;
import com.ilc.models.SalesOrderItem;
import com.ilc.util.SOLogger;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Owner on 11/21/2016.
 */
public class VSGWorker {

    private String csvFileName;
    private MutualWorker mw;
    private List<String []> fileLines;
    private List<SalesOrder> orders;
    private boolean parsed;
    List<ImportOrder> oLines;
    //private QueryRow billToData;
    private int tracker;
    private VSGCSVImporter accObj;

    public List<OrdersStruct> os;

    public VSGWorker(VSGCSVImporter obj)
    {
        accObj=obj;
    }

    public String getCSVFile()
    {
        mw = new MutualWorker();
        return mw.getCSVFile();
    }
    public int ordersParsed()
    {
        csvFileName = getCSVFile();
        oLines= new ArrayList<ImportOrder>();;

        if (!csvFileName.isEmpty()) {
            try {
                CSVReader reader = null;
                try {
                    reader = new CSVReader(new FileReader(csvFileName));
                    String[] line;
                    reader.readNext(); //ignore header
                    while ((line = reader.readNext()) != null) {
                        ImportOrder o = mapLine(line);
                        oLines.add(o);
                    }
                    parsed=true;
                    //br.close();
                } catch (Exception e) {
                    SOLogger.error("Error Uploading File. " + e.getMessage(),null);
                    UtilGui.showMessageDialog("Error Uploading file: "+e.getMessage(),"Error",0);
                }
            } catch (Exception e) {
                SOLogger.error("Error Uploading File. " + e.getMessage(), null);
                UtilGui.showMessageDialog("Error Uploading file: " + e.getMessage(), "Error", 0);
            }
        }
        return ConvertToSalesOrder();
    }
    private ImportOrder mapLine(String[]data)
    {
        ImportOrder orderLine=new ImportOrder();
        try {
            orderLine.ORDERREF = data[1];
            //orderLine.UNIQUEREF = data[1];
            //orderLine.SHIPTONAME = data[11];
            //orderLine.SHIPTOADR1 = data[12];
            //orderLine.SHIPTOADR2 = data[13];
            //orderLine.SHIPTOADR3 = data[14];
            //orderLine.SHIPTOCITY = data[15];
            //orderLine.SHIPTOZIP = data[17];
            //orderLine.SHIPTOSTATE = data[16];
            //orderLine.SHIPPHONE = data[19];
            //orderLine.SHIPCOUNTRY = data[18];
            //orderLine.SHIPSERVICE = data[49];
            //orderLine.ITEMNAME = data[69];
            orderLine.ITEMNUM = data[2];
            //orderLine.ITEMPRICE = data[74];
            //orderLine.UPC = data[70];
            orderLine.QTY = data[5];
            orderLine.NOTES = data[0];
            orderLine.ShipDate=data[8];
        }
        catch (Exception e) {
            SOLogger.error("Error Mapping Order. " + e.getMessage(), null);
            UtilGui.showMessageDialog("Error Mapping Order: " + e.getMessage(), "Error", 0);
        }
        return orderLine;

    }
    public int ConvertToSalesOrder() {
        List<SalesOrder> soList = new ArrayList<SalesOrder>();

        //String query = "select address.name, address, city, stateconst.CODE as state, zip, COUNTRYCONST.ABBREVIATION as country from address join stateconst on stateconst.id = address.stateid join COUNTRYCONST on COUNTRYCONST.ID=address.COUNTRYID where address.name containing 'overstock'";

        //java.util.List<QueryRow> data = accObj.runQuery(query);

        //billToData=data.get(0);

        if (oLines.size() > 0) {
            int counter = 0;
            tracker=counter;
            do {

                ImportOrder order = oLines.get(counter);

                SalesOrder so = new SalesOrder();
                so.PONum = order.ORDERREF;
                so.Flag = "SO";
                so.Status = "10";
                so.CustomerName = "Acuity Brands Lighting";

                so.BillToAddress.name ="Acuity Brands Lighting";
                so.BillToAddress.stAddress="Accounts Payable RC-05"+"\n"+"PO Box 4775";
                so.BillToAddress.country="US";
                so.BillToAddress.city ="Portland";
                so.BillToAddress.state ="OR";
                so.BillToAddress.zip = "97208";



                /*
                ///so.BillToName = billToData.getString("name");
                //so.BillToAddress = billToData.getString("address");
                //so.BillToCountry = billToData.getString("country");
                //so.BillToCity = billToData.getString("city");
                //so.BillToState = billToData.getString("state");
                //so.BillToZip = billToData.getString("zip");
                */
                //so.BillToName="";
                //

                so.ShipToAddress.name ="Acuity Brands Lighting";
                so.ShipToAddress.stAddress="MPF facility - C/O Laredo Quality Transfe"+"\n"+"502 Nafta Blvd.";
                so.ShipToAddress.country="US";
                so.ShipToAddress.city ="Laredo";
                so.ShipToAddress.state ="TX";
                so.ShipToAddress.zip = "78045";
                /*
                so.ShipToName = order.SHIPTONAME;
                so.ShipToAddress = order.SHIPTOADR1 + " " + order.SHIPTOADR2 + " " + order.SHIPTOADR3;
                so.ShipToCountry = order.SHIPCOUNTRY;
                so.ShipToCity = order.SHIPTOCITY;
                so.ShipToState = order.SHIPTOSTATE;
                so.ShipToZip = order.SHIPTOZIP;
                */



                try {

                    Date date = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(order.ShipDate);
                    so.FulfillmentDate = (new SimpleDateFormat("MM/dd/yyyy")).format(new Date(date.getTime()));
                }
                catch(Exception ex)
                {

                }
                so.Date = (new SimpleDateFormat("MM/dd/yyyy")).format(new Date(((new Date()).getTime())));
                so.Salesman = "admin";
                so.CarrierName = "CEVA Truck";
                so.TaxRateName = "NONE";
                so.Note=order.NOTES;
                so.LocationGroupName="CEVA";
                //so.Phone=order.SHIPPHONE;
                so.Email="aleggett@vsgus.com";
                so.Items = MapLineItems(oLines, counter);

                counter=tracker;
                soList.add(so);
            } while (oLines.size() > tracker);
            orders=soList;
        }
        return soList.size();
    }
    public List<ArrayList<String>> VSGSoCSV()
    {
        os=new ArrayList<>();
        List<ArrayList<String>> csv = new ArrayList<ArrayList<String>>();
        for(SalesOrder so: orders)
        {
            StringBuilder sb = new StringBuilder();
            ArrayList<String> order = new ArrayList<String>();
            List<String> itemsData = new ArrayList<String>();
            try {
                order.add(so.printOverStock_SoHeader());
                if(so.Items.size()>0)
                {
                    order.add(so.Items.get(0).printOverStock_SOIHeader());
                    itemsData=printOSItems(so.Items);
                }
                order.add(so.printOverStock_SoData());
                if(!itemsData.isEmpty()) {
                    order.addAll(itemsData);
                }
                OrdersStruct e = new OrdersStruct();
                e.soCSV=order;
                e.so=so;
                os.add(e);
            }
            catch(IOException ex)
            {

            }
            csv.add(order);
        }
        return csv;
    }
    private List<String> printOSItems(List<SalesOrderItem> items)
    {
        List<String> ret = new ArrayList<String>();
        for(SalesOrderItem itm:items)
        {
            try {
                ret.add(itm.printOverStock_SOIData());
            }
            catch(IOException ex)
            {

            }
        }
        return ret;
    }
    private List<SalesOrderItem> MapLineItems (List<ImportOrder> orderLines, int counter) {
        List<SalesOrderItem> ret = new ArrayList<>();
        String nextOrder;
        String thisOrderNum;
        int len;
        do {
            ImportOrder item = orderLines.get(counter);
            SalesOrderItem soi = new SalesOrderItem();
            thisOrderNum= orderLines.get(counter).ORDERREF;
            String query = "select kitflag from product where product.NUM='"+item.ITEMNUM+"'";
            java.util.List<QueryRow> data = accObj.runQuery(query);

            int itemType=0;
            String strItemType;

            if(data.size()>0) {
                itemType=Integer.parseInt(data.get(0).getString("kitflag"));
                if(itemType==1)
                {
                    strItemType="80";
                }
                else
                    strItemType="10";
            }
            else
                strItemType="10";

            soi.Flag = "Item";
            soi.ProductNumber = item.ITEMNUM;
            //soi.FulfillmentDate = (new SimpleDateFormat("MM/dd/yyyy")).format(new Date(((new Date()).getTime())));
            soi.SOItemTypeID = strItemType;
            soi.ProductDescription = item.ITEMNAME;
            //soi.UOM = item.getUOM().getCode().toString();
            soi.ProductPrice = item.ITEMPRICE;
            //soi.Taxable = item.isTaxable() ? "true" : "false";
            soi.TaxCode = "None";
            //soi.ShowItem = item.showOnSOCombo() ? "true" : "false";
            soi.KitItem = (itemType==1) ? "true" : "false";
            //soi.KitItem = "false";
            //soi.Note = item.ITEMNUM;
            soi.ProductQuantity = item.QTY;
            soi.Note=item.NOTES;
            ret.add(soi);
            counter++;
            if(orderLines.size()<=counter)
            {
                tracker=counter;
                return ret;
            }
            tracker=counter;
            nextOrder = orderLines.get(counter).ORDERREF;
            len = nextOrder.length();
        }while(thisOrderNum.compareTo(nextOrder) == 0);
        return ret;
    }
}