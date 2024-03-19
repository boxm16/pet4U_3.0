package CamelotItemsOfInterest;

//import Service.StaticsDispatcher;
import BasicModel.Item;
import MonthSales.Eksagoges;
import MonthSales.EksagogesControllerB;
import MonthSales.ItemEksagoges;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotItemsOfInterestController {

    @Autowired
    private CamelotItemsOfInterestDao camelotItemsOfInterestDao;
    @Autowired
    private CamelotDao camelotDao;

    @RequestMapping(value = "camelotItemsOfOurInterestDashboard")
    public String showCamelotItemsOfOurInterest(ModelMap model) {
//        ArrayList<CamelotItemOfInterest> camelotItemsOfInterest = camelotItemsOfInterestDao.getItemsOfInterest();
        //   model.addAttribute("camelotItemsReferenceFile", StaticsDispatcher.getCamelotLastUploadedFileName());
        //  model.addAttribute("pet4UItemsReferenceFile", StaticsDispatcher.getPet4uLastUploadedFileName());
        TreeMap<String, CamelotItemOfInterest> camelotItemsOfInterestFilled = new TreeMap<>();

        LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest = camelotItemsOfInterestDao.getCamelotItemsOfInterset();

        LinkedHashMap<String, Item> pet4UItems = camelotItemsOfInterestDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        //  SalesDaoX salesDao = new SalesDaoX();
        //  HashMap<String, SoldItem> sixMonthsSalesXX = salesDao.getSixMonthsSalesX();
        EksagogesControllerB eksagogesController = new EksagogesControllerB();
        LinkedHashMap<String, ItemEksagoges> lastSixMonthsSales = eksagogesController.getLastSixMonthsSales();

        for (Map.Entry<String, CamelotItemOfInterest> entrySet : camelotItemsOfInterest.entrySet()) {
            String altercode = entrySet.getKey();
            CamelotItemOfInterest camelotItemOfInterest = entrySet.getValue();
            Item pet4uItem = pet4UItems.get(altercode);
            Item camelotItem = camelotItems.get(altercode);

            if (pet4uItem == null || camelotItem == null) {
                System.out.println("ALtercode " + altercode + " is present in camelot_interest db table, but......");

                if (pet4uItem == null) {
                    System.out.println("Pet4uItem  not present in the lists from microsoft db");
                }
                if (pet4uItem == null) {
                    System.out.println("CamelotItem not present in the lists from microsoft db");
                }

            } else {

                camelotItemOfInterest.setDescription(pet4uItem.getDescription());

                Double d1 = Double.parseDouble(pet4uItem.getQuantity());

                camelotItemOfInterest.setPet4uStock(d1);

                //  Double d2 = Double.parseDouble(camelotItem.getQuantity());
                camelotItemOfInterest.setCamelotStock(Double.parseDouble(camelotItem.getQuantity()));

                camelotItemOfInterest.setPosition(pet4uItem.getPosition());
                camelotItemOfInterest.setCamelotPosition(camelotItem.getPosition());
                String position = camelotItemOfInterest.getPosition();
                if (camelotItemsOfInterestFilled.containsKey(position)) {
                    position = position + ":A";
                }

                ItemEksagoges itemEksagoges = lastSixMonthsSales.get(pet4uItem.getCode());
                if (itemEksagoges == null) {
                    //do nothing
                } else {
                    Eksagoges eksagogesForLastMonths = itemEksagoges.getEksagogesForLastMonths(6);
                    camelotItemOfInterest.setTotalSalesInPieces(eksagogesForLastMonths.getEshopSales() + eksagogesForLastMonths.getShopsSupply());
                }

                camelotItemsOfInterestFilled.put(position, camelotItemOfInterest);
            }

        }
        model.addAttribute("camelotItemsOfInterest", camelotItemsOfInterestFilled);

        return "/camelot/itemsOfInterestDashboard";
    }

    //this methid is same with above method --showCamelotItemsOfOurInterest. just returns to another page
    @RequestMapping(value = "redAndRoseAlerts")
    public String redAndRoseAlerts(ModelMap model) {
        TreeMap<String, CamelotItemOfInterest> camelotItemsOfInterestFilled = new TreeMap<>();

        LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest = camelotItemsOfInterestDao.getCamelotItemsOfInterset();

        LinkedHashMap<String, Item> pet4UItems = camelotItemsOfInterestDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        //    SalesDaoX salesDao = new SalesDaoX();
        //  HashMap<String, SoldItem> sixMonthsSales = salesDao.getSixMonthsSalesX();
        EksagogesControllerB eksagogesController = new EksagogesControllerB();
        LinkedHashMap<String, ItemEksagoges> lastSixMonthsSales = eksagogesController.getLastSixMonthsSales();

        for (Map.Entry<String, CamelotItemOfInterest> entrySet : camelotItemsOfInterest.entrySet()) {
            String altercode = entrySet.getKey();
            CamelotItemOfInterest camelotItemOfInterest = entrySet.getValue();
            Item pet4uItem = pet4UItems.get(altercode);
            Item camelotItem = camelotItems.get(altercode);

            if (pet4uItem == null || camelotItem == null) {
                System.out.println("ALtercode " + altercode + " is present in camelot_interest db table, but......");

                if (pet4uItem == null) {
                    System.out.println("Pet4uItem  not present in the lists from microsoft db");
                }
                if (pet4uItem == null) {
                    System.out.println("CamelotItem not present in the lists from microsoft db");
                }

            } else {

                camelotItemOfInterest.setDescription(pet4uItem.getDescription());

                Double d1 = Double.parseDouble(pet4uItem.getQuantity());

                camelotItemOfInterest.setPet4uStock(d1);

                //  Double d2 = Double.parseDouble(camelotItem.getQuantity());
                camelotItemOfInterest.setCamelotStock(Double.parseDouble(camelotItem.getQuantity()));

                camelotItemOfInterest.setPosition(pet4uItem.getPosition());
                camelotItemOfInterest.setCamelotPosition(camelotItem.getPosition());
                String position = camelotItemOfInterest.getPosition();
                if (camelotItemsOfInterestFilled.containsKey(position)) {
                    position = position + ":A";
                }

                ItemEksagoges itemEksagoges = lastSixMonthsSales.get(pet4uItem.getCode());
                if (itemEksagoges == null) {
                    //do nothing
                } else {
                    Eksagoges eksagogesForLastMonths = itemEksagoges.getEksagogesForLastMonths(6);
                    camelotItemOfInterest.setTotalSalesInPieces(eksagogesForLastMonths.getEshopSales() + eksagogesForLastMonths.getShopsSupply());
                }

                camelotItemsOfInterestFilled.put(position, camelotItemOfInterest);
            }

        }
        model.addAttribute("camelotItemsOfInterest", camelotItemsOfInterestFilled);

        return "/camelot/alerts";
    }

    //this methid is same with above 2 methods --showCamelotItemsOfOurInterest amd redAndRoseAlerts. just returns to another page
    @RequestMapping(value = "orderAlert")
    public String orderAlert(ModelMap model) {
        TreeMap<String, CamelotItemOfInterest> camelotItemsOfInterestFilled = new TreeMap<>();

        LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest = camelotItemsOfInterestDao.getCamelotItemsOfInterset();

        LinkedHashMap<String, Item> pet4UItems = camelotItemsOfInterestDao.getPet4UItemsRowByRow();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        //    SalesDaoX salesDao = new SalesDaoX();
        //  HashMap<String, SoldItem> sixMonthsSales = salesDao.getSixMonthsSalesX();
        EksagogesControllerB eksagogesController = new EksagogesControllerB();
        LinkedHashMap<String, ItemEksagoges> lastSixMonthsSales = eksagogesController.getLastSixMonthsSales();

        for (Map.Entry<String, CamelotItemOfInterest> entrySet : camelotItemsOfInterest.entrySet()) {
            String altercode = entrySet.getKey();
            CamelotItemOfInterest camelotItemOfInterest = entrySet.getValue();
            Item pet4uItem = pet4UItems.get(altercode);
            Item camelotItem = camelotItems.get(altercode);

            if (pet4uItem == null || camelotItem == null) {
                System.out.println("ALtercode " + altercode + " is present in camelot_interest db table, but......");

                if (pet4uItem == null) {
                    System.out.println("Pet4uItem  not present in the lists from microsoft db");
                }
                if (pet4uItem == null) {
                    System.out.println("CamelotItem not present in the lists from microsoft db");
                }

            } else {

                camelotItemOfInterest.setDescription(pet4uItem.getDescription());

                Double d1 = Double.parseDouble(pet4uItem.getQuantity());

                camelotItemOfInterest.setPet4uStock(d1);

                //  Double d2 = Double.parseDouble(camelotItem.getQuantity());
                camelotItemOfInterest.setCamelotStock(Double.parseDouble(camelotItem.getQuantity()));

                camelotItemOfInterest.setPosition(pet4uItem.getPosition());
                camelotItemOfInterest.setCamelotPosition(camelotItem.getPosition());
                String position = camelotItemOfInterest.getPosition();
                if (camelotItemsOfInterestFilled.containsKey(position)) {
                    position = position + ":A";
                }

                ItemEksagoges itemEksagoges = lastSixMonthsSales.get(pet4uItem.getCode());
                if (itemEksagoges == null) {
                    //do nothing
                } else {
                    Eksagoges eksagogesForLastMonths = itemEksagoges.getEksagogesForLastMonths(6);
                    camelotItemOfInterest.setTotalSalesInPieces(eksagogesForLastMonths.getEshopSales() + eksagogesForLastMonths.getShopsSupply());
                }

                camelotItemsOfInterestFilled.put(position, camelotItemOfInterest);
            }

        }
        model.addAttribute("camelotItemsOfInterest", camelotItemsOfInterestFilled);

        return "/camelot/orderAlert";
    }

//--------------------------------------------------------------------
    @RequestMapping(value = "goForAddingItemOfInterest")
    public String goForAddingItemOfInterest(ModelMap model) {
        CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
        camelotItemOfInterest.setWeightCoefficient(1);
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
            @RequestParam(name = "camelotMinimalStock") String camelotMinimalStock,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
        camelotItemOfInterest.setCode(code);
        camelotItemOfInterest.setOwner(owner);
        camelotItemOfInterest.setMinimalStock(Integer.parseInt(minimalStock));
        camelotItemOfInterest.setOrderUnit(orderUnit);
        camelotItemOfInterest.setWeightCoefficient(Integer.parseInt(weightCoefficient));
        camelotItemOfInterest.setOrderQuantity(Integer.parseInt(orderQuantity));
        camelotItemOfInterest.setCamelotMinimalStock(Integer.parseInt(camelotMinimalStock));
        camelotItemOfInterest.setNote(note);
        if (minimalStock.isEmpty() || orderQuantity.isEmpty() || camelotMinimalStock.isEmpty() || weightCoefficient.isEmpty()) {
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

//        ArrayList<String> camelotAltercodes = camelotItemsOfInterestDao.getCamelotAltercodes();
        //      ArrayList<String> pet4UAltercodes = camelotItemsOfInterestDao.getPet4UAltercodes();
        ArrayList<String> camelotAltercodes = camelotItemsOfInterestDao.getCamelotAltercodesFromMicrosoftDB();
        ArrayList<String> pet4UAltercodes = camelotItemsOfInterestDao.getPet4UAltercodesFromMicrosoftDB();

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
            @RequestParam(name = "camelotMinimalStock") String camelotMinimalStock,
            @RequestParam(name = "note") String note,
            ModelMap model) {
        CamelotItemOfInterest camelotItemOfInterest = new CamelotItemOfInterest();
        camelotItemOfInterest.setCode(code);
        camelotItemOfInterest.setOwner(owner);
        camelotItemOfInterest.setMinimalStock(Integer.parseInt(minimalStock));
        camelotItemOfInterest.setWeightCoefficient(Integer.parseInt(weightCoefficient));
        camelotItemOfInterest.setOrderUnit(orderUnit);
        camelotItemOfInterest.setOrderQuantity(Integer.parseInt(orderQuantity));
        camelotItemOfInterest.setCamelotMinimalStock(Integer.parseInt(camelotMinimalStock));
        camelotItemOfInterest.setNote(note);
        if (minimalStock.isEmpty() || orderQuantity.isEmpty() || camelotMinimalStock.isEmpty()) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "SOMETHING IS MISSING.");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/editItem";
        }
        if (Integer.parseInt(weightCoefficient) == 0 || Integer.parseInt(weightCoefficient) < 0) {
            model.addAttribute("resultColor", "rose");
            model.addAttribute("result", "Bad Coefficient.");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/editItem";
        }
        ArrayList<String> camelotAltercodes = camelotItemsOfInterestDao.getCamelotAltercodesFromMicrosoftDB();
        if (!camelotAltercodes.contains(code)) {
            model.addAttribute("resultColor", "red");
            model.addAttribute("result", "NO SUCH CODE IN CAMELOT");
            model.addAttribute("itemOfInterest", camelotItemOfInterest);
            return "/camelot/editItem";
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

    @RequestMapping(value = "makeCamelotItemsInterestDayRestSnapshot")
    public String makeCamelotItemsInterestDayRestSnapshot() {
        addSnapshot();
        return "redirect:/camelotItemsOfOurInterestDashboard.htm";
    }

    public void addSnapshot() {
        /*  CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();

        LinkedHashMap<String, CamelotItemOfInterest> camelotItemsOfInterest = camelotItemsOfInterestDao.getCamelotItemsOfInterset();
        CamelotItemsOfInterestDao camelotDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItems = camelotDao.getCamelotItemsRowByRow();

        for (Map.Entry<String, CamelotItemOfInterest> entrySet : camelotItemsOfInterest.entrySet()) {
            String code = entrySet.getKey();
            CamelotItemOfInterest camelotItemOfInterest = entrySet.getValue();

            Item camelotItem = camelotItems.get(code);
            if (camelotItem == null) {
                System.out.println("pet4uItem or camelotItem not present in the lists from microsoft db");
            } else {
                camelotItemOfInterest.setQuantity(camelotItem.getQuantity());
            }
        }
         */
        LinkedHashMap<String, CamelotItemOfInterest> allCamelotItems = camelotItemsOfInterestDao.getAllCamelotItemsAsItemsOfInterest();

        LocalDate nowDate = LocalDate.now();
        String snapshotInsertionResult = camelotItemsOfInterestDao.insertDayRestSnapshot(nowDate, allCamelotItems);

        System.out.println("Insertion for " + nowDate + ".Result:" + snapshotInsertionResult);
    }

    @RequestMapping(value = "itemSnapshots")
    public String itemSnapshots(@RequestParam(name = "code") String code, ModelMap model) {
        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        ArrayList<ItemSnapshot> itemSnapshots = camelotItemsOfInterestDao.getItemSnapshots(code);
        model.addAttribute("itemSnapshots", itemSnapshots);
        model.addAttribute("code", code);
        return "/camelot/itemSnapshots";
    }
}
