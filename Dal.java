package edu.aydin.sda.frontent;

import java.util.HashMap;

public class Dal {

	private String value;
	private Integer sol;
	private Integer sag;
	private Integer ust;
	private String returnValue;

	public Dal(String value) {
		// TODO Auto-generated constructor stub
		this.setValue(value);
	}

	public Dal() {
		// TODO Auto-generated constructor stub
	}

	public Integer getSol() {
		return sol;
	}

	public void setSol(Integer sol) {
		this.sol = sol;
	}

	public Integer getSag() {
		return sag;
	}

	public void setSag(Integer sag) {
		this.sag = sag;
	}

	public Integer getUst() {
		return ust;
	}

	public void setUst(Integer ust) {
		this.ust = ust;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	// SOL - ORTA - SAÐ
	static void printTree(Integer index, HashMap<Integer, Dal> map) {

		if (map.get(index).getSol() == null) {
			System.out.println(map.get(index).value);
		} else {
			printTree(map.get(index).getSol(), map);
			System.out.println(map.get(index).value);
		}
		if (map.get(index).getSag() != null) {
			printTree(map.get(index).getSag(), map);
		}

	}

}
