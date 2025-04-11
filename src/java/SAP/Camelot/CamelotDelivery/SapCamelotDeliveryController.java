/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAP.Camelot.CamelotDelivery;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SapCamelotDeliveryController {

    @RequestMapping(value = "camelotItemsDashboard")
    public String camelotItemsDashboard(ModelMap modelMap) {

        return "sap/camelot/delivery/sapCamelotDeliveryDashboard";
    }
}
