package CamelotSearch;

import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import Inventory.InventoryItem;
import Notes.NotesDao;
import Pet4uItems.Pet4uItemsController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CamelotSearchController {

    @Autowired
    private CamelotSearchDao camelotSearchDao;

    @RequestMapping(value = "camelotSearchDashboard")
    public String searchForItem() {

        return "camelotSearch/camelotSearchDashboard";
    }

    @RequestMapping(value = "findCamelotItemByAltercode")
    public String findCamelotItemByAltercode(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        Item item = camelotSearchDao.getItemByAltercode(altercode);
        modelMap.addAttribute("target", altercode);
        modelMap.addAttribute("item", item);
        if (item == null) {
            return "camelotSearch/searchErrorPage";
        }
        return "camelotSearch/singleItemSearchResult";
    }

    @RequestMapping(value = "findCamelotItemByItemId")
    public String findCamelotItemById(@RequestParam(name = "itemId") String itemId, ModelMap modelMap) {
        Item item = camelotSearchDao.getItemByItemId(itemId);
        modelMap.addAttribute("target", itemId);
        modelMap.addAttribute("item", item);
        if (item == null) {
            return "camelotSearch/searchErrorPage";
        }
        return "camelotSearch/singleItemSearchResult";
    }

    @RequestMapping(value = "findCamelotItemsByAltercodeMask")
    public String findCamelotItemsByAltercodeMask(@RequestParam(name = "altercodeMask") String altercodeMask, ModelMap modelMap) {
        LinkedHashMap<String, Item> items = camelotSearchDao.getItemsByAltercodeMask(altercodeMask);
        modelMap.addAttribute("target", altercodeMask);
        modelMap.addAttribute("items", items);
        return "camelotSearch/multipleItemsSearchResult";
    }

    @RequestMapping(value = "findCamelotItemsByADescriptionMask")
    public String findItemsByADescriptionMask(@RequestParam(name = "descriptionMask") String descriptionMask, ModelMap modelMap) {
        LinkedHashMap<String, Item> items = camelotSearchDao.getItemsByDescriptionMask(descriptionMask);

        modelMap.addAttribute("target", descriptionMask);
        modelMap.addAttribute("items", items);
        return "camelotSearch/multipleItemsSearchResult";
    }

    @RequestMapping(value = "getCamelotItemForNote", method = RequestMethod.GET)
    public String getCamelotItemForNote(@RequestParam(name = "altercode") String altercode, ModelMap modelMap) {
        NotesDao notesDao = new NotesDao();
        Item item = notesDao.getCamelotItemForNote(altercode);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("altercode", altercode);
        return "camelotSearch/camelotNoteServant";
    }

    @RequestMapping(value = "saveCamelotNote", method = RequestMethod.POST)
    public String saveCamelotNote(@RequestParam(name = "altercode") String altercode,
            @RequestParam(name = "note") String note,
            ModelMap model) {

        NotesDao notesDao = new NotesDao();
        String result = notesDao.saveCamelotNote(altercode, note);
        String resultColor = "";
        if (!result.equals("New Camelot Note Added Successfully")) {
            result = "ΚΑΤΙ ΠΗΓΕ ΣΤΡΑΒΑ. ΔΕΙΞΕ ΤΟ ΣΤΟΝ ΜΗΧΑΛΗ <br>" + result;
            resultColor = "red";
        }
        model.addAttribute("result", result);
        model.addAttribute("resultColor", resultColor);

        return "camelotSearch/camelotNoteSavingResult";

    }

    @RequestMapping(value = "camelotNotesDisplay")
    public String camelotNotesDisplay(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getAllCamelotNotes();

        LinkedHashMap<String, LinkedHashMap<Integer, String>> allStockPositions = notesDao.getAllStockPositions();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        TreeMap<String, InventoryItem> sortedNotes = new TreeMap();
        int x = 0;
        for (InventoryItem inventoryItem : notes) {
            //     System.out.println("ITETM:" + inventoryItem.getCode());
            String altercode = inventoryItem.getCode();
            Item camelotItem = camelotItems.get(altercode);
            if (camelotItem == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  not present in the lists from microsoft db");
                camelotItem.setCode(altercode);

                inventoryItem.setCode(altercode);
                inventoryItem.setDescription("No Data Available");
                inventoryItem.setPosition("No Data Available");
                inventoryItem.setQuantity("No Data Available");
            } else {
                inventoryItem.setCode(camelotItem.getCode());
                inventoryItem.setDescription(camelotItem.getDescription());
                inventoryItem.setPosition(camelotItem.getPosition());
                inventoryItem.setQuantity(camelotItem.getQuantity());
            }
            //---------
            LinkedHashMap<Integer, String> itemStockPositions = allStockPositions.get(camelotItem.getCode());

            if (itemStockPositions == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  does note have stock positions");
                itemStockPositions = new LinkedHashMap<>();
                itemStockPositions.put(x, "ΔΕΝ ΥΠΑΡΧΟΥΝ ΘΕΣΕΙΣ ΣΤΟΚ <br> ΓΙΑ ΑΥΤΟΝ ΤΟΝ ΚΩΔΙΚΟ");

            }
            inventoryItem.setStockPositions(itemStockPositions);

            Entry<Integer, String> mapEntry = itemStockPositions.entrySet().iterator().next();
            String firstStockPosition = mapEntry.getValue();
            System.out.println("First Stock Position: " + firstStockPosition);
            if (sortedNotes.containsKey(firstStockPosition)) {
                System.out.println("Sorted Notes contain position : " + firstStockPosition);
                firstStockPosition = firstStockPosition + ":" + x;
                System.out.println("Creating fake position : " + firstStockPosition);
                sortedNotes.put(firstStockPosition, inventoryItem);
            } else {
                sortedNotes.put(firstStockPosition, inventoryItem);
            }
            x++;
        }

        ArrayList<InventoryItem> sortedNotesArrayList = new ArrayList<InventoryItem>(sortedNotes.values());
        model.addAttribute("notes", sortedNotesArrayList);
        return "camelotSearch/camelotNotesDisplay";
    }

    //--THIS IS COPY OF ABOVE METHOD< BUT FOR PRINTING-------------------------
    @RequestMapping(value = "printCamelotNotes")
    public String printCamelotNotes(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getAllCamelotNotes();

        LinkedHashMap<String, LinkedHashMap<Integer, String>> allStockPositions = notesDao.getAllStockPositions();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        TreeMap<String, InventoryItem> sortedNotes = new TreeMap();
        int x = 0;
        for (InventoryItem inventoryItem : notes) {
            //     System.out.println("ITETM:" + inventoryItem.getCode());
            String altercode = inventoryItem.getCode();
            Item camelotItem = camelotItems.get(altercode);
            if (camelotItem == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  not present in the lists from microsoft db");
                camelotItem.setCode(altercode);

                inventoryItem.setCode(altercode);
                inventoryItem.setDescription("No Data Available");
                inventoryItem.setPosition("No Data Available");
                inventoryItem.setQuantity("No Data Available");
            } else {
                inventoryItem.setCode(camelotItem.getCode());
                inventoryItem.setDescription(camelotItem.getDescription());
                inventoryItem.setPosition(camelotItem.getPosition());
                inventoryItem.setQuantity(camelotItem.getQuantity());
            }
            //---------
            LinkedHashMap<Integer, String> itemStockPositions = allStockPositions.get(camelotItem.getCode());

            if (itemStockPositions == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  does note have stock positions");
                itemStockPositions = new LinkedHashMap<>();
                itemStockPositions.put(x, "ΔΕΝ ΥΠΑΡΧΟΥΝ ΘΕΣΕΙΣ ΣΤΟΚ <br> ΓΙΑ ΑΥΤΟΝ ΤΟΝ ΚΩΔΙΚΟ");

            }
            inventoryItem.setStockPositions(itemStockPositions);

            Entry<Integer, String> mapEntry = itemStockPositions.entrySet().iterator().next();
            String firstStockPosition = mapEntry.getValue();
            System.out.println("First Stock Position: " + firstStockPosition);
            if (sortedNotes.containsKey(firstStockPosition)) {
                System.out.println("Sorted Notes contain position : " + firstStockPosition);
                firstStockPosition = firstStockPosition + ":" + x;
                System.out.println("Creating fake position : " + firstStockPosition);
                sortedNotes.put(firstStockPosition, inventoryItem);
            } else {
                sortedNotes.put(firstStockPosition, inventoryItem);
            }
            x++;
        }

        ArrayList<InventoryItem> sortedNotesArrayList = new ArrayList<InventoryItem>(sortedNotes.values());
        model.addAttribute("notes", sortedNotesArrayList);

        String printName = "HP LaserJet Pro MFP M127-M128 PCLmS";
        // this is the printer we want
        //String printName = "HPF907E5 (HP LaserJet Pro M428f-M429f)";
        CamelotNotesPrinter camelotNotesPinter = new CamelotNotesPrinter();
        //---------------
        camelotNotesPinter.setSortedNotesArrayList(sortedNotesArrayList);
        camelotNotesPinter.printSomething(printName);

        return "camelotSearch/camelotNotesCardMode";
    }

    //--------------------------------------------------------------------------
    @RequestMapping(value = "camelotNotesCardMode")
    public String camelotNotesCardMode(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getAllCamelotNotes();
        LinkedHashMap<String, LinkedHashMap<Integer, String>> allStockPositions = notesDao.getAllStockPositions();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItems = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (InventoryItem inventoryItem : notes) {
            //     System.out.println("ITETM:" + inventoryItem.getCode());
            String altercode = inventoryItem.getCode();

            Item camelotItem = camelotItems.get(altercode);

            if (camelotItem == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  not present in the lists from microsoft db");
            } else {
                inventoryItem.setCode(camelotItem.getCode());
                inventoryItem.setDescription(camelotItem.getDescription());
                inventoryItem.setPosition(camelotItem.getPosition());
                inventoryItem.setQuantity(camelotItem.getQuantity());

            }
            //---------
            LinkedHashMap<Integer, String> itemStockPositions = allStockPositions.get(camelotItem.getCode());

            if (itemStockPositions == null) {
                System.out.println("CamelotItem with altercode " + altercode + "  does note have stock positions");
            } else {
                inventoryItem.setStockPositions(itemStockPositions);
            }
        }
        model.addAttribute("notes", notes);
        return "camelotSearch/camelotNotesCardMode";
    }

    @RequestMapping(value = "deleteCamelotNote", method = RequestMethod.GET)
    public String deleteNote(@RequestParam(name = "id") String id) {
        NotesDao notesDao = new NotesDao();
        notesDao.deleteCamelotNote(id);
        return "redirect:camelotNotesDisplay.htm";
    }

    @RequestMapping(value = "deleteCamelotNoteCardMode", method = RequestMethod.GET)
    public String deleteCamelotNoteCardMode(@RequestParam(name = "id") String id) {
        NotesDao notesDao = new NotesDao();
        notesDao.deleteCamelotNote(id);
        return "redirect:camelotNotesCardMode.htm";
    }

    @RequestMapping(value = "deleteAllCamelotNotes", method = RequestMethod.GET)
    public String deleteAllCamelotNotes() {
        NotesDao notesDao = new NotesDao();
        notesDao.deleteAllCamelotNote();
        return "redirect:camelotNotesDisplay.htm";
    }

    @RequestMapping(value = "camelotStockPositions", method = RequestMethod.GET)
    public String camelotStockPositions(@RequestParam(name = "itemCode") String altercode, ModelMap modelMap) {

        Item item = camelotSearchDao.getItemByAltercode(altercode);
        NotesDao notesDao = new NotesDao();
        LinkedHashMap<Integer, String> stockPositions = notesDao.getStockPositions(item);

        modelMap.addAttribute("item", item);
        modelMap.addAttribute("stockPositions", stockPositions);
        return "camelotSearch/camelotStockPositions";
    }

    @RequestMapping(value = "addCamelotStockPosition", method = RequestMethod.POST)
    public String addCamelotStockPosition(@RequestParam(name = "itemCode") String itemCode,
            ModelMap model) {

        ArrayList<String> stockPositions = new ArrayList<>();
        stockPositions.add("Α1-1");
        stockPositions.add("Α1-2");
        stockPositions.add("Α1-3");
        stockPositions.add("Α1-4");
        stockPositions.add("Α1-5");

        stockPositions.add("Α2-1");
        stockPositions.add("Α2-2");
        stockPositions.add("Α2-3");
        stockPositions.add("Α2-4");
        stockPositions.add("Α2-5");

        stockPositions.add("Α3-1");
        stockPositions.add("Α3-2");
        stockPositions.add("Α3-3");
        stockPositions.add("Α3-4");
        stockPositions.add("Α3-5");

        stockPositions.add("Α4-1");
        stockPositions.add("Α4-2");
        stockPositions.add("Α4-3");
        stockPositions.add("Α4-4");
        stockPositions.add("Α4-5");

        stockPositions.add("Α5-1");
        stockPositions.add("Α5-2");
        stockPositions.add("Α5-3");
        stockPositions.add("Α5-4");
        stockPositions.add("Α5-5");

        stockPositions.add("Α6-1");
        stockPositions.add("Α6-2");
        stockPositions.add("Α6-3");
        stockPositions.add("Α6-4");
        stockPositions.add("Α6-5");

        stockPositions.add("Α7-1");
        stockPositions.add("Α7-2");
        stockPositions.add("Α7-3");
        stockPositions.add("Α7-4");
        stockPositions.add("Α7-5");

        stockPositions.add("Α8-1");
        stockPositions.add("Α8-2");
        stockPositions.add("Α8-3");
        stockPositions.add("Α8-4");
        stockPositions.add("Α8-5");

        stockPositions.add("Α9-1");
        stockPositions.add("Α9-2");
        stockPositions.add("Α9-3");
        stockPositions.add("Α9-4");
        stockPositions.add("Α9-5");

        stockPositions.add("Α10-1");
        stockPositions.add("Α10-2");
        stockPositions.add("Α10-3");

        stockPositions.add("Α11-1");
        stockPositions.add("Α11-2");
        stockPositions.add("Α11-3");
        stockPositions.add("Α11-4");
        stockPositions.add("Α11-5");

        stockPositions.add("Α12-1");
        stockPositions.add("Α12-2");
        stockPositions.add("Α12-3");
        stockPositions.add("Α12-4");
        stockPositions.add("Α12-5");

        stockPositions.add("Α13-1");
        stockPositions.add("Α13-2");
        stockPositions.add("Α13-3");
        stockPositions.add("Α13-4");
        stockPositions.add("Α13-5");

        stockPositions.add("Α14-1");
        stockPositions.add("Α14-2");
        stockPositions.add("Α14-3");
        stockPositions.add("Α14-4");
        stockPositions.add("Α14-5");

        stockPositions.add("Α15-1");
        stockPositions.add("Α15-2");
        stockPositions.add("Α15-3");
        stockPositions.add("Α15-4");
        stockPositions.add("Α15-5");
        //------------

        stockPositions.add("Β1-1");
        stockPositions.add("Β1-2");
        stockPositions.add("Β1-3");

        stockPositions.add("Β2-1");
        stockPositions.add("Β2-2");
        stockPositions.add("Β2-3");

        stockPositions.add("Β3-1");
        stockPositions.add("Β3-2");
        stockPositions.add("Β3-3");

        stockPositions.add("Β4-1");
        stockPositions.add("Β4-2");
        stockPositions.add("Β4-3");

        stockPositions.add("Β5-1");
        stockPositions.add("Β5-2");
        stockPositions.add("Β5-3");

        stockPositions.add("Β6-1");
        stockPositions.add("Β6-2");
        stockPositions.add("Β6-3");

        stockPositions.add("Β7-1");
        stockPositions.add("Β7-2");
        stockPositions.add("Β7-3");

        stockPositions.add("Β8-1");
        stockPositions.add("Β8-2");
        stockPositions.add("Β8-3");

        stockPositions.add("Β9-1");
        stockPositions.add("Β9-2");
        stockPositions.add("Β9-3");
        //------------------
        stockPositions.add("Γ1-1");
        stockPositions.add("Γ1-2");
        stockPositions.add("Γ1-3");

        stockPositions.add("Γ2-1");
        stockPositions.add("Γ2-2");
        stockPositions.add("Γ2-3");

        stockPositions.add("Γ3-1");
        stockPositions.add("Γ3-2");
        stockPositions.add("Γ3-3");

        stockPositions.add("Γ4-1");
        stockPositions.add("Γ4-2");
        stockPositions.add("Γ4-3");

        stockPositions.add("Γ5-1");
        stockPositions.add("Γ5-2");
        stockPositions.add("Γ5-3");

        stockPositions.add("Γ6-1");
        stockPositions.add("Γ6-2");
        stockPositions.add("Γ6-3");

        stockPositions.add("Γ7-1");
        stockPositions.add("Γ7-2");
        stockPositions.add("Γ7-3");

        stockPositions.add("Γ8-1");
        stockPositions.add("Γ8-2");
        stockPositions.add("Γ8-3");

        stockPositions.add("Γ9-1");
        stockPositions.add("Γ9-2");
        stockPositions.add("Γ9-3");
        //------------------
        stockPositions.add("Δ1-1");
        stockPositions.add("Δ1-2");
        stockPositions.add("Δ1-3");

        stockPositions.add("Δ2-1");
        stockPositions.add("Δ2-2");
        stockPositions.add("Δ2-3");

        stockPositions.add("Δ3-1");
        stockPositions.add("Δ3-2");
        stockPositions.add("Δ3-3");

        stockPositions.add("Δ4-1");
        stockPositions.add("Δ4-2");
        stockPositions.add("Δ4-3");

        stockPositions.add("Δ5-1");
        stockPositions.add("Δ5-2");
        stockPositions.add("Δ5-3");

        stockPositions.add("Δ6-1");
        stockPositions.add("Δ6-2");
        stockPositions.add("Δ6-3");

        stockPositions.add("Δ7-1");
        stockPositions.add("Δ7-2");
        stockPositions.add("Δ7-3");

        stockPositions.add("Δ8-1");
        stockPositions.add("Δ8-2");
        stockPositions.add("Δ8-3");

        stockPositions.add("Δ9-1");
        stockPositions.add("Δ9-2");
        stockPositions.add("Δ9-3");
        //------------------
        stockPositions.add("Ε1-1");
        stockPositions.add("Ε1-2");
        stockPositions.add("Ε1-3");

        stockPositions.add("Ε2-1");
        stockPositions.add("Ε2-2");
        stockPositions.add("Ε2-3");

        stockPositions.add("Ε3-1");
        stockPositions.add("Ε3-2");
        stockPositions.add("Ε3-3");

        stockPositions.add("Ε4-1");
        stockPositions.add("Ε4-2");
        stockPositions.add("Ε4-3");

        stockPositions.add("Ε5-1");
        stockPositions.add("Ε5-2");
        stockPositions.add("Ε5-3");

        stockPositions.add("Ε6-1");
        stockPositions.add("Ε6-2");
        stockPositions.add("Ε6-3");

        stockPositions.add("Ε7-1");
        stockPositions.add("Ε7-2");
        stockPositions.add("Ε7-3");

        stockPositions.add("Ε8-1");
        stockPositions.add("Ε8-2");
        stockPositions.add("Ε8-3");

        stockPositions.add("Ε9-1");
        stockPositions.add("Ε9-2");
        stockPositions.add("Ε9-3");
        //------------------
        stockPositions.add("Ζ1-1");
        stockPositions.add("Ζ1-2");
        stockPositions.add("Ζ1-3");

        stockPositions.add("Ζ2-1");
        stockPositions.add("Ζ2-2");
        stockPositions.add("Ζ2-3");

        stockPositions.add("Ζ3-1");
        stockPositions.add("Ζ3-2");
        stockPositions.add("Ζ3-3");

        stockPositions.add("Ζ4-1");
        stockPositions.add("Ζ4-2");
        stockPositions.add("Ζ4-3");

        stockPositions.add("Ζ5-1");
        stockPositions.add("Ζ5-2");
        stockPositions.add("Ζ5-3");

        stockPositions.add("Ζ6-1");
        stockPositions.add("Ζ6-2");
        stockPositions.add("Ζ6-3");

        stockPositions.add("Ζ7-1");
        stockPositions.add("Ζ7-2");
        stockPositions.add("Ζ7-3");

        stockPositions.add("Ζ8-1");
        stockPositions.add("Ζ8-2");
        stockPositions.add("Ζ8-3");

        stockPositions.add("Ζ9-1");
        stockPositions.add("Ζ9-2");
        stockPositions.add("Ζ9-3");
//------------------
        stockPositions.add("Η1-1");
        stockPositions.add("Η1-2");
        stockPositions.add("Η1-3");

        stockPositions.add("Η2-1");
        stockPositions.add("Η2-2");
        stockPositions.add("Η2-3");

        stockPositions.add("Η3-1");
        stockPositions.add("Η3-2");
        stockPositions.add("Η3-3");

        stockPositions.add("Η4-1");
        stockPositions.add("Η4-2");
        stockPositions.add("Η4-3");

        stockPositions.add("Η5-1");
        stockPositions.add("Η5-2");
        stockPositions.add("Η5-3");

        stockPositions.add("Η6-1");
        stockPositions.add("Η6-2");
        stockPositions.add("Η6-3");

        stockPositions.add("Η7-1");
        stockPositions.add("Η7-2");
        stockPositions.add("Η7-3");

        stockPositions.add("Η8-1");
        stockPositions.add("Η8-2");
        stockPositions.add("Η8-3");

        stockPositions.add("Η9-1");
        stockPositions.add("Η9-2");
        stockPositions.add("Η9-3");
        //------------------
        stockPositions.add("Θ1-1");
        stockPositions.add("Θ1-2");
        stockPositions.add("Θ1-3");

        stockPositions.add("Θ2-1");
        stockPositions.add("Θ2-2");
        stockPositions.add("Θ2-3");

        stockPositions.add("Θ3-1");
        stockPositions.add("Θ3-2");
        stockPositions.add("Θ3-3");

        stockPositions.add("Θ4-1");
        stockPositions.add("Θ4-2");
        stockPositions.add("Θ4-3");

        stockPositions.add("Θ5-1");
        stockPositions.add("Θ5-2");
        stockPositions.add("Θ5-3");

        stockPositions.add("Θ6-1");
        stockPositions.add("Θ6-2");
        stockPositions.add("Θ6-3");

        stockPositions.add("Θ7-1");
        stockPositions.add("Θ7-2");
        stockPositions.add("Θ7-3");

        stockPositions.add("Θ8-1");
        stockPositions.add("Θ8-2");
        stockPositions.add("Θ8-3");

        stockPositions.add("Θ9-1");
        stockPositions.add("Θ9-2");
        stockPositions.add("Θ9-3");
        //------------------
        stockPositions.add("Ι1-1");
        stockPositions.add("Ι1-2");
        stockPositions.add("Ι1-3");

        stockPositions.add("Ι2-1");
        stockPositions.add("Ι2-2");
        stockPositions.add("Ι2-3");

        stockPositions.add("Ι3-1");
        stockPositions.add("Ι3-2");
        stockPositions.add("Ι3-3");

        stockPositions.add("Ι4-1");
        stockPositions.add("Ι4-2");
        stockPositions.add("Ι4-3");

        stockPositions.add("Ι5-1");
        stockPositions.add("Ι5-2");
        stockPositions.add("Ι5-3");

        stockPositions.add("Ι6-1");
        stockPositions.add("Ι6-2");
        stockPositions.add("Ι6-3");

        stockPositions.add("Ι7-1");
        stockPositions.add("Ι7-2");
        stockPositions.add("Ι7-3");

        stockPositions.add("Ι8-1");
        stockPositions.add("Ι8-2");
        stockPositions.add("Ι8-3");

        stockPositions.add("Ι9-1");
        stockPositions.add("Ι9-2");
        stockPositions.add("Ι9-3");
        //------------------
        stockPositions.add("Κ1-1");
        stockPositions.add("Κ1-2");
        stockPositions.add("Κ1-3");

        stockPositions.add("Κ2-1");
        stockPositions.add("Κ2-2");
        stockPositions.add("Κ2-3");

        stockPositions.add("Κ3-1");
        stockPositions.add("Κ3-2");
        stockPositions.add("Κ3-3");

        stockPositions.add("Κ4-1");
        stockPositions.add("Κ4-2");
        stockPositions.add("Κ4-3");

        stockPositions.add("Κ5-1");
        stockPositions.add("Κ5-2");
        stockPositions.add("Κ5-3");

        stockPositions.add("Κ6-1");
        stockPositions.add("Κ6-2");
        stockPositions.add("Κ6-3");

        stockPositions.add("Κ7-1");
        stockPositions.add("Κ7-2");
        stockPositions.add("Κ7-3");

        stockPositions.add("Κ8-1");
        stockPositions.add("Κ8-2");
        stockPositions.add("Κ8-3");

        stockPositions.add("Κ9-1");
        stockPositions.add("Κ9-2");
        stockPositions.add("Κ9-3");

        //------------------
        stockPositions.add("Λ1-1");
        stockPositions.add("Λ1-2");
        stockPositions.add("Λ1-3");

        stockPositions.add("Λ2-1");
        stockPositions.add("Λ2-2");
        stockPositions.add("Λ2-3");

        stockPositions.add("Λ3-1");
        stockPositions.add("Λ3-2");
        stockPositions.add("Λ3-3");

        stockPositions.add("Λ4-1");
        stockPositions.add("Λ4-2");
        stockPositions.add("Λ4-3");

        stockPositions.add("Λ5-1");
        stockPositions.add("Λ5-2");
        stockPositions.add("Λ5-3");

        stockPositions.add("Λ6-1");
        stockPositions.add("Λ6-2");
        stockPositions.add("Λ6-3");

        stockPositions.add("Λ7-1");
        stockPositions.add("Λ7-2");
        stockPositions.add("Λ7-3");

        stockPositions.add("Λ8-1");
        stockPositions.add("Λ8-2");
        stockPositions.add("Λ8-3");

        stockPositions.add("Λ9-1");
        stockPositions.add("Λ9-2");
        stockPositions.add("Λ9-3");

        //------------------
        stockPositions.add("Μ1-1");
        stockPositions.add("Μ1-2");
        stockPositions.add("Μ1-3");

        stockPositions.add("Μ2-1");
        stockPositions.add("Μ2-2");
        stockPositions.add("Μ2-3");

        stockPositions.add("Μ3-1");
        stockPositions.add("Μ3-2");
        stockPositions.add("Μ3-3");

        stockPositions.add("Μ4-1");
        stockPositions.add("Μ4-2");
        stockPositions.add("Μ4-3");

        stockPositions.add("Μ5-1");
        stockPositions.add("Μ5-2");
        stockPositions.add("Μ5-3");

        stockPositions.add("Μ6-1");
        stockPositions.add("Μ6-2");
        stockPositions.add("Μ6-3");

        stockPositions.add("Μ7-1");
        stockPositions.add("Μ7-2");
        stockPositions.add("Μ7-3");

        stockPositions.add("Μ8-1");
        stockPositions.add("Μ8-2");
        stockPositions.add("Μ8-3");

        stockPositions.add("Μ9-1");
        stockPositions.add("Μ9-2");
        stockPositions.add("Μ9-3");

        //------------------
        stockPositions.add("Ν1-1");
        stockPositions.add("Ν1-2");
        stockPositions.add("Ν1-3");

        stockPositions.add("Ν2-1");
        stockPositions.add("Ν2-2");
        stockPositions.add("Ν2-3");

        stockPositions.add("Ν3-1");
        stockPositions.add("Ν3-2");
        stockPositions.add("Ν3-3");

        stockPositions.add("Ν4-1");
        stockPositions.add("Ν4-2");
        stockPositions.add("Ν4-3");

        stockPositions.add("Ν5-1");
        stockPositions.add("Ν5-2");
        stockPositions.add("Ν5-3");

        stockPositions.add("Ν6-1");
        stockPositions.add("Ν6-2");
        stockPositions.add("Ν6-3");

        stockPositions.add("Ν7-1");
        stockPositions.add("Ν7-2");
        stockPositions.add("Ν7-3");

        stockPositions.add("Ν8-1");
        stockPositions.add("Ν8-2");
        stockPositions.add("Ν8-3");

        stockPositions.add("Ν9-1");
        stockPositions.add("Ν9-2");
        stockPositions.add("Ν9-3");

        //------------------
        stockPositions.add("Ξ1-1");
        stockPositions.add("Ξ1-2");
        stockPositions.add("Ξ1-3");

        stockPositions.add("Ξ2-1");
        stockPositions.add("Ξ2-2");
        stockPositions.add("Ξ2-3");

        stockPositions.add("Ξ3-1");
        stockPositions.add("Ξ3-2");
        stockPositions.add("Ξ3-3");

        stockPositions.add("Ξ4-1");
        stockPositions.add("Ξ4-2");
        stockPositions.add("Ξ4-3");

        stockPositions.add("Ξ5-1");
        stockPositions.add("Ξ5-2");
        stockPositions.add("Ξ5-3");

        stockPositions.add("Ξ6-1");
        stockPositions.add("Ξ6-2");
        stockPositions.add("Ξ6-3");

        stockPositions.add("Ξ7-1");
        stockPositions.add("Ξ7-2");
        stockPositions.add("Ξ7-3");

        stockPositions.add("Ξ8-1");
        stockPositions.add("Ξ8-2");
        stockPositions.add("Ξ8-3");

        stockPositions.add("Ξ9-1");
        stockPositions.add("Ξ9-2");
        stockPositions.add("Ξ9-3");

        stockPositions.add("Ψ1");
        stockPositions.add("Ψ2");
        stockPositions.add("Ψ3");
        stockPositions.add("Ψ4");
        stockPositions.add("Ψ5");
        stockPositions.add("Ψ6");
        stockPositions.add("Ψ7");
        stockPositions.add("Ψ8");
        stockPositions.add("Ψ9");
        stockPositions.add("Ψ10");
        stockPositions.add("Ψ11");
        stockPositions.add("Ψ12");

        stockPositions.add("Ψ13");
        stockPositions.add("Ψ14");
        stockPositions.add("Ψ15");
        stockPositions.add("Ψ16");
        stockPositions.add("Ψ17");
        stockPositions.add("Ψ18");
        stockPositions.add("Ψ19");
        stockPositions.add("Ψ20");
        stockPositions.add("Ψ21");
        stockPositions.add("Ψ22");
        stockPositions.add("Ψ23");
        stockPositions.add("Ψ24");
        stockPositions.add("Ψ25");
        stockPositions.add("Ψ26");
        stockPositions.add("Ψ27");
        stockPositions.add("Ψ28");
        stockPositions.add("Ψ29");
        stockPositions.add("Ψ30");
        stockPositions.add("Ψ31");
        stockPositions.add("Ψ32");
        stockPositions.add("Ψ33");
        stockPositions.add("Ψ34");
        stockPositions.add("Ψ35");
        stockPositions.add("Ψ36");
        stockPositions.add("Ψ37");
        stockPositions.add("Ψ38");
        stockPositions.add("Ψ39");
        stockPositions.add("Ψ41");
        stockPositions.add("Ψ42");
        stockPositions.add("Ψ43");
        stockPositions.add("Ψ44");
        stockPositions.add("Ψ45");
        stockPositions.add("Ψ46");
        stockPositions.add("Ψ47");
        stockPositions.add("Ψ48");
        stockPositions.add("Ψ49");
        stockPositions.add("Ψ50");
        stockPositions.add("Ψ51");
        stockPositions.add("Ψ52");
        stockPositions.add("Ψ53");
        stockPositions.add("Ψ54");
        stockPositions.add("Ψ55");
        stockPositions.add("Ψ56");
        stockPositions.add("Ψ57");
        stockPositions.add("Ψ58");
        stockPositions.add("Ψ59");
        stockPositions.add("Ψ61");
        stockPositions.add("Ψ62");
        stockPositions.add("Ψ63");
        stockPositions.add("Ψ64");
        stockPositions.add("Ψ65");
        stockPositions.add("Ψ66");
        stockPositions.add("Ψ67");

        stockPositions.add("CONTAINER_1");
        stockPositions.add("CONTAINER_2");
        stockPositions.add("CONTAINER_3");
        stockPositions.add("CONTAINER_4");
        stockPositions.add("CONTAINER_5");
        stockPositions.add("CONTAINER_6");
        stockPositions.add("CONTAINER_7");
        stockPositions.add("CONTAINER_8");
        stockPositions.add("CONTAINER_9");
        stockPositions.add("CONTAINER_10");

        stockPositions.add("CONTAINER_11");
        stockPositions.add("CONTAINER_12");
        stockPositions.add("CONTAINER_13");
        stockPositions.add("CONTAINER_14");
        stockPositions.add("CONTAINER_15");
        stockPositions.add("CONTAINER_16");
        stockPositions.add("CONTAINER_17");
        stockPositions.add("CONTAINER_18");
        stockPositions.add("CONTAINER_19");
        stockPositions.add("CONTAINER_20");

        stockPositions.add("CONTAINER_21");
        stockPositions.add("CONTAINER_22");
        stockPositions.add("CONTAINER_23");
        stockPositions.add("CONTAINER_24");
        stockPositions.add("CONTAINER_25");

        /*    for (String sp : stockPositions) {
            System.out.println(sp);
        } */
        model.addAttribute("itemCode", itemCode);
        model.addAttribute("stockPositions", stockPositions);

        return "camelotSearch/addCamelotStockPosition";
    }

    @RequestMapping(value = "setCamelotStockPosition", method = RequestMethod.POST)
    public String setCamelotStockPosition(HttpSession session, @RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "position") String position,
            ModelMap model) {
        try {
            String user = (String) session.getAttribute("user");
            System.out.println("Super User Status:" + user);
            if (user == null) {
                model.addAttribute("message", "You are not authorized for this paged");
                return "errorPage";
            }
            String userName = (String) session.getAttribute("userName");
            NotesDao notesDao = new NotesDao();
            String result = notesDao.addCamelotStockPosition(itemCode, position, userName);
            model.addAttribute("result", result);

            return "redirect:camelotStockPositions.htm?itemCode=" + URLEncoder.encode(itemCode, "UTF-8");
            // return "vakulina/notesDisplay";
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CamelotSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "erroPage";

    }

    @RequestMapping(value = "camelotStockPositionDeletion", method = RequestMethod.GET)
    public String camelotStockPositionDeletion(@RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "id") String id,
            ModelMap model) {

        model.addAttribute("id", id);
        model.addAttribute("itemCode", itemCode);
        return "camelotSearch/camelotStockPositionDeletion";
    }

    @RequestMapping(value = "deleteCameltoStockPosition", method = RequestMethod.GET)
    public String deleteCameltoStockPosition(HttpSession session, @RequestParam(name = "itemCode") String itemCode,
            @RequestParam(name = "id") String id,
            ModelMap model) {
        try {
            String user = (String) session.getAttribute("user");
            System.out.println("Super User Status:" + user);
            if (user == null) {
                model.addAttribute("message", "You are not authorized for this paged");
                return "errorPage";
            }
            String userName = (String) session.getAttribute("userName");

            model.addAttribute("id", id);

            NotesDao notesDao = new NotesDao();
            notesDao.deleteCamelotStockPosition(id, userName);

            return "redirect:camelotStockPositions.htm?itemCode=" + URLEncoder.encode(itemCode, "UTF-8");
            // return "vakulina/notesDisplay";
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CamelotSearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "erroPage";
    }

    @RequestMapping(value = "camelotStockPositionsDisplay", method = RequestMethod.GET)
    public String camelotStockPositionsDisplay(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        LinkedHashMap<String, ArrayList<String>> camelotItemsByStockPosition = notesDao.getCamelotItemsByStockPositions();
        model.addAttribute("camelotItemsByStockPosition", camelotItemsByStockPosition);
        return "camelotSearch/camelotStockPositionsDisplay";
    }

    @RequestMapping(value = "camelotStockPositionsByItemCodeDisplay", method = RequestMethod.GET)
    public String camelotStockPositionsByItemCodeDisplay(ModelMap model) {

        NotesDao notesDao = new NotesDao();
        LinkedHashMap<String, ArrayList<String>> camelotStockPositionsByItemCode = notesDao.camelotStockPositionsByItemCode();
        model.addAttribute("camelotStockPositionsByItemCode", camelotStockPositionsByItemCode);
        return "camelotSearch/camelotStockPositionsByItemCodeDisplay";
    }

    //-----------------------------Barcode PRintig--------------------
    @RequestMapping(value = "printCamelotBarcode")
    public String printCamelotBarcode(@RequestParam(name = "altercode") String altercode, ModelMap model) {
        System.out.println("Altercode :" + altercode);
        CamelotSearchDao searchDao = new CamelotSearchDao();
        Item item = searchDao.getItemByAltercode(altercode);

        if (item == null) {
            System.out.println("Item Null");
        }

        OutputStream out = null;
        try {

            Code128Bean barcode128Bean = new Code128Bean();
            barcode128Bean.setCodeset(Code128Constants.CODESET_B);
            final int dpi = 100;
            //Configure the barcode generator
            //adjust barcode width here
            barcode128Bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));
            barcode128Bean.doQuietZone(false);
            barcode128Bean.setBarHeight(8);
            //bean.setVerticalQuietZone(3);
            barcode128Bean.setQuietZone(0);
            barcode128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            //Open output file
            File outputFile = new File("C:/Pet4U_3.0/barcode.png");
            out = new FileOutputStream(outputFile);
            try {
                BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(
                        out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

                barcode128Bean.generateBarcode(canvasProvider, altercode);

                try {
                    canvasProvider.finish();
                } catch (IOException ex) {
                    Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // model.addAttribute("code", code);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //+++++++++++++++++++++++
        String path = "C:/Pet4U_3.0/qrCode.png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        try {
            //invoking the user-defined method that creates the QR code
            generateQRcode(altercode, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly
//prints if the QR code is generated
        } catch (WriterException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        }

//-------------
        String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        CamelotBarcodePrinter barcodePrinter = new CamelotBarcodePrinter();
        // String printName = "ZDesigner GC420t (EPL)";
        // BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------
        barcodePrinter.setLabelsCount(1);

        barcodePrinter.setCode(item.getCode());
        //  barcodePrinter.setBarcode(altercode.substring(altercode.length() - 6));
        if (altercode.length() >= 6) {
            barcodePrinter.setBarcode(altercode.substring(altercode.length() - 6));
        } else {
            barcodePrinter.setBarcode(altercode);
        }
        barcodePrinter.setDescription(item.getDescription());
        // String position = item.getPosition().substring(2);
        barcodePrinter.setPosition(item.getPosition());

        barcodePrinter.printSomething(printName);

        return "index";
    }

    //++
    @RequestMapping(value = "printCamelotMainBarcode")
    public String printCamelotMainBarcode(@RequestParam(name = "altercode") String altercode, ModelMap model) {
        System.out.println("Altercode :" + altercode);
        CamelotSearchDao searchDao = new CamelotSearchDao();
        Item item = searchDao.getItemByAltercode(altercode);

        if (item == null) {
            System.out.println("Item Null");
            model.addAttribute("message", "Can't print this label. Item is NULL. Ask for help");
            return "errorPage";
        }
        String mainBarcode = item.getMainBarcode();

        if (mainBarcode == null) {
            System.out.println("mainBarcode Null");
            model.addAttribute("message", "Can't print this label. mainBarcode is NULL. Ask for help");
            return "errorPage";
        }
        OutputStream out = null;
        try {

            Code128Bean barcode128Bean = new Code128Bean();
            barcode128Bean.setCodeset(Code128Constants.CODESET_B);
            final int dpi = 100;
            //Configure the barcode generator
            //adjust barcode width here
            barcode128Bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));
            barcode128Bean.doQuietZone(false);
            barcode128Bean.setBarHeight(8);
            //bean.setVerticalQuietZone(3);
            barcode128Bean.setQuietZone(0);
            barcode128Bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
            //Open output file
            File outputFile = new File("C:/Pet4U_3.0/barcode.png");
            out = new FileOutputStream(outputFile);
            try {
                BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(
                        out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

                barcode128Bean.generateBarcode(canvasProvider, mainBarcode);

                try {
                    canvasProvider.finish();
                } catch (IOException ex) {
                    Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // model.addAttribute("code", code);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //+++++++++++++++++++++++
        String path = "C:/Pet4U_3.0/qrCode.png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        try {
            //invoking the user-defined method that creates the QR code
            generateQRcode(mainBarcode, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly
//prints if the QR code is generated
        } catch (WriterException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        }

//-------------
        String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        CamelotBarcodePrinter barcodePrinter = new CamelotBarcodePrinter();
        // String printName = "ZDesigner GC420t (EPL)";
        // BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------
        barcodePrinter.setLabelsCount(1);

        barcodePrinter.setCode(item.getCode());
        //  barcodePrinter.setBarcode(altercode.substring(altercode.length() - 6));
        if (mainBarcode.length() >= 6) {
            barcodePrinter.setBarcode(mainBarcode.substring(mainBarcode.length() - 6));
        } else {
            barcodePrinter.setBarcode(mainBarcode);
        }

        barcodePrinter.setDescription(item.getDescription());
        // String position = item.getPosition().substring(2);
        barcodePrinter.setPosition(item.getPosition());

        barcodePrinter.printSomething(printName);

        return "index";
    }
    //-__

    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
//the BitMatrix class represents the 2D matrix of bits  
//MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.  
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

}
