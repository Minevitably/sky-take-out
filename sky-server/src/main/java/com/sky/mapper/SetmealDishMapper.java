package com.sky.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id列表获取关联的套餐id列表
     * @param ids
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
