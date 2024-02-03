package LocalUploads;

import Service.Basement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LocalUploadsController {

    @Autowired
    private Basement basement;

    @RequestMapping(value = "goForSalesUpload")
    public String goForSalesUpload(ModelMap model) {
        model.addAttribute("uploadTitle", "Last Six Months Upload");
        model.addAttribute("uploadTarget", "uploadSales.htm");
        return "localUploads/localUploadsDashboard";
    }

}
