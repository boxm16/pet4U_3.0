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
import java.util.LinkedHashMap;
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

        //  LocalDate firstDate = date.minusDays(30);
        //  LocalDate lastDate = date.minusDays(1);
        LocalDate sd = LocalDate.parse(startDate);
        LocalDate ed = LocalDate.parse(endDate);
        System.out.println("SD:" + sd);
        System.out.println("ED:" + ed);
        sd = sd.minusDays(1);
        ed = ed.plusDays(1);
        startDate = sd.toString();
        endDate = ed.toString();
        while (sd.isBefore(ed)) {
            InputOutput inputOutput = new InputOutput();
            inputOutputs.put(ed, inputOutput);
            ed = ed.minusDays(1);

        }

        InputOutputDao inputOutputDao = new InputOutputDao();

        inputOutputs = inputOutputDao.fillSales(inputOutputs, itemCode, startDate, endDate);
        inputOutputs = inputOutputDao.fillDeliveries(inputOutputs, itemCode, startDate, endDate);
        inputOutputs = inputOutputDao.fillEndoParalaves(inputOutputs, itemCode, startDate, endDate);
        inputOutputs = inputOutputDao.fillEndoApostoles(inputOutputs, itemCode, startDate, endDate);

        modelMap.addAttribute("inputOutputs", inputOutputs);

        DailySalesDao dailySalesDao = new DailySalesDao();
        LinkedHashMap<LocalDate, DailySale> dailySales = dailySalesDao.getLast300DaysSales(item.getCode());
        modelMap.addAttribute("dailySales", dailySales);

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<LocalDate, ItemSnapshot> allSnapshots = pet4uItemsDao.getItemSnapshotsFullVersion(item.getCode());
        modelMap.addAttribute("allSnapshots", allSnapshots);
        // System.out.println("Retrieving Last 100 Days Snapshot. Done: " + LocalDateTime.now());

        return "/inputOutput/inputOutputDisplay";
    }

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
}
