/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean;

import com.entity.FavoriteProperty;
import com.entity.Property;
import com.entity.Users;
import com.lib.Dao;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author admin
 */
@ManagedBean
@RequestScoped
public class StatisticBean {

    /**
     * Creates a new instance of NoOfPostInYearBean
     */
    public StatisticBean() {
    }
    private int yearStatisticPostInYear;

    public CartesianChartModel getStatisticPostInYear() {
        CartesianChartModel multiAxisModel = new BarChartModel();

        BarChartSeries boys = new BarChartSeries();
        boys.setLabel("Count");
        Date now = new Date();
        int abc = now.getYear();
        int year = yearStatisticPostInYear == 0 ? now.getYear() : yearStatisticPostInYear;
        Dao dao = new Dao();
        //1
        Date from1 = new Date(year, 0, 1);
        Date to1 = new Date(year, 0, 31);
        Criterion logic1 = Restrictions.between("proCreateDate", from1, to1);
        int count1 = dao.getByCondition(Property.class, logic1).size();
        //2
        Date from2 = new Date(year, 1, 1);
        Date to2 = new Date(year, 1, 28);
        Criterion logic2 = Restrictions.between("proCreateDate", from2, to2);
        int count2 = dao.getByCondition(Property.class, logic2).size();
        //3
        Date from3 = new Date(year, 2, 1);
        Date to3 = new Date(year, 2, 31);
        Criterion logic3 = Restrictions.between("proCreateDate", from3, to3);
        int count3 = dao.getByCondition(Property.class, logic3).size();
        //4
        Date from4 = new Date(year, 3, 1);
        Date to4 = new Date(year, 3, 30);
        Criterion logic4 = Restrictions.between("proCreateDate", from4, to4);
        int count4 = dao.getByCondition(Property.class, logic4).size();
        //5
        Date from5 = new Date(year, 4, 1);
        Date to5 = new Date(year, 4, 31);
        Criterion logic5 = Restrictions.between("proCreateDate", from5, to5);
        int count5 = dao.getByCondition(Property.class, logic5).size();
        //6
        Date from6 = new Date(year, 5, 1);
        Date to6 = new Date(year, 5, 30);
        Criterion logic6 = Restrictions.between("proCreateDate", from6, to6);
        int count6 = dao.getByCondition(Property.class, logic6).size();
        //7
        Date from7 = new Date(year, 6, 1);
        Date to7 = new Date(year, 6, 31);
        Criterion logic7 = Restrictions.between("proCreateDate", from7, to7);
        int count7 = dao.getByCondition(Property.class, logic7).size();
        //8
        Date from8 = new Date(year, 7, 1);
        Date to8 = new Date(year, 7, 31);
        Criterion logic8 = Restrictions.between("proCreateDate", from8, to8);
        int count8 = dao.getByCondition(Property.class, logic8).size();
        //9
        Date from9 = new Date(year, 8, 1);
        Date to9 = new Date(year, 8, 30);
        Criterion logic9 = Restrictions.between("proCreateDate", from9, to9);
        int count9 = dao.getByCondition(Property.class, logic9).size();
        //10
        Date from10 = new Date(year, 9, 1);
        Date to10 = new Date(year, 9, 31);
        Criterion logic10 = Restrictions.between("proCreateDate", from10, to10);
        int count10 = dao.getByCondition(Property.class, logic10).size();
        //11
        Date from11 = new Date(year, 10, 1);
        Date to11 = new Date(year, 10, 30);
        Criterion logic11 = Restrictions.between("proCreateDate", from11, to11);
        int count11 = dao.getByCondition(Property.class, logic11).size();
        //12
        Date from12 = new Date(year, 11, 1);
        Date to12 = new Date(year, 11, 31);
        Criterion logic12 = Restrictions.between("proCreateDate", from12, to12);
        int count12 = dao.getByCondition(Property.class, logic12).size();

        boys.set("01", count1);
        boys.set("02", count2);
        boys.set("03", count3);
        boys.set("04", count4);
        boys.set("05", count5);
        boys.set("06", count6);
        boys.set("07", count7);
        boys.set("08", count8);
        boys.set("09", count9);
        boys.set("10", count10);
        boys.set("11", count11);
        boys.set("12", count12);

        multiAxisModel.addSeries(boys);

        //multiAxisModel.setTitle("Statistic number of post in year");
        multiAxisModel.setMouseoverHighlight(false);
        multiAxisModel.setLegendPosition("ne");
        multiAxisModel.setMouseoverHighlight(false);
        multiAxisModel.setShowDatatip(false);
        multiAxisModel.setShowPointLabels(true);

//        multiAxisModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
//        multiAxisModel.getAxes().put(AxisType.X2, new CategoryAxis("Period"));
        Axis yAxis = multiAxisModel.getAxis(AxisType.Y);
        yAxis.setLabel("Count");
        yAxis.setMin(0);
        yAxis.setMax(200);
        return multiAxisModel;
    }

