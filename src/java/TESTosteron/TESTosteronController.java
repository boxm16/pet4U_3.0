package TESTosteron;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TESTosteronController {

    @RequestMapping(value = "testosteronDashboard")
    public String testosteronDashboard() {

        return "testosteron/testosteronDashboard";
    }
}
