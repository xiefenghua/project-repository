## Docker 学习文档

### Docker 概述

#### Docker为什么出现？

Docker的思想来自于集装箱；

JRE – 多个应用（端口冲突）-- 原来都是交叉的！

隔离 ： Docker核心思想，打包装箱，每个箱子是互相隔离的。

Docker通过隔离机制，可以将服务器利用到极致！

#### Docker的历史

Docker 是 [PaaS](https://baike.baidu.com/item/PaaS) 提供商 dotCloud 开源的一个基于 [LXC](https://baike.baidu.com/item/LXC) 的高级容器引擎，源代码托管在 [Github](https://baike.baidu.com/item/Github) 上, 基于[go语言](https://baike.baidu.com/item/go语言)并遵从Apache2.0协议开源。

#### Docker能做什么

> 之前的虚拟机技术与Docker技术对比

![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b932d689259b4f3d8902f1ef672135dc~tplv-k3u1fbpfcp-watermark.image)

虚拟机技术的缺点：

1. 资源占用十分多
2. 冗余步骤多
3. 启动很慢

> 容器化技术

容器化技术不是模拟一个完整的操作系统

比较Docker和虚拟机技术的不同：

- 传统虚拟机，虚拟出一套硬件，运行一个完整的操作系统，然后在这个系统上安装和运行软件
- Docker容器内的应用直接运行在宿主机的内核，容器没有自己的内核，也没有虚拟我们的硬件，所以就轻便了
- 每个容器间是相互隔离，每个容器都有一个属于自己的文件系统，互不影响

> DevOps（开发、运维）

**应用更快速的交付和部署**

传统：一堆帮助文档，安装程序

Docker : 打包镜像发布测试，一键运行

**更快捷的升级和扩容**

使用了Docker之后，我们部署应用就和搭积木一样！

项目打包为一个镜像

**更简单的系统运维**

在容器化之后，我们的开发，测试环境都是高度一致的

**更高效的计算资源利用**

Docker是内核级别的虚拟化，可以在一个物理机上运行很多的容器实例，服务器的性能可以被压榨到极致。

### Docker 相关概念

> Docker基本组成

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/05e02e6204554fadb3d23f00e7861dee~tplv-k3u1fbpfcp-watermark.image)

> 镜像

docker镜像就好比是一个模板，可以通过这个模板来创建容器服务。

tomcat镜像 ==> run ==> tomcat容器（提供服务）

通过这个镜像可以创建多个容器（最终运行或者项目运行都是在容器中的）。



> 容器

Docker利用容器技术，独立运行一个或者一组应用，是通过镜像来创建的。

有启动，停止，删除等Linux基本指令！

目前就可以把这个容器理解为一个简易的Linux系统



> 仓库

仓库就是存放镜像的地方，分为私有仓库和公有仓库；

Docker Hub（默认是国外的）

阿里云…都有容器服务器（配置镜像加速！）



> 系统环境查看

```shell
# 查看系统内核，是3.10以上的
uname -r
3.10.0-957.21.3.el7.x86_64


# 查看系统版本
cat /etc/os-release 
NAME="CentOS Linux"
VERSION="7 (Core)"
ID="centos"
ID_LIKE="rhel fedora"
VERSION_ID="7"
PRETTY_NAME="CentOS Linux 7 (Core)"
ANSI_COLOR="0;31"
CPE_NAME="cpe:/o:centos:centos:7"
HOME_URL="https://www.centos.org/"
BUG_REPORT_URL="https://bugs.centos.org/"

CENTOS_MANTISBT_PROJECT="CentOS-7"
CENTOS_MANTISBT_PROJECT_VERSION="7"
REDHAT_SUPPORT_PRODUCT="centos"
REDHAT_SUPPORT_PRODUCT_VERSION="7"

```


### docker 安装
官方文档：https://docs.docker.com/engine/install/centos/

```shell
# 1、卸载旧的版本
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
                  
# 2、需要的安装包
sudo yum install -y yum-utils

# 3、设置镜像的仓库
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

# 更新yum软件包索引， centos 8 去掉fast即可
yum makecache fast


# 4、安装docker相关的源 docker-ce 社区 ee 企业版
yum install docker-ce docker-ce-cli containerd.io

# 5、启动docker
systemctl start docker

# 6、使用docker version 查看是否安装成功

# 7、docker run hello-world

# 8、查看下载的这个 hello-world 镜像

```

