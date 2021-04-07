package com.example.webfluxservice.util;

import com.example.webfluxservice.excption.CheckException;

import java.util.stream.Stream;

/**
 * @author zhy
 * @since 2021/3/23 17:36
 */
public class CheckUtil {
    public final static String[] INVALIDE_NAMES = {"admin"};

    public static void check(String value) {
        Stream.of(INVALIDE_NAMES).filter(name -> name.equals(value))
                .findAny().ifPresent(name -> {
            throw new CheckException("name", value);
        });
    }
}
