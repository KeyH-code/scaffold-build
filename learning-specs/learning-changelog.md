# 学习变更日志（learning-changelog）

> 每次学习单元合并后更新：学会了什么、踩了什么坑、下阶段调整

## 2026-08-12 — 启动：Phase 0 + 章程阶段
**做了什么**
- Phase 0 环境核查完成，结果写入 `environment.md`：
  - ✅ MySQL 8 运行中（3306 监听）；git 2.54.0 可用；8080 端口空闲；IDEA 2025.2（社区版）可用
  - ⚠️ JDK 21.0.5（目标 Java 17）→ 决策：**先用 JDK 21 试跑**，遇兼容问题再装 17
  - ⚠️ mvn 命令行不可用 → 决策：**用 IDEA 内置 Maven**（本地仓库 D:\maven-repo 已缓存依赖）
  - ❌ Redis 未安装 → 脚手架阶段不需要，功能开发前再装
- 章程阶段完成：生成 learning-mission / knowledge-map / learning-roadmap / environment 四文档

**决策记录**
| 决策 | 选项 | 结论 |
|---|---|---|
| JDK 版本 | 装 17 / 先用 21 | 先用 21 试 |
| Maven | 独立安装 / IDEA 内置 | IDEA 内置 |
| 目标优先级 | 理解优先 / 复刻优先 | 理解优先 |
| 单元划分 | 5 / 3 / 8+ 单元 | 5 单元 |
| git 操作 | 谁执行 | **用户亲自执行**（老师只给命令与讲解） |

**踩坑记录**
- PATH 上的 `javapath\java.exe`（Oracle 自动更新产物）损坏，命令行 `java` 崩溃（Segmentation fault）；真实 JDK 21 正常。待处理：修正 PATH 或删除损坏副本。

**下阶段调整**
- 下一单元：单元 1（Maven 多模块骨架），建议另开新会话，按 sdd-learning 三件套流程推进。

## 2026-08-13 — 单元 1：Maven 多模块骨架
**做了什么**
- 任务组 1-5 全部完成：概念铺垫 → 根 pom → 三个子 pom → SkyApplication + 包骨架 → 验证
- 产出 `sky-take-out/`：根 pom + 3 子 pom + SkyApplication.java + 6 个包骨架（.gitkeep），与参照物 diff 内容一致
- IDEA Maven 工程加载成功（4 模块识别），compile BUILD SUCCESS
- Obsidian 笔记沉淀：`03. Maven多模块与pom编写.md`

**学会了什么**
- Maven 坐标三要素（groupId 域名反写 / artifactId / version + SNAPSHOT 语义）
- 仓库体系（本地/中央/镜像）与查找顺序；坐标三类（依赖/工程/parent）
- 聚合（modules）与继承（parent）配套机制；两级爹（spring-boot-starter-parent + 自家根 pom）
- dependencyManagement（只管定版本）vs dependencies（真正引入）；继承链两级找版本，查不到必须自己写
- 内部模块依赖必须写全三要素；spring-boot-maven-plugin（fat jar + Main-Class）；scope 三值；jar 无需解压

**踩坑记录**
- groupId 反写写反（sky.com → com.sky）；modules 里填了根坐标三要素（应填子模块 artifactId）
- parent 的 artifactId 写成子模块自己的名字（应写父工程名 sky-take-out）
- SkyApplication 目录建错：`com.sky` 单层文件夹（应为 `com/sky` 两级——包名每个点 = 一层目录）
- 在复刻代码里自己加注释（参照物没有的注释破坏零差异——知识点应记笔记，不进复刻代码）
- IDEA 未加载 Maven 工程时无高亮/运行标识（需 File→Open 选 sky-take-out + 等依赖下载）

**规格修订确认（已同步各文档）**
- roadmap 写"16 个受管依赖" → 实际 15 个（已修订）
- knowledge-map 写"common ← pojo ← server" → 实际 pojo 不依赖 common（已修订）
- 验收标准：diff 零差异 → **内容一致**（XML 顺序/空行放行，缺内容/写错必改）——已同步 mission/plan/check/CLAUDE.md
- 教学协作约定新增：多轮询问推进节奏、任务组按块细化、知识沉淀触发条件（切换文件类型时/大阶段完成时）+ 笔记书写标准——已同步 mission/plan/CLAUDE.md

**下阶段调整**
- 下一单元：单元 2（sky-common 公共层，29 文件），建议另开新会话按三件套流程推进
- 待处理（上一单元遗留）：PATH 上损坏的 `javapath\java.exe`；Maven 面板"两个 sky-take-out"重复加载（建议只保留一个工程窗口）

