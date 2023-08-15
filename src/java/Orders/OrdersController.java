package Orders;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrdersController {

    @RequestMapping(value = "ordersDashboard")
    public String ordersDashboard() {

        return "orders/ordersDashboard";
    }

    @RequestMapping(value = "ordersDashboard")
    public String orderDashboard(@RequestParam(name = "supplierId") String altercode, ModelMap modelMap) {

        return "orders/ordersDashboard";
    }

}
