/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Users;
import com.lib.Dao;
import com.util.Encrypt;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class ForgetPasswordBean {

    private Users user;

    /**
     * Creates a new instance of ForgetPasswordBean
     */
    public ForgetPasswordBean() {
        if (user == null) {
            user = new Users();
        }
    }

    public void getPassword(ActionEvent event) {
        String msg;
        FacesMessage fmsg;

        Dao dao = new Dao();
        Criterion cU = Restrictions.eq("UUsername", user.getUUsername());
        List<Users> list = dao.getByCondition(Users.class, cU);
        if (list.size() > 0) {
            Users u = list.get(0);
            if (u.getUUsername().equals(user.getUUsername()) && u.getUEmail().equals(user.getUEmail())) {
                u.setUPassword(Encrypt.md5("123456"));
                if (dao.update(u) != null) {
                    user=new Users();
                    msg = "Your password is changed to 123456 . Please login and change password";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                } else {
                    msg = "Error. Please contact administrator.";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
                }
            } else {
                msg = "Username or email incorrect";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
            }
        } else {
            msg = "Username or email incorrect";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);

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
