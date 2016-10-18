package org.gemini.markdown.model;


import org.gemini.markdown.model.type.MarkdownSyntaxType;
import org.gemini.markdown.model.type.Range;

/**
 * Created by geminiwen on 15/3/13.
 */
public class MarkdownSyntaxModel {

    private MarkdownSyntaxType mSyntaxType;
    private Range mRange;

    public MarkdownSyntaxModel(MarkdownSyntaxType type, Range range) {
        this.mSyntaxType = type;
        this.mRange = range;
    }


    public MarkdownSyntaxType getSyntaxType() {
        return mSyntaxType;
    }

    public void setSyntaxType(MarkdownSyntaxType mSyntaxType) {
        this.mSyntaxType = mSyntaxType;
    }

    public Range getRange() {
        return mRange;
    }

    public void setRange(Range mRange) {
        this.mRange = mRange;
    }
}
