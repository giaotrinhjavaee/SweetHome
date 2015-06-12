/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Directory;
import com.entity.DirectoryType;
import com.lib.Dao;
import com.util.UploadFile;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
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
public class DirectoryAdminBean {

    private String searchkey;
    private Directory directorySelected;
    private UploadedFile fileMain;
    com.util.UploadFile u = new UploadFile();

    /**
     * Creates a new instance of DirectoryAdminBean
     */
    public DirectoryAdminBean() {
        if (directorySelected == null) {
            directorySelected = new Directory();
        }
    }

    public List<Directory> getAllDirectory() {
        Dao dao = new Dao();
        List<Directory> list = null;
        if (searchkey == null || searchkey.equals(null) || searchkey.equals("")) {
            list = dao.getByCondition(Directory.class, Order.desc("dirId"));
        } else {
            Criterion name = Restrictions.like("dirName", "%" + searchkey + "%");
            Criterion address = Restrictions.like("dirAddress", "%" + searchkey + "%");
            Criterion web = Restrictions.like("dirWebsite", "%" + searchkey + "%");
            Criterion desc = Restrictions.like("dirDescription", "%" + searchkey + "%");
            Criterion phone = Restrictions.like("dirPhone", "%" + searchkey + "%");
            Disjunction orExp = Restrictions.or(new Criterion[]{name, address, web, desc, phone});
            list = dao.getByCondition(Directory.class, orExp, Order.desc("dirId"));
        }
        return list;
    }

    public void addDirectory(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        if (fileMain.getFileName() != null && !fileMain.getFileName().equals("")) {
            String imageMain = u.copyFile(fileMain.getFileName(), fileMain.getInputstream());
            directorySelected.setDirImage(imageMain);
            u.flush();
        }
        if (dao.add(directorySelected) != null) {
            msg = "Add successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateDirectory(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        String msg = "";
        FacesMessage fmsg;
        if (fileMain.getFileName() != null && !fileMain.getFileName().equals("")) {
            String imageMain = u.copyFile(fileMain.getFileName(), fileMain.getInputstream());
            directorySelected.setDirImage(imageMain);
            u.flush();
        }
        if (dao.update(directorySelected) != null) {
            msg = "Update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void deleteDirectory(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        Directory p = (Directory) dao.getById(Directory.class, directorySelected.getDirId());
        if (dao.delete(p)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }
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
     * @return the directorySelected
     */
    public Directory getDirectorySelected() {
        return directorySelected;
    }

    /**
     * @param directorySelected the directorySelected to set
     */
    public void setDirectorySelected(Directory directorySelected) {
        this.directorySelected = directorySelected;
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

}
