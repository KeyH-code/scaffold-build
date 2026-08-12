# 环境核查报告（Phase 0）

> 核查日期：2026-08-12　核查方式：逐项命令检测
> 本文件记录开发环境现状与待办事项，服务于「苍穹外卖脚手架复刻」学习项目。

## 一、核查结果总览

| 核查项 | 目标 | 实际状态 | 判定 | 备注 |
|---|---|---|---|---|
| JDK | 17.x | **JDK 21.0.5 LTS**（`C:\Program Files\Java\jdk-21`） | ⚠️ 版本不符 | 详见"问题 1" |
| Maven | 3.6+ | **mvn 命令不可用**（未安装或未配 PATH） | ⚠️ 缺失 | 本地仓库 `D:\maven-repo` 已缓存依赖；详见"问题 2" |
| MySQL | 8.x 运行中 | ✅ **运行中**（PID 7868 监听 3306，另有 PID 6000 实例） | ✅ | 版本与 root 密码待确认 |
| Redis | 运行 | **未安装、未运行** | ❌ 缺失 | 脚手架阶段用不到（登录链路无 Redis），功能开发阶段再装 |
| git | 可用 | ✅ 2.54.0（windows），身份已配置（keycode / 3362805328@qq.com） | ✅ | |
| 8080 端口 | 空闲 | ✅ 无监听 | ✅ | 启动服务无冲突 |
| IDEA | 可用 | ✅ IdeaIC 2025.2（社区版） | ✅ | 安装路径待用户确认；自带 Bundled Maven |

## 二、问题与待办

### 问题 1：JDK 版本（21 vs 17）
- 现象：PATH 上的 `java` 命令**崩溃**（Segmentation fault）——`C:\Program Files\Common Files\Oracle\Java\javapath\java.exe` 这个副本损坏；真实 JDK 21.0.5 正常（直接调用 `"C:\Program Files\Java\jdk-21\bin\java.exe" -version` 输出正常）。
- 影响：官方脚手架 pom 以 **Java 17** 为编译目标（`java.version=17`）。JDK 21 可编译 `--release 17` 的代码，但 Spring Boot 2.7.3 官方支持测试到 Java 19，JDK 21 运行可能有兼容风险。
- **待决策**：① 安装 JDK 17（完全对齐官方，推荐）；② 先用 JDK 21 试跑，遇坑再换。
- **已定（2026-08-12 访谈）**：**先用 JDK 21 试跑**，编译目标保持 17；若 Spring Boot 2.7.3 在 JDK 21 上运行报兼容错误，再回头装 JDK 17。
- 提醒：无论哪种方案，都建议把 PATH 上的 `javapath` 删掉或修正，否则命令行 `java` 一直崩溃。

### 问题 2：Maven 未配命令行
- 现象：`mvn` 命令找不到；但 `D:\maven-repo` 是 Maven 本地仓库（依赖已大量缓存，构建时无需重新下载）。
- **待决策**：① 安装独立 Maven 并配置 PATH（命令行可用，便于"亲手跑通"时敲命令，推荐）；② 直接用 IDEA 自带 Bundled Maven（无需安装，但命令行不可用）。
- **已定（2026-08-12 访谈）**：**用 IDEA 内置 Bundled Maven**，构建/运行全在 IDEA 内完成；后续如需要命令行 mvn 再装独立版。
- 需要告诉用户本地仓库位置，将来配置 Maven 时把 `<localRepository>` 指向 `D:\maven-repo` 即可复用缓存。

### 问题 3：MySQL 凭据
- dev 配置（官方 `application-dev.yml`）为 `root / root`（host localhost:3306，库 `sky_take_out`）。
- **待确认**：本机 MySQL root 密码是否就是 `root`；若不同，跑通验证时需建同密码账号或调整本机环境（官方配置照抄不改，保证复刻零差异）。

### 问题 4：Redis 缺失
- 脚手架复刻与员工登录链路**不依赖 Redis**（pom 里有 starter 但登录业务不用），不阻塞本阶段；
- 后续功能开发（购物车、缓存等）前需安装 Windows 版 Redis。

## 三、关键路径速查

| 用途 | 路径 |
|---|---|
| JDK 21 | `C:\Program Files\Java\jdk-21` |
| Maven 本地仓库（缓存） | `D:\maven-repo` |
| IDEA 配置 | `C:\Users\胡成凯\AppData\Local\JetBrains\IdeaIC2025.2` |
| 官方脚手架（只读参照物） | `E:\ACourseLearning\sky-take-out\sky-take-out-learn\sky-take-out\backend\sky-take-out` |
| 复刻产物（待建） | `E:\ACourseLearning\sky-take-out\sky-take-out-learn\scaffold-build\sky-take-out` |

## 四、启动服务的方法（备忘）
- **MySQL**：服务已自动运行（3306 监听中），无需手动启动。
- **Redis**：暂未安装（见问题 4）。
- **复刻工程启动**：`mvn spring-boot:run`（sky-server 模块）或 IDEA 直接运行 `SkyApplication`。
