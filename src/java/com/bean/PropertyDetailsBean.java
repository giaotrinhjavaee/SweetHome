/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Property;
import com.lib.Dao;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class PropertyDetailsBean {
    
    private String propertyId;
    private Property property;

    /**
     * Creates a new instance of PropertyDetailsBean
     */
    public PropertyDetailsBean() {
    }
    
    public void init() throws IOException {
        if (propertyId != null && !propertyId.equals("")) {
            Dao dao = new Dao();
            
            try {
                long id = Long.parseLong(propertyId);
                property = (Property) dao.getById(Property.class, id);
                property.setProHit(property.getProHit() + 1);
                dao.update(property);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }

    
    public boolean hasFavorite() {
        
        return false;
    }

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId the propertyId to set
     */
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @return the property
     */
    public Property getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(Property property) {
        this.property = property;
    }
    
}
