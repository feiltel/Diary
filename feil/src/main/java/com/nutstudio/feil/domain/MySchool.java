package com.nutstudio.feil.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity//表示一个实体类，和数据表进行映射
@Table(name = "my_school")  //所映射的表的名字，可省略，默认为实体类名
public class MySchool implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
