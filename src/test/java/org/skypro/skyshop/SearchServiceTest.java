// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Unit-тестирование и mock-объекты"

package org.skypro.skyshop;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    /*  Тестируем реальный объект с макетными данными.
        Единственный метод:
            @NotNull Collection<SearchResult> search(@Nullable String pattern)
                !null, [!]isEmpty, Collection<>
     */

    @NotNull
    @Spy
    private StorageService storageService;

    @NotNull
    @InjectMocks
    private SearchService searchService;

    @Test
    void whenSearchOnEmptyStore_thenReturnsGoodEmptyCollection() {
        storageService.clear();
        var searchResults = searchService.search("hello");
        Assertions.assertFalse(TestTools.isNotCollection(searchResults, false));
        Assertions.assertTrue(searchResults.isEmpty());
    }

//    @Test
//    void whenSearchExisting_thenReturnsGoodNotEmptyCollection() {
//        storageService.initializeWithSamples();
//
//        var product = TestTools.getProductMock();
//        Mockito.when(product.getTitle()).thenReturn("hello");
//        Mockito.when(product.getSearchableTerm()).thenReturn("hello");
//
//        storageService.addProduct(product);
//
//        var searchResults = searchService.search("hello");
//        Assertions.assertTrue(TestTools.isCollectionOfSearchResult(searchResults, true));
//    }
}
