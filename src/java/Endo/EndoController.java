package Endo;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import Pet4uItems.Pet4uItemsDao;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EndoController {

    ArrayList<String> endoIdsArray;
    ArrayList<String> receivingEndoIdsArray;

    public EndoController() {
        endoIdsArray = new ArrayList<>();
        receivingEndoIdsArray = new ArrayList<>();

    }

    @RequestMapping(value = "deltioApostolis", method = RequestMethod.GET)
    public String deltioApostolis(ModelMap modelMap) {

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = pet4uItemsDao.getPet4UItemsRowByRow();

        modelMap.addAttribute("pet4UItemsRowByRow", pet4UItemsRowByRow);

        return "endo/deltioApostolis";

    }

    @RequestMapping(value = "/saveEndo", method = RequestMethod.POST)
    public String save(ModelMap modelMap, @ModelAttribute("endo") Endo endo) {

        if (endo.getDateString() != null) {
            System.out.println("DA " + endo.getDateString());
            EndoDao endoDao = new EndoDao();
            String result = endoDao.saveDeltioApostolis(endo);

        } else {
            System.out.println("Date String is NULL");
            modelMap.addAttribute("endo", endo);
            modelMap.addAttribute("result", "Date String is NULL");
            return "redirect:endoDashboard.htm";
        }
        modelMap.addAttribute("endo", endo);
        return "endo/endoDashboard";
    }

    @RequestMapping(value = "compareEndos", method = RequestMethod.POST)
    public String compareEndos(@RequestParam(name = "endoIds") String endoIds,
            @RequestParam(name = "receivingEndoIds") String receivingEndoIds,
            ModelMap modelMap) {

        this.endoIdsArray = createItemsIdsArray(endoIds);
        this.receivingEndoIdsArray = createItemsIdsArray(receivingEndoIds);
        LinkedHashMap<String, DeliveryItem> sentItems = new LinkedHashMap<>();
        LinkedHashMap<String, DeliveryItem> deliveredIetms = new LinkedHashMap<>();
        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();
        if (!endoIdsArray.isEmpty()) {
            sentItems = endoDao.getSentItems(endoIdsArray, pet4UItemsRowByRow);
        }
        if (!receivingEndoIdsArray.isEmpty()) {
            deliveredIetms = endoDao.getReceivedItems(receivingEndoIdsArray, pet4UItemsRowByRow);
        }
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        for (Map.Entry<String, DeliveryItem> deliveredIetmsEntry : deliveredIetms.entrySet()) {
            DeliveryItem deliveredItem = deliveredIetmsEntry.getValue();

            String altercode = deliveredIetmsEntry.getKey();

            DeliveryItem sentItem = sentItems.remove(altercode);
            Item itemWithDescription = pet4UItemsRowByRow.get(altercode);

            if (sentItem == null) {
                System.out.println("SENT ITEM IS NULL :" + altercode);
                if (itemWithDescription == null) {
                    deliveredItem.setDescription("NO DATA FOR THIS CODE");
                } else {
                    deliveredItem.setDescription(itemWithDescription.getDescription());
                }
                deliveredItem.setSentQuantity("0");
                deliveredIetms.put(altercode, deliveredItem);
            } else {
                if (itemWithDescription == null) {
                    deliveredItem.setDescription("NO DATA FOR THIS CODE");
                } else {
                    deliveredItem.setDescription(itemWithDescription.getDescription());
                }
                deliveredItem.setSentQuantity(sentItem.getSentQuantity());
                deliveredIetms.put(altercode, deliveredItem);
            }
        }

        if (sentItems.size() > 0) {

            System.out.println("LEFT OVERS: " + sentItems.size());
            for (Map.Entry<String, DeliveryItem> sentItemsEntry : sentItems.entrySet()) {
                System.out.println("LEFTO OVER ITEM:" + sentItemsEntry.getKey());
                String key = sentItemsEntry.getKey();
                DeliveryItem di = sentItems.get(key);

                Item itemWithDescription = pet4UItemsRowByRow.get(key);
                if (itemWithDescription == null) {
                    di.setDescription("NO DATA FOR THIS CODE");
                } else {
                    di.setDescription(itemWithDescription.getDescription());
                }

                di.setDeliveredQuantity("0");
                deliveredIetms.put(key, di);
            }

        }

        deliveryInvoice.setItems(deliveredIetms);

        modelMap.addAttribute(
                "deliveryInvoice", deliveryInvoice);

        return "endo/endoChecking";

    }

    private ArrayList<String> createItemsIdsArray(String itemsIds) {
        ArrayList idsArray = new ArrayList();
        //trimming and cleaning input
        itemsIds = itemsIds.trim();
        if (itemsIds.length() == 0) {
            return new ArrayList<String>();
        }
        if (itemsIds.substring(itemsIds.length() - 1, itemsIds.length()).equals(",")) {
            itemsIds = itemsIds.substring(0, itemsIds.length() - 1).trim();
        }
        String[] ids = itemsIds.split(",");
        idsArray.addAll(Arrays.asList(ids));
        return idsArray;

    }

    @RequestMapping(value = "showDeltiaApostolisOfItem", method = RequestMethod.GET)
    public String showDeltiaApostolisOfItem(@RequestParam(name = "itemCode") String itemCode, ModelMap modelMap) {

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = pet4uItemsDao.getPet4UItemsRowByRow();

        String sentItemDescription = pet4UItemsRowByRow.get(itemCode).getDescription();

        EndoDao endoDao = new EndoDao();
        ArrayList<Endo> endos = endoDao.getEndosOfItem(itemCode, this.endoIdsArray);
        System.out.println("---------------");
        modelMap.addAttribute("itemCode", itemCode);
        modelMap.addAttribute("sentItem", sentItemDescription);
        modelMap.addAttribute("endos", endos);
        return "endo/deltiaApostolisDisplay";
    }

    @RequestMapping(value = "bindDeltiaApostolisKaiParalavis", method = RequestMethod.GET)
    public String bindDeltiaApostolisKaiParalavis() {
        EndoDao endoDao = new EndoDao();

        if (this.receivingEndoIdsArray.size() == 1) {
            endoDao.bindDeltiaApostolisKaiParalavis(this.endoIdsArray, this.receivingEndoIdsArray.get(0));

        } else {
            System.out.println("this.receivingEndoIdsArray.size():" + this.receivingEndoIdsArray.size());
        }

        return "redirect:endoDashboard.htm";
    }

    //------------------------------------------------------------------------------------
    @RequestMapping(value = "endoDashboard", method = RequestMethod.GET)
    public String endoDashboard(ModelMap modelMap) {

        EndoDao endoDao = new EndoDao();

        LinkedHashMap<String, Endo> incomingEndos = endoDao.getLastIncomingEndos(30);
        LinkedHashMap<String, Endo> receivingEndos = endoDao.getLastReceivingEndos(30);
        LinkedHashMap<String, BindedEndos> bindedEndos = endoDao.getAllBindedEndos();

        LinkedHashMap<String, BindedEndos> filteredBinder = new LinkedHashMap();

        for (Map.Entry<String, BindedEndos> bindedEndosEndtry : bindedEndos.entrySet()) {
            String bindedEndosId = bindedEndosEndtry.getKey();
            BindedEndos bindedEndoWrapper = bindedEndosEndtry.getValue();

            if (receivingEndos.containsKey(bindedEndosId)) {

                bindedEndoWrapper.setBindingReceivingEndoId(bindedEndosId);
                bindedEndoWrapper.setBindingReceivingEndo(receivingEndos.remove(bindedEndosId));

                LinkedHashMap<String, Endo> bindedSendingEndos = bindedEndoWrapper.getBindedSendingEndos();

                for (Map.Entry<String, Endo> bindedSendingEndosEntry : bindedSendingEndos.entrySet()) {
                    String sendingEndoId = bindedSendingEndosEntry.getKey();
                    if (incomingEndos.containsKey(sendingEndoId)) {
                        bindedSendingEndos.put(sendingEndoId, incomingEndos.remove(sendingEndoId));
                    }
                }
                filteredBinder.put(bindedEndosId, bindedEndoWrapper);
            }

        }

        modelMap.addAttribute("incomingEndos", incomingEndos);
        if (receivingEndos.containsKey("359761")) {
            receivingEndos.remove("359761");
        }
        if (receivingEndos.containsKey("360140")) {
            receivingEndos.remove("360140");
        }
        if (receivingEndos.containsKey("362480")) {
            receivingEndos.remove("362480");
        }
        if (receivingEndos.containsKey("362926")) {
            receivingEndos.remove("362926");
        }
        if (receivingEndos.containsKey("381889")) {
            receivingEndos.remove("381889");
        }
        if (receivingEndos.containsKey("383428")) {
            receivingEndos.remove("383428");
        }
        if (receivingEndos.containsKey("383703")) {
            receivingEndos.remove("383703");
        }
        if (receivingEndos.containsKey("388760")) {
            receivingEndos.remove("388760");
        }
        if (receivingEndos.containsKey("402796")) {
            receivingEndos.remove("402796");
        }

        modelMap.addAttribute("receivingEndos", receivingEndos);
        modelMap.addAttribute("bindedEndos", filteredBinder);

        return "endo/endoDashboard";
    }

    @RequestMapping(value = "showDeltioApostolis", method = RequestMethod.GET)
    public String showDeltioApostolis(@RequestParam(name = "id") String id, ModelMap modelMap) {
        System.out.println(id);

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = pet4uItemsDao.getPet4UItemsRowByRow();
        EndoDao endoDao = new EndoDao();

        Endo endo = endoDao.getEndo(id, pet4UItemsRowByRow);

        modelMap.addAttribute("endo", endo);
        return "endo/deltioApostolisDisplay";
    }

    @RequestMapping(value = "showDeltioParalavis", method = RequestMethod.GET)
    public String showDeltioParalavis(@RequestParam(name = "id") String id, ModelMap modelMap) {
        System.out.println(id);

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = pet4uItemsDao.getPet4UItemsRowByRow();
        EndoDao endoDao = new EndoDao();

        Endo endo = endoDao.getEndoParalavis(id, pet4UItemsRowByRow);

        modelMap.addAttribute("endo", endo);
        return "endo/deltioApostolisDisplay";
    }

    @RequestMapping(value = "endosChecking", method = RequestMethod.POST)
    public String endosChecking(@RequestParam(name = "endoIds") String endoIds, ModelMap modelMap) {
        this.endoIdsArray = createItemsIdsArray(endoIds);

        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(endoIdsArray, pet4UItemsRowByRow);

        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        deliveryInvoice.setItems(sentItems);

        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        ArrayList<Item> listValues = new ArrayList<Item>(pet4UItemsRowByRow.values());
        modelMap.addAttribute("pet4UItemsRowByRow", listValues);

        return "endo/endosChecking";

    }

    @RequestMapping(value = "endoDeliveryChecking", method = RequestMethod.POST)
    public String endoDeliveryChecking(@RequestParam(name = "endoIds") String endoIds, ModelMap modelMap) {
        this.endoIdsArray = createItemsIdsArray(endoIds);

        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(endoIdsArray, pet4UItemsRowByRow);

        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        deliveryInvoice.setItems(sentItems);

        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);

        ArrayList<Item> listValues = new ArrayList<Item>(pet4UItemsRowByRow.values());
        modelMap.addAttribute("pet4UItemsRowByRow", listValues);

        ArrayList<AltercodeContainer> pet4UAllAltercodeContainers = endoDao.getAllAltercodeContainers();
        modelMap.addAttribute("pet4UAllAltercodeContainers", pet4UAllAltercodeContainers);
        
        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('saveEndoDeliveryChecking.htm')\"><H1>Save  ENDO Delivery Checking</H1></button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "endo/endoDeliveryChecking";

    }

    @RequestMapping(value = "saveEndoDeliveryChecking", method = RequestMethod.POST)
    public String saveEndoDeliveryChecking(@RequestParam(name = "sentItems") String sentItemsData,
            @RequestParam(name = "deliveredItems") String deliveredItemsData) {
        System.out.println(sentItemsData);
        System.out.println(deliveredItemsData);
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        String endoDeliveryId = LocalDateTime.now().toString();
        deliveryInvoice.setInvoiceId(endoDeliveryId);

        LinkedHashMap<String, String> deliveredItems = decodeDeliveredItemsData(deliveredItemsData);
        LinkedHashMap<String, String> sentItems = decodeDeliveredItemsData(sentItemsData);

        ArrayList<DeliveryItem> deliveryItems = new ArrayList<>();
        for (Map.Entry<String, String> deliveredItemsEntry : deliveredItems.entrySet()) {
            DeliveryItem deliveryItem = new DeliveryItem();
            deliveryItem.setCode(deliveredItemsEntry.getKey());
            deliveryItem.setDeliveredQuantity(deliveredItemsEntry.getValue());
            deliveryItem.setSentQuantity(sentItems.get(deliveredItemsEntry.getKey()));
            deliveryItems.add(deliveryItem);
        }

        EndoDao endoDao = new EndoDao();
        String result = endoDao.saveEndoDeliveryChecking(endoDeliveryId, deliveryItems, this.endoIdsArray);

        System.out.println("Saved Endos Paralavis: ");
        return "redirect:endoParalaves_B.htm";
    }

    @RequestMapping(value = "endoDeliveryJointLoad", method = RequestMethod.POST)
    public String endoDeliveryJointLoad(@RequestParam(name = "endoIds") String endoIds,
            ModelMap modelMap) {

        this.endoIdsArray = createItemsIdsArray(endoIds);

        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();

        DeliveryInvoice lastEndoDelivery = endoDao.getLastEndoDelivery();

        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(endoIdsArray, pet4UItemsRowByRow);

        LinkedHashMap<String, DeliveryItem> items = lastEndoDelivery.getItems();

        for (Map.Entry<String, DeliveryItem> itemsEntry : items.entrySet()) {
            itemsEntry.getValue().setDescription(pet4UItemsRowByRow.get(itemsEntry.getKey()).getDescription());
            if (sentItems.containsKey(itemsEntry.getKey())) {
                itemsEntry.getValue().setSentQuantity(sentItems.get(itemsEntry.getKey()).getSentQuantity());
                sentItems.remove(itemsEntry.getKey());
            } else {
                itemsEntry.getValue().setSentQuantity("0");
            }
        }
        if (sentItems.size() > 0) {
            for (Map.Entry<String, DeliveryItem> sentItemsEntry : sentItems.entrySet()) {
                items.put(sentItemsEntry.getKey(), sentItemsEntry.getValue());
            }
        }

        modelMap.addAttribute("deliveryInvoice", lastEndoDelivery);

        ArrayList<Item> listValues = new ArrayList<Item>(pet4UItemsRowByRow.values());
        modelMap.addAttribute("pet4UItemsRowByRow", listValues);

        String saveButton = "<button class=\"btn-primary\" onclick=\"requestRouter('updateEndoDeliveryChecking.htm')\"><H1>UPDATE  ENDO Delivery Checking</H1></button>";
        modelMap.addAttribute("saveButton", saveButton);
        return "endo/endoDeliveryChecking";

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
            String[] code_text = codePart.split("@");
            String code = code_text[0];
            decodedData.put(code, quantity);
        }

        return decodedData;
    }

    @RequestMapping(value = "showbindedEndos", method = RequestMethod.GET)
    public String showbindedEndos(@RequestParam(name = "binderId") String binderId, ModelMap modelMap) {
        EndoDao endoDao = new EndoDao();
        this.receivingEndoIdsArray = new ArrayList();

        this.receivingEndoIdsArray.add(binderId);

        this.endoIdsArray = endoDao.getBindedIds(binderId);

        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(this.endoIdsArray, pet4UItemsRowByRow);
        LinkedHashMap<String, DeliveryItem> deliveredIetms = endoDao.getReceivedItems(this.receivingEndoIdsArray, pet4UItemsRowByRow);

        System.out.println("SENT ITEMS SIZE: " + sentItems.size());
        System.out.println("DELIVERED ITEMS SIZE: " + deliveredIetms.size());
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        for (Map.Entry<String, DeliveryItem> deliveredIetmsEntry : deliveredIetms.entrySet()) {
            DeliveryItem deliveredItem = deliveredIetmsEntry.getValue();

            String altercode = deliveredIetmsEntry.getKey();

            DeliveryItem sentItem = sentItems.remove(altercode);
            Item itemWithDescription = pet4UItemsRowByRow.get(altercode);

            if (sentItem == null) {
                if (itemWithDescription == null) {

                    deliveredItem.setDescription("NO DATA FOR THIS CODE");
                } else {
                    deliveredItem.setDescription(itemWithDescription.getDescription());
                }
                deliveredItem.setSentQuantity("0");
                deliveredIetms.put(altercode, deliveredItem);
            } else {

                if (itemWithDescription == null) {

                    deliveredItem.setDescription("NO DATA FOR THIS CODE");
                } else {
                    deliveredItem.setDescription(itemWithDescription.getDescription());
                }
                deliveredItem.setSentQuantity(sentItem.getSentQuantity());
                deliveredIetms.put(altercode, deliveredItem);
            }
        }

        if (sentItems.size() > 0) {

            System.out.println("LEFT OVERS: " + sentItems.size());
            for (Map.Entry<String, DeliveryItem> sentItemsEntry : sentItems.entrySet()) {
                System.out.println("LEFTO OVER ITEM:" + sentItemsEntry.getKey());
                String key = sentItemsEntry.getKey();
                DeliveryItem di = sentItems.get(key);

                Item itemWithDescription = pet4UItemsRowByRow.get(key);
                di.setDescription(itemWithDescription.getDescription());
                di.setDeliveredQuantity("0");
                deliveredIetms.put(key, di);
            }

        }

        deliveryInvoice.setItems(deliveredIetms);

        modelMap.addAttribute(
                "binderId", binderId);
        modelMap.addAttribute(
                "deliveryInvoice", deliveryInvoice);

        return "endo/bindedEndosDisplay";

    }

    @RequestMapping(value = "/unbindEndos", method = RequestMethod.GET)
    public String unbindEndos(ModelMap modelMap, @ModelAttribute("binderId") String binderId) {

        EndoDao endoDao = new EndoDao();
        String result = endoDao.unbindeEndos(binderId);

        return "redirect:endoParalaves.htm";
    }

    @RequestMapping(value = "endosBarcodification", method = RequestMethod.POST)
    public String endosBarcodification(@RequestParam(name = "endoIds") String endoIds, ModelMap modelMap) {
        this.endoIdsArray = createItemsIdsArray(endoIds);

        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(endoIdsArray, pet4UItemsRowByRow);

        int index = 0;
        for (Map.Entry<String, DeliveryItem> sentItemsEntrySet : sentItems.entrySet()) {
            Item item = sentItemsEntrySet.getValue();
            String mainBarcode = item.getCode();
            if (mainBarcode == null) {
                System.out.println("mainBarcode Null");
                modelMap.addAttribute("message", "Can't print this label. mainBarcode is NULL. Ask for help");

            }
            OutputStream out = null;
            try {

                Code128Bean barcode128Bean = new Code128Bean();
                barcode128Bean.setCodeset(Code128Constants.CODESET_B);
                final int dpi = 100;
                //Configure the barcode generator
                //adjust barcode width here
                barcode128Bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));
                barcode128Bean.doQuietZone(false);
                barcode128Bean.setBarHeight(15);
                //bean.setVerticalQuietZone(3);
                barcode128Bean.setQuietZone(0);
                barcode128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);

                //Open output file
                File outputFile = new File("C:/Pet4U_3.0/barcodification/barcodification" + index + ".png");
                out = new FileOutputStream(outputFile);
                try {
                    BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(
                            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

                    barcode128Bean.generateBarcode(canvasProvider, mainBarcode);

                    try {
                        canvasProvider.finish();
                    } catch (IOException ex) {
                        Logger.getLogger(EndoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } finally {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(EndoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // model.addAttribute("code", code);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EndoController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(EndoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            index++;

        }

        //-------------
        //String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        // BarcodePrinter barcodePrinter = new BarcodePrinter();
        String printName = "HP LaserJet Pro MFP M127-M128 PCLmS";
        BarcodificationPrinter barcodePrinter = new BarcodificationPrinter();
//---------------

        int labelsCount = Integer.parseInt("1");
        barcodePrinter.setLabelsCount(labelsCount);

        barcodePrinter.printSomething(printName);
        return "endo/endoBarcodification";

    }

}
