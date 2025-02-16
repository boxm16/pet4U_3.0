/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camelot.CamelotDelivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotDeliveryController {

    @RequestMapping(value = "camelotDeliveryInvoiceCreation")
    public String camelotDelivery() {

        return "/camelot/camelotDelivery/camelotDeliveryInvoiceCreationResult";
    }
}
