# SuVideo

一个基于 Android 的多模块短视频应用，采用组件化架构设计，支持模块独立运行与集成调试。

## 项目简介

SuVideo 是一个功能完整的短视频 App，涵盖视频播放、发现、搜索、评论、用户系统等核心功能。项目采用 **组件化架构**，各业务模块独立可拆分，支持组件化开发模式（`isModule` 开关控制），便于团队协作与功能复用。

## 技术栈

| 技术 | 用途 |
|------|------|
| **Java** | 开发语言 |
| **AGP 9.2.1** | Android Gradle Plugin |
| **Gradle Version Catalog** | 统一依赖管理 |
| **DataBinding** | 视图与数据绑定 |
| **MVVM（LiveData + ViewModel）** | 架构模式 |
| **ARouter 1.5.2** | 组件化页面路由与通信 |
| **OkHttp 4.12 + Retrofit 2.11 + Gson** | 网络请求 |
| **Glide 4.16** | 图片加载 |
| **Media3 ExoPlayer** | 视频播放 |
| **Room 2.6.1** | 本地数据库（播放历史） |
| **SmartRefreshLayout** | 下拉刷新与上拉加载 |
| **LoadSir** | 页面加载状态管理（加载/空/错误） |
| **AndroidAutoSize** | 屏幕适配 |
| **EventBus** | 组件间事件通信 |
| **CameraX 1.2** | 相机拍摄 |
| **FlowHelper** | 指示器/引导页 |
| **AndroidX Security** | 数据加密存储 |

## 模块架构

### 模块依赖关系

```
app (主工程)
 ├── feature_home (首页)
 ├── feature_find (发现)
 ├── feature_plaza (广场)
 ├── feature_user (用户中心)
 ├── mediaplayer (视频播放器)
 └── library_base (基础公共库)
       ├── network (网络层)
       └── data_video (视频数据层)
```

### 各模块说明

| 模块 | 类型 | 说明 |
|------|------|------|
| `app` | application | 主工程，Application 入口，聚合所有业务模块 |
| `library_base` | library | 基础公共库，封装了 BaseActivity/BaseFragment/BaseViewModel、工具类、公共 UI 组件、全局配置、ARouter 路径管理等 |
| `network` | library | 网络层，封装 OkHttp + Retrofit，提供统一请求回调与基础响应实体 |
| `data_video` | library | 视频数据层，定义视频相关的请求/响应 Bean |
| `feature_home` | library/application | 首页模块，视频推荐列表 |
| `feature_find` | library/application | 发现模块，分类浏览、主题列表、话题、锚点 |
| `feature_plaza` | library/application | 广场模块，Banner 轮播、图文广场 |
| `feature_user` | library/application | 用户模块，手机号登录/注册、密码重置、个人信息编辑、相机拍照、播放设置、推送设置、关于我们等 |
| `mediaplayer` | library/application | 视频播放模块，Media3 ExoPlayer 播放器、视频详情、评论系统、搜索、播放记录、分类视频列表 |

> 每个 `feature_*` 模块都可通过开关 `rootProject.ext.isModule` 切换为独立运行的 App，方便单独调试。

## 功能特性

- **视频推荐** — 首页瀑布流视频推荐
- **视频播放** — 基于 Media3 ExoPlayer 的视频播放，支持全屏、进度控制
- **分类浏览** — 多维度分类视频浏览
- **发现页** — 分类、主题、话题、锚点推荐
- **广场** — Banner 轮播 + 图文信息流
- **搜索** — 视频搜索功能
- **评论系统** — 视频评论、回复、删除、举报
- **播放记录** — 基于 Room 本地数据库，记录播放历史
- **用户系统** — 手机号登录/注册、密码重置、个人信息编辑
- **相机拍摄** — CameraX 相机拍照上传头像
- **设置中心** — 播放设置、推送设置
- **状态管理** — LoadSir 统一处理加载中/空数据/错误页面
- **屏幕适配** — AndroidAutoSize 自适应适配
- **组件化路由** — ARouter 实现模块间页面跳转与服务调用

## 环境要求

- **Android Studio** — Hedgehog (2023.1.1) 或更高版本
- **JDK** — JDK 17+
- **Gradle** — 8.x +
- **Android SDK** — compileSdk 36, minSdk 26, targetSdk 36
- **AGP** — 9.2.1

## 构建与运行

### 集成模式（完整 App）

```bash
# 在 build.gradle 中将 isModule 设为 false
# 直接运行 app 模块即可

./gradlew assembleDebug
```

### 组件模式（独立调试某个模块）

```bash
# 在 build.gradle 中将 rootProject.ext.isModule 设为 true
# 然后单独运行对应的 feature 模块
```

## 项目结构

```
SuVideo/
├── app/                          # 主工程
│   └── src/main/java/com/su/video/
│       ├── MainActivity.java     # 主页 Activity
│       ├── MainViewModel.java    # 主页 ViewModel
│       └── MyApplication.java    # Application 入口
├── library_base/                 # 基础公共库
│   └── src/main/java/com/su/library/
│       ├── base/                 # Base 基类
│       ├── config/               # 全局配置、路由路径
│       ├── utils/                # 工具类
│       └── list/                 # 列表通用组件
├── network/                       # 网络层
│   └── src/main/java/com/su/network/
│       ├── OkhttpClientProvider.java
│       ├── RetrofitProvider.java
│       └── ApiCall.java
├── data_video/                   # 视频数据层
├── feature_home/                 # 首页模块
├── feature_find/                 # 发现模块
├── feature_plaza/                # 广场模块
├── feature_user/                 # 用户模块
├── mediaplayer/                  # 视频播放模块
├── gradle/
│   └── libs.versions.toml        # 版本目录（统一依赖管理）
├── build.gradle                  # 根构建文件
├── settings.gradle               # 模块配置
└── gradle.properties             # Gradle 属性
```

## 许可证

本项目基于 [MIT License](LICENSE) 开源。
