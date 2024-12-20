// SkyPro
// Терских Константин, kostus.online.1974@yandex.ru, 2024
// Домашнее задание по теме "Зависимости и исключения в Spring Boot"

package org.skypro.skyshop.model.exceptions;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException() {
        super("Продукт недоступен");
    }
}
