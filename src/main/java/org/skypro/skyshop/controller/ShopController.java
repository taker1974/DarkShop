// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

/**
 * Контроллер магазина.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
@RestController
public class ShopController {
    private final StorageService storageService;
    private final SearchService searchService;
    private final BasketService basketService;

    public ShopController(StorageService storageService,
                          SearchService searchService,
                          BasketService basketService) {
        this.storageService = storageService;
        this.searchService = searchService;
        this.basketService = basketService;
    }

    @GetMapping("/products")
    @NotNull
    public Collection<Product> getProductsAll() {
        return storageService.getProductsAll().values();
    }

    @GetMapping("/articles")
    @NotNull
    public Collection<Article> getArticlesAll() {
        return storageService.getArticlesAll().values();
    }

    @GetMapping("/search")
    @NotNull
    public Collection<SearchResult> getSearchResultsAll(@Nullable String pattern) {
        return searchService.search(pattern);
    }

    @GetMapping("/basket/{id}")
    @NotNull
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProduct(id);
        return "Товар добавлен в корзину";
    }

    @GetMapping("/basket")
    @NotNull
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}
