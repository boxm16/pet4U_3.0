package Endo;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EndoControllerX {

    @RequestMapping(value = "endoParalaves", method = RequestMethod.GET)
    public String endoParalaves(ModelMap modelMap) {
        EndoDaoX endoDaoX = new EndoDaoX();
        LinkedHashMap<String, EndoBinder> allEndoBinders = endoDaoX.getAllEndoBinders();

        LinkedHashMap<String, EndoApostolis> endoApostoliss = endoDaoX.getLastIncomingEndoApostoliss(7);
        LinkedHashMap<String, EndoParalavis> endoParalaviss = endoDaoX.getLastEndoParalaviss(7);

        Iterator<Entry<String, EndoParalavis>> endoParalavissIterator = endoParalaviss.entrySet().iterator();

        while (endoParalavissIterator.hasNext()) {
            Entry<String, EndoParalavis> endoParalavisEntry = endoParalavissIterator.next();
            String endoParalavisId = endoParalavisEntry.getKey();
            if (allEndoBinders.containsKey(endoParalavisId)) {
                endoParalavissIterator.remove();
                EndoBinder endoBinder = allEndoBinders.get(endoParalavisId);
                LinkedHashMap<String, EndoApostolis> enAps = endoBinder.getEndosApostolis();
                for (Map.Entry<String, EndoApostolis> enApEntry : enAps.entrySet()) {
                    if (endoApostoliss.containsKey(enApEntry.getKey())) {
                        endoApostoliss.remove(enApEntry.getKey());
                    }
                }
            }
        }

        modelMap.addAttribute("incomingEndos", endoApostoliss);
        modelMap.addAttribute("receivingEndos", endoParalaviss);

        return "endo/endoParalaves";
    }

}