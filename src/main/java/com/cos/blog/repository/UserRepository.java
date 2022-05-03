package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// DAO
// 자동으로 bean등록이 된다.
// @Repository //생략 가능
public interface UserRepository extends JpaRepository<User, Integer > {
    //JPA Naming 전략
    // select * From user Where username = ? AND password = ? ;
    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "select * From user Where username = ?1 AND password = ?2",nativeQuery = true)
//    User login(String username, String password);
}
