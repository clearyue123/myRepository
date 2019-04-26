// 定义服务层:
app.service("userService",function($http){

	this.search = function(page,rows,searchEntity){
		return $http.post("../user/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
});