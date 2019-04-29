package com.pinyougou.controller.cart;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.service.order.OrderService;

import entity.PageResult;
import entity.Result;
import util.IdWorker;
/**
 * controller
 * @author yue
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbOrder> findAll(){			
		return orderService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return orderService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param order
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbOrder order){
		
		//获取当前登录人账号
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		order.setUserId(username);
		order.setSourceType("2");//订单来源  PC
		
		try {
			orderService.add(order);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param order
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbOrder order){
		try {
			orderService.update(order);
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
	public TbOrder findOne(Long id){
		return orderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			orderService.delete(ids);
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
	public PageResult search(@RequestBody TbOrder order, int page, int rows  ){
		return orderService.findPage(order, page, rows);		
	}
	
	
	/**
	 * 小程序接口  订单列表查询
	 * @param userId
	 * @param status
	 * @return
	 */
	@RequestMapping("/ordersList")
	public List<Map<String,Object>> OrdersList(@RequestParam(required = true, value = "userId")String userId,
			                  @RequestParam(required = true, value = "status")String status){
		return orderService.orderList(userId, status);
	}
	
	/**
	 * 小程序接口 订单删除
	 * @param orderId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/delOrder")
	public Result delOrder(@RequestParam(required = true, value = "orderId")String orderId,
            @RequestParam(required = true, value = "userId")String userId){
		orderService.delOrderById(Long.parseLong(orderId));
		return new Result(true,"删除成功");
	}
	
	/**
	 * 小程序接口 订单详情
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param status
	 * @return
	 */
	public Map<String,Object> showOrderDetail(
			          @RequestParam(required=true,value="userId")String userId,
			          @RequestParam(required=true,value="userType")String userType,
			          @RequestParam(required=true,value="orderId")String orderId,
			          @RequestParam(required=true,value="status")String status
			          ){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("userType", userType);
		paramMap.put("orderId", orderId);
		paramMap.put("status", status);
		return orderService.selectOrderDetail(paramMap);
	}
	
	/**
	 * 小程序接口 
	 * operateFlag 
	 *   0:取消订单
	 *   1:提醒发货
	 *   2:已收货
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param operateFlag
	 * @return
	 */
	public Result oprateOrder(
			  @RequestParam(required=true,value="userId")String userId,
	          @RequestParam(required=true,value="userType")String userType,
	          @RequestParam(required=true,value="orderId")String orderId,
	          @RequestParam(required=true,value="operateFlag")String operateFlag){
		if("0".equals(operateFlag)){//取消订单
			orderService.delOrderById(Long.parseLong(userId));
			return new Result(true, "已取消订单");
		}else if("1".equals(operateFlag)){//提醒发货
			return new Result(true, "已提醒发货");
		}else{//已收货
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("ORDERID", orderId);
			paramMap.put("STATUS", "5");//交易成功
			orderService.updateStatusById(paramMap);
			return new Result(true, "已收货");
		}
	}
	
	/**
	 * 小程序接口 支付成功
	 * 支付成功后修改订单状态
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param message
	 * @return
	 */
	public Result payOrder( 
			  @RequestParam(required=true,value="userId")String userId,
	          @RequestParam(required=true,value="userType")String userType,
	          @RequestParam(required=true,value="orderId")String orderId,
	          @RequestParam(required=true,value="message")String message){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("ORDERID", orderId);
		paramMap.put("STATUS", "2");//交易成功
		orderService.updateStatusById(paramMap);
		return new Result(true, "支付成功");
	}
	
	/**
	 * 订单支付成功
	 * @param userId
	 * @param userType
	 * @param listParams
	 * @return
	 */
	public Result createOrder(
			@RequestParam(required=true,value="userId")String userId,
	        @RequestParam(required=true,value="userType")String userType,
	        @RequestParam(required=true,value="receiverAreaName")String receiverAreaName,
	        @RequestParam(required=true,value="receiverMobile")String receiverMobile,
	        @RequestParam(required=true,value="receiver")String receiver,
	        @RequestParam(required=true,value="listOrderParams")List<Map<String,Object>> listOrderParams){
		TbOrder tbOrder = new TbOrder();
		TbOrderItem tbOrderItem = new TbOrderItem();
		//设置orderId
		IdWorker idWorker = new IdWorker(0,0);
		long orderId = idWorker.nextId();
		tbOrder.setOrderId(orderId);
		tbOrder.setPaymentType("1");//支付类型
		tbOrder.setStatus("1");//未付款 
		tbOrder.setCreateTime(new Date());//下单时间
		tbOrder.setUpdateTime(new Date());//更新时间
		tbOrder.setUserId(userId);//当前用户
		tbOrder.setReceiverAreaName(receiverAreaName);//收货人地址
		tbOrder.setReceiverMobile(receiverMobile);//收货人电话
		tbOrder.setReceiver(receiver);//收货人
		orderService.add(tbOrder);
		for(Map<String,Object> orderParams:listOrderParams){
			long orderItemId = idWorker.nextId();
			Long itemId = (Long)orderParams.get("itemId");
			Long goodsId = (Long)orderParams.get("goodsId");
			String sellerId = (String)orderParams.get("sellerId");
			Integer num = (Integer)orderParams.get("num");
			String picPath = (String)orderParams.get("picPath");
			Double price = (Double)orderParams.get("price");
			Double totalFee = price*num;
			tbOrderItem.setId(orderItemId);
			tbOrderItem.setItemId(itemId);
			tbOrderItem.setGoodsId(goodsId);
			tbOrderItem.setSellerId(sellerId);
			tbOrderItem.setNum(num);
			tbOrderItem.setPicPath(picPath);
			tbOrderItem.setPrice(new BigDecimal(price.toString()));
			tbOrderItem.setTotalFee(new BigDecimal(totalFee.toString()));
			orderItemMapper.insert(tbOrderItem);
		}
		return new Result(true, "订单支付成功");
	}
}
