var app = angular.module('eventApp', ['ui.bootstrap']);
app.controller('eventController', function($scope, $http,$compile,$filter) {
    $scope.dateformat = 'MM/dd/yyyy';
    $scope.eventList=[];
    $scope.appName="/housie";
    $scope.event={
        eventDate:"",
        eventName:"",
        maximumticket:"0",
        priceperticket:"0",
        status:"Not started"
    };
    $scope.setModal=function(val){
        $('#exampleModal').modal(val);
        $scope.errorList = {eventDate:'',name:''};
        $scope.event.eventName = '';
        $scope.event.eventDate = '';
        $scope.event.maximumticket = '';
        $scope.event.priceperticket = '';
        $scope.event.Status = '';
     }
    $scope.saveEvent=function(val){

        $scope.checkEmpty($scope.event.eventDate,"Event Date");
        $scope.checkEmpty($scope.event.eventName,"Event Name");
        $scope.checkEmpty($scope.event.maximumticket,"Maximum Ticker Per User");
        $scope.checkEmpty($scope.event.priceperticket,"Price Per Ticket");
        $scope.checkEmpty($scope.event.status,"Status");
        var urlName=$scope.appName+"/event/save";

        var eventObj={
            name:$scope.event.eventName,
            //eventDate:$filter('date')($scope.event.eventDate,$scope.dateformat),
            eventDate:'2024-09-23T22:05:18.884Z',
            noOfTickets: $scope.event.maximumticket,
            pricePerTicket: $scope.event.priceperticket,
            status:$scope.event.status
        };
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $scope.ajax(urlName,eventObj,"POST",$scope.successMessage,$scope.AjaxErrorMessage);
    };
    $scope.successMessage=function(response){
        console.log(response);
        if(response.data.status==='fail'){
            var errorList = response.data.errorList;
            for(var i=0;i<errorList.length;i++){
                $scope.errorList[errorList[i]['field']] = errorList[i].defaultMessage;
            }
        }else{
            $scope.setModal('hide');
            //$scope.loadData(1);
        }
    }
    $scope.loadDataSuccess=function(response){

        $scope.eventList = response.data.data;
        //var table = $('#example').DataTable();
        //table.page( 'next' ).draw( 'page' );

    };
    $scope.AjaxErrorMessage=function(response){
        if(response.status==404){
            alert("Requested Page not found ");
        }else if(response.status==403){
            alert("Internal server error.");
        }
    };
    $scope.checkEmpty=function(htmlId,htmlcontrolname){
        if(angular.isUndefined(htmlId) || htmlId==null || $.trim(htmlId).length<=0 ){
            alert("Please select "+htmlcontrolname+" ");
            return false;
        }
        return true;
    };
    $scope.handleAjaxErrorLoc=function(xhr,textstatus,err){
        console.log(err);
        console.log(xhr);
        console.log(textstatus);

    };

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

});