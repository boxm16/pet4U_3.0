package DailySales;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DailySalesController {

    @RequestMapping(value = "getAllSalesDocsForDateAndItem")
    public String deliveryInvoicesForDate(@RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "date") String date, ModelMap modelMap) {

        return "/order/ordersOfDate";
    }

}
