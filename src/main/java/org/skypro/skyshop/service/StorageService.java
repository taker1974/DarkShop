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

    private void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    private void addArticle(Article article) {
        articles.put(article.getId(), article);
    }

    /**
     * Инициализация для демонстрации работы сервиса.
     */
    public void initializeWithSamples() {
        Product product1 = new SimpleProduct("Молоко", 80);
        Product product2 = new FixPriceProduct("Хлеб");
        Product product3 = new FixPriceProduct("Сыр");
        Product product4 = new DiscountedProduct("Масло", 400, 20);
        Product product5 = new DiscountedProduct("Яйца", 140, 10);
        Product product6 = new SimpleProduct("Мясо", 900);
        Product product7 = new SimpleProduct("Бластер", 200);
        Product product8 = new SimpleProduct("Молоко \"Пискарёвское\" пастеризованное, 850 г", 80);
        products.clear();
        addProduct(product1);
        addProduct(product2);
        addProduct(product3);
        addProduct(product4);
        addProduct(product5);
        addProduct(product6);
        addProduct(product7);
        addProduct(product8);

        Article article1 = new Article("Хлеб и молоко - можно ли выжить?",
                "Выжить на хлебе и молоке невозможно, " +
                        "так как ни один продукт не способен дать человеку всё, " +
                        "что нужно для здорового образа жизни.");
        Article article2 = new Article("Что нужно есть время от времени",
                "При составлении рациона питания стоит учитывать " +
                        "индивидуальные особенности человека, " +
                        "в том числе биологические ритмы. " +
                        "Но мясо есть необходимо.");
        Article article3 = new Article("Lorem Ipsum,",
                "У меня когда-то давно был автомобиль Toyota Ipsum в 10-м кузове. " +
                        "Лучшая машина на планете Земля.");
        Article article4 = new Article("Lorem Ipsum про автомобиль Toyota Ipsum в 10-м кузове.",
                "У меня когда-то давно был автомобиль Toyota Ipsum в 10-м кузове. " +
                        "Лучшая машина на планете Земля - это минивэн Toyota Ipsum");
        articles.clear();
        addArticle(article1);
        addArticle(article2);
        addArticle(article3);
        addArticle(article4);
    }
}
