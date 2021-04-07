package com.example.webfluxservice.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author zhy
 * @since 2021/3/23 10:07
 */
@Document(collection = "user")
@Data
public class User implements Serializable {
    @NotBlank
    private String name;

    @Id
    private String id;

    @Range(min = 10,max = 100)
    private Integer age;
}
