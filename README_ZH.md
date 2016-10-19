<h4>
    <font color="#777">欢迎到 Github 或者微博关注我</font>
</h4> 

GitHub: https://github.com/Freelander

Weibo: http://weibo.com/gaojunhuang

---

## Elephant

<p>
    <a href="https://travis-ci.org/Freelander/Elephant">
        <img src="https://travis-ci.org/Freelander/Elephant.svg?branch=master">
    </a>
    <a href="http://weibo.com/gaojunhuang">
        <img src="https://img.shields.io/badge/contact-%40Freelander-orange.svg">
    </a>
    <a href="https://github.com/Freelander/Elephant/blob/master/LICENSE">
        <img src="https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg?style=flat">
    </a>
    <a href="http://fir.im/elephpant">
        <img src="https://img.shields.io/badge/downloads-fir.im-yellowgreen.svg">
    </a>
     <a href="https://android-arsenal.com/api?level=15">
        <img src="https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat" border="0" alt="API">
    </a>
</p>

PHPHub 是积极向上的 PHP & Laravel 开发者社区.

大象是 PHPHub 社区非官方 Android 客户端, App UI 风格遵循了 Google Material Design 设计风格, 项目架构使用了 MVP 模式, 数据处理使用了 RxJava + Retrofit 技术.

为什么给 App 取名为“大象”呢？因为大象是 PHP 的吉祥物, 至于由来历史, 可以去[这里](http://www.phpchina.com/blog-56751-183726.html)了解一下

## PHPHub 相关项目

* [PHPHub-iOS](https://github.com/Aufree/phphub-ios) by [@Aufree](https://github.com/Aufree)
* [PHPHub-Android](https://github.com/CycloneAxe/phphub-android) by [@Kelvin](https://github.com/CycloneAxe) and [@Xiaoxiaoyu](https://github.com/xiaoxiaoyu)
* [PHPHub-UI](https://github.com/phphub/phphub-ui) by [@Summer](https://github.com/summerblue) and [@Aufree](https://github.com/aufree)
* [PHPHub-Web](https://github.com/summerblue/phphub) by [@Summer](https://github.com/summerblue)

## 特点

- [x] 界面设计遵循 Google 设计规范
- [x] 沉浸式状态栏
- [x] 数据处理使用了 RxJava + Retrofit
- [x] 二维码扫码登录
- [x] WebView 图片点击事件 JS 注入
- [x] 项目架构使用 MVP 模式
- [x] 发布帖子支持使用 Markdown 语法编辑器
- [x] 多主题切换

## Screenshots

![](http://ww1.sinaimg.cn/large/006xB1lsgw1f8ofu9f0s8j31kw1zu1k9.jpg)

## 运行环境

1. Min Android SDK version 4.0+
2. Android Studio 2.2.1
3. Gradle version 2.14.1
4. Gradle plugin version 2.2.1
5. Build tools version 24.0.2

## 安装方式

1.下载源码到本地;

> $ git clone https://github.com/Freelander/Elephant.git

2.下一步需要复制 gradle.properties.example 更名为 gradle.properties 并编辑 里面的相关信息;

> $ cp gradle.properties.example gradle.properties

3.最后将项目导入 Android Studio 运行即可, 祝你好运!

## 登录帮助

### 开发环境

请使用以下二维码登录

![](http://ww3.sinaimg.cn/large/76dc7f1bgw1exrg86f5ubj20ml0dsq45.jpg)

### 生产环境

打开 [PHPHub](https://laravel-china.org/) 桌面 Web 端, 进入 Web 版个人中心, 对准如下位置的二维码扫描: 

![](https://dn-phphub.qbox.me/uploads/images/201609/05/1/LGYQoWp9kY.png)

## 使用到开源库

  项目名称 | 简介
  -------- | ------
[Logger](https://github.com/orhanobut/logger) | 一个强大漂亮的Log输出日志，支持json格式化输出
[Material-Dialogs](https://github.com/afollestad/material-dialogs) | 一个强大漂亮的Material Dialog
[BGABadgeView-Android](https://github.com/bingoogolapple/BGABadgeView-Android) | Android徽章控件
[MultiStateView](https://github.com/Kennyc1012/MultiStateView) | 通用显示各种状态 View
[FloatingActionButton](https://github.com/makovkastar/FloatingActionButton) | 悬浮操作按钮库, 支持监听滑滚动事件
[glide-transformations](https://github.com/wasabeef/glide-transformations) | 快速实现毛玻璃效果
[writeily-pro](https://github.com/plafue/writeily-pro) | Markdown 文本编辑 Demo
[RxJava](https://github.com/ReactiveX/RxJava) | RxJava
[RxAndroid](https://github.com/ReactiveX/RxAndroid) | RxAndroid
[Retrofit](https://github.com/square/retrofit) | Retrofit
[Gson](https://github.com/google/gson) | Json 解析库
[Prettytime](https://github.com/ocpsoft/prettytime) | 格式化日期时间
[barcodescanner](https://github.com/dm77/barcodescanner) | 二维码扫描
[fresco](https://github.com/facebook/fresco) | Facebook 开源的图片缓存库
[butterknife](https://github.com/JakeWharton/butterknife) | 注解框架
[materialish-progress](https://github.com/pnikosis/materialish-progress) |  Material Design 风格进度条
[PhotoDraweeView](https://github.com/ongakuer/PhotoDraweeView) | 基于 Fresco 的图片缩放控件
[cwac-anddown](https://github.com/commonsguy/cwac-anddown) | Markdown 渲染
[gm-mkdroid](https://github.com/geminiwen/gm-mkdroid) | 一个所见即所得的 Markdown 编辑器
[android-Ultra-Pull-To-Refresh-With-Load-More](https://github.com/captainbupt/android-Ultra-Pull-To-Refresh-With-Load-More) | 这个是基于 [Ultra-Pull-to-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh) 库修改增加支持加载更多的刷新控件
[T-MVP](https://github.com/north2014/T-MVP) | 泛型深度解耦下的MVP大瘦身

## UI 设计

界面设计灵感来自于知乎, 掘金 App.

## License

Copyright 2016 Freelander

Licensed under the [Apache License2.0](https://github.com/Freelander/Elephant/blob/master/LICENSE)
