/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotReplenishment;

import BasicModel.Item;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotReplenishmentController {

    @RequestMapping(value = "goForCamelotReplenishment", method = RequestMethod.GET)
    public String goForReplenishment(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        CamelotReplenishmentDao camelotReplenishmentDao = new CamelotReplenishmentDao();
        Item item = camelotReplenishmentDao.getItemForReplenishment(altercode);

        CamelotReplenishment replenishment = camelotReplenishmentDao.getItemReplenishment(item.getCode());

        if (replenishment == null) {
            replenishment = new CamelotReplenishment();
            replenishment.setCode(item.getCode());
            replenishment.setReplenishmentQuantity(0);

            replenishment.setCode(item.getCode());
            replenishment.setDescription(item.getDescription());
            replenishment.setAltercodes(item.getAltercodes());
            replenishment.setQuantity(item.getQuantity());
            replenishment.setPosition(item.getPosition());
            modelMap.addAttribute("replenishment", replenishment);
            modelMap.addAttribute("saveType", "insertCamelotReplenishment.htm");
            return "camelotReplenishment/replenishmentSettingsServant";
        } else {
            modelMap.addAttribute("replenishment", replenishment);
            replenishment.setCode(item.getCode());

            replenishment.setDescription(item.getDescription());
            replenishment.setAltercodes(item.getAltercodes());
            replenishment.setQuantity(item.getQuantity());
            replenishment.setPosition(item.getPosition());
            modelMap.addAttribute("replenishment", replenishment);
            modelMap.addAttribute("saveType", "updateCamelotReplenishment.htm");
            return "camelotReplenishment/replenishmentServant";
        }
    }

    @RequestMapping(value = "insertCamelotReplenishment", method = RequestMethod.POST)
    public String insertReplenishment(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "replenishmentUnit") String replenishmentUnit,
            @RequestParam(name = "itemsInReplenishmentUnit") String itemsInReplenishmentUnit,
            @RequestParam(name = "minimalShelfStock") String minimalShelfStock,
            @RequestParam(name = "replenishmentQuantity") String replenishmentQuantity,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        CamelotReplenishmentDao replenishmentDao = new CamelotReplenishmentDao();
        String result = replenishmentDao.insertReplenishment(itemCode, replenishmentUnit, itemsInReplenishmentUnit, minimalShelfStock, replenishmentQuantity, note);
        String resultColor = "";
        if (!result.equals("New Replenishment Done Successfully")) {
            result = "ΚΑΤΙ ΠΗΓΕ ΣΤΡΑΒΑ. <br>" + result;
            resultColor = "red";
        }
        model.addAttribute("result", result);
        model.addAttribute("resultColor", resultColor);
        return "camelotReplenishment/replenishmentSavingResult";

    }

    @RequestMapping(value = "updateCamelotReplenishment", method = RequestMethod.POST)
    public String updateCamelotReplenishment(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "replenishmentQuantity") String replenishmentQuantity,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        CamelotReplenishmentDao replenishmentDao = new CamelotReplenishmentDao();
        String result = replenishmentDao.updateReplenishment(itemCode, replenishmentQuantity, note);
        String resultColor = "";
        if (!result.equals("Camelot Replenishment Updated Successfully")) {
            result = "ΚΑΤΙ ΠΗΓΕ ΣΤΡΑΒΑ. <br>" + result;
            resultColor = "red";
        }
        model.addAttribute("result", result);
        model.addAttribute("resultColor", resultColor);
        return "camelotReplenishment/replenishmentSavingResult";

    }

    @RequestMapping(value = "camelotShelvesReplenishment", method = RequestMethod.GET)
    public String camelotShelvesReplenishment(ModelMap model) {
        CamelotReplenishmentDao camelptReplenishmentDao = new CamelotReplenishmentDao();
        LinkedHashMap<String, CamelotReplenishment> replenishments = camelptReplenishmentDao.getAllReplenishments();

        ArrayList referalAltercodes = new ArrayList(replenishments.keySet());
        StringBuilder inPartForSqlQueryByReferralAltercodes = buildStringFromArrayList(referalAltercodes);
        replenishments = camelptReplenishmentDao.addCamelotBasicData(replenishments, inPartForSqlQueryByReferralAltercodes);
        replenishments = camelptReplenishmentDao.addSailsData(replenishments, inPartForSqlQueryByReferralAltercodes);
        replenishments = camelptReplenishmentDao.addVarPcData(replenishments, inPartForSqlQueryByReferralAltercodes);
        TreeMap<String, CamelotReplenishment> sortedByPositionReplenishment = new TreeMap();
        for (Map.Entry<String, CamelotReplenishment> replenishmentsEntry : replenishments.entrySet()) {
            CamelotReplenishment replenishment = replenishmentsEntry.getValue();
            String position = replenishment.getPosition();
            sortedByPositionReplenishment.put(position, replenishment);
        }
        model.addAttribute("replenishments", replenishments);
        model.addAttribute("sortedByPositionReplenishment", sortedByPositionReplenishment);
        return "camelotReplenishment/shelvesReplenishment";

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
