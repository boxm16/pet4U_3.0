/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotSales;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Michail Sitmalidis
 */
public class CamelotSalesController {

    @RequestMapping(value = "goForCamelotMonthSalesUpload")
    public String goForCamelotMonthSalesUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Camelot Last Six Months Upload");
        model.addAttribute("uploadTarget", "camelotMonthSalesUpload.htm");
        return "localUploads/localUploadsDashboard";
    }

    @RequestMapping(value = "camelotMonthSalesUpload")
    public String camelotMonthSalesUpload(ModelMap model) {

        return "index";
    }

}
