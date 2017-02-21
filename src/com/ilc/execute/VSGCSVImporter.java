package com.ilc.execute;

import com.evnt.client.modules.MenuGroupNameConst;
import com.fbi.plugins.FishbowlPlugin;
import com.ilc.worker.MutualWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Owner on 2/21/2017.
 */
public class VSGCSVImporter extends FishbowlPlugin {

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
        MutualWorker mw = new MutualWorker();
        String csvFileName = mw.getCSVFile();

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
