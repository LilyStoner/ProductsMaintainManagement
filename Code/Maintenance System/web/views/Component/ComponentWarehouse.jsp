<%-- 
    Document   : ComponentWarehouse
    Created on : Jan 17, 2025, 8:24:34 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Responsive Admin &amp; Dashboard Template based on Bootstrap 5">
        <meta name="author" content="AdminKit">
        <meta name="keywords" content="adminkit, bootstrap, bootstrap 5, admin, dashboard, template, responsive, css, sass, html, theme, front-end, ui kit, web">
        <base href="${pageContext.request.contextPath}/">

        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link rel="shortcut icon" href="img/icons/icon-48x48.png" />

        <link rel="canonical" href="https://demo-basic.adminkit.io/" />

        <title>Component Warehouse</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <style>
            .btn-pagination {
                width: 40px;
                height: 40px;
                display: flex;
                align-items: center;
                justify-content: center;
                text-align: center;
            }
            .btn-sort {
                background: none;
                border: none;
                padding: 0;
                cursor: pointer;
            }

        </style>
    </head>

    <body>
        <div class="wrapper">
            <jsp:include page="../../includes/navbar-left.jsp" />

            <div class="main">

                <jsp:include page="../../includes/navbar-top.jsp" />
                <main class="content">
                    <form action="Redirect" enctype="multipart/form-data">
                        <input type="hidden" name="target" value="${empty componentWarehouseFrom ? 'Home' : componentWarehouseFrom}">
                        <button type="submit" class="btn btn-primary d-flex align-items-center justify-content-center" style="transform:translate(-30%,-60%); height: 2.5rem; width: 5.2rem">
                            <i class="fas fa-arrow-left fa-4"></i> <span class="ms-2">Back</span>
                        </button>
                    </form>

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h2>Component Warehouse</h2>
                        <c:if test="${staff.hasPermissions('VIEW_SUPPLEMENT_REQUEST')}">
                            <a href="supplementRequest?action=listSupplementRequest" class="btn btn-secondary">Supplement List </a>
                        </c:if>
                    </div>               



                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div>
                            <c:if test="${staff.hasPermissions('ADD_NEW_COMPONENT')}">
                                <form action="ComponentWarehouse/Add" method="POST" enctype="multipart/form-data" style="display: inline;">
                                    <button type="submit" class="btn btn-success"><i class="fas fa-add"></i> Add Component</button>
                                </form>                  
                            </c:if>
                            <c:if test="${staff.hasPermissions('MANAGE_BRAND')}">
                                <form action="Brand" method="POST" enctype="multipart/form-data" style="display: inline;">
                                    <button type="submit" class="btn btn-primary"><i class="fas fa-add"></i> Brand</button>
                                </form>
                            </c:if>
                            <c:if test="${staff.hasPermissions('MANAGE_COMPONENT_TYPE')}">
                                <form action="ComponentType" method="POST" enctype="multipart/form-data" style="display: inline;">
                                    <button type="submit" class="btn btn-primary"><i class="fas fa-add"></i> Component Type</button>
                                </form> 
                            </c:if>
                            <c:if test="${staff.hasPermissions('VIEW_PRODUCT_TYPE')}">
                                <form action="ProductType" method="POST" enctype="multipart/form-data" style="display: inline;">
                                    <button type="submit" class="btn btn-primary"><i class="fas fa-add"></i> Product Type</button>
                                </form>   
                            </c:if>
                        </div>
                        <c:if test="${staff.hasPermissions('SEARCH_ADVANCE_COMPONENT')}">
                            <form action="ComponentWarehouse/Search" method="get" style="display: inline;">
                                <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Advanced Search</button>
                            </form>
                        </c:if>
                    </div>
                    <form action="ComponentWarehouse" method="get" >
                        <div class="row align-items-center">
                            <input type="hidden" name="page" value="${pagination.currentPage}">
                            <input type="hidden" name="sort" value="${pagination.sort}">
                            <input type="hidden" name="order" value="${pagination.order}">
                            <div class="col-sm-6 col-md-6">
                                <label>Show 
                                    <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                        <c:forEach items="${pagination.listPageSize}" var="s">
                                            <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                        </c:forEach>
                                    </select> 
                                    entries
                                </label>
                            </div>
                            <div class="col-sm-6 col-md-6 text-end ">
                                <div class="col-md-3 input-group d-flex justify-content-end">
                                    <input type="search" style="flex: 0.5 1 auto" name="search" class="form-control form-control-md" placeholder="Search" value="${pagination.searchValues[0]}" aria-controls="datatables-column-search-text-inputs">
                                    <button type="submit" class="btn btn-primary">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-search align-middle"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
                                    </button>
                                    </form>
                                    <c:if test="${staff.hasPermissions('EXPORT_COMPONENTS')}">
                                    <form action="ExportComponents" method="get" class="ms-1">
                                        <button type="submit" class="btn btn-primary"><i class="fas fa-print"></i></button>
                                    </form>
                                     </c:if>
                                    
                                     <c:if test="${staff.hasPermissions('IMPORT_COMPONENTS')}">
                                    <form action="ImportComponents" class="ms-1" id="importForm" method="post" enctype="multipart/form-data" >
                                        <input type="file" name="file" id="fileInput" style="display: none;" required>
                                        <button type="button" class="btn btn-primary" id="uploadBtn">
                                            <i class="fas fa-file-import"></i>
                                        </button>                                
                                    </form>
                                     </c:if>
                                </div>
                            </div>
                        </div>
                        <c:if test="${not empty errorMessages}">
                            <div class="alert alert-warning alert-dismissible mt-1" role="alert">
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                <div class="alert-message">
                                    <strong>${errorMessages}</strong>
                                </div>
                                <%
                                    session.removeAttribute("errorMessages");
                                %>
                            </div>
                        </c:if>
                        <c:if test="${not empty alertImportSuccess}">
                            <div class="alert alert-success alert-dismissible mt-1" role="alert">
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                <div class="alert-message">
                                    <strong>${alertImportSuccess}</strong>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${not empty alertImportFail}">
                            <div class="alert alert-warning alert-dismissible mt-1" role="alert">
                                <form action="ComponentWarehouse" method="post" class="d-inline">
                                    <input type="hidden" name="page" value="${pagination.currentPage}" />
                                    <input type="hidden" name="page-size" value="${pagination.pageSize}">
                                    <c:if test="${fn:length(pagination.searchFields) > 0}">

                                        <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                            <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${fn:length(pagination.rangeFields) > 0}">
                                        <c:forEach var="i" begin="0" end="${fn:length(pagination.rangeFields) - 1}">
                                            <input type="hidden" name="${pagination.rangeFields[i]}" value="${pagination.rangeValues[i]}">
                                        </c:forEach>
                                    </c:if>

                                    <input type="hidden" name="sort" value="${pagination.sort}">
                                    <input type="hidden" name="order" value="${pagination.order}">
                                    <input type="hidden" name="action" value="clearErrorComponents">
                                    <button type="submit" class="btn-close" aria-label="Close"></button>
                                </form>
                                <form action="ExportComponents" method="get" class="ms-2 d-flex justify-content-center">
                                    <input type="hidden" name="type" value="error">
                                    <button type="submit" class="btn btn-warning"><i class="fas fa-print"></i></button>
                                </form>
                                <div class="alert-message">
                                    <strong>${alertImportFail}</strong>
                                </div>
                            </div>
                        </c:if>
                        <table class="table table-hover my-0">
                            <thead>
                                <tr>
                                    <th style="width:3%">#</th>
                                    <th style="width:13%">
                                        <form action="ComponentWarehouse" method="get">
                                            <input type="hidden" name="page" value="${pagination.currentPage}" />
                                            <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                            <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                            <input type="hidden" name="sort" value="code" />
                                            <input type="hidden" name="order" value="${pagination.sort eq 'code' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                            <button type="submit" class="btn-sort">
                                                <i class="align-middle fas fa-fw
                                                   ${pagination.sort eq 'code' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                                </i>
                                            </button>
                                            Code
                                        </form>
                                    </th>
                                    <th>
                                        Type
                                    </th>
                                    <th>
                                        Brand
                                    </th>

                                    <th style="width:30%">
                                        <form action="ComponentWarehouse" method="get">
                                            <input type="hidden" name="page" value="${pagination.currentPage}" />                                     
                                            <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                            <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                            <input type="hidden" name="sort" value="name" />
                                            <input type="hidden" name="order" value="${pagination.sort eq 'name' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                            <button type="submit" class="btn-sort">
                                                <i class="align-middle fas fa-fw
                                                   ${pagination.sort eq 'name' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                                </i>
                                            </button>
                                            Name
                                        </form>
                                    </th>

                                    <th style="width:15%">
                                        <form action="ComponentWarehouse" method="get">
                                            <input type="hidden" name="page" value="${pagination.currentPage}" />                                    
                                            <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                            <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                            <input type="hidden" name="sort" value="quantity" />
                                            <input type="hidden" name="order" value="${pagination.sort eq 'quantity' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                            <button type="submit" class="btn-sort">
                                                <i class="align-middle fas fa-fw
                                                   ${pagination.sort eq 'quantity' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                                </i>
                                            </button>
                                            Quantity
                                        </form>
                                    </th>

                                    <th style="width:15%">
                                        <form action="ComponentWarehouse" method="get">
                                            <input type="hidden" name="page" value="${pagination.currentPage}" />                                      
                                            <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                            <input type="hidden" name="search" value="${pagination.searchValues[0]}" />
                                            <input type="hidden" name="sort" value="price" />
                                            <input type="hidden" name="order" value="${pagination.sort eq 'price' and order eq 'asc' ? 'desc' : 'asc'}" />
                                            <button type="submit" class="btn-sort">
                                                <i class="align-middle fas fa-fw
                                                   ${pagination.sort eq 'price' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                                </i>
                                            </button>
                                            Unit Price
                                        </form>
                                    </th>

                                    <th style="width:8%">Action<a href="ComponentWarehouse?page=${pagination.currentPage}&page-size=${pagination.pageSize}"><i class="fa fa-refresh ms-2"></i></a></th>
                                </tr>
                            </thead>
                            <tbody>
                                <!--                        varStatus để lấy trạng thái của vòng lặp-->
                                <c:forEach var="component" items="${components}" varStatus="status">
                                    <tr class="${status.index % 2 == 0 ? 'table-primary' : ''}">
                                        <td>${status.index + 1 + (pagination.currentPage - 1) * pagination.pageSize}</td>
                                        <td>${component.componentCode}</td>
                                        <td>${component.type}</td>
                                        <td>${component.brand}</td>
                                        <td>${component.componentName}</td>
                                        <td>${component.quantity}</td>
                                        <td>${component.price}</td>
                                        <td class="table-action">
                                            <a href="ComponentWarehouse/Detail?ID=${component.componentID}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-edit-2 align-middle">
                                                <path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path>
                                                </svg>
                                            </a>
                                            <c:if test="${staff.hasPermissions('DELETE_COMPONENT')}">

                                                <a data-bs-toggle="modal" data-bs-target="#centeredModalPrimary_${component.componentID}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash align-middle">
                                                    <polyline points="3 6 5 6 21 6"></polyline>
                                                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                                                    </svg>
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>

                                    <!-- Modal for each component -->
                                <div class="modal fade" id="centeredModalPrimary_${component.componentID}" tabindex="-1" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Delete Confirmation</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body m-3">
                                                <p class="mb-0">Confirm your action. Really want to delete?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <a href="ComponentWarehouse/Delete?ID=${component.componentID}&page=${pagination.currentPage}&page-size=${pagination.pageSize}&search=${pagination.searchValues[0]}&sort=${pagination.sort}&order=${pagination.order}" class="btn btn-primary">Delete</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>


                            </tbody>
                        </table>
                        <c:if test="${totalComponents==0}">
                            <div class="alert alert-primary alert-dismissible" role="alert">
                                <div class="alert-message text-center">
                                    <strong style="font-size:1.6rem">No suitable card in the filter</strong>
                                </div>
                            </div>

                        </c:if>
                        <jsp:include page="../../includes/pagination.jsp" />

                        <c:if test="${not empty deleteStatus}">
                            <div class="alert alert-warning alert-dismissible" role="alert">
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                <div class="alert-message">
                                    <strong>${deleteStatus}</strong>
                                </div>
                            </div>
                        </c:if>
                        </div>

                </main>
                <jsp:include page="../../includes/footer.jsp" />
            </div>

        </div>
        <script>
            document.getElementById("uploadBtn").addEventListener("click", function () {
                document.getElementById("fileInput").click(); // Khi bấm nút, mở hộp chọn file
            });

            document.getElementById("fileInput").addEventListener("change", function () {
                if (this.files.length > 0) {
                    document.getElementById("importForm").submit(); // Khi chọn file xong, tự động submit
                }
            });
        </script>
        <script src="js/app.js"></script>
        <script src="js/format-input.js"></script>

    </body>

</html>