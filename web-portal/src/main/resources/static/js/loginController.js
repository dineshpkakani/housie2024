var app = angular.module('loginApp', []);
app.controller('loginController', function ($scope, $http) {
    $scope.doLogin = function () {

        if($scope.username.trim().length<3){
            alert("Please enter proper username");
            return false;
        }
        if($scope.password.trim().length==0){
            alert("Please enter password");
            return false;
        }
        let param={
          username:$scope.username,
          password:$scope.password
        };

        $.ajax({
            url: "/auth/player/login",
            type: "POST",
            contentType: "application/json",  // tell server we send JSON
            data: JSON.stringify(param),     // convert JS object → JSON string
            success: function(response) {

                // ✅ Save tokens to localStorage (or cookie if preferred)
                localStorage.setItem("accessToken", data.accessToken);
                localStorage.setItem("refreshToken", data.refreshToken);
                localStorage.setItem("roles", JSON.stringify(data.roles));

                // ✅ Redirect to landing page from backend response
                window.location.href = data.landingPage;


            },
            error: function(xhr, status, error) {
                console.error("Error: ", xhr.responseText);
                responseMessage.textContent = "⚠️ Error connecting to server!";
                responseMessage.className = "message fail";
            }
        });
    };
    $scope.doRegister = function (){
        window.location.href="register.html";
    }
    $scope.ajax=function(url,param,methodtype,successCallback,errorCallback){

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        if(methodtype=="POST"){

            $http({
                url: url,
                method: "POST",
                params: param,
                headers: {
                    'X-CSRF-TOKEN':$("meta[name='_csrf']").attr("content")
                }
            }).then(function (response) {
                successCallback(response);
            }, function (response) {
                errorCallback(response);
            });

        }else{
            $http.get(url)
                .then(function(response) {
                    successCallback(response);
                });
        }
    };
    $scope.movetoLogin=function (){
        window.location.href="login.html";
    }
    const form = document.getElementById('registrationForm');

    $scope.savePlayer=function (){
        let valid = true;

        // Clear errors
        document.querySelectorAll('.error').forEach(el => el.textContent = "");

        const firstName = document.getElementById('firstName').value.trim();
        const lastName = document.getElementById('lastName').value.trim();
        const username = document.getElementById('username').value.trim();
        const password = document.getElementById('password').value.trim();
        const email = document.getElementById('email').value.trim();
        const phone = document.getElementById('phone').value.trim();

        if (firstName === "") {
            document.getElementById('firstNameError').textContent = "First name is required";
            valid = false;
        }
        if (lastName === "") {
            document.getElementById('lastNameError').textContent = "Last name is required";
            valid = false;
        }
        if (username.length < 5) {
            document.getElementById('usernameError').textContent = "Username must be at least 5 characters";
            valid = false;
        }
        if (password.length < 6) {
            document.getElementById('passwordError').textContent = "Password must be at least 6 characters";
            valid = false;
        }
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            document.getElementById('emailError').textContent = "Enter a valid email";
            valid = false;
        }
        if (!/^\d{10}$/.test(phone)) {
            document.getElementById('phoneError').textContent = "Enter a valid 10-digit phone number";
            valid = false;
        }

        if (valid) {

            // Prepare payload
            const payload = { firstName, lastName, username, password, email, phone };
            let param={
                firstName:firstName,
                lastName:lastName,
                email:email ,
                phone:phone,
                username:username,
                password:password
            }
            let url="/auth/register";
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            let player = {
                firstName: $("#firstName").val(),
                lastName: $("#lastName").val(),
                email: $("#email").val(),
                phone: $("#phone").val(),
                password: $("#password").val()
            };


            $.ajax({
                url: "/auth/player/register",
                type: "POST",
                contentType: "application/json",  // tell server we send JSON
                data: JSON.stringify(player),     // convert JS object → JSON string
                success: function(response) {
                    alert("Registered successfully: " + response);
                    responseMessage.textContent = "✅ Registration successful!";
                    responseMessage.className = "message success";
                },
                error: function(xhr, status, error) {
                    console.error("Error: ", xhr.responseText);
                    responseMessage.textContent = "⚠️ Error connecting to server!";
                    responseMessage.className = "message fail";
                }
            });
        }
    }
    $scope.checkEmail=function (){
            $.ajax({
                url: "/auth/player/check-email",
                method: "GET",
                data: { email: $("#email").val() },
                success: function(response) {
                    if (response.includes("exists")) {
                        $("#emailError").text(response).removeClass("success").addClass("error");
                    } else {
                        $("#emailError").text(response).removeClass("error").addClass("success");
                    }
                },
                error: function() {
                    $("#emailError").text("Error checking email").removeClass("success").addClass("error");
                }
            });

    }
});