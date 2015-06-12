/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.DirectoryType;
import com.entity.Province;
import com.lib.Dao;
import com.util.HibernateUtil;
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
public class DirectoryTypeBean {

    private String searchkey;
    private DirectoryType directoryTypeSelected;

    /**
     * Creates a new instance of DirectoryBean
     */
    public DirectoryTypeBean() {
        if (directoryTypeSelected == null) {
            directoryTypeSelected = new DirectoryType();
        }
    }

    public List<DirectoryType> getAllDirectoryType() {
        Dao dao = new Dao();
        List<DirectoryType> list = null;
        if (searchkey == null || searchkey.equals(null) || searchkey.equals("")) {
            list = dao.getByCondition(DirectoryType.class, Order.desc("dirtId"));
        } else {
            Criterion name = Restrictions.like("dirtName", "%" + searchkey + "%");
            Criterion desc = Restrictions.like("dirtDescription", "%" + searchkey + "%");
            LogicalExpression orExp = Restrictions.or(desc, name);
            list = dao.getByCondition(DirectoryType.class, orExp, Order.desc("dirtId"));
        }
        return list;
    }

    public void addDirectoryType(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        if (dao.add(directoryTypeSelected) != null) {
            msg = "Add successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateDirectoryType(ActionEvent event) {
        Dao dao = new Dao();
        String msg = "";
        FacesMessage fmsg;
        if (dao.update(directoryTypeSelected) != null) {
            msg = "Update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void deleteDirectory(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        DirectoryType p = (DirectoryType) dao.getById(DirectoryType.class, directoryTypeSelected.getDirtId());
        if (dao.delete(p)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
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
     * @return the directoryTypeSelected
     */
    public DirectoryType getDirectoryTypeSelected() {
        return directoryTypeSelected;
    }

    /**
     * @param directoryTypeSelected the directoryTypeSelected to set
     */
    public void setDirectoryTypeSelected(DirectoryType directoryTypeSelected) {
        this.directoryTypeSelected = directoryTypeSelected;
    }

}
