package com.watermelon.chat.config.socketIO;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private String redisPort;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setOrigin("*");
//        config.setAuthorizationListener(new AuthorizationListener() {
//            @Override
//            public AuthorizationResult getAuthorizationResult(HandshakeData data) {
//                return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
//            }
//        });

        SocketConfig socketConfig = config.getSocketConfig();
        socketConfig.setReuseAddress(true);

        Config redissonConfig = new Config();
        redissonConfig.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
        Redisson redisson = (Redisson) Redisson.create(redissonConfig);
        RedissonStoreFactory redisStoreFactory = new RedissonStoreFactory(redisson);

        config.setStoreFactory(redisStoreFactory);
        return new SocketIOServer(config);
    }

}

