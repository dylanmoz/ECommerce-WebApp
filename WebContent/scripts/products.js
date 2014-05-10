angular.module('products', ['ngAnimate', 'ngSanitize', 'mgcrea.ngStrap', 'shopping'])
.controller('ProductCtrl', ['$scope', '$http', '$log', '$modal', '$window', 'productHttp', 'shoppingHttp',
function($scope, $http, $log, $modal, $window, productHttp, shoppingHttp){
	
	$scope.role = $window.role;
	
	$scope.modal = {title: 'Add Product'};
	
	$scope.filterCategory = null;
	
	$scope.alerts = [];
	
	$scope.removeAlert = function(index) {
		$log.debug("Remove alert at index ", index);
		$scope.alerts.splice(index, 1);
	};
	
	// Retrieve category information
	$http({method: 'GET', url: 'categories'})
	.success(function(data) {
		$log.debug('GET categories: ', data);
		$scope.categories = data.categories;
	});
	
	// Retrieve product information
	$http({method: 'GET', url: 'products'})
	.success(function(data) {
		$log.debug('GET products: ', data);
		$scope.products = data.products;
	});
	
	$scope.isNull = function(obj) {
		return obj === null;
	};
	
	$scope.isCategoryFilter = function(cat) {
		return $scope.filterCategory === cat;
	};
	
	$scope.filterByCategory = function(cat) {
		$scope.filterCategory = cat;
	};
	
	$scope.showUpdateForm = function(p) {
		$log.debug('Show update form.');
		p.showUpdateForm = true;
		p.tempName = p.name;
		angular.forEach($scope.categories, function(cat, i){
			if(cat.name === p.category) {
				$log.debug('Found matching category: ', cat);
				$log.debug('for product: ', p);
				p.tempCategory = cat;
				return;
			}
		});
		p.tempSKU = p.sku;
		p.tempPrice = p.price;
	};
	
	$scope.updateProduct = function(p) {
		$scope.formSuccess = false;	
		$scope.tempnameError = false; $scope.tempskuError = false; $scope.temppriceError = false; $scope.tempcategoryError = false;
		if(!p.tempName) {
			$scope.tempnameError = true;
			return;
		} else if(!p.tempCategory) {
			$scope.tempcategoryError = true;
			return;
		} else if(!p.tempSKU) {
			$scope.tempskuError = true;
			return;
		} else if(!p.tempPrice || !isFinite(String(p.tempPrice)) || parseInt(p.tempPrice) < 0) {
			$scope.temppriceError = true;
			return;
		} 
		
		var duplicate = false;
		angular.forEach($scope.products, function(prod, i) {
			if(prod !== p && prod.sku === p.tempSKU) duplicate = true;
		});
		if(duplicate) {
			$scope.alerts.push("Error updating product. Please make sure the SKU is unique.");
			return;
		}
		
		$log.debug('No errors in form.');
		$scope.nameError = false; $scope.skuError = false; $scope.priceError = false; $scope.categoryError = false;
		
		
		p.showUpdateForm = false;
		p.name = p.tempName;
		p.category = p.tempCategory.name;
		p.categoryId = p.tempCategory.id;
		p.sku = p.tempSKU;
		p.price = p.tempPrice;
		
		productHttp.postToServer('UPDATE', p)
		.success(function(data){
			
		})
		.error(function(data) {
			
		});
	};
	
	$scope.deleteProduct = function(p) {
		productHttp.postToServer('DELETE', p)
		.success(function(data) {
			angular.forEach($scope.products, function(product, i) {
				if(product.id === p.id) {
					$scope.products.splice(i,1);
					return;
				}
			});
		})
		.error(function(data) {
			
		});
	};
	
	$scope.submitNewProduct = function(name, category, sku, price) {
		$log.debug('Submitting Add Product Form...');
		$log.debug('name: ', name);
		$log.debug('category: ', category);
		$log.debug('sku: ', sku);
		$log.debug('price: ', price);
		
		$scope.formSuccess = false;	
		$scope.duplicateError = false;
		$scope.nameError = false; $scope.skuError = false; $scope.priceError = false; $scope.categoryError = false;
		if(!name) {
			$scope.nameError = true;
			return;
		} else if(!category) {
			$scope.categoryError = true;
			return;
		} else if(!sku) {
			$scope.skuError = true;
			return;
		} else if(!price || !isFinite(String(price)) || parseInt(price) < 0) {
			$scope.priceError = true;
			return;
		} 
		
		var duplicate = false;
		angular.forEach($scope.products, function(prod, i) {
			if(prod.sku === sku) duplicate = true;
		});
		if(duplicate) {
			$scope.duplicateError = true;
			return;
		}
		
		$log.debug('No errors in form.');
		$scope.nameError = false; $scope.skuError = false; $scope.priceError = false; $scope.categoryError = false;
		
		
		
		var newProduct = {
			name : name,
			category : category.name,
			categoryId : category.id,
			sku : sku,
			price : price
		};
		
		productHttp.postToServer('INSERT', newProduct)
		.success(function(data) {
			if(data.id == -1 || !data) {
				$log.debug('Insert failure');
				$scope.formError = true;
				return;
			}
			$log.debug('Insert success', data);
			$scope.formSuccess = true;
			newProduct.id = data.id;
			$scope.products.push(newProduct);
		});
	};
	
	$scope.addToShoppingCart = function(product) {
		$log.debug('addToShoppingCart');
		shoppingHttp.postToServer("INSERT", {
			productId : product.id
		});
	};

}])
.factory('productHttp', ['$log', '$http',
function($log, $http) {
	
	return {
		postToServer : function(action, data) {
			return $http({method: 'POST', url: 'products', 
				params : {
					action : action,
					id : data.id,
					name : data.name,
					categoryId : data.categoryId,
					sku : data.sku,
					price : data.price
				}
			});
		}
	};

}])
.filter('categoryFilter', [function(){
	return function(input, category) {
		if(!category) return input;
		
		var filtered = [];
		angular.forEach(input, function(item, i) {
			if(item.category === category.name) {
				filtered.push(item);
			}
		});
		return filtered;
	}
}]);