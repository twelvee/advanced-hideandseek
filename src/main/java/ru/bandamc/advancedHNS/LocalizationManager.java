package ru.bandamc.advancedHNS;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;

public class LocalizationManager {
    private static LocalizationManager instance;
    private static YamlConfiguration messages;

    public static LocalizationManager getInstance() {
        if (instance == null) {
            instance = new LocalizationManager();
        }
        return instance;
    }

    private LocalizationManager() {
        messages = YamlConfiguration.loadConfiguration(getResourceFile("plugins/AdvancedHNS/localization.yml"));
    }

    private File getResourceFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(filename)) {
                if (in != null) {
                    file.createNewFile();
                    java.nio.file.Files.copy(in, file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public String getLocale(String playerLocale) {
        // playerLocale
        // ru_ru
        // en_us
        String requiredLocale = playerLocale.substring(0, 2);
        if (messages.get(requiredLocale) != null) {
            return requiredLocale;
        }
        return getLocalization("fallback_locale");
    }

    public String getLocalization(String key) {
        return messages.getString(key);
    }
}