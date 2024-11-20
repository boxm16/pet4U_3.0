/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotReplenishment;

import BasicModel.Item;
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
        if (!result.equals("New Replenishment Done Successfully")) {
            result = "ΚΑΤΙ ΠΗΓΕ ΣΤΡΑΒΑ. <br>" + result;
            resultColor = "red";
        }
        model.addAttribute("result", result);
        model.addAttribute("resultColor", resultColor);
        return "camelotReplenishment/replenishmentSavingResult";

    }
}
