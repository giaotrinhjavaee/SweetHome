/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.District;
import com.entity.ExchangeType;
import com.entity.Property;
import com.entity.PropertyType;
import com.entity.Users;
import com.lib.Dao;
import com.util.HibernateUtil;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class PropertyOfUserBean {

    private String user = "";
    private List<Property> listProperty;
    private List<Property> propertySelected;
    private String searchkey;
    private String searchtype;
    private String searchcat;
    private String searchpub;
    private String searchStt;

    /**
     * Creates a new instance of PropertyOfUserBean
     */
    public PropertyOfUserBean() {
    }

    public void init() throws IOException {

        if (user != null && !user.equals("")) {
            try {
                Dao dao = new Dao();
                Users u = (Users) dao.getById(Users.class, user);
                if (!u.getUUsername().equals(user)) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                } else {
                    Criterion cUser = Restrictions.eq("users", u);
                    listProperty = dao.getByCondition(Property.class, cUser);
                }
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }

    public List<Property> getGenerateList() {
        Dao dao = new Dao();
        HibernateUtil.getInstance().clear();
        Users u = (Users) dao.getById(Users.class, user);
        Criterion cUser = Restrictions.eq("users", u);
        if (searchkey == null) {
            searchkey = "";
        }
        Criterion cTitle = Restrictions.like("proTitle", "%" + searchkey + "%");
        LogicalExpression logic;

        Criterion cType = null;
        if (searchtype != null && !searchtype.equals("")) {
            int id = 0;
            try {
                id = Integer.parseInt(searchtype);
                cType = Restrictions.eq("exchangeType", new ExchangeType(id));
            } catch (Exception ex) {
                cType = Restrictions.isNotNull("exchangeType");
            }
        } else {
            cType = Restrictions.isNotNull("exchangeType");
        }
        logic = Restrictions.and(cType, cTitle);

        Criterion cCat = null;
        if (searchcat != null && !searchcat.equals("")) {
            int id = 0;
            try {
                id = Integer.parseInt(searchcat);
                cCat = Restrictions.eq("propertyType", new PropertyType(id));
                logic = Restrictions.and(logic, cCat);
            } catch (Exception ex) {

            }
        }
        Criterion cPub = null;
        if (searchpub != null && !searchpub.equals("")) {
            boolean id = false;
            try {
                id = Boolean.parseBoolean(searchpub);
                cPub = Restrictions.eq("proPublish", id);
                logic = Restrictions.and(logic, cPub);
            } catch (Exception ex) {
            }
        }
        Criterion cStt = null;
        if (searchStt != null && !searchStt.equals("")) {
            int id;
            try {
                id = Integer.parseInt(searchpub);
                cStt = Restrictions.eq("proStatus", id);
                logic = Restrictions.and(logic, cStt);
            } catch (Exception ex) {
            }
        }
        logic = Restrictions.and(logic, cUser);
        Order o = Order.desc("proPublishDate");
        listProperty = dao.getByCondition(Property.class, logic, o);
        return listProperty;
    }

    public void updatePublish(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        for (Property item : propertySelected) {
            item.setProPublish(true);
            dao.update(item);
        }
        msg = "Update successfull";
        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateUnpublish(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        for (Property item : propertySelected) {
            item.setProPublish(false);
            dao.update(item);
        }
        msg = "Update successfull";
        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void deleteProperty(ActionEvent event) {
        Dao dao = new Dao(true);
        String msg;
        FacesMessage fmsg;
        HibernateUtil.getInstance().beginTransaction();
        for (Property item : propertySelected) {
            dao.delete(item);
        }
        if (HibernateUtil.getInstance().commit()) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the listProperty
     */
    public List<Property> getListProperty() {
        return listProperty;
    }

    /**
     * @param listProperty the listProperty to set
     */
    public void setListProperty(List<Property> listProperty) {
        this.listProperty = listProperty;
    }

    /**
     * @return the searchkey
     */
    public String getSearchkey() {
        return searchkey;
    }

    /**
     * @param searchkey the searchkey to set
     */
    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    /**
     * @return the searchtype
     */
    public String getSearchtype() {
        return searchtype;
    }

    /**
     * @param searchtype the searchtype to set
     */
    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
    }

    /**
     * @return the searchcat
     */
    public String getSearchcat() {
        return searchcat;
    }

    /**
     * @param searchcat the searchcat to set
     */
    public void setSearchcat(String searchcat) {
        this.searchcat = searchcat;
    }

    /**
     * @return the searchpub
     */
    public String getSearchpub() {
        return searchpub;
    }

    /**
     * @param searchpub the searchpub to set
     */
    public void setSearchpub(String searchpub) {
        this.searchpub = searchpub;
    }

    /**
     * @return the searchStt
     */
    public String getSearchStt() {
        return searchStt;
    }

    /**
     * @param searchStt the searchStt to set
     */
    public void setSearchStt(String searchStt) {
        this.searchStt = searchStt;
    }

    /**
     * @return the propertySelected
     */
    public List<Property> getPropertySelected() {
        return propertySelected;
    }

    /**
     * @param propertySelected the propertySelected to set
     */
    public void setPropertySelected(List<Property> propertySelected) {
        this.propertySelected = propertySelected;
    }

}
