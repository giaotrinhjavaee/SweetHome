/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Feedback;
import com.entity.Province;
import com.lib.Dao;
import com.util.HibernateUtil;
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

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class FeedbackBean {

    private Feedback feedbackSelected;
    private String searchkey = "";

    /**
     * Creates a new instance of FeedbackBean
     */
    public FeedbackBean() {
        if (feedbackSelected == null) {
            feedbackSelected = new Feedback();
        }
    }

    public List<Feedback> getAllFeedback() {
        Dao dao = new Dao();
        List<Feedback> list = null;
        if (searchkey == null || searchkey.equals(null) || searchkey.equals("")) {
            Order o = Order.desc("feedDate");
            list = dao.getByCondition(Feedback.class, o);
        } else {
            Criterion name = Restrictions.like("feedCustomerName", "%" + searchkey + "%");
            Criterion mail = Restrictions.like("feedEmail", "%" + searchkey + "%");
            Criterion phone = Restrictions.like("feedPhone", "%" + searchkey + "%");
            Criterion msg = Restrictions.like("feedMessage", "%" + searchkey + "%");
            Order o = Order.desc("feedDate");
            Disjunction orExp = Restrictions.or(new Criterion[]{name, mail, phone, msg});
            list = dao.getByCondition(Feedback.class, orExp, o);
        }
        return list;
    }

    public void deleteFeedback(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        Feedback p = (Feedback) dao.getById(Feedback.class, feedbackSelected.getFeedId());
        if (dao.delete(p)) {
            msg = "Delete successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            FacesContext.getCurrentInstance().addMessage(null, fmsg);
        }

    }

    /**
     * @return the feedbackSelected
     */
    public Feedback getFeedbackSelected() {
        return feedbackSelected;
    }

    /**
     * @param feedbackSelected the feedbackSelected to set
     */
    public void setFeedbackSelected(Feedback feedbackSelected) {
        this.feedbackSelected = feedbackSelected;
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

}
