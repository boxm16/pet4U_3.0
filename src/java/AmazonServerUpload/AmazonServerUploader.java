/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmazonServerUpload;

import Service.Basement;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class AmazonServerUploader {

    public void uploadExcelFile() {
        String charset = "UTF-8";
        Basement basement = new Basement();

        File uploadFile1 = new File(basement.getBasementDirectory() + "/pet4uExcelData.xlsx");
        //  File uploadFile2 = new File("e:/Test/PIC2.JPG");
        String requestURL = "http://ec2-54-74-154-166.eu-west-1.compute.amazonaws.com:8080/Pet4U_2.0/linuxServerUpload.htm";

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");

            multipart.addFormField("file", "pet4uExcelData.xlsx");
            // multipart.addFormField("keywords", "Java,upload,Spring");

            multipart.addFilePart("file", uploadFile1);
            // multipart.addFilePart("fileUpload", uploadFile2);

            List<String> response = multipart.finish();
            LocalTime time = LocalTime.now();
            System.out.print("SERVER REPLIED AT: " + time+" :" );

            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(AmazonServerUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
