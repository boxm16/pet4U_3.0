/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemCodeChanging;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemCodeChangingController {

    @RequestMapping(value = "itemCodeChangingDashboard")
    public String itemCodeChangingDashboard() {

        return "/itemCodeChanging/itemCodeChangingDashboard";
    }

    @RequestMapping(value = "changeItemCode")
    public String changeItemCode(@RequestParam(name = "oldItemCode") String oldItemCode, @RequestParam(name = "newItemCode") String newItemCode, ModelMap modelMap) {
        String result = "";
        ItemCodeChangingDao itemCodeChangingDao = new ItemCodeChangingDao();
        String result1 = itemCodeChangingDao.changeItemCodeIn("camelot_day_rest", oldItemCode, newItemCode);
        result = result1 + "<br>";
        modelMap.addAttribute("result", result);
        return "/itemCodeChanging/itemCodeChangingDashboard";
    }
}
