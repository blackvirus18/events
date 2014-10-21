<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery-1.11.0.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.js"></script>
<script src="../js/app.js"></script>
</head>
<body>
	<div ng-app="instantSearch" ng-controller="InstantSearchController">
		<div class="abPanel">
			<h2>List Contacts</h2>
			<table id='contactsGrid' width='100%' border='1' cellspacing='1'>
				<tr id="mainrow">
					<td id="club_name" sort-button><b>Club Name</b><span>+</span></td>
					<td id="event_link" sort-button><b>Event Link</b><span>+</span></td>
					<td id="photo" sort-button><b>Photo</b><span>+</span></td>
					<td id="name" sort-button><b>Event Name</b><span>+</span></td>
					<td id="description" sort-button><b>Event Description</b><span>+</span></td>
					<td id="start_date" sort-button><b>Start Date</b><span>+</span></td>
					<td id="valid_till" sort-button><b>Valid Till</b><span>+</span></td>
					<td id="is_valid" sort-button><b>Is Valid??</b><span>+</span></td>
				</tr>
				<tr	ng-repeat="i in items | orderBy:sortorder">
					<td id="club_name" sort-button><b>{{i.name}}</b><span>+</span></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>