package com.authhandler.authapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.authhandler.authapi.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}