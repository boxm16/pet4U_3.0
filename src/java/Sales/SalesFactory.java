/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

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

            String itemMeasureUnitLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String itemMeasureUnitString = data.remove(itemMeasureUnitLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemEshopSalesLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String itemEshopSalesString = data.remove(itemEshopSalesLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemShopSupplyLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
            String itemShopSupplyString = data.remove(itemShopSupplyLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemTotalSalesLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
            String itemTotalSalesString = data.remove(itemTotalSalesLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String coeficientLocationInTheRow = new StringBuilder("G").append(String.valueOf(rowIndex)).toString();
            String coeficinetString = data.remove(coeficientLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemTotalSalesInPiecesLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
            String itemTotalSalesInPiecesString = data.remove(itemTotalSalesInPiecesLocationInTheRow);//at the same time reading and removing the cell from hash Map

            if (itemCodeString == null) {//in theory this means that you reached the end of rows with data
                break;
            }

            SoldItem soldItem = new SoldItem();

            soldItem.setCode(itemCodeString);

            soldItem.setDescription(itemDescriptionString);
            soldItem.setMeasureUnit(itemMeasureUnitString);
            soldItem.setEshopSales(Integer.parseInt(itemEshopSalesString));
            soldItem.setShopsSupply(Integer.parseInt(itemShopSupplyString));
            soldItem.setTotalSales(Integer.parseInt(itemTotalSalesString));
            soldItem.setCoeficient(Integer.parseInt(coeficinetString));
            soldItem.setTotalSalesInPieces(Integer.parseInt(itemTotalSalesInPiecesString));
            items.add(soldItem);
            rowIndex++;
        }

        return items;
    }
}
