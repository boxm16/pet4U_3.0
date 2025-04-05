package SAP.Camelot.UnitOfMeasurement;

import SAP.SapBasicModel.SapUnitOfMeasurement;
import java.util.LinkedHashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SapCamelotUnitOfMeasurementControlle {

    @RequestMapping(value = "camelotUnitOfMeasurementDashboard")
    public String camelotUnitOfMeasurementDashboard(ModelMap modelMap) {
        SapCamelotUnitOfMeasurementDao sapCamelotUnitOfMeasurementDao = new SapCamelotUnitOfMeasurementDao();
        LinkedHashMap<Short, SapUnitOfMeasurement> allUnitsOfMeasurement = sapCamelotUnitOfMeasurementDao.getAllUnitsOfMeasurement();
       
        
        return "sap/camelot/unitOfMeasurement/camelotUnitOfMeasurementDashboard";
    }
}
