/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.District;
import com.entity.ExchangeType;
import com.entity.FavoriteProperty;
import com.entity.Property;
import com.entity.PropertyType;
import com.entity.Province;
import com.entity.Users;
import com.lib.Dao;
import com.util.HibernateUtil;
import com.util.SessionUtils;
import com.util.UploadFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
//@SessionScoped
public class PropertyBean {

    private String id;
    private String searchkey;
    private String searchcat;
    private String searchpub;
    private String searchStt;
    private String searchtype;

    private String provinceSelected;
    private List<Province> listProvince;
    private Property property;
    private List<Property> propertySelected;
    private List<Property> listProperty;
    private UploadedFile fileMain;
    private List listImages = new ArrayList();
    private String title;
    private long createDate;
    private String createUser;

    /**
     * Creates a new instance of PropertyBean
     */
    public PropertyBean() {
        if (property == null) {
            property = new Property();
        }
        title = "New property";

        Dao dao = new Dao();
        listProvince = dao.getAll(Province.class);
        SessionUtils.getSession().removeAttribute("upload");
    }

    public void init() throws IOException {
        Dao dao = new Dao();
        if (id != null && !id.equals("")) {
            try {
                long id2 = Long.parseLong(id);
                property = (Property) dao.getById(Property.class, id2);
                if (property.getProId() != id2) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("property.xhtml");
                } else {
                    provinceSelected = property.getDistrict().getProvince().getProvId() + "";
                    property.setProId(id2);
                    listDistrict = dao.getAll(District.class);
                    if (property.getProImages() != null && !property.getProImages().equals("")) {
                        String[] images = property.getProImages().split(",");
                        for (String item : images) {
                            listImages.add(item);
                        }
                    }
                    listImages.add(property.getProImage());
                    title = "Update property";
                    createDate = property.getProCreateDate().getTime();
                    createUser = property.getUsers().getUUsername();
                }
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("property.xhtml");
            }
        }
    }


    public void handleProperty(ActionEvent event) {
        getGenerateList();
    }

    public List<Property> getGenerateList() {
        Dao dao = new Dao();
        HibernateUtil.getInstance().clear();
        if (searchkey == null) {
            searchkey = "";
        }
        long idP = 0;
        try {
            idP = Long.parseLong(searchkey);
        } catch (Exception ex) {

        }
        Criterion cTitle = Restrictions.like("proTitle", "%" + searchkey + "%");
        Criterion cUser = Restrictions.eq("users", new Users(searchkey));
        LogicalExpression logic = Restrictions.or(cTitle, cUser);
        if (idP != 0) {
            Criterion cid = Restrictions.eq("proId", idP);
            logic = Restrictions.or(logic, cid);
        }

        Criterion cType = null;
        if (searchtype != null && !searchtype.equals("")) {
            int id = 0;
            try {
                id = Integer.parseInt(searchtype);
                cType = Restrictions.eq("exchangeType", new ExchangeType(id));
            } catch (Exception ex) {
                cType = Restrictions.isNotNull("exchangeType");
            }
        } else {
            cType = Restrictions.isNotNull("exchangeType");
        }
        logic = Restrictions.and(cType, logic);

        Criterion cCat = null;
        if (searchcat != null && !searchcat.equals("")) {
            int id = 0;
            try {
                id = Integer.parseInt(searchcat);
                cCat = Restrictions.eq("propertyType", new PropertyType(id));
                logic = Restrictions.and(logic, cCat);
            } catch (Exception ex) {

            }
        }
        Criterion cPub = null;
        if (searchpub != null && !searchpub.equals("")) {
            boolean id = false;
            try {
                id = Boolean.parseBoolean(searchpub);
                cPub = Restrictions.eq("proPublish", id);
                logic = Restrictions.and(logic, cPub);
            } catch (Exception ex) {
            }
        }
        Criterion cStt = null;
        if (searchStt != null && !searchStt.equals("")) {
            int id;
            try {
                id = Integer.parseInt(searchpub);
                cStt = Restrictions.eq("proStatus", id);
                logic = Restrictions.and(logic, cStt);
            } catch (Exception ex) {
            }
        }
        Order o = Order.desc("proPublishDate");
        listProperty = dao.getByCondition(Property.class, logic, o);
        return listProperty;
    }

    public List<PropertyType> getAllPropertyType() {
        Dao dao = new Dao();
        List<PropertyType> list = null;

        list = dao.getAll(PropertyType.class);
        return list;
    }
    private List<District> listDistrict;

    public void handleProvinceChange() {
        if (provinceSelected != null && !provinceSelected.equals("")) {
            Dao dao = new Dao();
            for (Province item : listProvince) {
                String id = item.getProvId() + "";
                if (provinceSelected.equals(id)) {
                    listDistrict = new ArrayList<District>();
                    Province p = (Province) dao.getById(Province.class, item.getProvId());
                    listDistrict.addAll(p.getDistricts());
                    break;
                }
            }
        }
    }

    public void addNewProperty(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        boolean isAdd = false;

        String url = "";
        if (fileMain.getFileName() != null && !fileMain.getFileName().equals("")) {
            String imageMain = u.copyFile(fileMain.getFileName(), fileMain.getInputstream());
            property.setProImage(imageMain);
            u.flush();
        }
        //property.setProImages(u.listFilename);

        if (property.getProId() == 0) {
            //add user
            Users u = (Users) SessionUtils.getSession().getAttribute("loginAdmin");
            property.setUsers(u);
            property.setProPublishDate(new Date());
            property.setProCreateDate(new Date());
            property.setProStatus(0);
            property.setProHit(0);
            if (dao.add(property) != null) {
                msg = "Add successfull";
                isAdd = true;
                url = "property.xhtml";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                FacesContext.getCurrentInstance().getExternalContext().redirect("property.xhtml");
            } else {
                msg = "Add fail";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            }
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        } else {
            property.setProPublishDate(new Date());
            property.setProCreateDate(new Date(createDate));
            property.setUsers(new Users(createUser));
            if (dao.update(property) != null) {
                msg = "Update successfull";
                isAdd = true;
                url = "property.xhtml";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                FacesContext.getCurrentInstance().getExternalContext().redirect("property.xhtml");
            } else {
                msg = "Update fail";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
            }
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    public void updatePublish(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        for (Property item : propertySelected) {
            item.setProPublish(true);
            dao.update(item);
        }
        msg = "Update successfull";
        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateUnpublish(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        for (Property item : propertySelected) {
            item.setProPublish(false);
            dao.update(item);
        }
        msg = "Update successfull";
        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateTrading(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        for (Property item : propertySelected) {
            item.setProStatus(0);
            dao.update(item);
        }
        msg = "Update successfull";
        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateStopTrading(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        for (Property item : propertySelected) {
            item.setProStatus(1);
            dao.update(item);
        }
        msg = "Update successfull";
        fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void refreshProperty(long id) {
        Dao dao = new Dao();
        Property p = (Property) dao.getById(Property.class, id);
        p.setProPublishDate(new Date());
        if (dao.update(p) != null) {
            String msg = "Update successfull";
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }

    }

    public void unpublishProperty(long id) {
        Dao dao = new Dao();
        Property p = (Property) dao.getById(Property.class, id);
        p.setProPublish(false);
        if (dao.update(p) != null) {
            String msg = "Update successfull";
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    public void updateTradingProperty(long id) {
        Dao dao = new Dao();
        Property p = (Property) dao.getById(Property.class, id);
        p.setProStatus(0);
        if (dao.update(p) != null) {
            String msg = "Update successfull";
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    public void stopTradingProperty(long id) {
        Dao dao = new Dao();
        Property p = (Property) dao.getById(Property.class, id);
        p.setProStatus(1);
        if (dao.update(p) != null) {
            String msg = "Update successfull";
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    public void deleteProperty(ActionEvent event) {
        Dao dao = new Dao(true);
        String msg;
        FacesMessage fmsg;
        HibernateUtil.getInstance().beginTransaction();
        int countP = 0;
        for (Property item : propertySelected) {
            Criterion cP = Restrictions.eq("property", item);
            List<FavoriteProperty> list = dao.getByCondition(FavoriteProperty.class, cP);
            int count = 0;
            for (FavoriteProperty itemF : list) {
                dao.delete(itemF);
                count++;
            }
            if (count == list.size()) {
                Property p = (Property) dao.getById(Property.class, item.getProId());
                dao.delete(p);
                countP++;
            }
        }
        if (countP == propertySelected.size()) {
            HibernateUtil.getInstance().commit();
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    public void deleteOneProperty(ActionEvent event) {
        Dao dao = new Dao(true);
        HibernateUtil.getInstance().beginTransaction();
        String msg;
        FacesMessage fmsg;

        Criterion cP = Restrictions.eq("property", property);
        List<FavoriteProperty> list = dao.getByCondition(FavoriteProperty.class, cP);
        int count = 0;
        for (FavoriteProperty item : list) {
            dao.delete(item);
            count++;
        }
        if (count == list.size()) {
            Property p = (Property) dao.getById(Property.class, property.getProId());
            dao.delete(p);
            if (HibernateUtil.getInstance().commit()) {
                msg = "Delete successfull";
                fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
                FacesContext.getCurrentInstance().addMessage(null, fmsg);
            }
        } else {
            HibernateUtil.getInstance().rollBack();
            msg = "Delete fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    public void deleteProperty(long id) {
        Dao dao = new Dao();
        Property p = (Property) dao.getById(Property.class, id);
        if (dao.delete(p)) {
            String msg = "Delete successfull";
            FacesMessage fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
    }

    com.util.UploadFile u = new UploadFile();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
     * @return the propertySelected
     */
    public List<Property> getPropertySelected() {
        return propertySelected;
    }

    /**
     * @param propertySelected the propertySelected to set
     */
    public void setPropertySelected(List<Property> propertySelected) {
        this.propertySelected = propertySelected;
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
     * @return the searchkey
     */
    public String getSearchkey() {
        return searchkey;
    }

    /**
     * @param searchkey the searchkey to set
     */
    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    /**
     * @return the searchcat
     */
    public String getSearchcat() {
        return searchcat;
    }

    /**
     * @param searchcat the searchcat to set
     */
    public void setSearchcat(String searchcat) {
        this.searchcat = searchcat;
    }

    /**
     * @return the searchpub
     */
    public String getSearchpub() {
        return searchpub;
    }

    /**
     * @param searchpub the searchpub to set
     */
    public void setSearchpub(String searchpub) {
        this.searchpub = searchpub;
    }

    /**
     * @return the searchtype
     */
    public String getSearchtype() {
        return searchtype;
    }

    /**
     * @param searchtype the searchtype to set
     */
    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
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
     * @return the searchStt
     */
    public String getSearchStt() {
        return searchStt;
    }

    /**
     * @param searchStt the searchStt to set
     */
    public void setSearchStt(String searchStt) {
        this.searchStt = searchStt;
    }

}
