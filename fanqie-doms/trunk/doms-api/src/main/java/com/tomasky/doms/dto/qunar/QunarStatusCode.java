package com.tomasky.doms.dto.qunar;

/**
 * Create by jame
 * Date: 2016/6/7
 * Version: 1.0
 * DOTO: 去哪儿系统服务状态码说明
 * 状态码 状态码描述
 */
public class QunarStatusCode {
    public static final Integer SUCCESS = 0;//处理成功
    public static final Integer ERROR_10000 = 10000;//未知异常
    public static final Integer ERROR_10001 = 10001;// 系统处理异常
    public static final Integer ERROR_1000 = 1000;// //缺少pmsId参数
    public static final Integer ERROR_1001 = 1001;// //缺少channelCode参数
    public static final Integer ERROR_1002 = 1002;////缺少PMS酒店代码
    public static final Integer ERROR_1003 = 1003;////缺少渠道酒店代码
    public static final Integer ERROR_1004 = 1004;// //缺少PMS价格计划代码
    public static final Integer ERROR_1005 = 1005;// //缺少渠道价格计划代码
    public static final Integer ERROR_1006 = 1006;////缺少PMS价格计划名称
    public static final Integer ERROR_1007 = 1007;// //缺少渠道价格计划名称
    public static final Integer ERROR_1008 = 1008;////缺少有效星期
    public static final Integer ERROR_1009 = 1009;// //缺少起始日期
    public static final Integer ERROR_1010 = 1010;////缺少结束日期
    public static final Integer ERROR_1011 = 1011;// 起始日期格式错误
    public static final Integer ERROR_1012 = 1012;//结束日期格式错误
    public static final Integer ERROR_1013 = 1013;////开始日期不能大于结束日期
    public static final Integer ERROR_1014 = 1014;// //开始日期不能小于当前日期
    public static final Integer ERROR_1015 = 1015;////缺少渠道订单号
    public static final Integer ERROR_1016 = 1016;// //缺少操作人ID
    public static final Integer ERROR_1017 = 1017;////缺少操作人姓名
    public static final Integer ERROR_1018 = 1018;////缺少拒单原因类型
    public static final Integer ERROR_1019 = 1019;// //缺少直连用户名
    public static final Integer ERROR_1020 = 1020;////缺少直连密码
    public static final Integer ERROR_1021 = 1021;////缺少用户IP
    public static final Integer ERROR_1022 = 1022;////缺少接口版本号
    public static final Integer ERROR_1023 = 1023;//缺少订单价格
    public static final Integer ERROR_1024 = 1024;//缺少订单客人姓名
    public static final Integer ERROR_1025 = 1025;//缺少PMS物理//房型代码
    public static final Integer ERROR_1026 = 1026;//缺少联系人姓名
    public static final Integer ERROR_1027 = 1027; //缺少联系人联系方式
    public static final Integer ERROR_1028 = 1028; //缺少有效//房量
    public static final Integer ERROR_1029 = 1029; //房量格式错误
    public static final Integer ERROR_1030 = 1030; //缺少酒店名称
    public static final Integer ERROR_1031 = 1031;//缺少渠道酒店名称
    public static final Integer ERROR_1032 = 1032;//开始结束时间间隔大于90天
    public static final Integer ERROR_1033 = 1033;//缺少渠道物理//房型代码
    public static final Integer ERROR_1034 = 1034;//缺少渠道物理//房型名称
    public static final Integer ERROR_1035 = 1035; //缺少PMS物理//房型名称
    public static final Integer ERROR_1036 = 1036;//底价、卖价、佣金至少2个不为空
    public static final Integer ERROR_1037 = 1037;// 价格字段内容格式错误
    public static final Integer ERROR_1038 = 1038;//缺少手机验证码
    public static final Integer ERROR_1039 = 1039;//缺少手机号码
    public static final Integer ERROR_2000 = 2000;//没有进行酒店级渠道匹配
    public static final Integer ERROR_2001 = 2001;//订单状态已改变
    public static final Integer ERROR_2002 = 2002;// 渠道未给您的账号分配操作权限
    public static final Integer ERROR_2003 = 2003;// 您的账号暂时没有绑定的酒店信息
    public static final Integer ERROR_2004 = 2004;// 不满足订单自动审核条件
    public static final Integer ERROR_2005 = 2005;//渠道下没有账号开通
    public static final Integer ERROR_2006 = 2006;//没有进行价格计划匹配
    public static final Integer ERROR_2007 = 2007;//没有进行物理//房型匹配
    public static final Integer ERROR_2008 = 2008;//该物理//房型已经匹配
    public static final Integer ERROR_2009 = 2009;//不存在该物理//房型
    public static final Integer ERROR_2010 = 2010;//该渠道价格计划已经匹配
    public static final Integer ERROR_2011 = 2011;// 该渠道价格计划与当前渠道酒店不匹配
    public static final Integer ERROR_2012 = 2012;//PMS代码不合法
    public static final Integer ERROR_2013 = 2013;///当前PMS服务处于停用状态
    public static final Integer ERROR_2014 = 2014;// 已存在的酒店匹配
    public static final Integer ERROR_2015 = 2015;//渠道酒店不属于该账号
    public static final Integer ERROR_2016 = 2016;//渠道代码尚未开通服务
    public static final Integer ERROR_2017 = 2017;//渠道不存在该组合的产品
    public static final Integer ERROR_2018 = 2018;//没有该组合的产品级对接匹配
    public static final Integer ERROR_2019 = 2019;// 该渠道产品已经匹配
    public static final Integer ERROR_2020 = 2020;//订单不存在或不属于该酒店
    public static final Integer ERROR_3000 = 3000;//渠道确认订单操作失败
    public static final Integer ERROR_3001 = 3001;//渠道拒单操作失败
    public static final Integer ERROR_3002 = 3002;// 渠道订单入住审核操作失败
    public static final Integer ERROR_3003 = 3003;//渠道订单离店审核操作失败
    public static final Integer ERROR_3004 = 3004;// 渠道更改//房量操作全部失败
    public static final Integer ERROR_3005 = 3005;//渠道更改//房量操作部分失败
    public static final Integer ERROR_3006 = 3006;//开//房全部操作失败
    public static final Integer ERROR_3007 = 3007;//  开//房部分操作失败
    public static final Integer ERROR_3008 = 3008;// 关//房全部操作失败
    public static final Integer ERROR_3009 = 3009;//关//房部分操作失败
    public static final Integer ERROR_3010 = 3010;// 设置//房价操作失败
    public static final Integer ERROR_3011 = 3011;//  设置//房价部分操作失败,请刷新重试
    public static final Integer ERROR_3012 = 3012;// 用户名或密码错误
    public static final Integer ERROR_3013 = 3013;//该手机号未与去哪儿ebooking账号进行绑定
    public static final Integer ERROR_3014 = 3014;//动态码发送次数过多，请明日再试
    public static final Integer ERROR_3015 = 3015;//动态码获取频繁，请1小时后再试
    public static final Integer ERROR_3016 = 3016;// 动态码获取频繁，请15分钟后再试
    public static final Integer ERROR_3017 = 3017;// 请10分钟后重试
    public static final Integer ERROR_3018 = 3018;//动态码获取频繁，请1分钟后再试
    public static final Integer ERROR_3019 = 3019;//账号异常，请10分钟后再试
    public static final Integer ERROR_3020 = 3020;//账号异常，请24小时后再试
    public static final Integer ERROR_3021 = 3021;//动态码错误，请重新输入
    public static final Integer ERROR_3022 = 3022;//账号异常，请联系去哪儿网工作人员进行咨询，联系电话4006812010
    public static final Integer ERROR_3023 = 3023;// 动态码已失效，请重新发送动态码
    public static final Integer ERROR_3024 = 3024;// 请输入正确的手机号码
    public static final Integer ERROR_4000 = 4000;// 不存在的渠道Code
    public static final Integer ERROR_4001 = 4001;//该渠道暂未开放此功能
    public static final Integer ERROR_4002 = 4002;//系统错误，请重试
    public static final Integer ERROR_4003 = 4003;// 贵方渠道不支持此种对接模式
    public static final Integer ERROR_5000 = 5000;// HMAC校验失败
    public static final Integer ERROR_5001 = 5001;//酒店无此订单操作权限
    public static final Integer ERROR_5002 = 5002;//该版本API接口暂未开放

    public static final String SUCCESS_MSG = "处理成功"; //处理成功
}
