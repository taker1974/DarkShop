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
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.UUID;

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
    void whenSearchOnEmptyStore_thenReturnsEmptyParticularCollection() {
        storageService.clear();

        var searchResults = searchService.search(TestTools.SEARCH_PATTERN);

        Assertions.assertTrue(searchResults.isEmpty());
        Assertions.assertFalse(TestTools.isNotCollection(searchResults, false));
    }

    @Test
    void whenSearchExisting_thenReturnsNotEmptyParticularCollection() {
        storageService.initializeWithSamples();

        // базовый мок продукта, который добавим в хранилище
        var uniqueProduct = Mockito.mock(SimpleProduct.class);
        Mockito.when(uniqueProduct.getId()).thenReturn(TestTools.UUID_PRODUCT);

        Mockito.when(uniqueProduct.getSearchableName()).thenReturn(TestTools.PRODUCT_SEARCHABLE_NAME);
        Mockito.when(uniqueProduct.getSearchableContentKind()).thenReturn(Product.SEARCHABLE_CONTENT_KIND);
        Mockito.when(uniqueProduct.getSearchableTerm()).thenReturn(TestTools.PRODUCT_TITLE);

        storageService.addProduct(uniqueProduct);

        // поищем этот продукт
        var searchResults = searchService.search(TestTools.SEARCH_PATTERN);
        Assertions.assertFalse(searchResults.isEmpty());
        Assertions.assertTrue(TestTools.isCollectionOfSearchResult(searchResults, true));

        var foundProduct = searchResults.iterator().next();
        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(TestTools.UUID_PRODUCT, UUID.fromString(foundProduct.getId()));
    }
}
