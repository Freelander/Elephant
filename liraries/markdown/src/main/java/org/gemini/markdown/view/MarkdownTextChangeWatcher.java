package org.gemini.markdown.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;

import org.gemini.markdown.generator.MarkdownSyntaxGenerator;
import org.gemini.markdown.model.MarkdownSyntaxModel;
import org.gemini.markdown.model.type.MarkdownSyntaxType;
import org.gemini.markdown.model.type.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geminiwen on 15/3/13.
 */
public class MarkdownTextChangeWatcher implements TextWatcher {

    /**
     * use to remove span, avoid removing edit span
     */
    private List<CharacterStyle> mLastStyle = new ArrayList<>();

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * {@inheritDoc}
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        for (CharacterStyle style: mLastStyle) {
            s.removeSpan(style);
        }
        List<MarkdownSyntaxModel> models = MarkdownSyntaxGenerator.syntaxModelsForString(s.toString());
        if (models.size() == 0) {
            return;
        }
        mLastStyle.clear();
        for (MarkdownSyntaxModel model : models) {
            MarkdownSyntaxType type = model.getSyntaxType();
            Range range = model.getRange();
//            CharacterStyle style = MarkdownSyntaxGenerator.styleFromSyntaxType(type);
            int low = range.getLower();
            int upper = range.getUpper();
//            mLastStyle.add(style);
//            s.setSpan(style, low, upper, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
