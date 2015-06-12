/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Users;
import com.lib.Dao;
import com.util.Encrypt;
import com.util.SessionUtils;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class ChangePasswordBean {

    private String oldPas;
    private String newPass;

    /**
     * Creates a new instance of ChangePasswordBean
     */
    public ChangePasswordBean() {
    }

    public void changePassword(ActionEvent event) {
        String msg;
        FacesMessage fmsg;
        Users u = (Users) SessionUtils.getSession().getAttribute("login");
        String passEn = Encrypt.md5(oldPas);
        if (u.getUPassword().equals(passEn)) {
            u.setUPassword(Encrypt.md5(newPass));
            Dao dao = new Dao();
            dao.update(u);
            SessionUtils.getSession().setAttribute("login", u);
            msg = "Change password successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);

        } else {
            msg = "Old password incorrect";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the oldPas
     */
    public String getOldPas() {
        return oldPas;
    }

    /**
     * @param oldPas the oldPas to set
     */
    public void setOldPas(String oldPas) {
        this.oldPas = oldPas;
    }

    /**
     * @return the newPass
     */
    public String getNewPass() {
        return newPass;
    }

    /**
     * @param newPass the newPass to set
     */
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

}
