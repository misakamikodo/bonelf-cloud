package com.bonelf.product.web.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bonelf.frame.base.util.redis.RedisUtil;
import com.bonelf.product.constant.CacheConstant;
import com.bonelf.product.constant.MQSendTag;
import com.bonelf.product.web.domain.ao.CalcPriceAO;
import com.bonelf.product.web.domain.bo.CalcPriceBO;
import com.bonelf.product.web.domain.entity.Spu;
import com.bonelf.product.web.domain.request.SpuClickRequest;
import com.bonelf.product.web.domain.vo.SpuVO;
import com.bonelf.product.web.mapper.SpuMapper;
import com.bonelf.product.web.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author bonelf
 * @since 2020-07-31
 */
@Service
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {
	@Autowired
	private RedisUtil redisUtil;
	// @Autowired
	// private SystemProvider systemProvider;

	@Override
	public SpuVO getDetail(Long spuId) {
		//统计存入商品表
		redisUtil.hincr(CacheConstant.SPU_CLICK_HASH, String.valueOf(spuId), 1);
		//发给系统按天统计所有商品的点击量
		// systemProvider.send(MQSendTag.SPU_CLICK, new SpuClickRequest(spuId, 1));
		return new SpuVO();
	}

	/**
	 * 商品售出
	 * @param spuId
	 * @return
	 */
	@Override
	public void spuSoldOut(long spuId) {
		//统计存入商品表
		redisUtil.hincr(CacheConstant.SPU_CLICK_HASH, String.valueOf(spuId), 1);
	}

	/**
	 * 更新库存
	 * @param skuId
	 */
	@Override
	public void updateStockBySkuId(long skuId) {
		this.baseMapper.updateSpuStockBySkuId(skuId);
	}

	/**
	 * 计算价格
	 * @param calcPriceBO
	 * @return
	 */
	@Override
	public CalcPriceAO calcPrice(CalcPriceBO calcPriceBO) {
		return null;
	}

	/**
	 * 合计点击量
	 * @return
	 */
	@Override
	public void sumStatistic() {
		redisUtil.<String, Integer>hmget(CacheConstant.SPU_CLICK_HASH).forEach((key, value) -> {
			Long spuId = Long.parseLong(key);
			baseMapper.update(new Spu(), Wrappers.<Spu>lambdaUpdate().setSql("click_count = click_count + " + value).eq(Spu::getSpuId, spuId));
			redisUtil.hdel(CacheConstant.SPU_CLICK_HASH, key);
		});
		redisUtil.<String, Integer>hmget(CacheConstant.SPU_SOLD_HASH).forEach((key, value) -> {
			Long spuId = Long.parseLong(key);
			baseMapper.update(new Spu(), Wrappers.<Spu>lambdaUpdate().setSql("sale = sale + " + value).eq(Spu::getSpuId, spuId));
			redisUtil.hdel(CacheConstant.SPU_CLICK_HASH, key);
		});
	}
}
