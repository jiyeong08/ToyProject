package com.toy.service;

import com.toy.entity.User;
import com.toy.exception.CustomException;
import com.toy.repository.UserRepository;
import com.toy.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public void userJoin(UserDto joinData) {
        log.info("[UserService] userJoin start");
        try {
            User user = User.builder()
                    .userId(joinData.getUserId())
                    .password(passwordEncoder.encode(joinData.getPassword()))
                    .nickname(joinData.getNickname())
                    .userName(joinData.getUserName())
                    .phoneNum(joinData.getPhoneNum())
                    .email(joinData.getEmail())
                    .joinDate(new Date())
                    .build();
            userRepository.save(user);
            log.info("[UserService] userJoin succeeded");
        } catch (Exception e){
            log.info("[UserService] an error occurs at userJoin : ", e);
            throw new CustomException("Failed to join user");
        }
    }

    public Map<String, Object> userList(int page, int pageSize, String sort) {
        log.info("[UserService] userList start");
        try {
            Page<User> userPage = userRepository.findAll(PageRequest.of(page - 1, pageSize, Sort.by(sort)));
            Map<String, Object> response = new HashMap<>();
            response.put("users", userPage.getContent().stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList()));
            response.put("currentPage", page);
            response.put("totalPages", userPage.getTotalPages());
            response.put("totalUsers", userPage.getTotalElements());
            log.info("[UserService] userList succeeded");
            return response;
        } catch (Exception e) {
            log.info("[UserService] an error occurs at userList : ", e);
            throw new CustomException("Failed to find user list");
        }
    }

    public UserDto userInfo(String userId, UserDto infoData) {
        log.info("[UserService] userInfo start");
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new CustomException("User not found");
            }
            user = user.builder()
                    .password(passwordEncoder.encode(infoData.getPassword()))
                    .nickname(infoData.getNickname())
                    .userName(infoData.getUserName())
                    .phoneNum(infoData.getPhoneNum())
                    .email(infoData.getEmail())
                    .build();
            userRepository.save(user);
            UserDto modifiedUser = modelMapper.map(user, UserDto.class);
            log.info("[UserService] userInfo succeeded");
            return modifiedUser;
        } catch (Exception e) {
            log.info("[UserService] an error occurs at userInfo : ", e);
            throw new CustomException("Failed to update user information");
        }
    }
}
