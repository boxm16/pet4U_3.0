/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonthSales;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import java.util.LinkedHashMap;
import java.util.Map;
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
            ItemEksagoges itemEksagoges = itemsWithEksagoges.get(key);

            if (itemEksagoges == null) {
                Item itemWithPosition = itemsWithPositionEntry.getValue();

                itemEksagoges = new ItemEksagoges();
                itemEksagoges.setCode(itemWithPosition.getCode());
                itemEksagoges.setDescription(itemWithPosition.getDescription());
                itemEksagoges.setPosition(itemWithPosition.getPosition());
                itemEksagoges.setAltercodes(itemWithPosition.getAltercodes());
                itemEksagoges.setState(itemWithPosition.getState());
                // itemSales.setSales(itemWithSales.getSales());
            } else {
                Item itemWithPosition = itemsWithPositionEntry.getValue();
                itemEksagoges = new ItemEksagoges();
                itemEksagoges.setCode(itemWithPosition.getCode());
                itemEksagoges.setDescription(itemWithPosition.getDescription());
                itemEksagoges.setPosition(itemWithPosition.getPosition());
                itemEksagoges.setAltercodes(itemWithPosition.getAltercodes());
                itemEksagoges.setState(itemWithPosition.getState());

                itemEksagoges.setEksagoges(itemEksagoges.getEksagoges());
            }
            refactoredEksagoges.put(key, itemEksagoges);
        }

        modelMap.addAttribute("eksagoges", refactoredEksagoges);
       
        return "monthSales/sixMonthsSales";
    }

}
