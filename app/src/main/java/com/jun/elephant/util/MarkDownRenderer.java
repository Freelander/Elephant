/*
 * Copyright 2016 Freelander
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jun.elephant.util;

import com.commonsware.cwac.anddown.AndDown;
import com.jun.elephant.global.Constants;


public class MarkDownRenderer {
    AndDown andDown = new AndDown();

    public String renderMarkdown(String markdownRaw) {
        return  Constants.MD_HTML_PREFIX +
                andDown.markdownToHtml(markdownRaw) +
                Constants.MD_HTML_SUFFIX;
    }

//    private String themeStringFromContext(Context context) {
//        String theme = getThemeFromPrefs(context);
//        if (!theme.equals("")) {
//            if (theme.equals(context.getString(R.string.theme_dark))) {
//                return Constants.DARK_MD_HTML_PREFIX;
//            } else {
//                return Constants.MD_HTML_PREFIX;
//            }
//        }
//        return "";
//    }
//
//    private String getThemeFromPrefs(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context)
//                .getString(context.getString(R.string.pref_theme_key), "");
//    }
}
