package com.galaxy.authentication.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.galaxy.authentication.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserCode(String userCode);

    @Cacheable("userByUserCode")
    Optional<User> findByUserCodeIgnoreCaseAndIsLockedAndIsDeleted(String userCode, String isLocked, String isDeleted);

    @Query(value = "SELECT u FROM User u WHERE (LOWER(u.userCode) like CONCAT('%',:keyword,'%')"
            + " OR LOWER(u.userName) like CONCAT('%',:keyword,'%')) AND u.isDeleted='0' AND u.isLocked='0'"
            , countQuery = "SELECT count(u.userCode) FROM User u"
            + " WHERE (LOWER(u.userCode) like CONCAT('%',:keyword,'%') OR LOWER(u.userName) like CONCAT('%',:keyword," +
            "'%')) AND u.isDeleted='0' AND u.isLocked='0'")
    Page<User> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<User> findByOrgFullCodeIn(List<String> orgFullCode);
}
