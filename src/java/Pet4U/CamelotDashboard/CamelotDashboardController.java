/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4U.CamelotDashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotDashboardController {

    @RequestMapping(value = "camelotDashboard")
    public String camelotDashboard() {
        System.out.println("dddd");
        return "/camelot/camelotDashboard";
    }

}
