package com.evian.sqct.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ClassName:StreamUtil
 * Package:com.evian.sqct.util
 * Description: Stream 衍生工具类
 *
 * @Date:2020/6/5 14:11
 * @Author:XHX
 */
public class StreamUtil {

    /**
     * 数组去重  用法  :
     * List<StaffDTO> collect = result.stream()
     * 				.filter(StreamUtil.distinctByKey(s -> s.getId()))
     * 				.collect(Collectors.toList());
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
