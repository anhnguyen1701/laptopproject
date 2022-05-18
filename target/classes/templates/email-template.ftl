<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Bill Mail</title>
</head>

<body style="background-color: #f1f1f1;">
  <table style="width: 600px;margin: 0px auto;font-family: monospace;background-color: white;">
    <tr>
      <td style="margin-left: 10px;">
        <div style="width: 100%;display: flex;">
          <div style="width: 30%;text-align: left;padding-left: 15px;margin-top: 15px;margin-bottom: 15px;">
            <img src='https://cuongphamptit999.github.io/cuongpham.github.io/img/logo.png' width='150px'>
          </div>
          <div style="width: 70%;text-align: right;padding-right: 15px;margin-top: 15px;margin-bottom: 15px;">
            <h1>HÓA ĐƠN</h1>
            <h4>Ngày: ${date }</h4>
          </div>
        </div>
        <hr style="margin-top: 1rem;
        margin-bottom: 1rem;
        border: 0;
        border-top: 1px solid rgba(0,0,0,.1);">
      </td>
    </tr>
    <tr>
      <td>
        <div style="width: 100%;display: flex;">
          <div style="width: 50%;text-align: left;padding-left: 15px;margin-bottom: 15px;">
            <h4><b>Đến</b></h4>
            <p>${bill.tenKhachHang }
              <br>${bill.soDienThoai }<br>
              ${bill.email }<br>
              ${bill.diaChi }
            </p>
          </div>
          <div style="width: 50%;text-align: right;padding-right: 15px;margin-bottom: 15px;">
            <h4><b>Từ</b></h4>
            <p><b>CÔNG TY COMPUTER CƯỜNG PHẠM</b>
              <br>0961656067<br>
              computercuongpham999@gmail.com<br>
              19A Xa La, Hà Đông, Hà Nội
            </p>
          </div>
        </div>
      </td>
    </tr>

    <tr>
      <td>
        <table style="width: 100%; ">
          <tr style="background-color: #e9ecef;">
            <th style="width: 40%;text-align: left;">
              Tên sản phẩm
            </th>
            <th>
              Đơn giá
            </th>
            <th>
              Số lượng
            </th>
            <th>
              Thành tiền
            </th>
          </tr>
          
          <#list bill.hoaDonDanhSachHangs as item_bills>
			<tr>
            <td style="width: 40%;text-align: left;">
              ${item_bills.tenLaptop }
            </td>
            <td style="text-align: center;">
              ${item_bills.giaTien } Đ
            </td>
            <td style="text-align: center;">
              ${item_bills.soLuong }
            </td>
            <td style="text-align: center;">
              ${item_bills.tongTienTungLoai } Đ
            </td>
          </tr>
		</#list>
		
		</table>
      </td>
    </tr>

    <tr>
      <td style="padding-right: 15px;">
        <div style="width:100%;text-align: right;margin-top: 15px;margin-bottom: 15px;">
          <b>Tổng tiền: </b>${bill.tongtien} Đ
        </div>
      </td>
    </tr>

    <tr>
      <td>
        <hr style="margin-top: 1rem;
        margin-bottom: 1rem;
        border: 0;
        border-top: 1px solid rgba(0,0,0,.1);">
        <div style="width:100%;text-align: center;height: 130px;padding-top: 20px;">
          <h3>Cảm ơn bạn đã mua hàng của chúng tôi</h3>
        </div>
      </td>
    </tr>

  </table>
</body>

</html>