/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonthSales;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import SalesX.SalesFactory;
import SalesX.SoldItem;
import Service.Basement;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class MonthSalesController {

    @Autowired
    private Basement basement;

    @Autowired
    private MonthSalesDao monthSalesDao;

    @RequestMapping(value = "goForMonthSalesUpload")
    public String goForMonthSalesUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Upload Sales Of The Month");
        model.addAttribute("uploadTarget", "monthSales.htm");
        return "monthSales/monthSalesUpload";
    }

    @RequestMapping(value = "/monthSales", method = RequestMethod.POST)
    public String uploadMonthSales(@RequestParam CommonsMultipartFile file, @RequestParam String date, ModelMap model) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Last Six Months Sales Upload: Starting .............. ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        String filename = "monthSales.xlsx";
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

        SalesFactory salesFactory = new SalesFactory();
        ArrayList<SoldItem> sodlItems = salesFactory.createSoldItemsFromUploadedFile(filePath);

        String result = monthSalesDao.insertNewUpload(date, sodlItems);

        System.out.println("DATE:" + date);
        model.addAttribute("uploadTitle", "Sales Upload");
        model.addAttribute("uploadStatus", result);

        return "monthSales/monthSalesUpload";
    }

    @RequestMapping(value = "/monthSales", method = RequestMethod.GET)
    public String monthSales(ModelMap modelMap) {
        LinkedHashMap<String, ItemSales> refactoredSales = new LinkedHashMap<>();
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemsWithPositions = pet4uItemsDao.getAllItems();

        LinkedHashMap<String, ItemSales> sales = monthSalesDao.getLastSixMonthsSales();

        for (Map.Entry<String, Item> itemsWithPositionEntry : itemsWithPositions.entrySet()) {
            String key = itemsWithPositionEntry.getKey();
            ItemSales itemSales = sales.get(key);
            if (itemSales == null) {
                System.out.println("THIS CODE NOT IN itemsWithPosition:"+key);
            } else {
                Item itemWithPosition = itemsWithPositionEntry.getValue();
                itemSales.setDescription(itemWithPosition.getDescription());
                itemSales.setPosition(itemWithPosition.getPosition());
                itemSales.setAltercodes(itemWithPosition.getAltercodes());
                itemSales.setState(itemWithPosition.getState());
                refactoredSales.put(key, itemSales);
            }

        }

        modelMap.addAttribute("sales", refactoredSales);
        return "monthSales/monthSales";
    }
}
