<@includes>

<div class="row">
	<div class="col-sm-6">
		<h1>Edit Contact</h1>
		<form action="<@spring.url '/contacts/${contact.id}' />" method="POST" enctype="multipart/form-data" class="form-horizontal" role="form">
				
				  <#include "/contacts/_form.ftl">
		
				<div class="form-group">
					<div class="col-sm-10 col-sm-offset-2">
						<button type="submit" class="btn btn-primary">Update</button>
					</div>
				</div>
		</form>
	</div>
</div>

</@includes>