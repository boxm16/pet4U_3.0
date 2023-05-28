package Sales;

import BasicModel.AltercodeContainer;
import Service.Basement;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
public class SalesController {

    @Autowired
    private Basement basement;

    @RequestMapping(value = "/uploadSales", method = RequestMethod.POST)
    public String uploadSales(@RequestParam CommonsMultipartFile file, ModelMap model) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Last Six Months Sales Upload: Starting .............. ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        String filename = "sales.xlsx";
        String filePath = this.basement.getBasementDirectory() + "/Pet4U_Uploads/" + filename;
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "No file has been selected");
            return "localUploads/localUploadsDashboard";
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
            return "localUploads/localUploadsDashboard";
        }

        SalesFactory salesmFactory = new SalesFactory();
        ArrayList<SoldItem> sodlItems = salesmFactory.createSoldItemsFromUploadedFile(filePath);
        SalesDao salesDao = new SalesDao();
        salesDao.deleteSoldItems();
        salesDao.insertNewUpload(sodlItems);

        model.addAttribute("uploadTitle", "Sales Upload");
        model.addAttribute("uploadStatus", "Sales Upload");

        return "localUploads/localUploadsDashboard";
    }

    @RequestMapping(value = "/sixMonthsSales", method = RequestMethod.GET)
    public String sixMonthsSales(ModelMap modelMap) {
        SalesDao salesDao = new SalesDao();
        LinkedHashMap<String, SoldItem> itemsWithPositions = salesDao.getAllPet4uItems();

        HashMap<String, SoldItem> itemsWithSales = salesDao.getSixMonthsSales();
        for (Map.Entry<String, SoldItem> entrySet : itemsWithPositions.entrySet()) {
            SoldItem itemWithPosition = entrySet.getValue();

            ArrayList<AltercodeContainer> altercodeContainers = itemWithPosition.getAltercodes();

            for (AltercodeContainer altercodeContainer : altercodeContainers) {
                String altercode = altercodeContainer.getAltercode();
                SoldItem itemWithSales = itemsWithSales.get(altercode);
                if (itemWithSales == null) {
//do nothing
                } else {

                    itemWithPosition.setCode(itemWithSales.getCode());
                    itemWithPosition.setMeasureUnit(itemWithSales.getMeasureUnit());
                    itemWithPosition.setCoeficient(itemWithSales.getCoeficient());
                    itemWithPosition.setEshopSales(itemWithSales.getEshopSales());
                    itemWithPosition.setShopsSupply(itemWithSales.getShopsSupply());
                    itemWithPosition.setTotalSalesInPieces(itemWithSales.getTotalSalesInPieces());

                }
            }
        }

        modelMap.addAttribute("sixMonthsSales", itemsWithPositions);
       
        return "sales/sixMonthsSales";
    }
}
