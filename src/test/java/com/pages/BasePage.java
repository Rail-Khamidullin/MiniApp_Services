package com.pages;

import com.microsoft.playwright.Page;

// Базовый класс для всех страниц.
public abstract class BasePage {

    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    // Проверка, что страница загружена (абстрактный метод, где каждая страница реализует свою проверку)
    public abstract boolean isPageLoaded();
}