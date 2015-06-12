/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class MakersViewBean {

    /**
     * Creates a new instance of MakersViewBean
     */
    public MakersViewBean() {
    }

    public MapModel getMakers(String plat, String plng) {
        MapModel simpleModel = new DefaultMapModel();
        double lat = Double.parseDouble(plat);
        double lng = Double.parseDouble(plng);
        LatLng coord1 = new LatLng(lat, lng);

        //Basic marker
        simpleModel.addOverlay(new Marker(coord1));
        return simpleModel;
    }

}
