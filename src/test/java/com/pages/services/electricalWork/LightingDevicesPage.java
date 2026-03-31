package com.pages.services.electricalWork;

import com.microsoft.playwright.Page;
import com.pages.BasePage;

public class LightingDevicesPage extends BasePage {

    public LightingDevicesPage(Page page) {
        super(page);
    }

    @Override
    public boolean isPageLoaded() {
        return false;
    }
}
