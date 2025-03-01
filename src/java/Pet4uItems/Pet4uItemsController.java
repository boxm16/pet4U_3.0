package Pet4uItems;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemOfInterest;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import CamelotItemsOfInterest.ItemSnapshot;
import Endo.EndoDaoX;
import Endo.EndoPackaging;
import Search.SearchDao;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
public class Pet4uItemsController {

    @Autowired
    private Pet4uItemsDao pet4uItemsDao;

    @Autowired
    private CamelotItemsOfInterestDao camelotItemsOfInterestDao;

    @RequestMapping(value = "pet4uNegativeStock")
    public String pet4uNegativeStock(ModelMap modelMap) {
        LinkedHashMap<String, Item> pet4uItems = pet4uItemsDao.getAllItems();
        modelMap.addAttribute("pet4uItems", pet4uItems);
        //----------------------------
        LinkedHashMap<String, CamelotItemOfInterest> pet4uNegativeItems = pet4uItemsDao.getNegativeItems();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItemsRowByRow = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (Map.Entry<String, CamelotItemOfInterest> pet4uNegatvieItemsEntry : pet4uNegativeItems.entrySet()) {
            String pet4uItemsKey = pet4uNegatvieItemsEntry.getKey();
            System.out.println("pet4uNegativeItems Key :" + pet4uItemsKey);

            Item camelotItem = camelotItemsRowByRow.get(pet4uItemsKey);
            if (camelotItem == null) {
                pet4uNegatvieItemsEntry.getValue().setSupplier("NOT CAMELOT");
                System.out.println("NO SUCH ITEM FROM CAMELOT :" + pet4uItemsKey);
            } else {
                String camelotItemQuantity = camelotItem.getQuantity();
                System.out.println("CAMELOT ITEM QUANTITY :" + camelotItemQuantity);
                Double doubleQuantity;
                try {
                    doubleQuantity = Double.parseDouble(camelotItemQuantity);
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid string in argumment while trying to convert string to double");
                    doubleQuantity = null;
                }
                System.out.println("CAMELOT ITEM QUANTITY IN DOUBLE: " + doubleQuantity);
                pet4uNegatvieItemsEntry.getValue().setCamelotStock(doubleQuantity);
                pet4uNegatvieItemsEntry.getValue().setSupplier("CAMELOT");
            }
        }
        modelMap.addAttribute("pet4uNegativeItems", pet4uNegativeItems);

        return "/pet4uItems/pet4uNegativeStock";
    }

    @RequestMapping(value = "itemsFromCamelot")
    public String itemsFromCamelot(ModelMap modelMap) {
        HashSet<String> filterHashSet = new HashSet();
        LinkedHashMap<String, Item> itemsFromCamelot = new LinkedHashMap<>();
        LinkedHashMap<String, Item> pet4uItems = pet4uItemsDao.getAllItems();
        ArrayList<String> camelotAltercodes = camelotItemsOfInterestDao.getCamelotAltercodesFromMicrosoftDB();

        for (Map.Entry<String, Item> pet4uItemsEntry : pet4uItems.entrySet()) {
            ArrayList<AltercodeContainer> altercodes = pet4uItemsEntry.getValue().getAltercodes();
            for (AltercodeContainer altercode : altercodes) {
                if (camelotAltercodes.contains(altercode.getAltercode())) {
                    if (filterHashSet.contains(pet4uItemsEntry.getValue().getDescription())) {
                    } else {
                        itemsFromCamelot.put(altercode.getAltercode(), pet4uItemsEntry.getValue());
                        filterHashSet.add(pet4uItemsEntry.getValue().getDescription());
                    }
                }
            }

        }
        modelMap.addAttribute("itemsFromCamelot", itemsFromCamelot);
        return "/pet4uItems/itemsFromCamelot";
    }

