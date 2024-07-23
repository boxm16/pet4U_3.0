/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @RequestMapping(value = "orderStatistics")
    public String orderStatistics(ModelMap modelMap) {

        modelMap.addAttribute("orderStatistics", "");

        return "/order/orderStatistics";
    }
    

}
