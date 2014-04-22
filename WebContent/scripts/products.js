angular.module('products', [])
.controller('CustomerCtrl', ['$scope', '$http', 
function($scope, $http){
	
	$http({method: 'GET', url: 'products'})
	.success(function(data) {
		console.log(data);
	})
	.error(function(data) {
		
	});

}]);