>  配置镜像加速

```shell
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://s9uopd4b.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```


![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2ba67dec63d14a989909b34721231d6e~tplv-k3u1fbpfcp-watermark.image)

### Docker 常用命令

``` shell
docker [option] --help
```
![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/b532d9e52f5440ebbf187122fd1a9fea~tplv-k3u1fbpfcp-watermark.image)

### Docker 镜像

#### 镜像是什么

镜像是一种轻量级、可执行的独立软件包，用来打包软件运行环境和基于运行环境开发的软件，他包含运行某个软件所需的所有内容，包括代码、运行时库、环境变量和配置文件。

所有的应用，直接打包docker镜像，就可以直接跑起来！

如何得到镜像：

- 从远程仓库下载
- 拷贝
- 自己制作一个镜像 DockerFile

#### Docker镜像加载原理

> UnionFs （联合文件系统)

UnionFs（联合文件系统）：Union文件系统（UnionFs）是一种分层、轻量级并且高性能的文件系统，他支持对文件系统的修改作为一次提交来一层层的叠加，同时可以将不同目录挂载到同一个虚拟文件系统下（ unite several directories into a single virtual filesystem)。Union文件系统是 Docker镜像的基础。镜像可以通过分层来进行继承，基于基础镜像（没有父镜像），可以制作各种具体的应用镜像
特性：一次同时加载多个文件系统，但从外面看起来，只能看到一个文件系统，联合加载会把各层文件系统叠加起来，这样最终的文件系统会包含所有底层的文件和目录

> Docker镜像加载原理

docker的镜像实际上由一层一层的文件系统组成，这种层级的文件系统UnionFS。
boots(boot file system）主要包含 bootloader和 Kernel, bootloader主要是引导加 kernel, Linux刚启动时会加载bootfs文件系统，在 Docker镜像的最底层是 bootfs。这一层与我们典型的Linux/Unix系统是一样的，包含boot加载器和内核。当boot加载完成之后整个内核就都在内存中了，此时**内存的使用权**已由 bootfs转交给内核，此时系统也会卸载bootfs。
rootfs（root file system),在 bootfs之上。包含的就是典型 Linux系统中的/dev,/proc,/bin,/etc等标准目录和文件。 rootfs就是各种不同的操作系统发行版，比如 Ubuntu, Centos等等。

![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/cca446014f524ba399a497afbc23a57b~tplv-k3u1fbpfcp-watermark.image)

平时我们安装进虚拟机的CentOS都是好几个G，为什么Docker这里才200M？



对于一个精简的OS,rootfs可以很小，只需要包合最基本的命令，工具和程序库就可以了，因为底层直接用Host的kernel，自己只需要提供rootfs就可以了。

**由此可见对于不同的Linux发行版， boots基本是一致的， rootfs会有差別，因此不同的发行版可以公用bootfs.**



**虚拟机是分钟级别，容器是秒级！**



### 容器数据卷

**作用：**

> 容器内文件夹与宿主机文件夹映射，可实现文件同步修改，例如配置文件的修改，mysql数据文件映射保存，容器删除了容器内文件会删除，映射到宿主机的文件并不会随着容器删除而删除
>

**使用数据卷**

1. 直接 -v 指定

   ```shell
   docker run -it -v /home/ceshi:/home centos /bin/bash
   ```

2. Dockerfile 中 通过 VOLUME 指定

   ```shell
   # 创建一个dockerfile文件，名字可以随便 建议Dockerfile
   # 文件中的内容 指令(大写) 参数
   FROM centos
   
   VOLUME ["volume01","volume02"]
   
   CMD echo "----end----"
   CMD /bin/bash
   #这里的每个命令，就是镜像的一层！
   
   ```

### DockerFile

`dockerfile`是用来构建docker镜像的文件，命令参数脚本！

构建步骤：

1. 编写一个dockerfile文件
2. docker build 构建成一个镜像
3. docker run运行镜像
4. docker push发布镜像（DockerHub 、阿里云仓库)

### Docker 网络

1. 理解Docker0

   docker0是docker安装启动后默认的网络，所有启动的容器默认分配到docker0下，可以相互ping通。



