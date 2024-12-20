// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Жизненный цикл компонентов Spring Boot приложения"

package org.skypro.skyshop.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

// https://www.baeldung.com/spring-bean-scopes

/**
 * Сервис поиска.
 *
 * @author Константин Терских, kostus.online.1974@yandex.ru, 2024
 * @version 1.1
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchService {
    /**
     * Количество результатов поиска.
     */
    public static final int MAX_RESULTS = 10_000;

    @NotNull
    private final StorageService storageService;

    /**
     * Конструктор.
     *
     * @param storageService экземпляр StorageService
     */
    public SearchService(@NotNull StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Поиск по шаблону.
     *
     * @param pattern шаблон поиска
     * @return коллекция результатов поиска
     */
    @NotNull
    public Collection<SearchResult> search(@Nullable String pattern) {
        if (Objects.isNull(pattern)) {
            return new TreeSet<>(new SearchResultComparator());
        }
        if (pattern.isEmpty()) {
            return new TreeSet<>(new SearchResultComparator());
        }

        Function<Searchable, SearchResult> toResult = SearchResult::fromSearchable;

        // Была ошибка в storage.getSearchableItems: я пытался возвращать
        // TreeSet для Searchable, не озаботившись соответствующим компаратором.
        // На этом потерял много времени, пока не начал пошаговую отладку запроса поиска
        // и не увидел, что ошибка в .collect внутри storage.

        return storageService.getSearchableItems().stream()
                .filter(searchable -> searchable.getSearchableTerm().contains(pattern))
                .distinct()
                .limit(MAX_RESULTS)
                .map(toResult)
                .collect(Collectors.toCollection(() -> new TreeSet<>(new SearchResultComparator())));
    }

    /**
     * Компаратор
     * Статьи в результатах поиска должны выводиться, начиная от статьи с самым длинным именем и
     * заканчивая статьей с самым коротким именем.
     * Если длины имен одинаковые, то статьи должны сортироваться в натуральном порядке (для строк).
     */
    private static class SearchResultComparator implements Comparator<SearchResult> {
        @Override
        public int compare(SearchResult s1, SearchResult s2) {
            int compareLength = Integer.compare(s2.getName().length(), s1.getName().length());
            if (compareLength != 0) {
                return compareLength;
            }
            return Objects.compare(s1.getName(), s2.getName(), String::compareTo);
        }
    }
}
