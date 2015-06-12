/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib;

import com.util.HibernateUtil;
import com.util.IDao;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author admin
 */
/**
 *
 * @author admin
 * @param <T>
 */
public class Dao<T> implements IDao<T> {

    protected Session session;
    protected Criteria crit;
    protected Query query;
    private boolean hasTransaction = false;

    /**
     *
     * @param hasTransaction Có transaction hay không
     */
    public Dao(boolean hasTransaction) {
        this.hasTransaction = hasTransaction;
        session = HibernateUtil.getInstance().getCurrentSession();
    }

    /**
     *
     */
    public Dao() {
        session = HibernateUtil.getInstance().getCurrentSession();
    }

    /**
     * Insert vào db
     *
     * @param entity Instance của entity trong packages "com.entity"
     * @return Giá trị khóa chính của dòng mới được insert
     */
    @Override
    public Serializable add(T entity) {
        Serializable id = null;
        setHasException(false);
        HibernateUtil.getInstance().beginTransaction();
        HibernateUtil.getInstance().clear();
        id = session.save(entity);
        if (!hasTransaction) {
            if (!HibernateUtil.getInstance().commit()) {
                id = null;
            }
        }
        return id;
    }

    private boolean hasException = false;
    private Exception ex;

    /**
     * Insert 1 mảng entity vào db
     *
     * @param entity Mảng Instance của entity trong packages "com.entity". Ví
     * dụ: (new Employees[] {emp,emp1})
     * @return Số dòng được thêm vào db
     */
    @Override
    public int addRange(T[] entity) {
        int count = 0;
        HibernateUtil.getInstance().beginTransaction();
        for (T item : entity) {
            session.save(entity);
            count++;
        }
        if (!hasTransaction) {
            HibernateUtil.getInstance().commit();
        }
        return count;
    }

    /**
     * Insert hoặc Update entity vào db
     *
     * @param entity Instance của entity trong packages "com.entity"
     */
    @Override
    public boolean addOrUpdate(T entity) {
        boolean isOK = false;
        HibernateUtil.getInstance().beginTransaction();
        session.saveOrUpdate(entity);
        if (!hasTransaction) {
            isOK = HibernateUtil.getInstance().commit();
        }
        return isOK;
    }

    /**
     * Update entity vào db
     *
     * @param entity Instance của entity trong packages "com.entity"
     * @return Instance của entity được update
     */
    @Override
    public T update(T entity) {
        HibernateUtil.getInstance().beginTransaction();
        T object = (T) session.merge(entity);
        if (!hasTransaction) {
            HibernateUtil.getInstance().commit();
        }
        return object;
    }

    /**
     * Delete entity
     *
     * @param entity Instance của entity trong packages "com.entity"
     */
    @Override
    public boolean delete(T entity) {
        boolean isOK = false;
        HibernateUtil.getInstance().beginTransaction();
        session.delete(entity);
        if (!hasTransaction) {
            isOK = HibernateUtil.getInstance().commit();
        }
        return isOK;
    }

    /**
     * Lấy tất cả các dòng trong 1 bảng nào đó
     *
     * @param clazz Class của entity trong packages "com.entity". Ví dụ:
     * Employees.class
     * @return List tất cả các dòng trong 1 bảng nào đó
     */
    @Override
    public List<T> getAll(Class<T> clazz) {
        crit = session.createCriteria(clazz);
        return this.crit.list();
    }

    public List<T> getAll(Class<T> clazz, Order o) {
        crit = session.createCriteria(clazz);
        crit.addOrder(o);
        return this.crit.list();
    }

    public List<T> getAll(Class<T> clazz, Order o, int first, int max) {
        crit = session.createCriteria(clazz);
        crit.addOrder(o);
        crit.setFirstResult(first);
        crit.setMaxResults(max);
        return this.crit.list();
    }

    public List<T> getAll(Class<T> clazz, int first, int max) {
        crit = session.createCriteria(clazz);
        crit.setFirstResult(first);
        crit.setMaxResults(max);
        return this.crit.list();
    }

    /**
     * Lấy 1 dòng của 1 bảng bằng khóa chính
     *
     * @param clazz Class của entity trong packages "com.entity". Ví dụ:
     * Employees.class
     * @param entity Giá trị khóa chính
     * @return 1 instance của 1 entity
     */
    @Override
    public T getById(Class<T> clazz, Serializable entity) {
        return (T) session.load(clazz, entity);
    }

    public T getById1(Class<T> clazz, Serializable entity) {
        return (T) session.get(clazz, entity);
    }

