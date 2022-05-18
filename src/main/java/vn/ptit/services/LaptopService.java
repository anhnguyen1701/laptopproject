package vn.ptit.services;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.ptit.entities.Banner;
import vn.ptit.entities.IdBanner;
import vn.ptit.entities.Laptop;
import vn.ptit.entities.LaptopManufacturer;
import vn.ptit.repositories.LaptopRepository;

@Service
public class LaptopService {
	@Autowired
	public LaptopRepository laptopRepository;
	@PersistenceContext
	EntityManager entityManager;

	private static int LIMIT = 36;

	@SuppressWarnings("unchecked")
	public List<Laptop> searchManufacturerItem(String laptopManufacturerSeo, Integer pageNumber) {

		String jpql = "select p from Laptop p where status=1"; // JPQL: lấy tất cả Post trong db.
		if (laptopManufacturerSeo != null) {
			jpql += " and p.laptopManufacturer.seo='" + laptopManufacturerSeo+"'"; // select p from Post p where 1=1 and
																			// p.category.id=1
		}
		Query query = entityManager.createQuery(jpql, Laptop.class);

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Laptop> searchAmount(String laptopSeo) {

		String jpql = "select p from Laptop p where status=1"; // JPQL: lấy tất cả Post trong db.
		if (laptopSeo != null) {
			jpql += " and p.seo='" + laptopSeo +"'";
		}
		Query query = entityManager.createQuery(jpql, Laptop.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Laptop> getLaptopBySeo(String seo) {

		String jpql = "select p from Laptop p where status=1"; // JPQL: lấy tất cả Post trong db.
		if (seo != null) {
			jpql += " and p.seo='" + seo+"'";
		}
		Query query = entityManager.createQuery(jpql, Laptop.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Laptop> getLaptopManufacturerBySeo(String seo) {

		String jpql = "select p from Laptop p where status=1"; // JPQL: lấy tất cả Post trong db.
		if (seo != null) {
			jpql += " and p.laptopManufacturer.seo='" + seo+"'";
		}
		Query query = entityManager.createQuery(jpql, Laptop.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Laptop> searchAllItem(Integer pageNumber) {

		String jpql = "select p from Laptop p where status=1"; // JPQL: lấy tất cả Post trong db.
		Query query = entityManager.createQuery(jpql, Laptop.class);

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<LaptopManufacturer> getAllLaptopManufacturer() {

		String jpql = "select p from LaptopManufacturer p where status=1"; // JPQL: lấy tất cả Post trong db.
		Query query = entityManager.createQuery(jpql, LaptopManufacturer.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<IdBanner> getBanner() {

		String jpql = "select p from IdBanner p where status=1"; // JPQL: lấy tất cả Post trong db.
		Query query = entityManager.createQuery(jpql, IdBanner.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Laptop> searchName(String tenLaptop) {

		if (!tenLaptop.isEmpty() && !tenLaptop.equalsIgnoreCase("'")) {
			String jpql = "select p from Laptop p where status=1 and p.name like" + "'%" + tenLaptop + "%'";
			Query query = entityManager.createQuery(jpql, Laptop.class);
			return query.getResultList();
		}
		return null;	
	}

	@SuppressWarnings("unchecked")
	public List<Laptop> filterByStatus(Integer pageNumber) {

		String jpql = "select p from Laptop p where status=1 and p.soLuongNhap>0"; // JPQL: lấy tất cả Post trong db.
		Query query = entityManager.createQuery(jpql, Laptop.class);

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> sortUpAscending(Integer pageNumber) {

		String jpql = "select p from Laptop p where status=1 order by p.price"; // JPQL: lấy tất cả Post trong db.
		Query query = entityManager.createQuery(jpql, Laptop.class);

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> descendingArrangement(Integer pageNumber) {

		String jpql = "select p from Laptop p where status=1 order by p.price desc"; // JPQL: lấy tất cả Post trong
																						// db.
		Query query = entityManager.createQuery(jpql, Laptop.class);

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> filterByPrice(String price, Integer pageNumber) {
		Query query = null;
		if (price.compareToIgnoreCase("duoi10trieu") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.price<10000000";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (price.compareToIgnoreCase("10den20trieu") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.price>=10000000 and p.price<20000000";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (price.compareToIgnoreCase("20den30trieu") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.price>=20000000 and p.price<30000000";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (price.compareToIgnoreCase("30den40trieu") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.price>=30000000 and p.price<40000000";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (price.compareToIgnoreCase("40den50trieu") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.price>=40000000 and p.price<50000000";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (price.compareToIgnoreCase("tren50trieu") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.price>=50000000";
			query = entityManager.createQuery(jpql, Laptop.class);
		}

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> filterByCpu(String cpu, Integer pageNumber) {
		Query query = null;
		if (cpu.compareToIgnoreCase("intel-core-i3") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%i3%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (cpu.compareToIgnoreCase("intel-core-i5") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%i5%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (cpu.compareToIgnoreCase("intel-core-i7") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%i7%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (cpu.compareToIgnoreCase("intel-core-i9") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%i9%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (cpu.compareToIgnoreCase("AMD3") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%AMD ryzen 3%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (cpu.compareToIgnoreCase("AMD5") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%AMD ryzen 5%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (cpu.compareToIgnoreCase("AMD7") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%AMD Ryzen 7%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (cpu.compareToIgnoreCase("AMD9") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.cpu like '%AMD Ryzen 9%'";
			query = entityManager.createQuery(jpql, Laptop.class);
		}

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> filterByRam(String ram, Integer pageNumber) {
		Query query = null;
		if (ram.compareToIgnoreCase("4gb") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.ram like '%4gb%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (ram.compareToIgnoreCase("8gb") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.ram like '%8gb%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (ram.compareToIgnoreCase("16gb") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.ram like '%16gb%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (ram.compareToIgnoreCase("32gb") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.ram like '%32gb%'";
			query = entityManager.createQuery(jpql, Laptop.class);
		}

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> filterByOCung(String oCung, Integer pageNumber) {
		Query query = null;
		if (oCung.compareToIgnoreCase("SSD") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.oCung like '%SSD%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (oCung.compareToIgnoreCase("HHD") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.oCung like '%HHD%'";
			query = entityManager.createQuery(jpql, Laptop.class);
		}

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> filterByVga(String vga, Integer pageNumber) {
		Query query = null;
		if (vga.compareToIgnoreCase("NVIDIA") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.vga like '%NVIDIA%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (vga.compareToIgnoreCase("AMD") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.vga like '%AMD%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (vga.compareToIgnoreCase("onboard") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.vga like '%Onboard%'";
			query = entityManager.createQuery(jpql, Laptop.class);
		}

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	public List<Laptop> filterByManHinh(String manHinh, Integer pageNumber) {
		Query query = null;
		if (manHinh.compareToIgnoreCase("123inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%12.3 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (manHinh.compareToIgnoreCase("125inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%12.5 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (manHinh.compareToIgnoreCase("133inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%13.3 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (manHinh.compareToIgnoreCase("13inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%13 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (manHinh.compareToIgnoreCase("14inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%14 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (manHinh.compareToIgnoreCase("154inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%15.4 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (manHinh.compareToIgnoreCase("156inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%15.6 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);

		} else if (manHinh.compareToIgnoreCase("173inch") == 0) {
			String jpql = "select p from Laptop p where status=1 and p.manHinh like '%17.3 inch%'";
			query = entityManager.createQuery(jpql, Laptop.class);
		}

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Laptop> filterBuySale(Integer pageNumber) {

		String jpql = "select p from Laptop p where 1=1 and p.khuyenMai is not null"; // JPQL: lấy tất cả Post trong db.
		Query query = entityManager.createQuery(jpql, Laptop.class);

		query.setFirstResult((pageNumber - 1) * LIMIT);
		query.setMaxResults(LIMIT);
		return query.getResultList();
	}

}
