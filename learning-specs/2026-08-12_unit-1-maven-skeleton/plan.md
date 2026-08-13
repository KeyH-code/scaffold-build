Mave# 单元 1 学习计划（plan）

> 单元：Maven 多模块骨架　日期：2026-08-12　分支：unit-1-maven-skeleton
> 命名约定（全文统一，避免两个 sky-take-out 目录混淆）：
> - **参照物** = 官方脚手架工程（只读基准，diff 对照对象）：`../sky-take-out/backend/sky-take-out/`
> - **产物** = 本工作区复刻出的工程（要建的东西）：`./sky-take-out/`

## 总目标
产出与官方**逐文件 diff 零差异**的 Maven 多模块工程骨架：根 pom + 3 子模块 pom + 启动类 + sky-server 包结构骨架；理解"父 pom 管版本、子模块只管依赖"的组织方式。

## 实施节奏约定（用户 · 老师共同遵守）
- 费曼检验全对且无疑问句 → 老师进入下一知识点；
- 回答有错/有疑问 → 老师解答后**必须等用户明确确认"明白了/继续"**，才能进入下一知识点，禁止答完疑问直接跳步；
- **任务组内的大块内容按块细化为学习小阶段**（如根 pom 的 5 块：每块一阶段，讲一块 → 费曼确认 → 下一块）；用户可随时要求进一步细化粒度；
- **大阶段沉淀**：本单元所属的 roadmap 里程碑（大阶段）完成时，将本次学习经验与知识记录进 Obsidian 仓库 `E:\deeplearning\dl\Java`，**记录更新完成后才进入下一步**；
- 与全局 learning-mission.md「教学协作约定」一致，本单元实施全程适用。

## 任务组清单（概念 → 动手 → 验证，按序实施，每任务组结束等用户确认）

### 任务组 1（概念铺垫）：Maven 多模块工程是什么
- Maven 是什么（类比：装修总包/包工头，管"项目长什么样 + 需要哪些材料 + 怎么建"）
- 为什么拆模块：一个工程拆成多个小房间（餐厅类比：公共调料/食材清单/前台大厅）
- Maven 坐标三要素：groupId / artifactId / version（类比：身份证的国家-省/名字/版本号）
- 官方工程总览：读参照物目录结构，数清楚 1 根 pom + 3 子 pom

### 任务组 2（动手·根 pom）：`sky-take-out/pom.xml`
- **分块推进（每块一个学习小阶段）**：块 1 → 块 2 → 块 3 → 块 4 → 块 5 顺序讲解，每块讲完费曼确认再进下一块：
  - 块 1 `<parent>`：spring-boot-starter-parent 2.7.3（"官方给的默认装修图纸"，自带默认配置和依赖版本）
  - 块 2 `<packaging>pom</packaging>`：聚合工程标记，父工程不打 jar
  - 块 3 `<modules>`：声明"我下面管这三个子模块"
  - 块 4 `<properties>`：13 个版本号变量，集中管版本
  - 块 5 `<dependencyManagement>`：15 个受管依赖（**只管定版本，不引入依赖**）
- 5 块全部确认后，用户**亲手写出**根 pom → diff 核对修正
- 学习点：dependencyManagement 与 dependencies 的区别（这是单元验收核心）

### 任务组 3（动手·子 pom ×3）：`sky-common/pom.xml` + `sky-pojo/pom.xml` + `sky-server/pom.xml`
- 逐行读参照物三个子 pom，讲解：
  - `<parent>` 指向自己父工程（com.sky:sky-take-out:1.0-SNAPSHOT）
  - 子模块**只写依赖、不写版本**（版本已被 dependencyManagement 管住）——为什么能省版本号
  - **反例**：sky-pojo 里 jackson-databind 直接写了 `<version>2.9.2</version>`——为什么这里例外（该依赖不在父工程受管列表里，必须自己写）
  - sky-server 的依赖方向：依赖 sky-common + sky-pojo（**注意：pojo 不依赖 common**，与知识地图描述的差异）
  - sky-server 独有 `<build>` + spring-boot-maven-plugin（只有"能启动的最终模块"才需要打成可执行 jar）
- 用户对照理解后**亲手写出**三个子 pom → diff 核对修正
- 学习点：模块依赖方向、spring-boot-maven-plugin 的作用

### 任务组 4（动手·启动类 + 包骨架）：`SkyApplication.java` + server 包结构
- 读参照物 SkyApplication.java，讲解 3 个注解：
  - `@SpringBootApplication`（入口总开关，由多个注解组合而成，单元 4 深入）
  - `@EnableTransactionManagement`（开启数据库事务管理，先知道"是开关"即可）
  - `@Slf4j`（Lombok 提供日志对象 log，先知道"是日志工具"即可）
- 用户写出 SkyApplication.java（放 `sky-server/src/main/java/com/sky/`）→ diff 核对
- 建 sky-server 包结构骨架（6 个空包 + .gitkeep 占位）：
  `config / controller / handler / interceptor / mapper / service`
- 说明：common/pojo 的包不建（单元 2/3 写文件时自然形成）；git 不跟踪空目录，所以放 .gitkeep

### 任务组 5（验证）：见 `check.md` 逐条执行
- 全量 diff 核对（4 pom + 启动类 + 包结构）
- IDEA 打开工程，Maven 加载检查（4 个模块识别、依赖下载、无红叉）
- 费曼检验（用户讲，老师提问）

## 产出清单（产物 `./sky-take-out/`，本单元结束时应存在）
```
├── pom.xml                      （根 pom）
├── sky-common/pom.xml
├── sky-pojo/pom.xml
├── sky-server/pom.xml
└── sky-server/src/main/java/com/sky/
    ├── SkyApplication.java
    ├── config/.gitkeep
    ├── controller/.gitkeep
    ├── handler/.gitkeep
    ├── interceptor/.gitkeep
    ├── mapper/.gitkeep
    └── service/.gitkeep
```

## 规格偏差备注（审查时确认是否同步修订 roadmap/knowledge-map）
1. roadmap 写"16 个受管依赖版本"，实际 dependencyManagement 是 15 个
2. knowledge-map 写"依赖方向 common ← pojo ← server"，实际 sky-pojo 不依赖 sky-common
