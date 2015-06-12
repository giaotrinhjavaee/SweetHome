/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.District;
import com.entity.Province;
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
 * @author LE THANH TAN
 */
@ManagedBean
@RequestScoped
public class DistrictBean {

    private District districtSelected;
    private String disSearch = "";
    private List<Province> province;
    List<District> list;

    public DistrictBean() {
        Dao dao = new Dao();
        if (districtSelected == null) {
            districtSelected = new District();
        }
    }

    public List<District> getAllDistrict() {
        Dao dao = new Dao();
        //them dồng này vô chỗ nào select lên
        HibernateUtil.getInstance().clear();
        list = null;
        if (disSearch == null || disSearch.equals(null) || disSearch.equals("")) {
            list = dao.getByCondition(District.class, Order.desc("distId"));
        } else {
            Criterion name = Restrictions.like("distName", "%" + disSearch + "%");
            Criterion desc = Restrictions.like("distDescription", "%" + disSearch + "%");
            LogicalExpression orExp = Restrictions.or(desc, name);
            list = dao.getByCondition(District.class, orExp, Order.desc("distId"));
        }
        return list;
    }

    public List<Province> getAllProvince() {
        Dao dao = new Dao();
        HibernateUtil.getInstance().clear();
        List<Province> list = null;
        list = dao.getAll(Province.class);
        return list;
    }

    /**
     * @return the districtSelected
     */
    public District getDistrictSelected() {
        return districtSelected;
    }

    /**
     * @param districtSelected the districtSelected to set
     */
    public void setDistrictSelected(District districtSelected) {
        this.districtSelected = districtSelected;
    }

    public void deleteDistrict(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        District d = (District) dao.getById(District.class, districtSelected.getDistId());
        if (dao.delete(d)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }

    }

    public void addDistrict(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        if (dao.add(districtSelected) != null) {
            msg = "Add successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateDistrict(ActionEvent event) {

        Dao dao = new Dao();
        String msg = "";
        FacesMessage fmsg;
        if (dao.update(districtSelected) != null) {
            msg = "Update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the disSearch
     */
    public String getDisSearch() {
        return disSearch;
    }

    /**
     * @param disSearch the disSearch to set
     */
    public void setDisSearch(String disSearch) {
        this.disSearch = disSearch;
    }

    /**
     * @return the province
     */
    public List<Province> getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(List<Province> province) {
        this.province = province;
    }

}
