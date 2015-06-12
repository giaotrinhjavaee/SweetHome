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
import com.entity.Province;
import com.lib.Dao;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
public class SearchBean {

    private int propertyType;
    private int provinceId;
    private int districtId;
    private String priceMin;
    private String priceMax;
    private String areaMin;
    private String areaMax;
    private int exchangeType;
    private List<Property> listSearch;
    private String title;

    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
    }

    public void init() {
        Dao dao = new Dao();

        long pMin = 0, pMax = 0, aMin = 0, aMax = 0;
        try {
            pMin = Long.parseLong(this.priceMin);
        } catch (Exception ex) {

        }
        try {
            pMax = Long.parseLong(this.priceMax);
        } catch (Exception ex) {

        }
        try {
            aMin = Long.parseLong(this.areaMin);
        } catch (Exception ex) {

        }
        try {
            aMax = Long.parseLong(this.areaMax);
        } catch (Exception ex) {

        }

        LogicalExpression logic;
        Criterion district = null;
        Criterion province;
        Criterion propertyType = null;
        Criterion exchange = null;
        Criterion priceMin;
        Criterion priceMax;
        Criterion areaMin;
        Criterion areaMax;

        if (this.propertyType != 0) {
            PropertyType p = new PropertyType(this.propertyType);
            propertyType = Restrictions.eq("propertyType", p);
        } else {
            propertyType = Restrictions.isNotNull("propertyType");
        }

        if (this.exchangeType != 0) {
            ExchangeType e = new ExchangeType(this.exchangeType);
            exchange = Restrictions.eq("exchangeType", e);
        } else {
            exchange = Restrictions.isNotNull("exchangeType");
        }

        if (districtId != 0) {
            District d = new District(districtId);
            district = Restrictions.eq("district", d);
        } else {
            if (provinceId != 0) {
                Province p = (Province) dao.getById1(Province.class, provinceId);
                //Set<District> list = p.getDistricts();
                if (p != null && p.getDistricts().size() > 0) {
                    district = Restrictions.in("district", p.getDistricts());
                } else {
                    district = Restrictions.isNull("district");
                }
            }
        }
        Criterion cpub = Restrictions.eq("proPublish", true);
        Criterion stt = Restrictions.eq("proStatus", 0);
        logic = Restrictions.and(propertyType, exchange);
        logic = Restrictions.and(logic, stt);
        logic = Restrictions.and(logic, cpub);
        if (district != null) {
            logic = Restrictions.and(logic, district);
        }

        if (pMax > 0) {
            priceMax = Restrictions.between("proPrice", pMin, pMax);
            logic = Restrictions.and(logic, priceMax);
        } else {
            priceMin = Restrictions.gt("proPrice", pMin);
            logic = Restrictions.and(logic, priceMin);
        }

        if (aMax > 0) {
            areaMax = Restrictions.between("proArea", aMin, aMax);
            logic = Restrictions.and(logic, areaMax);
        } else {
            areaMin = Restrictions.gt("proArea", aMin);
            logic = Restrictions.and(logic, areaMin);
        }

        Order o = Order.desc("proPublishDate");

        listSearch = dao.getByCondition(Property.class, logic, o);

    }

    public List<Property> getGenerateListSearch() {
        init();
        return listSearch;
    }

    /**
     * @return the propertyType
     */
    public int getPropertyType() {
        return propertyType;
    }

    /**
     * @param propertyType the propertyType to set
     */
    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    /**
     * @return the provinceId
     */
    public int getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId the provinceId to set
     */
    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * @return the districtId
     */
    public int getDistrictId() {
        return districtId;
    }

    /**
     * @param districtId the districtId to set
     */
    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    /**
     * @return the priceMin
     */
    public String getPriceMin() {
        return priceMin;
    }

    /**
     * @param priceMin the priceMin to set
     */
    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    /**
     * @return the priceMax
     */
    public String getPriceMax() {
        return priceMax;
    }

    /**
     * @param priceMax the priceMax to set
     */
    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    /**
     * @return the areaMin
     */
    public String getAreaMin() {
        return areaMin;
    }

    /**
     * @param areaMin the areaMin to set
     */
    public void setAreaMin(String areaMin) {
        this.areaMin = areaMin;
    }

    /**
     * @return the areaMax
     */
    public String getAreaMax() {
        return areaMax;
    }

    /**
     * @param areaMax the areaMax to set
     */
    public void setAreaMax(String areaMax) {
        this.areaMax = areaMax;
    }

    /**
     * @return the exchangeType
     */
    public int getExchangeType() {
        return exchangeType;
    }

    /**
     * @param exchangeType the exchangeType to set
     */
    public void setExchangeType(int exchangeType) {
        this.exchangeType = exchangeType;
    }

    /**
     * @return the listSearch
     */
    public List<Property> getListSearch() {
        return listSearch;
    }

    /**
     * @param listSearch the listSearch to set
     */
    public void setListSearch(List<Property> listSearch) {
        this.listSearch = listSearch;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
