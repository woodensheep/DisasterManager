package com.nandi.disastermanager.dao;


import com.nandi.disastermanager.MyApplication;
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
     * 增加数据
     * @param disasterPoint
     */
    public static void insertDisasterPoint(DisasterPoint disasterPoint) {
        DisasterPointDao disasterPointDao=GreenDaoManager.getInstance().getSession().getDisasterPointDao();
        disasterPointDao.insert(disasterPoint);
    }


    /**
     * 查询所有
     */
    private  static List<DisasterPoint> queryData() {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder().build().list();
        return disasterList;
    }

    /**
     * 根据名字查询
     */
    private  static List<DisasterPoint> queryDisasterName(String disasterName) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                                                .where(DisasterPointDao.Properties.DisasterName.like("%"+disasterName+"%"))
                                                .list();
        return disasterList;
    }

    /**
     * 根据隐患点类型查询
     */
    private  static List<DisasterPoint> queryType(String type) {

        List<DisasterPoint> disasterList = GreenDaoManager.getInstance().getSession().getDisasterPointDao().queryBuilder()
                .where(DisasterPointDao.Properties.Type.eq(type))
                .list();
        return disasterList;
    }



}
