<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
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
<%@ include file="common/footer.jspf"%>