    private int yearStatisticViewInYear;

    public LineChartModel getStatisticViewInYear() {
        LineChartModel combinedModel = new LineChartModel();

        BarChartSeries boys = new BarChartSeries();
        boys.setLabel("Post");

        Date now = new Date();
        int year = yearStatisticViewInYear == 0 ? now.getYear() : yearStatisticViewInYear;
        Dao dao = new Dao();
        //1
        Date from1 = new Date(year, 0, 1);
        Date to1 = new Date(year, 0, 31);
        Criterion logic1 = Restrictions.between("proCreateDate", from1, to1);
        int count1 = dao.getByCondition(Property.class, logic1).size();
        Criteria c = dao.createCriteria(Property.class);
        c.add(Restrictions.between("proCreateDate", from1, to1));
        c.setProjection(Projections.sum("proHit"));
        Object o = c.uniqueResult();
        long view1 = (long) (o == null ? 0L : o);
        //2
        Date from2 = new Date(year, 1, 1);
        Date to2 = new Date(year, 1, 28);
        Criterion logic2 = Restrictions.between("proCreateDate", from2, to2);
        int count2 = dao.getByCondition(Property.class, logic2).size();
        Criteria c2 = dao.createCriteria(Property.class);
        c2.add(Restrictions.between("proCreateDate", from2, to2));
        c2.setProjection(Projections.sum("proHit"));
        Object o2 = c2.uniqueResult();
        long view2 = (long) ((o2 == null) ? 0L : o2);
        //3
        Date from3 = new Date(year, 2, 1);
        Date to3 = new Date(year, 2, 31);
        Criterion logic3 = Restrictions.between("proCreateDate", from3, to3);
        int count3 = dao.getByCondition(Property.class, logic3).size();
        Criteria c3 = dao.createCriteria(Property.class);
        c3.add(Restrictions.between("proCreateDate", from3, to3));
        c3.setProjection(Projections.sum("proHit"));
        Object o3 = c3.uniqueResult();
        long view3 = (long) ((o3 == null) ? 0L : o3);
        //4
        Date from4 = new Date(year, 3, 1);
        Date to4 = new Date(year, 3, 30);
        Criterion logic4 = Restrictions.between("proCreateDate", from4, to4);
        int count4 = dao.getByCondition(Property.class, logic4).size();
        Criteria c4 = dao.createCriteria(Property.class);
        c4.add(Restrictions.between("proCreateDate", from4, to4));
        c4.setProjection(Projections.sum("proHit"));
        Object o4 = c4.uniqueResult();
        long view4 = (long) ((o4 == null) ? 0L : o4);
        //5
        Date from5 = new Date(year, 4, 1);
        Date to5 = new Date(year, 4, 31);
        Criterion logic5 = Restrictions.between("proCreateDate", from5, to5);
        int count5 = dao.getByCondition(Property.class, logic5).size();
        Criteria c5 = dao.createCriteria(Property.class);
        c5.add(Restrictions.between("proCreateDate", from5, to5));
        c5.setProjection(Projections.sum("proHit"));
        Object o5 = c5.uniqueResult();
        long view5 = (long) ((o5 == null) ? 0L : o5);
        //6
        Date from6 = new Date(year, 5, 1);
        Date to6 = new Date(year, 5, 30);
        Criterion logic6 = Restrictions.between("proCreateDate", from6, to6);
        int count6 = dao.getByCondition(Property.class, logic6).size();
        Criteria c6 = dao.createCriteria(Property.class);
        c6.add(Restrictions.between("proCreateDate", from6, to6));
        c6.setProjection(Projections.sum("proHit"));
        Object o6 = c6.uniqueResult();
        long view6 = (long) ((o6 == null) ? 0L : o6);
        //7
        Date from7 = new Date(year, 6, 1);
        Date to7 = new Date(year, 6, 31);
        Criterion logic7 = Restrictions.between("proCreateDate", from7, to7);
        int count7 = dao.getByCondition(Property.class, logic7).size();
        Criteria c7 = dao.createCriteria(Property.class);
        c7.add(Restrictions.between("proCreateDate", from7, to7));
        c7.setProjection(Projections.sum("proHit"));
        Object o7 = c7.uniqueResult();
        long view7 = (long) ((o7 == null) ? 0L : o7);
        //8
        Date from8 = new Date(year, 7, 1);
        Date to8 = new Date(year, 7, 31);
        Criterion logic8 = Restrictions.between("proCreateDate", from8, to8);
        int count8 = dao.getByCondition(Property.class, logic8).size();
        Criteria c8 = dao.createCriteria(Property.class);
        c8.add(Restrictions.between("proCreateDate", from8, to8));
        c8.setProjection(Projections.sum("proHit"));
        Object o8 = c8.uniqueResult();
        long view8 = (long) ((o8 == null) ? 0L : o8);
        //9
        Date from9 = new Date(year, 8, 1);
        Date to9 = new Date(year, 8, 30);
        Criterion logic9 = Restrictions.between("proCreateDate", from9, to9);
        int count9 = dao.getByCondition(Property.class, logic9).size();
        Criteria c9 = dao.createCriteria(Property.class);
        c9.add(Restrictions.between("proCreateDate", from9, to9));
        c9.setProjection(Projections.sum("proHit"));
        Object o9 = c9.uniqueResult();
        long view9 = (long) ((o9 == null) ? 0L : o9);
        //10
        Date from10 = new Date(year, 9, 1);
        Date to10 = new Date(year, 9, 31);
        Criterion logic10 = Restrictions.between("proCreateDate", from10, to10);
        int count10 = dao.getByCondition(Property.class, logic10).size();
        Criteria c10 = dao.createCriteria(Property.class);
        c10.add(Restrictions.between("proCreateDate", from10, to10));
        c10.setProjection(Projections.sum("proHit"));
        Object o10 = c10.uniqueResult();
        long view10 = (long) ((o10 == null) ? 0L : o10);
        //11
        Date from11 = new Date(year, 10, 1);
        Date to11 = new Date(year, 10, 30);
        Criterion logic11 = Restrictions.between("proCreateDate", from11, to11);
        int count11 = dao.getByCondition(Property.class, logic11).size();
        Criteria c11 = dao.createCriteria(Property.class);
        c11.add(Restrictions.between("proCreateDate", from11, to11));
        c11.setProjection(Projections.sum("proHit"));
        Object o11 = c11.uniqueResult();
        long view11 = (long) ((o11 == null) ? 0L : o11);
        //12
        Date from12 = new Date(year, 11, 1);
        Date to12 = new Date(year, 11, 31);
        Criterion logic12 = Restrictions.between("proCreateDate", from12, to12);
        int count12 = dao.getByCondition(Property.class, logic12).size();
        Criteria c12 = dao.createCriteria(Property.class);
        c12.add(Restrictions.between("proCreateDate", from12, to12));
        c12.setProjection(Projections.sum("proHit"));
        Object o12 = c12.uniqueResult();
        long view12 = (long) ((o12 == null) ? 0L : o12);

        boys.set("01", count1);
        boys.set("02", count2);
        boys.set("03", count3);
        boys.set("04", count4);
        boys.set("05", count5);
        boys.set("06", count6);
        boys.set("07", count7);
        boys.set("08", count8);
        boys.set("09", count9);
        boys.set("10", count10);
        boys.set("11", count11);
        boys.set("12", count12);

        LineChartSeries girls = new LineChartSeries();
        girls.setLabel("View");
        girls.setYaxis(AxisType.Y2);
        girls.set("01", view1);
        girls.set("02", view2);
        girls.set("03", view3);
        girls.set("04", view4);
        girls.set("05", view5);
        girls.set("06", view6);
        girls.set("07", view7);
        girls.set("08", view8);
        girls.set("09", view9);
        girls.set("10", view10);
        girls.set("11", view11);
        girls.set("12", view12);

        combinedModel.addSeries(boys);
        combinedModel.addSeries(girls);

        combinedModel.setTitle("Statistic view of post in year");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(true);
        combinedModel.setShowDatatip(true);
        combinedModel.setShowPointLabels(true);

        combinedModel.getAxes().put(AxisType.X, new CategoryAxis("Month"));
        combinedModel.getAxes().put(AxisType.X2, new CategoryAxis("Period"));

        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(200);

        Axis y2Axis = new LinearAxis("Number");
        y2Axis.setMin(0);
        y2Axis.setMax(100000);

        combinedModel.getAxes().put(AxisType.Y2, y2Axis);

        return combinedModel;
    }

