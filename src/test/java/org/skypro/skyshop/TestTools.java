// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Unit-тестирование и mock-объекты"

package org.skypro.skyshop;

import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
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

    public static final UUID UUID_EXISTING_MOCK_PRODUCT = UUID.fromString("12345678-1234-1234-1234-123456789012");
    public static final UUID UUID_NOT_EXISTING_MOCK_PRODUCT = UUID.fromString("12345678-4321-1234-1234-123456789012");

    public static final UUID UUID_EXISTING_MOCK_ARTICLE = UUID.fromString("12345678-1234-0000-1234-123456789012");
    public static final UUID UUID_NOT_EXISTING_MOCK_ARTICLE = UUID.fromString("12345678-4321-1111-1234-123456789012");

    public static Product getProductMock() {
        var title = "Молоко с планеты Венера";
        int price = 10;

        var product = Mockito.mock(SimpleProduct.class);

        Mockito.when(product.getId()).thenReturn(UUID_EXISTING_MOCK_PRODUCT);
        Mockito.when(product.getTitle()).thenReturn(title);
        Mockito.when(product.getPrice()).thenReturn(price);

        return product;
    }

    public static final String EXISTING_MOCK_SEARCHABLE_NAME = "Article-Unique-87243984985938";

    public static Article getArticleMock() {
        var article = Mockito.mock(Article.class);
        Mockito.when(article.getId()).thenReturn(UUID_EXISTING_MOCK_ARTICLE);
        return article;
    }

    public static boolean isCollectionOfSearchable(@NotNull Object o, boolean testItem) {
        boolean isCollection = o instanceof Collection<?>;
        if (!isCollection) {
            return false;
        }

        Collection<?> collection = (Collection<?>) o;
        var notEmpty = testItem && !collection.isEmpty();
        if (!notEmpty) {
            return false;
        }

        var nextItem = collection.iterator().next();
        @SuppressWarnings("inline")
        boolean result = nextItem instanceof Searchable;
        return result;
    }
}
