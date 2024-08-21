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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController_V_3_1 {

    @Autowired
    private OrderDao_V_3_1 orderDao;

    @RequestMapping(value = "orderDashboard")
    public String orderDashboard(ModelMap modelMap) {
        LocalDate date = LocalDate.now();
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate nowDate = LocalDate.now();
        modelMap.addAttribute("date", date);
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("nowDate", nowDate);
        return "/order/orderDashboard";
    }
      @RequestMapping(value = "ordersOfDate")
    public String ordersOfDate(@RequestParam(name = "date") String date, ModelMap modelMap) {

        LinkedHashMap<Integer, Order> orders = orderDao.getOrdersOfDate(date);
        modelMap.addAttribute("orders", orders);
        return "/order/ordersOfDate";
    }


    @RequestMapping(value = "ordersForDate")
    public String ordersForDate(@RequestParam(name = "date") String date, ModelMap modelMap) {

        LinkedHashMap<Integer, Order> orders = orderDao.getOrdersForDate(date);
        modelMap.addAttribute("orders", orders);
        return "/order/orders";
    }

  
}