## 2026-08-14 — 单元 2：sky-common 公共层
**做了什么**
- 任务组 1-6 全部完成：概念铺垫 → constant+result → enumeration+exception → properties+json → context+utils → 验证（diff + 编译 + 费曼 10 题 + 审查练习）
- 产出 `sky-common/src/main/java/com/sky/` 8 包 **29 文件**，与参照物逐文件 diff 实质内容一致（零必改项）；IDEA 编译 BUILD SUCCESS
- Obsidian 笔记沉淀 3 篇：`04. 公共层设计：常量契约、统一返回与异常体系` / `05. 配置绑定与 JSON 序列化设计` / `06. ThreadLocal 与 JWT：线程隔离与无状态凭证`
- 子 agent 审稿后补齐 03/04/05 三篇旧笔记的宏观设计铺垫（8 包三分类、包定位、分层/依赖方向设计）

**学会了什么**
- 公共层定位与判断标准（多模块共用 + 与业务无关）；**8 包按职责三分类**（契约类 / 配置与转换类 / 状态与能力类）；模块划分设计（有状态 context vs 无状态 utils 分离）
- 常量类设计（**契约属性**：字段名=引用契约、值=行为契约）；Result 统一返回契约（静态工厂锁状态、泛型携带类型、Serializable 为存储兜底、data 是响应数据）
- 枚举 vs 常量（编译器强制 vs 自觉）；异常体系（RuntimeException 冒泡统一处理、BaseException 多态捕获、异常只描述不处理、无参构造=反射兜底）
- @ConfigurationProperties（@Component/@ConfigurationProperties/@Data 协作、中划线↔驼峰绑定、类在 common 值在 server）；JacksonObjectMapper（时间格式定制、FAIL_ON_UNKNOWN_PROPERTIES 容错）
- ThreadLocal 机制（**数据存线程身上、ThreadLocal 只是钥匙**；线程池复用 → remove 必须调用）；JWT（三段结构、生成/解析流程、HS256 对称、防篡改原理、密钥保密防伪造而非防读取）
- 长工具类 = 官方 SDK 示例 + 封装（API 照抄 / 结构自定 / 业务语义），三种注入风格并存

**踩坑记录**
- **参照物被误改**（JwtClaimsConstant 的 `USER_ID` → `USERID`/`"userid"`）：改产物时路径认混——**参照物（`../sky-take-out/backend/...`）只读**，git checkout 恢复
- 常量字段名/值反复写错（`USERNAME` vs `USER_NAME`、`"userId"` vs `"userid"`）：教训——**敏感行（字段名/值）对照参照物逐字复制**
- 泛型方法参数写成 Object（`success(Object data)`）：T 无法推断 → 调用处编译失败；必须 `success(T object)`（泛型方法参数必须用类型参数）
- `JwtProperties.userTokenName` 类型写成 long（应为 String，同组规律：仅 ttl 是 long）；`WeChatProperties` 删了参照物的 `@Value` import、`JwtUtil` 加了参照物没有的 `lombok.Data` import——**零差异纪律：参照物有则抄、无则不加，不多不少**
- `JwtUtil` 方法名 `createJwt`（应为 `createJWT`——方法名是引用契约）
- 审查练习暴露：写 API 凭感觉命名（`threadLocal.setId` 不存在，应为 `set`）；包装类 `Integer` 默认值是 **null** 不是 0

**规格修订确认（已同步各文档）**
- knowledge-map json 包描述：Long→String 序列化 → **实际为时间格式序列化**（已修订）
- 新增"设计"维度：复现与设计并重（mission 成功标准③ / roadmap 单元 2 验收 / plan 总目标 / learning-goals G9 / check 第 9 题）
- 新增"模块划分设计"维度：learning-goals **G10**、check 费曼**第 10 题**、plan 总目标、mission 成功标准③、knowledge-map 新增模块划分设计行、roadmap 学习点（已同步）
- Obsidian 笔记统一补齐宏观设计铺垫：8 包三分类、各包"为什么存在/为什么放这里"、分层与依赖方向设计（03/04/05/06 四篇）

**下阶段调整**
- 下一单元：单元 3（sky-pojo 数据层，49 文件），建议另开新会话按三件套流程推进
- 待处理（沿用）：PATH 上损坏的 `javapath\java.exe`；Maven 面板重复加载问题
