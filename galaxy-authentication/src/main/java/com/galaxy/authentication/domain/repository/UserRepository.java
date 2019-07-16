package com.galaxy.authentication.domain.repository;

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

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 根据用户编码，找到用户
     * @param userCode
     * @return
     */
    Optional<User> findByUserCode(String userCode);

    /**
     * 根据忽视大小写的用户编码 + 锁定标识 + 删除标识，找到用户
     * @param userCode
     * @param isLocked
     * @param isDeleted
     * @return
     */
    @Cacheable("userByUserCode")
    Optional<User> findByUserCodeIgnoreCaseAndIsLockedAndIsDeleted(String userCode, String isLocked, String isDeleted);

    /**
     * 根据用户编码或用户姓名关键字，找到用户
     * @param keyword
     * @param pageable
     * @return
     */
    @Query(value = "SELECT u FROM User u WHERE (LOWER(u.userCode) like CONCAT('%',:keyword,'%')"
            + " OR LOWER(u.userName) like CONCAT('%',:keyword,'%')) AND u.isDeleted='0' AND u.isLocked='0'"
            , countQuery = "SELECT count(u.userCode) FROM User u"
            + " WHERE (LOWER(u.userCode) like CONCAT('%',:keyword,'%') OR LOWER(u.userName) like CONCAT('%',:keyword," +
            "'%')) AND u.isDeleted='0' AND u.isLocked='0'")
    Page<User> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 根据机构完整编码，找到所属机构的用户
     * @param orgFullCode
     * @return
     */
    List<User> findByOrgFullCodeIn(List<String> orgFullCode);
}
