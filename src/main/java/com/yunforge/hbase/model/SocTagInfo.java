package com.yunforge.hbase.model;

import java.util.List;

public class SocTagInfo {
	
	private String row;
	
	private List<DetailCellData> detail;
	
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public List<DetailCellData> getDetail() {
		return detail;
	}
	public void setDetail(List<DetailCellData> detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "SocTagInfo [row=" + row + ", detail=" + detail + "]";
	}
	
	
}
