/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotItemsOfOurInterest_V_3_1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotItemsOfOurInterestController {

    @Autowired
    private CamelotItemsOfOurInterestDao camelotItemsOfOurInterestDao;

    @RequestMapping(value = "camelotItemsOfInterestDashboard")
    public String camelotItemsOfInterestDashboard(ModelMap modelMap) {

        modelMap.addAttribute("camelotItemsOfOurInterest", getCamelotItemsOfOurInterest());

        return "/camelot/camelotItemsOfInterestDashboard";
    }

    private LinkedHashMap<String, CamelotItemOfInterest> getCamelotItemsOfOurInterest() {
        //Sequence is important
        LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfOurInterest = camelotItemsOfOurInterestDao.getCamelotItemsOfOurInterset();
        ArrayList referalAltercodes = new ArrayList(camelotItemsOfOurInterest.keySet());
        StringBuilder inPartForSqlQueryByReferralAltercodes = buildStringFromArrayList(referalAltercodes);

        camelotItemsOfOurInterest = camelotItemsOfOurInterestDao.addCamelotData(camelotItemsOfOurInterest, inPartForSqlQueryByReferralAltercodes);

        camelotItemsOfOurInterest = camelotItemsOfOurInterestDao.addPet4uBasicData(camelotItemsOfOurInterest, inPartForSqlQueryByReferralAltercodes);

        ArrayList itemCodes = new ArrayList(camelotItemsOfOurInterest.keySet());
        StringBuilder inPartForSqlQueryByItemCodes = buildStringFromArrayList(itemCodes);

        ArrayList<String> salesPeriod = camelotItemsOfOurInterestDao.getSalesPeriod();
        ArrayList<String> lastSixMonths = new ArrayList<>();
        for (int x = salesPeriod.size() - 6; x < salesPeriod.size(); x++) {
            lastSixMonths.add(salesPeriod.get(x));
        }

        StringBuilder lastSixMonthsForSqlQuery = buildStringFromArrayList(lastSixMonths);
        camelotItemsOfOurInterest = camelotItemsOfOurInterestDao.addSalesData(camelotItemsOfOurInterest, inPartForSqlQueryByItemCodes, lastSixMonthsForSqlQuery);

        camelotItemsOfOurInterest = camelotItemsOfOurInterestDao.addPet4ULast30DaysSalesData(camelotItemsOfOurInterest, inPartForSqlQueryByItemCodes);

        camelotItemsOfOurInterest = camelotItemsOfOurInterestDao.addCamelotLast30DaysSalesData(camelotItemsOfOurInterest, inPartForSqlQueryByItemCodes);
        return camelotItemsOfOurInterest;
    }

    @RequestMapping(value = "camelotOrderAlert")
    public String camelotOrderAlert(ModelMap modelMap) {

        modelMap.addAttribute("camelotItemsOfOurInterest", getCamelotItemsOfOurInterest());

        return "/camelot/camelotOrderAlert";
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
