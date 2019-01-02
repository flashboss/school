<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
        ${msg("doRegister")}
        <#assign schools = ["", "donlorenzomilani", "edoardodefilippo", "garibaldi", "giovannixxiii", "leonardodavinci", "manzi", "montecelio", "montelucci"]>
    <#elseif section = "form">
        <form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.registrationAction}" method="post">
            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('firstName',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="firstName" class="${properties.kcLabelClass!}">${msg("firstName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="firstName" class="${properties.kcInputClass!}" name="firstName" value="${(register.formData.firstName!'')}" />
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('lastName',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="lastName" class="${properties.kcLabelClass!}">${msg("lastName")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="lastName" class="${properties.kcInputClass!}" name="lastName" value="${(register.formData.lastName!'')}" />
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('email',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="email" class="${properties.kcLabelClass!}">${msg("email")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="email" class="${properties.kcInputClass!}" name="email" value="${(register.formData.email!'')}" autocomplete="email" />
                </div>
            </div>

          	<#if !realm.registrationEmailAsUsername>
            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('username',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="username" class="${properties.kcLabelClass!}">${msg("fiscalCode")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="username" class="${properties.kcInputClass!}" name="username" value="${(register.formData.username!'')}" autocomplete="username" />
                </div>
            </div>
          	</#if>
          
            <div class="${properties.kcFormGroupClass!}">
   				<div class="${properties.kcLabelWrapperClass!} ${messagesPerField.printIfExists('user.attributes.school',properties.kcFormGroupErrorClass!)}">
       				<label for="user.attributes.school" class="${properties.kcLabelClass!}">${msg("school")}</label>
   				</div>
   				<div class="${properties.kcInputWrapperClass!}">
       				<select id="schoolsSelector" name="user.attributes.school">
        				<#list schools as school>
            				<option value="${school}">${school}</option>
        				</#list>
    				</select>
   				</div>
			</div>
			
            <div class="${properties.kcFormGroupClass!}">
   				<div class="${properties.kcLabelWrapperClass!} ${messagesPerField.printIfExists('user.attributes.room',properties.kcFormGroupErrorClass!)}">
       				<label for="user.attributes.room" class="${properties.kcLabelClass!}">${msg("room")}</label>
   				</div>
   				<div class="${properties.kcInputWrapperClass!}">
       				<select id="roomsSelector" name="user.attributes.room">
    				</select>
   				</div>
			</div>
			
            <div class="${properties.kcFormGroupClass!}">
   				<div class="${properties.kcLabelWrapperClass!} ${messagesPerField.printIfExists('user.attributes.income',properties.kcFormGroupErrorClass!)}">
       				<label for="user.attributes.income" class="${properties.kcLabelClass!}">${msg("income")}</label>
   				</div>
   				<div class="${properties.kcInputWrapperClass!}">
       				<input type="text" id="user.attributes.income" class="${properties.kcInputClass!}" name="user.attributes.income"/>
   				</div>
			</div>

            <#if passwordRequired>
            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="password" class="${properties.kcLabelClass!}">${msg("password")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="password" id="password" class="${properties.kcInputClass!}" name="password" autocomplete="new-password"/>
                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('password-confirm',properties.kcFormGroupErrorClass!)}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="password-confirm" class="${properties.kcLabelClass!}">${msg("passwordConfirm")}</label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="password" id="password-confirm" class="${properties.kcInputClass!}" name="password-confirm" />
                </div>
            </div>
            </#if>

            <#if recaptchaRequired??>
            <div class="form-group">
                <div class="${properties.kcInputWrapperClass!}">
                    <div class="g-recaptcha" data-size="compact" data-sitekey="${recaptchaSiteKey}"></div>
                </div>
            </div>
            </#if>

            <div class="${properties.kcFormGroupClass!}">
                <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
                        <span><a href="${url.loginUrl}">${kcSanitize(msg("backToLogin"))?no_esc}</a></span>
                    </div>
                </div>

                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doRegister")}"/>
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
    </#if>
</@layout.registrationLayout>
