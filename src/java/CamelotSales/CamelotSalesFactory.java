/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotSales;

import BasicModel.Item;
import Excel.ExcelReader;
import SalesX.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class CamelotSalesFactory {

    ArrayList<SoldItem> createSoldItemsFromUploadedFile(String filePath, LinkedHashMap<String, Item> camelotAllItems) {
        System.out.println("STARTING READING EXCEL FILE");
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(filePath);
        ArrayList<SoldItem> soldItems = convertExcelDataToSoldItems(cellsFromExcelFile, camelotAllItems);
        System.out.println("READING EXCEL COMPLETED");
        return soldItems;
    }

    private ArrayList<SoldItem> convertExcelDataToSoldItems(HashMap<String, String> data, LinkedHashMap<String, Item> camelotAllItems) {
        ArrayList<SoldItem> items = new ArrayList();
        int rowIndex = 2;
        while (!data.isEmpty()) {

            String itemCodeLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
            String itemCodeString = data.remove(itemCodeLocationInTheRow);//at the same time reading and removing the cell from hash Map

            if (itemCodeString != null && itemCodeString.equals("END")) {//YOU NEED TO PUT THIS WORD AT THE END OF FILE
                break;
            }
            if (camelotAllItems.containsKey(itemCodeString)) {

                String itemEshopSalesLocationInTheRow = new StringBuilder("N").append(String.valueOf(rowIndex)).toString();
                String itemEshopSalesString = data.remove(itemEshopSalesLocationInTheRow);//at the same time reading and removing the cell from hash Map

                SoldItem soldItem = new SoldItem();

                soldItem.setCode(itemCodeString);

                if (itemEshopSalesString == null) {
                    soldItem.setEshopSales(0);
                } else if (itemEshopSalesString.isEmpty() || itemEshopSalesString.equals("")) {
                    soldItem.setEshopSales(0);
                } else {
                    soldItem.setEshopSales(Double.parseDouble(itemEshopSalesString));
                }
                items.add(soldItem);
            }

            rowIndex++;
        }

        return items;
    }

}
