/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.search.web.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "elasticsearch", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class ProductSearch {
	@Id
	private Long spuId;
	/**
	 * 名称 分词字段
	 */
	@Field(type = FieldType.Text, analyzer = "ik_spu_name")
	private String spuName;
	@Field(type = FieldType.Keyword)
	private String keywords;
}
