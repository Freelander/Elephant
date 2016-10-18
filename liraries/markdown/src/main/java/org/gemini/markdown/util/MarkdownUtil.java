package org.gemini.markdown.util;

import android.text.Editable;

import org.gemini.markdown.view.MarkdownEditText;

/**
 * Created by Jun on 2016/5/14.
 */
public class MarkdownUtil {

    private MarkdownEditText mEditText;

    public static MarkdownUtil markdownUtil;

    public MarkdownUtil(MarkdownEditText mEditText) {
        this.mEditText = mEditText;
    }

    public void insertTitle(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "# title";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 2, selectionStart + 7);
    }

    public void insertList(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String listTemplate = "- List Item";
        e.replace(selectionStart, selectionEnd, listTemplate);
        // start from "L", end to "m"
        mEditText.setSelection(selectionStart + 2, selectionStart + 11);
    }

    public void insertCodeBlock(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String codeBlockTemplate = "```\n\n```";
        e.replace(selectionStart, selectionEnd, codeBlockTemplate);
        mEditText.setSelection(selectionStart + 4, selectionStart + 4);
    }

    public void insertStrong(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "**Strong**";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 2, selectionStart + 8);
    }

    public void insertItalic(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "*Italic*";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 1, selectionStart + 7);
    }

    public void insertBlockquotes(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "> ";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 2, selectionStart + 2);
    }

    public void insertLink(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "[链接](http://example)";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 12, selectionStart + 19);
    }

    public void insertImage(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "![Image](http://resource)";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 16, selectionStart + 24);
    }

    public void insertHorizontalLine(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "---\n";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 4, selectionStart + 4);
    }




}
