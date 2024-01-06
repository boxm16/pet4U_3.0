/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endo;

import java.util.ArrayList;

/**
 *
 * @author Michail Sitmalidis
 */
public class BindedEndos {

    private String bindingReceivingEndoId;
    private Endo bindingReceivingEndo;
    private ArrayList<Endo> bindedSendingEndos;

    public BindedEndos() {
        bindedSendingEndos = new ArrayList<Endo>();
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

    public ArrayList<Endo> getBindedSendingEndos() {
        return bindedSendingEndos;
    }

    public void setBindedSendingEndos(ArrayList<Endo> bindedSendingEndos) {
        this.bindedSendingEndos = bindedSendingEndos;
    }

    public void addBindedSendingEndo(Endo sendingEndo) {
        this.bindedSendingEndos.add(sendingEndo);
    }

}
