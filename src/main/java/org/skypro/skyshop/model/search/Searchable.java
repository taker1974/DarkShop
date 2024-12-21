// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.search;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Searchable {
    /**
     * @return Идентификатор
     */
    @NotNull
    UUID getId();

    /**
     * @return Имя объекта Searchable
     */
    @NotNull
    default String getSearchableName() {
        return this.getClass().getSimpleName() + "-" + this.hashCode();
    }

    /**
     * @return Содержимое, по которому производится поиск
     */
    @NotNull
    String getSearchableTerm();

    /**
     * @return Вид содержимого, по которому производится поиск
     */
    @NotNull
    String getSearchableContentKind();
}
