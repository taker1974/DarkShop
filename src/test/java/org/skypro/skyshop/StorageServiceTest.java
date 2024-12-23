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
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.StorageService;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    /*  Тестируем реальный объект с реальными данными.
        Макеты продуктов и статей - по необходимости.

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

        Тестируются в ходе других тестов:
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
    void whenGetProductsAll_thenReturnsNotEmptyParticularMap() {
        storageService.initializeWithSamples();
        var map = storageService.getProductsAll();
        Assertions.assertNotNull(map);
        Assertions.assertFalse(map.isEmpty());
        Assertions.assertTrue(TestTools.isMapOfProducts(map));
    }

    // getArticlesAll()
    @Test
    void whenGetArticlesAll_thenReturnsNotEmptyParticularMap() {
        storageService.initializeWithSamples();
        var map = storageService.getArticlesAll();
        Assertions.assertNotNull(map);
        Assertions.assertFalse(map.isEmpty());
        Assertions.assertTrue(TestTools.isMapOfArticles(map));
    }

    // getProductById
    @Test
    void whenGetProductById_thenReturnsProductOrEmpty() {
        storageService.clear();

        // попробуем получить продукт из пустого хранилища
        var optional = storageService.getProductById(TestTools.UUID_PRODUCT);
        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isEmpty());

        // базовый мок продукта, который добавим в хранилище
        var uniqueProduct = Mockito.mock(SimpleProduct.class);
        Mockito.when(uniqueProduct.getId()).thenReturn(TestTools.UUID_PRODUCT);
        Mockito.when(uniqueProduct.getTitle()).thenReturn(TestTools.PRODUCT_TITLE);
        Mockito.when(uniqueProduct.getPrice()).thenReturn(TestTools.PRODUCT_PRICE);
        var id = uniqueProduct.getId();

        storageService.initializeWithSamples();
        storageService.addProduct(uniqueProduct);

        // поищем этот продукт
        optional = storageService.getProductById(id);
        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());

        var product = optional.get();
        Assertions.assertInstanceOf(SimpleProduct.class, product);
        Assertions.assertEquals(product.getTitle(), uniqueProduct.getTitle());
        Assertions.assertEquals(product.getPrice(), uniqueProduct.getPrice());

        // поищем несуществующий продукт
        UUID notExistingId = UUID.randomUUID();
        optional = storageService.getProductById(notExistingId);
        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isPresent());
    }

    // getArticleById
    @Test
    void whenGetArticleById_thenReturnsArticleOrEmpty() {
        storageService.clear();

        // попробуем получить статью из пустого хранилища
        var optional = storageService.getArticleById(TestTools.UUID_ARTICLE);
        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isEmpty());

        // базовый мок статьи, который добавим в хранилище
        var uniqueArticle = Mockito.mock(Article.class);
        Mockito.when(uniqueArticle.getId()).thenReturn(TestTools.UUID_ARTICLE);
        Mockito.when(uniqueArticle.getTitle()).thenReturn(TestTools.ARTICLE_TITLE);
        var id = uniqueArticle.getId();

        storageService.initializeWithSamples();
        storageService.addArticle(uniqueArticle);

        // поищем эту статью
        optional = storageService.getArticleById(id);
        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());

        var article = optional.get();
        Assertions.assertInstanceOf(Article.class, article);
        Assertions.assertEquals(article.getTitle(), uniqueArticle.getTitle());

        // поищем несуществующую статью
        UUID notExistingId = UUID.randomUUID();
        optional = storageService.getArticleById(notExistingId);
        Assertions.assertNotNull(optional);
        Assertions.assertFalse(optional.isPresent());
    }

    // getSearchableItems
    @Test
    void whenGetSearchableItems_thenReturnsParticularCollection() {
        storageService.clear();

        // попробуем получить коллекцию из пустого хранилища
        var searchableItems = storageService.getSearchableItems();
        Assertions.assertNotNull(searchableItems);
        Assertions.assertTrue(searchableItems.isEmpty());

        storageService.initializeWithSamples();

        searchableItems = storageService.getSearchableItems();
        Assertions.assertNotNull(searchableItems);
        Assertions.assertFalse(searchableItems.isEmpty());
        Assertions.assertTrue(TestTools.isCollectionOfSearchable(searchableItems, true));
    }

    /*
        Вспомогательные методы.
     */

    // clear
    @Test
    void whenClear_thenStorageServiceReturnsEmptyCollection() {
        storageService.initializeWithSamples();

        var searchableItems = storageService.getSearchableItems();
        Assertions.assertNotNull(searchableItems);
        Assertions.assertFalse(searchableItems.isEmpty());
        Assertions.assertTrue(TestTools.isCollectionOfSearchable(searchableItems, true));

        storageService.clear();

        searchableItems = storageService.getSearchableItems();
        Assertions.assertNotNull(searchableItems);
        Assertions.assertTrue(searchableItems.isEmpty());
    }
}
