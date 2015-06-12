/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;
import org.hibernate.criterion.Criterion;
/**
 *
 * @author admin
 */
public interface IDao<T> {
     Serializable add(T entity);

    int addRange(T[] entity);

    boolean addOrUpdate(T entity);

    T update(T entity);

    boolean delete(T entity);

    List<T> getAll(Class<T> clazz);

    T getById(Class<T> clazz, Serializable entity);

    List executeSQLQuery(String sql);

    List<T> getByCondition(Class<T> clazz, Criterion where);

    ResultSet selectFromProceduce(String nameProc, Object[] values);
}
