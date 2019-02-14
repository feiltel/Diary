package com.nutstudio.feil.repository;

import com.nutstudio.feil.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findByAddress(String address);

    public List<User> findByPhone(String phone);


    @Query(value = "select * from t_user WHERE phone = ? ", nativeQuery = true)
    List<User> getAllUser(String phone);

    //自定义SQL删除
    @Query(value = "delete from t_user WHERE phone = ? ", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteUser(String phone);

    @Query("select u from User u where u.name = ?1 and u.phone= ?2")
     List<User> getUserByNamePhone(String name,String phone);


    List<User> findByNameContainingAndPhoneContainingAndAddressContaining(String name,String phone,String address);

    List<User> findByNameContainingOrPhoneContainingOrAddressContaining(String name,String phone,String address);
    //限制查询
    List<User> findFirst2ByAddressOrderByIdDesc (String address);

    Page<User> findAllByNameContainingOrPhoneContainingOrAddressContaining(String name, String phone, String address, Pageable pageable);

    List<User> findAllByNameContainingOrPhoneContainingOrAddressContaining(String name, String phone, String address);
    Long countByNameContainingOrPhoneContainingOrAddressContaining(String name, String phone, String address);
}
