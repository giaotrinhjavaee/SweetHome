/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.ExchangeType;
import com.entity.Property;
import com.entity.PropertyType;
import com.lib.Dao;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.hibernate.criterion.Conjunction;
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
public class PropertyViewBean {

    private int type;
    private int cat;
    private String title;
    private List<Property> listProperty;

    /**
     * Creates a new instance of PropertyViewBean
     */
    public PropertyViewBean() {
    }

    public void init() {
        Criterion cType = null;
        Criterion cCat = null;
        title = "Real estate | Buy, sell, rent out real estate";
        if (type == 1) {
            title = "Property for sale";
            ExchangeType e = new ExchangeType(type);
            cType = Restrictions.eq("exchangeType", e);
        } else if (type == 2) {
            title = "Property for rent";
            ExchangeType e = new ExchangeType(type);
            cType = Restrictions.eq("exchangeType", e);
        } else {
            cType = Restrictions.isNotNull("exchangeType");
        }

        if (cat != 0) {
            PropertyType t = new PropertyType(cat);
            cCat = Restrictions.eq("propertyType", t);
        } else {
            cCat = Restrictions.isNotNull("propertyType");
        }
        Dao dao = new Dao();
        Criterion stt = Restrictions.eq("proStatus", 0);
        Criterion cpub = Restrictions.eq("proPublish", true);
        Conjunction logic = Restrictions.and(new Criterion[]{cCat, cType, stt, cpub});
        Order o = Order.desc("proPublishDate");
        listProperty = dao.getByCondition(Property.class, logic, o);
    }

    public List<Property> getGenerateListProperty() {
        init();
        return listProperty;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    public int getCat() {
        return cat;
    }

    /**
     * @param id the id to set
     */
    public void setCat(int cat) {
        this.cat = cat;
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

}
