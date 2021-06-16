## Elasticsearch 6.4.2 升级到 7.6.2

### 官方文档

https://www.elastic.co/guide/en/elasticsearch/reference/7.6/restart-upgrade.html

>  To upgrade directly to Elasticsearch 7.6.2 from versions 6.0-6.7, you must shut down all nodes in the cluster, upgrade each node to 7.6.2, and restart the cluster.

es 6.0-6.7 版本直接升级到 7.6.2，必须要停止所有集群节点，更新每个节点的版本到 7.6.2 ，然后重启集群



### 准备

1. ​	更新相应 es 代码操作，升级到 7.6.2 的es操作客户端；
2. ​	调整代码中es配置方式，升级后配置有所调整；
3. ​	如果你使用任何插件，请确保每个插件都有一个与 Elasticsearch 7.6.2 版本兼容的版本；
4. ​	升级生产环境之前在一个隔离的环境搭建好新集群；
5. ​	下载 7.6.2 版本以及相关 分词器（IK中文分词器）；

### 集群升级步骤

#### 禁用分片分配

集群中节点下线是，分片会重新分配，所有需要设置禁止分配重新分配。

```shell
curl -X PUT "localhost:9200/_cluster/settings?pretty" -H 'Content-Type: application/json' -d'
{
  "persistent": {
    "cluster.routing.allocation.enable": "primaries"
  }
}
'
```



#### 停止索引并执行同步刷新

加速数据刷盘

```shell
curl -X POST "localhost:9200/_flush/synced?pretty"
```



#### 关闭所有节点

```shell
sudo systemctl stop elasticsearch.service
```



#### 下载 7.6.2 安装包

1. 将zip或tar包解压缩到*新*目录。重点关注配置文件`config`目录和数据文件`data`目录。
2. 设置`ES_PATH_CONF`环境变量，指定旧集群中`config`目录和`jvm.options`文件的位置。或者将旧配置文件复制到新安装的配置文件目录。
3. 在`config/elasticsearch.yml`设置`path.data`，指向旧集群的数据文件目录。或者将旧数据目录复制到新安装的数据目录中。
4. 在`config/elasticsearch.yml`设置`path.logs`，指向您要存储日志的位置。如果未指定此设置，则日志将存储新的日志文件目录中。

#### 升级插件

使用`elasticsearch-plugin`脚本来安装每个已安装的 Elasticsearch 插件的升级版本。升级节点时，必须升级所有插件。

```
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.6.2/elasticsearch-analysis-ik-7.6.2.zip
```



#### 启动升级后的节点

等待所有节点加入群集，集群状态变为 yellow 



#### 重新启用分片分配

```shell
curl -X PUT "localhost:9200/_cluster/settings?pretty" -H 'Content-Type: application/json' -d'
{
  "persistent": {
    "cluster.routing.allocation.enable": null
  }
}
'
```









