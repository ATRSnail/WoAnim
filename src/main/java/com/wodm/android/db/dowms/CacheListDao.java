package com.wodm.android.db.dowms;

import android.content.Context;

import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.wodm.android.bean.CacheBean;

import org.eteclab.track.database.dao.BaseDao;
import org.eteclab.track.utils.DBUtil;

/**
 * Created by json on 2016/5/16.
 */
public class CacheListDao extends BaseDao<CacheBean> {
    public CacheListDao(Context context, Class<CacheBean> clazz) {
        super(context, clazz, DBUtil.initialize(context));
    }

    public void deleteByWhere(String columnName, String op, Object value) throws DbException {
        mDbUtils.delete(mClazz, WhereBuilder.b(columnName, op, value));
    }
}