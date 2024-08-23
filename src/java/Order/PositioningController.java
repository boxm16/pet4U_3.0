package Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PositioningController {

    @Autowired
    private PositioningDao positioningDao;

    @RequestMapping(value = "positioning")
    public String orderDashboard(ModelMap modelMap) {

        return "/order/positioning";
    }
}
