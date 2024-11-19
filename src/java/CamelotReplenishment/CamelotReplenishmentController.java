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
        } else {
            modelMap.addAttribute("replenishment", replenishment);
            replenishment.setCode(item.getCode());

            replenishment.setDescription(item.getDescription());
            replenishment.setAltercodes(item.getAltercodes());
            replenishment.setQuantity(item.getQuantity());
            replenishment.setPosition(item.getPosition());
            modelMap.addAttribute("replenishment", replenishment);
            modelMap.addAttribute("saveType", "editCamelotReplenishment.htm");
        }

        return "camelotReplenishment/replenishmentServant";

    }
}
