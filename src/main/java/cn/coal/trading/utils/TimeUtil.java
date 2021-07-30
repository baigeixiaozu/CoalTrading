package cn.coal.trading.utils;

/**
 * @Author jiyec
 * @Date 2021/7/31 6:11
 * @Version 1.0
 **/
public class TimeUtil {
    public static long parse(String input) {
        long result = 0;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else if (Character.isLetter(c) && (number.length() > 0)) {
                result += convert(Integer.parseInt(number.toString()), c);
                number = new StringBuilder();
            }
        }
        return result;
    }

    private static long convert(int value, char unit) {
        switch (unit) {
            case 'd':
                return value * 1000 * 60 * 60 * 24L;
            case 'h':
                return value * 1000 * 60 * 60L;
            case 'm':
                return value * 1000 * 60L;
            case 's':
                return value * 1000L;
        }
        return 0;
    }
}
