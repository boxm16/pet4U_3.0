/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Synchronization;

import BasicModel.AltercodeContainer;
import BasicModel.Item;
import CamelotItemsOfInterest.CamelotItemsOfInterestDao;
import Pet4uItems.Pet4uItemsDao;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SynchronizationController {

    //------------------here is part for position update
    @RequestMapping(value = "synchronizePositions")
    public String synchronizePositions(@RequestParam(name = "code") String code, ModelMap model) {
        System.out.println("CODE" + code);

        SynchronizationDao synchronizationDao = new SynchronizationDao();
        String camelotPosition = synchronizationDao.getCamelotItemPosition(code);
        System.out.println("CAMLEOT POSITON:" + camelotPosition);
        camelotPosition = "C-" + camelotPosition;
        System.out.println("Pet4U POSITON:" + camelotPosition);

        int positionId = synchronizationDao.getPet4UPositionId(camelotPosition);
        System.out.println("ID:" + positionId);

        String result = synchronizationDao.updatePet4UItemPosition(code, positionId);
        System.out.println("Result:" + result);
        return "redirect:camelotItemsWithPossitionDifference.htm";
    }

    @RequestMapping(value = "synchronizeAllPositions")
    public String synchronizeAllPositions() {

        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uItems = pet4uItemsDao.getAllItems();

        CamelotItemsOfInterestDao camelotItemsOfInterestDao = new CamelotItemsOfInterestDao();
        LinkedHashMap<String, Item> camelotItemsRowByRow = camelotItemsOfInterestDao.getCamelotItemsRowByRow();

        for (Map.Entry<String, Item> pet4uItemsEntry : pet4uItems.entrySet()) {
            ArrayList<AltercodeContainer> altercodes = pet4uItemsEntry.getValue().getAltercodes();
            for (AltercodeContainer altercode : altercodes) {
                String camelotVersionAltercode = altercode.getAltercode();
                if (altercode.getAltercode().contains("-WE")) {
                    camelotVersionAltercode = altercode.getAltercode().replace("-WE", "");
                }
                if (camelotItemsRowByRow.containsKey(camelotVersionAltercode)) {
                    Item camelotItem = camelotItemsRowByRow.get(camelotVersionAltercode);
                    Item pet4uItem = pet4uItemsEntry.getValue();
                    String c_position = "C-" + camelotItem.getPosition();
                    if (!camelotItem.getPosition().isEmpty()) {
                        if (pet4uItem.getPosition().contains("C-") || pet4uItem.getPosition().isEmpty()) {
                            Double q = Double.parseDouble(camelotItem.getQuantity());
                            if (q > 0) {
                                if (!pet4uItem.getPosition().equals(c_position)) {

                                    //-----------
                                    System.out.println("CODE" + pet4uItem.getCode());

                                    SynchronizationDao synchronizationDao = new SynchronizationDao();
                                    String camelotPosition = synchronizationDao.getCamelotItemPosition(pet4uItem.getCode());
                                    System.out.println("CAMLEOT POSITON:" + camelotPosition);
                                    camelotPosition = "C-" + camelotPosition;
                                    System.out.println("Pet4U POSITON:" + camelotPosition);

                                    int positionId = synchronizationDao.getPet4UPositionId(camelotPosition);
                                    System.out.println("ID:" + positionId);

                                    String result = synchronizationDao.updatePet4UItemPosition(pet4uItem.getCode(), positionId);
                                    System.out.println("Result:" + result);
                                    //----------------

                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return "redirect:camelotItemsWithPossitionDifference.htm";
    }

}
