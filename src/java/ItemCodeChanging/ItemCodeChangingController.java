/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemCodeChanging;

import Service.Basement;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class ItemCodeChangingController {

    @RequestMapping(value = "itemCodeChangingDashboard")
    public String itemCodeChangingDashboard() {

        return "/itemCodeChanging/itemCodeChangingDashboard";
    }

    @RequestMapping(value = "changeItemCode")
    public String changeItemCode(@RequestParam(name = "oldItemCode") String oldItemCode, @RequestParam(name = "newItemCode") String newItemCode, ModelMap modelMap) {
        String result = "";
        ItemCodeChangingDao itemCodeChangingDao = new ItemCodeChangingDao();
        String result1 = itemCodeChangingDao.changeItemCodeIn("camelot_day_rest", oldItemCode, newItemCode);

        String result2 = itemCodeChangingDao.changeItemCodeIn("camelot_day_rest_full_version", oldItemCode, newItemCode);

        String result3 = itemCodeChangingDao.changeItemCodeIn("camelot_interest", oldItemCode, newItemCode);

        String result4 = itemCodeChangingDao.changeItemIn("camelot_month_sales", oldItemCode, newItemCode);

        String result5 = itemCodeChangingDao.changeItemCodeIn("camelot_notes", oldItemCode, newItemCode);

        String result6 = itemCodeChangingDao.changeItemCodeIn("camelot_stock_positions", oldItemCode, newItemCode);

        String result7 = itemCodeChangingDao.changeItemCodeIn("delivery_data", oldItemCode, newItemCode);

        String result8 = itemCodeChangingDao.changeItemCodeIn("endo", oldItemCode, newItemCode);

        String result9 = itemCodeChangingDao.changeItemCodeIn("endo_locker_data", oldItemCode, newItemCode);

        String result10 = itemCodeChangingDao.changeItemCodeIn("endo_order_data", oldItemCode, newItemCode);

        String result11 = itemCodeChangingDao.changeItemCodeIn("endo_packaging", oldItemCode, newItemCode);

        String result12 = itemCodeChangingDao.changeItemCodeIn("inventory", oldItemCode, newItemCode);

        String result13 = itemCodeChangingDao.changeItemCodeIn("item_state", oldItemCode, newItemCode);

        String result14 = itemCodeChangingDao.changeItemCodeIn("item_state_full_version", oldItemCode, newItemCode);

        String result15 = itemCodeChangingDao.changeItemIn("month_sales", oldItemCode, newItemCode);

        String result16 = itemCodeChangingDao.changeItemCodeIn("not_for_endo", oldItemCode, newItemCode);

        String result17 = itemCodeChangingDao.changeItemCodeIn("notes", oldItemCode, newItemCode);

        String result18 = itemCodeChangingDao.changeItemCodeIn("offers", oldItemCode, newItemCode);

        String result19 = itemCodeChangingDao.changeItemCodeIn("pet4u_stock_snapshot", oldItemCode, newItemCode);

        String result20 = itemCodeChangingDao.changeItemCodeIn("royal_stock_management", oldItemCode, newItemCode);

        String result21 = itemCodeChangingDao.changeItemIn("sales", oldItemCode, newItemCode);

        String result22 = itemCodeChangingDao.changeItemIn("sales_x", oldItemCode, newItemCode);

        String result23 = itemCodeChangingDao.changeItemCodeIn("shelves_replenishment", oldItemCode, newItemCode);

        String result24 = itemCodeChangingDao.changeItemCodeIn("stock_management", oldItemCode, newItemCode);
        //---------------

        result = result1 + "<br>"
                + result2 + "<br>"
                + result3 + "<br>"
                + result4 + "<br>"
                + result5 + "<br>"
                + result6 + "<br>"
                + result7 + "<br>"
                + result8 + "<br>"
                + result9 + "<br>"
                + result10 + "<br>"
                + result11 + "<br>"
                + result12 + "<br>"
                + result13 + "<br>"
                + result14 + "<br>"
                + result15 + "<br>"
                + result16 + "<br>"
                + result17 + "<br>"
                + result18 + "<br>"
                + result19 + "<br>"
                + result20 + "<br>"
                + result21 + "<br>"
                + result22 + "<br>"
                + result23 + "<br>"
                + result24 + "<br>";
        modelMap.addAttribute("result", result);
        return "/itemCodeChanging/itemCodeChangingDashboard";
    }

    @RequestMapping(value = "/changeItemCodes", method = RequestMethod.POST)
    public String uploadPlannedData(@RequestParam CommonsMultipartFile file, ModelMap model) {

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Starting working on uploaded excel file ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        String filename = "itemCodesChanging.xlsx";
        Basement basement = new Basement();
        String filePath = basement.getBasementDirectory() + "/uploads/" + filename;
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "არცერთი ფაილი არ იყო არჩეული");
            return "upload";
        }
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("uploadStatus", "Upload could not been completed:" + e);
            return "upload";
        }
        //--------------- Starting new Thread For Upload-----------------------

        return "/itemCodeChanging/itemCodeChangingDashboard";
    }
}
