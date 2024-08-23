package Order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PositioningController {

   

    @RequestMapping(value = "positioning")
    public String orderDashboard(ModelMap modelMap) {

        return "/order/positioning";
    }
}
