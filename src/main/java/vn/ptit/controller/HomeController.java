package vn.ptit.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.ptit.beans.Search;
import vn.ptit.entities.Laptop;
import vn.ptit.entities.TotalVisit;
import vn.ptit.repositories.IdBannerRepository;
import vn.ptit.repositories.LaptopManufacturerRepository;
import vn.ptit.repositories.LaptopRepository;
import vn.ptit.repositories.TotalVisitRepository;
import vn.ptit.services.LaptopService;
import vn.ptit.services.UserService;

@Controller
public class HomeController {

	@Autowired
	LaptopService laptopsService;
	@Autowired
	LaptopRepository laptopRepository;
	@Autowired
	LaptopManufacturerRepository laptopManufacturerRepository;
	@Autowired
	UserService userService;
	@Autowired
	IdBannerRepository idBannerRepository;
	@Autowired TotalVisitRepository totalVisitRepository;
	int cnt=0;

	@RequestMapping(value = { "/", "/index", "/home" }, method = { RequestMethod.GET })
	public String index(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));
		List<TotalVisit> visits= totalVisitRepository.findAll();
		
		if(cnt==0) {
			cnt=visits.get(0).getTotal();
		}
		
		cnt++;
		
		visits.get(0).setTotal(cnt);
		totalVisitRepository.saveAll(visits);

		return "index";
	}

	@RequestMapping(value = { "/shop-all-item/search-name" }, method = { RequestMethod.POST })
	public String search1(@ModelAttribute("search") Search search, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		String nameLaptop = search.getName();
		model.addAttribute("laptop", laptopsService.searchName(nameLaptop));
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size()-1));
		return "search";
	}

	@RequestMapping(value = { "/shop-all-item/filter-buy-sale" }, method = { RequestMethod.GET })
	public String filterBuySale(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterBuySale(pageNumber));
		model.addAttribute("search", new Search());
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size()-1));

		return "filter/filter_buy_sale";
	}

	@RequestMapping(value = { "/gioi-thieu" }, method = { RequestMethod.GET })
	public String gioiThieu(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		return "gioi_thieu";
	}

	@RequestMapping(value = { "/chinh-sach/chinh-sach-bao-hanh" }, method = { RequestMethod.GET })
	public String chinhSachBaoHanh(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));
		
		return "chinh_sach_bao_hanh";
	}

	@RequestMapping(value = { "/chinh-sach/chinh-sach-giao-hang" }, method = { RequestMethod.GET })
	public String chinhSachGiaoHang(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		return "chinh_sach_giao_hang";
	}

	@RequestMapping(value = { "/chinh-sach/chinh-sach-hoan-tien" }, method = { RequestMethod.GET })
	public String chinhSachHoanTien(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));
		
		return "chinh_sach_hoan_tien";
	}

	@RequestMapping(value = { "/chinh-sach/chinh-sach-hang-chinh-hang" }, method = { RequestMethod.GET })
	public String chinhSachHangChinhHang(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		return "chinh_sach_hang_chinh_hang";
	}

	// Login
	@RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
	public String login(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		return "login";

	}

	@RequestMapping(value = { "/error-403" }, method = { RequestMethod.GET })
	public String error403(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		return "error_403";
	}

}
