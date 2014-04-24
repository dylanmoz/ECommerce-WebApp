angular.module('shopping', [])
.controller('ShoppingCtrl', ['$scope', '$http', '$log', 'shoppingHttp',
function($scope, $http, $log, shoppingHttp){
	$log.debug('ShoppingCtrl');
	
	$scope.shoppingcart = [];
	
	$http({method: 'GET', url: 'shopping'})
	.success(function(data) {
		$log.debug('GET shopping: ', data);
		$scope.shoppingcart = data.items;
	});
	
	$scope.quantities = [];
	for(var i = 1; i < 101; i++) {
		$scope.quantities.push(i);
	}
	
	$scope.total = function() {
		var sum = 0;
		angular.forEach($scope.shoppingcart, function(item, i) {
			sum += item.quantity * item.product.price;
		});
		
		return sum.toFixed(2);
	};
	
	$scope.saveItem = function(item) {
		$log.debug('saveItem(item), ', item);
		shoppingHttp.postToServer("UPDATE", {
			id : item.id,
			quantity : item.quantity,
			productId : item.product.id
		})
		.success(function(data) {
			
		});
	};
	
}])
.factory('shoppingHttp', ['$log', '$http',
function($log, $http) {
	
	return {
		postToServer : function(action, data) {
			return $http({method: 'POST', url: 'shopping', 
				params : {
					action : action,
					id : data.id,
					quantity : data.quantity,
					productId : data.productId,
				}
			});
		}
	};

}]);