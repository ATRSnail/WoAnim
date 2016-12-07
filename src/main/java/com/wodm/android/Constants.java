package com.wodm.android;

import android.os.Environment;

import com.wodm.android.bean.MedalInfoBean;
import com.wodm.android.bean.UserInfoBean;

import java.io.File;

/**
 * @description 常量类
 */
public class Constants {

    public static final String DATABASE_NAME = "wodm.db";// 数据库的名称

    public static final String APPKEY = "3z9jimbc8irarq6t6ty2wrwyc564mgt5";

    public static final String WX_APP_SELECT = "71506941d4e4b7ba12b02bc0f0a49973";

    public static final boolean DEBUG = true;

    public static UserInfoBean CURRENT_USER/* = new UserBean()*/;
    public static MedalInfoBean MEDALINFOBEAN;

    public static MedalInfoBean getMEDALINFOBEAN() {
        return MEDALINFOBEAN;
    }

    public static void setMEDALINFOBEAN(MedalInfoBean MEDALINFOBEAN) {
        Constants.MEDALINFOBEAN = MEDALINFOBEAN;
    }

    //             public final static String HOST = "http://wodm.9mobi.cn/api/v1/";// 正式
    //http://202.106.63.98:8990/wodm-api/
//     public final static String HOST = "http://202.106.63.98:8990/wodm-api/api/v1/";// 正式
//    public final static String HOST = "http://202.106.63.99:8990/wodm-api/api/v1/";// 正式
    public final static String HOST = "http://172.16.2.125:8899/api/v1/";// 公司测试服务器
//    public final static String HOST = "http://59.108.94.55:8899/api/v1/";// 公司外网服务器
//    public final static String HOST = "http://172.16.3.49:8080/api/v1/";//  小康子

    //分享详情页
    public final static String SHARE_URL = "http://mk.wo.com.cn/html/donghuaxiangqingye.html?id=";

    // 漫画详情页
    public final static String SHARE_ANIM_URL = "http://mk.wo.com.cn/html/donghuaxiangqingye.html?id=";
    //动画详情页
    public final static String SHARE_CARREAD_URL = "http://mk.wo.com.cn/html/manhuaxiangqingye.html?id=";
    public final static String URL_SCORE = HOST + "user/countScore";// 获取积分
    //    public final static String URL_SIGNIN = HOST + "user/checkin";// 签到
    public final static String URL_SIGNIN = HOST + "newuser/checkin";// 签到
    public final static String URL_CHECK_SIGNIN = HOST + "user/judgeCheckin?userId=";// 签到

    //    public final static String ULR_COLLECT = HOST + "resource/collect?userId=";// 作品收藏
    public final static String ULR_COLLECT = HOST + "newuser/collect?userId=";// 作品收藏

    public static final String USER_GET_CODE = HOST + "user/code";//获取验证码
    public static final String USER_REGIST = HOST + "newuser/regist";//注册
    //忘记密码
    public static final String USER_FORGET_PASSWORD = HOST + "/newuser/editPassword";//注册
    public static final String USER_LOGIN = HOST + "user/login";//登录
    public static final String BIND_WEIXIN = HOST + "newuser/weixin/binding";// 微信绑定的接口
    public static final String USER_LOGIN_WX = HOST + "user/weixin/login";//登录
    public static final String USER_LOGOUT = HOST + "user/logout";//退出登录
    public static final String USER_TOKEN = HOST + "user/token/";//TOKEN验证
    public static final String USER_RESET_PWD = HOST + "user/password/reset";//忘记密码


    public static final String USER_UPLOAD_PORTRAIT = HOST + "/user/uploadPortrait/";//上传头像
    public static final String USER_ADD_WATCH_RECORD = HOST + "/newuser/addWatchRecord";//记录用户观看作品

    public static final String GET_CATEGORY = HOST + "category";//获取分类列表
    public static final String GET_NEWS_CATEGORY = HOST + "news/category";//获取新闻分类列表
    public static final String GET_CATRESOURCE = HOST + "resource";//获取作品列表
    //作品章节列表
    public static final String URL_CHAPTER_LIST = HOST + "chapter?resourceId=";
    //作品详情
    public static final String URL_CARTTON_DETAIL = HOST + "resource/";
    //获取消息列表
    public static final String URL_USER_NOTICE = HOST + "user/notice";
    ///newuser/deleteWatchRecord  批量删除用户的浏览记录(足迹)
    public static final String URL_USER_DELETE_WATCH_RECORD = HOST + "newuser/deleteWatchRecord?userId=";

    ///newuser/deleteCollection 批量删除用户收藏的内容
    public static final String URL_USER_DELETE_COLLECTION = HOST + "newuser/deleteCollection?userId=";


    public static final String URL_HOME_TYPE = HOST + "column/list";
    //	http://wodm.eteclab.com/api/v1/column/findResourceByColumnId?columnId=1&page=1&size=20
    public static final String URL_HOME_TYPE_LIST = HOST + "column/findResourceByColumnId";

    //首页轮播图
    public static final String URL_HOME_TOP_LIST = HOST + "resource/topshow?location=1";

    //广场新闻
    public static final String URL_GUANGCHANG_LIST = HOST + "news/findNewsByCategoryId?categoryId=";

    //模糊搜索
    public static final String URL_SEARCH = HOST + "search?keyword=";
    //获取用户信息
    public static final String URL_USERINFO = HOST + "user/profile?userId=";

