/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.Feedback;
import com.lib.Dao;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class SendMailBean {
    
    private Feedback feedback;

    /**
     * Creates a new instance of SendMailBean
     */
    public SendMailBean() {
        if (feedback==null) {
            feedback = new Feedback();
        }
    }
    
    public void sendMail(ActionEvent event) {
        Dao dao = new Dao();
        String msg;
        FacesMessage fmsg;
        feedback.setFeedDate(new Date());
        if (dao.add(feedback) != null) {
            msg = "Add successfull";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null);
            feedback = new Feedback();
        } else {
            msg = "Add fail";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }
        FacesContext.getCurrentInstance().addMessage(null, fmsg);
    }

    /**
     * @return the feedback
     */
    public Feedback getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
    
}
