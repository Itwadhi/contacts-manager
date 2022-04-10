<@includes>

<div class="row">
	<div class="col-sm-12">
	
		<h1>All Contacts</h1>

		<form class="form-inline" action="<@spring.url '/' />">
		  <div class="form-group">
		    <input type="text" class="form-control" name="q" placeholder="Name/Phone/Email">
		  </div>
		  <button type="submit" class="btn btn-default">Search</button>
		</form>		
	
		<hr>
		
		<#if RequestParameters.q?exists >
		<div class="alert alert-default">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<strong>Search results for: </strong> ${RequestParameters.q}
			</div>
		</#if>			
		
		
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>Full Name</th>
					<th>Phone Number</th>
					<th>Email</th>
					<th>Date of Birth</th>
					<th>Profile Image</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<#list contacts as contact>
					<tr>
						<td>${contact.fullName}</td>
						<td>${contact.phoneNumber}</td>
						<td>${contact.email}</td>
						<td>${contact.dateOfBirth?date}</td>
						<td>
							<img src="<@spring.url '/attachments/${contact.profileImage}' />" width="100" height="100" class="img-responsive" alt="Profile Image">
						</td>
						<td>
							<a href="<@spring.url '/contacts/${contact.id}/edit' />">Edit</a>
							<a href="<@spring.url '/contacts/${contact.id}/delete' />" class="text-danger">Delete</a>
						</td>
					</tr>
				</#list>
			</tbody>
		</table>
	</div>
</div>

</@includes>