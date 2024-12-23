// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.service;

import org.jetbrains.annotations.NotNull;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://www.baeldung.com/spring-bean-scopes

/**
 * Сервис хранения.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public final class StorageService {
    /**
     * Начальная вместимость хранилища ассортимента товаров.
     */
    public static final int INITIAL_CAPACITY_PRODUCTS = 100;

    /**
     * Начальная вместимость хранилища статей.
     */
    public static final int INITIAL_CAPACITY_ARTICLES = 100;

    @NotNull
    private final Map<UUID, Product> products;

    @NotNull
    private final Map<UUID, Article> articles;

    /**
     * Конструктор.
     */
    public StorageService() {
        products = HashMap.newHashMap(INITIAL_CAPACITY_PRODUCTS);
        articles = HashMap.newHashMap(INITIAL_CAPACITY_ARTICLES);
        initializeWithSamples();
    }

    /**
     * @return коллекцию всего ассортимента продуктов
     */
    @NotNull
    public Map<UUID, Product> getProductsAll() {
        return Collections.unmodifiableMap(products);
    }

    /**
     * Получение товара по идентификатору.
     *
     * @param id идентификатор
     * @return товар или пустой
     */
    @NotNull
    public Optional<Product> getProductById(@NotNull UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Получение статьи по идентификатору.
     *
     * @param id идентификатор
     * @return статья или пустой
     */
    @NotNull
    public Optional<Article> getArticleById(@NotNull UUID id) {
        return Optional.ofNullable(articles.get(id));
    }

    /**
     * @return коллекцию всех статей
     */
    @NotNull
    public Map<UUID, Article> getArticlesAll() {
        return Collections.unmodifiableMap(articles);
    }

    /**
     * @return коллекцию всех товаров и статей
     */
    @NotNull
    public Collection<Searchable> getSearchableItems() {
        var collection = Stream.of(products.values(), articles.values());
        return collection
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Добавление товара в ассортимент.
     *
     * @param product товар
     */
    public void addProduct(@NotNull Product product) {
        products.put(product.getId(), product);
    }

    /**
     * Добавление статьи.
     *
     * @param article статья
     */
    public void addArticle(@NotNull Article article) {
        articles.put(article.getId(), article);
    }

    /**
     * Очистка данных.
     */
    public void clear() {
        products.clear();
        articles.clear();
    }

    /**
     * Инициализация для демонстрации работы сервиса.
     */
    public void initializeWithSamples() {
        clear();

        addProduct(new SimpleProduct("Молоко", 80));
        addProduct(new FixPriceProduct("Хлеб"));
        addProduct(new FixPriceProduct("Сыр"));
        addProduct(new DiscountedProduct("Масло", 400, 20));
        addProduct(new DiscountedProduct("Яйца", 140, 10));
        addProduct(new SimpleProduct("Мясо", 900));
        addProduct(new SimpleProduct("Бластер", 200));
        addProduct(new SimpleProduct("Молоко \"Пискарёвское\" пастеризованное, 850 г", 80));

        addArticle(new Article("Хлеб и молоко - можно ли выжить?",
                "Выжить на хлебе и молоке невозможно, " +
                        "так как ни один продукт не способен дать человеку всё, " +
                        "что нужно для здорового образа жизни."));
        addArticle(new Article("Что нужно есть время от времени",
                "При составлении рациона питания стоит учитывать " +
                        "индивидуальные особенности человека, " +
                        "в том числе биологические ритмы. " +
                        "Но мясо есть необходимо."));
        addArticle(new Article("Lorem Ipsum,",
                "У меня когда-то давно был автомобиль Toyota Ipsum в 10-м кузове. " +
                        "Лучшая машина на планете Земля."));
        addArticle(new Article("Lorem Ipsum про автомобиль Toyota Ipsum в 10-м кузове.",
                "У меня когда-то давно был автомобиль Toyota Ipsum в 10-м кузове. " +
                        "Лучшая машина на планете Земля - это минивэн Toyota Ipsum"));
    }
}
