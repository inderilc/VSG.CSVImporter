package com.ilc.worker;

import javax.swing.*;
import java.io.File;

/**
 * Created by Owner on 11/21/2016.
 */
public class MutualWorker {

    public String getCSVFile()
    {
        String csvFileName="";
        JFileChooser myFile = new JFileChooser();
        myFile.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = myFile.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = myFile.getSelectedFile();
            csvFileName = selectedFile.getAbsolutePath();
        }
        if(!csvFileName.isEmpty())
            return csvFileName;
        else
            return "";
    }
}

