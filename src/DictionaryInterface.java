import java.io.File;
import java.util.*;

public class DictionaryInterface {

    private DictionaryType dicType;
    private Dictionary dictionary;
    private Scanner scan;
    public DictionaryInterface() {
        scan = new Scanner(System.in);
        System.out.println("Добро пожаловать!");
        showMainMenu();
    }

    private void loadFile() {
        boolean fileCorrect = false;
        while(!fileCorrect) {
            System.out.println("Укажите путь к файлу: ");
            fileCorrect = dictionary.readDictionaryFile(scan.next());

            if(!fileCorrect)
                System.out.println("[Ошибка чтения файла]\n");
        }
    }

    private void showMainMenu() {
        String dicTypeInput = "";

        while(!(dicTypeInput.equals("1") || dicTypeInput.equals("2"))) {
            System.out.println("Выберите словарь(укажите соответствующую цифру):\n\t1 - Русско-английский\n\t2 - Англо-русский\n\t0 - Выход");

            dicTypeInput = scan.next();

            if(dicTypeInput.equals("0"))
                System.exit(0);

            if(!(dicTypeInput.equals("1") || dicTypeInput.equals("2")))
                System.out.println("[Указанного словаря не существует]\n");
        }
        dicType = DictionaryType.values()[Integer.parseInt(dicTypeInput)-1];
        dictionary = new Dictionary();
        loadFile();
        showFunctionMenu();
    }

    private void showFunctionMenu() {
        String actionInput = "";
        String[] menuItems = {"0", "1", "2", "3", "4"};
        while(!Arrays.asList(menuItems).contains(actionInput)) {
            System.out.println("[Словарь: " + dicType +" (" + dictionary.getDictPath() + ")]");
            System.out.println("Выберите действие(укажите соответствующую цифру):\n\t1 - Просмотреть словарь\n\t2 - Найти слово\n\t" +
                    "3 - Добавить слово\n\t4 - Удалить слово\n\t0 - Назад");

            actionInput = scan.next();

            if(!Arrays.asList(menuItems).contains(actionInput))
                System.out.println("[Выбранного действия не существует]\n");
        }

        switch (actionInput) {
            case "0":
                showMainMenu();
                break;
            case "1":
                showDictionary();
                showFunctionMenu();
                break;
            case "2":
                findWord();
                showFunctionMenu();
                break;
            case "3":
                addWord();
                showFunctionMenu();
                break;
            case "4":
                deleteWord();
                showFunctionMenu();
                break;
        }
    }

    private void showDictionary() {
        System.out.println("[Словарь: " + dicType +" (" + dictionary.getDictPath() + ")]");
        dictionary.showDictionaryFile();
    }
    private void findWord() {
        System.out.println("[Найти слово в " + dicType +" (" + dictionary.getDictPath() + ")]");
        System.out.println("Введите слово-ключ:");
        System.out.println("-------------");
        System.out.println(dictionary.findWordMap(scan.next()));
        System.out.println("-------------");
    }

    private void addWord() {
        System.out.println("[Добавить слово в " + dicType +" (" + dictionary.getDictPath() + ")]");
        System.out.println("Введите слово-ключ:");

        boolean wordValidate = false;
        String keyWord = "", valueWord = "";
        while(!wordValidate) {
            keyWord = scan.next();
            if(dicType == DictionaryType.ENG_RUS)
                wordValidate = dictionary.checkWordEng(keyWord);
            else
                wordValidate = dictionary.checkWordRus(keyWord);

            if(!wordValidate) {
                System.out.println("[Ошибка добавления. Проверьте язык и кол-во символов в слове(до 34)]");
            }
        }

        wordValidate = false;

        System.out.println("Введите слово-перевод:");

        while(!wordValidate) {
            valueWord = scan.next();
            if(dicType == DictionaryType.ENG_RUS)
                wordValidate = dictionary.checkWordRus(valueWord);
            else
                wordValidate = dictionary.checkWordEng(valueWord);

            if(!wordValidate) {
                System.out.println("[Ошибка добавления. Проверьте язык и кол-во символов в слове(до 34)]");
            }
        }

        System.out.println(dictionary.addWordMap(keyWord, valueWord));
    }

    private void deleteWord() {
        String deleteType = "";
        String[] menuItems = {"1", "2", "3"};
        while(!Arrays.asList(menuItems).contains(deleteType)) {
            System.out.println("[Удалить слово из" + dicType +" (" + dictionary.getDictPath() + ")]");
            System.out.println("Выберите вариант удаления:\n\t1 - Удаление по паре слов(по ключу и значению)\n\t" +
                    "2 - Удалить все встречные слова(по ключу)");
            deleteType = scan.next();

            if(!Arrays.asList(menuItems).contains(deleteType))
                System.out.println("[Выбранного действия не существует]\n");
        }

        System.out.println("Введите слово-ключ:");
        String keyWord = scan.next();

        switch (deleteType) {
            case "1":
                System.out.println("Введите слово-перевод:");
                String valueWord = scan.next();
                System.out.println(dictionary.deleteKeyValueMap(keyWord, valueWord));
                break;
            case "2":
                System.out.println(dictionary.deleteKeyMap(keyWord));
                break;
        }
    }
}
