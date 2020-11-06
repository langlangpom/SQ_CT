package com.evian.sqct.response;

/**
 * ClassName:ResultCode
 * Package:com.evian.sqct.response
 * Description:响应码枚举
 *
 * @Date:2020/6/11 10:02
 * @Author:XHX
 */
public enum ResultCode {

    /** 服务系统异常 */
    CODE_ERROR_SYSTEM(0,"服务系统异常"),
    /** 成功 */
    CODE_SUC(1,"成功"),
    /** 请求参数错误 */
    CODE_ERROR_PARAM(101,"请求参数错误"),
    /** 无效的客户端请求 */
    CODE_ERROR_SIGN(102,"无效的客户端请求"),
    /** 登录密码修改失败 */
    CODE_ERROR_UPDATEPASS(103,"登录密码修改失败"),
    /** 账号或密码错误 */
    CODE_FAIL_LOGIN(104,"账号或密码错误"),
    /** 门店坐标上传失败 */
    CODE_FAIL_MDLOCATION(105,"门店坐标上传失败"),
    /** 上传图片格式错误 */
    CODE_ERROR_IMAGE(106,"上传图片格式错误"),
    /** 上传图片失败 */
    CODE_FAIL_PHOTO(107,"上传图片失败"),
    /** %s单据失败 */
    CODE_STATE_UPDATE_FAIL(108,"%s单据失败"),
    /** 单据回单失败 */
    CODE_ORDER_HUIDAN_FAIL(109,"单据回单失败"),
    /** 单据转单失败 */
    CODE_ORDER_ZHUANDAN_FAIL(110,"单据转单失败"),
    /** 服务系统异常 */
    CODE_ADD_TERMINA_FAIL(111,"服务系统异常"),
    /** 今天已签到,不能重复签到 */
    CODE_SIGNIN_FAIL(112,"今天已签到,不能重复签到"),
    /** 今天已签出,不能重复签退 */
    CODE_SIGNOUT_FAIL(113,"今天已签出,不能重复签退"),
    /** 业务系统单据状态变化同步为 */
    CODE_ORDRE_CHANGE_SYN(114,"业务系统单据状态变化同步为"),
    /** 开户信息保存失败 */
    CODE_SAVE_ACCOUNT_FAIL(115,"开户信息保存失败"),
    /** 库存盘点失败 */
    CODE_SAVE_STORE_FAIL(116,"库存盘点失败"),
    /** 获取盘点编号失败 */
    CODE_GET_STORE_NO_FAIL(117,"获取盘点编号失败"),
    /** 门店交账失败 */
    CODE_SAVE_SETTLE_FAIL(118,"门店交账失败"),
    /** 保存开单失败 */
    CODE_NEW_ORDER_FAIL(119,"保存开单失败"),
    /** 门店补货失败 */
    CODE_STORE_BUHUO_FAIL(120,"门店补货失败"),
    /** 未查询到相关数据 */
    CODE_SDDV3_NO_DATA(121,"未查询到相关数据"),
    /** 备份数据失败 */
    CODE_BACKUP_DB_FAIL(122,"备份数据失败"),
    /** 上传单据签名失败 */
    CODE_ORDER_SIGN_FAIL(123,"上传单据签名失败"),
    /** 特殊单据不能取消 */
    CODE_NOT_CANCEL_ORDER_FAIL(124,"特殊单据不能取消"),
    /** 单据不能重复签收 */
    CODE_ORDER_CF_SIGN_FAIL(125,"单据不能重复签收"),
    /** 审核|拒绝客户失败 */
    CODE_AUDIT_REPLUS_CLINET_FAIL(126,"审核|拒绝客户失败"),
    /** 单据不能重复支付,单据完成 */
    CODE_QR_PAY_REPEAT(127,"单据不能重复支付,单据完成"),
    /** 商户信息不完整,无法收款 */
    CODE_QR_PAY_SERIAL_EMPTY(128,"商户信息不完整,无法收款"),
    /** 采购进货单据保存失败 */
    CODE_SAVE_PURCHARE_FAIL(129,"采购进货单据保存失败"),
    /** 批量更新单据状态失败 */
    CODE_PLUPDATE_ORDERSTATE_FAIL(130,"批量更新单据状态失败"),
    /** 提现账号保存失败 */
    CODE_EARNING_SAVE_FAIL(131,"提现账号保存失败"),
    /** 水叮咚提现失败 */
    CODE_TIXIIAN_SAVE_FAIL(132,"水叮咚提现失败"),
    /** 未成功设置收款平台 */
    CODE_GATHER_PLATFOME_NONE(133,"未成功设置收款平台"),
    /** 单据异常不能发起收款 */
    CODE_GATHER_ORDER_NONE(134,"单据异常不能发起收款"),
    /** 未查询相关的桶信息 */
    CODE_NO_QUERY_TONG(135,"未查询相关的桶信息"),
    /** 该手机号不存在 */
    CODE_NO_DENTIFYING_CODE(136,"该手机号不存在"),
    /** 添加或删除商品失败 */
    CODE_NO_ADD_OR_DEL(137,"添加或删除商品失败"),
    /** 用户账号被系统禁用 */
    CODE_ERROR_CLIENT_ENABLE(138,"用户账号被系统禁用"),
    /** 用户账号未注册 */
    CODE_ERROR_NO_REGISTER(139,"用户账号未注册"),
    /** 用户名或密码输入错误 */
    CODE_ERROR_LOGIN_PARAM(140,"用户名或密码输入错误"),
    /** 无权修改该信息 */
    CODE_LIMITED_AUTHORITY_MESSAGE(141,"无权修改该信息"),
    /** 审核类型错误 */
    CODE_ERROE_AUDIT_TYPE(142,"审核类型错误"),
    /** 订单是组合支付，必须全额退款 */
    CODE_NO_FULL_REFUND(143,"订单是组合支付，必须全额退款"),
    /** 此订单为APP支付,不能进行退款 */
    CODE_ERREO_APP_REFUND_TYPE(144,"此订单为APP支付,不能进行退款"),
    /** 没有支付信息 */
    CODE_NO_PAY_MESSAGE(145,"没有支付信息"),
    /** 手机号码已被注册 */
    CODE_ERROR_CELL_PHONE_NUMBER_ALREADY_IN_USE(146,"手机号码已被注册"),
    /** 操作频繁,稍后再试 */
    CODE_OPERATION_OFTEN(147,"操作频繁,稍后再试"),
    /** 微信ID已被绑定 */
    CODE_WECHAT_ID_ALREADY_BINDING(148,"微信ID已被绑定"),
    /** 修改失败 */
    CODE_ERROR_UPDATE(149,"修改失败"),
    /** 服务系统异常 */
    CUSTOM_ERROR(150,"服务系统异常"),
    /** 货柜店铺或类型不相同 */
    CODE_VENDOE_SHOP_OR_TYPE_DIFFERENT(151,"货柜店铺或类型不相同"),
    /** 用户不存在 */
    CODE_ERROR_NOUSER(152,"用户不存在"),
    /** 提现类型错误 */
    CODE_ERROR_WITHDRAW_DEOISUT(153,"提现类型错误"),
    /** 企业支付信息错误 */
    CODE_ERROR_ENTERPRISE_PAY(154,"企业支付信息错误"),
    /** 操作失败 */
    CODE_ERROR_OPERATION(155,"操作失败"),
    /** 订单状态已变更 */
    CODE_ERROR_ORDER_ALTERATION(156,"订单状态已变更"),
    /** 账号生成失败 */
    CODE_ERROR_ACCOUNT_SAVE(157,"账号生成失败"),
    /** access_token验证错误 */
    CODE_ERROR_ACCESS_TOKEN(158,"access_token验证错误"),
    /** refresh_token验证错误 */
    CODE_ERROR_REFRESH_TOKEN(159,"refresh_token验证错误"),
    /** 未查询到相关单据信息 */
    CODE_ERROR_NOT_ORDER(160,"未查询到相关单据信息"),
    /** 存储过程数据不正确 */
    CODE_ERROR_STORAGE_DATA_INCORRECT(161,"存储过程数据不正确"),
    /** 没有该存储过程 */
    CODE_ERROR_NOT_STORAGE_DATA(162,"没有该存储过程"),
    /** 没有订单状态 */
    CODE_ERROR_ORDER_STATUS_NOT(163,"没有订单状态"),
    /** 金额为0，不能发起支付 */
    CODE_ERROR_PAY_MONEY_ZERO(164,"金额为0，不能发起支付"),
    /** JSON解析出错 */
    CODE_ERROR_JSON_ANALYSIS(165,"JSON解析出错"),
    /** 没有按规定上传图片大小 */
    CODE_ERROR_IMAGE_SIZE(166,"没有按规定上传图片大小"),
    /** 职员信息不存在 */
    CODE_ERROR_OFFICE_NOT(167,"职员信息不存在"),
    /** 商户信息不足 */
    CODE_ERROR_MCCHANT_MESSAGE_NOT(168,"商户信息不足"),
    /** 文件没有后缀 */
    CODE_ERROR_FILE_NOT_SUFFIX(169,"文件没有后缀");

    /** 自定义code */
    public final static int CUSTOM_CODE = 150;

    private int code;
    private  String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
