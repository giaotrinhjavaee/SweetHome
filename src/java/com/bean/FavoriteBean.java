/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.FavoriteProperty;
import com.entity.Property;
import com.entity.Users;
import com.lib.Dao;
import com.util.HibernateUtil;
import com.util.SessionUtils;
import java.util.Date;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class FavoriteBean {

    private long propertyId;
    private FavoriteProperty favoriteSelected;
    private String favoriteSearch = "";

    /**
     * Creates a new instance of FavoriteBean
     */
    public FavoriteBean() {
        if (favoriteSelected == null) {
            favoriteSelected = new FavoriteProperty();
        }
    }

    public void init(long id) {
        propertyId = id;
    }

    public void addNewFavorite(ActionEvent event) {
        Dao dao = new Dao();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage fmsg;
        Users u = (Users) SessionUtils.getSession().getAttribute("login");
        Property p = (Property) dao.getById(Property.class, propertyId);
        boolean isOK = false;
        if (u != null && p.getProId() != 0) {

            FavoriteProperty f = new FavoriteProperty();
            f.setProperty(p);
            f.setUsers(u);
            f.setFavCreateDate(new Date());
            if (dao.add(f) != null) {
                isOK = true;
                fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Add property to favorite list successfull.", null);
            } else {
                fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Cannot add property to favorite list successfull.", null);
            }

        } else {
            fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Please login", null);
        }
        context.addCallbackParam("isOK", isOK);
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public boolean checkHaveFavorite() {
        Dao dao = new Dao();
        Users u = (Users) SessionUtils.getSession().getAttribute("login");
        Property p = (Property) dao.getById(Property.class, propertyId);
        if (u != null && p.getProId() != 0) {
            Criterion cU = Restrictions.eq("users", u);
            Criterion cP = Restrictions.eq("property", p);
            LogicalExpression logic = Restrictions.and(cP, cU);
            List<FavoriteProperty> f = dao.getByCondition(FavoriteProperty.class, logic);
            if (f.size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public List<FavoriteProperty> getAllFavorite() {
        Dao dao = new Dao();
        HibernateUtil.getInstance().clear();
        List<FavoriteProperty> list = null;
        Users u = (Users) SessionUtils.getSession().getAttribute("login");
        Criterion c = Restrictions.eq("users", u);
        list = dao.getByCondition(FavoriteProperty.class,c,Order.desc("favCreateDate"));
        return list;
    }

    public void deleteFavorite(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        FavoriteProperty f = (FavoriteProperty) dao.getById(FavoriteProperty.class, favoriteSelected.getFavId());
        if (dao.delete(f)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Delete fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the propertyId
     */
    public long getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId the propertyId to set
     */
    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @return the favoriteSelected
     */
    public FavoriteProperty getFavoriteSelected() {
        return favoriteSelected;
    }

    /**
     * @param favoriteSelected the favoriteSelected to set
     */
    public void setFavoriteSelected(FavoriteProperty favoriteSelected) {
        this.favoriteSelected = favoriteSelected;
    }

    /**
     * @return the favoriteSearch
     */
    public String getFavoriteSearch() {
        return favoriteSearch;
    }

    /**
     * @param favoriteSearch the favoriteSearch to set
     */
    public void setFavoriteSearch(String favoriteSearch) {
        this.favoriteSearch = favoriteSearch;
    }

}
