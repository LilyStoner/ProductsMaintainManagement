<%-- 
    Document   : ComponentWarehouse
    Created on : Jan 17, 2025, 8:24:34 PM
    Author     : ADMIN
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="coverDAO" class="DAO.HomePage_CoverDAO" scope="page" />
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="shortcut icon" href="img/icons/home-48x48.png" />


        <style>
            .logo-img {
                height: 50px;
                width: auto;
            }
        </style>

    </head>

    <body class="d-flex flex-column">
        <div class="wrapper">
            <div class="main">

                <nav class="navbar navbar-expand navbar-light navbar-bg fixed-top ">
                    <a href="Home" class="navbar-brand">
                        <img src="${pageContext.request.contextPath}/img/logo/logoText.png" alt="Main10 Logo" class="logo-img">
                    </a>

                    <ul class="navbar-nav d-none d-lg-flex"> <!-- start ul 1 -->
                        <li class="nav-item">
                            <a class="nav-link d-flex align-items-center" href="Home">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-home align-middle me-1">
                                <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                                <polyline points="9 22 9 12 15 12 15 22"></polyline>
                                </svg>
                                Home
                            </a>
                        </li>
                        <!--                        <li class="nav-item ms-1">
                                                        <a class="nav-link d-flex align-items-center" href="#"  >
                                                           <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-award align-middle">
                                                           <circle cx="12" cy="8" r="7"></circle>
                                                           <polyline points="8.21 13.89 7 23 12 20 17 23 15.79 13.88"></polyline>
                                                           </svg>
                                                        About
                                                    </a>
                                                </li>-->
                        <li class="nav-item ms-1">
                            <a class="nav-link d-flex align-items-center" href="BlogController" >
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-book align-middle">
                                <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
                                <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
                                </svg>
                                Blog
                            </a>
                        </li>
                        <!--                        <li class="nav-item dropdown">
                                                    <a class="nav-link dropdown-toggle" href="#" id="megaDropdown" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        Look Up Online
                                                    </a>
                                                    <div class="dropdown-menu dropdown-menu-start dropdown-mega" aria-labelledby="megaDropdown">
                                                        <div class="d-md-flex align-items-start justify-content-start">
                                                            <div class="dropdown-mega-list">
                                                                <a class="dropdown-item" href="LookUpOnline.jsp">Warranty Card</a>
                                                                <a class="dropdown-item" href="#">Product</a>
                                                            </div>
                        
                                                        </div>
                                                    </div>
                                                </li>-->
                    </ul> <!-- end ul 1 -->
                    <ul class="navbar-nav navbar-align"><!-- start ul2 -->
                        <div class="navbar-collapse collapse">
                            <li class="navbar-nav navbar-align m-xl-2">
                                <c:if test="${not empty sessionScope.staff or not empty sessionScope.customer}">
                                    <a class="btn btn-primary btn-sm" href="dashBoard">Back to dashboard</a>
                                </c:if>
                            </li>

                            <li class="navbar-nav navbar-align">
                                <c:if test="${empty sessionScope.staff and empty sessionScope.customer}">
                                    <a class="btn btn-primary btn-sm"  href="LoginForm.jsp">Login</a>
                                </c:if>
                            </li>

                            <li class="navbar-nav navbar-align">
                                <c:if test="${not empty sessionScope.staff or not empty sessionScope.customer}">
                                    <a class="btn btn-primary btn-sm"  href="logout">Logout</a>
                                </c:if>
                            </li>
                        </div>
                    </ul><!-- end ul2 -->
                </nav>
                <c:set var="backgroundImage" value="${coverDAO.getBackgroundImage()}" />
                <main style="background-image: url('${pageContext.request.contextPath}${backgroundImage}'); background-size: cover; background-position: center; background-attachment: fixed;">
                    <div style="height: 95vh" class="bg-primary bg-opacity-50  rounded shadow-lg p-5">
                        <div class="container  ">
                            <div>
                                <div style="padding: 15% 1% 1% 1%" class="text-center">
                                    <h1 style="font-size: 60px; color: white;
                                        text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.25),
                                        2px -2px 0 rgba(0, 0, 0, 0.25),
                                        -2px 2px 0 rgba(0, 0, 0, 0.25),
                                        2px 2px 0 rgba(0, 0, 0, 0.25);"  
                                        class="fw-bolder" >
                                        Warranty information lookup
                                    </h1>
                                    <h6 style="font-size: 20px; color: white;
                                        text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.25),
                                        2px -2px 0 rgba(0, 0, 0, 0.25),
                                        -2px 2px 0 rgba(0, 0, 0, 0.25),
                                        2px 2px 0 rgba(0, 0, 0, 0.25);"
                                        class="fw-bold" >Fill in your information to view detailed warranty information for your product
                                    </h6>
                                </div>
                                <div>

                                    <form action="SearchWarrantyController" method="get" > <!-- form here -->

                                        <div style="height: 55px;" class="input-group mx-auto w-50">
                                            <div class="position-absolute bg-secondary opacity-25 rounded-3"
                                                 style="top: -10px; left: -10px; right: -10px; bottom: -10px; z-index: 0;">
                                            </div>
                                            <input type="search" name="searchValue" class="form-control" placeholder="Information code">
                                            <div class="input-group-append">
                                                <button style="height: 55px" class="btn btn-primary" type="submit">Search</button>
                                            </div>
                                        </div>
                                        <div class="mx-auto text-center">
                                            <div style="margin-top: 15px" >
                                                <label class="form-check form-check-inline">
                                                    <input  class="form-check-input" type="radio" name="searchType" value="phone">
                                                    <span style=" color: white;
                                                          text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.15),
                                                          2px -2px 0 rgba(0, 0, 0, 0.15),
                                                          -2px 2px 0 rgba(0, 0, 0, 0.15),
                                                          2px 2px 0 rgba(0, 0, 0, 0.15);" class="form-check-label fs-6">
                                                        Search by phone number
                                                    </span>
                                                </label>
                                                <label class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="searchType" value="email">
                                                    <span style=" color: white;
                                                          text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.15),
                                                          2px -2px 0 rgba(0, 0, 0, 0.15),
                                                          -2px 2px 0 rgba(0, 0, 0, 0.15),
                                                          2px 2px 0 rgba(0, 0, 0, 0.15);" class="form-check-label fs-6">
                                                        Search by email
                                                    </span>
                                                </label>
                                                <label class="form-check form-check-inline">
                                                    <input class="form-check-input" type="radio" name="searchType" value="productCode" checked="">
                                                    <span style=" color: white;
                                                          text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.15),
                                                          2px -2px 0 rgba(0, 0, 0, 0.15),
                                                          -2px 2px 0 rgba(0, 0, 0, 0.15),
                                                          2px 2px 0 rgba(0, 0, 0, 0.15);" class="form-check-label fs-6">
                                                        Search by product code
                                                    </span>
                                                </label>
                                            </div>
                                        </div>

                                    </form> <!-- end form -->
                                </div>

                            </div>
                        </div>
                    </div> <!-- end div 1 -->
                    <c:set var="heightStyle" value="200vh" />
                    <c:if test="${fn:length(serviceItems) <= 4}">
                        <c:set var="heightStyle" value="100vh" />
                    </c:if>
                    <c:if test="${fn:length(serviceItems) > 4 and fn:length(serviceItems) <= 8}">
                        <c:set var="heightStyle" value="150vh" />
                    </c:if>
                    <c:if test="${fn:length(serviceItems) == 0}">
                        <c:set var="heightStyle" value="0vh" />
                    </c:if>


                    <div style="height: ${heightStyle}" class="bg-white">
                        <div style="padding-bottom: 7%; padding-top: 5% " class="container">
                            <div style="margin-bottom: 2%; margin-top: 1%;" class="row">
                                <div class="col-6">
                                    <div class="d-flex align-items-center">
                                        <h1 class="text-primary" style="font-size: 40px; margin-right: 5px;">I</h1>
                                        <h1 style="font-size: 30px" class="fw-bold"> ${section.title}</h1>
                                    </div>
                                    <h5>${section.subTitle}</h5>
                                </div>
                            </div>
                            <div class="row">
                                <c:forEach var="item" items="${serviceItems}">
                                    <div style="margin-bottom:2% " class="col-12 col-md-6 col-lg-3">
                                        <div class="card h-100">
                                            <img class="card-img-top" src="${pageContext.request.contextPath}${item.imageURL}" alt="${item.title}" style=" height: 150px; width: 276px; object-fit: cover;">
                                            <div class="card-header px-4 pt-4">
                                                <h5 class="card-title mb-0">${item.title}</h5>
                                            </div>
                                            <div class="card-body px-4 pt-2">
                                                <p>${item.description}</p>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>  
                        </div>
                    </div><!-- end div 2 -->

                    <div style="height: 50vh" class="bg-primary bg-opacity-50 d-flex justify-content-center align-items-center " >
                        <div class="container text-center d-flex align-items-center justify-content-center">
                            <div class="text-center">
                                <div style="padding: 1% 1% 1% 1%" class="text-center">
                                    <h1 style="font-size: 60px; color: white;
                                        text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.25),
                                        2px -2px 0 rgba(0, 0, 0, 0.25),
                                        -2px 2px 0 rgba(0, 0, 0, 0.25),
                                        2px 2px 0 rgba(0, 0, 0, 0.25);"  
                                        class="fw-bolder" >
                                        ${contactText.title}
                                    </h1>
                                    <h6 style="font-size: 20px; color: white;
                                        text-shadow: -2px -2px 0 rgba(0, 0, 0, 0.25),
                                        2px -2px 0 rgba(0, 0, 0, 0.25),
                                        -2px 2px 0 rgba(0, 0, 0, 0.25),
                                        2px 2px 0 rgba(0, 0, 0, 0.25);"
                                        class="fw-bold" >${contactText.subtitle}
                                    </h6>
                                </div>
                                <div>
                                    <a style="height: 50px; justify-content: center; align-content: center" class="btn btn-primary " href="customerContactForm.jsp">Contact Support</a>
                                </div>
                            </div>
                        </div>
                    </div> <!-- end div 3 -->

                    <!--                   <div style="height: 50vh" class="bg-white">
                                          
                                        </div> end div 4 -->
                </main>
                <footer class="footer bg-primary text-white text-center py-4 mt-auto">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12 text-center">
                                <p class="mb-1">
                                    <strong>Main10</strong> - ${footer.slogan}
                                </p>
                                <p class="mb-1">Address: ${footer.address}</p>
                                <p class="mb-1">Hotline: ${footer.hotline} | Email: ${footer.email}</p>
                                <p class="mb-0">© ${footer.copyrightYear} Main10. All rights reserved.</p>
                            </div>
                        </div>
                    </div>
                </footer>


            </div>
        </div>

        <script src="js/app.js"></script>
        <jsp:include page="/chatBox.jsp"/>
    </body>

</html>
