package com.nandi.disastermanager.dao;


import android.database.Cursor;

import com.nandi.disastermanager.MyApplication;

import com.nandi.disastermanager.entity.LocationInfo;
import com.nandi.disastermanager.search.entity.AreaInfo;
import com.nandi.disastermanager.search.entity.AreaInfoDao;
import com.nandi.disastermanager.search.entity.DaoMaster;
import com.nandi.disastermanager.search.entity.DaoSession;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.DisasterPointDao;
import com.nandi.disastermanager.search.entity.MonitorListPoint;
import com.nandi.disastermanager.search.entity.MonitorListPointDao;
import com.nandi.disastermanager.search.entity.MonitorPoint;
import com.nandi.disastermanager.search.entity.MonitorPointDao;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private static final String SQL_DISTINCT_GRADE = "SELECT DISTINCT " + DisasterPointDao.Properties.DisasterGrade.columnName + " FROM " + DisasterPointDao.TABLENAME;
    private static final String SQL_DISTINCT_MAJOR = "SELECT DISTINCT " + DisasterPointDao.Properties.MajorIncentives.columnName + " FROM " + DisasterPointDao.TABLENAME;


    public GreenDaoManager() {
        //创建一个数据库
        devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "greendao-db", null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    /**
     * 关闭数据连接
     */
    public void close() {
        if (devOpenHelper != null) {
            devOpenHelper.close();
        }
    }

    /**
     * 关闭数据连接
     */
    public static void deleteArea() {
        GreenDaoManager.getInstance().getSession().getAreaInfoDao().deleteAll();
    }

    public static void deleteDisaster() {
        GreenDaoManager.getInstance().getSession().getDisasterPointDao().deleteAll();
    }

    public static void insertLocation(LocationInfo locationInfo) {
        GreenDaoManager.getInstance().getSession().getLocationInfoDao().insert(locationInfo);
    }

    public static void updateLocation(LocationInfo locationInfo) {
        GreenDaoManager.getInstance().getSession().getLocationInfoDao().update(locationInfo);
    }

    public static LocationInfo queryLocation() {
        return GreenDaoManager.getInstance().getSession().getLocationInfoDao().queryBuilder().build().unique();
    }

    public static void deleteAllLocation() {
        GreenDaoManager.getInstance().getSession().getLocationInfoDao().deleteAll();
    }

    /**
     * 增加灾害点数据
     *
     * @param disasterPoint
     */
    public static void insertDisasterPoint(DisasterPoint disasterPoint) {
        DisasterPointDao disasterPointDao = GreenDaoManager.getInstance().getSession().getDisasterPointDao();
        disasterPointDao.insert(disasterPoint);
    }

    /**
     * 增加监测点
     *
     * @param monitorListPoint
     */
    public static void insertMonitorListPoint(MonitorListPoint monitorListPoint) {
        MonitorListPointDao monitorListPointDao = GreenDaoManager.getInstance().getSession().getMonitorListPointDao();
        monitorListPointDao.insert(monitorListPoint);
    }

    /**
     * 增加监测点数据
     *
     * @param monitorPoint
     */
    public static void insertMonitorPoint(MonitorPoint monitorPoint) {
        MonitorPointDao monitorPointDao = GreenDaoManager.getInstance().getSession().getMonitorPointDao();
        monitorPointDao.insert(monitorPoint);
    }

    /**
     * 删除监测点数据
     */
    public static void deleteAllMonitor() {
        GreenDaoManager.getInstance().getSession().getMonitorListPointDao().deleteAll();
    }

    /**
     * 删除监测点数据
     */
    public static void deleteAllMonitorData() {
        GreenDaoManager.getInstance().getSession().getMonitorPointDao().deleteAll();
    }

    /**
     * 根据隐患点编号查询监测点
     */
    public static List<MonitorListPoint> queryMonitorName(String disasterNum) {

        List<MonitorListPoint> monitorList = GreenDaoManager.getInstance().getSession().getMonitorListPointDao().queryBuilder()
                .where(MonitorListPointDao.Properties.DisNum.eq(disasterNum))
                .list();
        return monitorList;
    }

    /**
     * 根据监测点编号查询监测点数据
     */
    public static List<MonitorPoint> queryMonitorData(String monitorNum) {

        List<MonitorPoint> monitorData = GreenDaoManager.getInstance().getSession().getMonitorPointDao().queryBuilder()
                .where(MonitorPointDao.Properties.MonitorId.eq(monitorNum))
                .list();
        return monitorData;
    }

    /*灾害点去重*/
    public static List<String> getDistinct(int id) {
        String sql;
        if (id == 1) {
            sql = SQL_DISTINCT_GRADE;
        } else {
            sql = SQL_DISTINCT_MAJOR;
        }
        ArrayList<String> result = new ArrayList<String>();
        Cursor c = GreenDaoManager.getInstance().getSession().getDatabase().rawQuery(sql, null);
        try {
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }

    /**
     * 查询所有
     */
    public static List<DisasterPoint> queryDisasterData() {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder().build().list();
        return disasterList;
    }

    /**
     * 根据名字查询
     */
    public static List<DisasterPoint> queryDisasterName(String disasterName) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.DisasterName.like("%" + disasterName + "%"))
                .list();
        return disasterList;
    }

    /**
     * 根据id查询地灾点
     */
    public static DisasterPoint queryDisasterById(String id) {

        DisasterPoint disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.Id.like(id)).unique();
        return disasterList;
    }

    /**
     * 根据隐患点类型查询
     */
    public static List<DisasterPoint> queryDisasterType(String type) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.DisasterType.eq(type))
                .list();
        return disasterList;
    }

    /**
     * 根据隐患点类型查询
     */
    public static List<DisasterPoint> queryDisasterByType(String type) {

        return GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.DisasterType.eq(type))
                .list();
    }

    /**
     * 根据隐患点等级查询
     */
    public static List<DisasterPoint> queryDisasterByLevel(String level) {

        return GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.DisasterGrade.eq(level))
                .list();
    }

    /**
     * 增加区域数据
     *
     * @param areaInfo
     */
    public static void insertArea(AreaInfo areaInfo) {
        AreaInfoDao areaInfoDao = GreenDaoManager.getInstance().getSession().getAreaInfoDao();
        areaInfoDao.insert(areaInfo);
    }

    public static List<AreaInfo> queryArea2(String name) {
        List<AreaInfo> areaInfos = GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(2), AreaInfoDao.Properties.Name.eq(name))
                .list();
        return areaInfos;
    }

    public static List<AreaInfo> queryArea3(String name) {
        List<AreaInfo> areaInfos = GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(3), AreaInfoDao.Properties.Name.eq(name))
                .list();
        return areaInfos;
    }

    public static List<AreaInfo> queryArea4(String name) {
        List<AreaInfo> areaInfos = GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(4), AreaInfoDao.Properties.Name.eq(name))
                .list();
        return areaInfos;
    }

    /**
     * 等级查区域
     *
     * @param level
     * @return
     */
    public static List<AreaInfo> queryAreaLevel(int level) {
        List<AreaInfo> areaInfos = GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(level))
                .list();
        return areaInfos;
    }

    /**
     * 等级查区域,
     *
     * @param level
     * @param parentCode 父区域code
     * @return
     */
    public static List<AreaInfo> queryAreaLevel(int level, int parentCode) {
        List<AreaInfo> areaInfos = GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(level), AreaInfoDao.Properties.Area_parent.eq(parentCode))
                .list();
        return areaInfos;
    }


    /**
     * 根据搜索查询
     */
    public static List<DisasterPoint> queryDisasterList(String city, String county, String town,
                                                        String threatLevel, String type, String inducement, String disasterName) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.City.like("%" + city + "%"),
                        DisasterPointDao.Properties.County.like("%" + county + "%"),
                        DisasterPointDao.Properties.Town.like("%" + town + "%"),
                        DisasterPointDao.Properties.DisasterGrade.like("%" + threatLevel + "%"),
                        DisasterPointDao.Properties.DisasterType.like("%" + type + "%"),
                        DisasterPointDao.Properties.MajorIncentives.like("%" + inducement + "%"),
                        DisasterPointDao.Properties.DisasterName.like("%" + disasterName + "%"))
                .list();

        return disasterList;
    }


}
