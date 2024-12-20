// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Зависимости и исключения в Spring Boot"

package org.skypro.skyshop.model.exceptions;

import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public final class ShopError {

    @NonNull
    @Getter
    private final String code;

    @NonNull
    @Getter
    private final String message;

    public ShopError(@NotNull String code, @NotNull String message) {
        this.code = code;
        this.message = message;
    }
}
