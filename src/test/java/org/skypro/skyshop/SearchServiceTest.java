// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Unit-тестирование и mock-объекты"

package org.skypro.skyshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.BaseStubbing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    private final SearchService searchService;

    public SearchServiceTest() {
        this.storageService = new StorageService();
        this.searchService = new SearchService(storageService);
    }

    @Test
    void whenSearchOnEmptyStorage_thenReturnsEmptyCollection() {
        Mockito.when(storageService.getProductsAll()).thenReturn(null);
        storageService.clear();
        Assertions.assertTrue(searchService.search("some pattern").isEmpty());
    }

    @Test
    void whenSearchStrange_thenReturnsEmptyCollection() {
        storageService.initializeWithSamples();
        Assertions.assertTrue(searchService.search("some pattern").isEmpty());
        Assertions.assertTrue(searchService.search("").isEmpty());
        Assertions.assertTrue(searchService.search(null).isEmpty());
    }

    @Test
    void whenSearchExisting_thenReturnsNotEmptyCollection() {
        var p = Mockito.mock(Product.class);
        Mockito.when(p.getTitle()).thenReturn("Молоко");

        storageService.initializeWithSamples();
        Assertions.assertFalse(searchService.search("Молоко").isEmpty());
    }
}
