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

    http://localhost:8080/person

模拟场景： 用户向第三方注册信息，返回token
#### 1初始化数据，例如1w条数据

    http://localhost:8080/insertBatch/10000
    
#### 2导出离线数据 
    
    http://localhost:8080/export

#### 3本地高速读取

    http://localhost:8080/local?filePath=testData.txt

#### 4数据库高速操作

    http://localhost:8080/db?filePath=testData.txt


查看日志，可以看到执行过程！

### 