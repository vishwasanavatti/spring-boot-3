<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<head>
		<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet"></link>
		<title>Todos</title>	
	</head>
	<body>
		<div class="container">
			<h2>Your Todos</h2>
			<table class="table">
				<thead>
					<th>id</th>
					<th>Description</th>
					<th>Target Date</th>
					<th>Is Complete?</th>
					<th></th>
					<th></th>
				</thead>
				<tbody>
				<c:forEach items="${todos}" var="todo">
					<tr>
						<td>${todo.id}</td>
						<td>${todo.description}</td>
						<td>${todo.targetDate}</td>
						<td>${todo.isComplete}</td>
						<td><a href="update-todo?id=${todo.id}" class="btn btn-success">Update</a></td>
						<td><a href="delete-todo?id=${todo.id}" class="btn btn-warning">Delete</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<a href="add-todo" class="btn btn-success">Add Todo</a>
		</div>
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
		<script src="webjars/jquery/3.6.0/jquery.min.js"></script>
	</body>
</html>