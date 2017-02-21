package com.ilc.execute;

import com.evnt.client.modules.MenuGroupNameConst;
import com.evnt.fb.util.FBException;
import com.evnt.util.FbiMessage;
import com.fbi.fbo.impl.ApiCallType;
import com.fbi.fbo.impl.dataexport.QueryRow;
import com.fbi.fbo.impl.message.request.ImportRequestImpl;
import com.fbi.fbo.impl.message.request.QuickShipRequestImpl;
import com.fbi.fbo.message.request.QuickShipRequest;
import com.fbi.fbo.message.response.ImportResponse;
import com.fbi.fbo.message.response.QuickShipResponse;
import com.fbi.gui.util.UtilGui;
import com.fbi.plugins.FishbowlPlugin;
import com.ilc.models.OrdersStruct;
import com.ilc.util.SOLogger;
import com.ilc.worker.VSGWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Owner on 2/21/2017.
 */
public class VSGCSVImporter extends FishbowlPlugin {

    private java.util.List<ArrayList<String>> VSGSoCSV;
    private java.util.List<OrdersStruct> os;

    public VSGCSVImporter()
    {
        setModuleName("VSG Import Orders");
        setMenuGroup(MenuGroupNameConst.SALES);
        setDefaultHelpPath("");

    }
    @Override
    protected void initModule() {
        super.initModule();
        initComponents();
        setButtonEmailVisible(false);
        setButtonPrintVisible(false);
    }

    private void btnSelectMouseClicked(MouseEvent e) {
        // TODO add your code here

        VSGWorker vsg = new VSGWorker(this);

        int num = vsg.ordersParsed();
        writeTextArea("Total of "+num+" orders in CSV file.");
        //osw.ConvertToSalesOrder();
        VSGSoCSV=vsg.VSGSoCSV();
        os=vsg.os;
        btnImport.setEnabled(true);

    }
    private void writeTextArea(String st)
    {
        loggingArea.setText((loggingArea.getText()+System.lineSeparator()+st).trim());
    }

    private void btnImportMouseClicked(MouseEvent e) {
        // TODO add your code here

        int count=1;
        for(OrdersStruct o :os)
        {
            writeTextArea("Attempting Importing Sales Order "+count+"/"+VSGSoCSV.size()+".");
            try
            {
                ImportOrder(o.soCSV,o.so.PONum);
            }
            catch(FBException ex)
            {

            }
            count++;
        }
        btnImport.setEnabled(false);
    }

    private void ImportOrder(ArrayList<String> CSV, String cpo) throws FBException {
        ImportRequestImpl rq = new ImportRequestImpl();
        rq.setImportType("ImportSalesOrder");
        rq.setRows(CSV);
        ImportResponse rs = (ImportResponse) runApiRequest(ApiCallType.IMPORT, rq);
        if (FbiMessage.SUCCESS.getId() != rs.getStatusCode()) {
            //writeTextArea(rs.getStatusMessage());
            writeTextArea("Error: Importing Sales Order failed.");
            SOLogger.error("Error: Importing Sales Order. " + rs.getStatusCode() + " " + rs.getStatusMessage(), null);
            UtilGui.showMessageDialog(rs.getStatusMessage(), "Error Importing Sales Order", 0);
        } else {
            //UtilGui.showMessageDialog("Order Imported!", "Success", JOptionPane.INFORMATION_MESSAGE);
            QueryRow oNum;
            String query = "select num from so where customerpo='"+cpo+"'";
            java.util.List<QueryRow> data = runQuery(query);

            oNum = data.get(0);
            String num = oNum.getString("num");
            writeTextArea("Imported Sales Order (#"+num+") successfully.");

            QuickShipRequest qfRq = new QuickShipRequestImpl();
            qfRq.setSalesOrderNumber(num);
            QuickShipResponse qfRS = (QuickShipResponse) runApiRequest(ApiCallType.QUICK_SHIP, qfRq);

            if (FbiMessage.SUCCESS.getId() != qfRS.getStatusCode())
            {
                writeTextArea("Error: Quickfilling Order failed.");
                SOLogger.error("Error: Quickfilling Sales Order. " + rs.getStatusCode() + " " + rs.getStatusMessage(), null);
                UtilGui.showMessageDialog(rs.getStatusMessage(), "Error Quickfilling Sales Order", 0);
            }
            else
            {
                writeTextArea("Quickfilled SO # "+num+".");
            }
        }
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        panel2 = new JPanel();
        label1 = new JLabel();
        btnSelect = new JButton();
        btnImport = new JButton();
        scrollPane1 = new JScrollPane();
        loggingArea = new JTextArea();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel1 ========
        {
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //======== panel2 ========
            {
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0, 1.0E-4};

                //---- label1 ----
                label1.setText("Select the CSV File to Import");
                panel2.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 2, 0), 0, 0));

                //---- btnSelect ----
                btnSelect.setText("Select CSV to Import");
                btnSelect.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnSelectMouseClicked(e);
                    }
                });
                panel2.add(btnSelect, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 2, 0), 0, 0));

                //---- btnImport ----
                btnImport.setText("Import CSV File");
                btnImport.setEnabled(false);
                btnImport.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        btnImportMouseClicked(e);
                    }
                });
                panel2.add(btnImport, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 2, 0), 0, 0));
            }
            panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 2, 0), 0, 0));

            //======== scrollPane1 ========
            {

                //---- loggingArea ----
                loggingArea.setRows(10);
                loggingArea.setFocusAccelerator('0');
                scrollPane1.setViewportView(loggingArea);
            }
            panel1.add(scrollPane1, new GridBagConstraints(0, 1, 1, 8, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 2, 0), 0, 0));
        }
        add(panel1, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JPanel panel2;
    private JLabel label1;
    private JButton btnSelect;
    private JButton btnImport;
    private JScrollPane scrollPane1;
    private JTextArea loggingArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
