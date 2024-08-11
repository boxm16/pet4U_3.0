/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import BasicModel.Item;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public String ordersForDate(@RequestParam(name = "date") String date, ModelMap modelMap) {

        LinkedHashMap<Integer, Order> orders = orderDao.getOrdersForDate(date);
        modelMap.addAttribute("orders", orders);
        return "/order/orders";
    }

    @RequestMapping(value = "ordersOfDate")
    public String ordersOfDate(@RequestParam(name = "date") String date, ModelMap modelMap) {

        LinkedHashMap<Integer, Order> orders = orderDao.getOrdersOfDate(date);
        modelMap.addAttribute("orders", orders);
        return "/order/ordersOfDate";
    }

    @RequestMapping(value = "getOrder")
    public String getOrder(@RequestParam(name = "orderNumber") String orderNumber, ModelMap modelMap) {

        Order order = orderDao.getOrder(orderNumber);
        modelMap.addAttribute("order", order);

        return "/order/orderDisplay";
    }
    
    @RequestMapping(value = "getOrderById")
    public String getOrderById(@RequestParam(name = "orderId") String orderId, ModelMap modelMap) {

        Order order = orderDao.getOrderById(orderId);
        modelMap.addAttribute("order", order);

        return "/order/orderDisplay";
    }

    @RequestMapping(value = "codeQuantityInOrders")
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

    @RequestMapping(value = "positionsTraffic")
    public String positionsTraffic(ModelMap modelMap) {
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrders();

        TreeMap<String, Integer> positionsTraffic = new TreeMap<>();
        int totalTraffic = 0;
        for (Map.Entry<Integer, Order> allOrdersEntry : allOrders.entrySet()) {
            Order order = allOrdersEntry.getValue();
            LinkedHashMap<String, Item> items = order.getItems();
            for (Map.Entry<String, Item> itemsEntry : items.entrySet()) {
                Item item = itemsEntry.getValue();
                String position = item.getPosition();
                if (!positionsTraffic.containsKey(position)) {
                    positionsTraffic.put(position, 1);
                } else {
                    Integer t = positionsTraffic.get(position);
                    t = t + 1;
                    positionsTraffic.put(position, t);
                }
            }

            totalTraffic++;
        }

        modelMap.addAttribute("totalTraffic", totalTraffic);
        modelMap.addAttribute("positionsTraffic", positionsTraffic);

        return "/order/trafficStatistics";
    }

    @RequestMapping(value = "positionsBlockTraffic")
    public String positionsBlockTraffic(ModelMap modelMap) {
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrders();

        TreeMap<String, Integer> positionsTraffic = new TreeMap<>();
        int totalTraffic = 0;
        for (Map.Entry<Integer, Order> allOrdersEntry : allOrders.entrySet()) {
            Order order = allOrdersEntry.getValue();
            LinkedHashMap<String, Item> items = order.getItems();
            for (Map.Entry<String, Item> itemsEntry : items.entrySet()) {
                Item item = itemsEntry.getValue();
                String position = item.getPosition();
                int _count = position.length() - position.replaceAll("-", "").length();

                if (_count > 1) {
                    int first = position.indexOf("-");
                    int second = position.indexOf("-", first + 1);
                    position = position.substring(0, second);
                }

                if (!positionsTraffic.containsKey(position)) {
                    positionsTraffic.put(position, 1);
                } else {
                    Integer t = positionsTraffic.get(position);
                    t = t + 1;
                    positionsTraffic.put(position, t);
                }
            }

            totalTraffic++;
        }

        modelMap.addAttribute("totalTraffic", totalTraffic);
        modelMap.addAttribute("positionsTraffic", positionsTraffic);

        return "/order/trafficStatistics";
    }

    @RequestMapping(value = "ordersQuantityComparingAnalysis")
    public String ordersQuantityComparingAnalysis(ModelMap modelMap) {
        TreeMap<LocalDateTime, Integer> ordersQuantityByDate2023 = orderDao.countOrdersByDate2023();
        TreeMap<LocalDateTime, Integer> ordersQuantityByDate2024 = orderDao.countOrdersByDate2024();
        modelMap.addAttribute("2023", ordersQuantityByDate2023);
        modelMap.addAttribute("2024", ordersQuantityByDate2024);
        return "/order/orderQuantityComparison";
    }
}
