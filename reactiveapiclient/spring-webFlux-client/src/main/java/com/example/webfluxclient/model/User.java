package com.example.webfluxclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author zhy
 * @since 2021/3/23 10:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String name;

    private String id;

    private Integer age;
}
