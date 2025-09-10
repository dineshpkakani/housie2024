var app = angular.module('adminApp', []);
app.controller('adminController', function ($scope, $http) {

    $scope.appName = '';
    $scope.eventList = [];
    $scope.totalDisplayRecord = 10;
    $scope.currentpage = 1;
    $scope.eventID = 0;
    $scope.txteventsearch = '';
    $scope.prizeListData = [];

    $scope.prizeconfigobj = {
        eventnameListData: [],
        prizenameListData: [],
        eventprizedata: [],
        imagename: '',
        eventdate: '',
        prizename: '',
        prizedisplayname: '',
        eventname: '',
        eventdisplayname: '',
        prizeamount: '0',
        qty: '0'

    };
    $scope.summary = {
        eventname: '',
        eventdate: '',
        ticketprice: '',
        status: '',
        totalcollection: '',
        totalprize: '',
        soldtickets: '',
        noofplayers: ''
    };

    /** common function **/
    $scope.init = function () {

        $scope.setScreen(1);
        $scope.loadEventList();

        //prize master list
        $scope.priceList();

        //event prize configure screen
        $scope.loadEventnameList();
        $scope.loadPrizenameList();

    }
    $scope.setScreen = function (scrnumber) {
        $("#userregistartion").hide();
        $("#eventmasterdiv").hide();
        $("#pricemasterdiv").hide();
        $("#usermasterdiv").hide();
        $("#diveventprizeconfigure").hide();

        if (scrnumber === 1) {//Event Master
            $("#eventmasterdiv").show();
        } else if (scrnumber === 2) {//Price master
            $("#pricemasterdiv").show();
        } else if (scrnumber === 3) {//price configure
            $("#diveventprizeconfigure").show();
        } else if (scrnumber === 4) {//User Registration
            $("#userregistartion").show();
        }
    }
    /** Event master function's **/
    $scope.OpenEventModal = function (eventid) {
        $scope.eventID = 0;
        $scope.txteventname = '';
        $scope.txtmaxtickets = 1;
        $scope.txtperprice = 10;
        $scope.txtsoldtickets = 0;
        $scope.txttotplayers = 0;
        $scope.txteventdate = 0;
        $scope.errorlist = "";

        $('#eventmodaldial').fadeIn();
    }
    $scope.closeEventModal = function () {
        $("#eventmodaldial").fadeOut();
    }
    $scope.closeUserModal = function () {
        $("#userregistartion").fadeOut();
    }
    $scope.loadEventList = function () {
        var urlName = $scope.appName + "/event/getAll?page=" + ($scope.currentpage - 1) + "&size=" + $scope.totalDisplayRecord;
        $scope.ajax(urlName, null, "GET", $scope.loadEventListSuccess, $scope.AjaxErrorMessage);
    }
    $scope.loadEventByTitle = function () {
        if ($scope.txteventsearch == '') {
            $scope.loadEventList();
            return;
        } else {
            $scope.currentpage = 1;
            var urlName = $scope.appName + "/event/get/name/" + $scope.txteventsearch + "?page=0&size=" + $scope.totalDisplayRecord;
            $scope.ajax(urlName, null, "GET", $scope.loadEventListSuccess, $scope.AjaxErrorMessage);
        }
    }
    $scope.loadEventById = function (id) {
        $scope.eventID = id;
        var urlName = $scope.appName + "/event/get/id/" + id;
        $scope.ajax(urlName, null, "GET", $scope.loadEventDataForID, $scope.AjaxErrorMessage);
    }
    $scope.nextpage = function () {
        $("#btnNext").addClass("disabled");
        $scope.currentpage = $scope.currentpage + 1;
        $scope.loadEventList();
    }
    $scope.prevpage = function () {
        $("#btnPrev").addClass("disabled");
        $scope.currentpage = $scope.currentpage - 1;
        $scope.loadEventList();
    }
    $scope.loadEventListSuccess = function (response) {
        if (response.status === 200) {
            $scope.eventList = response.data.lst[0];
            $scope.totalCount = response.data.totalrecords;
        } else {
            $scope.eventList = [];
            $scope.totalCount = 0;
        }
        $scope.updateEventPaginations();

    };
    $scope.loadEventDataForID = function (response) {
        let data = response.data.lst[0];

        $scope.txteventname = data.name;
        if (data.eventDate != null && data.eventDate != '' && data.eventDate != undefined) {
            ///$scope.txteventdate = data.eventDate.replace(/(\d\d)\/(\d\d)\/(\d{4})/, "$3-$1-$2");
            $scope.txteventdate = new Date(data.eventDate).toLocaleDateString('fr-CA');
            $("#txteventdate").val($scope.txteventdate);
        }
        $scope.txtmaxtickets = data.noOfTickets;
        $scope.txtperprice = data.pricePerTicket;
        $scope.scltstatus = data.status;
        $scope.txtsoldtickets = data.soldTickets;
        $scope.txttotplayers = data.noOfUsers;
        $('#eventmodaldial').fadeIn();
    }
    $scope.saveEvent = function () {

        $scope.errordiv = "";
        var urlName = $scope.appName + "/event";
        var methodtype = 'POST';
        let saveOrUpdate = parseInt($scope.eventID) === 0;
        if (saveOrUpdate) {
            urlName = urlName + "/save";
            methodtype = 'POST'
        } else {
            methodtype = 'PUT';
        }
        let eventDate;
        if ($scope.txteventdate != "" && $scope.txteventdate != undefined) {

            if ($scope.txteventdate instanceof Date) {
                var localeventdate = $scope.txteventdate;
                let yyyy = localeventdate.getFullYear();
                let mm = localeventdate.getMonth() + 1;
                let dd = localeventdate.getDate();
                if (parseInt(dd) < 10) dd = '0' + dd;
                if (parseInt(mm) < 10) mm = '0' + mm;
                eventDate = mm + '/' + dd + '/' + yyyy;
            } else {
                var localeventdate = $scope.txteventdate.split("-");
                let yyyy = localeventdate[0];
                let mm = localeventdate[1];
                let dd = localeventdate[2];
                eventDate = mm + '/' + dd + '/' + yyyy;
            }
        }

        if ($scope.soldTickets == '') {
            $scope.soldTickets = 0;
        }
        let param = {
            "eventId": $scope.eventID,
            "name": $scope.txteventname,
            "noOfTickets": parseInt($scope.txtmaxtickets),
            "pricePerTicket": parseInt($scope.txtperprice),
            "status": $scope.scltstatus,
            "soldTickets": $scope.txtsoldtickets,
            "noOfUsers": $scope.txttotplayers
        };
        {
            param["eventDate"] = eventDate;
        }

        $scope.ajax(urlName, param, methodtype, function (response) {
                if (response.data.success != undefined) {
                    alert(response.data.success);
                } else {
                    alert(response.data.failed);
                }

                $scope.closeEventModal();
                $scope.loadEventList();
            },
            $scope.saveBatchError);
    };
    $scope.saveBatchError = function (response) {
        if (response.status = 400) {
            $scope.errorlist = response.data;
        }
    }
    $scope.updateEventPaginations = function () {

        let totalpages = Math.ceil($scope.totalCount / $scope.totalDisplayRecord);
        $scope.totalpages = totalpages;
        if ($scope.currentpage > 1) {
            $("#btnPrev").removeClass("disabled");
        } else {
            $("#btnPrev").addClass("disabled");
        }
        if ($scope.currentpage < totalpages) {
            $("#btnNext").removeClass("disabled");
        } else {
            $("#btnNext").addClass("disabled");
        }
    }
    $scope.openSummaryModal = function (eventid) {

        $scope.eventID = eventid;
        var urlName = $scope.appName + "/event/get/id/" + $scope.eventID;
        $('#eventsummarymodal').fadeIn();
        $scope.ajax(urlName, null, "GET", function (response) {
            if (response.status == 200) {
                let data = response.data.lst[0];
                $scope.summary = {
                    eventname: data.name,
                    eventdate: data.eventDate,
                    ticketprice: data.pricePerTicket,
                    status: data.status,
                    totalcollection: '0',
                    totalprize: '0',
                    soldtickets: data.soldTickets,
                    noofplayers: data.noOfUsers
                };
            } else {
                alert(response.data.failed);
            }
        });

    }
    $scope.closeSummaryModal = function () {
        $('#eventsummarymodal').fadeOut();
    }

    /** Prize master function's **/
    $scope.priceList = function () {
        var urlName = $scope.appName + "/prizemaster/getall";
        $scope.ajax(urlName, null, "GET", $scope.loadPrizeListSuccess, $scope.AjaxErrorMessage);
    }
    $scope.loadPrizeListSuccess = function (response) {
        $scope.prizeListData = response.data.lst[0];
    };
    $scope.openpriceConfigureView = function () {
        $('#priceconfigureddiv').fadeIn();
    }
    $scope.closepriceConfigureView = function () {
        $('#priceconfigureddiv').fadeOut();
    }
    $scope.ajax = function (url, param, methodtype, successCallback, errorCallback) {
        if (methodtype == "POST" || methodtype == "PUT") {
            $http({
                url: url,
                method: methodtype,
                data: JSON.stringify(param),
                contentType: 'application/json; charset=utf-8'
            }).then(function (response) {
                successCallback(response);
            }, function (response) {
                errorCallback(response);
            });

        } else {
            $http.get(url)
                .then(function (response) {
                    successCallback(response);
                });
        }
    };
    $scope.AjaxErrorMessage = function (response) {
        if (response.status == 404) {
            alert("Requested Page not found.Please Contact Developers. ");
        } else if (response.status == 403) {
            alert("Internal server error.Please Contact Developers.");
        }
    };
    //Fill event name list
    $scope.loadEventnameList = function () {

        var urlName = $scope.appName + "/event/geteventname";

        $scope.ajax(urlName, '', 'GET', function (response) {
                if (response.status === 200) {
                    $scope.prizeconfigobj.eventnameListData = response.data.lst[0];
                } else {
                    alert(response.data.failed);
                }
            },
            '');
    };
    //Fill prize name list in configure
    $scope.loadPrizenameList = function () {

        var urlName = $scope.appName + "/prizemaster/getname";
        $scope.ajax(urlName, '', 'GET', function (response) {
                if (response.status === 200) {
                    $scope.prizeconfigobj.prizenameListData = response.data.lst[0];
                } else {
                    alert(response.data.failed);
                }
            },
            '');
    };
    $scope.setPrizeListforEvent = function () {
        let data = $scope.prizeconfigobj.eventname.split("--");
        $scope.prizeconfigobj.eventdate = data[1];
        let EventID = data[0];

        var urlName = $scope.appName + "/prizeconfigure/getAll/" + EventID;
        $scope.ajax(urlName, '', 'GET', function (response) {
                if (response.status === 200) {
                    $scope.prizeconfigobj.eventprizedata = response.data.lst[0];
                } else {
                    alert(response.data.failed);
                }
            },
            '');

    }
    $scope.setPrizeConfigImagePath = function (imagepath) {
        $scope.prizeconfigobj.imagename = $scope.prizeconfigobj.prizename.split("--")[1];
    }
    $scope.addeventprize = function () {

        if ($scope.prizeconfigobj.eventname === '') {
            alert("Please select the Event.");
            $("#sclteventname").focus();
            return false;
        }
        if ($scope.prizeconfigobj.prizename === '') {
            alert("Please select the Prize.");
            $("#scltprizename").focus();
            return false;
        }
        if ($scope.prizeconfigobj.qty === '' || $scope.prizeconfigobj.qty === '0') {
            alert("Please select the Quantity.");
            $("#prizeconfigqty").focus();
            return false;
        }
        if (isNaN($scope.prizeconfigobj.qty)) {
            alert("Please enter numbers only in Quantity.");
            $("#prizeconfigqty").focus();
            return false;
        }
        $scope.prizeconfigobj.prizeamount = '0';

        try {
            for (var i = 1; i <= $scope.prizeconfigobj.qty; i++) {
                $("#txtprizeconfigqty" + i).val(0);
            }
        } catch (e) {
        }
        $scope.prizeconfigobj.eventdisplayname = $("#sclteventname option:selected").text();
        $scope.prizeconfigobj.prizedisplayname = $("#scltprizename option:selected").text();


        $("#addpriceconfigureddiv").fadeIn();
    }
    $scope.prizeConfigaddDialogClose = function () {
        $("#addpriceconfigureddiv").fadeOut();
    }
    $scope.getNumbers = function (num) {
        var x = new Array();
        for (var i = 0; i < num; i++) {
            x.push(i + 1);
        }
        return x;
    }
    $scope.EventPrizeConfigSetAmount = function (val) {

        let size = $scope.prizeconfigobj.qty;
        let amount = $scope.prizeconfigobj.prizeamount;
        let equallyAmount = Math.trunc(amount / size);
        var remainingamount = amount - (equallyAmount * size);
        for (var i = 1; i < size; i++) {
            $("#txtprizeconfigqty" + i).val(equallyAmount);
        }
        $("#txtprizeconfigqty" + size).val(equallyAmount + remainingamount);
    };

    $scope.saveEventPrizeData = function () {
        let qty = $scope.prizeconfigobj.qty;
        let totalamount = $scope.prizeconfigobj.prizeamount;
        let EventID = $scope.prizeconfigobj.eventname.split("--")[0];
        let prizeID = $scope.prizeconfigobj.prizename.split("--")[0];
        var newtotal = 0;
        if (totalamount == 0) {
            alert("Please enter total amount");
            return false;
        }
        for (var i = 1; i <= qty; i++) {
            let d = parseInt($("#txtprizeconfigqty" + i).val())
            if (d == 0 || d == '') {
                alert("Please enter all prize amount details");
                return false;
            }
            newtotal += d;
        }
        if (parseInt(totalamount) !== newtotal) {
            alert("Total is mismatch");
            return false;
        }

        var prizeconfig = new Array();
        for (var i = 1; i <= qty; i++) {
            let payload = {
                "prizeEntity": {
                    "id": prizeID
                },
                "eventEntity": {
                    "eventId": EventID
                },
                "sequence": i,
                "prizevalue": $("#txtprizeconfigqty" + i).val()
            }
            prizeconfig.push(payload)
        }
        console.log(prizeconfig);
        var urlName = $scope.appName + "/prizeconfigure";
        var methodtype = 'POST';
        let saveOrUpdate = parseInt($scope.eventID) === 0;

        $scope.ajax(urlName, prizeconfig, methodtype, function (response) {

        }, function (response) {
            alert("Error while saving prize configuration");
        });

    }
    $scope.saveuser = function () {
        if($scope.txtregistername==""){
            $scope.errorlist={"name":"Please enter full name"};
        }
        if($scope.txtregisterusername==""){
            $scope.errorlist={"name":"Please enter user name"};
        }
        if($scope.txtregisterempcode==""){
            $scope.errorlist={"name":"Please enter employee code"};
        }
        if($scope.txtregisteremail==""){
            $scope.errorlist={"name":"Please enter email"};
        }
        if($scope.slctregisterrole==""){
            $scope.errorlist={"name":"Please select role"};
        }


    }


});
