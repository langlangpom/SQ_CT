package com.evian.sqct.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @FileName: Constants.java
 * @Package com.evian.mobile.util
 * @Description: 前端常量
 * @author EVIAN(PA)
 * @date 2016年4月5日 下午6:21:43
 * @version V3.0
 */
public class Constants {
	public static final String vpass = "3499F7DB73B94ACBBBDC849A24F705208CB9692AF5AE7D55";
	public static final String errorMsg = "请求验证失败!";
	private static Map<Integer, String> map = new HashMap<Integer, String>();
	public static final int CODE_ERROR_SYSTEM = 0;// 服务系统异常
	public static final int CODE_SUC = 1; // 成功

	public static final int CODE_ERROR_PARAM = 101; // 请求参数错误
	public static final int CODE_ERROR_SIGN = 102; // 非合法用户请求
	public static final int CODE_ERROR_UPDATEPASS = 103; // 登录密码修改失败
	public static final int CODE_FAIL_LOGIN = 104; // 登录失败
	public static final int CODE_FAIL_MDLOCATION = 105; // 门店坐标上传失败
	public static final int CODE_ERROR_IMAGE = 106; // 上传图片格式错误
	public static final int CODE_FAIL_PHOTO = 107; // 上传图片失败
	public static final int CODE_STATE_UPDATE_FAIL = 108; // 单据状态更新失败
	public static final int CODE_ORDER_HUIDAN_FAIL = 109; // 单据回单失败
	public static final int CODE_ORDER_ZHUANDAN_FAIL = 110; // 单据转单失败
	public static final int CODE_ADD_TERMINA_FAIL = 111; // 添加终端失败
	public static final int CODE_SIGNIN_FAIL = 112; // 今天已签到,不能重复签到
	public static final int CODE_SIGNOUT_FAIL = 113; // 今天已签出,不能重复签退
	public static final int CODE_ORDRE_CHANGE_SYN = 114; // 业务系统单据状态变化,同步为
	public static final int CODE_SAVE_ACCOUNT_FAIL = 115; // 开户信息保存失败
	public static final int CODE_SAVE_STORE_FAIL = 116; // 库存盘点失败
	public static final int CODE_GET_STORE_NO_FAIL = 117; // 获取盘点编号失败
	public static final int CODE_SAVE_SETTLE_FAIL = 118; // 门店交账失败
	public static final int CODE_NEW_ORDER_FAIL = 119; // 保存开单失败
	public static final int CODE_STORE_BUHUO_FAIL = 120; // 门店补货失败
	public static final int CODE_SDDV3_NO_DATA = 121; // 未查询到相关数据
	public static final int CODE_BACKUP_DB_FAIL = 122; // 备份数据失败
	public static final int CODE_ORDER_SIGN_FAIL = 123; // 上传单据签名失败
	public static final int CODE_NOT_CANCEL_ORDER_FAIL = 124; // 特殊单据不能取消
	public static final int CODE_ORDER_CF_SIGN_FAIL = 125; // 单据不能重复签收
	public static final int CODE_AUDIT_REPLUS_CLINET_FAIL = 126; // 审核|拒绝客户失败
	public static final int CODE_QR_PAY_REPEAT = 127; // 单据已支付,不能重复支付
	public static final int CODE_QR_PAY_SERIAL_EMPTY = 128; // 未设置收款商户ID,无法发起收款
	public static final int CODE_SAVE_PURCHARE_FAIL = 129; // 采购进货单据保存失败
	public static final int CODE_PLUPDATE_ORDERSTATE_FAIL = 130; // 批量更新单据状态失败
	public static final int CODE_EARNING_SAVE_FAIL = 131; // 提现账号保存失败
	public static final int CODE_TIXIIAN_SAVE_FAIL = 132; // 水叮咚提现失败
	public static final int CODE_GATHER_PLATFOME_NONE = 133; // 未成功设置收款平台
	public static final int CODE_GATHER_ORDER_NONE = 134; // 单据异常不能发起收款
	public static final int CODE_NO_QUERY_TONG = 135; // 未查询相关的桶信息
	public static final int CODE_NO_DENTIFYING_CODE = 136; // 验证码发送失败
	public static final int CODE_NO_ADD_OR_DEL = 137; // 添加或删除商品失败
	public static final int CODE_ERROR_CLIENT_ENABLE = 138; // 用户账号被系统禁用
	public static final int CODE_ERROR_NO_REGISTER = 139; // 用户账号未注册
	public static final int CODE_ERROR_LOGIN_PARAM = 140; // 用户名或密码输入错误
	
	
	static {
		map.put(CODE_SUC, "成功");
		map.put(CODE_ERROR_PARAM, "请求参数错误");
		map.put(CODE_ERROR_SYSTEM, "服务系统异常");
		map.put(CODE_ERROR_SIGN, "无效的客户端请求");
		map.put(CODE_ERROR_UPDATEPASS, "登录密码修改失败");
		map.put(CODE_FAIL_LOGIN, "账号或密码错误");
		map.put(CODE_FAIL_MDLOCATION, "门店坐标上传失败");
		map.put(CODE_ERROR_IMAGE, "上传图片格式错误");
		map.put(CODE_FAIL_PHOTO, "上传图片失败");
		map.put(CODE_STATE_UPDATE_FAIL, "%s单据失败");
		map.put(CODE_ORDER_HUIDAN_FAIL, "单据回单失败");
		map.put(CODE_ORDER_ZHUANDAN_FAIL, "单据转单失败");
		map.put(CODE_SIGNIN_FAIL, "今天已签到,不能重复签到");
		map.put(CODE_SIGNOUT_FAIL, "今天已签出,不能重复签退");
		map.put(CODE_ORDRE_CHANGE_SYN, "业务系统单据状态变化\n同步为");
		map.put(CODE_SAVE_ACCOUNT_FAIL, "开户信息保存失败");
		map.put(CODE_SAVE_STORE_FAIL, "库存盘点失败");
		map.put(CODE_GET_STORE_NO_FAIL, "获取盘点编号失败");
		map.put(CODE_SAVE_SETTLE_FAIL, "门店交账失败");
		map.put(CODE_NEW_ORDER_FAIL, "保存开单失败");
		map.put(CODE_STORE_BUHUO_FAIL, "门店补货失败");
		map.put(CODE_SDDV3_NO_DATA, "未查询到相关数据");
		map.put(CODE_BACKUP_DB_FAIL, "备份数据失败");
		map.put(CODE_ORDER_SIGN_FAIL, "上传单据签名失败");
		map.put(CODE_NOT_CANCEL_ORDER_FAIL, "特殊单据不能取消");
		map.put(CODE_ORDER_CF_SIGN_FAIL, "单据不能重复签收");
		map.put(CODE_AUDIT_REPLUS_CLINET_FAIL, "审核|拒绝客户失败");
		map.put(CODE_QR_PAY_REPEAT, "单据不能重复支付,单据完成");
		map.put(CODE_QR_PAY_SERIAL_EMPTY, "商户信息不完整,无法收款");
		map.put(CODE_SAVE_PURCHARE_FAIL, "采购进货单据保存失败");
		map.put(CODE_PLUPDATE_ORDERSTATE_FAIL, "批量更新单据状态失败");
		map.put(CODE_EARNING_SAVE_FAIL, "提现账号保存失败");
		map.put(CODE_TIXIIAN_SAVE_FAIL, "水叮咚提现失败");
		map.put(CODE_GATHER_PLATFOME_NONE, "未成功设置收款平台");
		map.put(CODE_GATHER_ORDER_NONE, "单据异常不能发起收款");
		map.put(CODE_NO_QUERY_TONG, "未查询相关的桶信息");
		map.put(CODE_NO_DENTIFYING_CODE, "该手机号不存在");
		map.put(CODE_NO_ADD_OR_DEL, "添加或删除商品失败");
		map.put(CODE_ERROR_CLIENT_ENABLE, "用户账号被系统禁用");
		map.put(CODE_ERROR_NO_REGISTER, "用户账号未注册");
		map.put(CODE_ERROR_LOGIN_PARAM, "用户名或密码输入错误");
	}

	/**
	 * 获取错误消息集合
	 * 
	 * @return
	 */
	public static String getCodeValue(int code) {
		if (map.containsKey(code))
			return map.get(code);
		return map.get(CODE_ERROR_SYSTEM);
	}
}
