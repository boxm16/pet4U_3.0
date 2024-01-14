/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.util.LinkedHashMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class BindedEndos {

    private String bindingReceivingEndoId;
    private Endo bindingReceivingEndo;

    private LinkedHashMap<String, Endo> bindedSendingEndos;

    public BindedEndos() {
        this.bindedSendingEndos = new LinkedHashMap();
    }

    public String getBindingReceivingEndoId() {
        return bindingReceivingEndoId;
    }

    public void setBindingReceivingEndoId(String bindingReceivingEndoId) {
        this.bindingReceivingEndoId = bindingReceivingEndoId;
    }

    public Endo getBindingReceivingEndo() {
        return bindingReceivingEndo;
    }

    public void setBindingReceivingEndo(Endo bindingReceivingEndo) {
        this.bindingReceivingEndo = bindingReceivingEndo;
    }

    public LinkedHashMap<String, Endo> getBindedSendingEndos() {
        return bindedSendingEndos;
    }

    public void setBindedSendingEndos(LinkedHashMap<String, Endo> bindedSendingEndos) {
        this.bindedSendingEndos = bindedSendingEndos;
    }

    public void addBindedEndo(String id, Endo bindedEndo) {
        this.bindedSendingEndos.put(id, bindedEndo);
    }

}
