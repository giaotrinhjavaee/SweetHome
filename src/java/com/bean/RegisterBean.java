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
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class RegisterBean {

    private Users user;

    /**
     * Creates a new instance of RegisterBean
     */
    public RegisterBean() {
        if (user == null) {
            user = new Users();
        }
    }

    public void addUser(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg = null;

        List<Users> list = dao.getByCondition(Users.class, Restrictions.eq("UUsername", user.getUUsername()));
        if (list.size() == 0) {
            user.setUPassword(Encrypt.md5(user.getUPassword()));
            user.setUActived(true);
            user.setUIsAdmin(false);
            Object id = dao.add(user);
            if (id != null) {
                SessionUtils.getSession().setAttribute("login", user);
                FacesContext.getCurrentInstance().getExternalContext().redirect("account.xhtml");
            }
        } else {
            msg = "Username have already used.";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
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

}
