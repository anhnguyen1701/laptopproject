package vn.ptit.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill_product")
public class HoaDonDanhSachHang extends BaseEntity {
	
	@Column(name = "tenLaptop", length = 1000, nullable = false)
	private String tenLaptop;
	
	@Column(name = "tenHangLaptop", length = 1000, nullable = false)
	private String tenHangLaptop;
	
	@Column(name = "soLuong", nullable = false)
	private Integer soLuong;
	
	@Column(name = "giaTien", precision = 13, scale = 0, nullable = false)
	private BigDecimal giaTien;
	
	@Column(name = "tongTienTungLoai", precision = 13, scale = 0, nullable = false)
	private BigDecimal tongTienTungLoai;
	
	@Column(name = "tenAnh", length = 2000, nullable = false)
	private String tenAnh;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hoadon_id")
	private HoaDon hoaDon;

	public String getTenLaptop() {
		return tenLaptop;
	}

	public void setTenLaptop(String tenLaptop) {
		this.tenLaptop = tenLaptop;
	}

	public String getTenHangLaptop() {
		return tenHangLaptop;
	}

	public void setTenHangLaptop(String tenHangLaptop) {
		this.tenHangLaptop = tenHangLaptop;
	}

	public Integer getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Integer soLuong) {
		this.soLuong = soLuong;
	}

	public BigDecimal getGiaTien() {
		return giaTien;
	}

	public void setGiaTien(BigDecimal giaTien) {
		this.giaTien = giaTien;
	}

	public BigDecimal getTongTienTungLoai() {
		return tongTienTungLoai;
	}

	public void setTongTienTungLoai(BigDecimal tongTienTungLoai) {
		this.tongTienTungLoai = tongTienTungLoai;
	}

	public String getTenAnh() {
		return tenAnh;
	}

	public void setTenAnh(String tenAnh) {
		this.tenAnh = tenAnh;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

}
