package com.security.im.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedissonConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        if (redisProperties.getCluster() != null && redisProperties.getCluster().getNodes() != null
                && redisProperties.getCluster().getNodes().size() > 0) {
            List<String> clusterNodes = new ArrayList<>();
            for (String node : redisProperties.getCluster().getNodes()) {
                clusterNodes.add("redis://" + node);
            }
            config.useClusterServers().addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()])).setPassword(redisProperties.getPassword());
            // 主节点最小空闲连接数
            config.useClusterServers().setMasterConnectionMinimumIdleSize(1);
            // 从节点最小空闲连接数
            config.useClusterServers().setSlaveConnectionMinimumIdleSize(1);
        } else {
            String redisUrl = String.format("redis://%s:%s", redisProperties.getHost() + "", redisProperties.getPort() + "");
            config.useSingleServer().setAddress(redisUrl).setPassword(redisProperties.getPassword());
            config.useSingleServer().setDatabase(redisProperties.getDatabase());
        }

        return Redisson.create(config);
    }
}
