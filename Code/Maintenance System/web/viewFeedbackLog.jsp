<%-- 
    Document   : viewHistoryFeedback
    Created on : Jan 19, 2025, 10:37:07 AM
    Author     : Tra Pham
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedback Log</title>

        <link href="css/light.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="/includes/navbar-left.jsp" />

            <div class="main">
                <jsp:include page="/includes/navbar-top.jsp" />
                <main class="content">

                    <h1 class="text-center">History</h1>
                    <form class="" action="feedbacklog" method="get">
                        <input type="hidden" name="action" value="viewListFeedbackLog">        
                        <div class="row" style="justify-content: space-between">
                            <div class="col-md-6" style="width: 500px">
                                <input style="margin-top: 15px" class="form-control" type="search" name="feedbackID" placeholder="Feedback ID"  value="${feedbackID}" >
                                <div style="margin-top: 15px" class="col-sm-6 col-md-6">
                                    <label>Show 
                                        <select name="page-size" class="form-select form-select-sm d-inline-block" style="width: auto;" onchange="this.form.submit()">
                                            <c:forEach items="${pagination.listPageSize}" var="s">
                                                <option value="${s}" ${pagination.pageSize==s?"selected":""}>${s}</option>
                                            </c:forEach>
                                        </select> 
                                        entries
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-6" style="width: 500px">
                                <select style="margin-top: 15px" class="form-select" name="actionOfLog">
                                    <option value="">Action </option>
                                    <option ${(actionOfLog=='update')?"selected":""} value="update">Update</option>
                                    <option ${(actionOfLog=='delete')?"selected":""} value="delete">Delete</option>
                                </select>
                                <div style="float: right">
                                    <button class="btn btn-primary" style="margin-top: 15px" type="submit">Search</button>
                                </div>
                            </div>   
                        </div>
                    </form>
                    <c:set var="canUndoFeedback" value="false"/>

                    <c:forEach var="perm" items="${sessionScope.permissionIds}">
                        <c:if test="${perm == 51}"><c:set var="canUndoFeedback" value="true"/></c:if>
                    </c:forEach>

                   
                    <table class="table table-hover my-0">
                        <thead>
                            <tr>
                                <!--<th>Feedback Log ID</th>-->
                                <th>
                                    <form action="feedbacklog" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="FeedbackID" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'FeedbackID' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                        Feedback ID
                                        <button type="submit" class="btn-sort btn-primary btn">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'FeedbackID' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>

                                    </form>
                                </th>
                                <th>
                                    <form action="feedbacklog" method="get">
                                        <input type="hidden" name="page" value="${pagination.currentPage}" />
                                        <input type="hidden" name="page-size" value="${pagination.pageSize}" />
                                        <input type="hidden" name="sort" value="DateModified" />
                                        <input type="hidden" name="order" value="${pagination.sort eq 'DateModified' and pagination.order eq 'asc' ? 'desc' : 'asc'}" />
                                        <c:if test="${fn:length(pagination.searchFields) > 0}">
                                            <c:forEach var="i" begin="0" end="${fn:length(pagination.searchFields) - 1}">
                                                <input type="hidden" name="${pagination.searchFields[i]}" value="${pagination.searchValues[i]}">
                                            </c:forEach>
                                        </c:if>
                                        Date Modified
                                        <button type="submit" class="btn-sort btn-primary btn">
                                            <i class="align-middle fas fa-fw
                                               ${pagination.sort eq 'DateModified' ? (pagination.order eq 'asc' ? 'fa-sort-up' : 'fa-sort-down') : 'fa-sort'}">
                                            </i>
                                        </button>

                                    </form>
                                </th>
                                <th>Action</th>
                                <th>Old Feedback Text</th>
                                <th>New Feedback Text</th>
                                <!--<th>Modified By</th>-->

                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listFeedbackLog}" var="o">
                                <tr>
                                    <!--<td>${o.feedbackLogID}</td>-->
                                    <td>${o.feedbackID}</td>
                                    <td>${o.dateModified}</td>
                                    <td>${o.action}</td>
                                    <td>${o.oldFeedbackText}</td>
                                    <td>${o.newFeedbackText}</td>
                                    <!--<td>${o.modifiedBy}</td>-->

                                    <c:if test="${o.action=='delete'}">
                                      
                                    <c:if test="${canUndoFeedback}">
                                         <td><a class="btn btn-primary" href="feedbacklog?feedbackLogID=${o.feedbackLogID}&action=undoFeedback">Undo</a></td>
                                    </c:if>
                                    </c:if>
                                </tr>  
                            </c:forEach>
                        </tbody>
                    </table>
                    <jsp:include page="/includes/pagination.jsp" />
                    <a href="feedback?action=viewFeedback">Back</a>
                </main>
                <jsp:include page="/includes/footer.jsp" />

            </div>

        </div>
        <script src="js/app.js"></script>
    </body>
</html>
