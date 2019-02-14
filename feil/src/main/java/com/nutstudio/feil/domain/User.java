package com.nutstudio.feil.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @Author:
 * @Description:  JPA使用示例:使用JPA注解配置映射关系
 * @Date: Created in 18:45 2018/9/24
 */

/**错误1 解决办法
 * hibernate会给每个被管理的对象加上hibernateLazyInitializer属性，同时struts-jsonplugin或者其他的jsonplugin都是
 *
 * 因为jsonplugin用的是java的内审机制.hibernate会给被管理的pojo加入一个hibernateLazyInitializer属性,jsonplugin通过java的反射机制将pojo转换成json，
 * 会把hibernateLazyInitializer也拿出来操作,但是hibernateLazyInitializer无法由反射得到，所以就抛异常了。
 */

@Entity   //表示一个实体类，和数据表进行映射
@Table(name = "t_user")   //所映射的表的名字，可省略，默认为实体类名
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) //解决错误1
public class User {


    @Id //设置为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //定义为自增主键
    private Integer id;

    @Column(name = "t_name",nullable = false)  //设置该字段在数据表中的列名,并设置该字段设置为不可为空
    private String name;

    @Column     //默认则字段名就为属性名
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

  //  @Transient   //增加该注解，则在数据表中不会进行映射
    private String address;


}
