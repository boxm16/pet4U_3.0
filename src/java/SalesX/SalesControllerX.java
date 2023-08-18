package SalesX;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import Search.SearchDao;
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
public class SalesControllerX {

    @Autowired
    private Basement basement;

    @RequestMapping(value = "goForSalesUploadX")
    public String goForSalesUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Last Six Months Upload");
        model.addAttribute("uploadTarget", "uploadSalesX.htm");
        return "localUploads/localUploadsDashboard";
    }

    @RequestMapping(value = "/uploadSalesX", method = RequestMethod.POST)
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

        SalesFactory salesFactory = new SalesFactory();
        ArrayList<SoldItem> sodlItems = salesFactory.createSoldItemsFromUploadedFile(filePath);
        SalesDaoX salesDao = new SalesDaoX();
        salesDao.deleteSoldItems();
        String insertionResult = salesDao.insertNewUpload(sodlItems);
        model.addAttribute("uploadTitle", "Sales Upload");
        model.addAttribute("uploadStatus", insertionResult);

        return "localUploads/localUploadsDashboard";
    }

    @RequestMapping(value = "/sixMonthsSalesX", method = RequestMethod.GET)
    public String sixMonthsSalesX(ModelMap modelMap) {
        LinkedHashMap<String, Item> soldItems = new LinkedHashMap<>();

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemsWithPositions = pet4uItemsDao.getAllItems();

        SalesDaoX salesDao = new SalesDaoX();
        HashMap<String, SoldItem> itemsWithSales = salesDao.getSixMonthsSalesX();
        for (Map.Entry<String, Item> entrySet : itemsWithPositions.entrySet()) {
            Item itemWithPosition = entrySet.getValue();
            String code = itemWithPosition.getCode();
            SoldItem itemWithSales = itemsWithSales.get(code);
            SoldItem soldItem = new SoldItem();
            if (itemWithSales == null) {
                soldItem.setCode(itemWithPosition.getCode());
                soldItem.setDescription(itemWithPosition.getDescription());
                soldItem.setAltercodes(itemWithPosition.getAltercodes());
                soldItem.setPosition(itemWithPosition.getPosition());
                soldItem.setQuantity(itemWithPosition.getQuantity());
                soldItem.setState(itemWithPosition.getState());
            } else {
                soldItem.setCode(itemWithPosition.getCode());
                soldItem.setDescription(itemWithPosition.getDescription());
                soldItem.setAltercodes(itemWithPosition.getAltercodes());
                soldItem.setPosition(itemWithPosition.getPosition());
                soldItem.setQuantity(itemWithPosition.getQuantity());
                soldItem.setState(itemWithPosition.getState());

                soldItem.setEshopSales(itemWithSales.getEshopSales());
                soldItem.setShopsSupply(itemWithSales.getShopsSupply());
            }
            soldItems.put(code, soldItem);
        }
        modelMap.addAttribute("sixMonthsSales", soldItems);
        return "sales/sixMonthsSalesX";
    }

    @RequestMapping(value = "showItemSales", method = RequestMethod.GET)
    public String showItemSales(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {

        SoldItem soldItem = getItemSales(altercode);
        modelMap.addAttribute("soldItem", soldItem);
        return "sales/itemSales";
    }

    public SoldItem getItemSales(String altercode) {
        SalesDaoX salesDao = new SalesDaoX();
        HashMap<String, SoldItem> sixMonthsSalesX = salesDao.getSixMonthsSalesX();

        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(altercode);
        ArrayList<AltercodeContainer> altercodes = item.getAltercodes();

        for (AltercodeContainer altercodeContainer : altercodes) {
            if (sixMonthsSalesX.keySet().contains(altercodeContainer.getAltercode())) {
                SoldItem soldItem = sixMonthsSalesX.get(altercodeContainer.getAltercode());
                soldItem.setPosition(item.getPosition());

                return soldItem;
            }
        }
        return null;
    }
}
