package SuppliersAndStock;

import Service.Basement;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderExcelDownloadController {

    @Autowired
    private SupplierDao supplierDao;

    @RequestMapping(value = "downlodOrderInExcelFormat")
    public String downlodOrderInExcelFormat(@RequestParam("supplierId") String supplierId, @RequestParam("orderedItemsData") String orderedItemsData, ModelMap model) {
        Supplier supplier = this.supplierDao.getSupplier(supplierId);

        ArrayList<SuppliersItem> orderedItems = createOrderedItemsArray(orderedItemsData);

        LinkedHashMap<String, SuppliersItem> allItemsOfSupplier = this.supplierDao.getAllItemsOfSupplier(supplierId);

        for (SuppliersItem orderedItem : orderedItems) {
            SuppliersItem itemWithDescription = allItemsOfSupplier.get(orderedItem.getCode());
            if (itemWithDescription == null) {
                //do nothing
            } else {
                orderedItem.setDescription(itemWithDescription.getDescription());
            }

        }
        exportOrderedItems(orderedItems, supplierId);

        model.addAttribute("supplier", supplier);
       
        return "redirect:suppliersAndStockDashboard.htm";
    }

    private ArrayList<SuppliersItem> createOrderedItemsArray(String orderedItemsData) {
        ArrayList<SuppliersItem> orderedItems = new ArrayList();
        //trimming and cleaning input
        orderedItemsData = orderedItemsData.trim();
        if (orderedItemsData.length() == 0) {
            return new ArrayList<>();
        }
        if (orderedItemsData.substring(orderedItemsData.length() - 1, orderedItemsData.length()).equals(",")) {
            orderedItemsData = orderedItemsData.substring(0, orderedItemsData.length() - 1).trim();
        }
        String[] orderedItemsArray = orderedItemsData.split(",");
        for (String orderedItemData : orderedItemsArray) {
            String[] orderedItemCodeAndQuantity = orderedItemData.split(":");
            SuppliersItem suppliersItem = new SuppliersItem();
            suppliersItem.setCode(orderedItemCodeAndQuantity[0]);
            suppliersItem.setOrderQuantity(Integer.parseInt(orderedItemCodeAndQuantity[1]));
            orderedItems.add(suppliersItem);
        }

        return orderedItems;

    }

    void exportOrderedItems(ArrayList<SuppliersItem> orderedItems, String fileName) {

        Instant start = Instant.now();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Nestle Order");
//setting date 1904 system (to show negative duration in excel workbook)
        workbook.getCTWorkbook().getWorkbookPr().setDate1904(true);

        int rowIndex = 0;
        int columnIndex = 0;
        int rowHeigth = 0;
        int columnWidth = 0;

        //column width
        //  sheet.setDefaultColumnWidth(2);
        while (columnIndex < 7) {
            switch (columnIndex) {
                case 0:
                    columnWidth = 3000;
                    break;
                case 1:
                    columnWidth = 3000;
                    break;
                case 2:
                    columnWidth = 10000;
                    break;
                case 3:
                    columnWidth = 1000;
                    break;
                case 4:
                    columnWidth = 2800;
                    break;
                case 5:
                    columnWidth = 2800;
                    break;
                case 6:
                    columnWidth = 2800;
                    break;
            }
            sheet.setColumnWidth(columnIndex, columnWidth);
            columnIndex++;
        }
        //now headers
        //first header row
        Row headerRow1 = sheet.createRow(rowIndex);
        rowHeigth = 80;
        headerRow1.setHeightInPoints(rowHeigth);

        XSSFCellStyle headerStyle = getHeaderStyle(workbook, 209, 112, 247, 0, false);

        columnIndex = 0;
        while (columnIndex < 7) {
            Cell cell = headerRow1.createCell(columnIndex);
            switch (columnIndex) {
                case 0:
                    cell.setCellValue("Κωδικός Προϊόντος");
                    break;
                case 1:
                    cell.setCellValue("Κωδικός ERP");
                    break;
                case 2:
                    cell.setCellValue("Περιγραφή Προϊόντος");
                    break;
                case 3:
                    cell.setCellValue("Παραγγελία");
                    break;
                case 4:
                    cell.setCellValue("Κατάστημα");
                    break;
                case 5:
                    cell.setCellValue("Κωδικός Καταστήματος");
                    break;
            }

            columnIndex++;
        }

        //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
        XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");

        rowHeigth = 30;
        for (SuppliersItem orderedItem : orderedItems) {

            Row row = sheet.createRow(++rowIndex);
            row.setHeightInPoints(rowHeigth);

            Cell cell_0 = row.createCell(0);
            cell_0.setCellValue(orderedItem.getCode());
            cell_0.setCellStyle(rowStyleWhiteRegular);

            Cell cell_1 = row.createCell(1);
            cell_1.setCellValue("");
            cell_1.setCellStyle(rowStyleWhiteRegular);

            Cell cell_2 = row.createCell(2);
            cell_2.setCellValue(orderedItem.getDescription());
            cell_2.setCellStyle(rowStyleWhiteRegular);

            Cell cell_3 = row.createCell(3);
            cell_3.setCellValue(orderedItem.getOrderQuantity());
            cell_2.setCellStyle(rowStyleWhiteRegular);

            Cell cell_4 = row.createCell(4);
            cell_4.setCellValue("");
            cell_4.setCellStyle(rowStyleWhiteRegular);

            Cell cell_5 = row.createCell(5);
            cell_5.setCellValue("");
            cell_5.setCellStyle(rowStyleWhiteRegular);

            Cell cell_6 = row.createCell(6);
            cell_6.setCellValue("");
            cell_5.setCellStyle(rowStyleWhiteRegular);

        }
        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();
        try (FileOutputStream outputStream = new FileOutputStream(basementDirectory + "/downloads/" + fileName + ".xls")) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(OrderExcelDownloadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Instant end = Instant.now();
        System.out.println("Excel Export completed. Time needed:" + Duration.between(start, end));

    }

    private XSSFCellStyle getHeaderStyle(XSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, int rotation, boolean bold) {
        XSSFCellStyle style = workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setRotation((short) rotation);

        style.setWrapText(true);

        style.setRotation((short) rotation);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // font
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Sylfaen");
        font.setBold(bold);
        style.setFont(font);

        return style;
    }

    private XSSFCellStyle getRowStyle(XSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, boolean italic, boolean bold, String format) {

        XSSFCellStyle style = workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setWrapText(true);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // font
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Sylfaen");
        font.setItalic(italic);
        font.setBold(bold);
        style.setFont(font);

        //time formats "[hh]:mm"   "[mm]:ss"
        XSSFDataFormat fmts = workbook.createDataFormat();
        style.setDataFormat(fmts.getFormat(format));
        return style;
    }
}
