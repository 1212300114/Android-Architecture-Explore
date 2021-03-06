package com.jijunjie.myandroidlib.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author Gary Ji
 * @date on 16/2/29.
 * @description To deal with keyboard
 */
public class KeyBoardUtils {

    /**
     * Don't Instantiate this class
     */
    private KeyBoardUtils() {
        throw new UnsupportedOperationException("method class should not be instantiated");
    }

    /**
     * method to open keyboard
     *
     * @param editText input
     * @param context  context generally activity
     */
    public static void openKeyboard(EditText editText, Context context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    /**
     * method to close keyboard
     *
     * @param editText input
     * @param context  context generally activity
     */
    public static void closeKeyboard(EditText editText, Context context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * keyboard state
     *
     * @param context context
     * @return if soft input open
     */

    public static boolean isKeyboardOpened(Context context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return manager.isActive();
    }

}
