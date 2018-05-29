package com.yunforge.hbase.model;

import java.math.BigDecimal;

public class Student {
	
	private String name;
	
	private String gender;
	
	private BigDecimal chinese;
	
	private BigDecimal math;
	
	private String 语文;
	
	private String 数学;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getChinese() {
		return chinese;
	}

	public void setChinese(BigDecimal chinese) {
		this.chinese = chinese;
	}

	public BigDecimal getMath() {
		return math;
	}

	public void setMath(BigDecimal math) {
		this.math = math;
	}

	public String get语文() {
		return 语文;
	}

	public void set语文(String 语文) {
		this.语文 = 语文;
	}

	public String get数学() {
		return 数学;
	}

	public void set数学(String 数学) {
		this.数学 = 数学;
	}
	
	

}
