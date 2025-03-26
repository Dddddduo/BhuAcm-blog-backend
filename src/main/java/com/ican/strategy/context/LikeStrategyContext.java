package com.ican.strategy.context;

import com.ican.enums.LikeTypeEnum;
import com.ican.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 点赞策略上下文
 *
 * @author Dduo
 */
@Service
public class LikeStrategyContext {

    // 策略模式使得算法可以独立于使用它的用户而变化

    @Autowired
    private Map<String, LikeStrategy> likeStrategyMap;

    /**
     * 点赞
     *
     * @param likeType 点赞类型
     * @param typeId   类型id
     */
    public void executeLikeStrategy(LikeTypeEnum likeType, Integer typeId) {
        likeStrategyMap.get(likeType.getStrategy()).like(typeId);
    }
}