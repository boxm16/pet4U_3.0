/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4U.Pet4uDashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Pet4uDashboardController {

    @RequestMapping(value = "pet4uDashboard")
    public String pet4uDashboard() {

        return "/pet4u/pet4uDashboard";
    }

}
