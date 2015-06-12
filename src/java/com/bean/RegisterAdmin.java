/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Users;
import com.lib.Dao;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.urlPatternType;
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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Home
 */
@ManagedBean
@RequestScoped
public class RegisterAdmin {

    private Users userselect;
    private String usersearh = "";

    public RegisterAdmin() {
        if (userselect == null) {
            userselect = new Users();
        }
    }

    public List<Users> getAllUsers() {
        Dao dao = new Dao();
        List<Users> list1 = null;
        HibernateUtil.getInstance().clear();
        if (usersearh == null || usersearh.equals(null) || usersearh.equals("")) {
            Criterion admin = Restrictions.eq("UIsAdmin", true);
            list1 = dao.getByCondition(Users.class, admin);
        } else {
            Criterion admin = Restrictions.eq("UIsAdmin", true);
            Criterion name = Restrictions.like("UUsername", "%" + usersearh + "%");
            Criterion fname = Restrictions.like("UFullname", "%" + usersearh + "%");
            Criterion phone = Restrictions.like("UPhone", "%" + usersearh + "%");
            Criterion mail = Restrictions.like("UEmail", "%" + usersearh + "%");
            LogicalExpression orExp = Restrictions.or(fname, name);
            orExp = Restrictions.or(orExp, phone);
            orExp = Restrictions.or(orExp, mail);
            orExp = Restrictions.and(orExp, admin);
            list1 = dao.getByCondition(Users.class, orExp);
        }
        return list1;
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
            msg = "Update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void deleteUser(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        Users u = (Users) dao.getById(Users.class, userselect.getUUsername());
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

                if (user.getUIsAdmin() && user.getUActived()) {
                    SessionUtils.getSession().setAttribute("loginAdmin", user);
                    //chuyen trang 
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                    msg = "Welcome to web-site MySweetHome";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                } else if (!user.getUActived()) {
                    msg = "Your account is blocked";
                    fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
                } else {
                    msg = "Username invalid";
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

    public Users getLoginUser() {
        Object u = SessionUtils.getSession().getAttribute("loginAdmin");
        return (Users) u;
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
     * @return the usersearh
     */
    public String getUsersearh() {
        return usersearh;
    }

    /**
     * @param usersearh the usersearh to set
     */
    public void setUsersearh(String usersearh) {
        this.usersearh = usersearh;
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
