package Orders;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrdersController {

    @RequestMapping(value = "ordersDashboard")
    public String ordersDashboard() {
        
        return "orders/ordersDashboard";
    }

}
