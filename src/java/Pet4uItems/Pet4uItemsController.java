package Pet4uItems;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemOfInterest;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import CamelotItemsOfInterest.ItemSnapshot;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Pet4uItemsController {

    @Autowired
    private Pet4uItemsDao pet4uItemsDao;

    @Autowired
    private CamelotItemsOfInterestDao camelotItemsOfInterestDao;

    @RequestMapping(value = "pet4uNegativeStock")
    public String pet4uNegativeStock(ModelMap modelMap) {
        LinkedHashMap<String, Item> pet4uItems = pet4uItemsDao.getAllItems();
        modelMap.addAttribute("pet4uItems", pet4uItems);
        //----------------------------
        LinkedHashMap<String, CamelotItemOfInterest> pet4uNegativeItems = pet4uItemsDao.getNegativeItems();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItemsRowByRow = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (Map.Entry<String, CamelotItemOfInterest> pet4uNegatvieItemsEntry : pet4uNegativeItems.entrySet()) {
            String pet4uItemsKey = pet4uNegatvieItemsEntry.getKey();
            System.out.println("pet4uNegativeItems Key :" + pet4uItemsKey);

            Item camelotItem = camelotItemsRowByRow.get(pet4uItemsKey);
            if (camelotItem == null) {
                pet4uNegatvieItemsEntry.getValue().setSupplier("NOT CAMELOT");
                System.out.println("NO SUCH ITEM FROM CAMELOT :" + pet4uItemsKey);
            } else {
                String camelotItemQuantity = camelotItem.getQuantity();
                System.out.println("CAMELOT ITEM QUANTITY :" + camelotItemQuantity);
                Double doubleQuantity;
                try {
                    doubleQuantity = Double.parseDouble(camelotItemQuantity);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid string in argumment while trying to convert string to double");
                    doubleQuantity = null;
                }
                System.out.println("CAMELOT ITEM QUANTITY IN DOUBLE: " + doubleQuantity);
                pet4uNegatvieItemsEntry.getValue().setCamelotStock(doubleQuantity);
                pet4uNegatvieItemsEntry.getValue().setSupplier("CAMELOT");
            }
        }
        modelMap.addAttribute("pet4uNegativeItems", pet4uNegativeItems);

        return "/pet4uItems/pet4uNegativeStock";
    }

    @RequestMapping(value = "itemsFromCamelot")
    public String itemsFromCamelot(ModelMap modelMap) {
        HashSet<String> filterHashSet = new HashSet();
        LinkedHashMap<String, Item> itemsFromCamelot = new LinkedHashMap<>();
        LinkedHashMap<String, Item> pet4uItems = pet4uItemsDao.getAllItems();
        ArrayList<String> camelotAltercodes = camelotItemsOfInterestDao.getCamelotAltercodesFromMicrosoftDB();

        for (Map.Entry<String, Item> pet4uItemsEntry : pet4uItems.entrySet()) {
            ArrayList<AltercodeContainer> altercodes = pet4uItemsEntry.getValue().getAltercodes();
            for (AltercodeContainer altercode : altercodes) {
                if (camelotAltercodes.contains(altercode.getAltercode())) {
                    if (filterHashSet.contains(pet4uItemsEntry.getValue().getDescription())) {
                    } else {
                        itemsFromCamelot.put(altercode.getAltercode(), pet4uItemsEntry.getValue());
                        filterHashSet.add(pet4uItemsEntry.getValue().getDescription());
                    }
                }
            }

        }
        modelMap.addAttribute("itemsFromCamelot", itemsFromCamelot);
        return "/pet4uItems/itemsFromCamelot";
    }

    @RequestMapping(value = "camelotItemsWithPossitionDifference")
    public String camelotItemsWithPossitionDifference(ModelMap modelMap) {
        ArrayList<ArrayList<String>> differences = new ArrayList();

        LinkedHashMap<String, Item> pet4uItems = pet4uItemsDao.getAllItems();
        LinkedHashMap<String, Item> camelotItemsRowByRow = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (Map.Entry<String, Item> pet4uItemsEntry : pet4uItems.entrySet()) {
            ArrayList<AltercodeContainer> altercodes = pet4uItemsEntry.getValue().getAltercodes();
            for (AltercodeContainer altercode : altercodes) {
                String camelotVersionAltercode = altercode.getAltercode();
                if (altercode.getAltercode().contains("-WE")) {
                    camelotVersionAltercode = altercode.getAltercode().replace("-WE", "");
                }
                if (camelotItemsRowByRow.containsKey(camelotVersionAltercode)) {
                    Item camelotItem = camelotItemsRowByRow.get(camelotVersionAltercode);
                    Item pet4uItem = pet4uItemsEntry.getValue();
                    String c_position = "C-" + camelotItem.getPosition();
                    if (!camelotItem.getPosition().isEmpty()) {
                        if (pet4uItem.getPosition().contains("C-") || pet4uItem.getPosition().isEmpty()) {
                            Double q = Double.parseDouble(camelotItem.getQuantity());
                            if (q > 0) {
                                if (!pet4uItem.getPosition().equals(c_position)) {

                                    ArrayList<String> diff = new ArrayList<>();
                                    diff.add(pet4uItem.getCode());
                                    diff.add(pet4uItem.getDescription());
                                    diff.add(pet4uItem.getPosition());
                                    diff.add(c_position);
                                    diff.add(camelotItem.getQuantity());
                                    differences.add(diff);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        modelMap.addAttribute("differences", differences);
        return "/pet4uItems/camelotItemsWithPossitonDifference";
    }

    @RequestMapping(value = "weightItems")
    public String weightItems(ModelMap modelMap) {

        LinkedHashMap<String, Item> weightItems = pet4uItemsDao.getWeightAllItems();

        modelMap.addAttribute("weightItems", weightItems);
        return "/pet4uItems/weightItems";
    }

    @RequestMapping(value = "pet4uAllItems")
    public String pet4uAllItems(ModelMap modelMap) {

        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        modelMap.addAttribute("pet4uAllItems", pet4uAllItems);
        return "/pet4uItems/pet4uAllItems";
    }

    @RequestMapping(value = "pet4uAllItemsOneLine")
    public String pet4uAllItemsOneLine(ModelMap modelMap) {

        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        modelMap.addAttribute("pet4uAllItems", pet4uAllItems);
        return "/pet4uItems/pet4uAllItemsOneLine";
    }

    @RequestMapping(value = "pet4uItemsWithPosition")
    public String pet4uItemsWithPosition(ModelMap modelMap) {

        LinkedHashMap<String, Item> items = pet4uItemsDao.getAllItemsWithPosition();

        modelMap.addAttribute("items", items);
        return "/pet4uItems/pet4uItemsWithPosition";
    }

    public void updateItemsState() {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();
        pet4uItemsDao.insertPet4uItemsSnapshot(pet4uAllItems);
    }

    @RequestMapping(value = "itemsStateUpdates")
    public String itemsStateUpdates(ModelMap modelMap) {

        ArrayList<Item> difference = new ArrayList<>();

        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();
        LinkedHashMap<String, String> itemsStateSnapshotFromDB = pet4uItemsDao.getItemsStateSnapshot();

        for (Map.Entry<String, Item> pet4uAllItemsEntry : pet4uAllItems.entrySet()) {
            Item item = pet4uAllItemsEntry.getValue();
            String nowState = item.getState();
            String beforeState = itemsStateSnapshotFromDB.get(pet4uAllItemsEntry.getKey());
            if (beforeState == null) {
                System.out.println("New Code Here???" + pet4uAllItemsEntry.getKey());
                beforeState = "";
            }
            if (!nowState.equals(beforeState)) {
                item.setSupplier(beforeState);//i use here Supplier, becouse i dont want to add new field
                difference.add(item);
            }
        }
        modelMap.addAttribute("difference", difference);
        return "/pet4uItems/itemsStateUpdates";
    }

    @RequestMapping(value = "prosfores")
    public String prosfores(ModelMap modelMap) {

        LinkedHashMap<String, Item> prosfores = pet4uItemsDao.getOnlyProsfores();

        modelMap.addAttribute("prosfores", prosfores);
        return "/pet4uItems/prosfores";
    }

    @RequestMapping(value = "pet4uItemsOffSite")
    public String pet4uItemsOffSite(ModelMap modelMap) {

        LinkedHashMap<String, Item> items = pet4uItemsDao.getOffSiteItems();

        modelMap.addAttribute("items", items);
        return "/pet4uItems/pet4uItemsOffSite";
    }

    @RequestMapping(value = "pet4uItemSnapshots")
    public String pet4uItemSnapshots(@RequestParam(name = "code") String code, ModelMap model) {

        ArrayList<ItemSnapshot> itemSnapshots = pet4uItemsDao.getItemSnapshots(code);
        model.addAttribute("itemSnapshots", itemSnapshots);
        model.addAttribute("code", code);
        return "/pet4uItems/itemSnapshots";
    }

    

}
