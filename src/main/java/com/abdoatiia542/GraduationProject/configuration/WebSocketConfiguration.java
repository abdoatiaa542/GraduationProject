//package com.abdoatiia542.GraduationProject.configuration;
//
//import com.abdoatiia542.GraduationProject.handler.JwtHandshakeInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.ChannelRegistration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
//
//@Configuration
//@EnableWebSocketMessageBroker
//@RequiredArgsConstructor
//public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
//    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
//    private final SubscriptionInterceptor subscriptionInterceptor;
//    private final CustomStompErrorHandler customStompErrorHandler;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/direct-chat", "/notifications-count", "/topic", "/messages-count", "/user");
//        registry.setApplicationDestinationPrefixes("/app");
//        registry.setUserDestinationPrefix("/user");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws")
//                .addInterceptors(jwtHandshakeInterceptor)
//                .setAllowedOriginPatterns("*");
//    }
//
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(subscriptionInterceptor, new InboundMessageInterceptor());
//    }
//
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//
//    }
//
//}