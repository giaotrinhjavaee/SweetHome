package com.entity;
// Generated Jan 4, 2015 2:41:11 PM by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Province generated by hbm2java
 */
public class Province  implements java.io.Serializable {


     private int provId;
     private String provName;
     private String provDescription;
     private Set districts = new HashSet(0);

    public Province() {
    }

	
    public Province(int provId) {
        this.provId = provId;
    }
    public Province(int provId, String provName, String provDescription, Set districts) {
       this.provId = provId;
       this.provName = provName;
       this.provDescription = provDescription;
       this.districts = districts;
    }
   
    public int getProvId() {
        return this.provId;
    }
    
    public void setProvId(int provId) {
        this.provId = provId;
    }
    public String getProvName() {
        return this.provName;
    }
    
    public void setProvName(String provName) {
        this.provName = provName;
    }
    public String getProvDescription() {
        return this.provDescription;
    }
    
    public void setProvDescription(String provDescription) {
        this.provDescription = provDescription;
    }
    public Set getDistricts() {
        return this.districts;
    }
    
    public void setDistricts(Set districts) {
        this.districts = districts;
    }




}


