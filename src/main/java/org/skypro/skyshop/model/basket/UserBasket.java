// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.basket;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * Класс, представляющий собой корзину для пользователя.<br>
 * Этот класс должен содержать список из BasketItem, а также<br>
 * дополнительное поле total - общую стоимость корзины.
 * Класс также должен быть неизменяемым, а его конструктор должен<br>
 * принимать в себя только список BasketItem.<br>
 * (Это из условий ДЗ.)
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class UserBasket {

    @NotNull
    private final Collection<BasketItem> basketItems;
    private final int totalPrice;

    /**
     * Конструктор класса UserBasket.
     *
     * @param basketItems список из BasketItem
     */
    public UserBasket(@NotNull Collection<BasketItem> basketItems, int totalPrice) {
        this.basketItems = basketItems;
        this.totalPrice = totalPrice;
    }

    /**
     * @return список из BasketItem
     */
    @NotNull
    public Collection<BasketItem> getBasketItems() {
        return Collections.unmodifiableCollection(basketItems);
    }

    /**
     * @return общая стоимость корзины
     */
    public int getTotalPrice() {
        return totalPrice;
    }
}
