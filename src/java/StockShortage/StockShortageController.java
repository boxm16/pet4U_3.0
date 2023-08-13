package StockShortage;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import SalesX.SalesDaoX;
import SalesX.SoldItem;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StockShortageController {

    @RequestMapping(value = "dayStockShortageAlert")
    public String dayStockShortageAlert(ModelMap modelMap) {
        LinkedHashMap<String, SoldItem> stockShortage = new LinkedHashMap<>();

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> stocks = pet4uItemsDao.getAllItems();

        SalesDaoX salesDao = new SalesDaoX();
        HashMap<String, SoldItem> itemsWithSales = salesDao.getSixMonthsSalesX();
        for (Map.Entry<String, Item> entrySet : stocks.entrySet()) {
            Item itemWithStock = entrySet.getValue();
            String code = itemWithStock.getCode();
            SoldItem itemWithSales = itemsWithSales.get(code);

            if (itemWithSales == null) {
                //do nothing
            } else {

                double daySales = itemWithSales.getEshopSales() / 365 / 2;
                double stockNow = Double.parseDouble(itemWithStock.getQunatityAsPieces());
                String position = itemWithStock.getPosition();
                if (daySales > 1 && stockNow < daySales && !position.isEmpty() && !position.contains("C-")) {
                    SoldItem shortStockItem = new SoldItem();
                    shortStockItem.setCode(itemWithStock.getCode());
                    shortStockItem.setDescription(itemWithStock.getDescription());
                    shortStockItem.setAltercodes(itemWithStock.getAltercodes());
                    shortStockItem.setPosition(itemWithStock.getPosition());
                    shortStockItem.setQuantity(itemWithStock.getQuantity());
                    shortStockItem.setState(itemWithStock.getState());

                    shortStockItem.setEshopSales(daySales);

                    stockShortage.put(code, shortStockItem);
                }

            }

        }
        modelMap.addAttribute("stockShortage", stockShortage);
        return "stockShortage/dayStockShortageAlert";

    }

}
