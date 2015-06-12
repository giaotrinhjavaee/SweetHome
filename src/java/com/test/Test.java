/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import com.entity.Property;
import com.entity.Province;
import com.lib.Dao;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author admin
 */
public class Test {
    public static void main(String[] args) {
        Dao dao = new Dao();
//        String sql = "from Property where exchangeType =1";
//       List abc = dao.executeSQLQuery(sql);
//        System.out.println(abc.size());

        List<Property> list = null;
        Criterion type = Restrictions.ilike("exchangeType", 1);
        list = dao.getByCondition(Property.class, type);
    }
}
