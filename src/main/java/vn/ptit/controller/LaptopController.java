package vn.ptit.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import vn.ptit.beans.Cart;
import vn.ptit.beans.Search;
import vn.ptit.entities.HoaDon;
import vn.ptit.entities.HoaDonDanhSachHang;
import vn.ptit.entities.Laptop;
import vn.ptit.repositories.HoaDonRepository;
import vn.ptit.repositories.IdBannerRepository;
import vn.ptit.repositories.LaptopManufacturerRepository;
import vn.ptit.repositories.LaptopRepository;
import vn.ptit.services.LaptopService;
import vn.ptit.services.PaypalService;
import vn.ptit.services.UserService;

@Controller
public class LaptopController {
	@Autowired
	LaptopRepository laptopRepository;
	@Autowired
	LaptopManufacturerRepository laptopManufacturerRepository;
	@Autowired
	LaptopService laptopsService;
	@Autowired
	HoaDonRepository hoaDonRepository;
	@Autowired
	UserService userService;
	@Autowired
	IdBannerRepository idBannerRepository;
	@Autowired
	Configuration config;
	@Autowired
	JavaMailSender sender;
	@Autowired
	PaypalService service;

	@RequestMapping(value = { "/item-details/{laptopSeo}" }, method = { RequestMethod.GET })
	public String laptop_details(@PathVariable String laptopSeo, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("laptop", laptopsService.getLaptopBySeo(laptopSeo).get(0));

		List<Laptop> laptops = laptopsService.getLaptopBySeo(laptopSeo).get(0).getLaptopManufacturer().getLaptops();
		List<Laptop> laptops2 = new ArrayList<>();
		for (int i = 0; i < laptops.size(); i++) {
			if (!laptops.get(i).getSeo().equalsIgnoreCase(laptopSeo)) {
				laptops2.add(laptops.get(i));
			}
		}
		Collections.shuffle(laptops2);
		model.addAttribute("laptop_same", laptops2);

		return "item_details";
	}

