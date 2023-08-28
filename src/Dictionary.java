import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Dictionary {
    private MultiMap<String, String> dict = new MultiMap<String, String>();
    private String dictPath;

    public boolean readDictionaryFile(String dictPath) {
        File file = new File(dictPath);
        if(file.exists() && file.isFile() && getFileExtension(file).equals("txt")) {
            this.dictPath = dictPath;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(dictPath));
                String line = reader.readLine();

                while(line != null) {
                    String[] splitLine = line.split("-");
                    dict.put(splitLine[0], splitLine[1]);
                    line = reader.readLine();
                }

                reader.close();
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    public void showDictionaryFile() {
        try {
            if(new File(dictPath).exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(dictPath));
                String line = reader.readLine();
                System.out.println("-------------");
                while(line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
                System.out.println("-------------");
                reader.close();
                System.out.println(dict.keySet());
            }
            else {
                System.out.println("[Ошибка чтения файла]\n");
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList findWordMap(String key) {
        if(!dict.isEmpty()) {
            if(dict.get(key) != null)
                return new ArrayList(dict.get(key));
            else {
                ArrayList<String> ans = new ArrayList<>();
                ans.add("Указанного слова нет в словаре");
                return ans;
            }
        }
        else {
            ArrayList<String> ans = new ArrayList<>();
            ans.add("Словарь пуст");
            return ans;
        }
    }

    public boolean checkWordEng(String word) {
        boolean onlyLatinAlphabet = word.matches("^[a-zA-Z]+$");
        int len = word.length();

        return onlyLatinAlphabet && len <= 32;
    }

    public boolean checkWordRus(String word) {
        boolean onlyLatinAlphabet = word.matches("^[а-яА-Я]+$");
        int len = word.length();

        return onlyLatinAlphabet && len <= 32;
    }

    public String addWordMap(String key, String value) {
        if(new File(dictPath).exists()) {
            dict.put(key, value);
            writeDictionary();
            return "[Добавлено: " + key + "-" + value + "]\n";
        }
        else {
            return "[Ошибка чтения файла]\n";
        }
    }

    public String deleteKeyMap(String key) {
        if(dict.get(key) != null) {
            dict.remove(key);
            writeDictionary();
            return "[Удалены все значения слова " + key + "]\n";
        }
        else {
            return "[Указанного слова не существует]\n";
        }
    }

    public String deleteKeyValueMap(String key, String value) {
        if(dict.get(key) != null) {
            ArrayList<String> values = new ArrayList<>(dict.get(key));
            if(values.contains(value)) {
                dict.remove(key, value);
                writeDictionary();
                return "[Удалена пара " + key + "-" + value + "]\n";
            }
            else {
                return "[Для слова " + key + " нет перевода " + value + "]\n";
            }
        }
        else {
            return "[Указанного слова не существует]\n";
        }
    }

    private void writeDictionary() {
        if(new File(dictPath).exists()) {
            ArrayList<String> keys = new ArrayList<String>(dict.keySet());
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(dictPath));
                for(String k : keys) {
                    ArrayList<String> values = new ArrayList<String>(dict.get(k));
                    for(String v : values) {
                        writer.write(k + "-" + v + "\n");
                    }
                }
                writer.close();
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public String getDictPath() {
        return dictPath;
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    public void readDictHashMap() {
        System.out.println(dict);
    }
}
