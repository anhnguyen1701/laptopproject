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
	href="${pageContext.request.contextPath}/css/giohang.css">
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
	<div class="container giohang">
		<h4 class="tieude-tp">GIỎ HÀNG CỦA BẠN</h4>
		<c:choose>
			<c:when test="${status=='success'}">
				<div class="alert alert-success">
					<strong>Success!</strong> Bạn đã đặt hàng thành công!
				</div>
			</c:when>
			<c:when test="${status=='failePayPal'}">
				<div class="alert alert-danger">
					<strong>Faile!</strong> Bạn đã đặt hàng thất bại!
				</div>
			</c:when>
			<c:when test="${status=='faile'}">
				<div class="alert alert-danger">
					<strong>Faile!</strong> Bạn hãy chọn hàng cần mua!
				</div>
			</c:when>
			<c:when test="${status=='faileEdit'}">
				<div class="alert alert-danger">
					<strong>Faile!</strong> Bạn sửa thông tin thất bại! Số lượng bạn
					thêm vượt quá số lượng tồn trong kho.
				</div>
			</c:when>
			<c:when test="${status=='successEdit'}">
				<div class="alert alert-success">
					<strong>Success!</strong> Bạn sửa hàng thành công.
				</div>
			</c:when>
			<c:when test="${status=='successDelete'}">
				<div class="alert alert-success">
					<strong>Success!</strong> Bạn xóa hàng thành công.
				</div>
			</c:when>
		</c:choose>
		<div class="row card">
			<div class="btnluachon">
				<a href="/" class="btn btn-warning" style="color: #243a76;"> <i
					class="fas fa-angle-left"></i> Tiếp tục mua hàng
				</a>
			</div>

			<table class="table">
				<thead class="thead-light">
					<tr>
						<th style="padding-left: 10%;">Sản phẩm</th>
						<th class="gia">Giá</th>
						<th>Số lượng</th>
						<th class="tc-gia">Thành tiền</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="items" items="${giohang }">
						<tr>
							<td class="tensanpham">
								<div class="row">
									<div class="col-md-4">
										<a href="/item-details/${items.seo}"> <img
											src="${pageContext.request.contextPath}/files_laptops/${items.laptopNameImg }"
											alt="sanpham" width="100%">
										</a>
									</div>
									<div class="col-md-8">
										<a href="/item-details/${items.seo}"><h5>${items.laptopName}
											</h5></a> <span>Loại sản phẩm:</span> ${items.laptopManufacturerName}<br>
										<span>Bảo hành:</span> 24 tháng
									</div>

								</div>
							</td>
							<td class="gia"><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${items.price}" /> Đ</td>
							<td class="so-luong"><input class="form-control text-center nhap-so-luong"
								value="${items.amount}" type="number" min=1 style="width: 70px;"
								id="amount${items.seo}"></td>
							<td class="tc-gia"><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${items.allPrice}" /> Đ</td>
							<td class="hanh-dong"><a href="javascript:void(0);"
								onclick="Shop.editCart('modalEditItem', '${items.seo}')"
								class="btn btn-primary"><i class="fas fa-edit"></i></a> <a
								href="javascript:void(0);"
								onclick="Shop.deleteCart('modalDeleteItem', '${items.seo}')"
								class="btn btn-danger"><i class="fas fa-trash-alt"></i></a></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
			<div class="card-body" style="text-align: right;">
				<div class="tongtienthanhtoan">
					Tổng tiền đơn hàng : <strong><span id="total_value"><fmt:formatNumber
								type="number" maxFractionDigits="3" value="${tongTien }" /> </span> Đ</strong>
				</div>
			</div>

		</div>

		<form:form modelAttribute="hoadon" action="/cart/finish">
			<div class="row mt-5">
				<div class="col-md-4 thongtin">

					<div class="title_box_cart">THÔNG TIN KHÁCH HÀNG</div>
					<div class="form-group">
						<c:choose>
							<c:when test="${status=='faileNameNotNull'}">
								<div class="alert alert-danger">
									<strong>Faile!</strong> Họ tên không được để trống!
								</div>
							</c:when>
						</c:choose>
						<label class="required">Họ tên:</label>
						<form:input path="tenKhachHang" class="form-control"
							placeholder="Nhập họ tên" />
					</div>
					<div class="form-group">
						<c:choose>
							<c:when test="${status=='faileEmailNotFormat'}">
								<div class="alert alert-danger">
									<strong>Faile!</strong> Email không đúng định dạng!
								</div>
							</c:when>
						</c:choose>
						<label class="required">E-mail:</label>
						<form:input path="email" type="email" class="form-control"
							placeholder="Nhập email" />
					</div>
					<div class="form-group">
						<c:choose>
							<c:when test="${status=='faileMobileNotFormat'}">
								<div class="alert alert-danger">
									<strong>Faile!</strong> Số điện thoại không đúng định dạng!
								</div>
							</c:when>
						</c:choose>
						<label class="required">Số điện thoại:</label>
						<form:input path="soDienThoai" class="form-control"
							placeholder="Nhập số điện thoại" />
					</div>
					<div class="form-group">
						<c:choose>
							<c:when test="${status=='faileAddressNotNull'}">
								<div class="alert alert-danger">
									<strong>Faile!</strong> Địa chỉ không được để trống!
								</div>
							</c:when>
						</c:choose>
						<label class="required">Địa chỉ:</label>
						<form:input path="diaChi" class="form-control"
							placeholder="Nhập địa chỉ" />
					</div>
					<div class="form-group">
						<label>Ghi chú:</label>
						<form:textarea path="ghiChu" class="form-control" />
					</div>
				</div>
				<div class="col-md-4 thanhtoan">
					<div class="title_box_cart required">HÌNH THỨC THANH TOÁN</div>
					<div class="form-group">
						<c:choose>
							<c:when test="${status=='faileHinhThucNotNull'}">
								<div class="alert alert-danger">
									<strong>Faile!</strong> Hãy chọn hình thức thanh toán!
								</div>
							</c:when>
						</c:choose>
						<form:radiobutton path="hinhThucThanhToan"
							value="Thanh toán tiền mặt khi nhận hàng (tiền mặt / quẹt thẻ ATM, Visa, Master)"
							label="Thanh toán tiền mặt khi nhận hàng (tiền mặt / quẹt thẻ ATM, Visa, Master)" />
						<br>
						<form:radiobutton path="hinhThucThanhToan"
							value="Thanh toán qua chuyển khoản qua PayPal"
							label="Thanh toán qua chuyển khoản qua PayPal" />

					</div>
				</div>
				<div class="col-md-4">
					<table style="width: 100%; padding: 10px 5px;">
						<tbody>
							<tr class="txt_16">
								<td class="txt2"><b>Tổng tiền</b></td>
								<td class="txt2 text-right"><strong class="total_cart_new"><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${tongTien }" /> Đ</strong></td>
							</tr>
							<tr class="txt_16">
								<td class="txt2"><b>Thành tiền</b></td>
								<td class="txt2 text-right"><strong
									class="red total_cart_new"></strong><strong class="total_cart_new"><fmt:formatNumber type="number"
									maxFractionDigits="3" value="${tongTien }" /> Đ</strong><br> <span
									class="txt_12">(Giá chưa bao gồm VAT)</span></td>
							</tr>
						</tbody>
					</table>
					<div class="new-cart-button" style="margin-top: 10px;">
						<a href="/" class="btn btn-warning" style="width: 100%;color: #243a76;"> Chọn
							thêm sản phẩm </a>
						<button type="submit" class="btn btn-success" style="width: 100%">
							Đặt hàng</button>
					</div>
				</div>
			</div>
		</form:form>
		<h1 class="mb-5"></h1>
	</div>

	<!-- ---- -->


	<!--  FOOTER -->
	<%@ include file="/WEB-INF/views/layout/footer.jsp"%>
	<!-- ------- -->

</body>


</html>