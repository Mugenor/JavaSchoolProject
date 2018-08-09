<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <base href="${pageContext.request.contextPath}/">
    <link rel="icon" type="image/png" href="resource/images/icons/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="resource/vendor/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="resource/vendor/bootstrap/css/bootstrap-datepicker.css"/>
    <link rel="stylesheet" type="text/css" href="resource/css/util.css"/>
    <link rel="stylesheet" type="text/css" href="resource/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="resource/vendor/jquery-ui/jquery-ui.css"/>
    <script src="resource/vendor/jquery/jquery-3.3.1.js"></script>
    <script src="resource/vendor/moment/moment.js"></script>
    <script src="resource/vendor/bootstrap/js/bootstrap.js"></script>
    <script src="resource/vendor/bootstrap/js/bootstrap-datepicker.js"></script>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <script src="resource/js/register.js"></script>
</head>
<body>
<div class="limiter">
    <div class="container-login100" style="background-image: url('resource/images/bg-01.jpg');">
        <div class="wrap-login100 p-l-110 p-r-110 p-t-62 p-b-33">
            <sf:form cssClass="login100-form validate-form flex-sb flex-w"
                     method="post" modelAttribute="newUser">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger w-full text-center">
                        <p>${error}</p>
                    </div>
                </c:if>
                <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                <span class="login100-form-title">
						Sign Up
					</span>

                <div class="p-t-31 p-b-9">
						<span class="txt1">
							Name
						</span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Name is required">
                    <sf:input cssClass="input100" path="name" maxlength="15" minlength="2"/>
                    <span class="focus-input100"></span>
                </div>

                <div class="p-t-31 p-b-9">
						<span class="txt1">
							Surname
						</span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Surname is required">
                    <sf:input cssClass="input100" path="surname" maxlength="15" minlength="2"/>
                    <span class="focus-input100"></span>
                </div>

                <div class="p-t-31 p-b-9">
						<span class="txt1">
							Username
						</span>
                </div>
                <div class="wrap-input100 validate-input" data-validate="Username is required">
                    <sf:input cssClass="input100" path="username" maxlength="15" minlength="2"/>
                    <span class="focus-input100"></span>
                </div>

                <div class="p-t-31 p-b-9">
                    <span class="txt1">
                        Email
                    </span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="Username is required">
                    <sf:input cssClass="input100" path="email" maxlength="50" minlength="2"/>
                    <span class="focus-input100"></span>
                </div>

                <div class="p-t-13 p-b-9">
						<span class="txt1">
							Password
						</span>
                </div>
                <div class="wrap-input100" data-validate="Password is required">
                    <sf:password cssClass="input100" path="password" maxlength="20" minlength="5"/>
                    <span class="focus-input100"></span>
                </div>

                <div class="p-t-13 p-b-9">
						<span class="txt1">
							Birthday
						</span>
                </div>
                <div class="wrap-input100" data-validate="Birthday is required!">
                    <sf:input type='text' id="datetimepicker" cssClass="input100" minlength="10" maxlength="10"
                              path="birthday"
                              cssStyle="background-color:#f7f7f7 !important; border-radius: 10px !important;"/>
                    <span class="focus-input100"></span>
                </div>

                <div style="margin-top: 20px">
                    <div class="g-recaptcha"
                         data-sitekey="6LfxSmQUAAAAANYKXBvo5SjSGsBTPYAIbBefIiRg"
                         data-callback="reCaptchaCallback"
                         data-expired-callback="reCaptchaExpiredCallback"></div>
                </div>

                <div class="container-login100-form-btn m-t-17">
                    <button class="login100-form-btn">
                        Sign Up
                    </button>
                </div>
                <div class="w-full text-center p-t-20">
						<span class="txt2">
							Already have an account?
						</span>

                    <a href="login" class="txt2 bo1">
                        Sign in now
                    </a>
                </div>
            </sf:form>
        </div>
    </div>
</div>
</body>
</html>
