// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.product;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Продукт со скидкой.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class DiscountedProduct extends Product {
    private final int basePrice;
    private final int discount;

    /**
     * Конструктор.
     *
     * @param title     название продукта
     * @param basePrice базовая цена продукта
     * @param discount  скидка, 0..100%
     */
    public DiscountedProduct(@NotNull String title, int basePrice, int discount) {
        super(title);

        if (basePrice <= 0) {
            throw new IllegalArgumentException("Базовая цена не может быть отрицательной или быть равной нулю");
        }
        this.basePrice = basePrice;

        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 100%");
        }
        this.discount = discount;
    }

    @Override
    public int getPrice() {
        return basePrice - (int) ((double)(basePrice * discount) / 100.0);
    }

    @Override
    public String toString() {
        return getTitle() + ": " + getPrice() + " (скидка " + discount + "%)";
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
