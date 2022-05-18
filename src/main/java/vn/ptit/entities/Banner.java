package vn.ptit.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "banner_attachment")
public class Banner extends BaseEntity {
	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "path", length = 100, nullable = false)
	private String path;

	@Column(name = "mime", length = 100, nullable = false)
	private String mime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "banner_id")
	private IdBanner idBanner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public IdBanner getIdBanner() {
		return idBanner;
	}

	public void setIdBanner(IdBanner idBanner) {
		this.idBanner = idBanner;
	}
	
}
