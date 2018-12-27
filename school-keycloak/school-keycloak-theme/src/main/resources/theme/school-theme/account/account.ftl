<#import "template.ftl" as layout>
<@layout.mainLayout active='account' bodyClass='user'; section>
    <#assign none = []>
    <#if account.attributes.school == "donlorenzomilani">
    	<#assign rooms = ["0A", "0B", "0C", "1A", "2A", "3A", "4A", "5A", "1B", "2B", "3B", "4B", "5B"]>
    </#if>
    <#if account.attributes.school == "edoardodefilippo">
    	<#assign rooms = ["0B", "0C", "0D", "1A", "2A", "3A", "4A", "5A", "1C", "2C", "3C", "4C", "5C", "2F", "3F", "4F", "5F", "5G"]>
    </#if>
    <#if account.attributes.school == "garibaldi">
    	<#assign rooms = ["0A", "0B", "0C", "1A", "2A", "3A", "4A", "5A", "1B", "2B", "3B", "4B", "5B", "1C", "2C", "3C", "4C", "5C", "1G", "2G", "3G"]>
    </#if>
    <#if account.attributes.school == "giovannixxiii">
    	<#assign rooms = ["0A", "0B", "1A", "2A", "3A", "4A", "5A", "1C", "2C", "3C", "4C", "5C"]>
    </#if>
    <#if account.attributes.school == "leonardodavinci">
    	<#assign rooms = ["0B", "0C", "0E", "0F", "0I", "2C", "4C", "5C", "1D", "2D", "3D", "4D", "5D", "1E", "2E", "3E", "4E", "5E", "1F", "3F"]>
    </#if>
    <#if account.attributes.school == "manzi">
    	<#assign rooms = ["0D", "0E", "0G", "1D", "2D", "3D", "4D", "5D", "1E", "2E", "3E", "4E", "5E"]>
    </#if>
    <#if account.attributes.school == "montecelio">
    	<#assign rooms = ["0A", "2B"]>
    </#if>
    <#if account.attributes.school == "montelucci">
    	<#assign rooms = ["0B", "0E", "1C", "2C", "3C", "4C", "5C", "1D", "2D", "3D", "4D", "5D"]>
    </#if>
    <#if account.attributes.school == "">
    	<#assign rooms = []>
    </#if>
    <#assign schools = ["", "donlorenzomilani", "edoardodefilippo", "garibaldi", "giovannixxiii", "leonardodavinci", "manzi", "montecelio", "montelucci"]>
    <script>
      	(function() {
    		$('#schoolsSelector').on('change', function() {
        		var select_value = $('#instructorSelector').val();
        		<#list schools as school>
        			if (school == select_value){
        				alert("buuuuuuuu");
        			}
        		</#list>
    		});
		})();
    </script>
    <div class="row">
        <div class="col-md-10">
            <h2>${msg("editAccountHtmlTitle")}</h2>
        </div>
        <div class="col-md-2 subtitle">
            <span class="subtitle"><span class="required">*</span> ${msg("requiredFields")}</span>
        </div>
    </div>

    <form action="${url.accountUrl}" class="form-horizontal" method="post">

        <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">

        <#if !realm.registrationEmailAsUsername>
            <div class="form-group ${messagesPerField.printIfExists('username','has-error')}">
                <div class="col-sm-2 col-md-2">
                    <label for="username" class="control-label">${msg("fiscalCode")}</label> <#if realm.editUsernameAllowed><span class="required">*</span></#if>
                </div>

                <div class="col-sm-10 col-md-10">
                    <input type="text" class="form-control" id="username" name="username" <#if !realm.editUsernameAllowed>disabled="disabled"</#if> value="${(account.username!'')}"/>
                </div>
            </div>
        </#if>

        <div class="form-group ${messagesPerField.printIfExists('email','has-error')}">
            <div class="col-sm-2 col-md-2">
            <label for="email" class="control-label">${msg("email")}</label> <span class="required">*</span>
            </div>

            <div class="col-sm-10 col-md-10">
                <input type="text" class="form-control" id="email" name="email" autofocus value="${(account.email!'')}"/>
            </div>
        </div>

        <div class="form-group ${messagesPerField.printIfExists('firstName','has-error')}">
            <div class="col-sm-2 col-md-2">
                <label for="firstName" class="control-label">${msg("firstName")}</label> <span class="required">*</span>
            </div>

            <div class="col-sm-10 col-md-10">
                <input type="text" class="form-control" id="firstName" name="firstName" value="${(account.firstName!'')}"/>
            </div>
        </div>

        <div class="form-group ${messagesPerField.printIfExists('lastName','has-error')}">
            <div class="col-sm-2 col-md-2">
                <label for="lastName" class="control-label">${msg("lastName")}</label> <span class="required">*</span>
            </div>

            <div class="col-sm-10 col-md-10">
                <input type="text" class="form-control" id="lastName" name="lastName" value="${(account.lastName!'')}"/>
            </div>
        </div>
        
        <div class="form-group ${messagesPerField.printIfExists('user.attributes.school','has-error')}">
   			<div class="col-sm-2 col-md-2 ${messagesPerField.printIfExists('user.attributes.school',properties.kcFormGroupErrorClass!)}">
       			<label for="user.attributes.school" class="control-label">${msg("school")}</label>
   			</div>
   			<div class="col-sm-10 col-md-10">
       			<select id="schoolsSelector" name="user.attributes.school">
        			<#list schools as school>
            			<option value="${school}" <#if school == account.attributes.school!''>selected</#if>>${school}</option>
        			</#list>
    			</select>
       		</div>
		</div>
        
        <div class="form-group ${messagesPerField.printIfExists('user.attributes.room','has-error')}">
   			<div class="col-sm-2 col-md-2 ${messagesPerField.printIfExists('user.attributes.room',properties.kcFormGroupErrorClass!)}">
       			<label for="user.attributes.room" class="control-label">${msg("room")}</label>
   			</div>
   			<div class="col-sm-10 col-md-10">
       			<select id="roomsSelector" name="user.attributes.room">
        			<#list rooms as room>
            			<option value="${room}" <#if room == account.attributes.room!''>selected</#if>>${room}</option>
        			</#list>
    			</select>
       		</div>
		</div>
        
        <div class="form-group ${messagesPerField.printIfExists('user.attributes.income','has-error')}">
   			<div class="col-sm-2 col-md-2 ${messagesPerField.printIfExists('user.attributes.income',properties.kcFormGroupErrorClass!)}">
       			<label for="user.attributes.income" class="control-label">${msg("income")}</label>
   			</div>
   			<div class="col-sm-10 col-md-10">
       			<input type="text" class="form-control" id="user.attributes.income" name="user.attributes.income" value="${(account.attributes.income!'')}"/>
   			</div>
		</div>

        <div class="form-group">
            <div id="kc-form-buttons" class="col-md-offset-2 col-md-10 submit">
                <div class="">
                    <#if url.referrerURI??><a href="${url.referrerURI}">${kcSanitize(msg("backToApplication")?no_esc)}</a></#if>
                    <button type="submit" class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="submitAction" value="Save">${msg("doSave")}</button>
                    <button type="submit" class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}" name="submitAction" value="Cancel">${msg("doCancel")}</button>
                </div>
            </div>
        </div>
    </form>

</@layout.mainLayout>