/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.District;
import com.entity.Province;
import com.lib.Dao;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author admin
 */
@ManagedBean
@ViewScoped
public class DistrictViewBean {

    private String provinceSelected;
    private List<Province> listProvince;
    private List<District> listDistrict;

    /**
     * Creates a new instance of DistrictViewBean
     */
    public DistrictViewBean() {
        Dao dao = new Dao();
        listProvince = dao.getAll(Province.class);
        listDistrict = dao.getAll(District.class);
    }

    public void handleProvinceChange() {
        if (getProvinceSelected() != null && !provinceSelected.equals("")) {
            Dao dao = new Dao();
            for (Province item : getListProvince()) {
                String id = item.getProvId() + "";
                if (getProvinceSelected().equals(id)) {
                    setListDistrict(new ArrayList<District>());
                    Province p = (Province) dao.getById(Province.class, item.getProvId());
                    getListDistrict().addAll(p.getDistricts());
                    break;
                }
            }
        }
    }

    /**
     * @return the provinceSelected
     */
    public String getProvinceSelected() {
        return provinceSelected;
    }

    /**
     * @param provinceSelected the provinceSelected to set
     */
    public void setProvinceSelected(String provinceSelected) {
        this.provinceSelected = provinceSelected;
    }

    /**
     * @return the listProvince
     */
    public List<Province> getListProvince() {
        return listProvince;
    }

    /**
     * @param listProvince the listProvince to set
     */
    public void setListProvince(List<Province> listProvince) {
        this.listProvince = listProvince;
    }

    /**
     * @return the listDistrict
     */
    public List<District> getListDistrict() {
        return listDistrict;
    }

    /**
     * @param listDistrict the listDistrict to set
     */
    public void setListDistrict(List<District> listDistrict) {
        this.listDistrict = listDistrict;
    }

}
