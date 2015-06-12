/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Property;
import com.entity.Province;
import com.lib.Dao;
import java.util.List;
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
public class SuggestedBean {

    private int propertyId;
    private List<Property> listSuggested;

    /**
     * Creates a new instance of SuggestedBean
     */
    public SuggestedBean() {
    }

    public void init(long id) {
        Dao dao = new Dao();
        Property p = (Property) dao.getById(Property.class, id);
        Province prov = (Province) dao.getById(Province.class, p.getDistrict().getProvince().getProvId());
        Criterion d = Restrictions.in("district", prov.getDistricts());
        Criterion cat = Restrictions.eq("propertyType", p.getPropertyType());
        Criterion type = Restrictions.eq("exchangeType", p.getExchangeType());
        Criterion status = Restrictions.eq("proStatus", 0);
        Order o = Order.desc("proCreateDate");
        Conjunction logic = Restrictions.and(new Criterion[]{d, cat, type, status});
        listSuggested = dao.getByCondition(Property.class, logic, o, 0, 6);
    }

    public List<Property> getSpecialProperty() {
        Dao dao = new Dao();
        Order o = Order.desc("proPublishDate");
        Order o1 = Order.asc("proHit");
        Criterion stt = Restrictions.eq("proStatus", 0);
        Criterion cpub = Restrictions.eq("proPublish", true);
        Conjunction logic = Restrictions.and(new Criterion[]{stt, cpub});
        List<Property> list = dao.getByCondition(Property.class, logic, new Order[]{o, o1}, 0, 2);
        return list;
    }

    public List<Property> getLastestProperty() {
        Dao dao = new Dao();
        Order o = Order.desc("proPublishDate");
        Criterion stt = Restrictions.eq("proStatus", 0);
        Criterion cpub = Restrictions.eq("proPublish", true);
        Conjunction logic = Restrictions.and(new Criterion[]{stt, cpub});
        List<Property> list = dao.getByCondition(Property.class, logic, o, 0, 6);
        return list;
    }

    public List<Property> getMostViewProperty() {
        Dao dao = new Dao();
        Order o = Order.desc("proHit");
        Criterion stt = Restrictions.eq("proStatus", 0);
         Criterion cpub = Restrictions.eq("proPublish", true);
        Conjunction logic = Restrictions.and(new Criterion[]{stt, cpub});
        List<Property> list = dao.getByCondition(Property.class, logic, o, 0, 6);
        return list;
    }

    /**
     * @return the propertyId
     */
    public int getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId the propertyId to set
     */
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @return the listSuggested
     */
    public List<Property> getListSuggested() {
        return listSuggested;
    }

    /**
     * @param listSuggested the listSuggested to set
     */
    public void setListSuggested(List<Property> listSuggested) {
        this.listSuggested = listSuggested;
    }

}
