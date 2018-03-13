<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<%@ include file="/resources/includes/header.html"%>
<%@ include file="/resources/includes/newPost.html"%>
<%@ include file="/resources/includes/reads.html"%>
<%@ include file="/resources/includes/members.html"%>
</head>
<body>
	<div class="content">
		<div class="page container">
			<div class="content-grids">
				<div class="col-md-12 content-main">
					<h4>${name}'s POSTS</h4>
					<c:if test="${isGroup}">
						<h5>
							<a href="javascript:;" onclick="viewMembers(${id})">Members
								(${numMembers})</a>
						</h5>
					</c:if>
					<div class="content-grid">
						<c:if test="${empty posts}">
							<p>No posts to show</p>
						</c:if>
						<c:forEach var="item" items="${posts}">
							<div class="content-grid-info">
								<div class="post-info">
									<h4>
										<a>${item.postTitle}</a> ${item.timestamp}
									</h4>
									<c:choose>
										<c:when test="${item.ownPost}">
											<h5>Posted by <a href="${pageContext.request.contextPath}/home">Me</a> - 
											<a href="javascript:;" onclick="viewReads('${item.id}')">Read by ${item.numReads}
											<c:choose>
												<c:when test="${item.numReads == 1}">
								       			person
								    			</c:when>
												<c:otherwise>
								       			people
								    			</c:otherwise>
											</c:choose>
											</a>
											</h5>
											</br>
										</c:when>
										<c:otherwise>
											<h5>
												Posted by <a href="${pageContext.request.contextPath}/users?id=${item.postedBy.id}">${item.postedBy.name}</a>
											</h5>
											</br>
										</c:otherwise>
									</c:choose>
									<p>${item.postText}</p>
									<c:if test="${not item.ownPost}">
										<c:choose>
											<c:when test="${not item.read}">
												<button class="btn btn-small btn-info" id="rb_${item.id}"
													onclick="markRead('${item.id}')">Mark Read</button>
											</c:when>
											<c:otherwise>
												<button disabled class="btn btn-small btn-info"
													id="rb_${item.id}">Read</button>
											</c:otherwise>
										</c:choose>
									</c:if>
								</div>
							</div>
						</c:forEach>
					</div>
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