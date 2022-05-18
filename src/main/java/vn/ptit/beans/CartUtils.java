package vn.ptit.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import vn.ptit.entities.Laptop;
import vn.ptit.services.LaptopService;

public class CartUtils {
	@Autowired
	LaptopService laptopsService;

	public static void checkCart(Laptop laptop, HttpServletRequest request) {
		String laptopSeo = laptop.getSeo();
		// 1. Lấy danh sách sản phẩm trong giỏ hàng từ Session.
		List<Cart> carts = new ArrayList<Cart>();
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("giohang") != null) {
			carts = (List<Cart>) httpSession.getAttribute("giohang");
		}

		// 2. kiểm tra sản phẩm đã có trong giỏ hàng chưa?
		boolean isExists = false;
		for (Cart cart : carts) {
			if (cart.getSeo().equalsIgnoreCase(laptopSeo)) {
				isExists = true;
				break;
			}
		}

		// 3.
		if (!isExists) { // nếu chưa có trong giỏ hàng thì thêm mới.
			Cart cart = new Cart();
			cart.setLaptopNameImg(laptop.getLaptopAttachments().get(0).getName());
			cart.setLaptopManufacturerName(laptop.getLaptopManufacturer().getName());
			cart.setPrice(laptop.getGiaKhuyenMai());
			cart.setLaptopName(laptop.getName());
			cart.setAmount(1);
			cart.setAllPrice(cart.getPrice());
			cart.setSeo(laptop.getSeo());
			carts.add(cart);
		} else { // ngược lại cộng thêm số lượng cho sản phẩm đó.
			for (Cart cart : carts) {
				if (cart.getSeo().equalsIgnoreCase(laptopSeo)) {
					cart.setAmount(cart.getAmount() + 1);
					cart.setAllPrice(cart.getPrice().add(laptop.getGiaKhuyenMai()));
					break;
				}
			}
		}
		
		int soLuongMua=0;
		for(Cart cart: carts) {
			soLuongMua+=cart.getAmount();
		}

		httpSession.setAttribute("status", null);
		httpSession.setAttribute("giohang", carts);
		httpSession.setAttribute("soLuongMua", soLuongMua);

	}

	public static void deleteCart(String cartSeo, HttpServletRequest request) {
		List<Cart> carts = new ArrayList<Cart>();
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("giohang") != null) {
			carts = (List<Cart>) httpSession.getAttribute("giohang");
		}

		for (int i = 0; i < carts.size(); i++) {
			if (carts.get(i).getSeo().equalsIgnoreCase(cartSeo)) {
				carts.remove(i);
			}
		}
		
		int soLuongMua=0;
		for(Cart cart: carts) {
			soLuongMua+=cart.getAmount();
		}
		httpSession.setAttribute("giohang", carts);
		httpSession.setAttribute("status", "successDelete");
		httpSession.setAttribute("soLuongMua", soLuongMua);
	}

	public static void editCart(String cartSeo, String amount, Integer soLuongNhap, HttpServletRequest request) {
		List<Cart> carts = new ArrayList<Cart>();
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("giohang") != null) {
			carts = (List<Cart>) httpSession.getAttribute("giohang");
		}
		Integer soLuong = Integer.parseInt(amount);
		if (soLuongNhap >= soLuong) {

			for (int i = 0; i < carts.size(); i++) {

				if (carts.get(i).getSeo().equalsIgnoreCase(cartSeo)) {
					carts.get(i).setAmount(soLuong);
					BigDecimal gia = carts.get(i).getPrice();
					for (int j = 1; j < carts.get(i).getAmount(); j++) {
						gia = gia.add(carts.get(i).getPrice());
					}
					carts.get(i).setAllPrice(gia);
				}
			}
			
			int soLuongMua=0;
			for(Cart cart: carts) {
				soLuongMua+=cart.getAmount();
			}
			httpSession.setAttribute("giohang", carts);
			httpSession.setAttribute("status", "successEdit");
			httpSession.setAttribute("soLuongMua", soLuongMua);
		} else {
			httpSession.setAttribute("status", "faileEdit");
		}
	}

	public static void tongTien(HttpServletRequest request) {
		List<Cart> carts = new ArrayList<Cart>();
		HttpSession httpSession = request.getSession();
		if (httpSession.getAttribute("giohang") != null) {
			carts = (List<Cart>) httpSession.getAttribute("giohang");
		}
		BigDecimal t = new BigDecimal(0);
		for (int i = 0; i < carts.size(); i++) {
			t = t.add(carts.get(i).getAllPrice());
		}
		httpSession.setAttribute("tongTien", t);
	}

}
