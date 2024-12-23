// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Unit-тестирование и mock-объекты"

package org.skypro.skyshop;

import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class TestTools {

    public static boolean isMapOfProducts(@NotNull Object o) {
        return (o instanceof Map<?, ?> map) &&
                !map.isEmpty() &&
                map.keySet().iterator().next() instanceof UUID &&
                map.values().iterator().next() instanceof Product;
    }

    public static boolean isMapOfArticles(@NotNull Object o) {
        return (o instanceof Map<?, ?> map) &&
                !map.isEmpty() &&
                map.keySet().iterator().next() instanceof UUID &&
                map.values().iterator().next() instanceof Article;
    }

    public static final UUID UUID_PRODUCT = UUID.fromString("12345678-1234-1234-1234-123456789012");
    public static final String PRODUCT_TITLE = "Молоко с планеты Венера";
    public static final int PRODUCT_PRICE = 17;
    public static final String PRODUCT_SEARCHABLE_NAME = "Product-Unique-87243984985938";

    public static final UUID UUID_ARTICLE = UUID.fromString("12345678-1234-0000-1234-123456789012");
    public static final String ARTICLE_TITLE = "Вино из одуванчиков";
    public static final String ARTICLE_SEARCHABLE_NAME = "Article-Unique-87243984985938";

    public static final String SEARCH_PATTERN = "енера";

    public static boolean isNotCollection(@NotNull Object o, boolean testEmpty) {
        boolean isCollection = o instanceof Collection<?>;
        if (!isCollection) {
            return true;
        }
        Collection<?> collection = (Collection<?>) o;
        return testEmpty && collection.isEmpty();
    }

    public static boolean isCollectionOfSearchable(@NotNull Object o, boolean testItem) {
        if (isNotCollection(o, testItem)) {
            return false;
        }
        var nextItem = ((Collection<?>)o).iterator().next();
        return nextItem instanceof Searchable;
    }

    public static boolean isCollectionOfSearchResult(@NotNull Object o, boolean testItem) {
        if (isNotCollection(o, testItem)) {
            return false;
        }
        var nextItem = ((Collection<?>)o).iterator().next();
        return nextItem instanceof SearchResult;
    }
}
