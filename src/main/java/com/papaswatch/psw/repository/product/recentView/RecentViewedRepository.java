package com.papaswatch.psw.repository.product.recentView;

import java.util.Queue;

public interface RecentViewedRepository {
    Queue<Long> getRecentViewedProductQueue(long userId);
    void saveRecentViewedProduct(long userId, Queue<Long> queue);
}
