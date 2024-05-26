
import java.util.*;

public class LempelZiv {
    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    public static String compress(String input) {
        StringBuilder output = new StringBuilder();
        int cursor = 0;
        int windowSize = 100;

        while (cursor < input.length()) {
            int length = 1;
            int prevMatch = 0;

            while (true) {
                int match = KMP.search(input.substring(cursor, cursor + length),
                        input.substring(Math.max(0, cursor - windowSize), cursor));

                if (match != -1) {
                    prevMatch = cursor - (Math.max(cursor - windowSize, 0) + match);
                    length++;
                    if (cursor + length >= input.length()) {
                        if (cursor + length - 1 < input.length()) {
                            output.append("[" + prevMatch + "|" + (length - 1) + "|" + input.charAt(cursor + length - 1) + "]");
                        } else {
                            output.append("[" + prevMatch + "|" + (length - 1) + "|]");
                        }
                        break;
                    }
                } else {
                    if (length > 1) {
                        if (cursor + length - 1 < input.length()) {
                            output.append("[" + prevMatch + "|" + (length - 1) + "|" + input.charAt(cursor + length - 1) + "]");
                        } else {
                            output.append("[" + prevMatch + "|" + (length - 1) + "|]");
                        }
                    } else {
                        output.append("[0|0|" + input.charAt(cursor) + "]");
                    }
                    break;
                }
            }
            cursor += length;
        }
        return output.toString();
    }


    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public static String decompress(String compressed) {
        StringBuilder output = new StringBuilder();
        int count = 0;

        while (count < compressed.length()) {
            StringBuilder match = new StringBuilder();
            StringBuilder length = new StringBuilder();
            StringBuilder tChar = new StringBuilder();

            if (compressed.charAt(count) == '[') {
                count++;
            }

            while (compressed.charAt(count) != '|') {
                match.append(compressed.charAt(count));
                count++;
            }
            count++;

            while (compressed.charAt(count) != '|') {
                length.append(compressed.charAt(count));
                count++;
            }
            count++;

            while (compressed.charAt(count) != ']') {
                tChar.append(compressed.charAt(count));
                count++;
            }
            count++;

            if (match.toString().equals("0") && length.toString().equals("0")) {
                output.append(tChar);
            } else {
                int matchInt = Integer.parseInt(match.toString());
                int lengthInt = Integer.parseInt(length.toString());

                String matchString = output.substring(output.length() - matchInt, output.length() - matchInt + lengthInt);

                output.append(matchString).append(tChar);
            }
        }
        return output.toString();
    }

    /**
     * The getInformation method is here for your convenience, you don't need to
     * fill it in if you don't want to. It is called on every run and its return
     * value is displayed on-screen. You can use this to print out any relevant
     * information from your compression.
     */
    public String getInformation() {
        return "";
    }
}
