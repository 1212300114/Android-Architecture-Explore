package com.jijunjie.myandroidlib.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class SpanStringBuilder {
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
    private CharSequence sourceString;
    private SpannableString spannableString;
    private Context context;

    public SpanStringBuilder(CharSequence sourceString, Context context) {
        this.sourceString = sourceString;
        if (TextUtils.isEmpty(this.sourceString)) {
            throw new IllegalArgumentException("source String can not be empty or null");
        }
        this.spannableString = new SpannableString(sourceString);
        this.context = context;

    }

    /**
     * to add an color span to target
     * @param target target string
     * @param targetColorRes  target String color
     * @return this
     */
    public SpanStringBuilder addColorSpan(String target, @ColorRes int targetColorRes) {
        if (!sourceString.toString().contains(target)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }
        int index = sourceString.toString().indexOf(target);
        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(targetColorRes)), index,
                index + target.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * add an under line to target String
     * @param targetString the target String
     * @return this
     */
    public SpanStringBuilder addUnderLinedSpan(String targetString) {
        if (!sourceString.toString().contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }
        int index = sourceString.toString().indexOf(targetString);

        spannableString.setSpan(new UnderlineSpan(), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * to resize target String
     * @param targetString the target String
     * @param relativeSize the size of target String
     * @return this
     */
    public SpanStringBuilder addResizeSpan(String targetString, float relativeSize) {
        if (!sourceString.toString().contains(targetString)) {
            throw new IllegalArgumentException("you full String don't contains the target String");
        }

        int index = sourceString.toString().indexOf(targetString);
        spannableString.setSpan(new RelativeSizeSpan(relativeSize), index,
                index + targetString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * @return the instance of spannable String
     */
    public SpannableString build() {
        return spannableString;
    }
}
