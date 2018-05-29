package com.yunforge.hbase.model;

public class DetailCellData {
	
	private String row;
	
	private String family;
	
	private String qualifier;
	
	private String value;

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DetailCellData [row=" + row + ", family=" + family + ", qualifier=" + qualifier + ", value=" + value
				+ "]";
	}
	
	
}