	@RequestMapping(value = { "/shop-all-item" }, method = { RequestMethod.GET })
	public String laptops(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.searchAllItem(pageNumber));
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "shop_all_item";
	}

	@RequestMapping(value = { "/shop-all-item/{laptopManufacturerSeo}" }, method = { RequestMethod.GET })
	public String indexWithProductType(@PathVariable String laptopManufacturerSeo, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.searchManufacturerItem(laptopManufacturerSeo, pageNumber));
		model.addAttribute("seoLaptopManufacturer", laptopManufacturerSeo);
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "shop_manufacturer_item";
	}

	@RequestMapping(value = { "/cart/checkout" }, method = { RequestMethod.GET })
	public String viewGiohang(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("hoadon", new HoaDon());

		return "shop_cart";
	}

	@RequestMapping(value = { "/cart/finish" }, method = { RequestMethod.POST })
	public String saveHoaDon(@ModelAttribute("hoadon") HoaDon hoadon, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		List<Cart> carts = new ArrayList<Cart>();
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("giohang") != null) {
			carts = (List<Cart>) httpSession.getAttribute("giohang");
		}

		hoadon.setNgayTao(new Date());
		for (int i = 0; i < carts.size(); i++) {
			Integer soLuongNhap = laptopsService.searchAmount(carts.get(i).getSeo()).get(0).getSoLuongNhap()
					- carts.get(i).getAmount();
			laptopsService.getLaptopBySeo(carts.get(i).getSeo()).get(0).setSoLuongNhap(soLuongNhap);

		}
		for (Cart cart : carts) {
			HoaDonDanhSachHang hoaDonDanhSachHang = new HoaDonDanhSachHang();
			hoaDonDanhSachHang.setTenLaptop(cart.getLaptopName());
			hoaDonDanhSachHang.setTenHangLaptop(cart.getLaptopManufacturerName());
			hoaDonDanhSachHang.setSoLuong(cart.getAmount());
			hoaDonDanhSachHang.setGiaTien(cart.getPrice());
			hoaDonDanhSachHang.setTongTienTungLoai(cart.getAllPrice());
			hoaDonDanhSachHang.setTenAnh(cart.getLaptopNameImg());
			hoadon.addHoaDonDanhSachHang(hoaDonDanhSachHang);
		}
		BigDecimal tongTien = new BigDecimal(0);
		if (httpSession.getAttribute("tongTien") != null) {
			tongTien = (BigDecimal) httpSession.getAttribute("tongTien");
		}
		hoadon.setTongtien(tongTien);
		hoadon.setStatus(true);

		if (hoadon.getHoaDonDanhSachHangs().size() == 0) {
			model.addAttribute("status", "faile");
		} else if (hoadon.getTenKhachHang() == "") {
			model.addAttribute("status", "faileNameNotNull");
		} else if (!hoadon.getEmail().matches("^[a-zA-Z]+[a-zA-Z0-9]*@{1}[a-zA-Z]+mail.com$")) {
			model.addAttribute("status", "faileEmailNotFormat");
		} else if (!hoadon.getSoDienThoai().matches("^0{1}[1-9]{1}\\d{8}$")) {
			model.addAttribute("status", "faileMobileNotFormat");
		} else if (hoadon.getDiaChi() == "") {
			model.addAttribute("status", "faileAddressNotNull");
		} else if (hoadon.getHinhThucThanhToan() == null) {
			model.addAttribute("status", "faileHinhThucNotNull");
		} else {

			if (hoadon.getHinhThucThanhToan().equalsIgnoreCase("Thanh toán qua chuyển khoản qua PayPal")) {
				// ---------------------------- Thanh toan qua paypal
				// -------------------------------------------------
				try {
					Payment payment = service.createPayment(hoadon.getTongtien().multiply(new BigDecimal(getTyGia())),
							"USD", "paypal", "sale", "test", "http://localhost:8080/cart/finish/cancel",
							"http://localhost:8080/cart/finish/success");
					httpSession.setAttribute("bill_paypal", hoadon);
					for (Links link : payment.getLinks()) {
						if (link.getRel().equals("approval_url")) {
							return "redirect:" + link.getHref();
						}
					}

				} catch (PayPalRESTException e) {

					e.printStackTrace();
				}
			} else {

				// ---------------------------- Gửi mail
				// -------------------------------------------------
				sendMail(hoadon);

				hoaDonRepository.save(hoadon);
				model.addAttribute("status", "success");
				httpSession.setAttribute("giohang", null);
				httpSession.setAttribute("soLuongMua", 0);
				httpSession.setAttribute("tongTien", 0);
				model.addAttribute("hoadon", new HoaDon());
			}

		}

		return "shop_cart";

	}

	@RequestMapping(value = { "/cart/finish/cancel" }, method = { RequestMethod.GET })
	public String cancelPay(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {
		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("hoadon", new HoaDon());
		model.addAttribute("status", "failePayPal");
		return "shop_cart";
	}

	@RequestMapping(value = { "/cart/finish/success" }, method = { RequestMethod.GET })
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
			final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		try {
			Payment payment = service.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				HttpSession httpSession = request.getSession();
				HoaDon hoadon = (HoaDon) httpSession.getAttribute("bill_paypal");
				hoaDonRepository.save(hoadon);
				
				// ---------------------------- Gửi mail
				// -------------------------------------------------
				sendMail(hoadon);
				
				model.addAttribute("status", "success");
				httpSession.setAttribute("giohang", null);
				httpSession.setAttribute("soLuongMua", 0);
				httpSession.setAttribute("tongTien", 0);
				model.addAttribute("hoadon", new HoaDon());
				return "shop_cart";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "shop_cart";
	}

	public void sendMail(HoaDon hoaDon) {
		Map<String, Object> map = new HashMap<>();
		map.put("date", hoaDon.getNgayTao().toString());
		map.put("bill", hoaDon);
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment
//			helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

			Template t = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);

			helper.setTo(hoaDon.getEmail());
			helper.setText(html, true);
			helper.setSubject("HÓA ĐƠN COMPUTER");
			helper.setFrom("computercuongpham999@gmail.com", "CÔNG TY COMPUTER CuongPham");
			sender.send(message);

		} catch (MessagingException | IOException | TemplateException e) {
			e.printStackTrace();
		}
	}

	public double getTyGia() {
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(
					"https://free.currconv.com/api/v7/convert?apiKey=73186a7f4f40e1abc58c&q=VND_USD&compact=y");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(url.openConnection().getInputStream()));
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line);
			}
			bufferedReader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject object = new JSONObject(content.toString());
		JSONObject object1 = object.getJSONObject("VND_USD");
		double tyGia = object1.getDouble("val");
		return tyGia;
	}

	// Lọc sản phẩm
	@RequestMapping(value = { "/shop-all-item/filter-by-status" }, method = { RequestMethod.GET })
	public String filterByStatus(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterByStatus(pageNumber));
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/filter_by_status";
	}

	@RequestMapping(value = { "/shop-all-item/sort-up-ascending" }, method = { RequestMethod.GET })
	public String sortUpAscending(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.sortUpAscending(pageNumber));
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/sort_up_ascending";
	}

	@RequestMapping(value = { "/shop-all-item/descending-arrangement" }, method = { RequestMethod.GET })
	public String descendingArrangement(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.descendingArrangement(pageNumber));
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/descending_arrangement";
	}

	@RequestMapping(value = { "/shop-all-item/filter-by-price/{price}" }, method = { RequestMethod.GET })
	public String filterByPrice(@PathVariable String price, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterByPrice(price, pageNumber));
		model.addAttribute("khoangGia", price);
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/filter_by_price";
	}

	@RequestMapping(value = { "/shop-all-item/filter-by-cpu/{cpu}" }, method = { RequestMethod.GET })
	public String filterByCpu(@PathVariable String cpu, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterByCpu(cpu, pageNumber));
		model.addAttribute("cpu", cpu);
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/filter_by_cpu";
	}

	@RequestMapping(value = { "/shop-all-item/filter-by-ram/{ram}" }, method = { RequestMethod.GET })
	public String filterByRam(@PathVariable String ram, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterByRam(ram, pageNumber));
		model.addAttribute("ram", ram);
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/filter_by_ram";
	}

	@RequestMapping(value = { "/shop-all-item/filter-by-o-cung/{oCung}" }, method = { RequestMethod.GET })
	public String filterByOCung(@PathVariable String oCung, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterByOCung(oCung, pageNumber));
		model.addAttribute("oCung", oCung);
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/filter_by_o_cung";
	}

	@RequestMapping(value = { "/shop-all-item/filter-by-vga/{vga}" }, method = { RequestMethod.GET })
	public String filterByVga(@PathVariable String vga, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterByVga(vga, pageNumber));
		model.addAttribute("vga", vga);
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/filter_by_vga";
	}

	@RequestMapping(value = { "/shop-all-item/filter-by-man-hinh/{manHinh}" }, method = { RequestMethod.GET })
	public String filterByManHinh(@PathVariable String manHinh, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		int pageNumber = Integer.parseInt(request.getParameter("page"));
		model.addAttribute("laptop", laptopsService.filterByManHinh(manHinh, pageNumber));
		model.addAttribute("manHinh", manHinh);
		model.addAttribute("banner", laptopsService.getBanner().get(laptopsService.getBanner().size() - 1));

		return "filter/filter_by_man_hinh";
	}

}
