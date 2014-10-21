var app = angular.module("instantSearch", []);
/*app.filter('getIsbn', function() {
    return function(arr, searchString) {
        if (!searchString) {
            return arr;
        }
        var result = [];
        angular.forEach(arr, function(item) {
            try{
                if (item.globalbookid===searchString) {
                    result.push(item);
                }
        }
        catch(e){

        }
        });
        return result;
    };
});
app.directive("clickbutton", function() {
    return {
        restrict: 'AE',
        template: '<div button>click me</div>',
        link: function(scope, element) {
            element.bind("click", function(e) {
                scope.getitems();
            });
        }
    }
});
app.directive("sortButton", function() {
    return {
        restrict: 'A',
        link: function(scope,element,attr) {
            element.bind("click", function(e) {
                if ($(this).find('span').text() === '+') {
                    sortorder = '-' + $(this).attr('id');
                    $(this).find('span').html('-');

                } else {
                    sortorder = $(this).attr('id');
                    $(this).find('span').html('+');
                }
                scope.updateSortOrder(sortorder);
            });
        }
    }
});
app.directive("bookTable",function(){
    return{
        restrict:'A',
        templateUrl:'views/table.html'
    }
}); */
app.controller('InstantSearchController',function($scope, searchService){
    $scope.getitems = function() {
        var bookPromise = searchService.getBooks();
        bookPromise.then(function(data){
            $scope.items=data;
            /*$scope.availableIsbn=[];
            for(var i in $scope.items){
                $scope.availableIsbn.push($scope.items[i].globalbookid);
            }
            $scope.sortorder = 'title';
            $scope.loadingIsDone=true;
            if(!$scope.$$phase){
                $scope.$digest();
            }*/
        });
    }
    $scope.updateSortOrder = function(sortorder) {
        $scope.sortorder = sortorder;
        if(!$scope.$$phase){
            $scope.$digest();
        }
    }
});
/*app.factory('searchService',['$http','$q',function($http,$q){
    var service={};
    service.getBooks=function(){
        var deferred=$q.defer();
        $http({
            method:'GET',
            url:'searchbook.json'
        }).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject('request failed');
        })
        return deferred.promise;
    }
    return service;
}
]);*/
/*app.service('searchService',['$http','$q',function($http,$q){
    this.getBooks=function(){
        var deferred=$q.defer();
        $http({
            method:'GET',
            url:'searchbook.json'
        }).success(function(data){
            deferred.resolve(data);
        }).error(function(){
            deferred.reject('request failed');
        })
        return deferred.promise;
    }
}
]);*/
/*app.factory('searchService', ['$http',
    function($http) {
        return {
            getBooks: function() {
                return $http({
                    method: 'GET',
                    url: 'searchbook.json'
                })
                    .then(function(response) {
                        return response.data;
                    }, function(response) {
                        
                    });
            }
        }
    }
]);*/
app.provider('searchService',function(){
    this.$get=function($http,$q){
        return{
            getBooks:function(){
                var deferred=$q.defer();
                $http({
                    method:'GET',
                    url:'http://localhost:8080/events-1/api/reviewEvent/getevents'
                }).success(function(data){
                    deferred.resolve(data);
                }).error(function(){
                    deferred.reject('request failed');
                })
                return deferred.promise;
            }
        }
    }
});
console.log(bar);
var bar=1;