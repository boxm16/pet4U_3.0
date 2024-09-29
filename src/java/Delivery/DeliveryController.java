package Delivery;

import BasicModel.Item;
import Excel.ExcelReader;
import Service.Basement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeliveryController {

    @Autowired
    private DeliveryDao deliveryDao;

    @RequestMapping(value = "deliveryDemo")
    public String deliveryDemo(ModelMap modelMap) {
        LinkedHashMap<String, Item> deliveredItems = new LinkedHashMap();
        //  CamelotItemsOfInterestDao camelotItemsOfInteresDao = new CamelotItemsOfInterestDao();
        //    LinkedHashMap<String, Item> pet4UItemsRowByRow = camelotItemsOfInteresDao.getPet4UItemsRowByRow();

        Item item = new Item();
        item.setDescription("FCN URINARY CARE 2KG");
        String code = "2285020";
        item.setCode(code);
        item.setQuantity("3");
        deliveredItems.put(code, item);

        Item item1 = new Item();
        item1.setDescription("FCN ORAL SENSITIVE 1.5K");
        String code1 = "2284015";
        item1.setCode(code1);
        item1.setQuantity("5");
        deliveredItems.put(code1, item1);

        Item item2 = new Item();
        item2.setDescription("FCN DIGESTIVE COMFORT 2K");
        String code2 = "2280020";
        item2.setCode(code2);
        item2.setQuantity("7");
        deliveredItems.put(code2, item2);
        /*
        Item item3 = new Item();
        item3.setDescription("VD RENAL SPECIAL CAT 400g");
        String code3 = "3037004";
        item3.setCode(code3);
        item3.setQuantity("7");
        deliveredItems.put(code3, item3);
         */
        modelMap.addAttribute("deliveredItems", deliveredItems);
        //-------------------

        Basement basement = new Basement();
        String applicationHostName = basement.getApplicationHostName();
        if (applicationHostName.equals("LAPTOP")) {
            System.out.println("CANT GET DATABASE DATA , you are on your laptop");
        } else {
            DeliveryDao deliveryDao = new DeliveryDao();
            ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

            modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        }
        ArrayList ar = new ArrayList();

        Item it1 = new Item();
        it1.setDescription("ddd");
        it1.setCode("loko");
        ar.add(it1);

        Item it2 = new Item();
        it2.setDescription("second");
        it2.setCode("sukoo");
        ar.add(it2);

        modelMap.addAttribute("ar", ar);
        return "delivery/deliveryDemo";

    }

    @RequestMapping(value = "deliveryDashboard")
    public String deliveryDashboard(ModelMap modelMap) {
        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();
        try {
            Path file = Paths.get(basementDirectory + "/ROYAL/deliveryExport.xlsx");
            BasicFileAttributes attribute = Files.readAttributes(file, BasicFileAttributes.class);

            System.out.println("creationTime: " + attribute.creationTime());
            System.out.println("lastAccessTime: " + attribute.lastAccessTime());
            System.out.println("lastModifiedTime: " + attribute.lastModifiedTime());

            System.out.println("isDirectory: " + attribute.isDirectory());
            System.out.println("isOther: " + attribute.isOther());
            System.out.println("isRegularFile: " + attribute.isRegularFile());
            System.out.println("isSymbolicLink: " + attribute.isSymbolicLink());
            System.out.println("size: " + attribute.size());

            FileTime lastModifiedTime = attribute.lastModifiedTime();
            LocalDateTime localDateTime = lastModifiedTime
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            DateTimeFormatter DATE_FORMATTER
                    = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            String timeStamp = localDateTime.format(DATE_FORMATTER);
            modelMap.addAttribute("timeStamp", timeStamp);

            LocalDate date = LocalDate.now();
            modelMap.addAttribute("date", date);
        } catch (IOException ex) {
            Logger.getLogger(DeliveryController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }

        //-------------------------------
        ArrayList<DeliveryInvoice> allCheckedDeliveryInvoices = deliveryDao.getAllCheckedDeliveryInvoices();
        modelMap.addAttribute("allCheckedDeliveryInvoices", allCheckedDeliveryInvoices);
        return "delivery/deliveryDashboard";
    }

    @RequestMapping(value = "loadRoyalData")
    public String loadRoyalData(ModelMap modelMap) {
        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();

        String applicationHostName = basement.getApplicationHostName();
        if (applicationHostName.equals("LAPTOP")) {
            System.out.println("CANT GET DATABASE DATA , you are on your laptop");
        } else {

            DeliveryInvoice deliveryInvoice = createDeliveryInvoiceFromExcelFile(basementDirectory + "/ROYAL/deliveryExport.xlsx");

            ArrayList<DeliveryInvoice> allCheckedDeliveryInvoices = deliveryDao.getAllCheckedDeliveryInvoices();

            for (DeliveryInvoice checkedDeliveryInvoice : allCheckedDeliveryInvoices) {
                String checkedInvoiceNumber = checkedDeliveryInvoice.getNumber();
                if (deliveryInvoice.getNumber().equals(checkedInvoiceNumber)) {
                    modelMap.addAttribute("checkedInvoiceNumber", checkedInvoiceNumber);
                    return "delivery/deliveryPreloadChecking";
                }
            }

        }
        return "redirect:loadNewRoyalData.htm";
    }

    @RequestMapping(value = "loadNewRoyalData")
    public String loadNewRoyalData(ModelMap modelMap) {
        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();
        DeliveryInvoice deliveryInvoice = createDeliveryInvoiceFromExcelFile(basementDirectory + "/ROYAL/deliveryExport.xlsx");
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        String applicationHostName = basement.getApplicationHostName();
        if (applicationHostName.equals("LAPTOP")) {
            System.out.println("CANT GET DATABASE DATA , you are on your laptop");
        } else {
            DeliveryDao deliveryDao = new DeliveryDao();
            ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

            modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        }
        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('saveCheckUp.htm')\">Save Delivery Checking</button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "delivery/deliveryInvoiceChecking";
    }

    @RequestMapping(value = "reloadRoyalData")
    public String reloadCheckedRoyalData(ModelMap modelMap) {

        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();
        DeliveryInvoice deliveryInvoice = createDeliveryInvoiceFromExcelFile(basementDirectory + "/ROYAL/deliveryExport.xlsx");
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        String applicationHostName = basement.getApplicationHostName();
        if (applicationHostName.equals("LAPTOP")) {
            System.out.println("CANT GET DATABASE DATA , you are on your laptop");
        } else {

            ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

            modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        }

        String saveButton = "<button class=\"btn-danger\" onclick=\"requestRouter('rewriteDeliveryChecking.htm')\">Rewrite Checked Invoice</button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "delivery/deliveryInvoiceReChecking";
    }

    @RequestMapping(value = "loadCheckedRoyalDataFromDatabase")
    public String loadCheckedRoyalDataFromDatabase(@RequestParam(name = "number") String number,
            ModelMap modelMap) {

        DeliveryInvoice deliveryInvoice = deliveryDao.getDeliveryInvoice(number);
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();
        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        LinkedHashMap<String, DeliveryItem> deliveryItems = deliveryInvoice.getItems();
        for (DeliveryItem deliveryItem : pet4UItemsRowByRow) {
            String code = deliveryItem.getCode();
            if (deliveryItems.containsKey(code)) {
                DeliveryItem di = deliveryItems.get(code);
                di.setDescription(deliveryItem.getDescription());
                deliveryItems.put(code, di);
            }
        }
        deliveryInvoice.setItems(deliveryItems);

        String saveButton = "<button class=\"btn-danger\" onclick=\"requestRouter('rewriteDeliveryChecking.htm')\">Rewrite Checked Invoice</button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "delivery/deliveryInvoiceReChecking";
    }

    @RequestMapping(value = "checkDataFromExcelFileAndDatabase")
    public String checkDataFromExcelFileAndDatabase(@RequestParam(name = "number") String number,
            ModelMap modelMap) {

        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();
        DeliveryInvoice deliveryInvoiceFromExcel = createDeliveryInvoiceFromExcelFile(basementDirectory + "/ROYAL/deliveryExport.xlsx");

        String applicationHostName = basement.getApplicationHostName();
        if (applicationHostName.equals("LAPTOP")) {
            System.out.println("CANT GET DATABASE DATA , you are on your laptop");
        } else {
            DeliveryDao deliveryDao = new DeliveryDao();
            ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();

            modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

            DeliveryInvoice deliveryInvoiceFromDatabase = deliveryDao.getDeliveryInvoice(number);
            LinkedHashMap<String, DeliveryItem> deliveryItemsFromExcelFile = deliveryInvoiceFromExcel.getItems();
            LinkedHashMap<String, DeliveryItem> deliveryItemsFromDatabase = deliveryInvoiceFromDatabase.getItems();

            for (DeliveryItem deliveryItem : pet4UItemsRowByRow) {
                String code = deliveryItem.getCode();
                if (deliveryItemsFromDatabase.containsKey(code)) {
                    DeliveryItem di = deliveryItemsFromDatabase.get(code);
                    di.setDescription(deliveryItem.getDescription());
                    deliveryItemsFromDatabase.put(code, di);
                }
            }

            for (Map.Entry<String, DeliveryItem> deliveryItemsEntry : deliveryItemsFromDatabase.entrySet()) {
                //   System.out.println("DELIVERY ITEM CODE:"+deliveryItemsEntry.getValue().getCode()+ "   DELIVERED QUANTITY:"+deliveryItemsEntry.getValue().getDeliveredQuantity());
                DeliveryItem deliveryItemFromExcelFile = deliveryItemsFromExcelFile.remove(deliveryItemsEntry.getKey());
                if (deliveryItemFromExcelFile == null) {
                    deliveryItemsEntry.getValue().setQuantity("0");
                } else {
                    deliveryItemsEntry.getValue().setQuantity(deliveryItemFromExcelFile.getQuantity());
                }
            }
            if (deliveryItemsFromExcelFile.size() > 0) {
                for (Map.Entry<String, DeliveryItem> deliveryItemFromExcelFileEntry : deliveryItemsFromExcelFile.entrySet()) {
                    deliveryInvoiceFromDatabase.getItems().put(deliveryItemFromExcelFileEntry.getKey(), deliveryItemFromExcelFileEntry.getValue());
                }
            }

            modelMap.addAttribute("deliveryInvoice", deliveryInvoiceFromDatabase);

            String saveButton = "<button class=\"btn-danger\" onclick=\"requestRouter('rewriteDeliveryChecking.htm')\">Rewrite Checked Invoice</button>";
            modelMap.addAttribute("saveButton", saveButton);
        }
        return "delivery/deliveryInvoiceReChecking";
    }

   

    public DeliveryInvoice createDeliveryInvoiceFromExcelFile(String filePath) {
        System.out.println("STARTING READING ROYAL DELIVERY EXCEL FILE");
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(filePath);
        DeliveryInvoice deliveryInvoice = convertExcelDataToItems(cellsFromExcelFile);
        System.out.println("READING ROYAL DELIVERY EXCEL FILE COMPLETED");
        return deliveryInvoice;
    }

    private DeliveryInvoice convertExcelDataToItems(HashMap<String, String> data) {
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        LinkedHashMap<String, DeliveryItem> items = new LinkedHashMap();
        int rowIndex = 2;
        while (!data.isEmpty()) {

            String invoiceNumberLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String invoiceNumberString = data.remove(invoiceNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemCodeLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
            String itemCodeString = data.remove(itemCodeLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemDescriptionLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
            String itemDescriptionString = data.remove(itemDescriptionLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String quantityLocationInTheRow = new StringBuilder("G").append(String.valueOf(rowIndex)).toString();
            String quantityString = data.remove(quantityLocationInTheRow);//at the same time reading and removing the cell from hash Map

            if (itemCodeString == null) {//in theory this means that you reached the end of rows with data
                break;
            }

            if (items.containsKey(itemCodeString)) {
                String errorMessage = "Error: one code is 2 times in the list";
                deliveryInvoice.setErrorMessages(deliveryInvoice.getErrorMessages() + ":::" + errorMessage);
            } else {
                DeliveryItem deliveryItem = new DeliveryItem();
                deliveryItem.setCode(itemCodeString);
                if (rowIndex == 2) {
                    deliveryInvoice.setNumber(invoiceNumberString);

                }

                /*

                AltercodeWrapper altercodeWrapper = new AltercodeWrapper();
                altercodeWrapper.setAltercode(altercodeString);
                altercodeWrapper.setStatus(altercodeStatusString);
                item.addAltercode(altercodeWrapper); */
                deliveryItem.setDescription(itemDescriptionString);
                // item.setPosition(itemPositionString);
                deliveryItem.setQuantity(quantityString);
                deliveryItem.setDeliveredQuantity("0");
                // item.setState(itemStateString);
                items.put(itemCodeString, deliveryItem);
            }
            rowIndex++;
        }
        deliveryInvoice.setItems(items);

        return deliveryInvoice;
    }

    

    private LinkedHashMap<String, String> decodeDeliveredItemsData(String data) {
        LinkedHashMap<String, String> decodedData = new LinkedHashMap<>();
        //trimming and cleaning input
        data = data.trim();
        if (data.length() == 0) {
            return decodedData;
        }
        if (data.substring(data.length() - 1, data.length()).equals(",")) {
            data = data.substring(0, data.length() - 1).trim();
        }
        String[] its = data.split(",");
        for (String it : its) {
            String[] item_code_and_quantity = it.split(":");
            String codePart = item_code_and_quantity[0];
            String quantity = item_code_and_quantity[1];
            String[] code_text = codePart.split("_");
            String code = code_text[0];
            decodedData.put(code, quantity);
        }

        return decodedData;
    }

    @RequestMapping(value = "scanninger")
    public String scanninger(ModelMap modelMap) {

        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);
        DeliveryDao deliveryDao = new DeliveryDao();
        ArrayList<DeliveryItem> pet4UItemsRowByRow = deliveryDao.getPet4UItemsRowByRow();
        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        return "delivery/scanningerΧ";
    }

}
