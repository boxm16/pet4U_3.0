package SAP.Camelot.CamelotService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotServiceController {

    @RequestMapping(value = "camelotUnitOfMeasurementGroupCreationServant")
    public String camelotUnitOfMeasurementGroupCreationServant(ModelMap modelMap) {

        return "sap/camelot/service/camelotUnitOfMeasurementGroupCreationServant";
    }
}
