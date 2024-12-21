// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.model.search;

import org.jetbrains.annotations.NotNull;

/**
 * Результат поиска.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
public final class SearchResult {

    @NotNull
    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final String contentType;

    /**
     * Конструктор.
     *
     * @param id          ID
     * @param name        имя
     * @param contentType тип
     */
    public SearchResult(@NotNull String id, @NotNull String name, @NotNull String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    /**
     * Фабричный метод, возвращающий SearchResult из Searchable.
     *
     * @param searchable экземпляр Searchable
     * @return экземпляр SearchResult
     */
    public static SearchResult fromSearchable(@NotNull Searchable searchable) {
        return new SearchResult(searchable.getId().toString(),
                searchable.getSearchableName(),
                searchable.getSearchableContentKind());
    }

    /**
     * @return ID результата поиска
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * @return имя результата поиска
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * @return тип содержимого поиска
     */
    @NotNull
    public String getContentType() {
        return contentType;
    }
}
