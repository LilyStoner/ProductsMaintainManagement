<%-- 
    Document   : updateProduct1
    Created on : Feb 6, 2025, 5:07:44 AM
    Author     : sonNH
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                font-family: 'Inter', sans-serif;
                background-color: #f5f6fa;
                color: #333;
                line-height: 1.6;
            }
            .wrapper {
                display: flex;
                min-height: 100vh;
            }
            .wrapper > .jspIncludeSidebar,
            .wrapper > .navbar-left {
                width: 250px;
                background-color: #2c3e50;
                color: #fff;
                padding: 20px;
            }
            /* Phần main (nội dung chính) */
            .main {
                flex: 1;
                display: flex;
                flex-direction: column;
            }
            /* Nội dung chính */
            .content {
                flex: 1;
                padding: 20px;
            }
            /* Tiêu đề form với màu xanh dương chủ đạo */
            .content h2 {
                margin-bottom: 20px;
                font-size: 24px;
                color: #326ABC;
            }
            /* Các thông báo lỗi và thành công */
            .content .error,
            .content .success {
                text-align: center;
                margin-bottom: 15px;
                padding: 10px;
                border-radius: 4px;
            }
            .content .error {
                background-color: #f8d7da;
                color: #a94442;
            }
            .content .success {
                background-color: #d4edda;
                color: #40754c;
            }
            /* Form */
            form {
                background-color: #fff;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            /* Label và input */
            form label {
                display: block;
                margin-bottom: 5px;
                font-weight: 600;
                color: #34495e;
            }
            form input[type="number"],
            form input[type="date"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
            }
            .btn {
                display: inline-block;
                padding: 10px 20px;
                background-color: #326ABC;
                color: #fff;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
                text-align: center;
            }
            .btn:hover {
                background-color: #0056b3;
                color: white;
                text-decoration: none;
            }
            /* Footer */
            footer {
                background-color: #fff;
                text-align: center;
                padding: 15px 20px;
                border-top: 1px solid #ddd;
                margin-top: 20px;
            }
            /* Responsive cho màn hình nhỏ */
            @media (max-width: 768px) {
                .wrapper {
                    flex-direction: column;
                }
                .navbar-left, .jspIncludeSidebar {
                    width: 100%;
                }
                .main {
                    margin-left: 0;
                }
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />
            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">
                    <c:if test="${not empty errorMessage}">
                        <div style="color: red; text-align: center; margin-bottom: 10px;">
                            ${errorMessage}
                        </div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div style="color: green; text-align: center; margin-bottom: 10px;">
                            ${successMessage}
                        </div>
                        <script>
                            setTimeout(function () {
                                window.location.href = 'warrantyCardRepairContractor'; // Thay đổi đường dẫn trang đích tại đây
                            }, 3000);
                        </script>
                    </c:if>

                    <h2 style="text-align: center">Create Invoice for Repair Contractor</h2>
                    <form action="repairCreateInvoice" method="get" onsubmit="return validateAmount();">

                        <label for="amount">Amount:</label>
                        <input type="number" id="amount" name="amount" step="0.01" required min="0" /><br/><br/>

                        <label for="dueDate">Due Date:</label>
                        <input type="date" id="dueDate" name="dueDate" required min="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" />
                        <br/><br/>

                        <input type="hidden" name="invoiceType" value="RepairContractorToTechnician" />
                        <input type="hidden" name="status" value="pending" />

                        <input type="hidden" name="staffId" value="${param.staffId}" />
                        <input type="hidden" name="contractorCardID" value="${contractorCardID}" />
                        <input type="hidden" name="warrantyCardID" value="${warrantyCardID}" />

                        <button type="submit" class="btn btn-invoice">Create Invoice</button>

                        <button class="btn btn-invoice" type="button">
                            <a style="color: white; text-decoration: none" href="warrantyCardRepairContractor">
                                Back
                            </a>
                        </button>
                        <button type="button" class="btn" 
                                style="background-color: #dc3545; margin-left: 10px;"
                                onclick="window.history.back();">
                            Cancel
                        </button>
                    </form>

                </main>
                <jsp:include page="/includes/footer.jsp" />
            </div>
        </div>
        <script src="js/app.js"></script>
        <script>
                                    function validateAmount() {
                                        var amountInput = document.getElementById("amount");
                                        if (amountInput.value.trim() === "") {
                                            alert("Amount không được nhập toàn khoảng trắng.");
                                            amountInput.focus();
                                            return false;
                                        }
                                        return true;
                                    }
        </script>

    </body>
</html>
