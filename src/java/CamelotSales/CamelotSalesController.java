/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotSales;

import SalesX.SoldItem;
import Service.Basement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotSalesController {

    @Autowired
    private Basement basement;

    @RequestMapping(value = "goForCamelotMonthSalesUpload")
    public String goForCamelotMonthSalesUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Camelot Last Six Months Upload");
        model.addAttribute("uploadTarget", "camelotMonthSalesUpload.htm");
        return "monthSales/camelotSalesUpload";
    }

    @RequestMapping(value = "camelotMonthSalesUpload")
    public String camelotMonthSalesUpload(@RequestParam String date, ModelMap model) {
        CamelotSalesDao camelotSalesDao = new CamelotSalesDao();

        LinkedHashMap<String, SoldItem> camelotAllItemsForSales = camelotSalesDao.getCamelotItemsForSales();

        LinkedHashMap<String, SoldItem> sodlItems = camelotSalesDao.getMonthSalesFromMicrosoftDB(date, camelotAllItemsForSales);
        String result = camelotSalesDao.insertNewUpload(date, sodlItems);
        return "monthSales/camelotSalesUpload";
    }

    /*   @RequestMapping(value = "/camelotMonthSalesUpload", method = RequestMethod.POST)
    public String camelotMonthSalesUpload(@RequestParam CommonsMultipartFile file, @RequestParam String date, ModelMap model) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("CAMELOT Last Six Months Sales Upload: Starting .............. ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        String filename = "camelotMonthSales.xlsx";
        String filePath = this.basement.getBasementDirectory() + "/Pet4U_Uploads/" + filename;
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "No file has been selected");
            return "monthSales/monthSalesUpload";
        }

        if (date.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "No date has been selected");
            return "monthSales/monthSalesUpload";
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
            return "monthSales/monthSalesUpload";
        }
        CamelotDao camelotDao = new CamelotDao();
        LinkedHashMap<String, Item> camelotAllItems = camelotDao.getCamelotItems();

        CamelotSalesFactory salesFactory = new CamelotSalesFactory();
        ArrayList<SoldItem> sodlItems = salesFactory.createSoldItemsFromUploadedFile(filePath, camelotAllItems);
        CamelotSalesDao camelotSalesDao = new CamelotSalesDao();
        String result = camelotSalesDao.insertNewUpload(date, sodlItems);

        System.out.println("DATE:" + date);
        model.addAttribute("uploadTitle", "Camelot Last Six Months Upload");
        model.addAttribute("uploadTarget", "camelotMonthSalesUpload.htm");
        model.addAttribute("uploadStatus", result);

        return "monthSales/monthSalesUpload";
    }
     */
    @RequestMapping(value = "camelotSixMonthsSales")
    public String camelotSixMonthsSales(ModelMap model) {

        CamelotSalesDao monthSalesDao = new CamelotSalesDao();
        ArrayList<String> period = monthSalesDao.getSalesPeriod();
        List<String> sixMonthsPeriod =  period.subList(period.size() - 7, period.size() - 1);
        LinkedHashMap<String, Double> camelotSales = monthSalesDao.getSales(sixMonthsPeriod);
        System.out.println("S:"+camelotSales.size());
        return "camelot/camelotSixMonthsSales";
    }
}
