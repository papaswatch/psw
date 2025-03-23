package com.papaswatch.psw.repository.product.recentView;

import com.papaswatch.psw.dto.ProductRecentViewed;
import jakarta.servlet.http.HttpSession;

import java.util.Queue;

public interface RecentViewedRepository {
    Queue<ProductRecentViewed> getRecentViewedProductQueue(long userId);
    void saveRecentViewedProduct(long userId, Queue<ProductRecentViewed> queue);
}
