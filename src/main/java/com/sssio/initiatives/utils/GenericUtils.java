package com.sssio.initiatives.utils;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericUtils {

    public List<Long> StringToLongList(String input) {
            if(StringUtils.isEmpty(input)){
                return new ArrayList<>();
            }
            List<Long> longList = Arrays.stream(input.split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            return longList;
    }
}
