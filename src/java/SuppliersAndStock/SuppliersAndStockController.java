/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import BasicModel.Item;
import MonthSales.EksagogesController;
import MonthSales.ItemEksagoges;
import MonthSales.MonthSales;
import MonthSales.MonthSalesDao;
import Offer.Offer;
import Offer.OfferDao;
import Pet4uItems.Pet4uItemsDao;
import SalesX.SalesControllerX;
import SalesX.SoldItem;
import Search.SearchDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuppliersAndStockController {

    @Autowired
    private SupplierDao supplierDao;

    @RequestMapping(value = "suppliersAndStockDashboard")
    public String suppliersAndStockDashboard(ModelMap modelMap) {
        ArrayList<Supplier> suppliers = supplierDao.getAllSuppliers();
        modelMap.addAttribute("suppliers", suppliers);
        return "suppliersAndStock/suppliersAndStockDashboard";
    }

    @RequestMapping(value = "goForAddingSupplier")
    public String goForAddingSupplier() {

        return "suppliersAndStock/addSupplier";
    }

    @RequestMapping(value = "addSupplier")
    public String addSupplier(@RequestParam(name = "name") String name,
            @RequestParam(name = "afm") String afm,
            ModelMap modelMap) {

        Supplier supplier = new Supplier();
        supplier.setId(0);
        supplier.setName(name);
        supplier.setAfm(afm);
        if (name.isEmpty()) {
            modelMap.addAttribute("resultColor", "rose");
            modelMap.addAttribute("result", "SOMETHING IS MISSING.");
            modelMap.addAttribute("supplier", supplier);
            return "suppliersAndStock/addSupplier";

        }

        String result = supplierDao.addSupplier(supplier);

        modelMap.addAttribute("resultColor", "green");
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("supplier", supplier);

        return "suppliersAndStock/addSupplier";
    }

    //----------------
    @RequestMapping(value = "stockManagement")
    public String stockManagement(@RequestParam(name = "supplierId") String supplierId, ModelMap modelMap) {
        Supplier supplier = supplierDao.getSupplier(supplierId);
        LinkedHashMap<String, SuppliersItem> supplierItemsForView = new LinkedHashMap<>();
        TreeMap<String, SuppliersItem> usher = new TreeMap<>();

        LinkedHashMap<String, SuppliersItem> supplierItemsFromDatabase = supplierDao.getAllItemsOfSupplier(supplierId);

        EksagogesController eksagogesController = new EksagogesController();
        LinkedHashMap<String, ItemEksagoges> lastSixMonthsSales = eksagogesController.getLastSixMonthsSales();

        for (Map.Entry<String, SuppliersItem> supplierItemsFromDatabaseEntrySet : supplierItemsFromDatabase.entrySet()) {
            String key = supplierItemsFromDatabaseEntrySet.getKey();
            SuppliersItem suppliersItem = supplierItemsFromDatabaseEntrySet.getValue();

            ItemEksagoges itemEksagoges = lastSixMonthsSales.get(key);

            suppliersItem.setDescription(itemEksagoges.getDescription());
            suppliersItem.setPosition(itemEksagoges.getPosition());
            suppliersItem.setQuantity(itemEksagoges.getQuantity());

            suppliersItem.setEksagoges(itemEksagoges.getEksagoges());

            suppliersItem.setSupplierId(Integer.parseInt(supplierId));

            usher.put(suppliersItem.getPosition(), suppliersItem);
        }

        for (Map.Entry<String, SuppliersItem> usherEntry : usher.entrySet()) {

            supplierItemsForView.put(usherEntry.getValue().getCode(), usherEntry.getValue());
        }

        modelMap.addAttribute("supplierItems", supplierItemsForView);
        modelMap.addAttribute("supplier", supplier);

        return "suppliersAndStock/stockManagement";
    }

    @RequestMapping(value = "goForAddingItemToSupplier", method = RequestMethod.POST)
    public String goForAddingItemToSupplier(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "altercode") String altercode,
            ModelMap modelMap) {

        Supplier supplier = supplierDao.getSupplier(supplierId);

        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(altercode);
        SuppliersItem item = new SuppliersItem();
        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());

        item.setOrderUnit("item");
        item.setOrderUnitCapacity(1);

        modelMap.addAttribute("supplier", supplier);
        modelMap.addAttribute("item", item);
        return "suppliersAndStock/addItemToSupplier";
    }

    @RequestMapping(value = "addItemToSupplier", method = RequestMethod.POST)
    public String addItemToSupplier(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "orderUnit") String orderUnit,
            @RequestParam(name = "orderUnitCapacity") String orderUnitCapacity,
            @RequestParam(name = "note") String note,
            ModelMap modelMap) {

        Supplier supplier = supplierDao.getSupplier(supplierId);

        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(code);
        SuppliersItem item = new SuppliersItem();
        item.setSupplierId(Integer.parseInt(supplierId));
        item.setMinimalStockHorizon(0);
        item.setOrderUnit(orderUnit);
        item.setOrderUnitCapacity(Integer.parseInt(orderUnitCapacity));

        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());

        item.setNote(note);
        if (orderUnit.isEmpty() || orderUnitCapacity.isEmpty()) {
            modelMap.addAttribute("resultColor", "rose");
            modelMap.addAttribute("result", "SOMETHING IS MISSING.");
            modelMap.addAttribute("supplier", supplier);
            modelMap.addAttribute("item", item);
            return "suppliersAndStock/addItemToSupplier";
        }

        String result = supplierDao.addItemToSupplier(item);
        modelMap.addAttribute("resultColor", "green");
        modelMap.addAttribute("result", result);

        modelMap.addAttribute("supplier", supplier);
        modelMap.addAttribute("item", item);
        return "suppliersAndStock/addItemToSupplier";
    }

    @RequestMapping(value = "goForEditingSuppliersItem")
    public String goForEditingSuppliersItem(
            @RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "code") String code,
            ModelMap model) {
        SuppliersItem item = supplierDao.getSuppliersItem(supplierId, code);
        Supplier supplier = supplierDao.getSupplier(supplierId);
        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(code);

        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());

        model.addAttribute("supplier", supplier);
        model.addAttribute("item", item);
        return "/suppliersAndStock/editSuppliersItem";
    }

    @RequestMapping(value = "editItemOfSupplier", method = RequestMethod.POST)
    public String editItemOfSupplier(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "orderUnit") String orderUnit,
            @RequestParam(name = "orderUnitCapacity") String orderUnitCapacity,
            ModelMap modelMap) {

        Supplier supplier = supplierDao.getSupplier(supplierId);

        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(code);
        SuppliersItem item = new SuppliersItem();
        item.setSupplierId(Integer.parseInt(supplierId));

        item.setOrderUnit(orderUnit);
        item.setOrderUnitCapacity(Integer.parseInt(orderUnitCapacity));

        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());

        if (orderUnit.isEmpty() || orderUnitCapacity.isEmpty()) {
            modelMap.addAttribute("resultColor", "rose");
            modelMap.addAttribute("result", "SOMETHING IS MISSING.");
            modelMap.addAttribute("supplier", supplier);
            modelMap.addAttribute("item", item);
            return "suppliersAndStock/addItemToSupplier";
        }

        String result = supplierDao.editItemOfSupplier(item);
        modelMap.addAttribute("resultColor", "green");
        modelMap.addAttribute("result", result);

        modelMap.addAttribute("supplier", supplier);
        modelMap.addAttribute("item", item);
        return "suppliersAndStock/editSuppliersItem";
    }

    //--------------------------------
    @RequestMapping(value = "orderMode")
    public String printMode(@RequestParam("supplierId") String supplierId, @RequestParam("itemsIds") String itemsIds, ModelMap model) {
        Supplier supplier = this.supplierDao.getSupplier(supplierId);
        LinkedHashMap<String, SuppliersItem> supplierItemsForView = new LinkedHashMap<>();
        TreeMap<String, SuppliersItem> usher = new TreeMap<>();
        ArrayList<String> temsIdsArray = createItemsIdsArray(itemsIds);

        LinkedHashMap<String, SuppliersItem> supplierItemsFromDatabase = this.supplierDao.getItems(temsIdsArray);
        EksagogesController eksagogesController = new EksagogesController();
        LinkedHashMap<String, ItemEksagoges> lastSixMonthsSales = eksagogesController.getLastSixMonthsSales();

        for (Map.Entry<String, SuppliersItem> supplierItemsFromDatabaseEntrySet : supplierItemsFromDatabase.entrySet()) {
            String key = supplierItemsFromDatabaseEntrySet.getKey();
            SuppliersItem suppliersItem = supplierItemsFromDatabaseEntrySet.getValue();

            ItemEksagoges itemEksagoges = lastSixMonthsSales.get(key);

            suppliersItem.setDescription(itemEksagoges.getDescription());
            suppliersItem.setPosition(itemEksagoges.getPosition());
            suppliersItem.setQuantity(itemEksagoges.getQuantity());

            suppliersItem.setEksagoges(itemEksagoges.getEksagoges());

            suppliersItem.setSupplierId(Integer.parseInt(supplierId));

            usher.put(suppliersItem.getPosition(), suppliersItem);
        }

        for (Map.Entry<String, SuppliersItem> usherEntry : usher.entrySet()) {

            supplierItemsForView.put(usherEntry.getValue().getCode(), usherEntry.getValue());
        }
        model.addAttribute("supplier", supplier);
        model.addAttribute("supplierItems", supplierItemsForView);
        return "suppliersAndStock/orderMode";
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

    @RequestMapping(value = "deleteSupplierItem", method = RequestMethod.GET)
    public String deleteSupplierItem(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "itemCode") String code,
            ModelMap modelMap) {

        String result = supplierDao.deleteItemOfSupplier(supplierId, code);

        return "redirect:stockManagement.htm?supplierId=" + supplierId + "";
    }

    @RequestMapping(value = "objectiveSalesDashboard", method = RequestMethod.GET)
    public String objectiveSalesDashboard(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "itemCode") String code,
            ModelMap model) {
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(code);
        model.addAttribute("item", item);

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemSnapshots = pet4uItemsDao.getItemSnapshots(code);
        model.addAttribute("itemSnapshots", itemSnapshots);

        MonthSalesDao monthSalesDao = new MonthSalesDao();
        MonthSales itemSales = monthSalesDao.getItemSales(code);
        model.addAttribute("itemSales", itemSales);

        OfferDao offerDao = new OfferDao();
        ArrayList<Offer> offers = offerDao.getOffers(code);
        model.addAttribute("offers", offers);

        SuppliersItem supplierItem = supplierDao.getSuppliersItem(supplierId, code);
        model.addAttribute("supplierItem", supplierItem);

        return "suppliersAndStock/objectiveSalesDashboard";
        // return "redirect:stockManagement.htm?supplierId=" + supplierId + "";
    }

    @RequestMapping(value = "updateObjectiveSales", method = RequestMethod.POST)
    public String updateObjectiveSales(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "objectiveSales") String objectiveSales,
            @RequestParam(name = "expirationDate") String expirationDate,
            ModelMap modelMap) {

        if (objectiveSales.isEmpty() || expirationDate.isEmpty()) {
            modelMap.addAttribute("resultColor", "rose");
            modelMap.addAttribute("result", "SOMETHING IS MISSING.");

            return "redirect:objectiveSalesDashboard.htm?supplierId=" + supplierId + "&itemCode=" + itemCode;
        }

        String result = supplierDao.updateObjectiveSales(supplierId, itemCode, objectiveSales, expirationDate);

        return "redirect:stockManagement.htm?supplierId=" + supplierId + "";
    }

    @RequestMapping(value = "updateHorizons", method = RequestMethod.POST)
    public String updateOrderHorizon(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "orderHorizon") String orderHorizon,
            @RequestParam(name = "minimalStockHorizon") String minimalStockHorizon,
            ModelMap modelMap) {

        if (orderHorizon.isEmpty()||minimalStockHorizon.isEmpty()) {
            modelMap.addAttribute("resultColor", "rose");
            modelMap.addAttribute("result", "SOMETHING IS MISSING.");

            return "redirect:objectiveSalesDashboard.htm?supplierId=" + supplierId + "&itemCode=" + itemCode;
        }

        String result = supplierDao.updateHorizons(supplierId, itemCode, orderHorizon, minimalStockHorizon);

        return "redirect:stockManagement.htm?supplierId=" + supplierId + "";
    }

}
