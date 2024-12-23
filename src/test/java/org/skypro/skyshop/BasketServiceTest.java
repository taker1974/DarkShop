// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Unit-тестирование и mock-объекты"

package org.skypro.skyshop;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.exceptions.NoSuchProductException;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    /*  Этот сервис тестируем максимально по условиям ДЗ:

        Сервис корзины достаточно сложен для тестирования: он использует сразу два мока —
        ProductBasket и StorageService.
        Вам нужно проверить следующие сценарии:
        - Добавление несуществующего товара в корзину приводит к выбросу исключения.
        - Добавление существующего товара вызывает метод addProduct у мока ProductBasket.
        - Метод getUserBasket возвращает пустую корзину, если ProductBasket пуст.
        - Метод getUserBasket возвращает подходящую корзину, если в ProductBasket есть товары.
     */

    @NotNull
    @Spy
    private StorageService storageService;

    @NotNull
    @Spy
    private ProductBasket productBasket;

    @NotNull
    @InjectMocks
    private BasketService basketService;

    @Test
    // Добавление несуществующего товара в корзину приводит к выбросу исключения.
    void whenAddDiscontinuedProduct_thenThrowsException() {
        storageService.initializeWithSamples();

        UUID id = UUID.randomUUID();

        // добавим отсутствующий в продаже товар в корзину
        Assertions.assertThrows(NoSuchProductException.class, () -> basketService.addProduct(id));
    }

    @Test
    // Добавление существующего товара вызывает метод addProduct у мока ProductBasket.
    void whenAddActualProduct_thenCallsAddProduct() {
        storageService.initializeWithSamples();

        // добавим свой продукт в хранилище
        UUID id = TestTools.UUID_PRODUCT;
        var product = Mockito.mock(SimpleProduct.class);
        Mockito.when(product.getId()).thenReturn(id);

        storageService.addProduct(product);

        basketService.addProduct(id);
        Mockito.verify(productBasket).addProduct(id, 1);
    }

    @Test
    // Метод getUserBasket возвращает пустую корзину, если ProductBasket пуст.
    void whenProductBasketIsEmpty_thenGetUserBasketReturnsEmptyUserBasket() {
        var userBasket = basketService.getUserBasket();
        Assertions.assertTrue(userBasket.getBasketItems().isEmpty());
    }

    @Test
    // Метод getUserBasket возвращает подходящую корзину, если в ProductBasket есть товары.
    void whenProductBasketIsNotEmpty_thenGetUserBasketReturnsNotEmptyUserBasket() {
        storageService.initializeWithSamples();

        // добавим свой продукт в хранилище
        UUID id = TestTools.UUID_PRODUCT;
        var product = Mockito.mock(SimpleProduct.class);
        Mockito.when(product.getId()).thenReturn(id);

        storageService.addProduct(product);

        basketService.addProduct(id);

        var userBasket = basketService.getUserBasket();
        Assertions.assertFalse(userBasket.getBasketItems().isEmpty());
    }
}
