# Getting Started
# 1、版本选择
    由于springboot采用得版本是2.0.1，所以对应的spring-data-elasticsearchjar包版本为3.0.6;
    ES安装版本为elasticsearch-6.8.12，本地单元测试通过，详细见bmw-hiwi-common-es模块单元测试代码

# 2、引入方式
- 引用子模块bmw-hiwi-common-es
- mvn依赖添加如下内容：      
```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```
- yml配置文件引入配置
```
spring:
  data:
    elasticsearch:
      cluster-nodes: 127.0.0.1:9300
      repositories:
        enabled: true
```

# 3、使用说明
- 1）为了使API尽量简洁易操作，将部分逻辑以springdata注解方式加入Entity类中
```
例如：Entity类注解使用 @Document(indexName = "user")标明索引名称或者类型，
如果不指定类型，默认类名全小写

注意：如果字段需要聚合或者排序，需要使用@Field(type = FieldType.text, fielddata = true)注解
并且fielddata=true

其他常用注解可以查看官网文档
```
- 2）service中自动注入EsCommonDao封装类类即可

# 4、EsCommonDao常用API指南
```
createIndex()：创建索引
deleteIndex():删除索引
saveOrUpdDoc():保存或者修改文档，如果ID存在为修改
deleteDoc()：根据id删除文档
getDco()：获取文档详情
batchSaveDao()：批量保存文档
queryList(SearchQuery searchQuery, Class<T> classz) 查询文档列表
    注意：列表查询ES不能查询全部，默认查10条数据，可以通过Pageable指定：
queryPage()：分页查询

备注：各种查询方式详细参考单元测试用例中的测试代码    
```


