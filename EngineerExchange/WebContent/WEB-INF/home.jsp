<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<%@ include file="/resources/includes/header.html"%>
<%@ include file="/resources/includes/newPost.html"%>
<%@ include file="/resources/includes/reads.html"%>
<%@ include file="/resources/includes/groups.html"%>
</head>
<body>
	<div class="content">
		<div class="page container">
			<div class="content-grids">
				<div class="col-md-6 content-left">
					<h4>MY BLOG</h4>
					<div class="content-grid">
						<c:if test="${empty user.posts}">
							<p>No posts to show</p>
						</c:if>
						<c:forEach var="post" items="${user.posts}">
							<div class="content-grid-info">
								<div class="post-info">
									<h4>
										<a>${post.postTitle}</a> ${post.timestamp}
									</h4>
									<h5><a href="javascript:;" onclick="viewReads('${post.id}')">Read by ${post.numReads} 
									<c:choose>
										<c:when test="${post.numReads == 1}">
						       				person
						    			</c:when>
										<c:otherwise>
						       				people
						    			</c:otherwise>
									</c:choose>
									</a>
									</h5>
									</br>
									<p>${post.postText}</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="col-md-6 content-right">
					<h4>MY FEED</h4>
					<h5>
						<a href="javascript:;" onclick="viewGroups()">Subscribed
							Groups (${user.numGroups})</a>
					</h5>
					<div class="content-grid">
						<c:if test="${empty user.feed}">
							<p>No posts to show</p>
						</c:if>
						<c:forEach var="item" items="${user.feed}">
							<div class="content-grid-info">
								<div class="post-info">
									<h4>
										<a>${item.postTitle}</a> ${item.timestamp}
									</h4>
									<c:choose>
										<c:when test="${item.ownPost}">
											<h5>Posted by <a href="${pageContext.request.contextPath}/home">Me</a> to 
											<c:choose>
												<c:when test="${item.postScope == 3}">
													All Users
												</c:when>
												<c:when test="${item.postScope == 2}">
													${item.groupName}
												</c:when>
											</c:choose> - 
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
											<h5>Posted by <a href="${pageContext.request.contextPath}/users?id=${item.postedBy.id}">${item.postedBy.name}</a>
											to 
											<c:choose>
												<c:when test="${item.postScope == 3}">
													All Users
												</c:when>
												<c:when test="${item.postScope == 2}">
													${item.groupName}
												</c:when>
											</c:choose>
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