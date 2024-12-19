/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4U.Pet4uLabelPrinting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Pet4uLabelPrintingController {

    @RequestMapping(value = "labelPrintDashboard")
    public String labelPrintDashboard() {

        return "labelPrinting/labelPrintingDashboard";
    }
}
