/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.lib.Dao;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.transaction.spi.LocalStatus;
import org.hibernate.mapping.PersistentClass;

/**
 *
 * @author admin
 */
public class HibernateUtil {

    private static HibernateUtil instance;
    private Configuration configuration;
    private SessionFactory sessionFactory;
    private Session session;

    public synchronized static HibernateUtil getInstance() {
        if (instance == null) {
            instance = new HibernateUtil();
        }
        return instance;
    }

    private synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getConfiguration().buildSessionFactory();
        }
        return sessionFactory;
    }

    public synchronized Session getCurrentSession() {
        if (session == null) {
            session = getSessionFactory().openSession();
            session.setFlushMode(FlushMode.COMMIT);
            System.out.println("session opened.");
        }
        return session;
    }

    private synchronized Configuration getConfiguration() {
        if (configuration == null) {
            System.out.print("configuring Hibernate ... ");
            try {
                configuration = new Configuration().configure();
                //configuration.addClass(Contact.class);
                System.out.println("ok");
            } catch (HibernateException e) {
                System.out.println("failure");
                e.printStackTrace();
            }
        }
        return configuration;
    }

    public void reset() {
        Session session = getCurrentSession();
        if (session != null) {
            session.flush();
            if (session.isOpen()) {
                System.out.print("closing session ... ");
                session.close();
                System.out.println("ok");
            }
        }
        SessionFactory sf = getSessionFactory();
        if (sf != null) {
            System.out.print("closing session factory ... ");
            sf.close();
            System.out.println("ok");
        }
        this.configuration = null;
        this.sessionFactory = null;
        this.session = null;
    }

    /**
     * Xóa toàn bộ object đang được quản lý bởi Hibernate
     */
    public void clear() {
        this.session.clear();
    }

    public void evict(Object o) {
        this.session.evict(o);
    }

//    public PersistentClass getClassMapping(Class entityClass) {
//        return getConfiguration().getClassMapping(entityClass.getName());
//    }
    public void beginTransaction() {
        Transaction t = this.session.getTransaction();
        if (t.getLocalStatus() == LocalStatus.NOT_ACTIVE) {
            this.session.beginTransaction();
        }
    }

    public boolean commit() {
        if (session != null) {
            try {
                session.getTransaction().commit();
                Transaction t = this.session.getTransaction();
                LocalStatus abc = t.getLocalStatus();
                return true;
            } catch (HibernateException ex) {
                rollBack();
                FacesMessage fmsg = getMessage(ex);
                FacesContext.getCurrentInstance().addMessage(null, fmsg);
                return false;
            }
        }
        return false;
    }

    FacesMessage getMessage(HibernateException ex) {
        String msg = "";
        FacesMessage fmsg;

        if (ex.getClass().getName().equals("org.hibernate.exception.ConstraintViolationException")) {
            msg = "Data was used. You cannot delete.";
            fmsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
        } else {
            msg += ex.toString();
            fmsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null);
        }

        return fmsg;
    }

    public void rollBack() {
        if (session != null) {
            session.getTransaction().rollback();
        }
    }

}
