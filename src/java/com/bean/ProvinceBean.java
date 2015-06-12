/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Province;
import com.entity.Users;
import com.lib.Dao;
import com.util.HibernateUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
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
public class ProvinceBean {

    private Province provinceSelected;
    private String proSearch = "";

    public ProvinceBean() {
        if (provinceSelected == null) {
            provinceSelected = new Province();
        }
    }

    public List<Province> getAllProvince() {
        Dao dao = new Dao();
        HibernateUtil.getInstance().clear();
        List<Province> list = null;
        if (proSearch == null || proSearch.equals(null) || proSearch.equals("")) {
            list = dao.getByCondition(Province.class, Order.desc("provId"));
        } else {
            Criterion name = Restrictions.like("provName", "%" + proSearch + "%");
            Criterion desc = Restrictions.like("provDescription", "%" + proSearch + "%");
            LogicalExpression orExp = Restrictions.or(desc, name);
            list = dao.getByCondition(Province.class, orExp, Order.desc("provId"));
        }
        return list;
    }

    /**
     * @return the provinceSelected
     */
    public Province getProvinceSelected() {
        return provinceSelected;
    }

    /**
     * @param provinceSelected the provinceSelected to set
     */
    public void setProvinceSelected(Province provinceSelected) {
        this.provinceSelected = provinceSelected;
    }

    public void deleteProvince(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        Province p = (Province) dao.getById(Province.class, provinceSelected.getProvId());
        if (dao.delete(p)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }

    }

    public void addProvince(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        if (dao.add(provinceSelected) != null) {
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
        if (dao.update(provinceSelected) != null) {
            msg = "Update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the proSearch
     */
    public String getProSearch() {
        return proSearch;
    }

    /**
     * @param proSearch the proSearch to set
     */
    public void setProSearch(String proSearch) {
        this.proSearch = proSearch;
    }

}
