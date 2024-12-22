// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Unit-тестирование и mock-объекты"

package org.skypro.skyshop;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;

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

    // getProductsAll()
    @Test
    void whenGetProductsAll_thenStorageServiceReturnsGoodMap() {
        Assertions.assertNotNull(storageService.getProductsAll());
        Assertions.assertFalse(storageService.getProductsAll().isEmpty());
        Assertions.assertTrue(TestTools.isMapOfProducts(storageService.getProductsAll()));
    }

    // getArticlesAll()
    @Test
    void whenGetArticlesAll_thenStorageServiceReturnsGoodMap() {
        Assertions.assertNotNull(storageService.getArticlesAll());
        Assertions.assertFalse(storageService.getArticlesAll().isEmpty());
        Assertions.assertTrue(TestTools.isMapOfArticles(storageService.getArticlesAll()));
    }

    // getProductById
    @Test
    void whenGetProductById_thenStorageServiceReturnsProductOrEmpty() {
        var uniqueProduct = TestTools.getProductMock();
        var id = uniqueProduct.getId();

        storageService.addProduct(uniqueProduct);
        var optional = storageService.getProductById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());

        var product = optional.get();
        Assertions.assertInstanceOf(Product.class, product);
        Assertions.assertEquals(product.getTitle(), uniqueProduct.getTitle());
        Assertions.assertEquals(product.getPrice(), uniqueProduct.getPrice());

        optional = storageService.getProductById(TestTools.UUID_NOT_EXISTING_MOCK_PRODUCT);
        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isPresent());
    }

    // getArticleById
    @Test
    void whenGetArticleById_thenStorageServiceReturnsArticleOrEmpty() {
        var uniqueArticle = TestTools.getArticleMock();
        var id = uniqueArticle.getId();

        storageService.addArticle(uniqueArticle);
        var optional = storageService.getArticleById(id);

        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());

        var article = optional.get();
        Assertions.assertInstanceOf(Article.class, article);
        Assertions.assertEquals(article.getTitle(), uniqueArticle.getTitle());
        Assertions.assertEquals(article.getContent(), uniqueArticle.getContent());

        optional = storageService.getArticleById(TestTools.UUID_NOT_EXISTING_MOCK_ARTICLE);
        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isPresent());
    }

    // getSearchableItems
    @Test
    void whenGetSearchableItems_thenStorageServiceReturnsGoodCollection() {
        var optional = storageService.getArticleById(TestTools.UUID_EXISTING_MOCK_ARTICLE);
        if (optional.isEmpty()) {
            storageService.addArticle(TestTools.getArticleMock());
        }

        var searchableItems = storageService.getSearchableItems();
        Assertions.assertNotNull(searchableItems);
        Assertions.assertFalse(searchableItems.isEmpty());

        Assertions.assertTrue(TestTools.isCollectionOfSearchable(searchableItems, true));
    }
}
