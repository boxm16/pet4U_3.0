/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Replenishment;

import BasicModel.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReplenishmentController {

    @RequestMapping(value = "goForReplenishment", method = RequestMethod.GET)
    public String getItemForNote(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        ReplenishmentDao replenishmentDao = new ReplenishmentDao();
        Item item = replenishmentDao.getItemForReplenishment(altercode);

        Replenishment replenishment = replenishmentDao.getItemReplenishment(item.getCode());

        if (replenishment == null) {
            replenishment = new Replenishment();
            replenishment.setCode(item.getCode());
            replenishment.setDescription(item.getDescription());
            replenishment.setAltercodes(item.getAltercodes());

            modelMap.addAttribute("replenishment", replenishment);
            modelMap.addAttribute("saveType", "insertReplenishment.htm");
        } else {
            modelMap.addAttribute("endoPackaging", replenishment);
            replenishment.setCode(item.getCode());
            replenishment.setDescription(item.getDescription());
            replenishment.setAltercodes(item.getAltercodes());
            modelMap.addAttribute("saveType", "editReplenishment.htm");
        }

        return "replenishment/replenishmentServant";

    }

    @RequestMapping(value = "saveReplenishment", method = RequestMethod.POST)
    public String saveItemInventory(@RequestParam(name = "altercode") String altercode,
            @RequestParam(name = "replenishment") String replenishment,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        ReplenishmentDao replenishmentDao = new ReplenishmentDao();

        return "index";

    }
}
