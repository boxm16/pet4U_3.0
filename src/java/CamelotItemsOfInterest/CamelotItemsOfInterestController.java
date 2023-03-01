package CamelotItemsOfInterest;

//import Service.StaticsDispatcher;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotItemsOfInterestController {

    @Autowired
    private CamelotItemsOfInterestDao camelotItemsOfInterestDao;

    @RequestMapping(value = "camelotItemsOfOurInterestDashboard")
    public String showCamelotItemsOfOurInterest(ModelMap model) {
        ArrayList<CamelotItemOfInterest> camelotItemsOfInterest = camelotItemsOfInterestDao.getItemsOfInterest();

        //   model.addAttribute("camelotItemsReferenceFile", StaticsDispatcher.getCamelotLastUploadedFileName());
        //  model.addAttribute("pet4UItemsReferenceFile", StaticsDispatcher.getPet4uLastUploadedFileName());
        model.addAttribute("camelotItemsOfInterest", camelotItemsOfInterest);
        return "/camelot/itemsOfInterestDashboard";
    }

    @RequestMapping(value = "goForAddingItemOfInterest")
    public String goForAddingItemOfInterest(ModelMap model) {
        CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
        model.addAttribute("camelotItemsOfInterest", camelotItemOfInterest);
        return "/camelot/addItem";
    }

    @RequestMapping(value = "addItemOfInterest")
    public String addItemOfInterest(@RequestParam(name = "code") String code,
            @RequestParam(name = "owner") String owner,
            @RequestParam(name = "minimalStock") String minimalStock,
            @RequestParam(name = "weightCoefficient") String weightCoefficient,
            @RequestParam(name = "orderUnit") String orderUnit,
            @RequestParam(name = "orderQuantity") String orderQuantity,
            @RequestParam(name = "itemsQuantity") String itemsQuantity,
            ModelMap model) {
        CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
        camelotItemOfInterest.setCode(code);
        camelotItemOfInterest.setOwner(owner);
        camelotItemOfInterest.setMinimalStock(Integer.parseInt(minimalStock));
        camelotItemOfInterest.setOrderUnit(orderUnit);
        camelotItemOfInterest.setWeightCoefficient(Integer.parseInt(weightCoefficient));
        camelotItemOfInterest.setOrderQuantity(Integer.parseInt(orderQuantity));
        camelotItemOfInterest.setOrderTotalItems(Integer.parseInt(itemsQuantity));
        if (minimalStock.isEmpty() || orderQuantity.isEmpty() || itemsQuantity.isEmpty() || weightCoefficient.isEmpty()) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "SOMETHING IS MISSING.");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/addItem";
        }
        if (Integer.parseInt(weightCoefficient) == 0 || Integer.parseInt(weightCoefficient) < 0) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "Bad Coefficient.");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/addItem";
        }

        ArrayList<String> camelotAltercodes = camelotItemsOfInterestDao.getCamelotAltercodes();
        ArrayList<String> pet4UAltercodes = camelotItemsOfInterestDao.getPet4UAltercodes();

        if (!camelotAltercodes.contains(code)) {
            model.addAttribute("resultColor", "red");
            model.addAttribute("result", "NO SUCH CODE IN CAMELOT. Try better barcode, it is more secure");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/addItem";
        }
        if (!pet4UAltercodes.contains(code)) {
            model.addAttribute("resultColor", "red");
            model.addAttribute("result", "NO SUCH CODE IN PET4U. Try better barcode, it is more secure");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/addItem";
        }

        String result = camelotItemsOfInterestDao.addItem(camelotItemOfInterest);
        model.addAttribute("resultColor", "green");
        model.addAttribute("result", result);
        return "/camelot/addItem";
    }

    @RequestMapping(value = "goForEditingCamelotItemOfInterest")
    public String goForEditingCamelotItemOfInterest(@RequestParam(name = "code") String code, ModelMap model) {
        CamelotItemOfInterest itemOfInterest = camelotItemsOfInterestDao.getItemOfInterest(code);
        model.addAttribute("itemOfInterest", itemOfInterest);
        return "/camelot/editItem";
    }

    @RequestMapping(value = "editItemOfInterest")
    public String editItemOfInterest(@RequestParam(name = "code") String code,
            @RequestParam(name = "owner") String owner,
            @RequestParam(name = "minimalStock") String minimalStock,
            @RequestParam(name = "weightCoefficient") String weightCoefficient,
            @RequestParam(name = "orderUnit") String orderUnit,
            @RequestParam(name = "orderQuantity") String orderQuantity,
            @RequestParam(name = "itemsQuantity") String itemsQuantity,
            ModelMap model) {
        CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
        camelotItemOfInterest.setCode(code);
        camelotItemOfInterest.setOwner(owner);
        camelotItemOfInterest.setMinimalStock(Integer.parseInt(minimalStock));
        camelotItemOfInterest.setWeightCoefficient(Integer.parseInt(weightCoefficient));
        camelotItemOfInterest.setOrderUnit(orderUnit);
        camelotItemOfInterest.setOrderQuantity(Integer.parseInt(orderQuantity));
        camelotItemOfInterest.setOrderTotalItems(Integer.parseInt(itemsQuantity));

        if (minimalStock.isEmpty() || orderQuantity.isEmpty() || itemsQuantity.isEmpty()) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "SOMETHING IS MISSING.");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/editItem";
        }
        if (Integer.parseInt(weightCoefficient) == 0 || Integer.parseInt(weightCoefficient) < 0) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "Bad Coefficient.");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/addItem";
        }
        ArrayList<String> camelotAltercodes = camelotItemsOfInterestDao.getCamelotAltercodes();
        if (!camelotAltercodes.contains(code)) {
            model.addAttribute("resultColor", "red");
            model.addAttribute("result", "NO SUCH CODE IN CAMELOT");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/edit";
        }

        String result = camelotItemsOfInterestDao.editItem(camelotItemOfInterest);
        model.addAttribute("resultColor", "green");
        model.addAttribute("result", result);
        model.addAttribute("itemOfInterest", camelotItemOfInterest);
        return "/camelot/editItem";
    }

    @RequestMapping(value = "deleteItemOfInterest")
    public String deleteItemOfInterest(@RequestParam(name = "code") String code, ModelMap model) {
        String result = camelotItemsOfInterestDao.deleteItemOfInterest(code);
        if (result.equals("DONE")) {
            model.addAttribute("resultColor", "green");
            model.addAttribute("result", "Item Deleted Successfuly");
        } else {
            model.addAttribute("resultColor", "red");
            model.addAttribute("result", "Something Went Wrong:" + result);
        }

        return "/camelot/editItem";
    }
}
