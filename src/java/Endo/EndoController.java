package Endo;

import BasicModel.Item;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import Pet4uItems.Pet4uItemsDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
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
    public String compareEndo(@RequestParam(name = "endoIds") String endoIds,
            @RequestParam(name = "receivingEndoIds") String receivingEndoIds,
            ModelMap modelMap) {

        this.endoIdsArray = createItemsIdsArray(endoIds);
        this.receivingEndoIdsArray = createItemsIdsArray(receivingEndoIds);

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

    private ArrayList<String> createItemsIdsArray(String inventoryItemsIds) {
        ArrayList idsArray = new ArrayList();
        //trimming and cleaning input
        inventoryItemsIds = inventoryItemsIds.trim();
        if (inventoryItemsIds.length() == 0) {
            return new ArrayList<String>();
        }
        if (inventoryItemsIds.substring(inventoryItemsIds.length() - 1, inventoryItemsIds.length()).equals(",")) {
            inventoryItemsIds = inventoryItemsIds.substring(0, inventoryItemsIds.length() - 1).trim();
        }
        String[] ids = inventoryItemsIds.split(",");
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

        modelMap.addAttribute("itemCode", itemCode);
        modelMap.addAttribute("sentItem", itemCode + " : " + sentItemDescription);
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

        modelMap.addAttribute("incomingEndos", incomingEndos);
        if (receivingEndos.containsKey("359761")) {
            receivingEndos.remove("359761");
        }
        if (receivingEndos.containsKey("360140")) {
            receivingEndos.remove("360140");
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

}
