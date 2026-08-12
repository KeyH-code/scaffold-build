# 单元 1 验证判定标准（check）

> 单元：Maven 多模块骨架　日期：2026-08-12
> 验证三件套：费曼检验 + 亲手验证 + 审查练习，全部通过才算学会
> 命名约定同 plan.md：**参照物** = 官方脚手架（`../sky-take-out/backend/sky-take-out/`）；**产物** = `./sky-take-out/`

## 一、费曼检验（用户讲，老师提问）

**老师提问清单**（用户需不用看文档讲出来）：

1. Maven 坐标三要素是什么？各回答什么问题？（类比：哪个国家的谁家的哪个版本）
2. 根 pom 的 `dependencyManagement` 和子模块的 `dependencies` 有什么区别？如果只写前者不写后者会怎样？
3. 为什么子模块的依赖可以不写 `<version>`？什么情况下子模块**必须**自己写版本（jackson-databind 反例）？
4. 画一下本工程的模块依赖图（三个模块谁依赖谁）。
5. 为什么 `spring-boot-maven-plugin` 只出现在 sky-server 的 pom 里？
6. 根 pom 的 `<packaging>pom</packaging>` 是什么意思？`<modules>` 是干什么的？

**判定**：能用自己的话讲通（允许类比、允许小卡壳，但逻辑要对）；讲不出/讲错 = 未达标，重讲后再验证。

## 二、亲手验证（对照参照物逐文件核对）

### 核对 1：diff 零差异
对以下 5 个文件执行 diff，**输出必须为空**：
- `pom.xml`（根）→ 参照物 `pom.xml`
- `sky-common/pom.xml` → 参照物 `sky-common/pom.xml`
- `sky-pojo/pom.xml` → 参照物 `sky-pojo/pom.xml`
- `sky-server/pom.xml` → 参照物 `sky-server/pom.xml`
- `sky-server/src/main/java/com/sky/SkyApplication.java` → 参照物同路径文件

### 核对 2：包结构骨架
- `sky-server/src/main/java/com/sky/` 下有 6 个目录（config/controller/handler/interceptor/mapper/service），各含 .gitkeep
- 目录结构与参照物一致（参照物对应 6 个包均已存在）

### 核对 3：IDEA 加载检查（"跑通"的替代验证）
- IDEA 以 Maven 工程打开 `sky-take-out/`（本工作区产物）
- Maven 面板能看到 4 个模块：`sky-take-out`（根）、sky-common、sky-pojo、sky-server
- 依赖解析成功：sky-server 下能看到 sky-common、sky-pojo 作为依赖；无红色报错/红叉
- 执行 `compile`（IDEA Maven 面板 → sky-take-out → Lifecycle → compile）**BUILD SUCCESS**
- 首次加载需下载依赖到本地仓库 D:\maven-repo（可能较慢，属正常）

**判定**：以上 3 项核对全部通过。

## 三、审查练习（老师出题，用户独立判断）

老师给出 1 段故意写错的 pom（含 3-4 处错误：如依赖方向写反、版本号写在子模块、parent 坐标写错、模块未声明等），用户：
1. 找出所有错误
2. 逐一说明错误理由和正确写法
3. 说出"我为什么能看出来"（对应哪个知识点）

**判定**：错误全找对、理由全说对。

## 四、对照学习目标检查
逐条核对 learning-goals.md：G1-G5 费曼达标、S1-S4 验证达标；未达标项记录在 changelog 并安排补学。

## 五、收尾
- 更新 `learning-specs/learning-changelog.md`（学会了什么/踩了什么坑）
- 合并分支 unit-1-maven-skeleton → main（用户执行）
