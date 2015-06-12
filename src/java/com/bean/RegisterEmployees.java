/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Users;
import com.lib.Dao;
import com.util.Encrypt;
import com.util.HibernateUtil;
import com.util.SessionUtils;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Home
 */
@ManagedBean
@RequestScoped
public class RegisterEmployees {

    private Users userselect;
    private String userview;

    public RegisterEmployees() {
        if (userselect == null) {
            userselect = new Users();

        }
    }

    public List<Users> getAllUsers() {
        Dao dao = new Dao();
        List<Users> list = null;
        //HibernateUtil.getInstance().clear();
        if (userview == null || userview.equals(null) || userview.equals("")) {
            Criterion admin = Restrictions.eq("UIsAdmin", false);
            list = dao.getByCondition(Users.class, admin);
        } else {
            Criterion admin = Restrictions.eq("UIsAdmin", false);
            Criterion name = Restrictions.like("UUsername", "%" + userview + "%");
            Criterion fname = Restrictions.like("UFullname", "%" + userview + "%");
            Criterion phone = Restrictions.like("UPhone", "%" + userview + "%");
            Criterion mail = Restrictions.like("UEmail", "%" + userview + "%");
            LogicalExpression orExp = Restrictions.or(fname, name);
            orExp = Restrictions.or(orExp, phone);
            orExp = Restrictions.or(orExp, mail);
            orExp = Restrictions.and(orExp, admin);
            list = dao.getByCondition(Users.class, orExp);
        }
        return list;
    }

    public void addUser(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;

        List<Users> list = dao.getByCondition(Users.class, Restrictions.eq("UUsername", userselect.getUUsername()));
        if (list.size() == 0) {
            userselect.setUPassword(Encrypt.md5(userselect.getUPassword()));
            Object id = dao.add(userselect);
            if (id != null) {
                msg = "Add successfull";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                FacesContext.getCurrentInstance().addMessage(null, fmsg);
            }
        } else {
            msg = "Username have already used.";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }

//        else {
//            if (dao.isHasException()) {
//                if (dao.getEx().getClass().getName().equals("org.hibernate.NonUniqueObjectException")) {
//                    msg = "Username have already used.";
//                    fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
//                } else {
//                    msg = "Add fail";
//                    fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
//                }
//            } else {
//                msg = "Add fail";
//                fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
//            }
//        }
    }

    private String newPassword;

    public void updateUser(ActionEvent event) {
        Dao dao = new Dao();
        String msg = "";
        FacesMessage fmsg;
        if (newPassword != null && !newPassword.equals("")) {
            userselect.setUPassword(Encrypt.md5(newPassword));
        }
        if (dao.update(getUserselect()) != null) {
            msg = "update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void deleteUser(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        Users u = (Users) dao.getById(Users.class, getUserselect().getUUsername());
        if (dao.delete(u)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Delete fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void checkUsers(ActionEvent event) throws IOException {

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
                if (user.getUActived()) {
                    SessionUtils.getSession().setAttribute("login", user);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("account.xhtml");
                    msg = "Welcome to web-site MySweetHome";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                } else {
                    msg = "Your account is blocked";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
                }

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

    public void logOut() throws IOException {
        SessionUtils.getSession().invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
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
     * @return the userview
     */
    public String getUserview() {
        return userview;
    }

    /**
     * @param userview the userview to set
     */
    public void setUserview(String userview) {
        this.userview = userview;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
