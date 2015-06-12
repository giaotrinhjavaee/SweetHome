/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Faqs;
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
public class FAQsBean {

    private Faqs faqsSelected;
    private String faqsSearch = "";

    public FAQsBean() {
        if (faqsSelected == null) {
            faqsSelected = new Faqs();
        }
    }

    public List<Faqs> getAllFAQs() {
        Dao dao = new Dao();
        List<Faqs> list = null;
        if (faqsSearch == null || faqsSearch.equals("")) {
            list = dao.getByCondition(Faqs.class, Order.desc("faqId"));
        } else {
            Criterion name = Restrictions.like("faqQuestion", "%" + faqsSearch + "%");
            Criterion desc = Restrictions.like("faqQuestion", "%" + faqsSearch + "%");
            LogicalExpression orExp = Restrictions.or(desc, name);
            list = dao.getByCondition(Faqs.class, orExp, Order.desc("faqId"));
        }
        return list;
    }

    public void deleteFAQs(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        Faqs f = (Faqs) dao.getById(Faqs.class, faqsSelected.getFaqId());
        if (dao.delete(f)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Delete fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void addFAQs(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        if (dao.add(faqsSelected) != null) {
            msg = "Add successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    public void updateFAQs(ActionEvent event) {

        Dao dao = new Dao();
        String msg = "";
        FacesMessage fmsg;
        if (dao.update(faqsSelected) != null) {
            msg = "Update successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
        } else {
            msg = "Update fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the faqsSelected
     */
    public Faqs getFaqsSelected() {
        return faqsSelected;
    }

    /**
     * @param faqsSelected the faqsSelected to set
     */
    public void setFaqsSelected(Faqs faqsSelected) {
        this.faqsSelected = faqsSelected;
    }

    /**
     * @return the faqsSearch
     */
    public String getFaqsSearch() {
        return faqsSearch;
    }

    /**
     * @param faqsSearch the faqsSearch to set
     */
    public void setFaqsSearch(String faqsSearch) {
        this.faqsSearch = faqsSearch;
    }
}
