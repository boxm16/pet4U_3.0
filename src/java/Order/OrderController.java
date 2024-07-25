/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(value = "ordersForDate")
    public String deliveryInvoicesForDate(@RequestParam(name = "date") String date, ModelMap modelMap) {

        LinkedHashMap<Integer, Order> orders = orderDao.getOrdersForDate(date);
        modelMap.addAttribute("orders", orders);
        return "/order/orders";
    }

    @RequestMapping(value = "getOrder")
    public String getOrder(@RequestParam(name = "orderNumber") String orderNumber, ModelMap modelMap) {

        Order order = orderDao.getOrder(orderNumber);
        modelMap.addAttribute("order", order);
        
        return "/order/orderDisplay";
    }

    @RequestMapping(value = "ordersSixMonthsStatistics")
    public String orderStatistics(ModelMap modelMap) {
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrders();

        LinkedHashMap<Integer, Integer> codeQuantityInOrders = countCodesQuantityInOrders(allOrders);

        modelMap.addAttribute("codeQuantityInOrders", codeQuantityInOrders);

        return "/order/orderStatistics";
    }

    private LinkedHashMap<Integer, Integer> countCodesQuantityInOrders(LinkedHashMap<Integer, Order> allOrders) {
        LinkedHashMap<Integer, Integer> codesQuantityInOrders = new LinkedHashMap<>();

        for (Map.Entry<Integer, Order> allOrdersEntry : allOrders.entrySet()) {
            Order order = allOrdersEntry.getValue();
            int codesQuantity = order.getItems().size();
        }
        return codesQuantityInOrders;
    }

}
