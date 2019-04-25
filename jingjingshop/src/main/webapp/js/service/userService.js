// 定义服务层:
app.service("userService",function($http){
	this.findAll = function(){
		return $http.get("../user/findAll.do");
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../user/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	this.findById=function(id){
		return $http.get("../user/findOne.do?id="+id);
	}
});