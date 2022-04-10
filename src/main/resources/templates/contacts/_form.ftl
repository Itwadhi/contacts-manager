  <div class="form-group">
    <label for="fullName" class="col-sm-2 control-label">Full Name</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="fullName" id="fullName" value="<#if contact?? && !contact.fullName?matches('null') >${contact.fullName}</#if>" maxlength="30" placeholder="Full Name" required="">
    </div>
  </div>
  <div class="form-group">
    <label for="phoneNumber" class="col-sm-2 control-label">Phone Number</label>
    <div class="col-sm-10">
      <input type="number" class="form-control" name="phoneNumber" id="phoneNumber" value="<#if contact?? && !contact.phoneNumber?matches('null') >${contact.phoneNumber}</#if>" maxlength="15" placeholder="Phone Number" required="">
    </div>
  </div>
  <div class="form-group">
    <label for="email" class="col-sm-2 control-label">Email</label>
    <div class="col-sm-10">
      <input type="email" class="form-control" name="email" id="email" value="<#if contact?? && !contact.email?matches('null') >${contact.email}</#if>" placeholder="Email" required="">
    </div>
  </div>
  <div class="form-group">
    <label for="dateOfBirth" class="col-sm-2 control-label">Date Of Birth</label>
    <div class="col-sm-10">
      <input type="text" class="form-control datepicker" name="dateOfBirth" id="dateOfBirth" value="<#if contact?? && !contact.dateOfBirth?matches('null') >${contact.dateOfBirth?string['yyyy-MM-dd']}</#if>" required="">
    </div>
  </div>
  <div class="form-group">
    <label for="file" class="col-sm-2 control-label">Profile Image</label>
    <div class="col-sm-10">
      <#if contact?? && !contact.dateOfBirth?matches('null') >
      <img src="<@spring.url '/attachments/${contact.profileImage}/' />" width="100" height="100" class="img-responsive" alt="Profile Image">
      </#if>
      <input type="file" name="file" id="file" <#if contact?? && !contact.dateOfBirth?matches('null') ><#else>required=""</#if>>
    </div>
  </div>
