// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.service;

import org.jetbrains.annotations.NotNull;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.exceptions.NoSuchProductException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/* Шаг 2. Сервис работы с корзиной
    Этот сервис будет объединять наш новый компонент с контроллером и позволит нам добавлять в корзину товары и
    показывать текущее состояние корзины пользователю.
    Создайте класс BasketService в пакете service. В этом классе нам нужны два private final поля.
    Одно поле будет содержать наш компонент корзины, а второе — StorageService.
    Эти поля нужно инициализировать через конструктор, как обычно, используя constructor injection.
*/

// https://www.baeldung.com/spring-bean-scopes

/**
 * Сервис корзины товаров.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    /**
     * Конструктор.
     *
     * @param storageService сервис хранения данных
     * @param productBasket  корзина товаров
     */
    public BasketService(StorageService storageService, ProductBasket productBasket) {
        this.storageService = storageService;
        this.productBasket = productBasket;
    }

    /*  Метод добавления товара в корзину по id.
        Этот метод должен принимать UUID id и ничего не возвращать.
        Внутри метода вам нужно обратиться к StorageService и проверить,
        есть ли переданный в аргументах id в списке продуктов.

        После этого в теле метода вы обращаетесь к storage service, получаете Optional и,
        если он пустой (isPresent == false), то вам нужно выбросить IllegalArgumentException().

        Если же такой товар есть в StorageService, то достаточно добавить его в корзину,
        обратившись к ее компоненту. */

    /**
     * Добавить товар в корзину.
     *
     * @param id идентификатор товара
     */
    public void addProduct(@NotNull UUID id) {
        var product = storageService.getProductById(id);
        if (product.isEmpty()) {
            throw new NoSuchProductException();
        }
        productBasket.addProduct(product.get().getId(), 1);
    }

    /*  Метод отображения корзины пользователю.
        Чтобы написать этот метод, вам нужно сделать подготовительную работу.
        Вам потребуется объект, представляющий корзину в виде JSON-объекта, чтобы показать его пользователю.
        Если мы будем просто отправлять Map<UUID, Integer> пользователю, то будет непонятно,
        какая стоимость корзины и какие продукты были добавлены. */

    /**
     * @return корзина пользователя {@link UserBasket}
     */
    @NotNull
    public UserBasket getUserBasket() {
        Function<Map.Entry<UUID, Integer>, BasketItem> toBasketItem = entry -> {
            var product = storageService.getProductById(entry.getKey());
            if (product.isEmpty()) {
                throw new NoSuchProductException();
            }
            return new BasketItem(product.get(), entry.getValue());
        };

        var userBasketItems = productBasket.getProductsAll().entrySet().stream()
                .map(toBasketItem)
                .collect(Collectors.toCollection(HashSet::new));

        var totalPrice = userBasketItems.stream()
                .mapToInt(BasketItem::getTotalPrice)
                .sum();

        return new UserBasket(userBasketItems, totalPrice);
    }
}
