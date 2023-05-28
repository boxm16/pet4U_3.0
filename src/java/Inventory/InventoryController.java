package Inventory;

import BasicModel.Item;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
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
    public String addItemOfInterest(@RequestParam(name = "altercode") String altercode,
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

        LinkedHashMap<String, InventoryItem> inventories = this.inventoryDao.getAllInventories();

        LinkedHashMap<String, Item> pet4UItems = this.inventoryDao.getpet4UItemsRowByRow();

        for (Map.Entry<String, InventoryItem> entrySet : inventories.entrySet()) {
            String altercode = entrySet.getKey();
            InventoryItem inventoryItem = entrySet.getValue();
            Item pet4uItem = pet4UItems.get(altercode);

            if (pet4uItem == null) {
                System.out.println("Pet4uItem  not present in the lists from microsoft db");
            }
            inventoryItem.setCode(pet4uItem.getCode());
            inventoryItem.setDescription(pet4uItem.getDescription());
            model.addAttribute("inventories", inventories);
        }
        return "inventory/inventoriesDisplay";
    }
}
