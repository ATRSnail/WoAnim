package com.wodm.android.db.dowms;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.android.bean.DowmBean;

import org.eteclab.track.database.dao.BaseDao;
import org.eteclab.track.utils.DBUtil;

import java.util.List;

/**
 * Created by json on 2016/5/16.
 */
public class DowmListDao extends BaseDao<DowmBean> {
    private DowmListDao(Context context, Class<DowmBean> clazz) {
        super(context, clazz, DBUtil.initialize(context));
    }

    private static DowmListDao dao;

    public static DowmListDao getIntence(Context context) {
        if (dao == null) {
            dao = new DowmListDao(context, DowmBean.class);
            ;
        }
        return dao;
    }

    public void update(DowmBean downBean, String url) throws DbException {
        synchronized (mDbUtils) {
            mDbUtils.update(downBean, WhereBuilder.b("url", "=", url), "maxSize", "progress", "status");
        }
    }

    public void deleteByWhere(String columnName, String op, Object value) throws DbException {
        mDbUtils.delete(mClazz, WhereBuilder.b(columnName, op, value));
    }

    public List<DowmBean> findByWhereAnd(String columnName, String op, Object value, WhereBuilder builder) throws DbException {

      return  mDbUtils.findAll(Selector.from(mClazz).where(columnName, op, value).and(builder));
    }
}