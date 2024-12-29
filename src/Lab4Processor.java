import java.util.ArrayList;
import java.util.Scanner;

/**
 * Клас, що представляє одну літеру.
 */
class Letter {
    private char character;

    /**
     * Конструктор для ініціалізації літери.
     *
     * @param character символ, який буде збережений як літера
     */
    public Letter(char character) {
        this.character = character;
    }

    /**
     * Отримати символ літери.
     *
     * @return символ літери
     */
    public char getCharacter() {
        return character;
    }

}

/**
 * Клас, що представляє слово, яке складається з масиву літер.
 */
class Word {
    private ArrayList<Letter> letters;

    /**
     * Конструктор для ініціалізації слова.
     *
     * @param word строка, з якої буде побудовано слово
     */
    public Word(String word) {
        letters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            letters.add(new Letter(c));
        }
    }

    /**
     * Отримати слово як рядок.
     *
     * @return слово як строка
     */
    public String getWord() {
        StringBuilder word = new StringBuilder();
        for (Letter letter : letters) {
            word.append(letter.getCharacter());
        }
        return word.toString();
    }

    /**
     * Отримати довжину слова.
     *
     * @return довжина слова
     */
    public int length() {
        return letters.size();
    }
}

/**
 * Клас, що представляє речення, яке складається з масиву слів та розділових знаків.
 */
class Sentence {
    private ArrayList<Word> words;
    private ArrayList<Character> punctuationMarks;

    /**
     * Конструктор для ініціалізації речення.
     *
     * @param sentence строка, з якої буде побудоване речення
     */
    public Sentence(String sentence) {
        words = new ArrayList<>();
        punctuationMarks = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();

        for (char c : sentence.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                currentWord.append(c);
            } else {
                if (currentWord.length() > 0) {
                    words.add(new Word(currentWord.toString()));
                    currentWord.setLength(0);
                }
                if (c == '?' || c == '.' || c == '!') {
                    punctuationMarks.add(c);
                }
            }
        }
    }

    /**
     * Отримати список слів у реченні.
     *
     * @return список слів
     */
    public ArrayList<Word> getWords() {
        return words;
    }


    /**
     * Перевіряє, чи є це питальне речення (з розділовим знаком '?').
     *
     * @return true, якщо речення питальне, інакше false
     */
    public boolean isQuestion() {
        return punctuationMarks.contains('?');
    }
}

/**
 * Клас, що представляє текст, що складається з масиву речень.
 */
class Text {
    private ArrayList<Sentence> sentences;

    /**
     * Конструктор для ініціалізації тексту.
     *
     * @param text строка тексту, з якої буде побудовано текст
     */
    public Text(String text) {
        sentences = new ArrayList<>();
        String[] sentenceStrings = text.split("(?<=[.!?])\\s*");

        for (String sentence : sentenceStrings) {
            sentences.add(new Sentence(sentence));
        }
    }

    /**
     * Отримати список речень у тексті.
     *
     * @return список речень
     */
    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

}

/**
 * Основний клас для обробки тексту.
 */
public class Lab4Processor {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введіть текст:");
            String textInput = scanner.nextLine();

            System.out.print("Введіть довжину слова для пошуку: ");
            int wordLength = scanner.nextInt();

            // Створюємо об'єкт класу Text та виконуємо обробку тексту
            Text text = new Text(textInput);
            ArrayList<Word> result = findWordsInQuestionSentences(text, wordLength);

            // Виводимо результати
            System.out.println("Слова довжиною " + wordLength + " у питальних реченнях:");
            for (Word word : result) {
                System.out.println(word.getWord());
            }

        } catch (Exception e) {
            System.err.println("Виникла помилка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Знаходить та повертає унікальні слова заданої довжини з питальних речень тексту.
     *
     * @param text вхідний текст у вигляді об'єкта класу Text
     * @param wordLength довжина слів для пошуку
     * @return ArrayList, що містить унікальні слова відповідної довжини
     */
    public static ArrayList<Word> findWordsInQuestionSentences(Text text, int wordLength) {
        ArrayList<Word> words = new ArrayList<>();
        for (Sentence sentence : text.getSentences()) {
            if (sentence.isQuestion()) {
                for (Word word : sentence.getWords()) {
                    if (word.length() == wordLength && !containsWord(words, word)) {
                        words.add(word);
                    }
                }
            }
        }
        return words;
    }

    /**
     * Перевіряє, чи вже існує слово в списку.
     *
     * @param words список слів
     * @param word слово для перевірки
     * @return true, якщо слово вже є в списку, інакше false
     */
    private static boolean containsWord(ArrayList<Word> words, Word word) {
        for (Word existingWord : words) {
            if (existingWord.getWord().equals(word.getWord())) {
                return true;
            }
        }
        return false;
    }
}
