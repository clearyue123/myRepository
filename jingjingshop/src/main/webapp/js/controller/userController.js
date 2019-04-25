// 定义控制器:
app.controller("userController",function($scope,$controller,$http,userService){
	// AngularJS中的继承:伪继承
	$controller('baseController',{$scope:$scope});

	$scope.findAll = function(){
		// 向后台发送请求:
		userService.findAll().success(function(response){
			$scope.list = response;
		});
	}
	
	$scope.findByPage = function(page,rows){
		// 向后台发送请求获取数据:
		userService.findByPage(page,rows).success(function(response){
			$scope.paginationConf.totalItems = response.total;
			$scope.list = response.rows;
		});
	}
	
	$scope.findById = function(id){
		brandService.findById(id).success(function(response){
			$scope.entity = response;
		});
	}
	
    $scope.searchEntity={};
	
	// 假设定义一个查询的实体：searchEntity
	$scope.search = function(page,rows){
		// 向后台发送请求获取数据:
		brandService.search(page,rows,$scope.searchEntity).success(function(response){
			$scope.paginationConf.totalItems = response.total;
			$scope.list = response.rows;
		});
	}
	
});
