// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.product;

import org.jetbrains.annotations.NotNull;

/**
 * Продукт по фиксированной цене.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class FixPriceProduct extends Product {
    public static final int CURRENT_FIXED_PRICE = 100;

    /**
     * Конструктор.
     *
     * @param title название продукта
     */
    public FixPriceProduct(@NotNull String title) {
        super(title);
    }

    @Override
    public int getPrice() {
        return CURRENT_FIXED_PRICE;
    }

    @Override
    public String toString() {
        return getTitle() + ": Фиксированная цена " + CURRENT_FIXED_PRICE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
