/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuppliersAndStock;

import SalesX.SalesControllerX;
import SalesX.SoldItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
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

        LinkedHashMap<String, SuppliersItem> supplierItems = supplierDao.getAllItemsOfSupplier(supplierId);

        SalesControllerX salesControllerX = new SalesControllerX();
        LinkedHashMap<String, SoldItem> sixMonthesSales = salesControllerX.getSixMonthesSales();
        for (Map.Entry<String, SuppliersItem> supplierItemsEntrySet : supplierItems.entrySet()) {
            String key = supplierItemsEntrySet.getKey();

            SoldItem soldItem = sixMonthesSales.get(key);
            supplierItemsEntrySet.getValue().setDescription(soldItem.getDescription());
            supplierItemsEntrySet.getValue().setEshopSales(soldItem.getEshopSales());
            supplierItemsEntrySet.getValue().setShopsSupply(soldItem.getShopsSupply());
            supplierItemsEntrySet.getValue().setQuantity(soldItem.getQuantity());
        }

        modelMap.addAttribute("supplierItems", supplierItems);

        modelMap.addAttribute("supplier", supplier);
        return "suppliersAndStock/stockManagement";
    }

    @RequestMapping(value = "goForAddingItemToSupplier", method = RequestMethod.POST)
    public String goForAddingItemToSupplier(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "altercode") String altercode,
            ModelMap modelMap) {

        Supplier supplier = supplierDao.getSupplier(supplierId);
        System.out.println("SUP:" + supplier.getId() + supplier.getName());
        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(altercode);
        SuppliersItem item = new SuppliersItem();
        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());
        item.setEshopSales(soldItem.getEshopSales());
        item.setShopsSupply(soldItem.getShopsSupply());
        item.setOrderUnit("item");
        item.setOrderUnitCapacity(1);
        modelMap.addAttribute("sup", "ssssss");
        modelMap.addAttribute("supplier", supplier);
        modelMap.addAttribute("item", item);
        return "suppliersAndStock/addItemToSupplier";
    }

    @RequestMapping(value = "addItemToSupplier", method = RequestMethod.POST)
    public String addItemToSupplier(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "minimalStock") String minimalStock,
            @RequestParam(name = "orderUnit") String orderUnit,
            @RequestParam(name = "orderUnitCapacity") String orderUnitCapacity,
            @RequestParam(name = "note") String note,
            ModelMap modelMap) {
        System.out.println("SUPPLIER ID" + supplierId);

        Supplier supplier = supplierDao.getSupplier(supplierId);

        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(code);
        SuppliersItem item = new SuppliersItem();
        item.setSupplierId(Integer.parseInt(supplierId));
        item.setMinimalStock(Integer.parseInt(minimalStock));
        item.setOrderUnit(orderUnit);
        item.setOrderUnitCapacity(Integer.parseInt(orderUnitCapacity));

        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());

        item.setEshopSales(soldItem.getEshopSales());
        item.setShopsSupply(soldItem.getShopsSupply());
        item.setNote(note);
        if (minimalStock.isEmpty() || orderUnit.isEmpty() || orderUnitCapacity.isEmpty() || minimalStock.isEmpty()) {
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

        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(code);

        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());
        item.setEshopSales(soldItem.getEshopSales());
        item.setShopsSupply(soldItem.getShopsSupply());

        model.addAttribute("item", item);
        return "/suppliersAndStock/editSuppliersItem";
    }

    @RequestMapping(value = "editItemOfSupplier", method = RequestMethod.POST)
    public String editItemOfSupplier(@RequestParam(name = "supplierId") String supplierId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "minimalStock") String minimalStock,
            @RequestParam(name = "orderUnit") String orderUnit,
            @RequestParam(name = "orderUnitCapacity") String orderUnitCapacity,
            ModelMap modelMap) {

        Supplier supplier = supplierDao.getSupplier(supplierId);

        SalesControllerX salesControllerX = new SalesControllerX();
        SoldItem soldItem = salesControllerX.getItemSales(code);
        SuppliersItem item = new SuppliersItem();
        item.setSupplierId(Integer.parseInt(supplierId));
        item.setMinimalStock(Integer.parseInt(minimalStock));
        item.setOrderUnit(orderUnit);
        item.setOrderUnitCapacity(Integer.parseInt(orderUnitCapacity));

        item.setCode(soldItem.getCode());
        item.setDescription(soldItem.getDescription());
        item.setEshopSales(soldItem.getEshopSales());
        item.setShopsSupply(soldItem.getShopsSupply());

        if (minimalStock.isEmpty() || orderUnit.isEmpty() || orderUnitCapacity.isEmpty() || minimalStock.isEmpty()) {
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

        ArrayList<String> temsIdsArray = createItemsIdsArray(itemsIds);

        LinkedHashMap<String, SuppliersItem> supplierItems = this.supplierDao.getItems(temsIdsArray);
        SalesControllerX salesControllerX = new SalesControllerX();
        LinkedHashMap<String, SoldItem> sixMonthesSales = salesControllerX.getSixMonthesSales();
        for (Map.Entry<String, SuppliersItem> supplierItemsEntrySet : supplierItems.entrySet()) {
            String key = supplierItemsEntrySet.getKey();

            SoldItem soldItem = sixMonthesSales.get(key);
            supplierItemsEntrySet.getValue().setDescription(soldItem.getDescription());
            supplierItemsEntrySet.getValue().setEshopSales(soldItem.getEshopSales());
            supplierItemsEntrySet.getValue().setShopsSupply(soldItem.getShopsSupply());
            supplierItemsEntrySet.getValue().setQuantity(soldItem.getQuantity());
        }
        model.addAttribute("supplier", supplier);
        model.addAttribute("supplierItems", supplierItems);
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

        return "redirect:stockManagment.htm?supplierId=" + supplierId + "";
    }
}
