$(document).ready(function() {
    $('input[type=radio][name=scope]').change(function() {
        if (this.value == 2) {
        	$.get(myContextPath + '/users?param=myGroups', function(json){
        		for (var i = 0; i < json.length; i++) {
        			$('#groupSelect').append("<option value=" + json[i].id +">"+json[i].name+"</option>");
        		}
        		$('#groupSelectDiv').modal('show');
        	}).fail(function(){
        		alert('An error occured.');
        	});
        }
        else {
        	$('#groupSelectDiv').modal('hide');
        }
    });
    
   // $('#newPostText').markdownEditor();
});


function saveNewPost() {
	var newPost = $('#newPostForm');
	$.ajax({
		type: 'POST',
		url: myContextPath + '/post',
		data: newPost.serialize(),
		success: function() {
			$('#newPost').hide();
			window.location.reload();
		},
		error: function() {
			alert('An error occured :(')
		}
	});
	return false;
};

function saveNewGroup() {
	var newGroup = $('#newGroupForm');
	$.ajax({
		type: 'POST',
		url: myContextPath + '/groups?param=add',
		data: newGroup.serialize(),
		success: function() {
			$('#newGroup').hide();
			window.location.reload();
		},
		error: function() {
			alert('An error occured :(')
		}
	});
	return false;
};


function markRead(postID) {
	$.ajax({
		type: 'POST',
		url: myContextPath + '/post?param=read&postID=' + postID,
		success: function() {
			$('#rb_' + postID).attr('disabled','disabled');
			$('#rb_' + postID).html('Read');
		},
		error: function() {
			alert('An error occured :(')
		}
	});
	return false;
};


function joinGroup(groupID) {
	$.ajax({
		type: 'POST',
		url: myContextPath + '/groups?param=join&groupID=' + groupID,
		success: function() {
			$('#jb_' + groupID).attr('disabled','disabled');
			$('#jb_' + groupID).html('Joined');
		},
		error: function() {
			alert('An error occured :(')
		}
	});
	return false;
};


function viewReads(id) {
	$('#readsList').empty();
	$.get(myContextPath + '/post?postID=' + id, function(json){
		for (var i = 0; i < json.length; i++) {
			$('#readsList').append("<li><a href=" + myContextPath + "/users?id=" + json[i].id + ">" + json[i].name + "</a><h6>"+json[i].timestamp+"<h6></li></br>");
		}
		$('#reads').modal('show');
	}).fail(function(){
		alert('An error occured.');
	});
	
	return;
};

function viewGroups() {
	$('#groupsList').empty();
	$.get(myContextPath + '/users?param=myGroups', function(json){
		
		for (var i = 0; i < json.length; i++) {
			$('#groupsList').append("<li><a href=" + myContextPath + "/groups?id=" + json[i].id + ">" + json[i].name + "</a><h6>Joined "+json[i].timestamp+"<h6></li></br>");
		}
		$('#viewGroups').modal('show');

	}).fail(function(){
		alert('An error occured.');
	});
	
	return;
};

function viewMembers(id) {
	$('#membersList').empty();
	$.get(myContextPath + '/groups?param=getMembers&id=' + id, function(data){
		console.log(data);
		for (var i = 0; i < data.length; i++) {
			$('#membersList').append("<li><a href=" + myContextPath + "/users?id=" + data[i].id + ">" + data[i].name + "</a><h6>Joined "+data[i].timestamp+"<h6></li></br>");
		}
		$('#viewMembers').modal('show');
	}).fail(function(){
		alert('An error occured.');
	});
	
	return;
};
