// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

/**
 * Продукт.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public abstract class Product implements Searchable {
    public static final String SEARCHABLE_CONTENT_KIND = "PRODUCT";

    @NotNull
    private final UUID id;

    @NotNull
    private final String title;

    /**
     * Конструктор.
     *
     * @param title название продукта
     */
    protected Product(@NotNull String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Наименование продукта не может быть пустым");
        }
        this.id = UUID.randomUUID();
        this.title = title;
    }

    @NotNull
    public UUID getId() {
        return id;
    }

    /**
     * @return название продукта
     */
    @NotNull
    public String getTitle() {
        return title;
    }

    /**
     * @return цена продукта
     */
    public abstract int getPrice();

    @Override
    public String toString() {
        return title;
    }

    /**
     * @return true, если продукт специальный
     */
    public boolean isSpecial() {
        return false;
    }

    @JsonIgnore
    @Override
    @NotNull
    public String getSearchableName() {
        return this.getClass().getSimpleName() + "-" +
                SEARCHABLE_CONTENT_KIND + "-" +
                Integer.toHexString(Math.abs(hashCode())) + ": " +
                title;
    }

    @Override
    @NotNull
    public String getSearchableTerm() {
        return toString();
    }

    @JsonIgnore
    @Override
    @NotNull
    public String getSearchableContentKind() {
        return SEARCHABLE_CONTENT_KIND;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
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
        return Objects.equals(this.title, that.title);
    }
}
