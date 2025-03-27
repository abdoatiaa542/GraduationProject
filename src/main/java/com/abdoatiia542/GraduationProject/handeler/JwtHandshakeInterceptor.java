//package com.abdoatiia542.GraduationProject.handeler;
//
//import com.abdoatiia542.GraduationProject.repository.UserRepository;
//import com.abdoatiia542.GraduationProject.service.jwt.JwtService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.net.URI;
//import java.util.Map;
//
//@Component
//public class JwtHandshakeInterceptor implements HandshakeInterceptor {
//
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//
//    public JwtHandshakeInterceptor(JwtService jwtService, UserRepository userRepository) {
//        this.jwtService = jwtService;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        URI uri = request.getURI();
//        String query = uri.getQuery();  // Get query string
//        if (query != null) {
//            for (String param : query.split("&")) {
//                String[] pair = param.split("=");
//                if (pair.length > 1 && pair[0].equals("token")) {
//                    String token = pair[1];
//                    attributes.put("Authorization", "Bearer " + token);
//                    if (jwtService.isValidToken(token)) {
//                        String username = jwtService.getUsername(token);
//                        Long userId = userRepository.findByUsername(username).orElseThrow()
//                                .getId();
//                        attributes.put("userId", userId);
//                        return true;
//                    }
//                }
//            }
//        }
//        response.setStatusCode(HttpStatus.FORBIDDEN);
//        return false;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
//        // No action needed
//    }
//}