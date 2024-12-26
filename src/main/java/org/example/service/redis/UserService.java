package org.example.service.redis;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.entity.redis.TokenStore;
import org.example.entity.redis.User;
import org.example.exception.ExceptionUtil;
import org.example.repository.TokenStoreRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;
    TokenStoreRepository tokenStoreRepository;

    public User create(String id, String username, String status) {
        User user = new User(id, username, status);
        return userRepository.save(user);
    }

    public Optional<User> get(String userId) {
        return userRepository.findById(userId);
    }

    public User update(String id, String username, String status) {
        userRepository.findById(id)
                .map(user -> {
                    user.setUsername(username);
                    user.setStatus(status);
                    userRepository.save(user);
                    return user.getId();

                }).orElseThrow(() -> ExceptionUtil.throwNotFoundException("user this id does not exist~"));
        return new User(id, username, status);
    }

    @Transactional
    public void deactivateAndDeleteUser(String userId) {
        List<TokenStore> tokenStoreList = tokenStoreRepository.findAllByUserId(userId);
        for (TokenStore token : tokenStoreList) {
            token.setActive(false);
            tokenStoreRepository.save(token);
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus("INACTIVE");
            userRepository.save(user);
            // deleted user
            userRepository.delete(user);
        }
    }


    public List<User> getList() {
        return (List<User>) userRepository.findAll();
    }
}
