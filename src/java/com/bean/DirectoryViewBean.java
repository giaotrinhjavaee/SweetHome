/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Directory;
import com.entity.DirectoryType;
import com.entity.District;
import com.entity.ExchangeType;
import com.entity.Property;
import com.entity.PropertyType;
import com.lib.Dao;
import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
public class DirectoryViewBean {
    
    private int type;
    private String title;
    List<Directory> listDirectory;

    /**
     * Creates a new instance of DirectoryViewBean
     */
    public DirectoryViewBean() {
    }
    
    public void init(){
        Dao dao = new Dao();
        Criterion c = Restrictions.eq("dirtId", type);
        List<DirectoryType> d = dao.getByCondition(DirectoryType.class, c);
        if (d.size() == 0) {
            title = "Category of directory";
        } else {
            title = d.get(0).getDirtName();
        }
    }
    
    public List<Directory> getAllDirectory() {
        Dao dao = new Dao();
        
        Criterion c = Restrictions.eq("dirtId", type);
        List<DirectoryType> d = dao.getByCondition(DirectoryType.class, c);
        //DirectoryType d = (DirectoryType) dao.getById(DirectoryType.class, type);
        Criterion cType = null;
        if (d.size() == 0) {
            title = "Category of directory";
            cType = Restrictions.isNotNull("directoryType");
        } else {
            title = d.get(0).getDirtName();
            cType = Restrictions.eq("directoryType", d.get(0));
        }
        Order o = Order.desc("dirName");
        return listDirectory = dao.getByCondition(Directory.class, cType, o);
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
