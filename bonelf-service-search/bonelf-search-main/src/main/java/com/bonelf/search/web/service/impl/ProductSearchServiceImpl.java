/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.search.web.service.impl;

import com.bonelf.search.web.domain.dto.ProductSearchDTO;
import com.bonelf.search.web.domain.entity.ProductSearch;
import com.bonelf.search.web.domain.vo.ProductSearchVO;
import com.bonelf.search.web.repository.ElasticProductRepository;
import com.bonelf.search.web.service.ProductSearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {
	@Autowired
	private ElasticProductRepository elasticProductRepository;
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	/**
	 * 搜索
	 * @param productSearchDto
	 * @return
	 */
	@Override
	public Page<ProductSearchVO> searchProduct(ProductSearchDTO productSearchDto) {
		NativeSearchQuery searchQuery = getSearchQuery(1, 10);
		Page<ProductSearch> searchResult1 = elasticProductRepository.search(searchQuery);
		//其中IndexCoordinates为封装索引库名称的不可变值对象, 用IndexCoordinates.of("索引库名")来构造该对象
		// SearchHits<ProductSearch> searchResult2 = elasticsearchRestTemplate.search(searchQuery, ProductSearch.class);
		//searchResult2.getTotalHits();
		//searchResult2.getSearchHits();
		return null;
	}

	/**
	 * 根据搜索词构造搜索查询语句
	 * 代码流程：
	 * - 精确查询
	 * - 模糊查询
	 * - 排序查询
	 * - 设置分页参数
	 * @param page 当前页码
	 * @param limit 每页大小
	 * @return
	 */
	private NativeSearchQuery getSearchQuery(int page, int limit) {
		//创建builder
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		/*
		 must
		 所有的语句都 必须（must） 匹配，与 AND 等价。
		 must_not
		 所有的语句都 不能（must not） 匹配，与 NOT 等价。
		 should
		 至少有一个语句要匹配，与 OR 等价。
		 trem
		 精确查找 与= 号等价。
		 match
		 模糊匹配 与like 等价。
		 */
		//TODO 字段精确匹配
		if ("platFrom" != null) {
			builder.must(QueryBuilders.termQuery("platFrom", "platFrom"));
		}
		//TODO 设置多字段组合模糊搜索
		if (StringUtils.isNotBlank("searchContent")) {
			builder.must(QueryBuilders.multiMatchQuery("searchContent", "username", "operation", "exceptionDetail"));
		}
		//设置排序
		FieldSortBuilder sort = SortBuilders.fieldSort("id").order(SortOrder.DESC);
		//设置分页
		Pageable pageable = PageRequest.of(page, limit);

		return new NativeSearchQueryBuilder()
				.withPageable(pageable)
				.withQuery(builder)
				.withSort(sort)
				.build();
	}
}
