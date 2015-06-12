/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.FavoriteProperty;

import com.lib.Dao;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Home
 */
@ManagedBean
@RequestScoped
public class NewFavoritePropertyBean {
    private FavoriteProperty fpseleted;
    private  String fpsearch="";
    public NewFavoritePropertyBean(){
    if (fpseleted== null) {
           fpseleted  = new FavoriteProperty();
        }
    }
    public List<NewFavoritePropertyBean> getAllProvince() {
        Dao dao = new Dao();
        List<NewFavoritePropertyBean> list = null;
        if (getFpsearch() == null || getFpsearch().equals(null) || getFpsearch().equals("")) {
            list = dao.getAll(NewFavoritePropertyBean.class);
        } else {
            Criterion name = Restrictions.like("favId", "%" + getFpsearch() + "%");
            Criterion desc = Restrictions.like("property", "" + getFpsearch() + "%");
            LogicalExpression orExp = Restrictions.or(desc, name);
            list = dao.getByCondition(NewFavoritePropertyBean.class, orExp);
        }
        return list;
    }

   

    public void deleteProvince(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        NewFavoritePropertyBean fp = (NewFavoritePropertyBean) dao.getById(NewFavoritePropertyBean.class,getFpseleted().getFavId());
        if (dao.delete(fp)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Delete fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void addProvince(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        if (dao.add(getFpseleted()) != null) {
            msg = "Add successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateProvince(ActionEvent event) {

        Dao dao = new Dao();
        String msg = "";
        FacesMessage fmsg;
        if (dao.update(getFpseleted()) != null) {
            msg = "update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the fpseleted
     */
    public FavoriteProperty getFpseleted() {
        return fpseleted;
    }

    /**
     * @param fpseleted the fpseleted to set
     */
    public void setFpseleted(FavoriteProperty fpseleted) {
        this.fpseleted = fpseleted;
    }

    /**
     * @return the fpsearch
     */
    public String getFpsearch() {
        return fpsearch;
    }

    /**
     * @param fpsearch the fpsearch to set
     */
    public void setFpsearch(String fpsearch) {
        this.fpsearch = fpsearch;
    }
    
}
