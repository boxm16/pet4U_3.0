package Endo;

import BasicModel.Item;
import Delivery.DeliveryInvoice;
import Delivery.DeliveryItem;
import Service.Basement;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addAttribute("uploadTitle", "Endo Orders Upload");
        model.addAttribute("uploadStatus", result);

        return "endo/endoOrdersUpload";
    }

    @RequestMapping(value = "endoApostoles")
    public String endoApostoles(ModelMap model) {
        EndoDaoX endoDaoX = new EndoDaoX();
        String date = "2024-02-02";
        LinkedHashMap<String, EndoOrder> endoOrdersTitles = endoDaoX.getEndoOrdersTitles(date);
        LinkedHashMap<String, EndoApostolis> outgoingDeltioApostolisTitles = endoDaoX.getOutgoingDeltioApostolisTitles(date);

        model.addAttribute("endoOrdersTitles", endoOrdersTitles);
        model.addAttribute("outgoingDeltioApostolisTitles", outgoingDeltioApostolisTitles);

        return "endo/endoApostoles";
    }

    @RequestMapping(value = "showEndoOrder")
    public String showEndoOrder(@RequestParam(name = "id") String id, ModelMap model) {
        EndoDaoX endoDaoX = new EndoDaoX();

        EndoOrder endoOrder = endoDaoX.getEndoOrder(id);

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

}
