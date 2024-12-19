/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4U.Pet4uLabelPrinting;

import BasicModel.Item;
import Search.SearchDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Pet4uLabelPrintingController {

    @RequestMapping(value = "labelPrintingDashboard")
    public String labelPrintingDashboard() {

        return "pet4u/labelPrinting/labelPrintingDashboard";
    }

    @RequestMapping(value = "printSmallLabelsInARow")
    public String printSmallLabelsInARow(@RequestParam(name = "altercode") String altercode) {
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(altercode);

        System.out.println("Item:" + item.getCode());
        return "pet4u/labelPrinting/labelPrintingDashboard";
    }
}
