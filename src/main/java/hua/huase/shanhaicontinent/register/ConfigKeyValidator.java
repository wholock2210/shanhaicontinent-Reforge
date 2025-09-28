package hua.huase.shanhaicontinent.register;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigKeyValidator {

















































































    private static final String CORRECT_KEY = "ccEzBp43nzpcjc5UFykc";
    private static final Path CONFIG_PATH = Paths.get("config/on-singleplayer.toml");

    public static boolean isKeyValid() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                Files.writeString(CONFIG_PATH, "请输入密钥");
                return false;
            }
            String userKey = Files.readString(CONFIG_PATH).trim();
            return CORRECT_KEY.equals(userKey);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}