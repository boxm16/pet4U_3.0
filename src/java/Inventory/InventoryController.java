package Inventory;

import BasicModel.Item;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InventoryController {

    @Autowired
    private InventoryDao inventoryDao;

    @RequestMapping(value = "inventoryDashboard")
    public String inventoryDashboard() {

        return "inventory/inventoryDashboard";
    }

    @RequestMapping(value = "getItemForInventory", method = RequestMethod.GET)
    public String getItemForNote(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        System.out.println(altercode);
        Item item = inventoryDao.getItemForInventory(altercode);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("altercode", altercode);

        return "inventory/inventoryServant";
    }

    @RequestMapping(value = "saveItemInventory", method = RequestMethod.POST)
    public String saveItemInventory(@RequestParam(name = "altercode") String altercode,
            @RequestParam(name = "systemStock") String systemStock,
            @RequestParam(name = "realStock") String realStock,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String currentDate = dateFormat.format(date);
        String currentTime = timeFormat.format(date);

        String result = inventoryDao.saveItemInventory(altercode, currentDate, currentTime,
                systemStock, realStock, note);
        model.addAttribute("result", result);
        return "inventory/inventoryDashboard";
    }

    @RequestMapping(value = "showInventories")
    public String showInventories(ModelMap model) {

        ArrayList<InventoryItem> inventories = this.inventoryDao.getAllActiveInventories();

        System.out.println("invenotry size" + inventories.size());
        LinkedHashMap<String, Item> pet4UItems = this.inventoryDao.getpet4UItemsRowByRow();
        System.out.println("row ba row  size" + pet4UItems.size());
        for (InventoryItem inventoryItem : inventories) {
            System.out.println("ITETM:"+inventoryItem.getDescription());
            String altercode = inventoryItem.getCode();

            Item pet4uItem = pet4UItems.get(altercode);

            if (pet4uItem == null) {
                System.out.println("Pet4uItem  not present in the lists from microsoft db");
            }
            inventoryItem.setCode(pet4uItem.getCode());
            inventoryItem.setDescription(pet4uItem.getDescription());
            inventoryItem.setPosition(pet4uItem.getPosition());
             inventoryItem.setState(pet4uItem.getState());
            model.addAttribute("inventories", inventories);
        }
        return "inventory/inventoriesDisplay";
    }

    @RequestMapping(value = "deleteInventoryItem", method = RequestMethod.GET)
    public String deleteInventory(@RequestParam(name = "id") String id) {
        InventoryDao inventoryDao = new InventoryDao();
        inventoryDao.deleteInventoryItem(id);
        return "redirect:showInventories.htm";
    }

    @RequestMapping(value = "archivizeInventoryItem", method = RequestMethod.GET)
    public String archivizeInventoryItem(@RequestParam(name = "id") String id) {
        InventoryDao inventoryDao = new InventoryDao();
        inventoryDao.archivizeInventoryItem(id);
        return "redirect:showInventories.htm";
    }

    @RequestMapping(value = "printMode")
    public String printMode(@RequestParam("itemsIds") String inventoryItemsIds, ModelMap model) {
        ArrayList<String> inventoryItemsIdsArray = createItemsIdsArray(inventoryItemsIds);

        ArrayList<InventoryItem> inventories = this.inventoryDao.getInventories(inventoryItemsIdsArray);

        LinkedHashMap<String, Item> pet4UItems = this.inventoryDao.getpet4UItemsRowByRow();

        for (InventoryItem inventoryItem : inventories) {
            String altercode = inventoryItem.getCode();

            Item pet4uItem = pet4UItems.get(altercode);

            if (pet4uItem == null) {
                System.out.println("Pet4uItem  not present in the lists from microsoft db");
            }
            inventoryItem.setCode(pet4uItem.getCode());
            inventoryItem.setDescription(pet4uItem.getDescription());
            model.addAttribute("inventories", inventories);
        }

        return "inventory/printMode";
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

    @RequestMapping(value = "archivizeItems")
    public String archivizeItems(@RequestParam("itemsIds") String itemsIds, ModelMap model) {
        ArrayList<String> itemsIdsArray = createItemsIdsArray(itemsIds);
        String result = this.inventoryDao.archivizeItems(itemsIdsArray);
        return "redirect:showInventories.htm";
    }
}
