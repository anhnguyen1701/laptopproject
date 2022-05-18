package vn.ptit.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "banner")
public class IdBanner extends BaseEntity {
	
	@Column(name = "ngayTao", length = 1000, nullable = false)
	private String ngayTao;
	
	@Column(name = "status", nullable = true)
	private Boolean status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idBanner", fetch = FetchType.LAZY)
	private List<Banner> banners = new ArrayList<Banner>();

	public void addBanner(Banner banner) {
		banners.add(banner);
		banner.setIdBanner(this);
	}

	public void removeBanner(Banner banner) {
		banners.remove(banner);
		banner.setIdBanner(null);
	}

	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}

	public String getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
