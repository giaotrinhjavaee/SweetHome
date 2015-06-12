/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.District;
import com.entity.Property;
import com.entity.PropertyType;
import com.entity.Users;
import com.lib.Dao;
import com.util.SessionUtils;
import com.util.UploadFile;
import java.io.IOException;
import java.util.ArrayList;
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
import org.primefaces.model.UploadedFile;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class PropertyUserBean {

    private String id;
    private UploadedFile fileMain;
    private Property property;
    private com.util.UploadFile u = new UploadFile();
    private long createDate;
    private String createUser;
    private List<String> listImages = new ArrayList<String>();

    public PropertyUserBean() {
        if (property == null) {
            property = new Property();
        }
        Dao dao = new Dao();
        Users u = (Users) SessionUtils.getSession().getAttribute("login");
        if (u != null) {
            Criterion cr = Restrictions.eq("users", u);
            Order o = Order.desc("proPublishDate");
            listProperty = dao.getByCondition(Property.class, cr, o);
        }
    }

    public void init() throws IOException {
        Dao dao = new Dao();
        if (id != null && !id.equals("")) {
            try {
                long id2 = Long.parseLong(id);
                property = (Property) dao.getById(Property.class, id2);
                if (property.getProId() != id2) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("propertylist.xhtml");
                } else {
                    if (property.getProImages() != null && !property.getProImages().equals("")) {
                        String[] images = property.getProImages().split(",");
                        for (String item : images) {
                            getListImages().add(item);
                        }
                    }
                    getListImages().add(property.getProImage());
                    createDate = property.getProCreateDate().getTime();
                    createUser = property.getUsers().getUUsername();
                }
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("propertylist.xhtml");
            }
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("propertylist.xhtml");
        }
    }

    public void addNewProperty(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        boolean isAdd = false;

        String url = "";
        if (getFileMain().getFileName() != null && !fileMain.getFileName().equals("")) {
            String imageMain = getU().copyFile(getFileMain().getFileName(), getFileMain().getInputstream());
            getProperty().setProImage(imageMain);
            getU().flush();
        }
        //add user
        Users u = (Users) SessionUtils.getSession().getAttribute("login");
        property.setUsers(u);
        property.setProPublish(false);
        property.setProPublishDate(new Date());
        property.setProCreateDate(new Date());
        property.setProStatus(0);
        property.setProHit(0);
        if (dao.add(property) != null) {
            msg = "Add successfull";
            isAdd = true;
            url = "property.xhtml";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().getExternalContext().redirect("propertylist.xhtml");
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);

    }

    public void updateProperty(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        boolean isAdd = false;

        if (getFileMain().getFileName() != null && !fileMain.getFileName().equals("")) {
            String imageMain = getU().copyFile(getFileMain().getFileName(), getFileMain().getInputstream());
            getProperty().setProImage(imageMain);
            getU().flush();
        }
        getProperty().setProPublishDate(new Date());
        getProperty().setProCreateDate(new Date(getCreateDate()));
        getProperty().setUsers(new Users(getCreateUser()));
        if (dao.update(getProperty()) != null) {
            msg = "Update successfull";
            isAdd = true;
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().getExternalContext().redirect("propertylist.xhtml");
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    List<Property> listProperty;


    public void searchProperty(ActionEvent event) {
        getGenerateListProperty();
    }
    
    public List<Property> getGenerateListProperty(){
        Dao dao = new Dao();
        Users u = (Users) SessionUtils.getSession().getAttribute("login");
        if (u != null) {
            LogicalExpression logic = null;
            Criterion cr = Restrictions.eq("users", u);

            if (publish != null && !publish.equals("")) {
                Criterion pub = Restrictions.eq("proPublish", Boolean.parseBoolean(publish));
                logic = Restrictions.and(cr, pub);
            }else{
                Criterion pub = Restrictions.isNotNull("proPublish");
                logic = Restrictions.and(cr, pub);
            }

            Order o = Order.desc("proPublishDate");
            if (fromDate != null) {
                Criterion from = Restrictions.gt("proCreateDate", fromDate);
                logic = Restrictions.and(logic, from);
            }
            if (toDate != null) {
                Criterion to = Restrictions.lt("proCreateDate", toDate);
                logic = Restrictions.and(logic, to);
            }
            if (cat != 0) {
                PropertyType t = new PropertyType(cat);
                Criterion ca = Restrictions.eq("propertyType", t);
                logic = Restrictions.and(logic, ca);
            }
            listProperty = dao.getByCondition(Property.class, logic, o);
        }
        return listProperty;
    }

    private Date fromDate;
    private Date toDate;
    private int cat;
    private String publish;

    
    public List<Property> getListProperty() {
        return listProperty;
    }
    public void setListProperty(List<Property> listProperty) {
        this.listProperty = listProperty;
    }
    /**
     * @return the fileMain
     */
    public UploadedFile getFileMain() {
        return fileMain;
    }

    /**
     * @param fileMain the fileMain to set
     */
    public void setFileMain(UploadedFile fileMain) {
        this.fileMain = fileMain;
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

    /**
     * @return the u
     */
    public com.util.UploadFile getU() {
        return u;
    }

    /**
     * @param u the u to set
     */
    public void setU(com.util.UploadFile u) {
        this.u = u;
    }

    /**
     * @return the createDate
     */
    public long getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the listImages
     */
    public List<String> getListImages() {
        return listImages;
    }

    /**
     * @param listImages the listImages to set
     */
    public void setListImages(List<String> listImages) {
        this.listImages = listImages;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the cat
     */
    public int getCat() {
        return cat;
    }

    /**
     * @param cat the cat to set
     */
    public void setCat(int cat) {
        this.cat = cat;
    }

    /**
     * @return the publish
     */
    public String getPublish() {
        return publish;
    }

    /**
     * @param publish the publish to set
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

}
