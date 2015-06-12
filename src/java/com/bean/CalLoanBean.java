/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class CalLoanBean {

    private double totalLoan;
    private int term;
    private double rate;
    private int type = 1;

    /**
     * Creates a new instance of CalLoanBean
     */
    public CalLoanBean() {
    }

    public List<LoanInformation> initLoan() {
        List<LoanInformation> list = new ArrayList<LoanInformation>();
        double laiTruHangThang = term != 0 ? (rate / term) : 0;
        double laiSauKhiTru = rate;
        for (int i = 0; i < term; i++) {
            if (type == 1) {
                LoanInformation loan = new LoanInformation();
                loan.index = i + 1;
                
                if (i != 0) {
                    laiSauKhiTru -= laiTruHangThang;
                }
                loan.traGoc = totalLoan / term;
                loan.laiPhaiTra = Math.ceil((laiSauKhiTru / 100) * totalLoan);
                loan.tienPhaiTra = loan.getTraGoc() + loan.getLaiPhaiTra();
                list.add(loan);
            } else {
                LoanInformation loan = new LoanInformation();
                loan.index = i + 1;
                loan.traGoc = totalLoan / term;
                loan.laiPhaiTra = Math.ceil((rate / 100) * totalLoan);
                loan.tienPhaiTra = loan.getTraGoc() + loan.getLaiPhaiTra();
                list.add(loan);
            }
        }
        return list;
    }

    /**
     * @return the totalLoan
     */
    public double getTotalLoan() {
        return totalLoan;
    }

    /**
     * @param totalLoan the totalLoan to set
     */
    public void setTotalLoan(double totalLoan) {
        this.totalLoan = totalLoan;
    }

    /**
     * @return the term
     */
    public int getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(int term) {
        this.term = term;
    }

    /**
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double rate) {
        this.rate = rate;
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

    public class LoanInformation {

        private int index;
        private double traGoc;
        private double laiPhaiTra;
        private double tienPhaiTra;

        /**
         * @return the index
         */
        public int getIndex() {
            return index;
        }

        /**
         * @return the traGoc
         */
        public double getTraGoc() {
            return traGoc;
        }

        /**
         * @return the laiPhaiTra
         */
        public double getLaiPhaiTra() {
            return laiPhaiTra;
        }

        /**
         * @return the tienPhaiTra
         */
        public double getTienPhaiTra() {
            return tienPhaiTra;
        }
    }

}