2. Docker是如何处理容器间的网络访问的？

   容器间的网络访问都是通过docker0网络桥接访问的，docker0相当于一个路由器，默认启动的docker容器都连接在这个docker0网络下，

   用户也可以自定义网络桥接。

   

3. 容器重启分配的容器网络ip可能会变，如何不写死，通过服务名访问？

- --link  原理：配置了host
- 自定义网络 docker network create 

4. 不同网段网络连通：docker network connect

	原理：将容器添加到网段下，一个容器多个ip

### IDEA整合Docker

> 搜索插件 Docker



### 发布spring-boot服务（单机版）

```shell
#创建docker-spirng-boot文件夹
mkdir docker-spring-boot
cd docker-spring-boot
#准备spring-boot项目jar包放到此文件夹下，可以从镜像仓库拉取，可直接拷贝
---------------------------------------------------------------------------------------------------------
#编写Dockerfile文件
vim xxqa-usercenter-dockerfile
# Docker image for springboot file run
# VERSION 0.0.1
# Author: guanyp
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER guanyp <guanyp@xxxx.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名
ADD xxqa-usercenter-1.0-SNAPSHOT.jar xxqa-usercenter.jar
# 端口暴露，这里仅仅是声明暴露10081端口，启动容器时仍需要指定主机端口和容器端口映射，否则外网访问不通
EXPOSE 10081
# 运行jar包
RUN bash -c 'touch /xxqa-usercenter.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/xxqa-usercenter.jar"]
---------------------------------------------------------------------------------------------------------
#Dockerfile构建镜像
docker build -f xxqa-usercenter-dockerfile -t usercenter .
docker build -f leaf-dockerfile -t leaf .
---------------------------------------------------------------------------------------------------------
#查看镜像
docker images
REPOSITORY        TAG       IMAGE ID       CREATED          SIZE
usercenter   latest    b76e1ea1ce8d   16 seconds ago   798MB
leaf              1.0       d2a9e47f8090   20 hours ago     721MB
hello-world       latest    bf756fb1ae65   12 months ago    13.3kB
java              8         d23bdf5b1b1b   3 years ago      643MB
---------------------------------------------------------------------------------------------------------
#移除镜像
docker rmi leaf:1.0
---------------------------------------------------------------------------------------------------------
#启动leaf -p指定端口映射，主机端口：容器端口  --name 指定容器名称
docker run -d -p 8081:8081 -v /etc/localtime:/etc/localtime --name leaf leaf
#启动usercenter -v 主机时间和容器时间映射
docker run -d -p 10081:10081 -v /etc/localtime:/etc/localtime --name usercenter usercenter

#进入容器修改时区，java8中时区相差8小时，日志时间和主机对不上问题，所有容器都需要修改
docker exec -it leaf  /bin/bash
echo "Asia/Shanghai" > /etc/timezone
cat /etc/timezone 
Asia/Shangha
---------------------------------------------------------------------------------------------------------
#查看容器运行日志
docker logs -f -t --tail 100 xxqa-usercenter
---------------------------------------------------------------------------------------------------------
#查看运行的docker容器
docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED              STATUS              PORTS                      NAMES
805632110e0a   b76e1ea1ce8d   "java -Djava.securit…"   6 seconds ago        Up 4 seconds        0.0.0.0:10081->10081/tcp   xxqa-usercenter
b5b26423574a   d2a9e47f8090   "java -Djava.securit…"   About a minute ago   Up About a minute   0.0.0.0:8081->8081/tcp     leaf

---------------------------------------------------------------------------------------------------------
#测试启动的容器
curl "localhost:10081/im/user/17000009999"
{"errCode":"10000","errDesc":"业务处理成功","result":[],"success":true}

```

## Docker compose

### 简介

前面我们已经知道， Docker 可以通过 Dockerfile build run 手动操作，启动单个容器。

如果是微服务架构，一个工程下有 10 个甚至100 个以上微服务，且服务之间存在依赖关系。如何去管理编排这些容器呢？

Docker Compose 就是用来轻松高效的管理容器，定义运行多个容器。



> 官方介绍

定义、运行多个容器。

YAML file 配置文件。

single command。 命令有哪些？



