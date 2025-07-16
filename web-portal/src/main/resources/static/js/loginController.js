var app = angular.module('loginApp', []);
app.controller('loginController', function ($scope, $http) {
    $scope.doLogin = function () {
        let loginurl="/login";
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

        $("#frmlogin").submit();

        /*var data = $('#frmlogin').serialize();
        $.post(loginurl, data,function (response){
            alert(response);
        });*/

    };


});