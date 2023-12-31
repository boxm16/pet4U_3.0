package Endo;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    private static Map<String, String> contactMap = new HashMap<String, String>();

    static {
        contactMap.put("name", "John");
        contactMap.put("lastname", "Lennon");
        contactMap.put("genres", "Rock, Pop");
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView get() {

        ContactForm contactForm = new ContactForm();
        contactForm.setContactMap(contactMap);

        return new ModelAndView("add_contact", "contactForm", contactForm);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("contactForm") ContactForm contactForm) {

        return new ModelAndView("show_contact", "contactForm", contactForm);
    }
}
