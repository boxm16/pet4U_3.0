/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
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

        TreeMap<Integer, Integer> codesQuantityInOrders = new TreeMap<>();
        int totalOrders = 0;
        for (Map.Entry<Integer, Order> allOrdersEntry : allOrders.entrySet()) {
            Order order = allOrdersEntry.getValue();
            int codesQuantity = order.getItems().size();
            if (!codesQuantityInOrders.containsKey(codesQuantity)) {
                codesQuantityInOrders.put(codesQuantity, 1);
            } else {
                Integer cq = codesQuantityInOrders.get(codesQuantity);
                cq = cq + 1;
                codesQuantityInOrders.put(codesQuantity, cq);
            }
            totalOrders++;
        }

        modelMap.addAttribute("totalOrders", totalOrders);
        modelMap.addAttribute("codesQuantityInOrders", codesQuantityInOrders);

        return "/order/orderStatistics";
    }

    @RequestMapping(value = "getOrdersWithCodeQuantity")
    public String getOrdersWithCodeQuantity(@RequestParam(name = "quantity") String quantity, ModelMap modelMap) {
        LinkedHashMap<Integer, Order> orders = new LinkedHashMap();
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrders();
        for (Map.Entry<Integer, Order> allOrdersEntry : allOrders.entrySet()) {
            int size = allOrdersEntry.getValue().getItems().size();
            if (size == Integer.valueOf(quantity)) {
                orders.put(allOrdersEntry.getValue().getId(), allOrdersEntry.getValue());
            }
        }
        modelMap.addAttribute("orders", orders);
        return "/order/orders";
    }

}
