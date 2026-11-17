package com.cookies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.SameSiteAttribute;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class CookiesLoader {

    private CookiesLoader() {}

    public static void loadFromFile(BrowserContext context, Path jsonFile) {
        try {
            String raw = Files.readString(jsonFile);
            JsonNode root = new ObjectMapper().readTree(raw);

            List<Cookie> cookies = new ArrayList<>();
            for (JsonNode node : root.path("cookies")) {
                Cookie c = new Cookie(node.path("name").asText(),
                        node.path("value").asText())
                        .setDomain(node.path("domain").asText())
                        .setPath(node.path("path").asText(""))
                        .setHttpOnly(node.path("httpOnly").asBoolean(false))
                        .setSecure(node.path("secure").asBoolean(false))
                        .setSameSite(parseSameSite(node.path("sameSite").asText(null)));

                double expires = node.path("expires").asDouble(-1);
                if (expires > 0) {
                    c.setExpires(expires);
                }
                cookies.add(c);
            }

            context.addCookies(cookies);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Не удалось загрузить cookies из " + jsonFile
                            + " | абсолютный путь: " + jsonFile.toAbsolutePath()
                            + " | exists: " + Files.exists(jsonFile)
                            + " | cause: " + e.getClass().getSimpleName() + ": " + e.getMessage(), e);
        }
    }

    /** localStorage из того же файла (поле origins[].localStorage) */
    public static void loadLocalStorage(BrowserContext context, Path jsonFile) {
        try {
            String raw = Files.readString(jsonFile);
            JsonNode root = new ObjectMapper().readTree(raw);
            JsonNode origins = root.path("origins");
            if (!origins.isArray()) return;

            // откроем "пустую" страницу на нужном origin и выставим localStorage
            for (JsonNode origin : origins) {
                String url = origin.path("origin").asText();
                if (url.isBlank()) continue;

                var page = context.newPage();
                page.navigate(url);
                for (JsonNode item : origin.path("localStorage")) {
                    String name = item.path("name").asText();
                    String value = item.path("value").asText();
                    page.evaluate("([k, v]) => window.localStorage.setItem(k, v)",
                            java.util.List.of(name, value));
                }
                page.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить localStorage из " + jsonFile, e);
        }
    }

    private static SameSiteAttribute parseSameSite(String value) {
        if (value == null) return null;
        return switch (value) {
            case "Strict" -> SameSiteAttribute.STRICT;
            case "Lax"    -> SameSiteAttribute.LAX;
            case "None"   -> SameSiteAttribute.NONE;
            default       -> null;
        };
    }
}
