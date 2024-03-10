package com.example.productservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
//import javax.persistence.Id;
import java.util.Date;


@Getter
@Setter
@MappedSuperclass

public class BaseModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;
    public Date createdAt;
    public Date lastUpdatedAt;
    public boolean isDelete;
}
