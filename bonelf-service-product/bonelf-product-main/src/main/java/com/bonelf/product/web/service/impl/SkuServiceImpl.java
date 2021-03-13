package com.bonelf.product.web.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.product.web.domain.dto.SkuEditDTO;
import com.bonelf.product.web.domain.dto.SkuKeyDTO;
import com.bonelf.product.web.domain.dto.SkuKeyEditDTO;
import com.bonelf.product.web.domain.dto.SkuValueDTO;
import com.bonelf.product.web.domain.entity.Sku;
import com.bonelf.product.web.domain.entity.SkuKey;
import com.bonelf.product.web.domain.entity.SkuValue;
import com.bonelf.product.web.domain.vo.*;
import com.bonelf.product.web.mapper.SkuKeyMapper;
import com.bonelf.product.web.mapper.SkuMapper;
import com.bonelf.product.web.mapper.SkuValueMapper;
import com.bonelf.product.web.mapper.SpuMapper;
import com.bonelf.product.web.service.SkuKeyService;
import com.bonelf.product.web.service.SkuService;
import com.bonelf.product.web.service.SkuValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 规格服务
 * </p>
 * @author bonelf
 * @since 2020/11/13 14:32
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
	@Autowired
	private SkuKeyService skuKeyService;
	@Autowired
	private SkuValueService skuValueService;
	@Autowired
	private SpuMapper spuMapper;
	@Autowired
	private SkuKeyMapper skuKeyMapper;
	@Autowired
	private SkuValueMapper skuValueMapper;

	/**
	 * 规格列表
	 * @param spuId 商品编号
	 * @return
	 */
	@Override
	public List<SkuKeyApiVO> getSkuList(Long spuId) {
		List<SkuKey> skuKeys = this.skuKeyMapper.selectSkuList(spuId);
		return skuKeys.stream().map(item -> {
			SkuKeyApiVO skuKeyVO = new SkuKeyApiVO();
			skuKeyVO.setSkuName(item.getSkuName());
			skuKeyVO.setSkuKeyId(item.getSkuKeyId());
			if (item.getSkuValue() != null) {
				skuKeyVO.setSkuValues(item.getSkuValue().stream().map(skuValue -> {
					SkuValueApiVO skuValueVO = new SkuValueApiVO();
					skuValueVO.setSkuValueId(skuValue.getSkuValueId());
					skuValueVO.setSkuValueName(skuValue.getSkuValueName());
					skuValueVO.setVisible(true);
					return skuValueVO;
				}).collect(Collectors.toList()));
			}
			return skuKeyVO;
		}).collect(Collectors.toList());
	}

	/**
	 * 规格信息
	 * @param skuValueIds
	 * @return
	 */
	@Override
	public SkuApiVO getSkuInfoList(Long[] skuValueIds) {
		SkuApiVO result = new SkuApiVO();
		QueryWrapper<Sku> q = Wrappers.query();
		if (ArrayUtil.isNotEmpty(skuValueIds)) {
			for (Long skuValueId : skuValueIds) {
				q.gt("FIND_IN_SET(" + skuValueId + ",sku_value_ids)", 0);
			}
		}
		List<Sku> visibleSkuList = this.baseMapper.selectList(q);
		result.setInvisibleSku(new ArrayList<>());
		result.setVisibleSkuValueIds(new HashSet<>());
		for (Sku sku : visibleSkuList) {
			if (StrUtil.count(sku.getSkuValueIds(), StrUtil.COMMA) + 1 == skuValueIds.length) {
				//完全匹配
				result.setChosenSku(sku);
				//这里实际上可以break了，一般不存在选不全的规格有对应商品的情况。不过实际上visibleSkuList的长度应只有1
				continue;
			}
			for (String skuValueIdStr : sku.getSkuValueIds().split(StrUtil.COMMA)) {
				Long skuValueId = Long.parseLong(skuValueIdStr);
				if (ArrayUtil.contains(skuValueIds, skuValueId)) {
					continue;
				}
				//TODO redis库存
				if (sku.getStock() <= 0) {
					SkuValueApiVO skuValue = new SkuValueApiVO();
					skuValue.setSkuValueId(skuValueId);
					skuValue.setSkuValueName(skuValue.getSkuValueName());
					skuValue.setVisible(false);
					skuValue.setInvisibleReason("库存不足");
				} else {
					result.getVisibleSkuValueIds().add(skuValueId);
				}
			}
		}

		//查询不属于skuValueIds上级skuKey的其他skuValue，不在可用里的代表是不可用的下几级skuValue
		List<SkuValue> skuValues = skuValueMapper.selectSkuValueDiffFromSkuKey(skuValueIds);
		for (SkuValue skuValue : skuValues) {
			if (CollectionUtil.contains(result.getVisibleSkuValueIds(), skuValue.getSkuValueId())) {
				continue;
			}
			SkuValueApiVO skuValueVo = new SkuValueApiVO();
			skuValueVo.setSkuValueId(skuValue.getSkuValueId());
			skuValueVo.setSkuValueName(skuValue.getSkuValueName());
			skuValueVo.setVisible(false);
			skuValueVo.setInvisibleReason("无效规格");
			//此处重写了hashCode equals, invisibleReason不导致重复
			result.getInvisibleSku().add(skuValueVo);
		}
		return result;
	}

	/**
	 * 规格定义编辑
	 * @param spuId
	 * @return
	 */
	@Override
	public List<SkuKeyMgrVO> getSkuKey(Long spuId) {
		List<SkuKey> skuKeys = skuKeyService.selectSkuList(spuId);
		return skuKeys.stream().map(item -> {
			SkuKeyMgrVO skuKeyVO = new SkuKeyMgrVO();
			skuKeyVO.setSkuName(item.getSkuName());
			skuKeyVO.setSkuKeyId(item.getSkuKeyId());
			if (item.getSkuValue() != null) {
				skuKeyVO.setSkuValues(item.getSkuValue().stream().map(skuValue -> {
					SkuValueMgrVO skuValueVO = new SkuValueMgrVO();
					skuValueVO.setSkuValueId(skuValue.getSkuValueId());
					skuValueVO.setSkuValueName(skuValue.getSkuValueName());
					return skuValueVO;
				}).collect(Collectors.toList()));
			}
			return skuKeyVO;
		}).collect(Collectors.toList());
	}

	/**
	 * 规格信息编辑
	 * @param spuId
	 * @return
	 */
	@Override
	public List<Sku> getSku(Long spuId) {
		//生成笛卡尔积后返回给前端
		List<Sku> skuDb = this.baseMapper.selectList(Wrappers.<Sku>lambdaQuery().eq(Sku::getSpuId, spuId).orderByAsc(Sku::getSkuValueIds));
		List<SkuKey> skuKeyDb = skuKeyService.selectSkuList(spuId);
		//求skuKeyDb的笛卡尔积 如果与数据库中匹配则填充
		List<Sku> result = cartesian(skuKeyDb);
		//数据处理
		for (Sku sku : skuDb) {
			List<Long > skuValueIds = new ArrayList<>();
			for (String s : sku.getSkuValueIds().split(StrUtil.COMMA)) {
				skuValueIds.add(Long.parseLong(s));
			}
			sku.setSkuValueIdList(skuValueIds);
			Collections.sort(sku.getSkuValueIdList());
		}
		//统一插入和生成的顺序和规则可以调高效率
		for (Sku sku : result) {
			//此为前端传递参数 的 包装
			sku.setSkuValueIds(CollectionUtil.join(sku.getSkuValueIdList(), StrUtil.COMMA));
			Collections.sort(sku.getSkuValueIdList());
			//找到数据库和sku相等的
			for (int i = 0; i < skuDb.size(); i++) {
				Sku sku1 = skuDb.get(i);
				//判断skuDb是否有对应sku 插入数据
				if (sku.getSkuValueIdList().equals(sku1.getSkuValueIdList())) {
					BeanUtil.copyProperties(sku1, sku);
					//删除下次不遍历，如果顺序正确 往往是第一个或者找不到
					skuDb.remove(i);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 求笛卡尔积
	 * @param skuKeyDb
	 * @return
	 */
	private List<Sku> cartesian(List<SkuKey> skuKeyDb) {
		List<Sku> values = new LinkedList<>();
		int[] x = new int[skuKeyDb.size()];

		boolean flag;
		do {
			/*一种组合形式**/
			Sku objs = new Sku();
			objs.setSkuValueIdList(new ArrayList<>());
			for (int looper = 0; looper < skuKeyDb.size(); looper++) {
				SkuKey skuKey = skuKeyDb.get(looper);
				SkuValue skuValue = skuKey.getSkuValue().get(x[looper]);
				if (CollectionUtil.isNotEmpty(objs.getSkuValueIdList())) {
					objs.setSpecs(objs.getSpecs() + ";" + skuKey.getSkuName() + StrUtil.COLON + skuValue.getSkuValueName());
				} else {
					objs.setSpecs(skuKey.getSkuName() + StrUtil.COLON + skuValue.getSkuValueName());
				}
				objs.getSkuValueIdList().add(skuValue.getSkuValueId());
			}
			flag = nextPermutation(x, skuKeyDb);
			values.add(objs);
		} while (!flag);
		/*所有组合形式**/
		return values;
	}

	/**
	 * 下一个元素
	 * @param x
	 * @param nArray
	 * @return 是否循环完对应数组的元素
	 */
	private boolean nextPermutation(int[] x, List<SkuKey> nArray) {
		boolean carry = false;
		for (int looper = nArray.size() - 1; looper >= 0; looper--) {
			if (x[looper] + 1 == nArray.get(looper).getSkuValue().size()) {
				carry = true;
				x[looper] = 0;
			} else {
				x[looper] = x[looper] + 1;
				carry = false;
				return false;
			}
		}
		return carry;
	}

	/**
	 * 保存规格定义
	 * @param sku
	 */
	@Override
	public void saveSkuKey(SkuKeyEditDTO sku) {
		List<SkuKey> skuKeyDb = skuKeyService.selectSkuList(sku.getSpuId());
		//保存定义
		this.saveSkuKeyAndValue(sku, skuKeyDb);
	}

	/**
	 * 保存Sku
	 * @param skuDto
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveSku(SkuEditDTO skuDto) {
		//保存数据
		List<Sku> skus = this.baseMapper.selectList(Wrappers.<Sku>lambdaQuery().eq(Sku::getSpuId, skuDto.getSpuId()));
		this.saveSku(skuDto, skus);
		spuMapper.updateSpuBySku(skuDto.getSpuId());
	}

	/**
	 * 保存规格
	 * @param skuDto
	 * @param skus
	 */
	private void saveSku(SkuEditDTO skuDto, List<Sku> skus) {
		//清理空数据
		skuDto.getSkus().removeIf(item -> item.getSellPrice() == null);

		List<Sku> skuUpdateBatch = new ArrayList<>();
		List<Sku> skuSaveBatch = new ArrayList<>();
		for (Sku sku : skuDto.getSkus()) {
			if (sku.getSkuValueIds() == null) {
				throw new BonelfException("数据异常");
			}
			//新家的id确定不了 得分开接口
			sku.setSpecs(skuValueMapper.selectSpecsBySkuValueIds(StrUtil.splitToLong(sku.getSkuValueIds(), StrUtil.COMMA)));
			if (sku.getSkuId() != null) {
				//更新
				skuUpdateBatch.add(sku);
				skus.removeIf(item -> sku.getSkuId().equals(item.getSkuId()));
			} else {
				//添加
				skuSaveBatch.add(sku);
			}
		}
		if (CollectionUtil.isNotEmpty(skuSaveBatch)) {
			this.saveBatch(skuSaveBatch);
		}
		if (CollectionUtil.isNotEmpty(skuUpdateBatch)) {
			this.updateBatchById(skuUpdateBatch);
		}
		if (CollectionUtil.isNotEmpty(skus)) {
			this.removeByIds(skus.stream().map(Sku::getSkuId).collect(Collectors.toList()));
		}
	}

	/**
	 * 保存规格定义
	 * @param skuDto
	 * @param skuKeyDb
	 */
	private void saveSkuKeyAndValue(SkuKeyEditDTO skuDto, List<SkuKey> skuKeyDb) {
		List<SkuKey> skuKeyUpdateBatch = new ArrayList<>();
		List<SkuValue> skuValueUpdateBatch = new ArrayList<>();
		List<Long> deleteBatchSkuValueIds = new ArrayList<>();
		for (SkuKeyDTO skuKey : skuDto.getSkuKeys()) {
			if (skuKey.getSkuKeyId() != null) {
				//修改
				SkuKey skuKeyUpdate = new SkuKey();
				BeanUtil.copyProperties(skuKey, skuKeyUpdate);
				skuKeyUpdateBatch.add(skuKeyUpdate);
				//待保存 的数据
				List<SkuValue> skuValueSaveBatch = new ArrayList<>();
				for (SkuValueDTO skuValue : skuKey.getSkuValues()) {
					SkuValue skuValueUpdate = new SkuValue();
					BeanUtil.copyProperties(skuValue, skuValueUpdate);
					if (skuValue.getSkuValueId() != null) {
						//更新
						skuValueUpdateBatch.add(skuValueUpdate);
						skuKey.getSkuValues().removeIf(item -> skuValue.getSkuValueId().equals(item.getSkuValueId()));
					} else {
						//添加
						skuValueSaveBatch.add(skuValueUpdate);
					}
				}
				if (CollectionUtil.isNotEmpty(skuValueSaveBatch)) {
					skuValueService.saveBatch(skuValueSaveBatch);
				}
				for (SkuValueDTO skuValue : skuKey.getSkuValues()) {
					deleteBatchSkuValueIds.add(skuValue.getSkuValueId());
				}
				skuKeyDb.removeIf(item -> skuKey.getSkuKeyId().equals(item.getSkuKeyId()));
			} else {
				//添加
				SkuKey skuKeySave = new SkuKey();
				BeanUtil.copyProperties(skuKey, skuKeySave);
				skuKeySave.setSpuId(skuDto.getSpuId());
				skuKeyService.save(skuKeySave);
				List<SkuValue> skuValueSaveBatch = new ArrayList<>();
				for (SkuValueDTO skuValue : skuKey.getSkuValues()) {
					SkuValue skuValueSave = new SkuValue();
					BeanUtil.copyProperties(skuValue, skuValueSave);
					skuValueSave.setSkuKeyId(skuKeySave.getSkuKeyId());
					skuValueSaveBatch.add(skuValueSave);
				}
				if (CollectionUtil.isNotEmpty(skuValueSaveBatch)) {
					skuValueService.saveBatch(skuValueSaveBatch);
				}
			}
		}
		if (CollectionUtil.isNotEmpty(skuKeyUpdateBatch)) {
			skuKeyService.updateBatchById(skuKeyUpdateBatch);
		}
		if (CollectionUtil.isNotEmpty(skuValueUpdateBatch)) {
			skuValueService.updateBatchById(skuValueUpdateBatch);
		}
		if (CollectionUtil.isNotEmpty(skuKeyDb)) {
			skuKeyService.removeByIds(skuKeyDb.stream().map(SkuKey::getSkuKeyId).collect(Collectors.toList()));
		}
		//删除了SkuKey的SkuValue补充删除
		for (SkuKey skuKey : skuKeyDb) {
			for (SkuValue skuValue : skuKey.getSkuValue()) {
				deleteBatchSkuValueIds.add(skuValue.getSkuValueId());
			}
		}
		if (CollectionUtil.isNotEmpty(deleteBatchSkuValueIds)) {
			skuValueService.removeByIds(deleteBatchSkuValueIds);
		}
	}
}
