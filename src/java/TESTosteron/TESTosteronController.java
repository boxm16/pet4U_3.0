package TESTosteron;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotDao;
import Inventory.InventoryItem;
import Notes.NotesDao;
import Pet4uItems.Pet4uItemsDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TESTosteronController {

    @RequestMapping(value = "testosteronDashboard")
    public String testosteronDashboard(HttpSession session) {
        String user = (String) session.getAttribute("user");
        System.out.println("Super User Status:" + user);
        if (user == null) {
            return "errorPage";
        } else if (user.equals(
                "identified")) {
            return "testosteron/testosteronDashboard";
        } else {
            return "errorPage";
        }
    }

    @RequestMapping(value = "testViewAndDeepSearch")
    public String testViewAndDeepSearch() {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        TESTosteronDao testosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = testosteronDao.getAllPet4UItemsWithDeepSearch();
        for (Map.Entry<String, Item> pet4uAllItemsEntry : pet4uAllItems.entrySet()) {
            String codeEx = pet4uAllItemsEntry.getKey();
            Item value1 = pet4uAllItemsEntry.getValue();
            Item v2 = allPet4UItemsWithDeepSearch.remove(codeEx);
            if (v2 == null) {
                System.out.println("SHOUT, NULL IN DEEP SEARCH " + codeEx + "-DESCRIPTION-" + value1.getDescription() + "+POSITION+" + value1.getPosition());

            } else {
                if (!value1.getQuantity().equals(v2.getQuantity())) {
                    System.out.println("QYT " + codeEx + "--" + value1.getDescription() + "++" + value1.getQuantity() + "X" + v2.getQuantity());

                }
            }

        }

        System.out.println("LEFT OVERS: " + allPet4UItemsWithDeepSearch.size());
        for (Map.Entry<String, Item> pet4uAllItemsEntry : allPet4UItemsWithDeepSearch.entrySet()) {
            System.out.println(pet4uAllItemsEntry.getValue().getCode()
                    + "="
                    + pet4uAllItemsEntry.getValue().getDescription()
                    + "-"
                    + pet4uAllItemsEntry.getValue().getPosition()
                    + "+"
                    + pet4uAllItemsEntry.getValue().getPosition());

        }
        System.out.println("TEST COMPLETED: RESULT SEE ABOVE");
        return "testosteron/testResult";
    }

    @RequestMapping(value = "camelotStockPositonsTesting")
    public String camelotStockPositonsTesting(ModelMap modelMap) {
        CamelotDao camelotDao = new CamelotDao();
        LinkedHashMap<String, Item> camelotAllItems = camelotDao.getCamelotItems();

        NotesDao notesDao = new NotesDao();
        int index = 0;
        for (Map.Entry<String, Item> caiEntrySet : camelotAllItems.entrySet()) {
            String result = notesDao.saveCamelotNote(caiEntrySet.getValue().getCode(), "dokimastiko");
            System.out.println(index + " : " + caiEntrySet.getValue().getCode() + " : " + result);
            index++;
        }
        modelMap.addAttribute("camelotAllItems", camelotAllItems);
        return "testosteron/camelotAllItemsNotedTestResult";
    }

    @RequestMapping(value = "showAllDeletedCamelotNotesBatches")
    public String showAllDeletedCamelotNotesBatches(ModelMap modelMap) {
        NotesDao notesDao = new NotesDao();
        LinkedHashMap<String, Integer> deletedCamelotNotesBatches = notesDao.getAllDeletedCamelotNotesBatches();
        modelMap.addAttribute("deletedCamelotNotesBatches", deletedCamelotNotesBatches);
        return "testosteron/allDeletedCamelotNotesBatches";
    }

    @RequestMapping(value = "showDeletedCamelotNotesBatch")
    public String showDeletedCamelotNotesBatch(@RequestParam(name = "batch") String batch, ModelMap modelMap) {

        NotesDao notesDao = new NotesDao();
        ArrayList<InventoryItem> notes = notesDao.getDeletedCamelotNotesBatch(batch);

        modelMap.addAttribute("notes", notes);

        return "testosteron/deletedCamelotNotesBatch";
    }

    //-------------------------------------
    @RequestMapping(value = "printOut")
    public String printOut(ModelMap modelMap) {

        /* 
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Number of print services: " + printServices.length);
        DocPrintJob job = null;
        boolean printerCheck = false;
        String printerMessage = "PRINTER MESSAGE:";

        for (PrintService printer : printServices) {
            System.out.println("Printer: " + printer.getName());

            if (printer.getName().equals("HP LaserJet Pro MFP M127-M128 PCLmS")) {
                System.out.println("----------");
                System.out.println("Printer: " + printer.getName());

                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                InputStream inputStream = new ByteArrayInputStream(printerMessage.getBytes());
                Doc doc = new SimpleDoc(inputStream, flavor, null);
                job = printer.createPrintJob();
                try {
                    job.print(doc, null);
                } catch (PrintException ex) {
                    Logger.getLogger(TESTosteronController.class.getName()).log(Level.SEVERE, null, ex);
                }
                printerCheck = true;
            }
            //  }

        }
         */
        String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        PrintWithoutDialog pwd = new PrintWithoutDialog();
        pwd.printSomething(printName);
        return "testosteron/trc";
    }

    @RequestMapping(value = "showShadowCodes")
    public String showShadowCodes(ModelMap modelMap) {
        TESTosteronDao dao = new TESTosteronDao();
        LinkedHashMap<String, Item> allActiveItems = dao.getAllActiveItems();
        LinkedHashMap<String, Item> pet4UItemsRowByRow = dao.getPet4UItemsRowByRow();
        for (Map.Entry<String, Item> allActiveItemsEntry : allActiveItems.entrySet()) {
            Item item = allActiveItemsEntry.getValue();
            ArrayList<AltercodeContainer> altercodes = item.getAltercodes();
            for (AltercodeContainer altercodeContainer : altercodes) {
                String altercode = altercodeContainer.getAltercode();
                if (altercode.contains("-")) {
                    if (altercode.equals(item.getCode())) {
                        continue;
                    }
                    char firstChar = altercode.charAt(0);
                    char lastChar = altercode.charAt(altercode.length() - 1);
                    if (firstChar == '-' || lastChar == '-') {
                        //    System.out.println(item.getCode() + "   " + item.getDescription() + "   " + altercode);
                        String repfactoredAltercode = altercode.replaceAll("-", "");
                        Item shadowItem = pet4UItemsRowByRow.get(repfactoredAltercode);
                        System.out.println("     Item:" + item.getCode() + " " + item.getDescription());
                        if (shadowItem == null) {
                            System.out.println("No Shadow Item");
                        } else {
                            System.out.println("Shadow Item:" + shadowItem.getCode() + " " + shadowItem.getDescription());
                        }
                        System.out.println("----------------------------------------");
                    }

                }
            }
        }
        return "testosteron/shadowCodes";
    }

    @RequestMapping(value = "testSapHanaDB")
    public String testSapHanaDB(ModelMap modelMap) {
        TESTosteronDao tESTosteronDao = new TESTosteronDao();
        ArrayList<String> allSapHanaDatabases = tESTosteronDao.getAllSapHanaDatabases();

        return "testosteron/testosteronDashboard";
    }

    @RequestMapping(value = "getItemsFromSapHanaDB")
    public String getItemsFromSapHanaDB(ModelMap modelMap) {
        TESTosteronDao tESTosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allItems = tESTosteronDao.getItemsFromSapHanaDB();
        modelMap.addAttribute("allItems", allItems);
        return "testosteron/itemsFromSapHana";
    }

    @RequestMapping(value = "createPdfFile")
    public String createPdfFile(ModelMap modelMap) {
        try (PDDocument document = new PDDocument()) {
            // Add a blank page
            PDPage page = new PDPage();
            document.addPage(page);

            // Prepare content stream to write on the page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set font and font size
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

                // Begin writing text
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700); // Position the text on the page
                contentStream.showText("Hello, PDFBox!"); // Write text
                contentStream.endText();

                // Optionally, draw a line
                contentStream.moveTo(100, 690);
                contentStream.lineTo(300, 690);
                contentStream.stroke();
            }

            // Save the PDF to a file
            document.save("C:/Pet4U_3.0/example.pdf");
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("GOOD");
        return "index";
    }

}
