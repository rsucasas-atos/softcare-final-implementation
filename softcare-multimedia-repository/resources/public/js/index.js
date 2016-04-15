angular.module('angular-app', ['ngStorage', 'angularUtils.directives.dirPagination', 'chart.js'])


.config(function($provide, $httpProvider) {
    // Intercept http calls
    $provide.factory('httpRequestInterceptor', ['$q', '$location', '$localStorage', '$rootScope', function ($q, $location, $localStorage, $rootScope) {
        return {
            'request': function (config)
            {
                config.headers = config.headers || {};

                if ($localStorage.token) {
                    config.headers.Authorization = $localStorage.token;
                    config.headers.Ip = $rootScope.client_ip;
                }

                return config;
            },

            'responseError': function (response)
            {
                if (response.status === 401 || response.status === 403) {
                    $location.path('/index.html');
                }

                return $q.reject(response);
            }
        };
    }]);

    // Add the interceptor to the $httpProvider.
    $httpProvider.interceptors.push('httpRequestInterceptor');


    (function (ChartJsProvider) {
        ChartJsProvider.setOptions({ colors : [ '#803690', '#00ADF9', '#DCDCDC', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'] });
    });
})


.run(function() {

})


.controller('mainController', function($rootScope, $scope, $log, $http, $localStorage, $location, showAlertSrvc) {
    // BASE / REST API URL
	var str = $location.absUrl();
	var n = str.indexOf("#");
	if (n == -1)
	{
		$rootScope.approot = $location.absUrl().replace("index.html", "");
	}
	else
	{
		var res = str.substring(0, n);
		$rootScope.approot = res;
	}

    $rootScope.approot = $rootScope.approot.substring(0, $rootScope.approot.length - 1);

	$log.debug('>> root: ' + $rootScope.approot);
    //$rootScope.approot = gOptions.api_url; //'http://softcare-multimedia-repo.95.211.172.243.xip.io'; //'/multimedia-repository';

    ///////////////////////////////////////////////////////////////////////////
    // $rootScope
    $rootScope.loading = false;

    // GLOBAL FUNCTION: get client IP
    $rootScope.updateClientIp = function() {
        $rootScope.client_ip = "-";
        try {
            $.get("http://ipinfo.io", function(response) {
                $rootScope.client_ip = response.ip;
            }, "jsonp");
        }
        catch(err) {
            log.error(err);
        }
    };

    // FUNCTION: handle error / info messages
    $rootScope.showmessage = function(type, message, obj) {
        $rootScope.show_message_danger = false;
        $rootScope.show_message_info = false;
        $rootScope.show_message_success = false;

        $rootScope.messageLbl = message;

        var delay_time = 2000;
        if (type == 'error') {
            $rootScope.show_message_danger = true;
            $log.error(message);
            delay_time = 20000;
        }
        else if (type == 'success') {
            $rootScope.show_message_success = true;
            $log.debug(message);
        }
        else if (type == 'info') {
            $rootScope.show_message_info = true;
            $log.debug(message);
        }

        if (obj != null)
            $log.debug(obj);

        $rootScope.show_message = showAlertSrvc(delay_time);
    }


    // FUNCTION: close message
    $rootScope.closeMessage = function() {
        $rootScope.show_message = false;
        $rootScope.show_message_danger = false;
        $rootScope.show_message_info = false;
        $rootScope.show_message_success = false;
    }

    $rootScope.show_message = false;
    $rootScope.show_message_danger = false;
    $rootScope.show_message_info = false;
    $rootScope.show_message_success = false;


    ///////////////////////////////////////////////////////////////////////////
    // 1st time page loading / reload ...
    $rootScope.updateClientIp();

    $scope.app_version = gOptions.version;

    //
    // scope
    $scope.showModal = false;
    $scope.user = {
        username: null,
        logged: false,
        password: null,
        rol: null,
        ip: null
    };
    $scope.content= {
        active: false,
        url: 'main.html',
        current: 'Home'
    };

    // fill scope content if user already logged
    if ($localStorage.token != null) {
        $log.debug('>> User already logged ...');
        try {
            $scope.user.password = null;
            $scope.user.username = $localStorage.user.username;
            $scope.user.rol = $localStorage.user.rol;
            $scope.user.logged = true;

            $scope.showModal = $localStorage.showModal;

            $scope.content.url = $localStorage.content.url;
            $scope.content.current = $localStorage.content.current;
        }
        catch(err) {
            $log.error(err)
        }
    }

    ///// $localStorage //////
    $log.debug('>> Initialize $localStorage to store $scope content ... ');
    // token
    // showModal
    $localStorage.showModal = $scope.showModal;
    // user
    $localStorage.user = $scope.user;
    // content
    $localStorage.content = $scope.content;



    /* functions ***********************/
    // FUNCTION:
    $scope.toggleModal = function() {
        $scope.showModal = !$scope.showModal;
        $scope.error = false;
        $scope.user.username = "";
        $scope.user.password = "";
    };

    // FUNCTION: login
    $scope.login = function () {
        var dataObj = {
            username : $scope.user.username,
            password : $scope.user.password,
            ip :  $rootScope.client_ip
        };

        $http.post($rootScope.approot + '/api/user/auth', dataObj).success(function(data) {
            if ((data != null) && (data.code == 1))
            {
                $localStorage.token = data.token;

                $scope.user.password = null;
                $scope.user.username = data.content[0].username;
                $scope.user.rol = data.content[0].rol;
                $scope.user.logged = true;

                $scope.showModal = false;

                if ($scope.user.rol == 'admin') {
                    $scope.content.url = 'users.html';
                    $scope.content.current = 'Users';
                }
                else {
                    $scope.content.url = 'multimedia.html';
                    $scope.content.current = 'Multimedia Content';
                }
            }
            else
            {
                $scope.error = true;
                $scope.user.password = "";
                $scope.errorLbl = "Invalid username / password";
                $log.debug("User not authenticated");
            }
        })
        .error(function() {
            $log.error('Error [/auth/login] !!!!');
        });
    };

    // MENU:
    // FUNCTION: go to users
    $scope.goUsers = function() {
        $scope.content.url = 'users.html';
        $scope.content.current = 'Users';
    };

    // FUNCTION: go to multimedia content
    $scope.goMContent = function() {
        $scope.content.url = 'multimedia.html';
        $scope.content.current = 'Multimedia Content';
    };

    // FUNCTION: go to monitoring
    $scope.goMonitoring = function() {
        $scope.content.url = 'monitoring.html';
        $scope.content.current = 'Monitoring';
    };

    // GLOBAL FUNCTION: logout
    $rootScope.logout = function() {
        $log.debug('>> [' + $scope.user.username + '] >> Log out ... ');

        $localStorage.token = null;
        $scope.user.username = null;
        $scope.user.logged = false;
        $scope.user.rol = null;
        $scope.user.password = null;

        $scope.content.url = 'main.html';
        $scope.content.current = 'Home';
    };

    // GLOBAL FUNCTION: update token
    $rootScope.updateToken = function(data) {
        try {
            if (data.token != null) {
                //$log.debug('>> Setting token value ... '); // + data.token);
                $localStorage.token = data.token;
            }
        }
        catch(err) {
            $log.erro(err);
        }
    };


    /*$scope.user.logged = false;
    $scope.content.url = 'main.html';
    $scope.content.current = 'Home Content';*/
})


.directive('ngConfirmBoxClick', [
    function () {
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmBoxClick || "Are you sure want to delete?";
                var clickAction = attr.confirmedClick;
                element.bind('click', function (event) {
                    if (window.confirm(msg)) {
                        scope.$eval(clickAction)
                    }
                });
            }
        };
    }
])


.directive('modal', function () {
    return {
        template:
        '<div class="modal fade">' +
        '  <div class="modal-dialog modal-sm">' +
        '    <div class="modal-content">' +
        '      <div class="modal-header">' +
        '        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
        '        <h4 class="modal-title">{{ title }}</h4>' +
        '      </div>' +
        '      <div class="modal-body" ng-transclude></div>' +
        '    </div>' +
        '  </div>' +
        '</div>',
        restrict: 'E',
        transclude: true,
        replace:true,
        scope:true,
        link: function postLink(scope, element, attrs) {
            scope.title = attrs.title;

            scope.$watch(attrs.visible, function(value){
                if(value == true)
                    $(element).modal('show');
                else
                    $(element).modal('hide');
            });

            $(element).on('shown.bs.modal', function(){
                scope.$apply(function(){
                    scope.$parent[attrs.visible] = true;
                });
            });

            $(element).on('hidden.bs.modal', function(){
                scope.$apply(function(){
                    scope.$parent[attrs.visible] = false;
                });
            });
        }
    };
})


.service('showAlertSrvc', ['$timeout', function($timeout) {
    return function(delay) {
        var result = {hidden:true};
        $timeout(function() {
            result.hidden=false;
        }, delay);
        return result;
    };
}]);
