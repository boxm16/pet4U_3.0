package SAP;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SapController {

    @RequestMapping(value = "sapDashboard")
    public String pet4uNegativeStock() {

        return "/sap/sapDashboard";
    }
}
