/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.pay.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bonelf.cicada.util.EnumUtil;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.pay.constant.enums.OrderTypeEnum;
import com.bonelf.pay.constant.enums.PayTypeEnum;
import com.bonelf.pay.util.WxPayUtil;
import com.bonelf.pay.web.domain.dto.PayDTO;
import com.bonelf.pay.web.domain.vo.WxPayVO;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.utils.XmlUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private BestPayService bestPayService;
	@Autowired
	private WxPayConfig wxPayConfig;
	// @Autowired
	// private ProductProvider productProvider;
	// @Autowired
	// private SupportProvider supportProvider;

	/**
	 * WX:
	 * {
	 * "nonceStr": "0e216mWMWCOMUqzm",
	 * "signType": "MD5",
	 * "packAge": "prepay_id=wx2211152287571655b9f3bf648064500000",
	 * "timeStamp": "1603336522",
	 * "paySign": "677150438D0C726B432F19741306A286"
	 * }
	 * @param payDto
	 * @return
	 */
	@ApiOperation(value = "支付", httpMethod = "POST")
	@PostMapping("")
	public Result<?> pay(@RequestBody PayDTO payDto) {
		// FIXME: 2020/12/1 payDto->payRequest装参数
		PayRequest payRequest = null;
		log.info("\n【发起支付】request={}", JSON.toJSONString(payRequest));
		PayResponse payResponse = bestPayService.pay(payRequest);
		log.info("\n【发起支付】response={}", JSON.toJSONString(payResponse));
		if (PayTypeEnum.ALI.getCode().equals(payDto.getPayType())) {
			log.info("\n【返回参数】resp={}", payResponse.getBody());
			return Result.ok(payResponse.getBody());
		} else {
			WxPayVO pay = WxPayVO.builder()
					.nonceStr(payResponse.getNonceStr())
					.signType(payResponse.getSignType())
					.packAge(payResponse.getPackAge())
					.timeStamp(payResponse.getTimeStamp())
					.paySign(payResponse.getPaySign())
					.build();
			log.info("\n【返回参数】resp={}", JSON.toJSONString(pay));
			return Result.ok(pay);
		}
	}

	@ApiIgnore
	@ApiOperation(value = "微信回调", notes = "退款和支付一个接口，微信退款可自定义回调接口，没有则是同一个")
	@GetMapping("/notify/wx")
	public String notifyWx(@RequestBody String notifyData) {
		Map<String, String> resp = XmlUtil.toMap(notifyData);
		if (resp.containsKey("out_refund_no")) {
			//退款回调
			//获取req_info解密后的out_refund_no(refundId)
			//通知用户定金原路返回，订单关闭，通知供货订单状态关闭
			log.info("\n【微信退款回调】respMap：" + JSON.toJSONString(resp));
			//签名校验
			String reqInfo;
			try {
				reqInfo = WxPayUtil.descrypt(resp.get("req_info"), wxPayConfig.getMchId());
			} catch (Exception e) {
				e.printStackTrace();
				return "<xml><return_code><![CDATA[FAIL]]</return_code><return_msg><![CDATA[error descrypt]]></return_msg></xml>";
			}
			Map<String, String> reqInfoMap = XmlUtil.toMap(reqInfo);
		} else {
			//支付回调
			PayResponse response = bestPayService.asyncNotify(notifyData);
			//通知订单支付成功
			JSONObject json = JSONObject.parseObject(response.getAttach());
			OrderTypeEnum orderType = Objects.requireNonNull(EnumUtil.getByCode(Integer.parseInt(json.getString("orderType")), OrderTypeEnum.class));
		}
		Long orderId = 1L;
		Long spuId = 1L;
		//通知 助手服务 关闭订单超时定时任务
		// supportProvider.send(MQSendTag.PRODUCT_PAID_TAG, orderId);
		//通知 商品服务 成交数据增加 FIXME 列表 数量
		// productProvider.send(MQSendTag.PRODUCT_PAID_TAG, spuId);
		return "<xml><return_code><![CDATA[SUCCESS]]</return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		//return "<xml><return_code><![CDATA[FAIL]]</return_code><return_msg><![CDATA[error descrypt]]></return_msg></xml>";
	}

	@ApiIgnore
	@ApiOperation(value = "阿里回调", notes = "退款和支付一个接口")
	@GetMapping("/notify/ali")
	public String notifyAli(@RequestBody String notifyData) {
		JSONObject json = JSON.parseObject(notifyData);
		if (json.containsKey("refund_fee") && json.containsKey("gmt_refund")) {
			//退款成功
			//添加退款流水
			return "success";
		} else {
			//支付回调
			PayResponse response = bestPayService.asyncNotify(notifyData);
			//通知订单支付成功
			//添加支付流水
			return "success";
		}
		//return "failure";
	}
}
