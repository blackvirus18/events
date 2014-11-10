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
<style type="text/css">
	input{
		height: 100px;
	}
	td{
		width: 12.5%;
		word-wrap: break-word;
	}
	table{
		table-layout: fixed;
		width: 300%;
	}
	img{
		width: 100%;
	}
</style>
</head>
<body>
	<div ng-app="instantSearch" ng-controller="InstantSearchController" ng-init="getitems()">
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
				<table id='contactsGrid' width='100%' border='1' cellspacing='1' ng-repeat="i in items">
					<tr	ng-repeat="j in i.events | orderBy:sortorder">
					<td class="club_name" sort-button>{{i.name}}</td>
					<td class="club_link" sort-button><a href="{{j.link}}">{{j.link}}</a></td>
					<td class="club_imageSrc" sort-button><img src="{{j.imageSrc}}"></td>
					<td class="event_name" sort-button><textarea rows="10" cols="30">{{j.name}}</textarea> </td>
					<td class="event_description" sort-button> <textarea rows="10" cols="30">{{j.description}}</textarea></td>
					<td class="start_date" sort-button><textarea rows="10" cols="30">{{j.startDate}}</textarea></td>
					<td class="valid_till" sort-button><textarea rows="10" cols="30">{{j.valid}}</textarea></td>
					<td  sort-button><input id="{{j.id}}" type="checkbox" name="isValid" value="true" ></td>
				</tr>
				</table
			</table>
		</div>
		<input type="submit" value="Approve Events" ng-click="approveEvents()"/>
	</div>
</body>
</html>