    private int selectTopUserMostPost;

    public List<Object[]> getTopUserMostPost() {
        Dao dao = new Dao();
        Criteria c = dao.createCriteria(Property.class);
        ProjectionList proj = Projections.projectionList();
        proj.add(Projections.groupProperty("users"));
        proj.add(Projections.rowCount(), "Count");
        c.setProjection(proj);
        c.addOrder(Order.desc("Count"));
        c.setFirstResult(0);
        c = selectTopUserMostPost == 0 ? c.setMaxResults(10) : c.setMaxResults(selectTopUserMostPost);
        List<Object[]> a = c.list();
        return a;
    }
    private int selectTopPostMostView;

    public List<Property> getTopPostMostView() {
        Dao dao = new Dao();
        List<Property> list;
        if (selectTopPostMostView == 0) {
            list = dao.getByCondition(Property.class, Order.desc("proHit"), 0, 10);
        } else {
            list = dao.getByCondition(Property.class, Order.desc("proHit"), 0, selectTopPostMostView);
        }
        return list;
    }
    private int selectTopPostMostFav;

    public List<Object[]> getTopPostMostFav() {
        Dao dao = new Dao();
        Criteria c = dao.createCriteria(FavoriteProperty.class);
        ProjectionList proj = Projections.projectionList();
        proj.add(Projections.groupProperty("property"));
        proj.add(Projections.rowCount(), "Count");
        c.setProjection(proj);
        c.addOrder(Order.desc("Count"));
        c.setFirstResult(0);
        c = selectTopPostMostFav == 0 ? c.setMaxResults(10) : c.setMaxResults(selectTopPostMostFav);
        List<Object[]> a = c.list();
        return a;
    }

