# 报文解析工具

### 内容

使用RandomAccessFile(BufferedRandomAccessFile 摘自网络)读取文本文件.
读取文件后使用Forkjoin任务拆分.
简单使用,查看简单示例`SimpleTextMessageProcessorController`



### 如何使用
使用docker compose创建一个mysql容器,
	
	cd yourproject	
	docker-compose up

使用test下的`GeneratorServiceEntity`生成mybatis文件
然后启动springboot应用

启动成功,访问
![http://localhost:8080/person] (http://localhost:8080/person)

###