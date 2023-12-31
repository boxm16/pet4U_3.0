/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.util.HashMap;
import java.util.Map;

public class ContactForm {

    private Map<String, String> contactMap = new HashMap<String, String>();

    public Map<String, String> getContactMap() {
        return contactMap;
    }

    public void setContactMap(Map<String, String> contactMap) {
        this.contactMap = contactMap;
    }
}
