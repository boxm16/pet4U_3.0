/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeliveryController_V_3_1 {

    @RequestMapping(value = "deliveryDashboardTesting")
    public String deliveryDashboardTesting(ModelMap modelMap) {

        return "testosteron/testResult";
    }

}
