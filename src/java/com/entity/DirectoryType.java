package com.entity;
// Generated Jan 4, 2015 2:41:11 PM by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DirectoryType generated by hbm2java
 */
public class DirectoryType  implements java.io.Serializable {


     private int dirtId;
     private String dirtName;
     private String dirtDescription;
     private Set directories = new HashSet(0);

    public DirectoryType() {
    }

	
    public DirectoryType(int dirtId) {
        this.dirtId = dirtId;
    }
    public DirectoryType(int dirtId, String dirtName, String dirtDescription, Set directories) {
       this.dirtId = dirtId;
       this.dirtName = dirtName;
       this.dirtDescription = dirtDescription;
       this.directories = directories;
    }
   
    public int getDirtId() {
        return this.dirtId;
    }
    
    public void setDirtId(int dirtId) {
        this.dirtId = dirtId;
    }
    public String getDirtName() {
        return this.dirtName;
    }
    
    public void setDirtName(String dirtName) {
        this.dirtName = dirtName;
    }
    public String getDirtDescription() {
        return this.dirtDescription;
    }
    
    public void setDirtDescription(String dirtDescription) {
        this.dirtDescription = dirtDescription;
    }
    public Set getDirectories() {
        return this.directories;
    }
    
    public void setDirectories(Set directories) {
        this.directories = directories;
    }




}


