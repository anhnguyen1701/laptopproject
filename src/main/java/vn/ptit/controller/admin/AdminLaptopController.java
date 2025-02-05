package vn.ptit.controller.admin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.ptit.beans.ProductReport;
import vn.ptit.beans.Search;
import vn.ptit.beans.Utilities;
import vn.ptit.entities.HoaDon;
import vn.ptit.entities.Laptop;
import vn.ptit.entities.LaptopAttachment;
import vn.ptit.entities.UserInfo;
import vn.ptit.repositories.HoaDonRepository;
import vn.ptit.repositories.LaptopManufacturerRepository;
import vn.ptit.repositories.LaptopRepository;
import vn.ptit.repositories.TotalVisitRepository;
import vn.ptit.repositories.UserInfoRepository;
import vn.ptit.services.LaptopService;
import vn.ptit.services.UserService;

@Controller
public class AdminLaptopController {
	@Autowired
	LaptopManufacturerRepository laptopManufacturerRepository;
	@Autowired
	LaptopRepository laptopRepository;
	@Autowired
	UserService userService;
	@Autowired
	HoaDonRepository hoaDonRepository;
	@Autowired
	LaptopService laptopService;
	@Autowired TotalVisitRepository totalVisitRepository;
	@Autowired UserInfoRepository userInfoRepository;
	

	@Value("${file.upload.path}")
	private String attachmentPath;
	
	private String seoEdit;

	@RequestMapping(value = { "/admin/add-laptop" }, method = { RequestMethod.GET })
	public String add_laptop(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("laptop", new Laptop());
		return "admin/add_laptop";
	}

	@RequestMapping(value = "/admin/add-laptop", method = RequestMethod.POST)
	public String add_laptop(@RequestParam("laptopImage") MultipartFile[] laptopImage,
			@ModelAttribute("laptop") Laptop laptop, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IllegalStateException, IOException {

		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		if (laptopImage != null && laptopImage.length > 0) {
			for (MultipartFile multipartFile : laptopImage) {
				if (multipartFile.getSize() <= 0)
					continue;
				LaptopAttachment laptopAttachment = new LaptopAttachment();
				laptopAttachment.setName(multipartFile.getOriginalFilename());
				laptopAttachment.setMime(multipartFile.getContentType());
				laptopAttachment.setPath(attachmentPath);
				laptop.addLaptopAttachment(laptopAttachment);
				multipartFile.transferTo(new File(attachmentPath + "/" + multipartFile.getOriginalFilename()));
			}
		}
		Utilities utilities = new Utilities();
		laptop.setSeo(utilities.createSeoLink(laptop.getName().substring(0, laptop.getName().indexOf("("))));
		laptop.setStatus(true);
		laptopRepository.save(laptop);
		model.addAttribute("status", "success");

		return "admin/add_laptop";
	}

	@RequestMapping(value = { "/admin/manage" }, method = { RequestMethod.GET })
	public String quanly(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response) {
		
		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));
		model.addAttribute("totalVisit", totalVisitRepository.findAll().get(0).getTotal());
		
		List<ProductReport> listProductReport=new ArrayList<>();
		List<HoaDon> hoaDons = hoaDonRepository.findAll();
		BigDecimal t=new BigDecimal(0);
		for (HoaDon hoaDon : hoaDons) {
			t=t.add(hoaDon.getTongtien());
			for(int i=0;i<hoaDon.getHoaDonDanhSachHangs().size();i++) {
				listProductReport.add(new ProductReport(hoaDon.getHoaDonDanhSachHangs().get(i).getTenLaptop(),hoaDon.getHoaDonDanhSachHangs().get(i).getSoLuong()));
			}
		}
		
		model.addAttribute("totalMoney", t);
		
		model.addAttribute("totalUser", userInfoRepository.findAll().size());
		
		model.addAttribute("totalOrder", hoaDons.size());
		
		Map<String,Integer> res=listProductReport.stream().collect(Collectors.groupingBy(ProductReport::getNameProduct,Collectors.summingInt(ProductReport::getAmount)));
		List<ProductReport> listRP=new ArrayList<>();
		Set<String> key=res.keySet();
		for (String k : key) {
	        listRP.add(new ProductReport(k,res.get(k)));
	    }
		
		listRP.sort(new Comparator<ProductReport>() {

			@Override
			public int compare(ProductReport o1, ProductReport o2) {
				return o2.getAmount()-o1.getAmount();
			}
			
		});
		
		model.addAttribute("listProductRP", listRP);
		

		return "admin/manage";
	}

	@RequestMapping(value = { "/admin/list-laptop" }, method = { RequestMethod.GET })
	public String listLaptop(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("laptop", laptopRepository.findAll());

		return "admin/list_laptop";
	}

	@RequestMapping(value = { "/admin/edit-laptop/{laptopId}" }, method = { RequestMethod.GET })
	public String edit_laptop(@PathVariable int laptopId, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("laptop", laptopRepository.getOne(laptopId));
		seoEdit = laptopRepository.getOne(laptopId).getSeo();

		return "admin/edit_laptop";
	}

	@RequestMapping(value = { "/admin/edit-laptop" }, method = { RequestMethod.POST })
	public String saveEditLaptop(@RequestParam("laptopImage") MultipartFile[] laptopImage, @ModelAttribute("laptop") Laptop laptop, final ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response) throws IllegalStateException, IOException {

		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));
		
		laptop.setSeo(seoEdit);
		
		if (laptopImage != null && laptopImage.length > 0 && !laptopImage[0].isEmpty()) {
			List<LaptopAttachment> productImgs = laptopService.getLaptopBySeo(seoEdit).get(0).getLaptopAttachments();
			for (LaptopAttachment laptopAttachment : productImgs) {
				laptop.removeLaptopAttachment(laptopAttachment);
			}
			
			for (MultipartFile multipartFile : laptopImage) {
				if (multipartFile.getSize() <= 0)
					continue;
				LaptopAttachment laptopAttachment = new LaptopAttachment();
				laptopAttachment.setName(multipartFile.getOriginalFilename());
				laptopAttachment.setMime(multipartFile.getContentType());
				laptopAttachment.setPath(attachmentPath);
				laptop.addLaptopAttachment(laptopAttachment);
				multipartFile.transferTo(new File(attachmentPath + "/" + multipartFile.getOriginalFilename()));
			}
		}
		laptop.setStatus(true);

		laptopRepository.save(laptop);
		model.addAttribute("status", "success");
		return "admin/edit_laptop";
	}

	// hoa don

	@RequestMapping(value = { "/admin/bill/{hoadonId}" }, method = { RequestMethod.GET })
	public String hoadon(@PathVariable int hoadonId, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("hoa_don", hoaDonRepository.getOne(hoadonId));
		SimpleDateFormat f = new SimpleDateFormat("'Ngày tạo:' hh:mm:ss E dd/MM/yyyy");
		model.addAttribute("date", f.format(hoaDonRepository.getOne(hoadonId).getNgayTao()));
		return "admin/bill";
	}

	@RequestMapping(value = { "/admin/list-bill" }, method = { RequestMethod.GET })
	public String list_hoadon(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("hoadons", hoaDonRepository.findAll());
		return "admin/list_bill";
	}

}
