package TESTosteron;

import BasicModel.Item;
import Pet4uItems.Pet4uItemsDao;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TESTosteronController {

    @RequestMapping(value = "testosteronDashboard")
    public String testosteronDashboard() {

        return "testosteron/testosteronDashboard";
    }

    @RequestMapping(value = "testViewAndDeepSearch")
    public String testViewAndDeepSearch() {
        Pet4uItemsDao pet4uItemsDao = new Pet4uItemsDao();
        LinkedHashMap<String, Item> pet4uAllItems = pet4uItemsDao.getAllItems();

        TESTosteronDao testosteronDao = new TESTosteronDao();
        LinkedHashMap<String, Item> allPet4UItemsWithDeepSearch = testosteronDao.getAllPet4UItemsWithDeepSearch();
        for (Map.Entry<String, Item> pet4uAllItemsEntry : pet4uAllItems.entrySet()) {
            String codeEx = pet4uAllItemsEntry.getKey();
            Item value1 = pet4uAllItemsEntry.getValue();
            Item v2 = allPet4UItemsWithDeepSearch.remove(codeEx);
            if (v2 == null) {
                System.out.println("SHOUT, NULL AGAIN " + codeEx + "--" + value1.getDescription() + "++" + value1.getPosition());

            } else {
                if (!value1.getQuantity().equals(v2.getQuantity())) {
                    System.out.println("QYT " + codeEx + "--" + value1.getDescription() + "++" + value1.getQuantity() + "X" + v2.getQuantity());

                }
            }

        }

        System.out.println("LEFT OVERS: " + allPet4UItemsWithDeepSearch.size());
        for (Map.Entry<String, Item> pet4uAllItemsEntry : allPet4UItemsWithDeepSearch.entrySet()) {
            System.out.println(pet4uAllItemsEntry.getValue().getCode()
                    + "="
                    + pet4uAllItemsEntry.getValue().getDescription()
                    + "-"
                    + pet4uAllItemsEntry.getValue().getPosition()
                    + "+"
                    + pet4uAllItemsEntry.getValue().getPosition());

        }
        return "testosteron/testResult";
    }
}
