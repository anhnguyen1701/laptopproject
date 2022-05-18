package vn.ptit.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bill")
public class HoaDon extends BaseEntity {
	
	@Column(name = "ngayTao", nullable = false)
	private Date ngayTao;
	
	@Column(name = "tenKhachHang", length = 1000, nullable = false)
	private String tenKhachHang;
	
	@Column(name = "email", length = 1000, nullable = false)
	private String email;

	@Column(name = "soDienThoai", length = 1000, nullable = false)
	private String soDienThoai;
	
	@Column(name = "diaChi", length = 1000, nullable = false)
	private String diaChi;
	
	@Column(name = "ghiChu", length = 1000, nullable = true)
	private String ghiChu;
	
	@Column(name = "hinhThucThanhToan", length = 1000, nullable = false)
	private String hinhThucThanhToan;
	
	@Column(name = "tongTien", precision = 13, scale = 0, nullable = false)
	private BigDecimal tongtien;
	
	@Column(name = "status", nullable = true)
	private Boolean status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hoaDon", fetch = FetchType.LAZY)
	private List<HoaDonDanhSachHang> hoaDonDanhSachHangs = new ArrayList<HoaDonDanhSachHang>();

	public void addHoaDonDanhSachHang(HoaDonDanhSachHang hoaDonDanhSachHang) {
		hoaDonDanhSachHangs.add(hoaDonDanhSachHang);
		hoaDonDanhSachHang.setHoaDon(this);
	}

	public void removeHoaDonDanhSachHang(HoaDonDanhSachHang hoaDonDanhSachHang) {
		hoaDonDanhSachHangs.remove(hoaDonDanhSachHang);
		hoaDonDanhSachHang.setHoaDon(null);
	}

	public Date getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getHinhThucThanhToan() {
		return hinhThucThanhToan;
	}

	public void setHinhThucThanhToan(String hinhThucThanhToan) {
		this.hinhThucThanhToan = hinhThucThanhToan;
	}

	public BigDecimal getTongtien() {
		return tongtien;
	}

	public void setTongtien(BigDecimal tongtien) {
		this.tongtien = tongtien;
	}

	public List<HoaDonDanhSachHang> getHoaDonDanhSachHangs() {
		return hoaDonDanhSachHangs;
	}

	public void setHoaDonDanhSachHangs(List<HoaDonDanhSachHang> hoaDonDanhSachHangs) {
		this.hoaDonDanhSachHangs = hoaDonDanhSachHangs;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
