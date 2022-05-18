package vn.ptit.controller.admin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.ptit.beans.Search;
import vn.ptit.entities.Banner;
import vn.ptit.entities.IdBanner;
import vn.ptit.entities.Laptop;
import vn.ptit.repositories.IdBannerRepository;
import vn.ptit.repositories.LaptopManufacturerRepository;
import vn.ptit.repositories.LaptopRepository;
import vn.ptit.services.LaptopService;
import vn.ptit.services.UserService;

@Controller
public class AdminBannerController {
	@Autowired
	IdBannerRepository idBannerRepository;
	@Autowired
	LaptopManufacturerRepository laptopManufacturerRepository;
	@Autowired
	LaptopRepository laptopRepository;
	@Autowired
	UserService userService;
	@Autowired
	LaptopService laptopsService;

	@Value("${file.upload.path2}")
	private String attachmentPath;

	@RequestMapping(value = { "/admin/add-banner" }, method = { RequestMethod.GET })
	public String add_carsproduct(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		model.addAttribute("idBanner", new IdBanner());

		return "admin/add_banner";
	}

	@RequestMapping(value = "/admin/add-banner", method = RequestMethod.POST)
	public String add_movie(@RequestParam("bannerImg") MultipartFile[] bannerImg,
			@ModelAttribute("idBanner") IdBanner idBanner, final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) throws IllegalStateException, IOException {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		if (bannerImg != null && bannerImg.length > 0) {
			for (MultipartFile multipartFile : bannerImg) {
				if (multipartFile.getSize() <= 0)
					continue;
				Banner banner = new Banner();
				banner.setName(multipartFile.getOriginalFilename());
				banner.setMime(multipartFile.getContentType());
				banner.setPath(attachmentPath);
				idBanner.addBanner(banner);
				multipartFile.transferTo(new File(attachmentPath + "/" + multipartFile.getOriginalFilename()));
			}
		}
		idBanner.setNgayTao(new Date().toString());
		idBanner.setStatus(true);
		idBannerRepository.save(idBanner);
		model.addAttribute("status", "success");

		return "admin/add_banner";
	}

	@RequestMapping(value = { "/admin/list-banner" }, method = { RequestMethod.GET })
	public String list_movie(final ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response) {

		// must have
		model.addAttribute("laptopManufacturer", laptopsService.getAllLaptopManufacturer());
		model.addAttribute("search", new Search());
		model.addAttribute("userDis", userService.loadUserByUsername(request.getRemoteUser()));

		List<IdBanner> banners = idBannerRepository.findAll();
		model.addAttribute("banners", banners);

		return "admin/list_banner";
	}
}
