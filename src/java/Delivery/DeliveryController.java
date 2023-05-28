package Delivery;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeliveryController {

    @RequestMapping(value = "deliveryDashboard")
    public String deliveryDashboard(ModelMap modelMap) {
        LinkedHashMap<String, Item> deliveredItems = new LinkedHashMap();
        //  CamelotItemsOfInterestDao camelotItemsOfInteresDao = new CamelotItemsOfInterestDao();
        //    LinkedHashMap<String, Item> pet4UItemsRowByRow = camelotItemsOfInteresDao.getPet4UItemsRowByRow();

        Item item = new Item();
        item.setDescription("FCN URINARY CARE 2KG");
        AltercodeContainer altercodeContainer = new AltercodeContainer();
        String barcode = "2275020";
        altercodeContainer.setAltercode(barcode);
        altercodeContainer.setStatus("");
        item.addAltercodeContainer(altercodeContainer);
        item.setQuantity("3");
        deliveredItems.put(barcode, item);

        Item item1 = new Item();
        item1.setDescription("FCN ORAL SENSITIVE 1.5K");
        AltercodeContainer altercodeContainer1 = new AltercodeContainer();
        String barcode1 = "2274015";
        altercodeContainer1.setAltercode(barcode1);
        altercodeContainer1.setStatus("");
        item1.addAltercodeContainer(altercodeContainer1);
        item1.setQuantity("2");
        deliveredItems.put(barcode1, item1);
        
        Item item2 = new Item();
        item2.setDescription("FCN DIGESTIVE COMFORT 2K");
        AltercodeContainer altercodeContainer2 = new AltercodeContainer();
        String barcode2 = "2270020";
        altercodeContainer2.setAltercode(barcode1);
        altercodeContainer2.setStatus("");
        item2.addAltercodeContainer(altercodeContainer2);
        item2.setQuantity("1");
        deliveredItems.put(barcode2, item2);

        modelMap.addAttribute("deliveredItems", deliveredItems);
        return "delivery/deliveryDashboard";
    }

}
