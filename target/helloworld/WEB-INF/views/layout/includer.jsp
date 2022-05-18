<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- tich hop jstl vao jsp -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap-4.0.css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/shop_home.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/all.css">

<!-- JavaScript -->
<script src="${pageContext.request.contextPath}/js/chart.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/js/waypoint.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap-4.0.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.js"></script>
<script src="${pageContext.request.contextPath}/js/all.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/shop_home.js"></script>

<!-- FAVICONS -->
<link rel="apple-touch-icon" sizes="180x180"
	href="${pageContext.request.contextPath}/favicons/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="${pageContext.request.contextPath}/favicons/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="${pageContext.request.contextPath}/favicons/favicon-16x16.png">
<link rel="manifest"
	href="${pageContext.request.contextPath}/favicons/site.webmanifest">
<link rel="mask-icon"
	href="${pageContext.request.contextPath}/favicons/safari-pinned-tab.svg"
	color="#5bbad5">
<meta name="msapplication-TileColor" content="#da532c">
<meta name="theme-color" content="#ffffff">

<!-- MODAL -->
<%@ include file="/WEB-INF/views/layout/modal.jsp"%>
<!-- ----- -->