    /**
     * @return the selectTopUserMostPost
     */
    public int getSelectTopUserMostPost() {
        return selectTopUserMostPost;
    }

    /**
     * @param selectTopUserMostPost the selectTopUserMostPost to set
     */
    public void setSelectTopUserMostPost(int selectTopUserMostPost) {
        this.selectTopUserMostPost = selectTopUserMostPost;
    }

    /**
     * @return the selectTopPostMostView
     */
    public int getSelectTopPostMostView() {
        return selectTopPostMostView;
    }

    /**
     * @param selectTopPostMostView the selectTopPostMostView to set
     */
    public void setSelectTopPostMostView(int selectTopPostMostView) {
        this.selectTopPostMostView = selectTopPostMostView;
    }

    /**
     * @return the selectTopPostMostFav
     */
    public int getSelectTopPostMostFav() {
        return selectTopPostMostFav;
    }

    /**
     * @param selectTopPostMostFav the selectTopPostMostFav to set
     */
    public void setSelectTopPostMostFav(int selectTopPostMostFav) {
        this.selectTopPostMostFav = selectTopPostMostFav;
    }

    /**
     * @return the yearStatisticPostInYear
     */
    public int getYearStatisticPostInYear() {
        return yearStatisticPostInYear;
    }

    /**
     * @param yearStatisticPostInYear the yearStatisticPostInYear to set
     */
    public void setYearStatisticPostInYear(int yearStatisticPostInYear) {
        this.yearStatisticPostInYear = yearStatisticPostInYear;
    }

    /**
     * @return the yearStatisticViewInYear
     */
    public int getYearStatisticViewInYear() {
        return yearStatisticViewInYear;
    }

    /**
     * @param yearStatisticViewInYear the yearStatisticViewInYear to set
     */
    public void setYearStatisticViewInYear(int yearStatisticViewInYear) {
        this.yearStatisticViewInYear = yearStatisticViewInYear;
    }

}
