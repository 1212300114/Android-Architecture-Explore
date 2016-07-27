package com.jijunjie.myandroidlib.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

import com.jijunjie.myandroidlib.R;


/**
 * @author Gary Ji
 * @date 2016/4/11 0011.
 * @description the util class to create a spannable String
 */
public class SpanStringCreateUtils {
    /**
     * all styles of string span  if need add more method
     *
     1、BackgroundColorSpan 背景色
     2、ClickableSpan 文本可点击，有点击事件
     3、ForegroundColorSpan 文本颜色（前景色）
     4、MaskFilterSpan 修饰效果，如模糊(BlurMaskFilter)、浮雕(EmbossMaskFilter)
     5、MetricAffectingSpan 父类，一般不用
     6、RasterizerSpan 光栅效果
     7、StrikethroughSpan 删除线（中划线）
     8、SuggestionSpan 相当于占位符
     9、UnderlineSpan 下划线
     10、AbsoluteSizeSpan 绝对大小（文本字体）
     11、DynamicDrawableSpan 设置图片，基于文本基线或底部对齐。
     12、ImageSpan 图片
     13、RelativeSizeSpan 相对大小（文本字体）
     14、ReplacementSpan 父类，一般不用
     15、ScaleXSpan 基于x轴缩放
     16、StyleSpan 字体样式：粗体、斜体等
     17、SubscriptSpan 下标（数学公式会用到）
     18、SuperscriptSpan 上标（数学公式会用到）
     19、TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
     20、TypefaceSpan 文本字体
     21、URLSpan 文本超链接
     *
     */

    /**
     * to create a spannable string with different color of different chars
     *
     * @param fullString     the full String need to transform
     * @param targetString   the target string to transform
     * @param context        the context to get resource
     * @param targetColorRes the target class
     * @return the spannable string with colorful
     */
    public static SpannableString createColorfulString(CharSequence fullString, String targetString, Context context,
                                                       @ColorRes int targetColorRes) {
        SpannableString spannableString = new SpannableString(fullString);
        if (!fullString.toString().contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }
        int index = fullString.toString().indexOf(targetString);

        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.defaultColor)), 0, index,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(targetColorRes)), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.defaultColor)),
                index + targetString.length(), fullString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    public static SpannableString createColorfulString(String fullString, String targetString, Context context,
                                                       @ColorRes int targetColorRes, @ColorRes int normalColorRes) {
        SpannableString spannableString = new SpannableString(fullString);
        if (!fullString.contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }

        int index = fullString.indexOf(targetString);

        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(normalColorRes)), 0, index,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(targetColorRes)), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(normalColorRes)),
                index + targetString.length(), fullString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * * to create a spannable string with different color inside
     *
     * @param fullString     the full String need to transform
     * @param targetString   the target string to transform
     * @param context        the context to get resource
     * @param targetColorRes the target class
     * @return the spannable string with colorful back
     */

    public static SpannableString createBackColorString(String fullString, String targetString, Context context,
                                                        @ColorRes int targetColorRes) {
        SpannableString spannableString = new SpannableString(fullString);
        if (!fullString.contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }
        int index = fullString.indexOf(targetString);

        spannableString.setSpan(new BackgroundColorSpan(context.getResources().getColor(android.R.color.transparent)), 0, index,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BackgroundColorSpan(context.getResources().getColor(targetColorRes)), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BackgroundColorSpan(context.getResources().getColor(android.R.color.transparent)),
                index + targetString.length(), fullString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString createUnderLinedString(String fullString, String targetString) {
        SpannableString spannableString = new SpannableString(fullString);
        if (!fullString.contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }
        int index = fullString.indexOf(targetString);

        spannableString.setSpan(new UnderlineSpan(), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString createResizedString(String fullString, String targetString, float relativeSize) {
        SpannableString spannableString = new SpannableString(fullString);
        if (!fullString.contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }

        int index = fullString.indexOf(targetString);
        spannableString.setSpan(new RelativeSizeSpan(relativeSize), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;

    }

    public static SpannableString createSuperScriptString(CharSequence fullString, String targetString) {
        SpannableString spannableString = new SpannableString(fullString);
        if (!fullString.toString().contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }

        int index = fullString.toString().indexOf(targetString);
        spannableString.setSpan(new SuperscriptSpan(), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    // TODO: 2016/4/25 0025  to add method to create more span style
}
