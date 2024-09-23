/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemCodeChanging;

import Excel.ExcelReader;
import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class ItemCodesFactory {

    public TreeMap<String, String> getItemCodesFromExcelFile(String pathFile) {

        System.out.println("STARTING READING EXCEL FILE");
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(pathFile);
        TreeMap<String, String> itemCodes = convertExcelDataToItemCodesData(cellsFromExcelFile);
        System.out.println("COMPLETED READING EXCEL FILE");
        return itemCodes;
    }

    private TreeMap<String, String> convertExcelDataToItemCodesData(HashMap<String, String> cellsFromExcelFile) {
        TreeMap<String, String> itemCodes = new TreeMap<>();
        int excelRowIndex = 1;

        while (!cellsFromExcelFile.isEmpty()) {
            String locationA = new StringBuilder("A").append(String.valueOf(excelRowIndex)).toString();
            String newItemCode = cellsFromExcelFile.remove(locationA);//at the same time reading and removing the cell from hash Map

            String locationD = new StringBuilder("D").append(String.valueOf(excelRowIndex)).toString();
            String oldItemCode = cellsFromExcelFile.remove(locationD);//at the same time reading and removing the cell from hash Map
            if (oldItemCode == null) {//in theory this means that you reached the end of rows with data
                break;
            }

            itemCodes.put(oldItemCode, newItemCode);
            excelRowIndex++;
        }
        return itemCodes;
    }
}
