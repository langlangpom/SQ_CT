package com.evian.sqct;

import com.evian.sqct.bean.baseBean.PagingPojo;
import com.evian.sqct.bean.thirdParty.RecruitOrderPojoBean;
import com.evian.sqct.bean.vendor.SkipShuiqooProduct;
import com.evian.sqct.bean.vendor.write.EWechatServicepaySubaccountApplyProvinceRepDTO;
import com.evian.sqct.bean.vendor.write.SkipShuiqooProductRepDTO;
import com.evian.sqct.dao.mybatis.primaryDataSource.dao.IThirdPartyMapperDao;
import com.evian.sqct.dao.mybatis.primaryDataSource.dao.IUserMapperDao;
import com.evian.sqct.dao.mybatis.vendorDataSource.dao.ISkipShuiqooProductDao;
import com.evian.sqct.dao.mybatis.vendorDataSource.dao.IVendorMapperDao;
import com.evian.sqct.util.DaoUtil;
import com.evian.sqct.util.JacksonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * ClassName:MybatisTests
 * Package:com.evian.sqct
 * Description:请为该功能做描述
 *
 * @Date:2020/9/30 10:40
 * @Author:XHX
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ISkipShuiqooProductDao skipShuiqooProductDao;

    @Autowired
    private IVendorMapperDao vendorMapperDao;

    @Autowired
    private IUserMapperDao userMapperDao;

    @Autowired
    private IThirdPartyMapperDao thirdPartyMapperDao;

    @Test
    public void test001() {
        SkipShuiqooProduct skipShuiqooProduct = new SkipShuiqooProduct();
        skipShuiqooProduct.setPid(444);
        skipShuiqooProduct.setSort(1);
        skipShuiqooProduct.setCreater("xhx");
        skipShuiqooProduct.setEid(1);
        int i = skipShuiqooProductDao.insertSkipShuiqooProduct(skipShuiqooProduct);
        System.out.println(i);
    }
    @Test
    public void test002() {

        SkipShuiqooProduct select = skipShuiqooProductDao.select(1);
        System.out.println(select);
    }
    @Test
    public void test003() {

        List<SkipShuiqooProduct> skipShuiqooProducts = skipShuiqooProductDao.selectSkipShuiqooProductByEid(1);
        System.out.println(skipShuiqooProducts);
    }
    @Test
    public void test004() {
        int i = skipShuiqooProductDao.deleteSkipShuiqooProduct(1);
        System.out.println(i);
    }
    @Test
    public void test005() {
        List<SkipShuiqooProductRepDTO> skipShuiqooProductRepDTOS = skipShuiqooProductDao.selectSkipShuiqooProductRepByEid(1);
        System.out.println(skipShuiqooProductRepDTOS);
    }
    @Test
    public void test006() {
        PageHelper.startPage(1, 10);
        List<SkipShuiqooProductRepDTO> skipShuiqooProductRepDTOS = skipShuiqooProductDao.selectSkipShuiqooProductRepByEid(1);
        PageInfo<SkipShuiqooProductRepDTO> skipShuiqooProductRepDTOPageInfo = new PageInfo<>(skipShuiqooProductRepDTOS);

        System.out.println(skipShuiqooProductRepDTOS);
        System.out.println(skipShuiqooProductRepDTOPageInfo);

        String s = JacksonUtils.obj2json(skipShuiqooProductRepDTOPageInfo);
        System.out.println(s);
        PagingPojo d = new PagingPojo();
        d.setPageIndex(1);
        d.setPageSize(10);
        Map<String, Object> map = DaoUtil.resultPageData(d, "product", () -> skipShuiqooProductDao.selectSkipShuiqooProductRepByEid(1));
        System.out.println(JacksonUtils.obj2json(map));
    }

    @Test
    public void test007() {
        int i = vendorMapperDao.updatesingleOrderStatus(0, 1);
        System.out.println(i);
    }

    @Test
    public void test008() {
        List<EWechatServicepaySubaccountApplyProvinceRepDTO> eWechatServicepaySubaccountApplyProvinceRepDTOS = vendorMapperDao.selectEWechatServicepaySubaccountApplyProvince();
        System.out.println(eWechatServicepaySubaccountApplyProvinceRepDTOS);
    }

    @Test
    public void test009() {
        Map<String, Object> map = userMapperDao.selectNicknameByAccount("15920034610");
        System.out.println(map);
    }

    @Test
    public void test010() {
        List<RecruitOrderPojoBean> recruitOrderPojoBeans = thirdPartyMapperDao.selectRecruitOrderByOrderGuid("600B380D-B535-47F5-A3C0-0D6B5E0AD758");
        System.out.println(recruitOrderPojoBeans);
    }


}
