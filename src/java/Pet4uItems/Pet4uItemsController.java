package Pet4uItems;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemOfInterest;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
                    if (!pet4uItem.getPosition().equals(camelotItem.getPosition())) {
                        ArrayList<String> diff = new ArrayList<>();
                        diff.add(pet4uItem.getCode());
                        diff.add(pet4uItem.getDescription());
                        diff.add(pet4uItem.getPosition());
                        diff.add(camelotItem.getPosition());
                        differences.add(diff);
                        break;
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

}
