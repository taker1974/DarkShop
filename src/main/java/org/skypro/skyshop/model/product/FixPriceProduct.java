// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.product;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPrice());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Product that = (Product) obj;
        return Objects.equals(this.getTitle(), that.getTitle()) &&
                this.getPrice() == that.getPrice();
    }
}
