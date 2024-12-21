// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.basket;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* Шаг 1. Добавление компонента корзины
    Корзина будет представлять из себя @SessionScoped.
    Компонент внутри будет содержать только Map с id товара в качестве ключа и Integer в качестве значения.
    Integer нам нужен, так как один товар можно добавлять в корзину несколько раз.
    Создайте класс ProductBasket в подпакете model.basket
*/

// https://www.baeldung.com/spring-bean-scopes

/**
 * Корзина товаров.
 * Содержит информацию об UUID продукта о количестве каждого товара в корзине.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
@Component
@SessionScope
public class ProductBasket {

    public static final int BASKET_INITIAL_CAPACITY = 10;

    @NotNull
    private final Map<UUID, Integer> basket;

    /**
     * Конструктор корзины.
     */
    public ProductBasket() {
        this.basket = HashMap.newHashMap(BASKET_INITIAL_CAPACITY);
    }

    /*  Метод добавления продукта в корзину. Он будет принимать UUID id
        и не будет ничего возвращать. */

    /**
     * Добавить товар в корзину.
     *
     * @param productId UUID продукта
     * @param quantity  количество добавляемых продуктов
     */
    public void addProduct(@NotNull UUID productId, int quantity) {
        if (quantity <= 0) {
            quantity = 1;
        }
        var value = basket.getOrDefault(productId, 0) + quantity;
        basket.put(productId, value);
    }

    /*  Метод получения всех продуктов, которые сейчас есть в корзине. Он должен возвращать ту же
        мапу Map<UUID, Integer>, которая хранится в поле.
        Чтобы защитить нас от возможных изменений этой мапы, нужно обернуть ее в Collections.unmodifiableMap */

    /**
     * @return массив товаров Map<UUID, Integer>, обёрнутый в Collections.unmodifiableMap
     */
    @NotNull
    public Map<UUID, Integer> getProductsAll() {
        return Collections.unmodifiableMap(basket);
    }
}
