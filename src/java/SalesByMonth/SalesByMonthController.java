/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesByMonth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SalesByMonthController {

    @RequestMapping(value = "goForSalesByMonthUpload")
    public String goForSalesByMonthUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Upload Sales Of The Month");
        model.addAttribute("uploadTarget", "uploadSalesOfMonth.htm");
        return "localUploads/localUploadsDashboard";
    }
}
