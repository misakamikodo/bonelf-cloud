##快速根据test生成一个服务

先通过idea的replace把 test 替换成模块名

再执行使用 RenameFile.java 修改文件名

##设置 mq 暂时禁用的办法（本想通过配置修改，但暂时没找到要排除哪个messaging的配置）：

1. RockermqConfig.java

注释 @EnableBinding 相关代码

2. ExampleSink.java

注释 Sink 相关代码

3. bonelf-test-app.xml (pom)

注释导入 bonelf-test-consumer

4. bonelf-test-base.xml (pom)

启用 bonelf-cloud 排除依赖 bonelf-mq