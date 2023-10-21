/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesByMonth;

import SalesX.SalesFactory;
import SalesX.SoldItem;
import Service.Basement;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class SalesByMonthController {
    @Autowired
    private Basement basement;
    
    @RequestMapping(value = "goForSalesByMonthUpload")
    public String goForSalesByMonthUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Upload Sales Of The Month");
        model.addAttribute("uploadTarget", "uploadSalesOfMonth.htm");
        return "salesByMonth/salesByMonthUpload";
    }
    
    
    @RequestMapping(value = "/uploadSalesOfMonth", method = RequestMethod.POST)
    public String uploadSales(@RequestParam CommonsMultipartFile file, @RequestParam String date,  ModelMap model  ) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Last Six Months Sales Upload: Starting .............. ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        String filename = "monthSales.xlsx";
        String filePath = this.basement.getBasementDirectory() + "/Pet4U_Uploads/" + filename;
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "No file has been selected");
           return "salesByMonth/salesByMonthUpload";
        }
        
        if (date.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "No date has been selected");
            return "salesByMonth/salesByMonthUpload";
        }
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("uploadStatus", "Upload could not been completed:" + e);
            return "salesByMonth/salesByMonthUpload";
        }

        SalesFactory salesFactory = new SalesFactory();
        ArrayList<SoldItem> sodlItems = salesFactory.createSoldItemsFromUploadedFile(filePath);

        
        System.out.println("DATE:"+date);
        model.addAttribute("uploadTitle", "Sales Upload");
        model.addAttribute("uploadStatus", "");

        return "localUploads/localUploadsDashboard";
    }
}
