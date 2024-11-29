/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4U.Pet4uDashboard;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Pet4uDashboardController {

    @RequestMapping(value = "pet4uDashboard")
    public String pet4uDashboard(HttpSession session) {

        String userName = (String) session.getAttribute("userName");

        if (userName.equals("me") || userName.equals("vasilis")) {
            return "/pet4u/pet4uDashboard";
        } else {
            return "/pet4u/authorization";
        }
    }

    @RequestMapping(value = "pet4uAuthorization", method = RequestMethod.POST)
    public String pet4uAuthorization(HttpSession session, @RequestParam(name = "password") String password) {
        if (password.equals("per4ito")
                || password.equals("PER4ITO")
                || password.equals("ΠΕΡ4ΙΤΟ")
                || password.equals("περ4ιτο")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "me");
            return "/pet4u/pet4uDashboard";
        } else if (password.equals("bilobi")) {
            session.setAttribute("user", "identified");
            session.setAttribute("userName", "vasilis");
            return "/pet4u/pet4uDashboard";
        } else {
            session.setAttribute("user", "unidentified");
            session.setAttribute("userName", "unidentified");
            System.out.println("Authorization failed. Password used: " + password);
            return "errorPage";
        }
    }

}
