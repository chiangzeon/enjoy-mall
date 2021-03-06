package cn.enjoy.mall.web.controller;

import cn.enjoy.core.utils.response.HttpResponseBody;
import cn.enjoy.mall.service.IOrderService;
import cn.enjoy.mall.vo.OrderCreateVo;
import cn.enjoy.sys.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
* 订单服务
* */
@RestController
@RequestMapping("/api/order")
public class OrderController  extends BaseController {
    @Autowired
    private IOrderService orderService;
    /**
     * 创建订单
     * @param orderCreateVo
     * @return
     */
    @PostMapping("save")
    public HttpResponseBody save(@RequestBody OrderCreateVo orderCreateVo){
        Long orderId = orderService.createOrder(orderCreateVo,getSessionUserId());
        return HttpResponseBody.successResponse("ok",orderId);
    }

    /**
     * 分页查询订单
     * @param type 0-全部订单，1-全部有效订单，2-待支付，3-待收货，4-已关闭
     * @param keywords 订单号
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("search")
    public HttpResponseBody search(@RequestParam(required = false, defaultValue = "") Integer type,
                                   @RequestParam(required = false, defaultValue = "") String keywords,
                                   @RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return HttpResponseBody.successResponse("ok",
                orderService.searchListPage(type,keywords,page,pageSize,getSessionUserId()));
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @GetMapping("detail/{orderId}")
    public HttpResponseBody search(@PathVariable("orderId") Long orderId){
        return HttpResponseBody.successResponse("ok",orderService.selectMyOrderDetail(orderId,getSessionUserId()));
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("cancel")
    public HttpResponseBody cancel(@RequestParam Long orderId){
        orderService.selfCancel(orderId,getSessionUserId());
        return HttpResponseBody.successResponse("ok");
    }

    /**
     * 确认收货
     * @param orderId
     * @return
     */
    @PostMapping("confirmReceiveGoods")
    public HttpResponseBody confirmReceiveGoods(@RequestParam Long orderId){
        orderService.confirmReceiveGoods(orderId,getSessionUserId());
        return HttpResponseBody.successResponse("ok");
    }

    /**
     * 查询各状态的订单数
     * @param type 0-全部订单，1-全部有效订单，2-待支付，3-待收货，4-已关闭
     * @return
     */
    @GetMapping("queryOrderNum")
    public HttpResponseBody queryOrderNum(@RequestParam(required = false, defaultValue = "") Integer type) {
        return HttpResponseBody.successResponse("ok",orderService.queryOrderNum(type,getSessionUserId()));
    }

}
