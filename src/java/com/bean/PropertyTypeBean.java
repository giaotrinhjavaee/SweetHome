/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.DirectoryType;
import com.entity.PropertyType;
import com.entity.Province;
import com.lib.Dao;
import com.util.HibernateUtil;
import com.util.SessionUtils;
import java.util.ArrayList;
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
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class PropertyTypeBean {

    private PropertyType propertytypeSelected;
    private String propertytypeSearch;

    /**
     * Creates a new instance of PropertyTypeBean
     */
    public PropertyTypeBean() {
        if (propertytypeSelected == null) {
            propertytypeSelected = new PropertyType();
        }

        DefaultMenuItem home = new DefaultMenuItem("Home");
        home.setUrl("index.xhtml");
        DefaultMenuItem login;
        if (SessionUtils.getSession().getAttribute("login") == null) {
            login = new DefaultMenuItem("Login");
            login.setUrl("login.xhtml");
        } else {
            login = new DefaultMenuItem("Account");
            login.setUrl("./account.xhtml");
        }

        DefaultSubMenu sell = new DefaultSubMenu("Selling homes");
        DefaultSubMenu rent = new DefaultSubMenu("Renting homes");
        DefaultSubMenu about = new DefaultSubMenu("Directory");
        DefaultMenuItem faq = new DefaultMenuItem("FAQS");
        faq.setUrl("faqs.xhtml");
        DefaultMenuItem contact = new DefaultMenuItem("Contact");
        contact.setUrl("contact.xhtml");
        DefaultMenuItem posting = new DefaultMenuItem("Posting");
        posting.setUrl("newproperty.xhtml");

        Dao dao = new Dao();
        List<PropertyType> list = dao.getAll(PropertyType.class);
        for (PropertyType item : list) {
            DefaultMenuItem menuSellItem = new DefaultMenuItem(item.getProtName());
            menuSellItem.setTitle(item.getProtName());
            menuSellItem.setUrl("viewproperty.xhtml?type=1&cat=" + item.getProtId());
            sell.addElement(menuSellItem);

            DefaultMenuItem menuRentItem = new DefaultMenuItem(item.getProtName());
            menuRentItem.setTitle(item.getProtName());
            menuRentItem.setUrl("viewproperty.xhtml?type=2&cat=" + item.getProtId());
            rent.addElement(menuRentItem);
        }

        List<DirectoryType> listDirec = dao.getAll(DirectoryType.class);
        for (DirectoryType item : listDirec) {
            DefaultMenuItem menuItem = new DefaultMenuItem(item.getDirtName());
            menuItem.setTitle(item.getDirtName());
            menuItem.setUrl("directory.xhtml?type=" + item.getDirtId());
            about.addElement(menuItem);
        }
        // Associate submenus with the menubar
        this.menubar.addElement(home);
        this.menubar.addElement(login);
        this.menubar.addElement(sell);
        this.menubar.addElement(rent);
        this.menubar.addElement(about);
        this.menubar.addElement(faq);
        this.menubar.addElement(contact);
        this.menubar.addElement(posting);

    }
    private MenuModel menubar = new DefaultMenuModel();

    public DefaultMenuModel getAllPropertyType() {
        Dao dao = new Dao();
        DefaultMenuModel list1 = new DefaultMenuModel();
        List<PropertyType> list = dao.getAll(PropertyType.class);
        for (PropertyType item : list) {
            DefaultMenuItem menuItem = new DefaultMenuItem();
            menuItem.setTitle("Computers");
            menuItem.setUrl("#");
            list1.addElement(menuItem);
        }
        return list1;
    }

    public List<PropertyType> getPropertyType() {
        Dao dao = new Dao();
        HibernateUtil.getInstance().clear();
        List<PropertyType> list = null;
        if (getPropertytypeSearch() == null || getPropertytypeSearch().equals(null) || getPropertytypeSearch().equals("")) {
            list = dao.getByCondition(PropertyType.class, Order.desc("protId"));
        } else {
            Criterion name = Restrictions.like("protName", "%" + getPropertytypeSearch() + "%");
            Criterion desc = Restrictions.like("protDescription", "%" + getPropertytypeSearch() + "%");
            LogicalExpression orExp = Restrictions.or(desc, name);
            list = dao.getByCondition(PropertyType.class, orExp, Order.desc("protId"));
        }
        return list;
    }

    public void deletePropertyType(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        PropertyType p = (PropertyType) dao.getById(PropertyType.class, propertytypeSelected.getProtId());
        if (dao.delete(p)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Delete fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void addPropertyType(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        if (dao.add(propertytypeSelected) != null) {
            msg = "Add successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updatePropertyType(ActionEvent event) {

        Dao dao = new Dao();
        String msg = "";
        FacesMessage fmsg;
        if (dao.update(propertytypeSelected) != null) {
            msg = "Update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the menubar
     */
    public MenuModel getMenubar() {
        return menubar;
    }

    /**
     * @param menubar the menubar to set
     */
    public void setMenubar(MenuModel menubar) {
        this.menubar = menubar;
    }

    /**
     * @return the propertytypeSelected
     */
    public PropertyType getPropertytypeSelected() {
        return propertytypeSelected;
    }

    /**
     * @param propertytypeSelected the propertytypeSelected to set
     */
    public void setPropertytypeSelected(PropertyType propertytypeSelected) {
        this.propertytypeSelected = propertytypeSelected;
    }

    /**
     * @return the propertytypeSearch
     */
    public String getPropertytypeSearch() {
        return propertytypeSearch;
    }

    /**
     * @param propertytypeSearch the propertytypeSearch to set
     */
    public void setPropertytypeSearch(String propertytypeSearch) {
        this.propertytypeSearch = propertytypeSearch;
    }

}
