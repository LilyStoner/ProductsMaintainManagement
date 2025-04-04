<%-- 
    Document   : viewProduct
    Created on : Feb 21, 2025, 11:49:16 PM
    Author     : sonNH (Modified to English)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%!
    // Arrays for number to English words conversion
    private static final String[] belowTwenty = {
        "", "One", "Two", "Three", "Four", "Five", "Six", 
        "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", 
        "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", 
        "Eighteen", "Nineteen"
    };
    private static final String[] tens = {
        "", "", "Twenty", "Thirty", "Forty", "Fifty", 
        "Sixty", "Seventy", "Eighty", "Ninety"
    };
    private static final String[] englishUnits = {"", "thousand", "million", "billion"};

    // Converts a positive number into English words.
    public String convertNumberToEnglishWords(long number) {
        if (number < 0) {
            return "Not valid";
        }
        if (number == 0) {
            return "zero";
        }
        String words = "";
        int unitIndex = 0;
        while (number > 0) {
            int n = (int)(number % 1000); // extract last three digits
            if (n != 0) {
                String s = convertLessThanOneThousandEnglish(n);
                if (!s.isEmpty()) {
                    words = s + " " + englishUnits[unitIndex] + " " + words;
                }
            }
            number /= 1000;
            unitIndex++;
        }
        return words.trim();
    }

    // Converts a number less than 1000 into English words.
    public String convertLessThanOneThousandEnglish(int number) {
        String result = "";
        if (number >= 100) {
            result += belowTwenty[number / 100] + " hundred";
            number %= 100;
            if (number > 0) {
                result += " ";
            }
        }
        if (number >= 20) {
            result += tens[number / 10];
            if (number % 10 > 0) {
                result += " " + belowTwenty[number % 10];
            }
        } else if (number > 0) {
            result += belowTwenty[number];
        }
        return result;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            /* Reset các kiểu mặc định */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            /* Cấu hình cơ bản cho body */
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f4f7fa;
                color: #333;
                line-height: 1.6;
            }
            /* Wrapper chính chia layout */
            .wrapper {
                display: flex;
                min-height: 100vh;
            }
            /* Thanh điều hướng bên trái (nếu có) */
            .navbar-left {
                width: 220px;
                background-color: #2c3e50;
                color: #fff;
                padding: 20px;
            }
            /* Khu vực main */
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
                background-color: #fff;
                padding: 20px;
            }
            /* Thanh điều hướng trên cùng */
            .navbar-top {
                background-color: #ecf0f1;
                padding: 10px 20px;
                border-bottom: 1px solid #dcdcdc;
            }
            /* Nội dung chính */
            .content {
                flex: 1;
                margin-top: 20px;
            }
            /* Container trung tâm */
            .container {
                max-width: 1200px;
                margin: 0 auto;
            }
            /* Styling cho form tìm kiếm và các nút */
            form {
                margin-bottom: 20px;
            }
            form label {
                font-weight: 600;
                margin-bottom: 5px;
                display: block;
            }
            form input[type="text"] {
                width: 100%;
                padding: 10px;
                margin: 5px 0 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }
            form button {
                background-color: #3498db;
                color: #fff;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            form button:hover {
                background-color: #2980b9;
            }
            /* Styling cho phần PDF preview */
            .pdf-preview {
                background: #f9f9f9;
                border: 1px solid #ddd;
                padding: 20px;
                border-radius: 4px;
                overflow-x: auto;
            }
            .pdf-header {
                font-size: 1.5rem;
                font-weight: 600;
                border-bottom: 2px solid #3498db;
                padding-bottom: 10px;
                margin-bottom: 15px;
                text-align: center;
            }
            .pdf-content {
                font-size: 0.95rem;
                line-height: 1.5;
            }
            /* Styling cho thông tin cửa hàng */
            .store-info p {
                margin-bottom: 8px;
            }
            /* Styling cho bảng danh sách linh kiện */
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
            }
            table, th, td {
                border: 1px solid #ddd;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #3498db;
                color: #fff;
            }
            tr:nth-child(even) {
                background-color: #f2f2f2;
            }
            /* Styling cho thông tin khách hàng */
            .customer-info {
                margin: 20px 0;
                display: flex;
                justify-content: space-between;
            }
            .customer-info p {
                margin-bottom: 10px;
            }
            /* Styling cho phần chữ ký */
            .signature-section {
                margin-top: 30px;
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .signature-section p {
                font-weight: 600;
            }
            .signature-box {
                border: 2px dashed #ccc;
                width: 150px;
                height: 50px;
            }
            /* Responsive cho thiết bị nhỏ hơn */
            @media (max-width: 768px) {
                .customer-info {
                    flex-direction: column;
                }
                .store-info {
                    flex-direction: column;
                    align-items: center;
                }
                .signature-section {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .navbar-left {
                    width: 180px;
                }
            }
            /* Bao quanh tất cả form bằng 1 class .button-row */
            .button-row {
                display: flex;
                flex-wrap: wrap;
                align-items: center;
                gap: 1rem;
                position: relative;
                margin-top: 10px;
            }
            /* Form tìm kiếm trên cùng 1 hàng */
            .search-form {
                display: flex;
                align-items: center;
                gap: 0.5rem;
                margin: 0;
            }
            .search-form label {
                display: inline-block;
                margin: 0;
                font-weight: 600;
                white-space: nowrap;
            }
            .search-form input[type="text"] {
                width: 180px;
                padding: 8px;
            }
            /* Hai form Export/Send đơn giản, chỉ có 1 nút */
            .inline-form {
                display: flex;
                align-items: center;
                margin: 0;
            }
            button {
                padding: 8px 12px;
                cursor: pointer;
                white-space: nowrap;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success" style="display: flex; justify-content: center; height: 30px; align-items: center">
                            ${successMessage}
                        </div>
                    </c:if>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger" style="display: flex; justify-content: center; height: 30px; align-items: center">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <div class="container">
                        <div class="row">
                            <!-- Cột 1: Search Form -->
                            <div class="col-md-5">
                                <h2>Search Warranty Card</h2>
                                <div class="search-section">
                                    <label for="warrantyCardCode">Enter Warranty Card Code:</label>
                                    <!-- Giữ lại giá trị người dùng nhập từ tham số -->
                                    <input type="text"
                                           id="warrantyCardCode"
                                           name="warrantyCardCode"
                                           placeholder="e.g., WC12345"
                                           required pattern="[A-Za-z0-9]+"
                                           title="Please enter only letters and numbers without spaces"
                                           form="searchForm"
                                           value="${warrantyCardCode != null ? warrantyCardCode : param.warrantyCardCode}" />

                                    <div class="button-row">
                                        <!-- Form Search -->
                                        <form id="searchForm" action="searchwc" method="post">
                                            <button type="submit">Search</button>
                                        </form>
                                        <!-- Form Export PDF -->
                                        <form id="exportForm" action="exportpdf" method="get" onsubmit="return validateExportForm();">
                                            <input type="hidden" id="hiddenWarrantyCode" name="warrantyCardCode"
                                                   value="${warrantyCardCode != null ? warrantyCardCode : param.warrantyCardCode}" />
                                            <button type="submit">Export PDF</button>
                                        </form>

                                        <!-- Form Send Warranty Card -->
                                        <form action="sendWC" method="post" onsubmit="return validateInput();">
                                            <input type="hidden" name="warrantyCardCode"
                                                   value="${warrantyCardCode != null ? warrantyCardCode : param.warrantyCardCode}" />
                                            <input type="hidden" name="customerName" value="${customer.name}" />
                                            <input type="hidden" name="customerEmail" value="${customer.email}" />
                                            <button type="submit">Send Warranty Card</button>
                                        </form>

                                    </div>
                                </div>
                            </div>
                            <!-- Cột 2: Preview Warranty Card -->
                            <div class="col-md-7">
                                <div class="pdf-preview">
                                    <div class="pdf-header">Warranty Card Preview</div>
                                    <div class="pdf-content">
                                        <!-- Phần nội dung preview -->
                                        <div style="display: flex; justify-content: space-around" class="store-info">
                                            <div>
                                                <p><strong>Store:</strong> ABC Electronics</p>
                                                <p><strong>Address:</strong> 123 XYZ Street, Ho Chi Minh City</p>
                                                <p><strong>Contact:</strong> 0901 234 567</p>
                                                <p><strong>Email:</strong> support@abc-electronics.com</p>
                                            </div>
                                            <div>
                                                <p><strong>Branch:</strong> ABC Electronics</p>
                                                <p><strong>Serial:</strong> 123 XYZ Street, Ho Chi Minh City</p>
                                                <p><strong>No:</strong> 0901 234 567</p>
                                            </div>
                                        </div>
                                        <hr>
                                        <c:if test="${not empty error}">
                                            <p style="color: red;">${error}</p>
                                        </c:if>
                                        <c:if test="${not empty warrantyCard}">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <p><strong>Warranty Card Code:</strong> ${warrantyCard.warrantyCardCode}</p>
                                                    <p><strong>Date Created:</strong> ${warrantyCard.createdDate}</p>
                                                    <p><strong>Return Date:</strong> ${warrantyCard.completedDate}</p>
                                                </div>
                                                <div class="col-md-6">
                                                    <p><strong>Staff In Charge:</strong> ${staffH.name}</p>
                                                    <p><strong>Staff Phone:</strong> ${staffH.phone}</p>
                                                    <p><strong>Staff Email:</strong> ${staffH.email}</p>
                                                </div>
                                                <hr>
                                                <!-- Thông tin khách hàng và sản phẩm -->
                                                <div class="customer-info" style="display: flex; justify-content: space-between">
                                                    <div>
                                                        <p><strong>Customer Name:</strong> ${customer.name}</p>
                                                        <p><strong>Date of Birth:</strong> 
                                                            <fmt:formatDate value="${customer.dateOfBirth}" pattern="dd-MM-yyyy" />
                                                        </p>
                                                        <p><strong>Customer Phone:</strong> ${customer.phone}</p>
                                                        <p><strong>Email:</strong> ${customer.email}</p>
                                                        <p><strong>Address:</strong> ${customer.address}</p>
                                                    </div>
                                                    <div>
                                                        <c:choose>
                                                            <c:when test="${isUnknownProduct}">
                                                                <p><strong>Product Name:</strong> ${product.productName}</p>
                                                                <p><strong>Product Code:</strong> ${product.productCode}</p>
                                                                <p><strong>Description:</strong> ${product.description}</p>
                                                                <p><strong>Received Date:</strong> ${product.receivedDate}</p>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <p><strong>Product Name:</strong> ${product.productName}</p>
                                                                <p><strong>Product Code:</strong> ${product.code}</p>
                                                                <p><strong>Brand:</strong> ${product.brandName}</p>
                                                                <p><strong>Type:</strong> ${product.productTypeName}</p>
                                                                <p><strong>Purchased Date:</strong> ${product.purchaseDate}</p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                                <hr>

                                                <table border="1">
                                                    <tr>
                                                        <th>Component Code</th>
                                                        <th>Component Name</th>
                                                        <th>Brand Name</th>
                                                        <th>Type Name</th>
                                                        <th>Price Per Piece</th>
                                                        <th>Number of Uses</th>
                                                        <th>Note</th>
                                                        <th>Total Price</th>
                                                    </tr>
                                                    <c:choose>
                                                        <c:when test="${not empty component}">
                                                            <c:set var="grandTotal" value="${0}" scope="page" />
                                                            <c:forEach var="detail" items="${component}">
                                                                <tr>
                                                                    <td>${detail.ComponentCode}</td>
                                                                    <td>${detail.ComponentName}</td>
                                                                    <td>${detail.BrandName}</td>
                                                                    <td>${detail.TypeName}</td>
                                                                    <td>${detail.pricePerPiece}</td>
                                                                    <td>${detail.numberOfUses}</td>
                                                                    <td>${detail.Note}</td>
                                                                    <td>
                                                                        <fmt:formatNumber value="${detail.totalPrice}"
                                                                                          type="number"
                                                                                          maxFractionDigits="0"
                                                                                          groupingUsed="false" />
                                                                    </td>
                                                                </tr>
                                                                <c:set var="grandTotal" value="${grandTotal + detail.totalPrice}" scope="page" />
                                                            </c:forEach>
                                                            <tr>
                                                                <td colspan="7" style="text-align:right"><strong>Total:</strong></td>
                                                                <td>
                                                                    <fmt:formatNumber value="${grandTotal}"
                                                                                      type="number"
                                                                                      maxFractionDigits="0"
                                                                                      groupingUsed="false" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="8" style="text-align:right; white-space: nowrap;">
                                                                    <strong>Total (in words): </strong>
                                                                    <%
                                                                        Object gtObj = pageContext.getAttribute("grandTotal");
                                                                        long total = 0;
                                                                        if (gtObj != null) {
                                                                            try {
                                                                                double doubleVal = Double.parseDouble(gtObj.toString());
                                                                                total = (long) doubleVal;
                                                                            } catch (Exception e) {
                                                                                total = 0;
                                                                            }
                                                                        }
                                                                        String words = convertNumberToEnglishWords(total).toLowerCase();
                                                                        if (words != null && words.length() > 0) {
                                                                            words = Character.toUpperCase(words.charAt(0)) + words.substring(1);
                                                                        }
                                                                        out.print(words);
                                                                    %>
                                                                    dollars
                                                                </td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <td colspan="9">No data available.</td>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </table>
                                                <hr>
                                                <div style="display: flex; justify-content: space-between">
                                                    <h4>Signature Confirmation</h4>
                                                    <h4>Hanoi, ${day}/${month}/${year}</h4>
                                                </div>
                                                <div style="display: flex; gap: 20px; align-items: center" class="signature-section">
                                                    <p><strong>Customer:</strong></p>
                                                    <div class="signature-box"></div>
                                                    <p><strong>Technical Staff:</strong></p>
                                                    <div class="signature-box"></div>
                                                </div>
                                                <div style="text-align: left; color: red; font-style: italic; margin-top: 20px;">
                                                    <p>- All your personal information will be kept strictly confidential. If any misconduct occurs on our part, we fully assume all responsibilities.</p>
                                                    <p>- Your personal data is used solely for warranty service purposes and will not be disclosed to any third party without your consent.</p>
                                                    <p>- You have the right to access, review, and request corrections for your personal data at any time.</p>
                                                </div>

                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div> <!-- end col-md-7 -->
                        </div> <!-- end row -->
                    </div> <!-- end container -->
                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div> <!-- end main -->
        </div> <!-- end wrapper -->

        <!-- Script kiểm tra trước khi submit form Export PDF -->
        <script>
            function validateExportForm() {
                var code = document.getElementById("warrantyCode").value.trim();
                if (code === "") {
                    alert("Please enter warranty card code before exporting PDF.");
                    return false;
                }
                // Gán giá trị đã nhập vào hidden input
                document.getElementById("hiddenWarrantyCode").value = code;
                return true;
            }
        </script>
        <script>
            // Hàm kiểm tra mã phiếu bảo hành nhập vào có hợp lệ không
            function validateInput() {
                var code = document.getElementById("warrantyCardCode").value.trim();
                var validRegex = /^[A-Za-z0-9]+$/; // chỉ cho phép chữ và số, không khoảng trắng hay ký tự đặc biệt
                if (code === "") {
                    alert("Please enter warranty card code.");
                    return false;
                }
                if (!validRegex.test(code)) {
                    alert("Invalid warranty card code. Please enter only letters and numbers without spaces.");
                    return false;
                }
                return true;
            }

            // Hàm kiểm tra dành riêng cho form Export PDF, cập nhật giá trị hidden input
            function validateExportForm() {
                if (!validateInput()) {
                    return false;
                }
                var code = document.getElementById("warrantyCardCode").value.trim();
                document.getElementById("hiddenWarrantyCode").value = code;
                return true;
            }
        </script>

        <script src="js/app.js"></script>
    </body>
</html>
