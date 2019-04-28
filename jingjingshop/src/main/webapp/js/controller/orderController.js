// 定义控制器:
app.controller("orderController",function($scope,$controller,$http,orderService){
	// AngularJS中的继承:伪继承
	$controller("baseController",{$scope:$scope});
	
	//分页 条件查询
    $scope.searchEntity={};
	// 假设定义一个查询的实体：searchEntity
	$scope.search = function(page,rows){
		// 向后台发送请求获取数据:
		orderService.search(page,rows,$scope.searchEntity).success(function(response){
			$scope.paginationConf.totalItems = response.total;
			$scope.list = response.rows;
		});
	}
	
	//新增功能
	$scope.save = function(){
		// 区分是保存还是修改
		var object;
		if($scope.entity.id != null){
			// 更新
			object = orderService.update($scope.entity);
		}else{
			// 保存
			object = orderService.save($scope.entity);
		}
		object.success(function(response){
			// {flag:true,message:xxx}
			// 判断保存是否成功:
			if(response.flag==true){
				// 保存成功
				alert(response.message);
				$scope.reloadList();
			}else{
				// 保存失败
				alert(response.message);
			}
		});
	}
	
	
});
