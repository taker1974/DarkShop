// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

/**
 * Статья.<br>
 * Не содержит привязок к продукту или к магазину. Просто общий тип.
 * Поэтому класс находится в отдельном пакете, в основной иерархии магазина
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class Article implements Searchable {

    public static final String SEARCHABLE_CONTENT_KIND = "ARTICLE";

    @NotNull
    private final UUID id;

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    /**
     * Конструктор.
     *
     * @param title   заголовок статьи
     * @param content текст статьи
     */
    public Article(@NotNull String title, @NotNull String content) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
    }

    @NotNull
    public UUID getId() {
        return id;
    }

    /**
     * @return заголовок статьи
     */
    @NotNull
    public String getTitle() {
        return title;
    }

    /**
     * @return текст статьи
     */
    @NotNull
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title + "\n" + content;
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
        Article that = (Article) obj;
        return Objects.equals(this.title, that.title);
    }
}
