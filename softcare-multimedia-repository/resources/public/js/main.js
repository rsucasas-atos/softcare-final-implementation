angular.module('angular-app')

// http://jtblin.github.io/angular-chart.js
.controller('mainhtmlController', function($rootScope, $scope, $interval, $http) {

    var refreshChart = function() {
        $http.get($rootScope.approot + '/api/records/info').success(function(data) {
            //console.log($rootScope.approot + '/api/records/info');
            if ((data != null) && (data.code == 1)) {
                // {:video 2, :music 0, :image 0, :book 0, :other 0}

                $scope.labels1 = ["Videos", "Books", "Music", "Images", "Other"];
                $scope.data1 = [data.content.video, data.content.music, data.content.image, data.content.book, data.content.other];
                $scope.type1 = 'PolarArea';
                $scope.options1 = {
                    animation: true,
                    showScale: true,
                    showTooltips: true,
                    pointDot: true,
                    datasetStrokeWidth: 0.5
                };

                $scope.toggle = function () {
                    $scope.type = $scope.type === 'PolarArea' ? 'Pie' : 'PolarArea';
                };
            }
            else if ((data != null) && (data.code == 0))
                $rootScope.logout();
            else
                $scope.showmessage('error', 'Error getting records info', data);
        })
        .error(function(data) {
            $scope.showmessage('error', 'Error validating user', data);
        })
        .finally(function() {
            $rootScope.loading = false;
        });
    };

    var promise = $interval(refreshChart, 30000);

    // Cancel interval on page changes
    $scope.$on('$destroy', function(){
        if (angular.isDefined(promise)) {
            $interval.cancel(promise);
            promise = undefined;
        }
    });

    refreshChart();


    $scope.getRandomizer = function(bottom, top) {
        return Math.floor( Math.random() * ( 1 + top - bottom ) ) + bottom;
    };
})


.controller('TicksCtrl', ['$scope', '$interval', function ($scope, $interval) {
    var maximum = 400; //document.getElementById('container').clientWidth / 2 || 300;
    $scope.data = [[]];
    $scope.labels = [];
    $scope.options = {
        animation: false,
        showScale: false,
        showTooltips: false,
        pointDot: false,
        datasetStrokeWidth: 0.5
    };


    $scope.getRandomValue = function  (data) {
        var l = data.length, previous = l ? data[l - 1] : 50;
        var y = previous + Math.random() * 10 - 5;
        return y < 0 ? 0 : y > 100 ? 100 : y;
    };


    // Update the dataset at 25FPS for a smoothly-animating chart
    $interval(function () {
        getLiveChartData();
    }, 30);

    function getLiveChartData () {
        if ($scope.data[0].length) {
            $scope.labels = $scope.labels.slice(1);
            $scope.data[0] = $scope.data[0].slice(1);
        }

        while ($scope.data[0].length < maximum) {
            $scope.labels.push('');
            $scope.data[0].push($scope.getRandomValue($scope.data[0]));
        }
    };

    var json = {
        "series": ["SeriesA"],
        "data": [["90", "99", "80", "91", "76", "75", "60", "67", "59", "55"]],
        "labels": ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10"],
        "colours": [{ // default
            "fillColor": "#FFFFFC",
            "strokeColor": "rgba(207,100,103,1)",
            "pointColor": "rgba(220,220,220,1)",
            "pointStrokeColor": "#fff",
            "pointHighlightFill": "#fff",
            "pointHighlightStroke": "rgba(151,187,205,0.8)"
        }]
    };
    $scope.ocw = json;

    $scope.colours123 = ["rgba(224, 108, 112, 1)",
            "rgba(224, 108, 112, 1)",
            "rgba(224, 108, 112, 1)"];
}]);

