package com.entity;
// Generated Jan 4, 2015 2:41:11 PM by Hibernate Tools 4.3.1


import java.io.Serializable;

/**
 * Faqs generated by hbm2java
 */
public class Faqs  implements java.io.Serializable {


     private int faqId;
     private String faqQuestion;
     private String faqAnswer;

    public Faqs() {
    }

	
    public Faqs(int faqId) {
        this.faqId = faqId;
    }
    public Faqs(int faqId, String faqQuestion, String faqAnswer) {
       this.faqId = faqId;
       this.faqQuestion = faqQuestion;
       this.faqAnswer = faqAnswer;
    }
   
    public int getFaqId() {
        return this.faqId;
    }
    
    public void setFaqId(int faqId) {
        this.faqId = faqId;
    }
    public String getFaqQuestion() {
        return this.faqQuestion;
    }
    
    public void setFaqQuestion(String faqQuestion) {
        this.faqQuestion = faqQuestion;
    }
    public String getFaqAnswer() {
        return this.faqAnswer;
    }
    
    public void setFaqAnswer(String faqAnswer) {
        this.faqAnswer = faqAnswer;
    }




}


