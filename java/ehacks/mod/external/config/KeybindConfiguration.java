package ehacks.mod.external.config;

import com.google.gson.Gson;
import ehacks.api.module.Module;
import ehacks.api.module.ModuleController;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;

public class KeybindConfiguration implements IConfiguration {

    private final File keybindConfig;

    public KeybindConfiguration() {
        this.keybindConfig = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehackslite/keybinds.txt");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.keybindConfig);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            KeybindConfigJson keybindConfig = new KeybindConfigJson();
            for (Module module : ModuleController.INSTANCE.modules) {
                String s = Keyboard.getKeyName((int) module.getKeybind());
                keybindConfig.keybinds.put(module.getName().toLowerCase(), s);
            }
            bufferedwriter.write(new Gson().toJson(keybindConfig));
            bufferedwriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void read() {
        try {
            String key;
            FileInputStream imputstream = new FileInputStream(this.keybindConfig.getAbsolutePath());
            DataInputStream datastream = new DataInputStream(imputstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(datastream));
            KeybindConfigJson keybindConfig = new Gson().fromJson(bufferedreader.readLine(), KeybindConfigJson.class);
            for (Module module : ModuleController.INSTANCE.modules) {
                if (!keybindConfig.keybinds.containsKey(module.getName().toLowerCase())) {
                    continue;
                }
                module.setKeybinding(Keyboard.getKeyIndex(keybindConfig.keybinds.get(module.getName().toLowerCase())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getConfigFilePath() {
        return "keybinds.txt";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    private class KeybindConfigJson {

        public HashMap<String, String> keybinds = new HashMap();
    }
}
