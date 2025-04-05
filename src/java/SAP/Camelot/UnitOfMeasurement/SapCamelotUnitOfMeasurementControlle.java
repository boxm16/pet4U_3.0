package SAP.Camelot.UnitOfMeasurement;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import SAP.SapBasicModel.SapUnitOfMeasurementGroup;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SapCamelotUnitOfMeasurementControlle {

    @RequestMapping(value = "camelotUnitOfMeasurementDashboard")
    public String camelotUnitOfMeasurementDashboard(ModelMap modelMap) {
        SapCamelotUnitOfMeasurementDao sapCamelotUnitOfMeasurementDao = new SapCamelotUnitOfMeasurementDao();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = sapCamelotUnitOfMeasurementDao.getAllUnitsOfMeasurement();
        modelMap.addAttribute("allUnitsOfMeasurement", allUnitsOfMeasurement);
        LinkedHashMap<Short, SapUnitOfMeasurementGroup> allUnitOfMeasurementGroups = sapCamelotUnitOfMeasurementDao.getAllUnitOfMeasurementGroups();
        modelMap.addAttribute("allUnitOfMeasurementGroups", allUnitOfMeasurementGroups);
        return "sap/camelot/unitOfMeasurement/camelotUnitOfMeasurementDashboard";
    }

    @RequestMapping(value = "camelotUnitOfMeasurementGroupEditServant", method = RequestMethod.GET)
    public String camelotUnitOfMeasurementGroupEditServant(@RequestParam("ugpEntry") String ugpEntry, ModelMap modelMap) {
        SapCamelotUnitOfMeasurementDao sapCamelotUnitOfMeasurementDao = new SapCamelotUnitOfMeasurementDao();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = sapCamelotUnitOfMeasurementDao.getAllUnitsOfMeasurement();
        modelMap.addAttribute("allUnitsOfMeasurement", allUnitsOfMeasurement);

        SapUnitOfMeasurementGroup uitOfMeasurementGroup = sapCamelotUnitOfMeasurementDao.getUnitOfMeasurementGroup(ugpEntry);
        modelMap.addAttribute("uitOfMeasurementGroup", uitOfMeasurementGroup);

        return "sap/camelot/unitOfMeasurement/camelotUnitOfMeasurementGroupEditServant";
    }
}
