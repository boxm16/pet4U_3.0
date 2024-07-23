/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @RequestMapping(value = "orderDashboard")
    public String orderDashboard(ModelMap modelMap) {
        LocalDate date = LocalDate.now();
        modelMap.addAttribute("date", date);
        return "/order/orderDashboard";
    }

    @RequestMapping(value = "orderStatistics")
    public String orderStatistics(ModelMap modelMap) {
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrders();

        modelMap.addAttribute("allOrders", allOrders);

        return "/order/orderStatistics";
    }

}
