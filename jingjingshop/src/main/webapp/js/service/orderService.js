// 定义服务层:
app.service("orderService",function($http){
	
	//分页 查询
	this.search = function(page,rows,searchEntity){
		return $http.post("../order/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	//新增功能
	this.save = function(){
		return $http.post("../order/save.do",entity);
	}
	
	//新增功能
	this.update = function(){
		return $http.post("../order/update.do",entity);
	}
	
});