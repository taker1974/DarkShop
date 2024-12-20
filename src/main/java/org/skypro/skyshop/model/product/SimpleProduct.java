// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.product;

import org.jetbrains.annotations.NotNull;

/**
 * Обычный продукт.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class SimpleProduct extends Product {
    private final int price;

    /**
     * Конструктор.
     *
     * @param title название продукта
     * @param price цена продукта
     */
    public SimpleProduct(@NotNull String title, int price) {
        super(title);

        if (price <= 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной или быть равной нулю");
        }
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    @NotNull
    public String toString() {
        return getTitle() + ": " + getPrice();
    }
}
