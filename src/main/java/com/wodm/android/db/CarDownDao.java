package com.wodm.android.db;

import android.content.Context;

import com.wodm.android.bean.ChapterBean;

import org.eteclab.track.database.dao.BaseDao;
import org.eteclab.track.utils.DBUtil;

/**
 * Created by moon1 on 2016/5/11.
 */
public class CarDownDao extends BaseDao<ChapterBean> {
    public CarDownDao(Context context, Class<ChapterBean> clazz) {
        super(context, clazz, DBUtil.initialize(context));
    }
}
