package com.evian.sqct.bean.user;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ClassName:EAppMerchantAccountEnterpriseRole
 * Package:com.evian.sqct.bean.user
 * Description:水趣商户职员权限表
 *
 * @Date:2020/5/9 16:17
 * @Author:XHX
 */
public class EAppMerchantAccountEnterpriseRole implements Serializable {

    private static final long serialVersionUID = 2572545768359722263L;

    private Integer roleId;				// 权限id
    private Integer eid;				// 企业id
    private String roleName;			// 权限名称
    private String sign;                // 角色标识，用于某个企业初始化或者恢复默认值等操作
    private Boolean isCanEditMenuItem;  // 是否可以编辑对应的菜单，比如超级管理员无需编辑
    private Boolean isEnable;           // 是否可以编辑对应的菜单，比如超级管理员无需编辑
    private Timestamp createTime;       // 录入时间
    private String creator;             // 录入人

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Boolean getCanEditMenuItem() {
        return isCanEditMenuItem;
    }

    public void setCanEditMenuItem(Boolean canEditMenuItem) {
        isCanEditMenuItem = canEditMenuItem;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "EAppMerchantAccountEnterpriseRole [" +
                "roleId=" + roleId +
                ", eid=" + eid +
                ", roleName=" + roleName +
                ", sign=" + sign +
                ", isCanEditMenuItem=" + isCanEditMenuItem +
                ", isEnable=" + isEnable +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ']';
    }
}
