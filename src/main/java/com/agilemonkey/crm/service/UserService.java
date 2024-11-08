package com.agilemonkey.crm.service;

import com.agilemonkey.crm.entity.User;
import com.agilemonkey.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> listAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public User get(Long id){
        return this.userRepository.getReferenceById(id);
    }

    public User create(User user, User createUser){
        user.setCreated(new Date());
        user.setCreatedBy(createUser);

        return this.userRepository.save(user);
    }

    public User update(User user, User updateUser){
        user.setUpdated(new Date());
        user.setUpdatedBy(updateUser);
        return this.userRepository.save(user);
    }

    public void delete(Long id){
        this.userRepository.deleteById(id);
    }

    public User changeAdmin(Long id, boolean admin, User updateUser) {
        User user = this.userRepository.getReferenceById(id);
        user.setAdmin(admin);
        user.setUpdated(new Date());
        user.setUpdatedBy(updateUser);

        return this.userRepository.save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
