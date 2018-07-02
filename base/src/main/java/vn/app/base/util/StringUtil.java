package vn.app.base.util;

import android.text.Html;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Envy 15T on 6/5/2015.
 */
public class StringUtil {

    /**
     * Upper case first letter in string
     *
     * @param str
     * @return
     */
    public static String UppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    public static String formatDate(int year, int monthOfYear, int dayOfMonth) {
        return year + "/" + monthOfYear + "/" + dayOfMonth;
    }

    public static String formatDate(Calendar calendar) {
        return formatDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static boolean checkStringValid(String data) {
        if (data != null && !data.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static void displayText(String text, TextView textView) {
        if (checkStringValid(text)) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
    }

    public static void displayTextHtml(String text, TextView textView) {
        if (checkStringValid(text)) {
            textView.setText(Html.fromHtml(text));
        } else {
            textView.setText("");
        }
    }

    public static String formatVND(String value) {
        return NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(value));
    }

    public static String formatPhone(String value) {
        if (value.length() == 10) {
            return value.substring(0, 4) + "-" + value.substring(4, 7) + "-" + value.substring(7, 10);
        } else {
            return value;
        }
    }

    public static String encryptPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            return bytesToHex(byteData);
        }
        return password;
    }

    final protected static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    public static String formatDateText(String date) {
        if (checkStringValid(date)) {
            String[] dateArray = date.split("-");
            return getMinguoYear(Integer.parseInt(dateArray[0])) + "年" + dateArray[1] + "月" + dateArray[2] + "日";
        } else {
            return "";
        }
    }

    public static String formatDateText(Calendar calendar) {
        return getMinguoYear(calendar.get(Calendar.YEAR)) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
    }

    private static int getMinguoYear(int year) {
        return year - 1911;
    }

    public static String formatDateTextWithSlash(String date) {
        if (checkStringValid(date)) {
            String[] dateArray = date.split("-");
            return getMinguoYear(Integer.parseInt(dateArray[0])) + "/" + dateArray[1] + "/" + dateArray[2];
        } else {
            return "";
        }
    }

    public static String formatTimeText2(String time) {
        if (checkStringValid(time)) {
            return time.substring(0, 2) + ":" + time.substring(2, 4);
        } else {
            return "";
        }
    }

    public static String formatTimeText(String time) {
        if (checkStringValid(time)) {
            if (time.equalsIgnoreCase("A")) {
                return "下午";
            } else if (time.equalsIgnoreCase("M")) {
                return "上午";
            } else if (time.equalsIgnoreCase("E")) {
                return " 晚間";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String formatDateTextWithTime(String date, String time) {
        String dateFormated = formatDateText(date);
        if (checkStringValid(dateFormated)) {
            if (time.equalsIgnoreCase("A")) {
                return dateFormated + " 下午診";
            } else if (time.equalsIgnoreCase("M")) {
                return dateFormated + " 上午診";
            } else if (time.equalsIgnoreCase("E")) {
                return dateFormated + " 晚間診";
            } else {
                return dateFormated;
            }
        } else {
            return "";
        }
    }

    public static String getYouTubeThumbnail(String url) {
        return "http://img.youtube.com/vi/" + url.substring(url.lastIndexOf("/") + 1) + "/0.jpg";
    }

    public static String getYouTubeThumbnail2(String url) {
        return "http://img.youtube.com/vi/" + url.substring(url.lastIndexOf("=") + 1) + "/0.jpg";
    }

    public static String getYouTubeId(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static String getYouTubeId2(String url) {
        return url.substring(url.lastIndexOf("=") + 1);
    }

    public static String getRandomStringId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isEmpty(String input) {
        if (input == null || input.length() == 0) {
            return true;
        }
        return false;
    }

}
