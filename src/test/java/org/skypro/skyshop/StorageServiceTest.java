// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Unit-тестирование и mock-объекты"

package org.skypro.skyshop;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.StorageService;

import java.util.Map;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    /*  Тестируем реальный объект с реальными данными.
        Моки продуктов и статей - по необходимости.

        Основные методы:
            @NotNull Map<UUID, Product> getProductsAll()
            @NotNull Map<UUID, Article> getArticlesAll()
                !null, !isEmpty, map<>

            @NotNull Optional<Product> getProductById(@NotNull UUID id)
            @NotNull Optional<Article> getArticleById(@NotNull UUID id)
                isPresent/isEmpty, Product, id exists/not exists

            @NotNull Collection<Searchable> getSearchableItems()
                !null, !isEmpty, collection<>

        Вспомогательные методы:
            void clear() -> get...All():
                !null, isEmpty, map<>

            void addProduct(@NotNull Product product) -> getProductById()
            void addArticle(@NotNull Article article) -> getArticleById()

            void initializeWithSamples() -> get...All()
     */

    @NotNull
    private final StorageService storageService;

    public StorageServiceTest() {
        this.storageService = new StorageService();
    }

    /*
        Основные методы.
     */

    private boolean isMapOfProducts(@NotNull Object o) {
        return (o instanceof Map<?, ?> map) &&
                !map.isEmpty() &&
                map.keySet().iterator().next() instanceof UUID &&
                map.values().iterator().next() instanceof Product;
    }

    private boolean isMapOfArticles(@NotNull Object o) {
        return (o instanceof Map<?, ?> map) &&
                !map.isEmpty() &&
                map.keySet().iterator().next() instanceof UUID &&
                map.values().iterator().next() instanceof Article;
    }

    @Test
        // getProductsAll()
    void whenGetProductsAll_thenStorageServiceReturnsGoodMap() {
        Assertions.assertNotNull(storageService.getProductsAll());
        Assertions.assertFalse(storageService.getProductsAll().isEmpty());
        Assertions.assertTrue(isMapOfProducts(storageService.getProductsAll()));
    }

    @Test
        // getArticlesAll()
    void whenGetArticlesAll_thenStorageServiceReturnsGoodArticles() {
        Assertions.assertNotNull(storageService.getArticlesAll());
        Assertions.assertFalse(storageService.getArticlesAll().isEmpty());
        Assertions.assertTrue(isMapOfArticles(storageService.getArticlesAll()));
    }

    public static final UUID UUID_EXISTING_MOCK_PRODUCT = UUID.fromString("12345678-1234-1234-1234-123456789012");
    public static final UUID UUID_NOT_EXISTING_MOCK_PRODUCT = UUID.fromString("12345678-4321-1234-1234-123456789012");

    public static final UUID UUID_EXISTING_MOCK_ARTICLE = UUID.fromString("12345678-1234-0000-1234-123456789012");
    public static final UUID UUID_NOT_EXISTING_MOCK_ARTICLE = UUID.fromString("12345678-4321-1111-1234-123456789012");

    private Product getProductMock() {
        var title = "Молоко с планеты Венера";
        int price = 10;

        var product = Mockito.mock(SimpleProduct.class);

        Mockito.when(product.getId()).thenReturn(UUID_EXISTING_MOCK_PRODUCT);
        Mockito.when(product.getTitle()).thenReturn(title);
        Mockito.when(product.getPrice()).thenReturn(price);

        return product;
    }

    private Article getArticleMock() {
        var title = "Поколение, глотнувшее свободы";
        var content = "Lorem ipsum, по гамбургскому счёту";

        var article = Mockito.mock(Article.class);

        Mockito.when(article.getId()).thenReturn(UUID_EXISTING_MOCK_ARTICLE);
        Mockito.when(article.getTitle()).thenReturn(title);
        Mockito.when(article.getContent()).thenReturn(content);

        return article;
    }

    @Test
        // getProductById
    void whenGetProductById_thenStorageServiceReturnsGoodProduct() {
        var uniqueProduct = getProductMock();
        var id = uniqueProduct.getId();

        storageService.addProduct(uniqueProduct);
        var optional = storageService.getProductById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());

        var product = optional.get();
        Assertions.assertInstanceOf(Product.class, product);
        Assertions.assertEquals(product.getTitle(), uniqueProduct.getTitle());
        Assertions.assertEquals(product.getPrice(), uniqueProduct.getPrice());

        optional = storageService.getProductById(UUID_NOT_EXISTING_MOCK_PRODUCT);
        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
        // getArticleById
    void whenGetArticleById_thenStorageServiceReturnsGoodArticle() {
        var uniqueArticle = getArticleMock();
        var id = uniqueArticle.getId();

        storageService.addArticle(uniqueArticle);
        var optional = storageService.getArticleById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());

        var article = optional.get();
        Assertions.assertInstanceOf(Article.class, article);
        Assertions.assertEquals(article.getTitle(), uniqueArticle.getTitle());
        Assertions.assertEquals(article.getContent(), uniqueArticle.getContent());

        optional = storageService.getArticleById(UUID_NOT_EXISTING_MOCK_ARTICLE);
        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isPresent());
    }
}