    /**
     * Select dữ liệu theo HQL
     *
     * @param sql Là 1 HQL(Hibernate query language). Ví dụ: FROM Employee
     * SELECT E.firstName FROM Employee E ----- FROM Employee E WHERE E.id > 10
     * ORDER BY E.salary DESC ----- SELECT SUM(E.salary), E.firtName FROM
     * Employee E GROUP BY E.firstName ----
     * @return List dữ liệu
     */
    @Override
    public List executeSQLQuery(String sql) {
        HibernateUtil.getInstance().beginTransaction();
        query = session.createQuery(sql);
        List list = query.list();
        HibernateUtil.getInstance().commit();
        return list;
    }

    /**
     * Select dữ liệu từ 1 bảng có điều kiện where
     *
     * @param clazz Class của entity trong packages "com.entity". Ví dụ:
     * Employees.class
     * @param where Điều kiện where. Ví dụ: Restrictions.gt("salary", 2000) ||
     * Restrictions.like("firstName", "zara%") || LogicalExpression orExp =
     * Restrictions.or(code, name);
     * @return List dữ liệu
     */
    @Override
    public List<T> getByCondition(Class<T> clazz, Criterion where) {
        crit = session.createCriteria(clazz);
        crit.add(where);
        return crit.list();
    }

    public List<T> getByCondition(Class<T> clazz, Criterion where, int first, int max) {
        crit = session.createCriteria(clazz);
        crit.add(where);
        crit.setFirstResult(first);
        crit.setMaxResults(max);
        return crit.list();
    }

    public List<T> getByCondition(Class<T> clazz, Order order) {
        crit = session.createCriteria(clazz);
        crit.addOrder(order);
        return crit.list();
    }

    public List<T> getByCondition(Class<T> clazz, Order order, int first, int max) {
        crit = session.createCriteria(clazz);
        crit.addOrder(order);
        crit.setFirstResult(first);
        crit.setMaxResults(max);
        return crit.list();
    }

    public List<T> getByCondition(Class<T> clazz, Criterion where, Order order) {
        crit = session.createCriteria(clazz);
        crit.add(where);
        crit.addOrder(order);
        return crit.list();
    }

    public List<T> getByCondition(Class<T> clazz, Criterion where, Order order, int first, int max) {
        crit = session.createCriteria(clazz);
        crit.add(where);
        crit.addOrder(order);
        crit.setFirstResult(first);
        crit.setMaxResults(max);
        return crit.list();
    }

    public List<T> getByCondition(Class<T> clazz, Criterion where, Order[] order, int first, int max) {
        crit = session.createCriteria(clazz);
        crit.add(where);
        for (Order o : order) {
            crit.addOrder(o);
        }

        crit.setFirstResult(first);
        crit.setMaxResults(max);
        return crit.list();
    }

    /**
     * Select dữ liệu từ procedure
     *
     * @param nameProc Tên của procedure (Ví dụ: exec GetABC ?,?,?)
     * @param values Tham số truyền vào cho proc
     * @return ResultSet
     */
    @Override
    public ResultSet selectFromProceduce(final String nameProc, final Object[] values) {

        ResultSet rs = session.doReturningWork(new ReturningWork<ResultSet>() {
            @Override
            public ResultSet execute(Connection cnctn) throws SQLException {
                CallableStatement cs = null;
                try {
                    cs = cnctn.prepareCall(nameProc);
                    for (int i = 0; i < values.length; i++) {
                        cs.setObject(i + 1, values[i]);
                    }
                    ResultSet rs = cs.executeQuery();
                    return rs;
                } catch (SQLException ex) {
                    Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            }
        });

        return rs;
    }

    /**
     *
     * @param type Class của entity trong packages "com.entity". Ví dụ:
     * Employees.class
     * @return
     */
    public Criteria createCriteria(Class type) {
        crit = session.createCriteria(type);
        return crit;
    }

    /**
     * @return the hasTransaction
     */
    public boolean isHasTransaction() {
        return hasTransaction;
    }

    /**
     * @param hasTransaction the hasTransaction to set
     */
    public void setHasTransaction(boolean hasTransaction) {
        this.hasTransaction = hasTransaction;
    }

    /**
     * @return the hasException
     */
    public boolean isHasException() {
        return hasException;
    }

    /**
     * @param hasException the hasException to set
     */
    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }

    /**
     * @return the ex
     */
    public Exception getEx() {
        return ex;
    }

    /**
     * @param ex the ex to set
     */
    public void setEx(Exception ex) {
        this.ex = ex;
    }

}
