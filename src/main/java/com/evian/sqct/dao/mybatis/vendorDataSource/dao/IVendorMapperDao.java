package com.evian.sqct.dao.mybatis.vendorDataSource.dao;

import com.evian.sqct.bean.vendor.EPaySubAccount;
import com.evian.sqct.bean.vendor.SingleProduct;
import com.evian.sqct.bean.vendor.VendorAppMerchantAccountProduct;
import com.evian.sqct.bean.vendor.VendorProduct;
import com.evian.sqct.bean.vendor.input.AddVendorProductReqDTO;
import com.evian.sqct.bean.vendor.input.EWechatServicepaySubaccountApplyInputDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyBankRepDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyBanknameReqDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyProvinceRepDTO;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyRepDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName:IVendorMapperDao
 * Package:com.evian.sqct.dao.mybatis.vendorDataSource.dao
 * Description:请为该功能做描述
 *
 * @Date:2020/10/9 11:03
 * @Author:XHX
 */
@Repository
public interface IVendorMapperDao {
    List<EPaySubAccount> selectEPaySubAccountByAccount(String account);

    int insertEPaySubAccount(EPaySubAccount ePaySubAccount);

    int insertEWechatServicepaySubaccountApply(EWechatServicepaySubaccountApplyInputDTO dto);

    List<EWechatServicepaySubaccountApplyRepDTO> selectEWechatServicepaySubaccountApplyByAccount(Integer accountId);

    int updateEWechatServicepaySubaccountApply(EWechatServicepaySubaccountApplyInputDTO dto);

    List<EWechatServicepaySubaccountApplyProvinceRepDTO> selectEWechatServicepaySubaccountApplyProvince();

    List<EWechatServicepaySubaccountApplyBankRepDTO> selectEWechatServicepaySubaccountApplyBank();

    List<EWechatServicepaySubaccountApplyBanknameReqDTO> selectEWechatServicepaySubaccountApplyBankname();

    int updatesingleOrderStatus(@Param("orderStatus") Integer orderStatus, @Param("orderId")Integer orderId);

    int singleProductInsert(SingleProduct product);

    int singleProductUpdate(SingleProduct product);

    List<SingleProduct> selectSingleProductsByEid(Integer eid);

    List<SingleProduct> selectSingleProductsByAccountId(Integer accountId);

    SingleProduct selectSingleProductsByPid(Integer productId);

    int updateSingleProductHitTheShelf(@Param("hitTheShelf") Boolean hitTheShelf,@Param("productId") Integer productId);

    int deleteSingleProduct(Integer productId);

    int insertVendorProduct(AddVendorProductReqDTO dto);

    int delVendorProduct(Integer id);

    int updateVendorProduct(VendorProduct vendorProduct);

    List<VendorProduct> selectVendorProductByAccountId(Integer accountId);

    VendorProduct selectVendorProductById(Integer id);

    int insertVendorAppMerchantAccountProduct(VendorAppMerchantAccountProduct product);

    int updateVendorAppMerchantAccountProduct(VendorAppMerchantAccountProduct product);

    int deleteVendorAppMerchantAccountProduct(VendorAppMerchantAccountProduct product);

    VendorAppMerchantAccountProduct selectVendorAppMerchantAccountProduct(VendorAppMerchantAccountProduct product);
}
