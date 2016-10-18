package org.gemini.markdown.generator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;

import org.gemini.markdown.model.MarkdownSyntaxModel;
import org.gemini.markdown.model.type.MarkdownSyntaxType;
import org.gemini.markdown.model.type.Range;
import org.gemini.markdown.syntax.SyntaxPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by geminiwen on 15/3/13.
 */
public class MarkdownSyntaxGenerator {

    private static Pattern getMatcherFromSyntaxType(MarkdownSyntaxType type) {
        switch(type) {
            case MarkdownSyntaxUnknown: {
                return null;
            }
            case MarkdownSyntaxHeaders: {
                return SyntaxPattern.MarkdownSyntaxHeaders;
            }
            case MarkdownSyntaxLinks: {
                return SyntaxPattern.MarkdownSyntaxLinks;
            }
            case MarkdownSyntaxBold: {
                return SyntaxPattern.MarkdownSyntaxBold;
            }
            case MarkdownSyntaxEmphasis: {
                return SyntaxPattern.MarkdownSyntaxEmphasis;
            }
            case MarkdownSyntaxDeletions: {
                return SyntaxPattern.MarkdownSyntaxDeletions;
            }
            case MarkdownSyntaxQuotes: {
                return SyntaxPattern.MarkdownSyntaxQuotes;
            }
            case MarkdownSyntaxCodeBlock: {
                return SyntaxPattern.MarkdownSyntaxCodeBlock;
            }
            case MarkdownSyntaxInlineCode: {
                return SyntaxPattern.MarkdownSyntaxInlineCode;
            }
            case MarkdownSyntaxBlockquotes: {
                return SyntaxPattern.MarkdownSyntaxBlockquotes;
            }
            case MarkdownSyntaxULLists: {
                return SyntaxPattern.MarkdownSyntaxULLists;
            }
            case MarkdownSyntaxOLLists: {
                return SyntaxPattern.MarkdownSyntaxOLLists;
            }
            case NumberOfMarkdownSyntax: {
                return null;
            }
            default: {
                return null;
            }
        }
    }

    public static CharacterStyle styleFromSyntaxType(MarkdownSyntaxType type) {
        switch(type) {
            case MarkdownSyntaxUnknown: {
                return null;
            }
            case MarkdownSyntaxHeaders: {
                //TODO adjust text size
                return new AbsoluteSizeSpan(30, true);
            }
            case MarkdownSyntaxLinks: {
                return new ForegroundColorSpan(Color.BLUE);
            }
            case MarkdownSyntaxBold: {
                return new StyleSpan(Typeface.BOLD);
            }
            case MarkdownSyntaxEmphasis: {
                return new StyleSpan(Typeface.BOLD);
            }
            case MarkdownSyntaxDeletions: {
                return new StrikethroughSpan();
            }
            case MarkdownSyntaxQuotes: {
                return new ForegroundColorSpan(Color.LTGRAY);
            }
            case MarkdownSyntaxCodeBlock: {
                return new BackgroundColorSpan(Color.parseColor("#fafafa"));
            }
            case MarkdownSyntaxInlineCode: {
                return new ForegroundColorSpan(Color.parseColor("#C2B17A"));
            }
            case MarkdownSyntaxBlockquotes: {
                return new ForegroundColorSpan(Color.LTGRAY);
            }
            case MarkdownSyntaxULLists: {
                return null;
            }
            case MarkdownSyntaxOLLists: {
                return null;
            }
            case NumberOfMarkdownSyntax: {
                return null;
            }
            default: {
                return null;
            }
        }
    }


    private static int regexGroupFromSyntaxType(MarkdownSyntaxType type) {
        switch(type) {
            case MarkdownSyntaxUnknown: {
                return 0;
            }
            case MarkdownSyntaxHeaders: {
                return 1;
            }
            case MarkdownSyntaxLinks: {
                return 0;
            }
            case MarkdownSyntaxBold: {
                return 0;
            }
            case MarkdownSyntaxEmphasis: {
                return 0;
            }
            case MarkdownSyntaxDeletions: {
                return 0;
            }
            case MarkdownSyntaxQuotes: {
                return 0;
            }
            case MarkdownSyntaxCodeBlock: {
                return 0;
            }
            case MarkdownSyntaxInlineCode: {
                return 0;
            }
            case MarkdownSyntaxBlockquotes: {
                return 0;
            }
            case MarkdownSyntaxULLists: {
                return 0;
            }
            case MarkdownSyntaxOLLists: {
                return 0;
            }
            case NumberOfMarkdownSyntax: {
                return 0;
            }
            default: {
                return 0;
            }
        }
    }

    public static List<MarkdownSyntaxModel> syntaxModelsForString(String text) {
        List<MarkdownSyntaxModel> models = new ArrayList<>();
        for (MarkdownSyntaxType type: MarkdownSyntaxType.values()) {
            Pattern pattern = getMatcherFromSyntaxType(type);
            if (pattern == null) continue;
            Matcher matcher = pattern.matcher(text);
            while(matcher.find()) {
                int group = regexGroupFromSyntaxType(type);
                int start = matcher.start(group);
                int end = matcher.end(group);
                MarkdownSyntaxModel model = new MarkdownSyntaxModel(type, new Range(start, end));
                models.add(model);
            }
        }
        return models;
    }
}
