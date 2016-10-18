package org.gemini.markdown.syntax;

import java.util.regex.Pattern;

/**
 * Created by geminiwen on 15/3/14.
 */
public class SyntaxPattern {
    public static Pattern MarkdownSyntaxHeaders = Pattern.compile("(?:^|\\n)(#+ +.+)", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxLinks = Pattern.compile("\\[([^\\[]+)\\]\\(([^\\)]+)\\)", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxBold = Pattern.compile("(\\*\\*|__)(.*?)\\1", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxItalic = Pattern.compile("\\*[^*]+\\*", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxEmphasis = Pattern.compile("\\s(\\*|_)(.*?)\\1\\s", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxDeletions = Pattern.compile("~~(.*?)~~", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxQuotes = Pattern.compile(":\"(.*?)\":", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxCodeBlock = Pattern.compile("```([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxInlineCode = Pattern.compile("`[^`\\s]+`", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxBlockquotes = Pattern.compile("\n(&gt;|>)(.*)", Pattern.CASE_INSENSITIVE);
    public static Pattern MarkdownSyntaxULLists = Pattern.compile("^\\*([^\\*]*)", Pattern.MULTILINE);
    public static Pattern MarkdownSyntaxOLLists = Pattern.compile("^[0-9]+\\.(.*)", Pattern.MULTILINE);
}
