<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<%@ include file="/resources/includes/header.html"%>
<%@ include file="/resources/includes/newPost.html"%>
<%@ include file="/resources/includes/newGroup.html"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/table.js"></script>
</head>
<body>
	<div class="content">
		<div class="page container">
			<div class="content-grids">
				<div class="col-md-12 content-main">
					<h4>${name}
					<c:if test="${name == 'GROUPS'}">
					<button class="btn btn-info btn-sm pull-right" style="height:34px;margin-left:5px"
					data-toggle="modal" data-target="#newGroup">Create New Group
					</button>
					</c:if>
					<div class="form-group pull-right">
						<input type="text" class="search form-control pull-right"
							placeholder="Search">
						<span class="counter pull-right"></span>
					</div>
					</h4>
					<table class="table table-hover table-bordered results">
						<thead>
							<tr>
								<th class="col-md-5 col-xs-5">Name</th>
								<th class="col-md-4 col-xs-4">Date Created</th>
							</tr>
							<tr class="warning no-result">
								<td colspan="4"><i class="fa fa-warning"></i> No result</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${items}">
								<tr>
									<td>
									<c:choose>
										<c:when test="${name == 'GROUPS'}">
								       		<a href="${pageContext.request.contextPath}/groups?id=${item.id}">${item.name}</a>
								       		<c:choose>
												<c:when test="${not item.joined}">
													<button id="jb_${item.id}" class="btn btn-info btn-sm pull-right" onclick="joinGroup('${item.id}')">
								       				Join Group</button>
												</c:when>
												<c:otherwise>
													<button disabled id="jb_${item.id}" class="btn btn-info btn-sm pull-right">
								       				Joined</button>
												</c:otherwise>
											</c:choose>
								    	</c:when>
										<c:otherwise>
						       				<a href="${pageContext.request.contextPath}/users?id=${item.id}">${item.name}</a>
						    			</c:otherwise>
									</c:choose>
									</td>
									<td>${item.timestamp}</td>  
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!---->
	<div class="footer">
		<div class="container">
			<p>
				CSS Template by <a href="http://w3layouts.com/">W3layouts</a>
			</p>
		</div>
	</div>
</body>