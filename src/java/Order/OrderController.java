/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Order;

import BasicModel.Item;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        LocalDate startDate = LocalDate.parse("2024-01-01");
        LocalDate nowDate = LocalDate.now();
        modelMap.addAttribute("date", date);
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("nowDate", nowDate);
        modelMap.addAttribute("before10DaysDate", nowDate.minusDays(10));
        return "/order/orderDashboard";
    }

    @RequestMapping(value = "ordersTimeStructureOfDate")
    public String ordersTimeStructureOfDate(@RequestParam(name = "date") String date, ModelMap modelMap) {
        LinkedHashMap<Integer, Integer> ordersTimeStrucuterOfDate = new LinkedHashMap<>();

        LinkedHashMap<String, Integer> ordersThreeLayersTimeStrucuterOfDate = new LinkedHashMap<>();
        ordersThreeLayersTimeStrucuterOfDate.put("00-11", 0);
        ordersThreeLayersTimeStrucuterOfDate.put("11-15", 0);
        ordersThreeLayersTimeStrucuterOfDate.put("15-24", 0);
        LinkedHashMap<Integer, Order> orders = orderDao.getOrdersOfDate(date);
        LocalDateTime startTime = LocalDateTime.parse(date + "T00:00:00.000");

        for (int x = 0; x < 24; x++) {
            ordersTimeStrucuterOfDate.put(x, 0);
        }
        int total = 0;
        for (Map.Entry<Integer, Order> orderEntry : orders.entrySet()) {
            LocalDateTime creationDateTime = orderEntry.getValue().getCreationDateTime();
            int hour = creationDateTime.getHour();
            Integer q = ordersTimeStrucuterOfDate.get(hour);
            q++;
            total++;
            ordersTimeStrucuterOfDate.put(hour, q);
            if (hour < 11) {
                Integer j = ordersThreeLayersTimeStrucuterOfDate.get("00-11");
                j++;
                ordersThreeLayersTimeStrucuterOfDate.put("00-11", j);
            }
            if (hour >= 11 && hour < 15) {
                Integer k = ordersThreeLayersTimeStrucuterOfDate.get("11-15");
                k++;
                ordersThreeLayersTimeStrucuterOfDate.put("11-15", k);
            }
            if (hour >= 15) {
                Integer l = ordersThreeLayersTimeStrucuterOfDate.get("15-24");
                l++;
                ordersThreeLayersTimeStrucuterOfDate.put("15-24", l);
            }
        }
        modelMap.addAttribute("date", date);
        modelMap.addAttribute("ordersTimeStrucuterOfDate", ordersTimeStrucuterOfDate);
        modelMap.addAttribute("ordersThreeLayersTimeStrucuterOfDate", ordersThreeLayersTimeStrucuterOfDate);

        modelMap.addAttribute("total", total);
        return "/order/ordersTimeStructureOfDate";
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
        modelMap.addAttribute("position", "");
        modelMap.addAttribute("orders", orders);
        return "/order/ordersOfDate";
    }

    @RequestMapping(value = "getAllSalesDocsOfDateAndItem")
    public String getAllSalesDocsOfDateAndItem(@RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "date") String date, ModelMap modelMap) {
        LinkedHashMap<Integer, Order> orders0 = orderDao.getOrdersOfDate(date);
        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<Integer, Order>();
        for (Map.Entry<Integer, Order> ordersEntry : orders0.entrySet()) {
            if (ordersEntry.getValue().getItems().containsKey(itemCode)) {
                orders.put(ordersEntry.getKey(), ordersEntry.getValue());
            }
        }
        modelMap.addAttribute("orders", orders);
        modelMap.addAttribute("position", "===");
        modelMap.addAttribute("itemCode", itemCode);
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

    @RequestMapping(value = "positionsBlockTrafficForPeriod")
    public String positionsBlockTrafficForPeriod(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrdersForPeriod(startDate, endDate);

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
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("endDate", endDate);

        return "/order/trafficStatisticsForPeriod";
    }

    @RequestMapping(value = "allOrdersForPositionBlockForPeriod")
    public String allOrdersForPositionBlockForPeriod(@RequestParam(name = "position") String position0, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {

        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrdersForPeriod(startDate, endDate);
        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<Integer, Order>();

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

                if (position0.equals(position)) {
                    orders.put(allOrdersEntry.getKey(), order);
                }
            }

        }

        modelMap.addAttribute("position", position0);
        modelMap.addAttribute("orders", orders);
        return "/order/ordersOfDate";

    }

    @RequestMapping(value = "ordersQuantityComparingAnalysis")
    public String ordersQuantityComparingAnalysis(ModelMap modelMap) {
        TreeMap<LocalDate, Integer> ordersQuantityByDate2023 = orderDao.countOrdersByDate2023();
        TreeMap<LocalDate, Integer> ordersQuantityByDate2024 = orderDao.countOrdersByDate2024();
        modelMap.addAttribute("2023", ordersQuantityByDate2023);
        modelMap.addAttribute("2024", ordersQuantityByDate2024);
        return "/order/orderQuantityComparison";
    }

    @RequestMapping(value = "positionsBlockTrafficForPeriodOneOrderOneVisit")
    public String positionsBlockTrafficForPeriodOneOrderOneVisit(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllOrdersForPeriod(startDate, endDate);

        TreeMap<String, Integer> positionsTraffic = new TreeMap<>();

        for (Map.Entry<Integer, Order> allOrdersEntry : allOrders.entrySet()) {
            Order order = allOrdersEntry.getValue();
            ArrayList<String> innerPool = new ArrayList();//if order has more then 1 codes that are for same block, i need this array so all codes are counted as one visit
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
                    innerPool.add(position);

                } else {
                    Integer t = positionsTraffic.get(position);
                    if (!innerPool.contains(position)) {

                        t = t + 1;
                        positionsTraffic.put(position, t);

                    }
                }
            }

        }

        //  modelMap.addAttribute("totalTraffic", totalTraffic);
        modelMap.addAttribute("positionsBlockTrafficOneOrderOneVisit", positionsTraffic);
        modelMap.addAttribute("startDate", startDate);
        modelMap.addAttribute("endDate", endDate);

        return "/order/trafficStatisticsForPeriodOneOrderOneVisit";
    }

    @RequestMapping(value = "getAllDocsForItemBetweenTwoDates")
    public String getAllDocsForItemBetweenTwoDates(@RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {
        LinkedHashMap<Integer, Order> orders0 = orderDao.getAllDocs(startDate, endDate);
        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<Integer, Order>();
        for (Map.Entry<Integer, Order> ordersEntry : orders0.entrySet()) {
            if (ordersEntry.getValue().getItems().containsKey(itemCode)) {
                orders.put(ordersEntry.getKey(), ordersEntry.getValue());
            }
        }
        modelMap.addAttribute("itemCode", itemCode);
        modelMap.addAttribute("orders", orders);
        modelMap.addAttribute("position", "====");//=== is just bullshit
        return "/order/allDocs";
    }

    @RequestMapping(value = "getAllDocsForItemBetweenTwoDatesWithThisBlockPosition")
    public String getAllDocsForItemBetweenTwoDatesWithThisBlockPosition(@RequestParam(name = "blockPosition") String blockPosition, @RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {

        LinkedHashMap<Integer, Order> orders0 = orderDao.getAllDocs(startDate, endDate);
        LinkedHashMap<Integer, Order> orders = new LinkedHashMap<Integer, Order>();
        for (Map.Entry<Integer, Order> ordersEntry : orders0.entrySet()) {
            if (ordersEntry.getValue().getItems().containsKey(itemCode)) {
                LinkedHashMap<String, Item> items = ordersEntry.getValue().getItems();
                for (Map.Entry<String, Item> itemsEntry : items.entrySet()) {
                    if (itemsEntry.getValue().getPosition().contains(blockPosition)) {
                        orders.put(ordersEntry.getKey(), ordersEntry.getValue());
                    }
                }
            }
        }
        modelMap.addAttribute("itemCode", itemCode);
        modelMap.addAttribute("orders", orders);
        modelMap.addAttribute("position", blockPosition);//=== is just bullshit
        return "/order/allDocs";
    }

    @RequestMapping(value = "itemsCollateralPositions")
    public String itemsCollateralPositions(@RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {
        LinkedHashMap<Integer, Order> allOrders = orderDao.getAllDocs(startDate, endDate);
        TreeMap<String, Integer> positionsTraffic = new TreeMap<>();
        int totalTraffic = 0;

        for (Map.Entry<Integer, Order> allOrdersEntry : allOrders.entrySet()) {
            Order order = allOrdersEntry.getValue();
            ArrayList<String> innerPool = new ArrayList();//if order has more then 1 codes that are for same block, i need this array so all codes are counted as one visit

            LinkedHashMap<String, Item> items = order.getItems();
            if (items.containsKey(itemCode)) {
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
                        innerPool.add(position);

                    } else {
                        Integer t = positionsTraffic.get(position);
                        if (!innerPool.contains(position)) {

                            t = t + 1;
                            positionsTraffic.put(position, t);

                        }
                    }

                    totalTraffic++;
                }
            }
            modelMap.addAttribute("itemCode", itemCode);
            modelMap.addAttribute("totalTraffic", totalTraffic);
            modelMap.addAttribute("positionsTraffic", positionsTraffic);
            modelMap.addAttribute("startDate", startDate);
            modelMap.addAttribute("endDate", endDate);

            return "/order/itemsCollateralPositions";

            //  modelMap.addAttribute("totalTraffic", totalTraffic);
            modelMap.addAttribute("positionsBlockTrafficOneOrderOneVisit", positionsTraffic);
            modelMap.addAttribute("startDate", startDate);
            modelMap.addAttribute("endDate", endDate);

        }
    }
