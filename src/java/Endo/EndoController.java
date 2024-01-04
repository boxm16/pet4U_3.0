package Endo;

import BasicModel.Item;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import Pet4uItems.Pet4uItemsDao;
import TESTosteron.TESTosteronDao;
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

    @RequestMapping(value = "endoDashboard", method = RequestMethod.GET)
    public String endoDashboard(ModelMap modelMap) {

        EndoDao endoDao = new EndoDao();

        LinkedHashMap<String, Endo> incomingEndos = endoDao.getLastIncomingEndos(7);
        LinkedHashMap<String, Endo> receivingEndos = endoDao.getLastReceivingEndos(7);

        modelMap.addAttribute("incomingEndos", incomingEndos);
        modelMap.addAttribute("receivingEndos", receivingEndos);

        return "endo/endoDashboard";

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

    @RequestMapping(value = "showDeltioApostolis", method = RequestMethod.GET)
    public String showDeltioApostolis(@RequestParam(name = "id") String id, ModelMap modelMap) {
        System.out.println(id);

        TESTosteronDao testosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = testosteronDao.getAllPet4UItemsWithDeepSearch();

        EndoDao endoDao = new EndoDao();

        Endo endo = endoDao.getEndo(id, allPet4UItemsWithDeepSearch);

        modelMap.addAttribute("endo", endo);
        return "endo/deltioApostolisDisplay";
    }

    @RequestMapping(value = "compareEndo", method = RequestMethod.POST)
    public String compareEndo(@RequestParam(name = "endoIds") String endoIds, ModelMap modelMap) {
        System.out.println(endoIds);

        this.endoIdsArray = createItemsIdsArray(endoIds);

        EndoDao endoDao = new EndoDao();
        LinkedHashMap<String, DeliveryItem> sentItems = endoDao.getSentItems(endoIdsArray);
        LinkedHashMap<String, DeliveryItem> deliveredIetms = endoDao.getDeliveredItems();

        Pet4uItemsDao inventoryDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = inventoryDao.getPet4UItemsRowByRow();

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
                DeliveryItem di = sentItems.remove(key);
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

        TESTosteronDao testosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = testosteronDao.getAllPet4UItemsWithDeepSearch();
        String sentItemDescription = allPet4UItemsWithDeepSearch.get(itemCode).getDescription();

        EndoDao endoDao = new EndoDao();
        ArrayList<Endo> endos = endoDao.getDeltiaApostolisOfItem(itemCode, this.endoIdsArray);
        modelMap.addAttribute("itemCode", itemCode);
        modelMap.addAttribute("sentItem", itemCode + ":" + sentItemDescription);
        modelMap.addAttribute("endos", endos);
        return "endo/deltiaApostolisDisplay";
    }

}
