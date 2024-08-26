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
        ReplenishmentDao notesDao = new ReplenishmentDao();
        Item item = notesDao.getItemForReplenishment(altercode);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("altercode", altercode);
        return "replenishment/replenishmentServant";

    }
}
