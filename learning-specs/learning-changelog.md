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
