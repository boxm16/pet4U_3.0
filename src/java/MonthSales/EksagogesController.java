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
public class EksagogesController {

    @RequestMapping(value = "/sixMonthsSalesY", method = RequestMethod.GET)
    public String getLastSixMonthsSales(ModelMap modelMap) {
        LinkedHashMap<String, ItemEksagoges> refactoredEksagoges = new LinkedHashMap<>();
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> itemsWithPositions = pet4uItemsDao.getAllItems();

        EksagogesDao eksagogesDao = new EksagogesDao();
        LinkedHashMap<String, ItemEksagoges> itemsWithEksagoges = eksagogesDao.getLastMonthsSales(6);

        for (Map.Entry<String, Item> itemsWithPositionEntry : itemsWithPositions.entrySet()) {
            String key = itemsWithPositionEntry.getKey();
            ItemEksagoges itemWithEksagoges = itemsWithEksagoges.get(key);

            if (itemWithEksagoges == null) {
                 System.out.println("NULLLLLLL");
                Item itemWithPosition = itemsWithPositionEntry.getValue();

                ItemEksagoges refactoredItemEksagoges = new ItemEksagoges();
                refactoredItemEksagoges.setCode(itemWithPosition.getCode());
                refactoredItemEksagoges.setDescription(itemWithPosition.getDescription());
                refactoredItemEksagoges.setPosition(itemWithPosition.getPosition());
                refactoredItemEksagoges.setAltercodes(itemWithPosition.getAltercodes());
                refactoredItemEksagoges.setState(itemWithPosition.getState());
                refactoredEksagoges.put(key, refactoredItemEksagoges);

            } else {
                System.out.println("NIT NULL");
                Item itemWithPosition = itemsWithPositionEntry.getValue();
                ItemEksagoges refactoredItemEksagoges = new ItemEksagoges();
                refactoredItemEksagoges.setCode(itemWithPosition.getCode());
                refactoredItemEksagoges.setDescription(itemWithPosition.getDescription());
                refactoredItemEksagoges.setPosition(itemWithPosition.getPosition());
                refactoredItemEksagoges.setAltercodes(itemWithPosition.getAltercodes());
                refactoredItemEksagoges.setState(itemWithPosition.getState());
                TreeMap<LocalDate, Eksagoges> eksagoges = itemWithEksagoges.getEksagoges();
               
                refactoredItemEksagoges.setEksagoges(eksagoges);
                refactoredEksagoges.put(key, refactoredItemEksagoges);
            }

        }

        modelMap.addAttribute("eksagoges", refactoredEksagoges);

        return "monthSales/sixMonthsSales";
    }

}
