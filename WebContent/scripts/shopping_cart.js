angular.module('shopping', [])
.controller('ShoppingCtrl', ['$scope', '$http', '$log',
function($scope, $http, $log){
	$log.debug('ShoppingCtrl');
	
	$scope.shoppingcart = [];
	
	$scope.quantities = [];
	for(var i = 0; i < 101; i++) {
		$scope.quantities.push(i);
	}
	
	// Test data for display purposes
	$scope.shoppingcart.push({
		quantity : 2,
		product : {
			id: 1,
			name: 'Laptop',
			price: 899.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	$scope.shoppingcart.push({
		quantity : 5,
		product : {
			id: 3,
			name: 'TV',
			price: 650
		}
	});
	$scope.shoppingcart.push({
		quantity : 9,
		product : {
			id: 7,
			name: 'Couch',
			price: 2999.99
		}
	});
	
}]);