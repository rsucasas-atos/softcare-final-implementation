var main = angular.module('main', []);

main.controller('mainController', function($scope, $http, $window, $location) {
	// base url
	var str = $location.absUrl();
	var n = str.indexOf("#");
	if (n == -1)
	{
		$scope.rooturl = $location.absUrl().replace("index.html", "");
	}
	else
	{
		var res = str.substring(0, n);
		$scope.rooturl = res;
	}
	console.log('root: ' + $scope.rooturl);
	
	$scope.user = {
        user: null,
        password: null
    };
	
	$scope.showModal = false;
    $scope.toggleModal = function(){
        $scope.showModal = !$scope.showModal;
        $scope.error = false;
    };
    
    
    // FUNCTION: login
    $scope.login = function () {
        var dataObj = {
            username : $scope.user.username,
            password : $scope.user.password,
            ip :  ''
        };
        
        //console.log($scope.user.username + ':' + $scope.user.password);
        
        $http.get($scope.rooturl + 'services/api/url/ws').success(function(data) {
			console.log('CALL ---> ' + $scope.rooturl + 'services/api/url/ws');
        	if ((data != null) && (data.code == 0))
            {
                console.log(data.result);
                $http({
                    method : 'POST',
                    url : data.result + '/rest/api/login', //'http://localhost:9080/softcare-ws/rest/api/login',
                    data : $.param({
                        'userName' : $scope.user.username,
                        'password' : $scope.user.password
                    }),
                    headers : {
                        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
                    }
                }).success(function (data) {
                    console.log(data);
                    if ((data != null) && (data.code > 0))
                    {
                    	$scope.error = false;
						document.form123.action = $scope.rooturl + 'adm/autologin.zul';
                        $("#var1").val(data.code);
                        $("#var2").val($scope.user.username);
                        $("#var3").val(data.data1);
                        $("#form").submit();

                        //$window.location.href = $scope.rooturl + 'adm/autologin.zul?id=' 
                        //	+ data.code + '&user=' + $scope.user.username + '&rol=' + data.data1;
                    }
                    else
                    {
                        $scope.error = true;
		                $scope.user.password = "";
		                $scope.errorLbl = "Invalid username / password";
                    }
                });
                
            }
        	else
    		{
        		console.log(data);
    		}
        })
        .error(function(data) {
           
        });
    };
    
});

main.directive('modal', function () {
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
});
