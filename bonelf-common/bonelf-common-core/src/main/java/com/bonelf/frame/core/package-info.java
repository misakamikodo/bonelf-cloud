/**
 * <p>
 * 公用模块
 *
 * 注意防止两个包（公共包和项目包）的bean冲突
 *
 * 公共调试接口写在gateway服务中，也可以写在test中，如果你认为test该部署
 *
 * 因为gateway并不是service 所以没有以来common 在constant和util有些重复定义了，修改时确认下gateway中对应修改
 * </p>
 * @author bonelf
 * @since 2020/10/4 23:02
 */
package com.bonelf.frame.core;