    // 获取作品评论
    public static final String URL_GET_COMMENTS = HOST + "resource/comment?sortBy=1&resourceId=";
    public static final String URL_COMMENTS = HOST + "comment";//评论
    public static final String URL_FEEDBACK = HOST + "newuser/opinion";//意见反馈
    public static final String URL_ABOUT = HOST + "app/legal?appkey=" + APPKEY;//关于我们
    //    public static final String URL_USER = HOST + "user/profile20160920";//修改用户资料
    public static final String URL_USER = HOST + "newuser/editProfile";//修改用户资料
    public static final String URL_NEWS_GET = HOST + "news/get";//咨询详情
    public static final String URL_GET_BARRAGE = HOST + "resource/barrage";//获取弹幕信息的
    public static final String URL_GET_ADD_BARRAGE = HOST + "barrage";//添加弹幕

    public static final String APP_UPGRADE_URL = HOST + "app/upgrade";
    /*新增接口*/

    //所有勋章
    public static final String APP_GET_MEDALLIST = HOST + "/newuser/medalList?userId=";
    public static final String APP_GET_USERINFO = HOST + "newuser/profile?userId=";
    //任务状态
    public static final String APP_GET_TASKSTATUS = HOST + "newuser/taskStatus?userId=";
    public static final String APP_GET_OPEN_VIP = HOST + "/newuser/openVip?userId=";
    public static final String APP_GET_VIP_TIME = HOST + "newuser/getVipTime?userId=";
    public static final String APP_PERFECT_USERINFO = HOST + "newuser/perfectUserInfo?userId=";
    //更新点击量
    public static final String APP_UPDATERESOURCECOUNT = HOST + "newuser/updateResourceCount?resourceId=";
    //获取点击量
    public static final String APP_GETATERESOURCECOUNT = HOST + "newuser/getResourceCount?resourceId=";
    public static final String BUYHISTORY = HOST + "product/getOrderList";//获取用户的交易记录
    public static final String APP_GET_WEATHRE_ISRECEIVER="newuser/isReceive?userId=";
    public static final String APP_GET_SHARE=HOST + "/newuser/share";
    public static final String APP_GET_WATCHNEWS=HOST + "/newuser/watchNews?userId=";
    public static final String APP_GET_MALL_TOUXIANG=HOST + "/product/pageProduct?userId=";
    public static final String APP_GET_PRODUCT_COLUMNLIST=HOST + "/product/columnlist";
    public static final String APP_GET_PRODUCT_PAGEBYCLIUMN=HOST + "/product/pageBycolumn?userId=";
    public static final String APP_GET_PRODUCT_BY_PRODUCTTYPE=HOST + "/product/getProductByProductType?productType=";
    public static final String APP_GET_PRODUCT_IS_BUY=HOST + "newuser/isBuy?userId=";
    public static final String APP_GET_BUY_PRODUCT=HOST + "newuser/buyProduct?userId=";
    public static final String APP_GET_MALL_OF_USER=HOST + "product/getPendantListByType?userId=";
    public static final String APP_GET_MALL_OF_USER_PENADANT=HOST + "newuser/usePendant?userId=";
    public static final String APP_GET_MALL_OF_PRODUCT_LIST=HOST + "product/list?userId=";
    public static final String APP_GET_MALL_OF_SAVEUSERBEHAVIOUR=HOST + "behavior/saveUserBehaviour";//启动时使用接口
    public static final String APP_GET_MALL_OF_SAVEUSERBEHAVIOURINFO=HOST + "behavior/saveUserBehaviourInfo";//上传用户数据是是使用接口
    public static final String APP_GET_MALL_OF_UPDATEUSERBEHAVIOURINFO=HOST + "behavior/updateUserBehaviour";//退出APP时使用
    public static final String APP_GET_MAIN_ADVERTISEMENT=HOST + "resource/advertisement";//获取广告
    public static final String APP_GET_MAIN_MORE_RESCOURE=HOST + "resource/listResourceByColumnId";//点击栏目获取更多的资源
    public static String OFFTIME;
    public static final String SAVE_USER_ATTENTION=HOST + "behavior/saveUserFollow?userId=";//保存用户关注的内容
    public static final String GET_USER_ATTENTION=HOST + "behavior/findFollowList?userId=";//获取用户的关注/粉丝的列表
    public static final String REMOVE_USER_ATTENTION=HOST + "behavior/deleteUserFollow?userId=";//取消用户关注的内容
    public static final String GETMESSAGELIST=HOST + "behavior/messageList?userId=";//获取消息的首页内容
    public static final String DELETEMESSAGE=HOST + "behavior/batchDelMessage?userId=";//批量删除用户的消息
    public static final String READMESSAGELIST=HOST + "behavior/readMessage?userId=";//读取一则消息
    public static final String DELETEALLMESSAGE=HOST + "behavior/delAllMessage?userId=";// 根据删除指定类别下面的所有的消息(清空的功能)
    public static final String SAVEUSERLIKE=HOST + "behavior/saveUserLike";// 保存用户点赞的内容
    public static final String DELETEUSERLIKE=HOST + "behavior/deleteUserLike";// 用户取消点赞的
    public static final String SystemMessageList=HOST + "behavior/systemMessageList?userId=";// 获取所有的系统通知的消息内容
    public static final String ReplyeMessageList=HOST + "behavior/replyMessageList?userId=";// 获取所有的 有人@我的消息
    public static final String LikeMessageList=HOST + "behavior/likeMessageList?userId=";// 获取所有的点赞的详情的列表


    public static final String SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "unicom.dm" + File.separator;

}
