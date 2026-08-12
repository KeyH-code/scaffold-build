# CLAUDE.md — 苍穹外卖脚手架搭建工作区

**重要背景，请先读**：本目录是「苍穹外卖」教学项目的**脚手架搭建学习工作区**——从零搭建一份与官方脚手架完全一致的 Maven 工程（`sky-take-out/`），即"最终结构 = 官方脚手架当前状态（架构完整、业务仅员工登录）"。复刻产物将作为后续功能开发（SDD vibecoding）的基底，接口、数据库、前端全部对齐、无缝衔接。搭建过程遵循 sdd-learning skill（规范驱动学习）。

## 参照物与产物
- **官方脚手架（参照物/基准，只读不可修改）**：`E:\ACourseLearning\sky-take-out\sky-take-out-learn\sky-take-out\backend\sky-take-out\`（= 课程 day01 初始工程，架构 100% 给定、业务仅员工登录链路）
- **本工作区复刻产物**：`./sky-take-out/`（Phase 2 产出），验收 = 与官方逐文件 diff **零差异**（目录结构/包名/类名/配置/行为）

## 目录结构
- `sky-take-out/`：（Phase 2 产出）从零搭建的 Maven 多模块工程：sky-common（公共层）/ sky-pojo（entity/dto/vo）/ sky-server（controller/service/mapper 等）
- `learning-specs/`：SDD 学习文档（learning-mission / knowledge-map / learning-roadmap / changelog / environment 环境核查报告 + 每单元三件套）

## 技术栈（复刻目标）
Spring Boot 2.7.3（Java 17）、MyBatis 2.2.0、MySQL 8、Druid、Redis、JWT(jjwt 0.9.1)、Lombok、knife4j 3.0.2、阿里云 OSS、POI、WebSocket、微信支付 SDK。

## 参考资源（勿改动）
- 课程根目录：`E:\ACourseLearning\sky-take-out\sky-take-out\`（讲义、PPT、资料、完整版代码）
- 完整版代码：`资料/day12/项目完整版代码/sky-take-out/`（功能开发阶段的审查基准）
- 接口文档：`资料/day01/项目接口/*.json`；数据库设计文档：`资料/day01/数据库/数据库设计文档.md`

## SDD 学习流程（详见 sdd-learning skill）
章程阶段（learning-specs/ 三文档）→ 学习单元循环（规划→实施→验证→合并）→ 复盘
- 每单元：git 独立分支，小步提交
- 验证不偷懒：费曼检验 + 亲手跑通 + 独立审查 AI 代码

## git 分工（重要约定）
- **所有 git 操作由用户亲自执行**（init / add / commit / branch / merge / push 等），AI 只提供命令建议与讲解——用户正借此学透 git。
- 远程仓库：https://github.com/KeyH-code/scaffold-build（已关联）
- 每单元独立分支、小步提交，git log 即学习轨迹。

## 当前状态
- Phase 0（环境核查）✅ 完成（2026-08-12）：MySQL 在跑 / JDK 21 先试跑（目标 17）/ IDEA 内置 Maven / Redis 未装（功能阶段再装），详见 `learning-specs/environment.md`
- 章程阶段 ✅ 完成：learning-mission / knowledge-map / learning-roadmap / changelog 四文档已生成并审查通过
- **下一步：单元 1（Maven 多模块骨架）**，建议另开新会话，按 sdd-learning 三件套流程（规划→实施→验证→合并）推进
