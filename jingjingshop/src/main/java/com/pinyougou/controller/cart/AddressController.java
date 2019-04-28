package com.pinyougou.controller.cart;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pinyougou.pojo.TbAddress;
import com.pinyougou.service.user.AddressService;

import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author yue
 *
 */
@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbAddress> findAll(){			
		return addressService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return addressService.findPage(page, rows);
	}
	
	/**
	 * 修改
	 * @param address
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbAddress address){
		try {
			addressService.update(address);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbAddress findOne(Long id){
		return addressService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			addressService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbAddress address, int page, int rows  ){
		return addressService.findPage(address, page, rows);		
	}
	
	@RequestMapping("/findListByLoginUser")
	public List<TbAddress> findListByLoginUser(){
		//获取登陆用户
		String username = "zhaoliu";
		return addressService.findListByUserId(username);		
	}
	
	
	/**
	 * 小程序 用户id查收货地址
	 * @param userId
	 * @return
	 */
	@RequestMapping("/findListByUserId")
    public List<TbAddress> findListByUserId(String userId){
		return addressService.findListByUserId(userId);
	}	
	
	/**
	 * 小程序  新增地址
	 * @param userId
	 * @param provinceId
	 * @param cityId
	 * @param townId
	 * @param mobile
	 * @param address
	 * @param contact
	 * @param alias
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestParam(required = true, value = "userId")String userId,
			          @RequestParam(required = true, value = "provinceId")String provinceId,
			          @RequestParam(required = true, value = "cityId")String cityId,
			          @RequestParam(required = true, value = "townId")String townId,
			          @RequestParam(required = true, value = "mobile")String mobile,
			          @RequestParam(required = true, value = "address")String address,
			          @RequestParam(required = true, value = "contact")String contact,
			          @RequestParam(required = true, value = "alias")String alias){
		try {
			TbAddress tbAddress = new TbAddress();
			tbAddress.setUserId(userId);
			tbAddress.setProvinceId(provinceId);
			tbAddress.setCityId(cityId);
			tbAddress.setTownId(townId);
			tbAddress.setMobile(mobile);
			tbAddress.setAddress(address);
			tbAddress.setContact(contact);
			tbAddress.setAlias(alias);
			tbAddress.setCreateDate(new Date());
			addressService.add(tbAddress);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
}
