package com.wodm.android.db;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.ObjectBean;

import org.eteclab.track.database.dao.BaseDao;
import org.eteclab.track.utils.DBUtil;

/**
 * Created by json on 2016/5/10.
 */
public class DownDao extends BaseDao<ChapterBean> {
    public DownDao(Context context, Class<ChapterBean> clazz) {
        super(context, clazz, DBUtil.initialize(context));
    }
}
