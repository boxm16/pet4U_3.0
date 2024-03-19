/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonthSales;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EksagogesControllerB {

    @RequestMapping(value = "/sixMonthsSalesY", method = RequestMethod.GET)
    public String getLastSixMonthsSales(ModelMap modelMap) {

        modelMap.addAttribute("eksagoges", getLastSixMonthsSales());

        return "monthSales/sixMonthsSales";
    }

    public LinkedHashMap<String, ItemEksagoges> getLastSixMonthsSales() {
        return getLastMonthsSales(6);
    }

    public LinkedHashMap<String, ItemEksagoges> getLastForMonthsSales() {
        return getLastMonthsSales(4);
    }

    public LinkedHashMap<String, ItemEksagoges> getLastTwoMonthsSales() {
        return getLastMonthsSales(2);
    }

    public LinkedHashMap<String, ItemEksagoges> getLastMonthSales() {
        return getLastMonthsSales(1);
    }

    private LinkedHashMap<String, ItemEksagoges> getLastMonthsSales(int months) {
        LinkedHashMap<String, ItemEksagoges> refactoredEksagoges = new LinkedHashMap<>();
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemsWithPositions = pet4uItemsDao.getAllItems();

        EksagogesDaoB eksagogesDao = new EksagogesDaoB();
        LinkedHashMap<String, ItemEksagoges> itemsWithEksagoges = eksagogesDao.getLastMonthsSales(months);

        for (Map.Entry<String, Item> itemsWithPositionEntry : itemsWithPositions.entrySet()) {
            String key = itemsWithPositionEntry.getKey();
            ItemEksagoges itemWithEksagoges = itemsWithEksagoges.get(key);

            if (itemWithEksagoges == null) {
                System.out.println("No Sales For This Code: " + key);
                Item itemWithPosition = itemsWithPositionEntry.getValue();

                ItemEksagoges refactoredItemEksagoges = new ItemEksagoges();
                refactoredItemEksagoges.setCode(itemWithPosition.getCode());
                refactoredItemEksagoges.setDescription(itemWithPosition.getDescription());
                refactoredItemEksagoges.setPosition(itemWithPosition.getPosition());
                refactoredItemEksagoges.setAltercodes(itemWithPosition.getAltercodes());
                refactoredItemEksagoges.setState(itemWithPosition.getState());
                refactoredItemEksagoges.setQuantity(itemWithPosition.getQuantity());
                refactoredEksagoges.put(key, refactoredItemEksagoges);

            } else {

                Item itemWithPosition = itemsWithPositionEntry.getValue();
                ItemEksagoges refactoredItemEksagoges = new ItemEksagoges();
                refactoredItemEksagoges.setCode(itemWithPosition.getCode());
                refactoredItemEksagoges.setDescription(itemWithPosition.getDescription());
                refactoredItemEksagoges.setPosition(itemWithPosition.getPosition());
                refactoredItemEksagoges.setAltercodes(itemWithPosition.getAltercodes());
                refactoredItemEksagoges.setState(itemWithPosition.getState());
                refactoredItemEksagoges.setQuantity(itemWithPosition.getQuantity());

                TreeMap<LocalDate, EksagogesB> eksagoges = itemWithEksagoges.getEksagoges();
                refactoredItemEksagoges.setEksagoges(eksagoges);

                refactoredEksagoges.put(key, refactoredItemEksagoges);
            }

        }
        return refactoredEksagoges;
    }

}
