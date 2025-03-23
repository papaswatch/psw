package com.papaswatch.psw.repository.product.recentView;

import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CachedRecentViewedRepository implements RecentViewedRepository {

    // 각 유저별 최근 본 상품을 저장하는 Map, Key는 유저의 key값입니다.
    ConcurrentHashMap<Long, Queue<Long>> store = new ConcurrentHashMap<>();

    @Override
    public Queue<Long> getRecentViewedProductQueue(long userId) {
        return store.get(userId);
    }

    @Override
    public void saveRecentViewedProduct(long userId, Queue<Long> queue) {
        store.put(userId, queue);
    }
}
