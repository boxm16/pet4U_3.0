/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesX;

import Excel.ExcelReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class SalesFactory {

    public ArrayList<SoldItem> createSoldItemsFromUploadedFile(String filePath) {
        System.out.println("STARTING READING EXCEL FILE");
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(filePath);
        ArrayList<SoldItem> soldItems = convertExcelDataToSoldItems(cellsFromExcelFile);
        System.out.println("READING EXCEL COMPLETED");
        return soldItems;
    }

    private ArrayList<SoldItem> convertExcelDataToSoldItems(HashMap<String, String> data) {
        ArrayList<SoldItem> items = new ArrayList();
        int rowIndex = 2;
        while (!data.isEmpty()) {

            String itemCodeLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
            String itemCodeString = data.remove(itemCodeLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemDescriptionLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
            String itemDescriptionString = data.remove(itemDescriptionLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemEshopSalesLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String itemEshopSalesString = data.remove(itemEshopSalesLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemShopSupplyLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String itemShopSupplyString = data.remove(itemShopSupplyLocationInTheRow);//at the same time reading and removing the cell from hash Map

           
            if (itemCodeString == null) {//in theory this means that you reached the end of rows with data
                break;
            }

            SoldItem soldItem = new SoldItem();

            soldItem.setCode(itemCodeString);

            soldItem.setDescription(itemDescriptionString);

            if (itemEshopSalesString == null) {
                soldItem.setEshopSales(0);
            } else if (itemEshopSalesString.isEmpty() || itemEshopSalesString.equals("")) {
                soldItem.setEshopSales(0);
            } else {
                soldItem.setEshopSales(Double.parseDouble(itemEshopSalesString));
            }

            if (itemShopSupplyString == null) {
                soldItem.setShopsSupply(0);
            } else if (itemShopSupplyString.isEmpty() || itemShopSupplyString.equals("")) {
                soldItem.setShopsSupply(0);
            } else {
                soldItem.setShopsSupply(Double.parseDouble(itemShopSupplyString));
            }

           
            items.add(soldItem);
            rowIndex++;
        }

        return items;
    }
}
