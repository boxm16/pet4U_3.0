package Endo;

import BasicModel.Item;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import Inventory.InventoryDao;
import Notes.NotesDao;
import Pet4uItems.Pet4uItemsDao;
import Service.Basement;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class EndoControllerX {

    private EndoBinder proEndoBinder;

    @RequestMapping(value = "endoParalaves", method = RequestMethod.GET)
    public String endoParalaves(ModelMap modelMap) {
        EndoDaoX endoDaoX = new EndoDaoX();
        LinkedHashMap<String, EndoBinder> allEndoBinders = endoDaoX.getAllEndoBinders();

        LinkedHashMap<String, EndoApostolis> endoApostoliss = endoDaoX.getLastIncomingEndoApostoliss(10);
        LinkedHashMap<String, EndoParalavis> endoParalaviss = endoDaoX.getLastEndoParalaviss(10);

        Iterator<Entry<String, EndoParalavis>> endoParalavissIterator = endoParalaviss.entrySet().iterator();

        while (endoParalavissIterator.hasNext()) {
            Entry<String, EndoParalavis> endoParalavisEntry = endoParalavissIterator.next();
            String endoParalavisId = endoParalavisEntry.getKey();
            if (endoParalavisId.equals("359761")
                    || endoParalavisId.equals("360140")
                    || endoParalavisId.equals("362926")
                    || endoParalavisId.equals("362480")
                    || endoParalavisId.equals("371898")
                    || endoParalavisId.equals("381889")
                    || endoParalavisId.equals("383428")
                    || endoParalavisId.equals("383703")) {
                endoParalavissIterator.remove();
            }

            if (allEndoBinders.containsKey(endoParalavisId)) {
                endoParalavissIterator.remove();
                EndoBinder endoBinder = allEndoBinders.get(endoParalavisId);
                LinkedHashMap<String, EndoApostolis> enAps = endoBinder.getEndoApostoliss();
                for (Map.Entry<String, EndoApostolis> enApEntry : enAps.entrySet()) {
                    if (endoApostoliss.containsKey(enApEntry.getKey())) {
                        endoApostoliss.remove(enApEntry.getKey());
                    }
                }
            }
        }

        modelMap.addAttribute("incomingEndos", endoApostoliss);
        modelMap.addAttribute("receivingEndos", endoParalaviss);

        //-----------------------------------------
        if (endoParalaviss.size() == 1) {
            this.proEndoBinder = new EndoBinder();

            Map.Entry<String, EndoParalavis> entry = endoParalaviss.entrySet().stream().findFirst().get();

            EndoParalavis endoParalavis = entry.getValue();
            this.proEndoBinder.setEndoParalavis(endoParalavis);

            for (Map.Entry<String, EndoApostolis> endoApostolissEntry : endoApostoliss.entrySet()) {
                if (endoParalavis.getThreeLastDigitsArrayList().contains(endoApostolissEntry.getValue().getShortNumber())) {

                    if (endoParalavis.getDateString().equals(endoApostolissEntry.getValue().getDateString())) {
                        this.proEndoBinder.addEndoApostolis(endoApostolissEntry.getValue().getId(), endoApostolissEntry.getValue());
                    }
                }
            }
            this.proEndoBinder = endoDaoX.fillEndoBinder(this.proEndoBinder);
            this.proEndoBinder.checkTotals();
            modelMap.addAttribute("proEndoBinder", this.proEndoBinder);
        }

        return "endo/endoParalaves";
    }

    @RequestMapping(value = "saveEndoBinder", method = RequestMethod.GET)
    public String saveEndoBinder(ModelMap modelMap) {
        EndoDaoX endoDaoX = new EndoDaoX();
        String result = endoDaoX.saveBinder(this.proEndoBinder);
        return "redirect:endoParalaves.htm";
    }

    @RequestMapping(value = "checkSuggestedBinder", method = RequestMethod.GET)
    public String checkSuggestedBinder(ModelMap modelMap) {

        Set<String> keySet = this.proEndoBinder.getEndoApostoliss().keySet();
        ArrayList<String> endoIdsArray = new ArrayList<String>(keySet);

        LinkedHashMap<String, EndoApostolis> endoApostoliss = this.proEndoBinder.getEndoApostoliss();
        ArrayList<String> receivingEndoIdsArray = new ArrayList(endoApostoliss.keySet());

        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(endoIdsArray, pet4UItemsRowByRow);
        LinkedHashMap<String, DeliveryItem> deliveredIetms = endoDao.getReceivedItems(receivingEndoIdsArray, pet4UItemsRowByRow);

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

        if (sentItems.size()
                > 0) {

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

        return "endo/endoCheckingB";
    }

    @RequestMapping(value = "showDeltiaApostolisOfItem_B", method = RequestMethod.GET)
    public String showDeltiaApostolisOfItem_B(@RequestParam(name = "itemCode") String itemCode, ModelMap modelMap) {

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = pet4uItemsDao.getPet4UItemsRowByRow();

        String sentItemDescription = pet4UItemsRowByRow.get(itemCode).getDescription();

        EndoDao endoDao = new EndoDao();
        Set<String> keySet = this.proEndoBinder.getEndoApostoliss().keySet();
        ArrayList endoIdsArray = new ArrayList(keySet);
        ArrayList<Endo> endos = endoDao.getEndosOfItem(itemCode, endoIdsArray);

        modelMap.addAttribute("itemCode", itemCode);
        modelMap.addAttribute("sentItem", sentItemDescription);
        modelMap.addAttribute("endos", endos);
        return "endo/deltiaApostolisDisplay";
    }

    @RequestMapping(value = "seeLastEndoBinders", method = RequestMethod.GET)
    public String seeLastEndoBinders(ModelMap modelMap) {
        EndoDao endoDao = new EndoDao();

        LinkedHashMap<String, Endo> incomingEndos = endoDao.getLastIncomingEndos(10);
        LinkedHashMap<String, Endo> receivingEndos = endoDao.getLastReceivingEndos(10);
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

        modelMap.addAttribute("bindedEndos", filteredBinder);
        return "endo/endoBinders";
    }

    //---------------------------------------
    @RequestMapping(value = "goForEndoOrdersUpload")
    public String goForEndoOrdersUpload(ModelMap model) {

        LocalDate date = LocalDate.now();
        model.addAttribute("date", date);
        model.addAttribute("uploadTitle", "Upload Endo Orders For Today");
        model.addAttribute("uploadTarget", "uploadEndoOrders.htm");
        return "endo/endoOrdersUpload";
    }

    @RequestMapping(value = "uploadEndoOrders", method = RequestMethod.POST)
    public String uploadEndoOrders(@RequestParam CommonsMultipartFile file, @RequestParam String date, ModelMap model) {

        //this string -date- should come from view, later
        //
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Endo Orders Upload: Starting .............. ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        String filename = "endoOrders.xlsx";
        Basement basement = new Basement();
        String filePath = basement.getBasementDirectory() + "/Pet4U_Uploads/" + filename;
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "No file has been selected");
            return "endo/endoOrdersUpload";
        }

        if (date.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "No date has been selected");
            return "endo/endoOrdersUpload";
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
            return "endo/endoOrdersUpload";
        }

        EndoOrdersFactory endoOrdersFactory = new EndoOrdersFactory();
        LinkedHashMap<String, EndoOrder> endoOrders = endoOrdersFactory.createEndoOrdersFromUploadedFile(filePath);
        EndoDaoX endoDaoX = new EndoDaoX();
        String result = endoDaoX.insertNewOrdersUpload(date, endoOrders);

        System.out.println("Endo Orders Upload DATE:" + date);
        model.addAttribute("uploadTitle", "ENDO ORDERS UPLOAD COMPELTED SUCCESSFULLY");
        model.addAttribute("uploadStatus", result);

        return "redirect:endoApostoles.htm";
    }

    @RequestMapping(value = "endoApostoles")
    public String endoApostoles(ModelMap model) {
        EndoDaoX endoDaoX = new EndoDaoX();
        String date = "2024-02-06";
        String date1 = "2024-02-07";
        LinkedHashMap<String, EndoOrder> endoOrdersTitles = endoDaoX.getEndoOrdersTitles(date);
        LinkedHashMap<String, EndoApostolis> outgoingDeltioApostolisTitles = endoDaoX.getOutgoingDeltioApostolisTitles(date1);

        LinkedHashMap<String, String> allBindedOrders = endoDaoX.getAllBindedOrdersTitles();

        for (Map.Entry<String, String> allBindedOrdersEntry : allBindedOrders.entrySet()) {

            if (endoOrdersTitles.containsKey(allBindedOrdersEntry.getKey())
                    && outgoingDeltioApostolisTitles.containsKey(allBindedOrdersEntry.getValue())) {
                endoOrdersTitles.remove(allBindedOrdersEntry.getKey());
                outgoingDeltioApostolisTitles.remove(allBindedOrdersEntry.getValue());
            } else {
                if (allBindedOrdersEntry.getKey().contains("NoOrder")) {
                    outgoingDeltioApostolisTitles.remove(allBindedOrdersEntry.getValue());

                }
            }
        }

        model.addAttribute("endoOrdersTitles", endoOrdersTitles);
        model.addAttribute("outgoingDeltioApostolisTitles", outgoingDeltioApostolisTitles);

        return "endo/endoApostoles";
    }

    @RequestMapping(value = "showEndoOrder")
    public String showEndoOrder(@RequestParam(name = "id") String id, ModelMap model) {
        EndoDaoX endoDaoX = new EndoDaoX();

        InventoryDao inventoryDao = new InventoryDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = inventoryDao.getpet4UItemsRowByRow();

        EndoOrder endoOrder = endoDaoX.getEndoOrder(id, pet4UItemsRowByRow);

        model.addAttribute("endoOrder", endoOrder);

        return "endo/endoOrderDisplay";
    }

    @RequestMapping(value = "showDeltioApostolisVaribobis", method = RequestMethod.GET)
    public String showDeltioApostolisVaribobis(@RequestParam(name = "id") String id, ModelMap modelMap) {
        System.out.println(id);

        EndoDaoX endoDaoX = new EndoDaoX();

        EndoApostolis endo = endoDaoX.getEndoApostolisVaribobis(id);

        modelMap.addAttribute("endo", endo);
        return "endo/deltioApostolisVaribobisDisplay";
    }

    @RequestMapping(value = "checkOrderWithEndo", method = RequestMethod.POST)
    public String checkOrderWithEndo(@RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "outgoingEndoId") String outgoingEndoId,
            ModelMap modelMap) {

        System.out.println("ORDER ID: " + orderId);
        System.out.println("OUTGOING ENDO ID: " + outgoingEndoId);

        InventoryDao inventoryDao = new InventoryDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = inventoryDao.getpet4UItemsRowByRow();

        EndoDaoX endoDaoX = new EndoDaoX();

        EndoOrder endoOrder = endoDaoX.getEndoOrder(orderId, pet4UItemsRowByRow);
        EndoApostolis endoApostolis = endoDaoX.getEndoApostolisVaribobis(outgoingEndoId);
        LocalDate deliveryDay = null;
        if (endoOrder.getDate().getDayOfWeek() == DayOfWeek.FRIDAY ) {
            deliveryDay = endoOrder.getDate().plusDays(3);

        } else {
            deliveryDay = endoOrder.getDate().plusDays(1);
        }
        System.out.println("Delivery Date:" + deliveryDay);
        if (deliveryDay.equals(endoApostolis.getDate())) {
            modelMap.addAttribute("dateCheckColor", "green");
        } else {
            modelMap.addAttribute("dateCheckColor", "red");
        }

        LinkedHashMap<String, EndoPackaging> allEndoPackaging = endoDaoX.getAllEndoPackaging();

        modelMap.addAttribute("endoOrder", endoOrder);
        modelMap.addAttribute("endoApostolis", endoApostolis);
        modelMap.addAttribute("allEndoPackaging", allEndoPackaging);
        return "endo/endoOrderChecking";
    }

    @RequestMapping(value = "/printLabel", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String printLabel(@RequestParam("labelsCount") String labelsCount, @RequestParam("storeName") String storeName) {
        System.out.println(labelsCount);
        System.out.println(storeName);
        int labelsCountInteger = 0;
        try {
            labelsCountInteger = Integer.parseInt(labelsCount);
        } catch (NumberFormatException e) {
            return "SOMΕTHING WRONG";
        }
        String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        EndoLablePrinter endoLablePrinter = new EndoLablePrinter();
        endoLablePrinter.setLabelsCount(labelsCountInteger);
        endoLablePrinter.setStoreName(storeName);
        endoLablePrinter.printSomething(printName);
        return "DONE";
    }
//---------------------------------

    @RequestMapping(value = "bindOrderWithEndo", method = RequestMethod.GET)
    public String bindOrderWithEndo(@RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "outgoingEndoId") String outgoingEndoId,
            ModelMap modelMap) {
        System.out.println("BINDING ENDO ORDER WITH ENDO APOSTOLIS");
        System.out.println("ORDER ID: " + orderId);
        System.out.println("OUTGOING ENDO ID: " + outgoingEndoId);

        EndoDaoX endoDaoX = new EndoDaoX();

        if (orderId.isEmpty()) {
            LocalDateTime timeNow = LocalDateTime.now();
            orderId = "NoOrder:" + timeNow.toString();
        }
        String result = endoDaoX.bindOrderWithEndo(orderId, outgoingEndoId);

        return "redirect:endoApostoles.htm";
    }

    @RequestMapping(value = "showBindedOrders", method = RequestMethod.GET)
    public String showBindedOrders(ModelMap modelMap) {

        EndoDaoX endoDaoX = new EndoDaoX();
        boolean someEndoIsChanged = false;
        LinkedHashMap<String, String> allBindedOrders = endoDaoX.getAllBindedOrdersTitles();

        LinkedHashMap<String, EndoApostolis> outgoingDeltioApostolisTitles = endoDaoX.getOutgoingDeltioApostolisTitles("2024-07-31");

        ArrayList<String> lockedOutgoingDeltiaApostolis = endoDaoX.getAllLockedOutgoingDeltiaApostolisIds();

        ArrayList<String> changedOutgoingDeltiaApostolis = endoDaoX.getAllChangedOutgoingDeltiaApostolisIds(lockedOutgoingDeltiaApostolis);

        ArrayList<EndoApostolis> bindedOutgoindDeltioApostolis = new ArrayList();
        for (Map.Entry<String, EndoApostolis> outgoingDeltioApostolisTitlesEntry : outgoingDeltioApostolisTitles.entrySet()) {
            if (lockedOutgoingDeltiaApostolis.contains(outgoingDeltioApostolisTitlesEntry.getKey())) {
                outgoingDeltioApostolisTitlesEntry.getValue().setIsLocked(true);
            }

            if (changedOutgoingDeltiaApostolis.contains(outgoingDeltioApostolisTitlesEntry.getKey())) {
                outgoingDeltioApostolisTitlesEntry.getValue().setIsChanged(true);
                someEndoIsChanged = true;
            }
            if (allBindedOrders.containsValue(outgoingDeltioApostolisTitlesEntry.getKey())) {
                bindedOutgoindDeltioApostolis.add(outgoingDeltioApostolisTitlesEntry.getValue());
            }
        }
        modelMap.addAttribute("isChanged", someEndoIsChanged);
        modelMap.addAttribute("bindedOutgoindDeltioApostolis", bindedOutgoindDeltioApostolis);
        return "endo/bindedEndoOrders";
    }

    @RequestMapping(value = "showBindedEndoOrder", method = RequestMethod.GET)
    public String showBindedEndoOrder(@RequestParam(name = "id") String outgoingEndoId,
            ModelMap modelMap) {

        EndoDaoX endoDaoX = new EndoDaoX();
        String bindedOrderId = endoDaoX.getBindedOrderIdByEndoApostolis(outgoingEndoId);

        System.out.println("ORDER ID: " + bindedOrderId);
        System.out.println("OUTGOING ENDO ID: " + outgoingEndoId);

        InventoryDao inventoryDao = new InventoryDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = inventoryDao.getpet4UItemsRowByRow();
        EndoApostolis endoApostolis = endoDaoX.getEndoApostolisVaribobis(outgoingEndoId);
        boolean locked = endoDaoX.endoApostolisIsLocked(outgoingEndoId);
        EndoOrder endoOrder;
        if (bindedOrderId.contains("NoOrder")) {
            endoOrder = new EndoOrder();
            endoOrder.setId(bindedOrderId);
        } else {
            endoOrder = endoDaoX.getEndoOrder(bindedOrderId, pet4UItemsRowByRow);
        }

        String lockerButton = "<center><a href='lockEndoApostolis.htm?outgoingEndoId=" + outgoingEndoId + "' class='btn btn-danger' style='font-size:30px'>LOCK ENDO APOSTOLIS</a></center>\n";
        if (locked) {
            lockerButton = "";
        }
        modelMap.addAttribute("lockerButton", lockerButton);
        modelMap.addAttribute("endoOrder", endoOrder);
        modelMap.addAttribute("endoApostolis", endoApostolis);

        return "endo/bindedEndoOrderDisplay";
    }

    @RequestMapping(value = "unbindOrderWithEndo", method = RequestMethod.GET)
    public String unbindOrderWithEndo(@RequestParam(name = "orderId") String orderId,
            @RequestParam(name = "outgoingEndoId") String outgoingEndoId,
            ModelMap modelMap) {
        System.out.println("UNBINDING ENDO ORDER WITH ENDO APOSTOLIS");
        System.out.println("ORDER ID: " + orderId);
        System.out.println("OUTGOING ENDO ID: " + outgoingEndoId);

        EndoDaoX endoDaoX = new EndoDaoX();

        String result = endoDaoX.unbindOrderWithEndo(orderId, outgoingEndoId);

        return "redirect:endoApostoles.htm";
    }

    @RequestMapping(value = "lockEndoApostolis", method = RequestMethod.GET)
    public String lockEndoApostolis(@RequestParam(name = "outgoingEndoId") String outgoingEndoId,
            ModelMap modelMap) {
        System.out.println("LOCKING ENDO APOSTOLIS");
        System.out.println("OUTGOING ENDO ID: " + outgoingEndoId);

        EndoDaoX endoDaoX = new EndoDaoX();
        EndoApostolis endoApostolisVaribobis = endoDaoX.getEndoApostolisVaribobis(outgoingEndoId);
        String result = endoDaoX.copyEndoApostolis(endoApostolisVaribobis);

        return "redirect:showBindedOrders.htm";
    }

    @RequestMapping(value = "deleteEndoOrder", method = RequestMethod.GET)
    public String deleteEndoOrder(@RequestParam(name = "id") String outgoingEndoId,
            ModelMap modelMap) {
        System.out.println("DELETING ENDO APOSTOLIS");
        System.out.println("OUTGOING ENDO ID: " + outgoingEndoId);

        EndoDaoX endoDaoX = new EndoDaoX();
        String result = endoDaoX.deleteEndoApostolisVaribobis(outgoingEndoId);

        System.out.println(result);
        return "redirect:endoApostoles.htm";
    }

    //-------------------------------------
    @RequestMapping(value = "endoOrdersPreliminaryCheck", method = RequestMethod.GET)
    public String endoOrdersPreliminaryCheck(ModelMap modelMap) {

        EndoDaoX endoDaoX = new EndoDaoX();
        String date = "2024-02-06";
        String date1 = "2024-02-07";
        LinkedHashMap<String, EndoOrder> endoOrdersTitles = endoDaoX.getEndoOrdersTitles(date);
        LinkedHashMap<String, EndoApostolis> outgoingDeltioApostolisTitles = endoDaoX.getOutgoingDeltioApostolisTitles(date1);

        LinkedHashMap<String, String> allBindedOrders = endoDaoX.getAllBindedOrdersTitles();

        for (Map.Entry<String, String> allBindedOrdersEntry : allBindedOrders.entrySet()) {

            if (endoOrdersTitles.containsKey(allBindedOrdersEntry.getKey())
                    && outgoingDeltioApostolisTitles.containsKey(allBindedOrdersEntry.getValue())) {
                endoOrdersTitles.remove(allBindedOrdersEntry.getKey());
                outgoingDeltioApostolisTitles.remove(allBindedOrdersEntry.getValue());
            } else {
                if (allBindedOrdersEntry.getKey().contains("NoOrder")) {
                    outgoingDeltioApostolisTitles.remove(allBindedOrdersEntry.getValue());

                }
            }
        }
        NotesDao notesDao = new NotesDao();
        ArrayList<String> allNotForEndos = notesDao.getAllNotForEndoIds();

        LinkedHashMap<String, EndoOrder> endoOrders = endoDaoX.getEndoOrders(endoOrdersTitles);

        ArrayList<String> notForEndosForTheseOrders = new ArrayList<>();
        for (Map.Entry<String, EndoOrder> endoOrdersEntrySet : endoOrders.entrySet()) {
            EndoOrder endoOrder = endoOrdersEntrySet.getValue();
            LinkedHashMap<String, EndoOrderItem> orderedItems = endoOrder.getOrderedItems();
            for (Map.Entry<String, EndoOrderItem> orderedItemsEntry : orderedItems.entrySet()) {
                EndoOrderItem orderedItem = orderedItemsEntry.getValue();
                if (allNotForEndos.contains(orderedItem.getCode())) {

                    String notForEndo = endoOrder.getDestination() + " : " + orderedItem.getCode() + " : " + orderedItem.getDescription();
                    notForEndosForTheseOrders.add(notForEndo);
                }
            }
        }
        modelMap.addAttribute("notForEndosForTheseOrders", notForEndosForTheseOrders);
        return "endo/endoOrdersPreliminaryCheck";
    }

    @RequestMapping(value = "goForEditEndoPackaging", method = RequestMethod.GET)
    public String goForEditEndoPackaging(@RequestParam(name = "code") String itemCode,
            ModelMap modelMap) {

        EndoDaoX endoDaoX = new EndoDaoX();
        EndoPackaging endoPackaging = endoDaoX.getEndoPackaging(itemCode);
        if (endoPackaging == null) {
            endoPackaging = new EndoPackaging();
            endoPackaging.setItemCode(itemCode);
            endoPackaging.setItem(1);
            endoPackaging.setLabel(1);
            modelMap.addAttribute("endoPackaging", endoPackaging);
            modelMap.addAttribute("saveType", "insertEndoPackaging.htm");
        } else {
            modelMap.addAttribute("endoPackaging", endoPackaging);
            modelMap.addAttribute("saveType", "editEndoPackaging.htm");
        }

        return "endo/editEndoPackaging";

    }

    @RequestMapping(value = "insertEndoPackaging")
    public String insertEndoPackaging(HttpSession session,
            @RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "item") String item,
            @RequestParam(name = "label") String label,
            ModelMap model) {


        /*   
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }
        if (!userName.equals("me")) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }
         */
        EndoPackaging endoPackaging = new EndoPackaging();
        endoPackaging.setItemCode(itemCode);
        endoPackaging.setItem(Integer.parseInt(item));
        endoPackaging.setLabel(Integer.parseInt(label));

        if (item.isEmpty() || label.isEmpty()) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "SOMETHING IS MISSING.");
            model.addAttribute("endoPackaging", endoPackaging);
            return "endo/editEndoPackaging";
        }
        if (Integer.parseInt(item) <= 0 || Integer.parseInt(label) <= 0) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "Bad Coefficient.");
            model.addAttribute("itemOfInterest", endoPackaging);
            return "endo/editEndoPackaging";
        }
        EndoDaoX endoDaoX = new EndoDaoX();

        String result = endoDaoX.insertEndoPackaging(endoPackaging);
        model.addAttribute("resultColor", "green");
        model.addAttribute("result", result);
        model.addAttribute("endoPackaging", endoPackaging);
        return "endo/editEndoPackaging";
    }

    @RequestMapping(value = "editEndoPackaging")
    public String editEndoPackaging(HttpSession session,
            @RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "item") String item,
            @RequestParam(name = "label") String label,
            ModelMap model) {


        /*   
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }
        if (!userName.equals("me")) {
            model.addAttribute("message", "You are not authorized for this page");
            return "errorPage";
        }
         */
        EndoPackaging endoPackaging = new EndoPackaging();
        endoPackaging.setItemCode(itemCode);
        endoPackaging.setItem(Integer.parseInt(item));
        endoPackaging.setLabel(Integer.parseInt(label));

        if (item.isEmpty() || label.isEmpty()) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "SOMETHING IS MISSING.");
            model.addAttribute("endoPackaging", endoPackaging);
            return "endo/editEndoPackaging";
        }
        if (Integer.parseInt(item) <= 0 || Integer.parseInt(label) <= 0) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "Bad Coefficient.");
            model.addAttribute("itemOfInterest", endoPackaging);
            return "endo/editEndoPackaging";
        }
        EndoDaoX endoDaoX = new EndoDaoX();

        String result = endoDaoX.editEndoPackaging(endoPackaging);
        model.addAttribute("resultColor", "green");
        model.addAttribute("result", result);
        model.addAttribute("endoPackaging", endoPackaging);
        return "endo/editEndoPackaging";
    }
}
