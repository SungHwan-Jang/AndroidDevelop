package com.syna.updatefirmwaredemo.helper;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yuh3 on 5/11/2017.
 */
public class CusTextView extends TextView {
    private boolean mEnabled = true;

    public CusTextView(Context context) {
        super(context);
    }

    public CusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @param enable
     */
    public void setAutoSplit(boolean enable) {
        mEnabled = enable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getWidth() > 0 && getHeight() > 0 && mEnabled) {
            CharSequence newText = autoSplitText(this);
            if (!TextUtils.isEmpty(newText)) {
                setText(newText);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * @param tv
     * @return
     */
    private CharSequence autoSplitText(final TextView tv) {
        final CharSequence rawCharSequence = tv.getText();
        final String rawText = rawCharSequence.toString();
        final Paint tvPaint = tv.getPaint();
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight();

        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                sbNewText.append(rawTextLine);
            } else {
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        if (cnt - 2 >= 0 && rawTextLine.charAt(cnt - 1) >= 'A' && rawTextLine.charAt(cnt - 1) <= 'z' && rawTextLine.charAt(cnt - 2) >= 'A' && rawTextLine.charAt(cnt - 2) <= 'z') {
                            sbNewText.deleteCharAt(sbNewText.length() - 1);
                            sbNewText.append("-\n");
                            lineWidth = 0;
                            cnt -= 2;
                        } else {
                            sbNewText.append("\n");
                            lineWidth = 0;
                            --cnt;
                        }
                    }
                }
            }
            sbNewText.append("\n");
        }

        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }
        SpannableString sp = new SpannableString(sbNewText.toString());
        if (rawCharSequence instanceof Spanned) {
            TextUtils.copySpansFrom((Spanned) rawCharSequence, 0, rawCharSequence.length(), null, sp, 0);
        }

        return sp;
    }
}
