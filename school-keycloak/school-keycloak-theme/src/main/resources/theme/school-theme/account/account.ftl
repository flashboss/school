<#import "template.ftl" as layout>
<@layout.mainLayout active='account' bodyClass='user'; section>
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
        
        <div class="form-group ${messagesPerField.printIfExists('account.attributes.school','has-error')}">
   			<div class="col-sm-2 col-md-2 ${messagesPerField.printIfExists('account.attributes.school',properties.kcFormGroupErrorClass!)}">
       			<label for="account.attributes.school" class="control-label">${msg("school")}</label>
   			</div>
   			<div class="col-sm-10 col-md-10">
       			<select id="schoolsSelector" name="account.attributes.school">
        			<#list schools as key,value>
            			<option value="${key}" <#if key == account.attributes.school!''>selected</#if>>${value}</option>
        			</#list>
    			</select>
       		</div>
		</div>
        
        <div class="form-group ${messagesPerField.printIfExists('account.attributes.room','has-error')}">
   			<div class="col-sm-2 col-md-2 ${messagesPerField.printIfExists('account.attributes.room',properties.kcFormGroupErrorClass!)}">
       			<label for="account.attributes.room" class="control-label">${msg("room")}</label>
   			</div>
   			<div class="col-sm-10 col-md-10">
       			<select id="roomsSelector" name="account.attributes.room">
    			</select>
       		</div>
		</div>
        
        <div class="form-group ${messagesPerField.printIfExists('account.attributes.income','has-error')}">
   			<div class="col-sm-2 col-md-2 ${messagesPerField.printIfExists('account.attributes.income',properties.kcFormGroupErrorClass!)}">
       			<label for="account.attributes.income" class="control-label">${msg("income")}</label>
   			</div>
   			<div class="col-sm-10 col-md-10">
       			<input type="text" class="form-control" id="account.attributes.income" name="account.attributes.income" value="${(account.attributes.income!'')}"/>
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
    <script>
      	(function() {
      	    function roomsSelect(schoolselect) {
      			var roomselect = document.getElementById("roomsSelector");
      			roomselect.innerText = "";
        		var select_value = schoolselect.value;
        		var rooms = [""];
        		if ("donlorenzomilani" == select_value){
        			rooms = ["", "0A", "0B", "0C", "1A", "2A", "3A", "4A", "5A", "1B", "2B", "3B", "4B", "5B"];
        		} else if ("edoardodefilippo" == select_value){
        			rooms = ["", "0B", "0C", "0D", "1A", "2A", "3A", "4A", "5A", "1C", "2C", "3C", "4C", "5C", "2F", "3F", "4F", "5F", "5G"];
        		} else if ("garibaldi" == select_value){
        			rooms = ["", "0A", "0B", "0C", "1A", "2A", "3A", "4A", "5A", "1B", "2B", "3B", "4B", "5B", "1C", "2C", "3C", "4C", "5C", "1G", "2G", "3G"];
        		} else if ("giovannixxiii" == select_value){
        			rooms = ["", "0A", "0B", "1A", "2A", "3A", "4A", "5A", "1C", "2C", "3C", "4C", "5C"];
        		} else if ("leonardodavinci" == select_value){
        			rooms = ["", "0B", "0C", "0E", "0F", "0I", "2C", "4C", "5C", "1D", "2D", "3D", "4D", "5D", "1E", "2E", "3E", "4E", "5E", "1F", "3F"];
        		} else if ("manzi" == select_value){
        			rooms = ["", "0D", "0E", "0G", "1D", "2D", "3D", "4D", "5D", "1E", "2E", "3E", "4E", "5E"];
        		} else if ("montecelio" == select_value){
        			rooms = ["", "0A", "2B"];
        		} else if ("montelucci" == select_value){
        			rooms = ["", "0B", "0E", "1C", "2C", "3C", "4C", "5C", "1D", "2D", "3D", "4D", "5D"];
        		}
				for(var i = 0; i < rooms.length; i++) {
    				var opt = document.createElement('option');
    				opt.innerHTML = rooms[i];
    				opt.value = rooms[i];
    				if ("${account.attributes.room}" == opt.value) {
    					opt.selected = true;
    				}
    				roomselect.appendChild(opt);
				}
      	    }
      		var schoolselect = document.getElementById("schoolsSelector");
      	    roomsSelect(schoolselect);
  			schoolselect.onchange = function() {
  				roomsSelect(schoolselect);
    		};
		})();
    </script>
</@layout.mainLayout>