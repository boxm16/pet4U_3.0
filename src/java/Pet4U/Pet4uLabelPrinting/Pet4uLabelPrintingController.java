/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pet4U.Pet4uLabelPrinting;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsController;
import static Pet4uItems.Pet4uItemsController.generateQRcode;
import Search.SearchDao;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Pet4uLabelPrintingController {

    @RequestMapping(value = "labelPrintingDashboard")
    public String labelPrintingDashboard(ModelMap model) {
        model.addAttribute("psliarlAutofocus", "autofocus");
        model.addAttribute("piclAutofocus", "");
        return "pet4u/labelPrinting/labelPrintingDashboard";
    }

    @RequestMapping(value = "printSmallLabelsInARow")
    public String printSmallLabelsInARow(@RequestParam(name = "altercode") String altercode, ModelMap model) {

        System.out.println(" Printing Small Labels For Altercode :" + altercode);
        SearchDao searchDao = new SearchDao();
        Item item = searchDao.getItemByAltercode(altercode);

        if (item == null) {
            model.addAttribute("message", "Item with  altercode " + altercode + " could not be found");
            return "erroPage";
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
                    Logger.getLogger(Pet4uLabelPrintingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Pet4uLabelPrintingController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // model.addAttribute("code", code);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pet4uLabelPrintingController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Pet4uLabelPrintingController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

//-------------
        //String printName = "\\\\eshoplaptop\\ZDesigner GC420t (EPL) (Αντιγραφή 1)";
        // BarcodePrinter barcodePrinter = new BarcodePrinter();
        String printName = "ZDesigner GC420t (EPL)";
        SmallLabelPrinter smallLabelPrinter = new SmallLabelPrinter();
//---------------

        int labelsCount = 1;
        smallLabelPrinter.setLabelsCount(labelsCount);

        if (altercode.length() > 6) {
            altercode = altercode.substring(altercode.length() - 6);
        }
        String mainBarcode = item.getMainBarcode();
        if (mainBarcode == null) {
            mainBarcode = "";
        }
        if (mainBarcode.length() > 6) {
            mainBarcode = mainBarcode.substring(mainBarcode.length() - 6);
        }
        smallLabelPrinter.setCode(altercode);
        smallLabelPrinter.setBarcode(mainBarcode);

        String position = item.getPosition().substring(2);
        smallLabelPrinter.setPosition(position);

        smallLabelPrinter.printSomething(printName);

        model.addAttribute("psliarlAutofocus", "autofocus");
        model.addAttribute("piclAutofocus", "");

        return "pet4u/labelPrinting/labelPrintingDashboard";
    }

    @RequestMapping(value = "printItemCodeLabel")
    public String printItemCodeLabel(@RequestParam(name = "altercode") String altercode, ModelMap model) {

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
        ItemCodeLablePrinter barcodePrinter = new ItemCodeLablePrinter();
        // String printName = "ZDesigner GC420t (EPL)";
        // BarcodePrinter2 barcodePrinter = new BarcodePrinter2();
//---------------
        barcodePrinter.setLabelsCount(1);

        if (mainBarcode.length() >= 6) {
            barcodePrinter.setBarcode(mainBarcode.substring(mainBarcode.length() - 6));
        } else {
            barcodePrinter.setBarcode(mainBarcode);
        }

        barcodePrinter.setDescription(item.getDescription());
        //I change position and item code here, to print accordingly

        String itemCode = item.getCode();
        if (itemCode.length() > 8) {
            itemCode = itemCode.substring(0, 5) + "/" + itemCode.substring(itemCode.length() - 3);
        }
        barcodePrinter.setPosition(itemCode);
        String position = item.getPosition().substring(2);
        barcodePrinter.setCode(position);

        barcodePrinter.printSomething(printName);

        model.addAttribute("psliarlAutofocus", "");
        model.addAttribute("piclAutofocus", "autofocus");

        return "pet4u/labelPrinting/labelPrintingDashboard";
    }
}