Compose is a tool for defining and running multi-container Docker applications. With Compose, you use a YAML file to configure your application’s services. Then, with a single command, you create and start all the services from your configuration. To learn more about all the features of Compose, see [the list of features](https://docs.docker.com/compose/#features).

所有的环境都可以使用 Compose

Compose works in all environments: production, staging, development, testing, as well as CI workflows. You can learn more about each case in [Common Use Cases](https://docs.docker.com/compose/#common-use-cases).

**三步骤**

Using Compose is basically a three-step process:

1. Define your app’s environment with a `Dockerfile` so it can be reproduced anywhere.
   1. Dockerfile 保证我们的项目可以在任何地方运行。
2. Define the services that make up your app in `docker-compose.yml` so they can be run together in an isolated environment.
   1. services 什么是服务
   2. docker-compose.yml 这个文件怎么写？
3. Run `docker-compose up` and Compose starts and runs your entire app.
   1. 启动项目



作用：批量容器编排。

> 个人理解

Compose 是Docker官方都的开源项目。需要安装。

Dockerfile 让程序在任何地方运行。一个web服务，包含redis、mysql、nginx... 多个容器。run

Compose

```yaml
version: "3.9"  # optional since v1.27.0
services:
  web:
    build: .
    ports:
      - "5000:5000"
    volumes:
      - .:/code
      - logvolume01:/var/log
    links:
      - redis
  redis:
    image: redis
volumes:
  logvolume01: {}
```

docker-compose up 100个服务

Compose：重要的概念。

	1. 服务services，容器，应用。（web，mysql，redis...)
	2. 项目project。一组关联的容器。博客，web、mysql。

### 安装

``` shell
#安装 下载速度慢，推荐国内链接下载
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

#国内链接快速安装
curl -L https://get.daocloud.io/docker/compose/releases/download/1.25.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

#授权
sudo chmod +x /usr/local/bin/docker-compose

#查看安装版本
docker-compose --version

#错误
/usr/local/bin/docker-compose: Text file busy

fuser /usr/local/bin/docker-compose
/usr/local/bin/docker-compose:         50340

kill -9 50340
```



### 体验

官网地址：https://docs.docker.com/compose/gettingstarted/

1、应用 app.py

2、Dockerfile 应用打包为镜像

3、Docker-compose.yaml 文件（定义整个服务，web，redis）完整的上线服务。

4、启动 compose 项目 （docker-compose up）

流程：

1、创建网络

2、执行 Docker-compose.yaml

3、启动服务。



#### yaml规则

docker-compose.yaml 核心

```yaml
#3层
version: '' # 版本
services:  # 服务
	服务1: web
		# 服务配置
		images
		build
		network
		...
	服务2: redis
		...
	服务3: mysql
		...
# 其他配置 网络、卷、全局规则
volumes:
networks:
configs:
```

### 开源项目实战

#### 博客搭建（WordPress）

原来：下载程序、安装数据库、配置...

compose 应用。 => 一键启动！



1. 创建一个项目文件夹，仅包含构建镜像的文件

   ```shell
   mkdir my_wordpress
   cd my_wordpress
   ```

   

2. 编写 docker-compose.yml 文件

   ```shell
   vim docker-compose.yml
   ```

   ```yaml
   version: '3.3'
   
   services:
      db:
        image: mysql:5.7
        volumes:
          - db_data:/var/lib/mysql
        restart: always
        environment:
          MYSQL_ROOT_PASSWORD: somewordpress
          MYSQL_DATABASE: wordpress
          MYSQL_USER: wordpress
          MYSQL_PASSWORD: wordpress
   
      wordpress:
        depends_on:
          - db
        image: wordpress:latest
        ports:
          - "8000:80"
        restart: always
        environment:
          WORDPRESS_DB_HOST: db:3306
          WORDPRESS_DB_USER: wordpress
          WORDPRESS_DB_PASSWORD: wordpress
          WORDPRESS_DB_NAME: wordpress
   volumes:
       db_data: {}
   ```

3. 构建项目

   ```shell
   docker-compose up -d
   Creating network "my_wordpress_default" with the default driver
   Pulling db (mysql:5.7)...
   5.7: Pulling from library/mysql
   efd26ecc9548: Pull complete
   a3ed95caeb02: Pull complete
   ...
   Digest: sha256:34a0aca88e85f2efa5edff1cea77cf5d3147ad93545dbec99cfe705b03c520de
   Status: Downloaded newer image for mysql:5.7
   Pulling wordpress (wordpress:latest)...
   latest: Pulling from library/wordpress
   efd26ecc9548: Already exists
   a3ed95caeb02: Pull complete
   589a9d9a7c64: Pull complete
   ...
   Digest: sha256:ed28506ae44d5def89075fd5c01456610cd6c64006addfe5210b8c675881aff6
   Status: Downloaded newer image for wordpress:latest
   Creating my_wordpress_db_1
   Creating my_wordpress_wordpress_1
   ```

```shell
#外网访问需开放端口
firewall-cmd --zone=public --add-port=80/tcp --permanent
```

至此项目搭建完成，可通过 http://localhost:8000 访问博客



1、下载项目（docker-compose.yml)

