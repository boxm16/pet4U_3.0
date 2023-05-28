package Excel;

import Service.Basement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExcelController {

    @Autowired
    private ExcelDao excelDao;

    public void createExcelFileFromDatabaseData() {
        ExcelDao ed=new ExcelDao();
        ArrayList<ExcelItem> excelItems = ed.getExcelItems();
        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();
        String fileName = "pet4uExcelData.xlsx";
        fileName = writeExcel(excelItems, basementDirectory, fileName);
    }

    @RequestMapping(value = "downloadPet4UExcelData")
    public void downloadPet4UExcelData(HttpServletRequest request, HttpServletResponse response) {

        ArrayList<ExcelItem> excelItems = excelDao.getExcelItems();
        Basement basement = new Basement();
        String basementDirectory = basement.getBasementDirectory();
        String fileName = "pet4uExcelData.xlsx";
        fileName = writeExcel(excelItems, basementDirectory, fileName);

        File file = new File(basementDirectory + "/" + fileName);
        downloadFile(file, fileName, response);

    }

    private XSSFCellStyle getRowStyle(SXSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, boolean italic, boolean bold, String format) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
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
        XSSFDataFormat fmts = (XSSFDataFormat) workbook.createDataFormat();
        style.setDataFormat(fmts.getFormat(format));
        return style;
    }

    private String writeExcel(ArrayList<ExcelItem> excelItems, String basementDirectory, String fileName) {
        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(basementDirectory + "/" + fileName)) {

            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet sheet = workbook.createSheet("Pet4U");
            //  int rowIndex = 0;
            //------------------------------------------------------------------
            int rowIndex = 0;
            int columnIndex = 0;
            int columnWidth = 0;

            //column width
            while (columnIndex < 14) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 3000;
                        break;
                    case 1:
                        columnWidth = 5000;
                        break;
                    case 2:
                        columnWidth = 3000;
                        break;
                    case 3:
                        columnWidth = 10000;
                        break;
                    case 4:
                        columnWidth = 3000;
                        break;
                    case 5:
                        columnWidth = 3000;
                        break;

                }
                sheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");

//first header row
            Row headerRow = sheet.createRow(rowIndex);
            columnIndex = 0;
            while (columnIndex < 37) {

                switch (columnIndex) {
                    case 0:
                        Cell cell_0 = headerRow.createCell(columnIndex);
                        cell_0.setCellValue("CODE");
                        cell_0.setCellStyle(rowStyleWhiteRegular);
                        break;
                    case 1:
                        Cell cell_1 = headerRow.createCell(columnIndex);
                        cell_1.setCellValue("ALTERCODE");
                        cell_1.setCellStyle(rowStyleWhiteRegular);
                        break;
                    case 2:
                        Cell cell_2 = headerRow.createCell(columnIndex);
                        cell_2.setCellValue("ALT. Status");
                        cell_2.setCellStyle(rowStyleWhiteRegular);
                        break;
                    case 3:
                        Cell cell_3 = headerRow.createCell(columnIndex);
                        cell_3.setCellValue("Description");
                        cell_3.setCellStyle(rowStyleWhiteRegular);
                        break;
                    case 4:
                        Cell cell_4 = headerRow.createCell(columnIndex);
                        cell_4.setCellValue("POSITION");
                        cell_4.setCellStyle(rowStyleWhiteRegular);
                        break;
                    case 5:
                        Cell cell_5 = headerRow.createCell(columnIndex);
                        cell_5.setCellValue("QUANTITY");
                        cell_5.setCellStyle(rowStyleWhiteRegular);
                        break;
                    case 6:
                        Cell cell_6 = headerRow.createCell(columnIndex);
                        cell_6.setCellValue("State");
                        cell_6.setCellStyle(rowStyleWhiteRegular);
                        break;

                }
                columnIndex++;
            }

            //now rows
            rowIndex++;
            for (ExcelItem excelItem : excelItems) {
                Row row = sheet.createRow(rowIndex);

                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(excelItem.getCode());
                cell_0.setCellStyle(rowStyleWhiteRegular);

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(excelItem.getAltercode());
                cell_1.setCellStyle(rowStyleWhiteRegular);

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(excelItem.getAltercodeStatus());
                cell_2.setCellStyle(rowStyleWhiteRegular);

                Cell cell_3 = row.createCell(3);
                cell_3.setCellValue(excelItem.getDescription());
                cell_3.setCellStyle(rowStyleWhiteRegular);

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(excelItem.getQuantity());
                cell_4.setCellStyle(rowStyleWhiteRegular);

                Cell cell_5 = row.createCell(5);
                cell_5.setCellValue(excelItem.getPosition());
                cell_5.setCellStyle(rowStyleWhiteRegular);

                Cell cell_6 = row.createCell(6);
                cell_6.setCellValue(excelItem.getState());
                cell_6.setCellStyle(rowStyleWhiteRegular);

                rowIndex++;
            }

            workbook.write(os);
            System.out.println("++++ Excel Writing Completed++++");

        } catch (IOException ex) {
            Logger.getLogger(ExcelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    private void downloadFile(File file, String fileName, HttpServletResponse response) {
        try {
            InputStream inputStream = new FileInputStream(file);
            //response.setContentType("application/force-download");
            // response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            response.setContentType("application/ms-excel; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
