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
