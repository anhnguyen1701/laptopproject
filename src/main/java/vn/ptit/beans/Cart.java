package vn.ptit.beans;

import java.math.BigDecimal;

public class Cart {
	private String seo;
	private String laptopNameImg;
	private String laptopName;
	private String laptopManufacturerName;
	private BigDecimal price;
	private Integer amount;
	private BigDecimal allPrice;
	
	public String getLaptopNameImg() {
		return laptopNameImg;
	}
	public void setLaptopNameImg(String laptopNameImg) {
		this.laptopNameImg = laptopNameImg;
	}
	public String getLaptopName() {
		return laptopName;
	}
	public void setLaptopName(String laptopName) {
		this.laptopName = laptopName;
	}
	public String getLaptopManufacturerName() {
		return laptopManufacturerName;
	}
	public void setLaptopManufacturerName(String laptopManufacturerName) {
		this.laptopManufacturerName = laptopManufacturerName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public BigDecimal getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(BigDecimal allPrice) {
		this.allPrice = allPrice;
	}
	public String getSeo() {
		return seo;
	}
	public void setSeo(String seo) {
		this.seo = seo;
	}
	
		
}
