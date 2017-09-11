package com.nandi.disastermanager.dao;


import com.nandi.disastermanager.MyApplication;
import com.nandi.disastermanager.search.entity.AreaInfo;
import com.nandi.disastermanager.search.entity.AreaInfoDao;
import com.nandi.disastermanager.search.entity.DaoMaster;
import com.nandi.disastermanager.search.entity.DaoSession;
import com.nandi.disastermanager.search.entity.DisasterPoint;
import com.nandi.disastermanager.search.entity.DisasterPointDao;

import java.util.List;

public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;


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
    public static  void deleteAll() {
        GreenDaoManager.getInstance().getSession().getDisasterPointDao().deleteAll();
        GreenDaoManager.getInstance().getSession().getAreaInfoDao().deleteAll();
    }


    /**
     * 增加灾害点数据
     * @param disasterPoint
     */
    public static void insertDisasterPoint(DisasterPoint disasterPoint) {
        DisasterPointDao disasterPointDao=GreenDaoManager.getInstance().getSession().getDisasterPointDao();
        disasterPointDao.insert(disasterPoint);
    }


    /**
     * 查询所有
     */
    public  static List<DisasterPoint> queryDisasterData() {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder().build().list();
        return disasterList;
    }

    /**
     * 根据名字查询
     */
    public  static List<DisasterPoint> queryDisasterName(String disasterName) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                                                .where(DisasterPointDao.Properties.DisasterName.like("%"+disasterName+"%"))
                                                .list();
        return disasterList;
    }

    /**
     * 根据隐患点类型查询
     */
    public  static List<DisasterPoint> queryDisasterType(String type) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.Type.eq(type))
                .list();
        return disasterList;
    }


    /**
     * 增加区域数据
     * @param areaInfo
     */
    public static void insertArea(AreaInfo areaInfo) {
        AreaInfoDao areaInfoDao=GreenDaoManager.getInstance().getSession().getAreaInfoDao();
        areaInfoDao.insert(areaInfo);
    }

    public static List<AreaInfo> queryArea2(String name){
        List<AreaInfo> areaInfos=GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(2),AreaInfoDao.Properties.Name.eq(name))
                .list();
        return areaInfos;
    }

    public static List<AreaInfo> queryArea3(String name){
        List<AreaInfo> areaInfos=GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(3),AreaInfoDao.Properties.Name.eq(name))
                .list();
        return areaInfos;
    }

    public static List<AreaInfo> queryArea4(String name){
        List<AreaInfo> areaInfos=GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(4),AreaInfoDao.Properties.Name.eq(name))
                .list();
        return areaInfos;
    }

    /**
     * 等级查区域
     * @param level
     * @return
     */
    public static List<AreaInfo> queryAreaLevel(int level){
        List<AreaInfo> areaInfos=GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(level))
                .list();
        return areaInfos;
    }

    /**
     * 等级查区域,
     * @param level
     * @param parentCode 父区域code
     * @return
     */
    public static List<AreaInfo> queryAreaLevel(int level,int parentCode){
        List<AreaInfo> areaInfos=GreenDaoManager.getInstance().getSession().getAreaInfoDao().queryBuilder()
                .where(AreaInfoDao.Properties.Level.eq(level),AreaInfoDao.Properties.Area_parent.eq(parentCode))
                .list();
        return areaInfos;
    }


    /**
     * 根据搜索查询
     */
    public  static List<DisasterPoint> queryDisasterList(String city, String county, String town,
                                                         String threatLevel, String type, String inducement, String disasterName) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.City.like("%"+city+"%"),
                        DisasterPointDao.Properties.County.like("%"+county+"%"),
                        DisasterPointDao.Properties.Town.like("%"+town+"%"),
                        DisasterPointDao.Properties.ThreatLevel.like("%"+threatLevel+"%"),
                        DisasterPointDao.Properties.Type.like("%"+type+"%"),
                        DisasterPointDao.Properties.Inducement.like("%"+inducement+"%"),
                        DisasterPointDao.Properties.DisasterName.like("%"+disasterName+"%"))
                .list();

        return disasterList;
    }


}