2、如果需要文件。Dockerfile

3、文件准备齐全（直接意见启动！）



后台启动

docker -d

docker-compose up -d







## Docker Swarm

官方文档：https://docs.docker.com/engine/swarm/

作用：快速搭建集群，会自动将启动的服务平均分配到集群下的机器

例如：上面的实战部署，仅仅在一台机器启动了 4 个 leaf 服务，和 4 个 xxqa-usercenter 服务，假如我们有四台机器，通过 Docker Swarm 搭建了一个机器集群，则启动的 4 个服务会被平均分配到 4 台机器中，无需在每台机器单独启动。



### 准备四台服务器

1c2g

### 4台机器安装Docker

和我们单机安装一样

技巧：xshell 直接同步操作，省时间！ （右键，发送键到所有会话）

### 工作模式

Docker Engine 1.12 introduces swarm mode that enables you to create a cluster of one or more Docker Engines called a swarm. A swarm consists of one or more nodes: physical or virtual machines running Docker Engine 1.12 or later in swarm mode.

There are two types of nodes: [**managers**](https://docs.docker.com/engine/swarm/how-swarm-mode-works/nodes/#manager-nodes) and [**workers**](https://docs.docker.com/engine/swarm/how-swarm-mode-works/nodes/#worker-nodes).

![Swarm mode cluster](https://docs.docker.com/engine/swarm/images/swarm-diagram.png)

If you haven’t already, read through the [swarm mode overview](https://docs.docker.com/engine/swarm/) and [key concepts](https://docs.docker.com/engine/swarm/key-concepts/).

### 机器集群搭建

目标：三主一从

四台集群机器：docker1，docker2，docker3，docker4

manager：docker1，docker2，docker3

worker：docker4

运行以下命令以创建新的群集：

```shell
#初始化Swarm集群,成为管理节点
$ docker swarm init
Swarm initialized: current node (wyiufkynoiteaz7si670h78aj) is now a manager.

To add a worker to this swarm, run the following command:

    docker swarm join --token SWMTKN-1-2zmqyb9705m653t0dlw5j3h3ms7nr24strv8ast4nnfk4jpm02-3afwkibhunla9oci1bkoa5nje 172.22.19.48:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.

#根据提示将机器加入Swarm集群，查看集群
$ docker node ls
ID                            HOSTNAME                  STATUS    AVAILABILITY   MANAGER STATUS   ENGINE VERSION
srjbxu022diirhh9gugtv0zvg     iZbp1907haerdhq7kdb8msZ   Ready     Active                          20.10.2
qs753myroskb68ousm5h9cq3h     iZbp1907haerdhq7kdb8mtZ   Ready     Active         Reachable        20.10.2
ord16e9gclvh3dtebe5cieqrv     iZbp1907haerdhq7kdb8muZ   Ready     Active         Reachable        20.10.2
rnjvhvdtbiodvxe32iky6avb5 *   izbp1907haerdhq7kdb8mvz   Ready     Active         Leader           20.10.2
```



#### Raft协议

Raft协议：保证大多数节点存活才可以用，只要>1，集群机器至少3台

实验：

1、双主双从下，一个主节点停止，宕机！另外一个主节点也不能用了；

2、节点离开集群

```shell
docker swarm leave
```

3、三主一从，一个主节点停止，仍然可用。

### Docker Swarm 发布服务

1. xshell打开 docker1。

2. 运行以下命令：

   ```shell
   #启动服务
   $ docker service create --replicas 1 --name helloworld alpine ping docker.com
   
   9uk4639qpg7npwf3fn2aasksr
   ```

   - 该`docker service create`命令创建服务。
   - 该`--name`标志为服务命名`helloworld`。
   - 该`--replicas`标志指定1个运行实例的所需状态。
   - 参数`alpine ping docker.com`将服务定义为执行命令的Alpine Linux容器`ping docker.com`。

3. 运行`docker service ls`以查看正在运行的服务列表：

   ```shell
   $ docker service ls
   
   ID            NAME        SCALE  IMAGE   COMMAND
   9uk4639qpg7n  helloworld  1/1    alpine  ping docker.com 
   ```

4. 运行`docker service inspect --pretty <SERVICE-ID>`以易于阅读的格式显示有关服务的详细信息。

   ```shell
   $ docker service inspect --pretty helloworld
   
   ID:		9uk4639qpg7npwf3fn2aasksr
   Name:		helloworld
   Service Mode:	REPLICATED
    Replicas:		1
   Placement:
   UpdateConfig:
    Parallelism:	1
   ContainerSpec:
    Image:		alpine
    Args:	ping docker.com
   Resources:
   Endpoint Mode:  vip
   ```

   

### 扩展服务群

当用 docker service 发布了一个服务后，可以通过 docker service scale 命令扩展服务容器的数量。在服务中运行的容器称为“任务”。

1. xshell 打开 docker1 。

2. 运行以下命令来更改群集中运行的服务的所需状态：

   ```shell
   $ docker service scale <SERVICE-ID>=<NUMBER-OF-TASKS>
   ```

   例如：

   ```shell
   # 扩展 helloworld 的服务集群为5
   $ docker service scale helloworld=5 
   
   helloworld scaled to 5
   ```

3. 运行`docker service ps <SERVICE-ID>`以查看更新的任务列表：

   ```shell
   $ docker service ps helloworld
   
   NAME                                    IMAGE   NODE      DESIRED STATE  CURRENT STATE
   helloworld.1.8p1vev3fq5zm0mi8g0as41w35  alpine  worker2   Running        Running 7 minutes
   helloworld.2.c7a7tcdq5s0uk3qr88mf8xco6  alpine  worker1   Running        Running 24 seconds
   helloworld.3.6crl09vdcalvtfehfh69ogfb1  alpine  worker1   Running        Running 24 seconds
   helloworld.4.auky6trawmdlcne8ad8phb0f1  alpine  manager1  Running        Running 24 seconds
   helloworld.5.ba19kca06l18zujfwxyc5lkyn  alpine  worker2   Running        Running 24 seconds
   ```

   您可以看到swarm创建了4个新任务，以扩展到总共5个Alpine Linux运行实例。任务分布在群集的三个节点之间。一个正在运行`manager1`。

4. 运行`docker ps`以查看在您连接的节点上运行的容器。以下示例显示了在上运行的任务`manager1`：

   ```shell
   $ docker ps
   
   CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
   528d68040f95        alpine:latest       "ping docker.com"   About a minute ago   Up About a minute                       helloworld.4.auky6trawmdlcne8ad8phb0f1
   ```

   如果要查看在其他节点上运行的容器，请使用ssh进入这些节点并运行`docker ps`命令。



### nacos集群安装

- Clone project

  ```shell
  git clone --depth 1 https://github.com/nacos-group/nacos-docker.git
  cd nacos-docker
  
  #配置启动jvm内存大小
  vim nacos-docker-master/env/nacos-hostname.env
  #jvm
  JVM_XMS=256m
  JVM_XMX=256m
  JVM_XMN=256m
  
  ```

- Cluster

  ```shell
  docker-compose -f nacos-docker-master/example/cluster-hostname.yaml up -d
  ```

## 学习强安二期部署

1、编写微服务项目，编写项目下各个项目的dockerfile文件

> leaf

```dockerfile
# Docker image for springboot file run
# VERSION 0.0.1
# Author: guanyp
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER guanyp <guanyp@hiwitech.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名为leaf.jar
ADD leaf.jar leaf.jar
# 端口暴露
EXPOSE 8081
# 设置 docker 容器时间
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
  
# 运行jar包
RUN bash -c 'touch /leaf.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/leaf.jar"]
```

> usercenter

```dockerfile
# Docker image for springboot file run
# VERSION 0.0.1
# Author: guanyp
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER guanyp <guanyp@hiwitech.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名
ADD xxqa-usercenter-1.0-SNAPSHOT.jar xxqa-usercenter.jar
# 端口暴露
EXPOSE 10081
# 设置 docker 容器时间
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
  
# 运行jar包
RUN bash -c 'touch /xxqa-usercenter.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/xxqa-usercenter.jar"]
```

2、创建 xxqa 文件夹，进入 xxqa 下，创建 leaf 、usercenter 文件夹，将 leaf 和 usercenter 的 jar 包 和 Dockerfile 文件 分别放到各自的文件夹下，在 xxqa目录下编写 docker-compose.yml 文件。

```shell
# 在集群每台机器里构建好镜像，用 xshell 发送指令到所有窗口, 切换至 leaf 和 usercenter文件下，执行一下命令构建镜像
# 若上传到镜像仓库，可省略这一步，直接拉取镜像，执行第三部，这一步是构建镜像。
$ docker build -t leaf .
$ docker build -t usercenter .
```



3、编写项目的 docker-compose.yml 编排项目

```yaml
version: "3.3"
services:
  usercenter:
    image: usercenter:latest #指定镜像
    ports: # 向外暴露端口映射 容器：主机
    - "10081:10081"
    build:
      #指定上下文目录，当前目录
      context: .
    #docker swarm 环境下生效，集群机器数
    deploy:
      replicas: 4 #副本数量
    depends_on: # 依赖 leaf 服务
      - leaf
  leaf:
    image: leaf:latest
    ports:
    - "8081:8081"
    build:
      context: .
    deploy:
      replicas: 4
```

4、搭建 Docker Swarm 集群环境，参考 Docker Swarm 章节

5、在当前目录执行docker-compose命令启动项目。

```shell
# 一键启动项目
$ docker stack deploy --compose-file docker-compose.yml xxqa

# 查看项目运行情况,xxqa 项目下启动了两个服务
$ docker stack ls 
NAME      SERVICES   ORCHESTRATOR
xxqa      2          Swarm

# 查询项目运行详情
# xxqa 项目下启动了两个服务，一个 usercenter ，一个 leaf ，根据 docker-compose.yml 里面的配置，各自启动了 4 个集群。
$ docker stack ps xxqa
ID             NAME                    IMAGE               NODE                      DESIRED STATE   CURRENT STATE               ERROR                       PORTS
rb11hl4dxwqp   xxqa_leaf.1             leaf:latest         iZbp1907haerdhq7kdb8msZ   Running         Running 14 minutes ago     g90mikd1cvqc   xxqa_leaf.2             leaf:latest         iZbp1907haerdhq7kdb8muZ   Running         Running 14 minutes ago     7o1jbvtpfcoh   xxqa_leaf.3             leaf:latest         iZbp1907haerdhq7kdb8mtZ   Running         Running 14 minutes ago     ljv6j70osa40   xxqa_leaf.4             leaf:latest         izbp1907haerdhq7kdb8mvz   Running         Running 14 minutes ago     a64er2spscdn   xxqa_usercenter.1       usercenter:latest   iZbp1907haerdhq7kdb8msZ   Running         Running 14 minutes ago     eymmlvewx5a2   xxqa_usercenter.2       usercenter:latest   iZbp1907haerdhq7kdb8muZ   Running         Running 14 minutes ago     qia0kqq3nlh4   xxqa_usercenter.3       usercenter:latest   iZbp1907haerdhq7kdb8mtZ   Running         Running 15 seconds ago     
nxk9zmvmef4b   xxqa_usercenter.4       usercenter:latest   izbp1907haerdhq7kdb8mvz   Running         Running 12 seconds ago 

# 移除服务
$ docker service rm xxqa_leaf
$ docker service rm xxqa_usercenter
```

6、访问项目，测试是否可用。

```shell
$ curl "localhost:8081/api/snowflake/get/user"
1352541389522485342
```



7、Docker Swarm 如何做到弹性伸缩，例如：强安需要搞活动，需要临时增加集群数量支持高并发的流量。

```shell
# 1. 如果有新增机器资源，通过 Docker Swarm 里面的教程将活动临时新增的机器加入 Docker Swarm 集群里
# 2. 选择我们需要扩展的服务，修改集群数量，例如：将 usercenter 服务扩展至 10 台，xxqa 是项目发布的命名空间，usercenter 是 docker-compose.yml 里面的服务名称。
$ docker service scale xxqa_usercenter=10
```





## Docker 镜像仓库搭建

### 仓库搭建

```shell
# 下载Registry镜像并启动
$ docker pull registry
Using default tag: latest
latest: Pulling from library/registry
0a6724ff3fcd: Pull complete 
d550a247d74f: Pull complete 
1a938458ca36: Pull complete 
acd758c36fc9: Pull complete 
9af6d68b484a: Pull complete 
Digest: sha256:d5459fcb27aecc752520df4b492b08358a1912fcdfa454f7d2101d4b09991daa
Status: Downloaded newer image for registry:latest
docker.io/library/registry:latest

# 运行 Registry 
$ docker run -d -v /edc/images/registry:/var/lib/registry -p 5000:5000 --restart=always --name xdp-registry registry

# 查看镜像仓库中的所有镜像 curl http://ip:port/v2/_catalog
$ curl "172.22.19.46:5000/v2/_catalog"
{"repositories":[]}


# 运行 http 访问
$ vim /etc/docker/daemon.json

# 加上下面这一句，这里的"your-server-ip"请换为你的服务器的外网IP地址：
{ 
    "insecure-registries" : [ "172.22.19.46:5000" ] 
}

# 重启 docker
$ systemctl restart docker
```

### 上传镜像

```shell
# 查看镜像
$ docker images
REPOSITORY   TAG       IMAGE ID       CREATED       SIZE
leaf         latest    2e0e5c2b0d50   2 days ago    682MB
usercenter   latest    935002439689   2 days ago    721MB
java         8         d23bdf5b1b1b   4 years ago   643MB

# tag 要上传的镜像打Tag 
$ docker tag leaf:test 172.22.19.46:5000/leaf:test

# 再查看镜像，本地多了打上tag的镜像
$ docker images
REPOSITORY               TAG       IMAGE ID       CREATED       SIZE
172.22.19.46:5000/leaf   test      2e0e5c2b0d50   2 days ago    682MB
leaf                     latest    2e0e5c2b0d50   2 days ago    682MB
usercenter               latest    935002439689   2 days ago    721MB
java                     8         d23bdf5b1b1b   4 years ago   643MB

# 上传镜像 leaf:test 到镜像仓库
$ docker push 172.22.19.46:5000/leaf:test

# 查看镜像仓库中的所有镜像，已经上传到镜像仓库了
$ curl "172.22.19.46:5000/v2/_catalog"
{"repositories":["leaf"]}
```

### 下载镜像

```shell
# 切换到一台没有 leaf:test 的docker服务器
$ docker images
REPOSITORY   TAG       IMAGE ID       CREATED       SIZE
leaf         latest    c2667ad17cb1   2 days ago    682MB
usercenter   latest    6b31f9fa9e1d   3 days ago    721MB
java         8         d23bdf5b1b1b   4 years ago   643MB

# 拉取镜像
$ docker pull 172.22.19.46:5000/leaf:test
test: Pulling from leaf
7448db3b31eb: Pull complete 
c36604fa7939: Pull complete 
29e8ef0e3340: Pull complete 
a0c934d2565d: Pull complete 
a360a17c9cab: Pull complete 
cfcc996af805: Pull complete 
2cf014724202: Pull complete 
4bc402a00dfe: Pull complete 
34bba5de40ce: Pull complete 
6896fed66014: Pull complete 
Digest: sha256:d012631f35039c381973871b7cd263cfdfd0ff10801f0360b1ce3196f638389c
Status: Downloaded newer image for 172.22.19.46:5000/leaf:test
172.22.19.46:5000/leaf:test


#在查看镜像，下载成功
$ docker images
REPOSITORY               TAG       IMAGE ID       CREATED       SIZE
leaf                     latest    c2667ad17cb1   2 days ago    682MB
172.22.19.46:5000/leaf   test      2e0e5c2b0d50   2 days ago    682MB
usercenter               latest    6b31f9fa9e1d   3 days ago    721MB
java                     8         d23bdf5b1b1b   4 years ago   643MB
```

