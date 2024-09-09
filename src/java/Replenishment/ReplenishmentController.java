/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Replenishment;

import BasicModel.Item;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReplenishmentController {

    @RequestMapping(value = "goForReplenishment", method = RequestMethod.GET)
    public String goForReplenishment(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        Item item = replenishmentDao.getItemForReplenishment(altercode);

        Replenishment replenishment = replenishmentDao.getItemReplenishment(item.getCode());

        if (replenishment == null) {
            replenishment = new Replenishment();
            replenishment.setCode(item.getCode());
            replenishment.setReplenishmentQuantity(0);

            replenishment.setCode(item.getCode());
            replenishment.setDescription(item.getDescription());
            replenishment.setAltercodes(item.getAltercodes());
            replenishment.setQuantity(item.getQuantity());
            replenishment.setPosition(item.getPosition());
            modelMap.addAttribute("replenishment", replenishment);
            modelMap.addAttribute("saveType", "insertReplenishment.htm");
        } else {
            modelMap.addAttribute("replenishment", replenishment);
            replenishment.setCode(item.getCode());

            replenishment.setDescription(item.getDescription());
            replenishment.setAltercodes(item.getAltercodes());
            replenishment.setQuantity(item.getQuantity());
            replenishment.setPosition(item.getPosition());
            modelMap.addAttribute("replenishment", replenishment);
            modelMap.addAttribute("saveType", "editReplenishment.htm");
        }

        return "replenishment/replenishmentServant";

    }

    @RequestMapping(value = "insertReplenishment", method = RequestMethod.POST)
    public String saveItemInventory(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "replenishmentQuantity") String replenishmentQuantity,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        String result = replenishmentDao.insertReplenishment(itemCode, replenishmentQuantity, note);
        String resultColor = "";
        if (!result.equals("New Replenishment Done Successfully")) {
            result = "ΚΑΤΙ ΠΗΓΕ ΣΤΡΑΒΑ. <br>" + result;
            resultColor = "red";
        }
        model.addAttribute("result", result);
        model.addAttribute("resultColor", resultColor);
        return "replenishment/replenishmentSavingResult";

    }

    @RequestMapping(value = "editReplenishment", method = RequestMethod.POST)
    public String updateReplenishment(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "replenishmentQuantity") String replenishmentQuantity,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        String result = replenishmentDao.updateReplenishment(itemCode, replenishmentQuantity, note);
        String resultColor = "";
        if (!result.equals("Replenishment Updated Successfully")) {
            result = "ΚΑΤΙ ΠΗΓΕ ΣΤΡΑΒΑ. <br>" + result;
            resultColor = "red";
        }
        model.addAttribute("result", result);
        model.addAttribute("resultColor", resultColor);
        return "replenishment/replenishmentSavingResult";

    }

    @RequestMapping(value = "shelvesReplenishment", method = RequestMethod.GET)
    public String shelvesReplenishmentSV(ModelMap model) {
        LinkedHashMap<String, Replenishment> replenishments = getReplenishments();
        model.addAttribute("replenishments", replenishments);
        return "replenishment/shelvesReplenishment";

    }

    @RequestMapping(value = "shelvesReplenishmentDashboard", method = RequestMethod.GET)
    public String shelvesReplenishmentDashboard(ModelMap model) {

        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        LinkedHashMap<String, Replenishment> replenishments = replenishmentDao.getAllReplenishments();
        ArrayList referalAltercodes = new ArrayList(replenishments.keySet());
        StringBuilder inPartForSqlQueryByReferralAltercodes = buildStringFromArrayList(referalAltercodes);
        replenishments = replenishmentDao.addPet4uBasicData(replenishments, inPartForSqlQueryByReferralAltercodes);
        replenishments = replenishmentDao.addSailsData(replenishments, inPartForSqlQueryByReferralAltercodes);

        model.addAttribute("replenishments", replenishments);
        return "replenishment/shelvesReplenishmentDashboard";

    }

    private LinkedHashMap<String, Replenishment> getReplenishments() {
        ReplenishmentDao replenishmentDao = new ReplenishmentDao();

        LinkedHashMap<String, Replenishment> replenishments = replenishmentDao.getAllReplenishments();

        ArrayList referalAltercodes = new ArrayList(replenishments.keySet());
        StringBuilder inPartForSqlQueryByReferralAltercodes = buildStringFromArrayList(referalAltercodes);
        replenishments = replenishmentDao.addPet4uBasicData(replenishments, inPartForSqlQueryByReferralAltercodes);

        return replenishments;

    }

    @RequestMapping(value = "goForEditingReplenishment", method = RequestMethod.GET)
    public String goForEditingReplenishment(@RequestParam(name = "itemCode") String itemCode, ModelMap model) {

        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        Replenishment replenishment = replenishmentDao.getItemReplenishment(itemCode);
        model.addAttribute("replenishment", replenishment);
        return "replenishment/editReplenishment";

    }

    @RequestMapping(value = "editReplenishmentMinimalStock", method = RequestMethod.POST)
    public String editReplenishmentMinimalStock(@RequestParam(name = "itemCode") String itemCode, @RequestParam(name = "minimalShelfStock") String minimalShelfStock, ModelMap model) {

        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        String result = replenishmentDao.updateReplenishment(itemCode, minimalShelfStock);

        if (result.equals("Replenishment Updated Successfully")) {
            model.addAttribute("resultColor", "green");
        } else {
            model.addAttribute("resultColor", "red");
        }
        model.addAttribute("result", result);
        return "replenishment/editReplenishment";

    }

    @RequestMapping(value = "deleteReplenishment", method = RequestMethod.GET)
    public String deleteReplenishment(@RequestParam(name = "itemCode") String itemCode, ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");

        if (userName == null) {
            model.addAttribute("message", "You are not authorized for this action. Don`t do this again.");
            return "errorPage";
        }
        if (!userName.equals("me")) {
            model.addAttribute("message", "You are not authorized for this action. Don`t do this again.");
            return "errorPage";
        }

        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        String result = replenishmentDao.deleteReplenishment(itemCode);

        if (result.equals("Replenishment Deleted Successfully")) {
            model.addAttribute("resultColor", "green");
        } else {
            model.addAttribute("resultColor", "red");
        }
        model.addAttribute("result", result);
        return "replenishment/editReplenishment";

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
