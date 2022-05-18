<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- sử dụng taglibs của spring để bind-data từ end-point trả về. -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- tích hợp jstl vào jsp -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="description"
	content="ban laptop uy tin, chat luong, dich vu tot">
<meta name="keywords"
	content="laptop, asus, dell, hp, lenovo, acer, apple, msi, lg">
<meta name="author" content="CuongPham">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- CSS & JAVA_SCRIPT -->
<%@ include file="/WEB-INF/views/layout/includer.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/shop_all-item.css">
<!-- ----------------- -->

<title>Laptop Project</title>
</head>

<body>

	<div id="fb-root"></div>
	<script async defer crossorigin="anonymous"
		src="https://connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v8.0"
		nonce="ChmFo6ST"></script>

	<!-- HEADER -->
	<%@ include file="/WEB-INF/views/layout/header.jsp"%>
	<!-- ------ -->

	<!-- MAIN -->
	<div id="main" class="container all-item">

		<h4 class="tieude-tp">LAPTOP</h4>
		<div class="loc-sp">
			<span class="title-x">LỌC SẢN PHẨM</span>
			<div class="danh-muc">
				<span class="title">DANH MỤC</span>
				<ul class="list-unstyled">
					<c:forEach var="laptopManufacturer_" items="${laptopManufacturer }">
						<li><a
							href="/shop-all-item/${laptopManufacturer_.seo }/?page=1"><i
								class="fas fa-angle-double-right"></i> Laptop
								${laptopManufacturer_.name }</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="khoang-gia">
				<span class="title">KHOẢNG GIÁ</span>
				<ul class="list-unstyled">
					<li><a
						href="/shop-all-item/filter-by-price/duoi10trieu/?page=1"><i
							class="far fa-square"></i> Dưới 10 triệu</a></li>
					<li><a
						href="/shop-all-item/filter-by-price/10den20trieu/?page=1"><i
							class="far fa-square"></i> 10 triệu - 20 triệu</a></li>
					<li><a
						href="/shop-all-item/filter-by-price/20den30trieu/?page=1"><i
							class="far fa-square"></i> 20 triệu - 30 triệu</a></li>
					<li><a
						href="/shop-all-item/filter-by-price/30den40trieu/?page=1"><i
							class="far fa-square"></i> 30 triệu - 40 triệu</a></li>
					<li><a
						href="/shop-all-item/filter-by-price/40den50trieu/?page=1"><i
							class="far fa-square"></i> 40 triệu - 50 triệu </a></li>
					<li><a
						href="/shop-all-item/filter-by-price/tren50trieu/?page=1"><i
							class="far fa-square"></i> Trên 50 triệu</a></li>
				</ul>
			</div>
			<div class="cpu">
				<span class="title">CPU</span>
				<ul class="list-unstyled">
					<li><a
						href="/shop-all-item/filter-by-cpu/intel-core-i3/?page=1"><i
							class="far fa-square"></i> Intel Core i3</a></li>
					<li><a
						href="/shop-all-item/filter-by-cpu/intel-core-i5/?page=1"><i
							class="far fa-square"></i> Intel Core i5</a></li>
					<li><a
						href="/shop-all-item/filter-by-cpu/intel-core-i7/?page=1"><i
							class="far fa-square"></i> Intel Core i7</a></li>
					<li><a
						href="/shop-all-item/filter-by-cpu/intel-core-i9/?page=1"><i
							class="far fa-square"></i> Intel Core i9</a></li>
					<li><a href="/shop-all-item/filter-by-cpu/AMD3/?page=1"><i
							class="far fa-square"></i> AMD Ryzen 3 </a></li>
					<li><a href="/shop-all-item/filter-by-cpu/AMD5/?page=1"><i
							class="far fa-square"></i> AMD Ryzen 5</a></li>
					<li><a href="/shop-all-item/filter-by-cpu/AMD7/?page=1"><i
							class="far fa-square"></i> AMD Ryzen 7 </a></li>
					<li><a href="/shop-all-item/filter-by-cpu/AMD9/?page=1"><i
							class="far fa-square"></i> AMD Ryzen 9 </a></li>
				</ul>
			</div>
			<div id="waypoint_here" class="ram">
				<span class="title">RAM</span>
				<ul class="list-unstyled">
					<li><a href="/shop-all-item/filter-by-ram/4gb/?page=1"><i
							class="far fa-square"></i> 4GB</a></li>
					<li><a href="/shop-all-item/filter-by-ram/8gb/?page=1"><i
							class="far fa-square"></i> 8GB</a></li>
					<li><a href="/shop-all-item/filter-by-ram/16gb/?page=1"><i
							class="far fa-square"></i> 16GB</a></li>
					<li><a href="/shop-all-item/filter-by-ram/32gb/?page=1"><i
							class="far fa-square"></i> 32GB</a></li>
				</ul>
			</div>

			<div class="o-cung">
				<span class="title">Ổ CỨNG</span>
				<ul class="list-unstyled">
					<li><a href="/shop-all-item/filter-by-o-cung/SSD/?page=1"><i
							class="far fa-square"></i> SSD</a></li>
					<li><a href="/shop-all-item/filter-by-o-cung/HHD/?page=1"><i
							class="far fa-square"></i> HHD</a></li>
				</ul>
			</div>

			<div class="vga">
				<span class="title">VGA</span>
				<ul class="list-unstyled">
					<li><a href="/shop-all-item/filter-by-vga/NVIDIA/?page=1"><i
							class="far fa-square"></i> NVIDIA</a></li>
					<li><a href="/shop-all-item/filter-by-vga/AMD/?page=1"><i
							class="far fa-square"></i> AMD</a></li>
					<li><a href="/shop-all-item/filter-by-vga/onboard/?page=1"><i
							class="far fa-square"></i> Onboard</a></li>
				</ul>
			</div>

			<div class="man-hinh">
				<span class="title">KÍCH THƯỚC MÀN HÌNH</span>
				<ul class="list-unstyled">
					<li><a
						href="/shop-all-item/filter-by-man-hinh/123inch/?page=1"><i
							class="far fa-square"></i> 12.3 inch</a></li>
					<li><a
						href="/shop-all-item/filter-by-man-hinh/125inch/?page=1"><i
							class="far fa-square"></i> 12.5 inch</a></li>
					<li><a
						href="/shop-all-item/filter-by-man-hinh/133inch/?page=1"><i
							class="far fa-square"></i> 13.3 inch</a></li>
					<li><a href="/shop-all-item/filter-by-man-hinh/13inch/?page=1"><i
							class="far fa-square"></i> 13 inch</a></li>
					<li><a href="/shop-all-item/filter-by-man-hinh/14inch/?page=1"><i
							class="far fa-square"></i> 14 inch</a></li>
					<li><a
						href="/shop-all-item/filter-by-man-hinh/154inch/?page=1"><i
							class="far fa-square"></i> 15.4 inch</a></li>
					<li><a
						href="/shop-all-item/filter-by-man-hinh/156inch/?page=1"><i
							class="far fa-square"></i> 15.6 inch</a></li>
					<li><a
						href="/shop-all-item/filter-by-man-hinh/173inch/?page=1"><i
							class="far fa-square"></i> 17.3 inch</a></li>
				</ul>
			</div>
		</div>

		<div class="laptop-sp">
			<div id="demo" class="carousel slide" data-ride="carousel">

				<ul class="carousel-indicators">
					<li data-target="#demo" data-slide-to="0" class="active"></li>
					<c:forEach var="i" begin="1" end="${banner.banners.size()-1}">
        				<li data-target="#demo" data-slide-to="${i }"></li>
					</c:forEach>
				</ul>

				<div class="carousel-inner">
					<div class="carousel-item active">
						<img
							src="${pageContext.request.contextPath}/files_banners/${banner.banners.get(0).name }"
							alt="qc">
					</div>
					<c:forEach var="banner_" items="${banner.banners }"
						varStatus="loop">
						<c:if test="${loop.index >0 }">
							<div class="carousel-item">
								<img
									src="${pageContext.request.contextPath}/files_banners/${banner_.name }"
									alt="qc">
							</div>
						</c:if>
					</c:forEach>
				</div>

				<a class="carousel-control-prev" href="#demo" data-slide="prev">
					<span class="carousel-control-prev-icon"></span>
				</a> <a class="carousel-control-next" href="#demo" data-slide="next">
					<span class="carousel-control-next-icon"></span>
				</a>

			</div>

			<div class="laptop-list">
				<div class="list-sort">
					<select id="other_filter" onchange="location.href=this.value">
						<option value="0">Tình trạng kho hàng</option>
						<option value="/shop-all-item/filter-by-status/?page=1">Còn
							hàng</option>
					</select> <select id="sort-select" onchange="location.href=this.value">
						<option value="0">Sắp xếp sản phẩm</option>
						<option value="/shop-all-item/sort-up-ascending/?page=1">Giá
							tăng dần</option>
						<option value="/shop-all-item/descending-arrangement/?page=1">Giá
							giảm dần</option>
					</select>
					<div class="paging pa1">
						<a
							href="javascript:Shop.goPrev('/shop-all-item/filter-buy-sale/')">Previous</a>
						<a href="/shop-all-item/filter-buy-sale/?page=1">1</a> <a
							href="/shop-all-item/filter-buy-sale/?page=2">2</a> <a
							href="/shop-all-item/filter-buy-sale/?page=3">3</a> <a
							href="/shop-all-item/filter-buy-sale/?page=4">4</a> <a
							href="/shop-all-item/filter-buy-sale/?page=5">5</a> <a
							href="javascript:Shop.goNext('/shop-all-item/filter-buy-sale/')">Next</a>
					</div>
				</div>

				<div class="list-sp">
					<div class="row">
						<c:forEach var="laptop_" items="${laptop }">
							<div class="col-md-3">
								<a href="/item-details/${laptop_.seo}"><img
									src="${pageContext.request.contextPath}/files_laptops/${laptop_.laptopAttachments.get(0).name }"
									alt="laptop"></a>
								<div class="card-body">
									<a href="/item-details/${laptop_.seo}">
										<h5 class="card-title">${laptop_.name }</h5>
									</a>
									<c:choose>
										<c:when test="${not empty laptop_.khuyenMai }">
											<div class="gia-goc"><fmt:formatNumber type="number" maxFractionDigits="3"
													value="${laptop_.price }" /> Đ</div>
											<div class="khuyen-mai">(Tiết kiệm: ${laptop_.khuyenMai }%)</div>
										</c:when>
										<c:when test="${empty laptop_.khuyenMai }">
											<div class="gia-goc"></div>
											<div class="khuyen-mai"></div>
										</c:when>
									</c:choose>
									<h6 class="card-title"><fmt:formatNumber type="number" maxFractionDigits="3"
													value="${laptop_.getGiaKhuyenMai() }" /> Đ</h6>
									<div class="check-item">
										<i class="fas fa-check"></i> ${laptop_.getTrangThai() }
									</div>
									<a href="javascript:Shop.addToCart('${laptop_.seo}');"
										class="shop-now"><i class="fas fa-shopping-cart"></i> Mua
										ngay</a>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="my-5"></div>
				<div class="list-sort">

					<div class="paging mx-auto">
						<a
							href="javascript:Shop.goPrev('/shop-all-item/filter-buy-sale/')">Previous</a>
						<a href="/shop-all-item/filter-buy-sale/?page=1">1</a> <a
							href="/shop-all-item/filter-buy-sale/?page=2">2</a> <a
							href="/shop-all-item/filter-buy-sale/?page=3">3</a> <a class="pa4"
							href="/shop-all-item/filter-buy-sale/?page=4">4</a> <a class="pa5"
							href="/shop-all-item/filter-buy-sale/?page=5">5</a> <a
							href="javascript:Shop.goNext('/shop-all-item/filter-buy-sale/')">Next</a>
					</div>
				</div>


			</div>
		</div>

	</div>
	<div class="clear-with-height"></div>
	<!-- --- -->

	<!--  FOOTER -->
	<%@ include file="/WEB-INF/views/layout/footer.jsp"%>
	<!-- ------- -->

</body>


</html>