angular.module('categories', ['ngAnimate', 'ngSanitize', 'mgcrea.ngStrap'])
/*.controller('CategoryCtrl', ['$scope', '$http', '$log', 'categoryHttp',
function($scope, $http, $log, categoryHttp){
	
	$http({method: 'GET', url: 'products'})
	.success(function(data) {
		$log.debug(data);
		$scope.categories = data.categories;
	})
	.error(function(data) {
		
	});

}])*/
.controller('CategoryMgmtCtrl', ['$scope', '$http', '$log', '$modal', 'categoryHttp',
function($scope, $http, $log, $modal, categoryHttp){
	$log.debug("CategoryMgmtCtrl");
	
	$scope.categories = [];
	
	$scope.alerts = [];
	
	$http({method: 'GET', url: 'categories'})
	.success(function(data) {
		$log.debug(data);
		angular.forEach(data.categories, function(cat, i){
			$scope.categories.push(cat);
		});
		$scope.categories = $scope.categories ? $scope.categories : [];
	})
	.error(function(data) {
		$log.debug('HTTP ERROR');
	});
	
	$scope.modal = {title: 'Add Category'};
	
	$scope.formError = false;
	
	$scope.deleteCategory = function(cat) {
		categoryHttp.postToServer('DELETE', cat)
		.success(function(data) {
			if(data.didNotDelete) {
				$scope.alerts.push("The category was not deleted because it has products.");
			} else {
				angular.forEach($scope.categories, function(categ, i) {
					if(categ.id === cat.id) {
						$scope.categories.splice(i,1);
						return;
					}
				});
			}
		});
	};
	
	$scope.showUpdateForm = function(cat) {
		cat.showUpdateForm = true;
		cat.tempName = cat.name;
		cat.tempDescription = cat.description;
	};
	
	$scope.updateCategory = function(cat) {
		var duplicate = false;
		angular.forEach($scope.categories, function(c, i) {
			if(c !== cat && c.name === cat.tempName) duplicate = true;
		});
		if(duplicate) {
			$scope.alerts.push("Error occured while updating the category. Make sure the name is not a duplicate.");
			return;
		}
		categoryHttp.postToServer('UPDATE', cat)
		.success(function(data) {
			if(!data.updateError) {
				cat.showUpdateForm = false;
				cat.name = cat.tempName;
				cat.description = cat.tempDescription;
			} else {
				$scope.alerts.push("Error occured while updating the category. Make sure the name is not a duplicate.");
			}
		})
		.error(function(data) {
			
		});
	};
	
	$scope.submitNewCategory = function(name, description) {
		$log.debug('Submitting Add Category Form...');
		$log.debug('name: ', name);
		$log.debug('description: ', description);
		if(!name) {
			$scope.formError = true;
			return;
		}
		formError = false;
		
		var newCategory = {
			name: name,
			description: description,
			hasProducts: false
		};
		
		categoryHttp.postToServer('INSERT', {name: name, description: description})
		.success(function(data) {
			if(data.id == -1) {
				$log.debug('Insert failure');
				formError = true;
				return;
			}
			$log.debug('Insert success', data);
			newCategory.id = data.id;
			$scope.categories.push(newCategory);
		});
	};
	
	$scope.removeAlert = function(index) {
		$log.debug("Remove alert at index ", index);
		$scope.alerts.splice(index, 1);
	};
	
}])
.factory('categoryHttp', ['$log', '$http',
function($log, $http) {
	
	return {
		postToServer : function(action, data) {
			return $http({method: 'POST', url: 'categories', 
				params : {
					action: action,
					id: data.id,
					name: data.name,
					description: data.description
				}
			});
		}
	};

}]);