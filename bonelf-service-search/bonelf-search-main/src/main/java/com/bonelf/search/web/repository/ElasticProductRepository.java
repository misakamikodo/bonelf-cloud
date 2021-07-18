/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.search.web.repository;

import com.bonelf.search.web.domain.entity.BonelfSpu;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;

@Repository
public interface ElasticProductRepository extends ElasticsearchRepository<BonelfSpu, Long> {
	/**
	 * 流方式查询
	 * @param spuType
	 * @return
	 */
	Streamable<BonelfSpu> findByKeywords(Short spuType);

	List<BonelfSpu> findBySpuType(Short spuType);

	//删除
	Long deleteBySpuCode(String spuCode);

	//异步查询
	@Async
	Future<BonelfSpu> findOneBySpuCode(String spuCode);

	@Async
	ListenableFuture<BonelfSpu> findOneBySpuType(Short spuType);

	/**
	 * match查询并设置operator
	 * @param title
	 * @param operator
	 * @return
	 */
	@Query("{\"match\": {\"title\":{ \"query\": \"?0\",\"operator\":\"?1\"}}}")
	BonelfSpu findByQueryExp(String title, String operator);

}
