package com.evian.sqct.dao;

import com.evian.sqct.bean.user.EclientDTO;

import java.util.List;
import java.util.Map;

/**
 * ClassName:IEntityCardDao
 * Package:com.evian.sqct.dao
 * Description:实体卡dao
 *
 * @Date:2019/11/14 16:19
 * @Author:XHX
 */
public interface IEntityCardDao {

    /**
     * 实体卡激活 （app商户端调用）
     * @param eid
     * @param accountId app商户端账号Id
     * @param barCode 条码
     * @return
     */
    String Proc_Backstage_entity_card_storeIn_code_activate(Integer eid,Integer accountId,String barCode);

    /**
     * 实体卡码查询
     * @param beginTime
     * @param endTime
     * @param eid
     * @param codeStatus
     * @param cardCode
     * @param isActivate
     * @param accountId
     * @param pcode
     * @param account
     * @param PageIndex
     * @param PageSize
     * @param IsSelectAll
     * @return
     */
    Map<String,Object> Proc_Backstage_entity_card_storeIn_code_select(String beginTime,String endTime,Integer eid,Integer codeStatus,String cardCode,String barCode,String qrcode,Boolean isActivate,Integer accountId,String pcode,String account,String buyAccount,String useAccount,String sellBeginTime, String sellEndTime,String batchName,String storeType,String storeName,Boolean isExchange,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);

    /**
     *app商户实体卡片数量统计
     * @param eid
     * @param accountId
     * @return
     */
    Map<String,Object> Proc_Backstage_entity_card_storeIn_code_AccountStatistics(Integer eid,Integer accountId);

    /**
     * 查询票套餐有效期
     * @param ticketId
     * @return
     */
    Map<String,Object> selectTicketValidTime(Integer ticketId);

    /**
     * 生成单号
     * @param DanJuLeiXingID 填8
     * @param City_ID 填1
     * @return
     */
    Map<String,Object> Proc_DisPark_Get_OrderNo(Integer DanJuLeiXingID,Integer City_ID);

    /**
     *
     * @param eid
     * @param orderNo
     * @param account
     * @param userName
     * @param address
     * @param cardType
     * @param totalMoney
     * @param realTotalMoney
     * @param creator
     * @return
     */
    Map<String,Object> Proc_Backstage_entity_card_order_add(Integer eid,String orderNo,String buyAccount,String userName,String address,String vipName,Double totalMoney,Double realTotalMoney,String creator);

    Map<String,Object> Proc_Backstage_integral_vip_select(Integer vipId,Integer eid,String eName,Integer PageIndex,Integer PageSize,Boolean IsSelectAll);

    int add_e_entity_card_order_detail(Integer orderId,Integer codeId,Integer ticketId,Double price,String beginDate,String endDate);

    int updateE_entity_card_storeIn_codeCodeStatus(Integer codeId,Integer codeStatus);

    String Proc_Backstage_entity_card_order_detail_add(Integer orderId,Integer codeId,Integer ticketId,Double price,Integer accountId,Integer eid,String buyAccount,String userName,String creator);

    /**
     * 根据卡号查询购买用户信息
     * @param cardCode 卡号
     * @return
     */
    List<EclientDTO> selectEntityCardOrderBuyAccountByCardCode(String cardCode);
}
