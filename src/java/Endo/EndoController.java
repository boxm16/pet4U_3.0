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

    public EndoController() {
        endoIdsArray = new ArrayList<>();

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
    public String compareEndo(@RequestParam(name = "endoIds") String endoIds, ModelMap modelMap) {
        System.out.println(endoIds);

        this.endoIdsArray = createItemsIdsArray(endoIds);

        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> pet4UItemsRowByRow = endoDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(endoIdsArray, pet4UItemsRowByRow);
        LinkedHashMap<String, DeliveryItem> deliveredIetms = endoDao.getDeliveredItems();

        System.out.println("SENT ITEMS SIZE: " + sentItems.size());
        System.out.println("DELIVERED ITEMS SIZE: " + deliveredIetms.size());
        DeliveryInvoice deliveryInvoice = new DeliveryInvoice();
        for (Map.Entry<String, DeliveryItem> deliveredIetmsEntry : deliveredIetms.entrySet()) {
            DeliveryItem deliveredItem = deliveredIetmsEntry.getValue();

            String altercode = deliveredIetmsEntry.getKey();

            DeliveryItem sentItem = sentItems.remove(altercode);
            Item itemWithDescription = pet4UItemsRowByRow.get(altercode);

            if (itemWithDescription == null) {

                System.out.println("Pet4uItem  not present in the lists from microsoft db: altercode" + altercode);

            } else {
                if (sentItem == null) {
                    System.out.println("SENT ITEM IS NULL :" + altercode);
                    deliveredItem.setDescription(itemWithDescription.getDescription());
                    deliveredItem.setSentQuantity("0");
                    deliveredIetms.put(altercode, deliveredItem);
                } else {

                    deliveredItem.setDescription(itemWithDescription.getDescription());
                    deliveredItem.setSentQuantity(sentItem.getSentQuantity());
                    deliveredIetms.put(altercode, deliveredItem);
                }
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
        System.out.println("delivered Items countL " + deliveredIetms.size());
        modelMap.addAttribute("deliveryInvoice", deliveryInvoice);
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
        System.out.println("ENDOS SIZE: " + endos.size());
        modelMap.addAttribute("itemCode", itemCode);
        modelMap.addAttribute("sentItem", itemCode + ":" + sentItemDescription);
        modelMap.addAttribute("endos", endos);
        return "endo/deltiaApostolisDisplay";
    }

    @RequestMapping(value = "saveDeltiaApostolisKaiParalavis", method = RequestMethod.GET)
    public String saveDeltiaApostolisKaiParalavis() {

        return "redirect:endoDashboard.htm";
    }

    //------------------------------------------------------------------------------------
    @RequestMapping(value = "endoDashboard", method = RequestMethod.GET)
    public String endoDashboard(ModelMap modelMap) {

        EndoDao endoDao = new EndoDao();

        LinkedHashMap<String, Endo> incomingEndos = endoDao.getLastIncomingEndos(7);
        LinkedHashMap<String, Endo> receivingEndos = endoDao.getLastReceivingEndos(7);
        LinkedHashMap<String, String> bindedEndos = endoDao.getAllBindedEndos();

        LinkedHashMap<String, BindedEndos> bindedEndosFiltered = new LinkedHashMap();

        for (Map.Entry<String, String> bindedEndosEntry : bindedEndos.entrySet()) {
            String bindedEndoId = bindedEndosEntry.getKey();
            String bindingEndoId = bindedEndosEntry.getValue();

            System.out.println("BINDED ENDO ID:" + bindedEndoId);
            //---------
            Endo n = new Endo();
            n.setDateString(incomingEndos.get(bindedEndoId).getDateString());
            receivingEndos.put(bindingEndoId, n);
            //----------
            if (receivingEndos.containsKey(bindingEndoId)) {
                Endo bindedEndo = incomingEndos.remove(bindedEndoId);
                if (bindedEndosFiltered.containsKey(bindingEndoId)) {
                    bindedEndosFiltered.get(bindingEndoId).addBindedSendingEndo(bindedEndo);
                } else {
                    BindedEndos bindedEndos1 = new BindedEndos();

                    bindedEndos1.setBindingReceivingEndoId(bindingEndoId);
                    bindedEndos1.setBindingReceivingEndo(receivingEndos.remove(bindingEndoId));
                    bindedEndos1.addBindedSendingEndo(bindedEndo);
                    bindedEndosFiltered.put(bindingEndoId, bindedEndos1);

                }

            }

        }

        modelMap.addAttribute("incomingEndos", incomingEndos);
        modelMap.addAttribute("receivingEndos", receivingEndos);
        modelMap.addAttribute("bindedEndos", bindedEndosFiltered);

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
}
