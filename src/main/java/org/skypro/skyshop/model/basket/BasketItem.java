// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.basket;

import org.jetbrains.annotations.NotNull;
import org.skypro.skyshop.model.product.Product;

/**
 * Неизменяемый класс с двумя полями - <br>
 * Product и его количество int.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class BasketItem {
    private final Product product;
    private final int count;

    /**
     * Конструктор.
     *
     * @param product продукт
     * @param count   количество
     */
    public BasketItem(@NotNull Product product, int count) {
        this.product = product;
        this.count = count;
    }

    /**
     * @return вид продукта в элементе корзины
     */
    @NotNull
    public Product getProduct() {
        return product;
    }

    /**
     * @return количество продукта одного типа в элементе корзины
     */
    public int getCount() {
        return count;
    }

    /**
     * @return общая стоимость продукта в элементе корзины
     */
    public int getTotalPrice() {
        return product.getPrice() * count;
    }
}
