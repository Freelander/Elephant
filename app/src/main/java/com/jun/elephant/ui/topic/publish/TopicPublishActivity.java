/*
 * Copyright 2016 Freelander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jun.elephant.ui.topic.publish;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jun.elephant.R;
import com.jun.elephant.entity.topic.CategoryEntity;
import com.jun.elephant.entity.topic.TopicEntity;
import com.jun.elephant.mvpframe.base.BaseFrameActivity;
import com.jun.elephant.global.Constants;
import com.jun.elephant.ui.main.TopicPreviewActivity;
import com.jun.elephant.ui.topic.details.TopicDetailsActivity;
import com.jun.elephant.util.JLog;

import org.gemini.markdown.util.MarkdownUtil;
import org.gemini.markdown.view.MarkdownEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jun on 2016/8/24.
 */
public class TopicPublishActivity extends BaseFrameActivity<TopicPublishPresenter, TopicPublishModel>
        implements Toolbar.OnMenuItemClickListener, TopicPublishContract.View {

    @Bind(R.id.toolBar)
    Toolbar mToolBar;
    @Bind(R.id.topic_title_edt)
    EditText mTopicTitleEdt;
    @Bind(R.id.topic_node_tv)
    TextView mTopicNodeTv;
    @Bind(R.id.topic_content_edt)
    MarkdownEditText mTopicContentEdt;
    @Bind(R.id.edit_opt_ll)
    LinearLayout mEditOptLl;

    private MaterialDialog.Builder mNodeListDialog;
    private MaterialDialog mLoadingDialog;

    private MarkdownUtil mMarkdownUtil;

    private List<CategoryEntity.Category> mCategory;

    private String nodeId;

    private List<String> mCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_publish);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mMarkdownUtil = new MarkdownUtil(mTopicContentEdt);

        mCategory = new ArrayList<>();
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.app_publish));
        mToolBar.inflateMenu(R.menu.menu_publish);

        mLoadingDialog = getLoadingDialog().content("正在发布...").build();
    }

    private void initListDialog() {

        if (mCategoryList == null)
            return;

        mNodeListDialog = new MaterialDialog.Builder(this);
        mNodeListDialog.title("请选择节点");
        mNodeListDialog.titleColorRes(R.color.text_common);
        mNodeListDialog.positiveColorRes(R.color.colorPrimary);
        mNodeListDialog.positiveText("再看看");

        if (!mCategoryList.isEmpty()) {
            mNodeListDialog.items(mCategoryList);
        }

        mNodeListDialog.itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                JLog.logd("node click position",  " ===" + which);
                nodeId = String.valueOf(mCategory.get(which).getId());
                mTopicNodeTv.setText(text);
            }
        });

        mNodeListDialog.show();
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setOnMenuItemClickListener(this);

        mTopicContentEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEditOptLl.setVisibility(View.VISIBLE);
                } else {
                    mEditOptLl.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick({R.id.topic_node_tv,
            R.id.edit_bold_ib,
            R.id.edit_code_ib,
            R.id.edit_img_ib,
            R.id.edit_italic_ib,
            R.id.edit_line_ib,
            R.id.edit_link_ib,
            R.id.edit_list_ib,
            R.id.edit_quote_ib,
            R.id.edit_title_ib
    })
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Editable e = mTopicContentEdt.getText();
        switch (v.getId()) {
            case R.id.topic_node_tv:
//                mNodeListDialog.show();
                initListDialog();
                break;
            case R.id.edit_bold_ib:
                mMarkdownUtil.insertStrong(e);
                break;
            case R.id.edit_code_ib:
                mMarkdownUtil.insertCodeBlock(e);
                break;
            case R.id.edit_img_ib:
                mMarkdownUtil.insertImage(e);
                break;
            case R.id.edit_italic_ib:
                mMarkdownUtil.insertItalic(e);
                break;
            case R.id.edit_line_ib:
                mMarkdownUtil.insertHorizontalLine(e);
                break;
            case R.id.edit_link_ib:
                mMarkdownUtil.insertLink(e);
                break;
            case R.id.edit_list_ib:
                mMarkdownUtil.insertList(e);
                break;
            case R.id.edit_quote_ib:
                mMarkdownUtil.insertBlockquotes(e);
                break;
            case R.id.edit_title_ib:
                mMarkdownUtil.insertTitle(e);
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_publish:
                publishTopic();
                break;
            case R.id.action_preview:
//                FileUtil.saveNote(elephantApplication.getTopicDir(),mTopicContentEdt.getText().toString(),
//                        mTopicTitleEdt.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putString(Constants.Key.PREVIEW_TOPIC_TITLE, mTopicTitleEdt.getText().toString());
                bundle.putString(Constants.Key.PREVIEW_TOPIC_CONTENT, mTopicContentEdt.getText().toString().replace("\n-", "\n\n-"));
                openActivity(TopicPreviewActivity.class, bundle);
                break;
        }
        return true;
    }

    @Override
    public void initLoad() {
        super.initLoad();
        mPresenter.getCategory();
    }

    public void publishTopic() {
        String title = mTopicTitleEdt.getText().toString();
        String body = mTopicContentEdt.getText().toString();
        if (TextUtils.isEmpty(title)) {
            showShortToast("请填写标题");
            return;
        }

        if (TextUtils.isEmpty(body)) {
            showShortToast("请输入内容");
            return;
        }

        if (TextUtils.isEmpty(nodeId)) {
            showShortToast("请选择发布类型");
            return;
        }

        mPresenter.publishTopic(title, body, nodeId);

    }

    @Override
    public void getCategory(CategoryEntity categoryEntity) {
        this.mCategory = categoryEntity.getData();

        if (!mCategory.isEmpty()) {
            mCategoryList = new ArrayList<>();
            for (int i = 0; i < mCategory.size(); i++) {
                mCategoryList.add(i, mCategory.get(i).getName());
            }
        }
    }

    @Override
    public void publishTopicSuccess(TopicEntity topicEntity) {
        if (topicEntity != null) {

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.Key.TOPIC, topicEntity);
            openActivity(TopicDetailsActivity.class, bundle);

            finish();
        }
    }

    @Override
    public void onRequestStart() {
        mLoadingDialog.show();
    }

    @Override
    public void onRequestEnd() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        JLog.e("publish fail msg === ",  msg);
        showShortToast(getString(R.string.toast_publish_fail));
    }
}
