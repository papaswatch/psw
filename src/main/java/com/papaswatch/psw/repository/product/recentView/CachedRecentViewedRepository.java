package com.papaswatch.psw.repository.product.recentView;

import com.papaswatch.psw.dto.ProductRecentViewed;
import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CachedRecentViewedRepository implements RecentViewedRepository {

    // 각 유저별 최근 본 상품을 저장하는 Map, Key는 유저의 key값입니다.
    ConcurrentHashMap<Long, Queue<ProductRecentViewed>> store = new ConcurrentHashMap<>();

    @Override
    public Queue<ProductRecentViewed> getRecentViewedProductQueue(long userId) {
        return store.get(userId);
    }

    @Override
    public void saveRecentViewedProduct(long userId, Queue<ProductRecentViewed> queue) {
        store.put(userId, queue);
    }
}
