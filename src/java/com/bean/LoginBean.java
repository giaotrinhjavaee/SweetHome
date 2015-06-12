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
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Home
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        if (userselect == null) {
            userselect = new Users();
        }
    }

    private Users userselect;
    private String username;
    private String password;

    public void checkUsers(ActionEvent event) throws IOException {
        // string passMD5;
        //passMD5 = ClsPublic.EncryptMD5(password);

        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;

        //tuong duong :   where u_username = 'admin'
       
        Criterion cr = Restrictions.eq("UUsername", userselect.getUUsername());

        List<Users> u = dao.getByCondition(Users.class, cr);

        String password = Encrypt.md5(userselect.getUPassword());

        if (u.size() > 0) {
            Users user = u.get(0);
            if (user.getUPassword().equals(password)) {
            
                /*if (user.getUIsAdmin()) {ben login admin
                     SessionUtils.getSession().setAttribute("login", u);
                     //chuyen trang 
                FacesContext.getCurrentInstance().getExternalContext().redirect("account.xhtml");
                msg = "Welcome to web-site MySweetHome";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                }else{
                    //k co quuyen admin
                }
                */
                SessionUtils.getSession().setAttribute("login", u);
                FacesContext.getCurrentInstance().getExternalContext().redirect("account.xhtml");
                msg = "Welcome to web-site MySweetHome";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            } else {
                msg = "Password invalid";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            }
        } else {
            msg = "Username invalid";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }

        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the userselect
     */
    public Users getUserselect() {
        return userselect;
    }

    /**
     * @param userselect the userselect to set
     */
    public void setUserselect(Users userselect) {
        this.userselect = userselect;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
