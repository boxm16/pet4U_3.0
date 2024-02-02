package Endo;

import BasicModel.Item;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
            if (endoParalavisId.equals("359761") || endoParalavisId.equals("360140")) {
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
                    this.proEndoBinder.addEndoApostolis(endoApostolissEntry.getValue().getId(), endoApostolissEntry.getValue());
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
        ArrayList<String> receivingEndoIdsArray = new ArrayList();
        receivingEndoIdsArray.add(this.proEndoBinder.getEndoParalavis().getId());

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

        return "endo/endoChecking";
    }

}
