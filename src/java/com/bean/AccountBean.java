/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Province;
import com.entity.Users;
import com.lib.Dao;
import com.util.Encrypt;
import com.util.SessionUtils;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class AccountBean {

    private Users user;
    private String oldPass;
    private String newPass;

    /**
     * Creates a new instance of AccountBean
     */
    public AccountBean() {
        if (user == null) {
            user = new Users();
        }
    }


    public void initAdmin() {
        setUser((Users) SessionUtils.getSession().getAttribute("loginAdmin"));
    }
    
    public void initUser(){
        setUser((Users) SessionUtils.getSession().getAttribute("login"));
    }

    public void updateAccount(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;

        Criterion cU = Restrictions.eq("UUsername", user.getUUsername());
        List<Users> p = dao.getByCondition(Province.class, cU);

        if (p.size() > 0) {
            Users u = p.get(0);
            if (oldPass != null && !oldPass.equals("")) {
                String oldPassEn = Encrypt.md5(oldPass);
                if (u.getUPassword().equals(oldPassEn)) {
                    user.setUPassword(oldPassEn);
                    if (dao.update(user) != null) {
                        msg = "Update successfull";
                        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                        FacesContext.getCurrentInstance().addMessage(null, fmsg);
                    } else {
                        msg = "Update fail";
                        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                        FacesContext.getCurrentInstance().addMessage(null, fmsg);
                    }
                } else {
                    msg = "Old password incorrect";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                    FacesContext.getCurrentInstance().addMessage(null, fmsg);
                }
            } else {
                if (dao.update(user) != null) {
                    msg = "Update successfull";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                    FacesContext.getCurrentInstance().addMessage(null, fmsg);
                } else {
                    msg = "Update fail";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                    FacesContext.getCurrentInstance().addMessage(null, fmsg);
                }
            }
        }
    }

  

    /**
     * @return the user
     */
    public Users getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * @return the oldPass
     */
    public String getOldPass() {
        return oldPass;
    }

    /**
     * @param oldPass the oldPass to set
     */
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
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
