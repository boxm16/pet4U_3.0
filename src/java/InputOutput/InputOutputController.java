/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputOutput;

import BasicModel.Item;
import CamelotItemsOfInterest.ItemSnapshot;
import DailySales.DailySale;
import DailySales.DailySalesDao;
import Pet4uItems.Pet4uItemsDao;
import Search.SearchDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InputOutputController {

    @RequestMapping(value = "inputOutput")
    public String inputOutput(@RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {
        if (itemCode.isEmpty()) {
            modelMap.addAttribute("code", itemCode);
            modelMap.addAttribute("message", "Empty text.");

            return "analitica/itemAnalysisErrorPage";
        }
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(itemCode);

        if (item == null) {
            modelMap.addAttribute("code", itemCode);
            modelMap.addAttribute("message", "No such code in Pet4u Database");

            return "analitica/itemAnalysisErrorPage";
        }
        modelMap.addAttribute("item", item);
        LinkedHashMap<LocalDate, InputOutput> inputOutputs = new LinkedHashMap<>();

        LocalDate sd = LocalDate.parse(startDate);
        LocalDate ed = LocalDate.parse(endDate);

        String startDateX = "";
        String endDateX = "";
        sd = sd.minusDays(1);
        ed = ed.plusDays(1);
        startDateX = sd.toString();
        endDateX = ed.toString();
        while (ed.isAfter(sd)) {
            InputOutput inputOutput = new InputOutput();
            inputOutputs.put(sd, inputOutput);
            sd = sd.plusDays(1);
        }

        InputOutputDao inputOutputDao = new InputOutputDao();

        inputOutputs = inputOutputDao.fillSales(inputOutputs, itemCode, startDateX, endDateX);
        inputOutputs = inputOutputDao.fillDeliveries(inputOutputs, itemCode, startDateX, endDateX);
        inputOutputs = inputOutputDao.fillEndoParalaves(inputOutputs, itemCode, startDateX, endDateX);
        inputOutputs = inputOutputDao.fillCamelotParalaves(inputOutputs, itemCode, startDateX, endDateX);
       
        inputOutputs = inputOutputDao.fillEndoApostoles(inputOutputs, itemCode, startDateX, endDateX);

        inputOutputs = inputOutputDao.fillWithSnapshots(inputOutputs, itemCode, startDateX, endDateX);

        modelMap.addAttribute("inputOutputs", inputOutputs);

        return "/inputOutput/inputOutputDisplay";
    }

    //this is reserva for method above
    @RequestMapping(value = "inputOutputR")
    public String inputOutputR(@RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, ModelMap modelMap) {
        if (itemCode.isEmpty()) {
            modelMap.addAttribute("code", itemCode);
            modelMap.addAttribute("message", "Empty text.");

            return "analitica/itemAnalysisErrorPage";
        }
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(itemCode);

        if (item == null) {
            modelMap.addAttribute("code", itemCode);
            modelMap.addAttribute("message", "No such code in Pet4u Database");

            return "analitica/itemAnalysisErrorPage";
        }
        modelMap.addAttribute("item", item);

        DailySalesDao dailySalesDao = new DailySalesDao();
        LinkedHashMap<LocalDate, DailySale> dailySales = dailySalesDao.getLast300DaysSales(item.getCode());
        modelMap.addAttribute("dailySales", dailySales);

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<LocalDate, ItemSnapshot> allSnapshots = pet4uItemsDao.getItemSnapshotsFullVersion(item.getCode());
        modelMap.addAttribute("allSnapshots", allSnapshots);
        // System.out.println("Retrieving Last 100 Days Snapshot. Done: " + LocalDateTime.now());

        return "/order/inputOutput";
    }

    /////
    @RequestMapping(value = "inputOutputAlarms")
    public String inputOutputAlarms(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate, @RequestParam(value = "shortVersion", required = false) String checkboxValue, ModelMap modelMap) {

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> allItems = pet4uItemsDao.getAllItems();

        LinkedHashMap<String, InputOutputContainer> inputOutputContainers = new LinkedHashMap<String, InputOutputContainer>();
        int a = 0;
        int b = 0;
        String startDateX = "";
        String endDateX = "";
        ArrayList<String> targetItemCodes = new ArrayList();
        for (Map.Entry<String, Item> allItemsEntry : allItems.entrySet()) {
            Item item = allItemsEntry.getValue();
            if (item.getPosition() == null || item.getPosition().equals("")) {
                // System.out.println("A" + a++);
                continue;
            }
            targetItemCodes.add(item.getCode());

            LinkedHashMap<LocalDate, InputOutput> inputOutputs = new LinkedHashMap<>();

            LocalDate sd = LocalDate.parse(startDate);
            LocalDate ed = LocalDate.parse(endDate);

            /*sd = sd.minusDays(1);
            ed = ed.plusDays(1);
            while (sd.isBefore(ed)) {
                InputOutput inputOutput = new InputOutput();
                inputOutputs.put(ed, inputOutput);
                ed = ed.minusDays(1);

            }*/
            sd = sd.minusDays(1);
            ed = ed.plusDays(1);
            startDateX = sd.toString();
            endDateX = ed.toString();
            while (ed.isAfter(sd)) {
                InputOutput inputOutput = new InputOutput();
                inputOutputs.put(sd, inputOutput);
                sd = sd.plusDays(1);
            }

            InputOutputContainer inputOutputContainer = new InputOutputContainer();
            inputOutputContainer.setItem(item);
            inputOutputContainer.setInputOutputs(inputOutputs);
            inputOutputContainers.put(allItemsEntry.getKey(), inputOutputContainer);

            //    System.out.println("B" + b++);
            if (b > 300) {
                break;
            }
            // b++;
        }

        StringBuilder inPartForSqlQuery = buildStringFromArrayList(targetItemCodes);

        InputOutputDao inputOutputDao = new InputOutputDao();
        inputOutputContainers = inputOutputDao.fillInputOutputContainersWithDeliveries(inputOutputContainers, inPartForSqlQuery, startDateX, endDateX);
        inputOutputContainers = inputOutputDao.fillInputOutputContainersWithEndoParalaves(inputOutputContainers, inPartForSqlQuery, startDateX, endDateX);
        inputOutputContainers = inputOutputDao.fillInputOutputContainersWithCamelotParalaves(inputOutputContainers, inPartForSqlQuery, startDateX, endDateX);

        inputOutputContainers = inputOutputDao.fillInputOutputContainersWithEndoApostoles(inputOutputContainers, inPartForSqlQuery, startDateX, endDateX);
        inputOutputContainers = inputOutputDao.fillInputOutputContainersWithSales(inputOutputContainers, inPartForSqlQuery, startDateX, endDateX);

        //inputOutputs = inputOutputDao.fillEndoApostoles(inputOutputs, itemCode, startDate, endDate);
        //  LinkedHashMap<LocalDate, ItemSnapshot> allSnapshots = inputOutputDao.combineInputOutputContainersWithSnapshots(inputOutputContainers, inPartForSqlQuery, startDateX, endDateX);
        inputOutputContainers = inputOutputDao.fillInputOutputContainersWithSnapshots(inputOutputContainers, inPartForSqlQuery, startDateX, endDateX);
        modelMap.addAttribute("inputOutputContainers", inputOutputContainers);
        //    modelMap.addAttribute("allSnapshots", allSnapshots);
        if (checkboxValue != null) {
            return "/inputOutput/inputOutputAlarmsSV";
        } else {
            return "/inputOutput/inputOutputAlarms";
        }

    }

    private StringBuilder buildStringFromArrayList(ArrayList<String> arrayList) {

        StringBuilder stringBuilder = new StringBuilder("(");
        if (arrayList.isEmpty()) {
            stringBuilder.append(")");
            return stringBuilder;
        }
        int x = 0;
        for (String entry : arrayList) {
            if (x == 0) {
                stringBuilder.append("'").append(entry).append("'");
            } else {
                stringBuilder.append(",'").append(entry).append("'");
            }
            if (x == arrayList.size() - 1) {
                stringBuilder.append(")");
            }
            x++;
        }
        return stringBuilder;
    }

}
