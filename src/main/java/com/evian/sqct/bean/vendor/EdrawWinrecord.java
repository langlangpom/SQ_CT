package com.evian.sqct.bean.vendor;

/**
 * @date   2019年2月28日 下午2:26:05
 * @author XHX
 * @Description 售货机中奖人
 */
public class EdrawWinrecord {

	private String wxId;
	private String cellPhone;
	public String getWxId() {
		return wxId;
	}
	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	@Override
	public String toString() {
		return "EdrawWinrecord [wxId=" + wxId + ", cellPhone=" + cellPhone + "]";
	}
	
}
