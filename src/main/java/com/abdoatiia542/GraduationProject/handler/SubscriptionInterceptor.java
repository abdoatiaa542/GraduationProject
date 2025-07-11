//package com.abdoatiia542.GraduationProject.handler;
//
//import com.abdoatiia542.GraduationProject.model.User;
//import com.abdoatiia542.GraduationProject.repository.UserRepository;
//import com.abdoatiia542.GraduationProject.service.jwt.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessagingException;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.Objects;
//
//@Component
//@RequiredArgsConstructor
//public class SubscriptionInterceptor implements ChannelInterceptor {
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
//        if (accessor.getCommand().equals(StompCommand.MESSAGE)) {
////            System.out.println(accessor.getDestination());
//        }
//
//        if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
//            String destination = accessor.getDestination();
//            if (accessor.getUser() == null) {
//                String userId = String.valueOf(Objects.requireNonNull(sessionAttributes).get("userId"));
//                if (userId != null) {
//                    accessor.setUser(() -> userId);
//                }
//            }
//
//            String authorization = (String) sessionAttributes.get("Authorization");
//            String token = authorization.substring(7);
//
//            if (authorization == null || !jwtService.isValidToken(token)) {
//                throw new MessagingException("Your subscription request should be authorized");
//            }
//
//            String username = jwtService.getUsername(token);
//
//            User user = userRepository.findByUsername(username)
//                    .orElseThrow(() -> new MessagingException("User not found"));
//
//            if (destination.startsWith("/user")) {
//                String userId = destination.split("/")[2];
//                if (!user.getId().toString().equals(userId)) {
//                    throw new MessagingException("You are not authorized to view this user messages.");
//                }
//            }
//
//            if (accessor.getDestination().startsWith("/chat")) {
//
//                String chatId = destination.split("/")[2];
//                DirectChat directChat = chatRepo.findById(Integer.valueOf(chatId)).orElseThrow(() -> new ResourceNotFoundException("DirectChat doesn't exist"));
//
//                User firstUser = directChat.getFirstUser();
//                User secondUser = directChat.getSecondUser();
//
//                if (!(firstUser.getId().equals(user.getId()) || secondUser.getId().equals(user.getId()))) {
//                    throw new RuntimeException("You are not allowed to send message to this directChat");
//                }
//            }
//
//        }
//
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String userId = String.valueOf(Objects.requireNonNull(sessionAttributes).get("userId"));
//            if (userId != null) {
//                accessor.setUser(() -> userId);
//            }
//        }
//
//        return message;
//    }
//}