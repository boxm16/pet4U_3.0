package SAP.Camelot.CamelotService;

import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CamelotServiceController {

    @RequestMapping(value = "camelotUnitOfMeasurementGroupCreationServant")
    public String camelotUnitOfMeasurementGroupCreationServant(ModelMap modelMap) {
        SapUnitOfMeasurementGroup uomGroup = new SapUnitOfMeasurementGroup();
        modelMap.addAttribute("uomGroup", uomGroup);
        return "sap/camelot/service/camelotUnitOfMeasurementGroupCreationServant";
    }
}
