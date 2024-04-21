/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CamelotComparingAnalysis;

import Excel.ExcelReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class Factory {

    LinkedHashMap<String, SoldItem3> createSoldItemsFromUploadedFile(String filePath, LinkedHashMap<String, SoldItem3> camelotItemsForSales) {
        System.out.println("STARTING READING EXCEL FILE");
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(filePath);
        LinkedHashMap<String, SoldItem3> soldItems = convertExcelDataToSoldItems(cellsFromExcelFile, camelotItemsForSales);
        System.out.println("READING EXCEL COMPLETED");
        return soldItems;
    }

    private LinkedHashMap<String, SoldItem3> convertExcelDataToSoldItems(HashMap<String, String> data, LinkedHashMap<String, SoldItem3> camelotItemsForSales) {

        int rowIndex = 2;
        while (!data.isEmpty()) {
            String shopNameLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
            String shopNameString = data.remove(shopNameLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemCodeLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
            String itemCodeString = data.remove(itemCodeLocationInTheRow);//at the same time reading and removing the cell from hash Map

            if (itemCodeString != null && itemCodeString.equals("END")) {//YOU NEED TO PUT THIS WORD AT THE END OF FILE
                break;
            }
            if (camelotItemsForSales.containsKey(itemCodeString)) {

                String pet4uSalesLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
                String pet4uSalesString = data.remove(pet4uSalesLocationInTheRow);//at the same time reading and removing the cell from hash Map

                SoldItem3 soldItem = camelotItemsForSales.get(itemCodeString);

                if (pet4uSalesString.equals("ΝΕΑ ΕΡΥΘΡΑΙΑ")) {
                    System.out.println(shopNameString);
                    soldItem.setVaribobiSales(Double.parseDouble(pet4uSalesString));
                } else {
                    System.out.println(shopNameString);
                }
                camelotItemsForSales.put(soldItem.getCode(), soldItem);
            } else {
                System.out.println("BAD THING");
            }

            rowIndex++;
        }

        return camelotItemsForSales;
    }
}
