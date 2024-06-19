/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import Excel.ExcelReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class EndoOrdersFactory {

    LinkedHashMap<String, EndoOrder> createEndoOrdersFromUploadedFile(String filePath) {
        System.out.println("STARTING READING EXCEL FILE");
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> cellsFromExcelFile = excelReader.getCellsFromExcelFile(filePath);
        LinkedHashMap<String, EndoOrder> endoOrders = convertExcelDataToEndoOrders(cellsFromExcelFile);
        System.out.println("READING EXCEL COMPLETED");
        return endoOrders;
    }

    private LinkedHashMap<String, EndoOrder> convertExcelDataToEndoOrders(HashMap<String, String> data) {
        LinkedHashMap<String, EndoOrder> endoOrders = new LinkedHashMap<String, EndoOrder>();
        int rowIndex = 2;
        while (!data.isEmpty()) {

            String itemCodeLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
            String itemCodeString = data.remove(itemCodeLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemDescriptionLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
            String itemDescriptionString = data.remove(itemDescriptionLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String orderedQuantityLocationInTheRow = new StringBuilder("G").append(String.valueOf(rowIndex)).toString();
            String orderedQuantityString = data.remove(orderedQuantityLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String commentLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
            String commentString = data.remove(commentLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String destinationLocationInTheRow = new StringBuilder("K").append(String.valueOf(rowIndex)).toString();
            String destinationString = data.remove(destinationLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String priceLocationInTheRow = new StringBuilder("L").append(String.valueOf(rowIndex)).toString();
            String priceString = data.remove(priceLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String amountLocationInTheRow = new StringBuilder("M").append(String.valueOf(rowIndex)).toString();
            String amountString = data.remove(amountLocationInTheRow);//at the same time reading and removing the cell from hash Map

            String itemBarcodeLocationInTheRow = new StringBuilder("N").append(String.valueOf(rowIndex)).toString();
            String itemBarcodeString = data.remove(itemBarcodeLocationInTheRow);//at the same time reading and removing the cell from hash Map

            if (itemCodeString == null) {//in theory this means that you reached the end of rows with data
                break;
            }

            if (itemCodeString.charAt(0) == '-') {
                itemCodeString = itemCodeString.substring(1);
            }

            if (endoOrders.containsKey(destinationString)) {
                EndoOrder endoOrder = endoOrders.get(destinationString);
                EndoOrderItem orderedItem = new EndoOrderItem();

                orderedItem.setCode(itemCodeString);

                orderedItem.setItemBarcode(itemBarcodeString);

                orderedItem.setDescription(itemDescriptionString);

                orderedItem.setQuantity(orderedQuantityString);

                orderedItem.setOrderedQuantity(Double.parseDouble(orderedQuantityString));

                orderedItem.setSentQuantity(Double.parseDouble(orderedQuantityString));

                orderedItem.setPrice(Double.parseDouble(priceString));

                orderedItem.setAmount(Double.parseDouble(amountString));

                orderedItem.setComment(commentString);

                endoOrder.addOrderItem(itemCodeString, orderedItem);

                endoOrders.put(destinationString, endoOrder);
            } else {
                EndoOrder endoOrder = new EndoOrder();

                LocalDateTime timeNow = LocalDateTime.now();
                endoOrder.setId(destinationString + ":" + timeNow.toString());

                endoOrder.setDestination(destinationString);

                EndoOrderItem orderedItem = new EndoOrderItem();

                orderedItem.setCode(itemCodeString);

                orderedItem.setItemBarcode(itemBarcodeString);

                orderedItem.setDescription(itemDescriptionString);

                orderedItem.setQuantity(orderedQuantityString);

                orderedItem.setOrderedQuantity(Double.parseDouble(orderedQuantityString));

                orderedItem.setSentQuantity(Double.parseDouble(orderedQuantityString));

                orderedItem.setPrice(Double.parseDouble(priceString));

                orderedItem.setAmount(Double.parseDouble(amountString));

                orderedItem.setComment(commentString);

                endoOrder.addOrderItem(itemCodeString, orderedItem);

                endoOrders.put(destinationString, endoOrder);

            }

            rowIndex++;
        }

        return endoOrders;
    }

}