    @RequestMapping(value = "camelotItemsWithPossitionDifference")
    public String camelotItemsWithPossitionDifference(ModelMap modelMap) {
        ArrayList<ArrayList<String>> differences = new ArrayList();

        LinkedHashMap<String, Item> pet4uItems = pet4uItemsDao.getAllActiveItems();
        LinkedHashMap<String, Item> camelotItemsRowByRow = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (Map.Entry<String, Item> pet4uItemsEntry : pet4uItems.entrySet()) {
            ArrayList<AltercodeContainer> altercodes = pet4uItemsEntry.getValue().getAltercodes();
            for (AltercodeContainer altercode : altercodes) {
                String camelotVersionAltercode = altercode.getAltercode();
                if (altercode.getAltercode().contains("-WE")) {
                    camelotVersionAltercode = altercode.getAltercode().replace("-WE", "");
                }
                if (camelotItemsRowByRow.containsKey(camelotVersionAltercode)) {
                    Item camelotItem = camelotItemsRowByRow.get(camelotVersionAltercode);
                    Item pet4uItem = pet4uItemsEntry.getValue();
                    String c_position = "C-" + camelotItem.getPosition();
                    if (!camelotItem.getPosition().isEmpty()) {
                        if (pet4uItem.getPosition().contains("C-") || pet4uItem.getPosition().isEmpty()) {
                            String quantity = camelotItem.getQuantity();
                            if (quantity != null) {
                                Double q = Double.parseDouble(quantity);
                                if (q > 0) {
                                    if (!pet4uItem.getPosition().equals(c_position)) {

                                        ArrayList<String> diff = new ArrayList<>();
                                        diff.add(pet4uItem.getCode());
                                        diff.add(pet4uItem.getDescription());
                                        diff.add(pet4uItem.getPosition());
                                        diff.add(c_position);
                                        diff.add(camelotItem.getQuantity());
                                        differences.add(diff);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        modelMap.addAttribute("differences", differences);
        return "/pet4uItems/camelotItemsWithPossitonDifference";
    }

    @RequestMapping(value = "weightItems")
    public String weightItems(ModelMap modelMap) {

        LinkedHashMap<String, Item> weightItems = pet4uItemsDao.getWeightAllItems();

        modelMap.addAttribute("weightItems", weightItems);
        return "/pet4uItems/weightItems";
    }

    @RequestMapping(value = "pet4uAllItemsFromTable")
    public String pet4uAllItemsFromTable(ModelMap modelMap) {

        LinkedHashMap<String, Item> pet4uAllItemsFromTable = pet4uItemsDao.getAllItemsFromTable();

        modelMap.addAttribute("pet4uAllItemsFromTable", pet4uAllItemsFromTable);
        return "/pet4uItems/pet4uAllItemsFromTable";
    }

    @RequestMapping(value = "pet4uAllItems")
    public String pet4uAllItems(ModelMap modelMap) {

        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        modelMap.addAttribute("pet4uAllItems", pet4uAllItems);
        return "/pet4uItems/pet4uAllItems";
    }

    @RequestMapping(value = "pet4uAllItemsFromTableAndViewComparison")
    public String pet4uAllItemsFromTableAndViewComparison(ModelMap modelMap) {

        LinkedHashMap<String, Item> pet4uAllItemsFromTable = pet4uItemsDao.getAllItemsFromTable();
        LinkedHashMap<String, Item> pet4uAllItemsFromView = pet4uItemsDao.getAllItems_NullVersion();

        modelMap.addAttribute("pet4uAllItemsFromTable", pet4uAllItemsFromTable);
        modelMap.addAttribute("pet4uAllItemsFromView", pet4uAllItemsFromView);
        return "/pet4uItems/pet4uAllItemsFromTableAndViewComparison";
    }

    @RequestMapping(value = "pet4uAllItemsOneLine")
    public String pet4uAllItemsOneLine(ModelMap modelMap) {

        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();
        EndoDaoX endoDaoX = new EndoDaoX();
        LinkedHashMap<String, EndoPackaging> allEndoPackaging = endoDaoX.getAllEndoPackaging();

        modelMap.addAttribute("pet4uAllItems", pet4uAllItems);
        modelMap.addAttribute("allEndoPackaging", allEndoPackaging);
        return "/pet4uItems/pet4uAllItemsOneLine";
    }

    @RequestMapping(value = "pet4uItemsWithPosition")
    public String pet4uItemsWithPosition(ModelMap modelMap) {

        LinkedHashMap<String, Item> items = pet4uItemsDao.getAllItemsWithPosition();
        LinkedHashMap<String, Item> camelotItemsRowByRow = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        modelMap.addAttribute("items", items);
        modelMap.addAttribute("camelotItems", camelotItemsRowByRow);
        return "/pet4uItems/pet4uItemsWithPosition";
    }

    @RequestMapping(value = "pet4uItemsLessThanThree")
    public String pet4uItemsLessThanThree(ModelMap modelMap) {

        LinkedHashMap<String, Item> items = pet4uItemsDao.getAllItemsWithPosition();

        modelMap.addAttribute("items", items);
        return "/pet4uItems/pet4uAllItemsWithLessThanThreeStock";
    }

    public void updateItemsState() {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems_NullVersion();
        // LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllActiveItems();
        pet4uItemsDao.insertPet4uItemsSnapshot(pet4uAllItems);
    }

    @RequestMapping(value = "itemsStateUpdates")
    public String itemsStateUpdates(ModelMap modelMap) {

        ArrayList<Item> difference = new ArrayList<>();

        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();
        LinkedHashMap<String, String> itemsStateSnapshotFromDB = pet4uItemsDao.getItemsStateSnapshot();

        for (Map.Entry<String, Item> pet4uAllItemsEntry : pet4uAllItems.entrySet()) {
            Item item = pet4uAllItemsEntry.getValue();
            String nowState = item.getState();
            String beforeState = itemsStateSnapshotFromDB.get(pet4uAllItemsEntry.getKey());
            if (beforeState == null) {
                System.out.println("New Code Here???" + pet4uAllItemsEntry.getKey());
                beforeState = "";
            }
            if (!nowState.equals(beforeState)) {
                item.setSupplier(beforeState);//i use here Supplier, becouse i dont want to add new field
                difference.add(item);
            }
        }
        modelMap.addAttribute("difference", difference);
        return "/pet4uItems/itemsStateUpdates";
    }

    @RequestMapping(value = "prosfores")
    public String prosfores(ModelMap modelMap) {

        LinkedHashMap<String, Item> prosfores = pet4uItemsDao.getOnlyProsfores();

        modelMap.addAttribute("prosfores", prosfores);
        return "/pet4uItems/prosfores";
    }

    @RequestMapping(value = "pet4uItemsOffSite")
    public String pet4uItemsOffSite(ModelMap modelMap) {

        LinkedHashMap<String, Item> items = pet4uItemsDao.getOffSiteItems();

        modelMap.addAttribute("items", items);
        return "/pet4uItems/pet4uItemsOffSite";
    }

    @RequestMapping(value = "pet4uItemSnapshots")
    public String pet4uItemSnapshots(@RequestParam(name = "code") String code, ModelMap model) {

        ArrayList<ItemSnapshot> itemSnapshots = pet4uItemsDao.getItemSnapshots(code);
        model.addAttribute("itemSnapshots", itemSnapshots);
        model.addAttribute("code", code);
        return "/pet4uItems/itemSnapshots";
    }

    @RequestMapping(value = "printBarcode")
    public String printBarcode(@RequestParam(name = "altercode") String altercode, ModelMap model) {
        System.out.println("Altercode :" + altercode);
        SearchDao searchDao = new SearchDao();
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
        BarcodePrinter barcodePrinter = new BarcodePrinter();
        // String printName = "ZDesigner GC420t (EPL)";
        // BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------
        barcodePrinter.setLabelsCount(1);

        barcodePrinter.setCode(item.getCode());
        barcodePrinter.setBarcode(altercode.substring(altercode.length() - 6));
        barcodePrinter.setDescription(item.getDescription());
        String position = item.getPosition().substring(2);
        barcodePrinter.setPosition(position);

        barcodePrinter.printSomething(printName);

        return "index";
    }

    //------------//------------//-----------
    @RequestMapping(value = "printMainBarcodeX")
    public String printMainBarcodeX(@RequestParam(name = "altercode") String altercode, ModelMap model) {
        System.out.println("Printing Item(Main Barcode) With Altercode: " + altercode);
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(altercode);

        if (item == null || item.getMainBarcode() == null) {
            System.out.println("Item or MainBarcode is NULL");
            model.addAttribute("message", "Can't print this label. Item is NULL. Ask for help");
            return "errorPage";
        }

        String mainBarcode = item.getMainBarcode();
        File outputFile = new File("C:/Pet4U_3.0/barcode.png");

        try (OutputStream out = new FileOutputStream(outputFile)) {
            Code128Bean barcode128Bean = new Code128Bean();
            barcode128Bean.setCodeset(Code128Constants.CODESET_B);

            final int dpi = 600; // 🔹 Force High Resolution to Improve Line Rendering

            barcode128Bean.setModuleWidth(0.8); // Still needed for basic scaling
            barcode128Bean.setBarHeight(20); // Taller bars = better scanning
            barcode128Bean.doQuietZone(true);
            barcode128Bean.setQuietZone(10);

            // Generate barcode
            BitmapCanvasProvider canvasProvider = new BitmapCanvasProvider(
                    dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            barcode128Bean.generateBarcode(canvasProvider, mainBarcode);
            canvasProvider.finish();

            // Force Thicker Bars Using Graphics2D
            BufferedImage barcodeImage = canvasProvider.getBufferedImage();
            Graphics2D g2d = barcodeImage.createGraphics();
            g2d.setStroke(new BasicStroke(2)); // 🔹 Thicker lines without resizing
            g2d.drawImage(barcodeImage, 0, 0, null);
            g2d.dispose();

            // Save the modified barcode
            ImageIO.write(barcodeImage, "png", outputFile);

        } catch (IOException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index";
    }

    //------------//------------//-----------
    @RequestMapping(value = "printMainBarcode")
    public String printMainBarcode(@RequestParam(name = "altercode") String altercode, ModelMap model) {
        System.out.println("Printing Item(Main Barcode)  With Altercode :" + altercode);
        SearchDao searchDao = new SearchDao();
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

            /*if (mainBarcode.matches("\\d+")) { 
            barcode128Bean.setCodeset(Code128Constants.CODESET_C);
        } else {
            barcode128Bean.setCodeset(Code128Constants.CODESET_B);
        }*/
            final int dpi = 100;////// try 300
            //Configure the barcode generator
            //adjust barcode width here
            barcode128Bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));//0.6
            barcode128Bean.doQuietZone(false);
            barcode128Bean.setBarHeight(8);//20
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
        BarcodePrinter barcodePrinter = new BarcodePrinter();
        // String printName = "ZDesigner GC420t (EPL)";
        // BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------
        barcodePrinter.setLabelsCount(1);

        barcodePrinter.setCode(item.getCode());

        if (mainBarcode.length() >= 6) {
            barcodePrinter.setBarcode(mainBarcode.substring(mainBarcode.length() - 6));
        } else {
            barcodePrinter.setBarcode(mainBarcode);
        }

        barcodePrinter.setDescription(item.getDescription());
        String position = item.getPosition().substring(2);
        barcodePrinter.setPosition(position);

        barcodePrinter.printSomething(printName);

        return "index";
    }

    //------------//------------//-----------
    @RequestMapping(value = "printMainBarcodeY")
    public String printMainBarcodeΥ(@RequestParam(name = "altercode") String altercode, ModelMap model) {
        System.out.println("Printing Item(Main Barcode)  With Altercode :" + altercode);
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(altercode);
        //-------------
        String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        BarcodePrinter barcodePrinter = new BarcodePrinter();
        // String printName = "ZDesigner GC420t (EPL)";
        // BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------
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
        try {
            String barcodeText = mainBarcode;
            int width = 400; //
            int height = 200; //
            BarcodeFormat barcodeFormat;
            if (mainBarcode.length() == 13 && mainBarcode.matches("\\d+")) {
                barcodeFormat = BarcodeFormat.EAN_13;  // EAN-13 for 13-digit numeric barcodes
            } else if (mainBarcode.length() == 8 && mainBarcode.matches("\\d+")) {
                barcodeFormat = BarcodeFormat.EAN_8;   // EAN-8 for 8-digit numeric barcodes
            } else {
                barcodeFormat = BarcodeFormat.CODE_128; // CODE-128 for alphanumeric or other lengths
            }

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 0);//-+-+2 or 1

            // ✅ Generate high-res barcode
            BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeText, barcodeFormat, width, height, hints);

            // ✅ Convert BitMatrix to Image
            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix, new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF));

            // ✅ Scale the barcode to make bars thicker
            BufferedImage scaledImage = scaleBarcodeImage(barcodeImage, 2.0);  // 2x thicker bars-- go for more if you want

            barcodePrinter.setRead(scaledImage);

            // ✅ Save the scaled barcode
            File outputFile = new File("C:/Pet4U_3.0/barcode.png");//wil not this anymore to print, i hope
            ImageIO.write(scaledImage, "png", outputFile);  // ✅ Now correctly saving the scaled image

            System.out.println("✅ Barcode saved successfully at: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
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

        barcodePrinter.setLabelsCount(1);

        barcodePrinter.setCode(item.getCode());

        if (mainBarcode.length() >= 6) {
            barcodePrinter.setBarcode(mainBarcode.substring(mainBarcode.length() - 6));
        } else {
            barcodePrinter.setBarcode(mainBarcode);
        }

        barcodePrinter.setDescription(item.getDescription());
        String position = item.getPosition().substring(2);
        barcodePrinter.setPosition(position);

        barcodePrinter.printSomething(printName);
        return "index";
    }

    // ✅ This method manually stretches the barcode image to make bars thicker
    private static BufferedImage scaleBarcodeImage(BufferedImage originalImage, double scaleFactor) {
        int newWidth = (int) (originalImage.getWidth() * scaleFactor);
        int newHeight = originalImage.getHeight();

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        // ✅ Use high-quality scaling
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return resizedImage;
    }
//------------------------------

    @RequestMapping(value = "printQRcode")
    public String printQRcode(@RequestParam(name = "altercode") String altercode, ModelMap model) {
        try {
            System.out.println("Altercode :" + altercode);

            SearchDao searchDao = new SearchDao();
            Item item = searchDao.getItemByAltercode(altercode);

            if (item == null) {
                System.out.println("Item Null");
                model.addAttribute("message", "Item is NULL");
                return "errorPage";
            }
            String path = "C:/Pet4U_3.0/barcode.png";
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//generates QR code with Low level(L) error correction capability
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//invoking the user-defined method that creates the QR code
            generateQRcode(altercode, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly
//prints if the QR code is generated

//-------------
            String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
            BarcodePrinter barcodePrinter = new BarcodePrinter();
// String printName = "ZDesigner GC420t (EPL)";
// BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------
            barcodePrinter.setLabelsCount(1);

            barcodePrinter.setCode(item.getCode());
            barcodePrinter.setBarcode(altercode.substring(altercode.length() - 6));
            barcodePrinter.setDescription(item.getDescription());
            String position = item.getPosition().substring(2);
            barcodePrinter.setPosition(position);

            barcodePrinter.printSomething(printName);

        } catch (WriterException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pet4uItemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index";
    }

    //--//--//--//--//--//--//--//
    @RequestMapping(value = "printSmallLabels")
    public String printSmallLabels(@RequestParam(name = "altercode") String altercode, @RequestParam(name = "labelsQuantity") String labelsQuantity, ModelMap model) {
        System.out.println(" Printing Small Labels For Altercode :" + altercode);
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(altercode);

        if (item == null) {
            System.out.println("Item Null");
        }

        OutputStream out = null;
        try {
//this part is for barcode, dont need it here, just keep , maybe will neef later
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

//-------------
        //String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        // BarcodePrinter barcodePrinter = new BarcodePrinter();
        String printName = "ZDesigner GC420t (EPL)";
        BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------

        int labelsCount = Integer.parseInt(labelsQuantity);
        barcodePrinter.setLabelsCount(labelsCount);

        barcodePrinter.setCode(item.getCode());
        barcodePrinter.setBarcode(altercode.substring(altercode.length() - 6));
        barcodePrinter.setDescription(item.getDescription());
        String position = item.getPosition().substring(2);
        barcodePrinter.setPosition(position);

        barcodePrinter.printSomething(printName);

        return "index";
    }

    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
//the BitMatrix class represents the 2D matrix of bits  
//MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.  
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    //-----------------------------+++++++++++++++++++++++++++++++++++++++++----------------
    public void updateItemsStateFullVersion() {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems_NullVersion();
        pet4uItemsDao.insertPet4uItemsSnapshotFullVersion(pet4uAllItems);
    }

    //--------------------------00000000000000000-=====================
    @RequestMapping(value = "goForPositionDeleting")
    public String goForPositionDeleting(@RequestParam(name = "itemId") String itemId,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }

        model.addAttribute("itemId", itemId);
        return "/pet4uItems/positionDeletionConfirmationPage";
    }

    @RequestMapping(value = "deletePet4uItemPositionConfirmed")
    public String deletePet4uItemPositionConfirmed(@RequestParam(name = "itemId") String itemId,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || !userName.equals("me")) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        } else {
            if (userName.equals("me")) {//second lock
                Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
                // String result = pet4uItemsDao.changeItemPosition(itemId, newPositionId);
                String result = pet4uItemsDao.deleteItemPosition(itemId);
                System.out.println(result);
                return "index";
            }
        }
        return "index";
    }

    @RequestMapping(value = "goForPositionChanignX")
    public String goForPositionChanignX(@RequestParam(name = "itemId") String itemId,
            ModelMap model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null || (!userName.equals("me") && !userName.equals("Vasilis"))) {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }

        model.addAttribute("itemId", itemId);
        return "/pet4uItems/positionChanignServantX";
    }

    @RequestMapping(value = "changePet4uItemPositionX", method = RequestMethod.POST)
    public String changePet4uItemPositionX(@RequestParam(name = "itemId") String itemId,
            @RequestParam(name = "row") String row,
            @RequestParam(name = "blockNumber") String blockNumber,
            @RequestParam(name = "positionNumber") String positionNumber,
            ModelMap model) {
        row = row.toUpperCase();
        if (blockNumber.length() == 1) {
            blockNumber = "0" + blockNumber;
        }
        String newPosition = "";
        if (blockNumber.isEmpty() && positionNumber.isEmpty()) {
            newPosition = row;
        } else {
            newPosition = row + "-" + blockNumber + "-" + positionNumber;
        }
        System.out.println("newPosition:" + newPosition);
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Long> allPositions = pet4uItemsDao.getAllPosition();
        if (allPositions.get(newPosition) == null) {
            return "redirect:goForPositionChanign.htm?itemId=" + itemId;
        }
        Long newPositionId = allPositions.get(newPosition);

        model.addAttribute("itemId", itemId);
        model.addAttribute("newPosition", newPosition);
        model.addAttribute("newPositionId", newPositionId);
        return "/pet4uItems/confirmationPage";
    }
    //- - - - - - - - - - 

    @RequestMapping(value = "goForPositionChanign")
    public String goForPositionChanign(@RequestParam(name = "itemId") String itemId, ModelMap model) {
        Pet4uItems_NotActivePositions pet4uItems_NotActivePositions = new Pet4uItems_NotActivePositions();
        ArrayList<String> notActivePositions = pet4uItems_NotActivePositions.getNotActivePositions();

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<Long, String> allPositions = pet4uItemsDao.getAllPosition(notActivePositions);

        model.addAttribute("itemId", itemId);
        model.addAttribute("allPositions", allPositions);
        return "/pet4uItems/positionChanignServant";
    }

    @RequestMapping(value = "changePet4uItemPosition", method = RequestMethod.POST)
    public String changePet4uItemPosition(@RequestParam(name = "itemId") String itemId,
            @RequestParam(name = "newPositionId") String newPositionId,
            ModelMap model) {
        model.addAttribute("itemId", itemId);
        model.addAttribute("newPositionId", newPositionId);
        return "/pet4uItems/confirmationPage";
    }

    @RequestMapping(value = "changePet4uItemPositionConfirmed", method = RequestMethod.POST)
    public String changePet4uItemPositionConfirmed(@RequestParam(name = "itemId") String itemId,
            @RequestParam(name = "newPositionId") String newPositionId,
            ModelMap model, HttpSession session) {

        String userName = (String) session.getAttribute("userName");
        if (userName.equals("me") || userName.equals("Vasilis")) {

            Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
            // String result = pet4uItemsDao.changeItemPosition(itemId, newPositionId);
            String result = pet4uItemsDao.updateItemPosition(itemId, newPositionId);
            System.out.println(result);
            return "index";
        } else {
            System.out.println("Somebody trying to breach encryption");
            return "index";
        }
    }

}
