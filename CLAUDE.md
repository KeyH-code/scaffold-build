# CLAUDE.md — 苍穹外卖脚手架搭建工作区

**重要背景，请先读**：本目录是「苍穹外卖」教学项目的**脚手架搭建学习工作区**——从零搭建一份与官方脚手架完全一致的 Maven 工程（`sky-take-out/`），即"最终结构 = 官方脚手架当前状态（架构完整、业务仅员工登录）"。复刻产物将作为后续功能开发（SDD vibecoding）的基底，接口、数据库、前端全部对齐、无缝衔接。搭建过程遵循 sdd-learning skill（规范驱动学习）。

## 参照物与产物
- **官方脚手架（参照物/基准，只读不可修改）**：`E:\ACourseLearning\sky-take-out\sky-take-out-learn\sky-take-out\backend\sky-take-out\`（= 课程 day01 初始工程，架构 100% 给定、业务仅员工登录链路）
- **本工作区复刻产物**：`./sky-take-out/`（Phase 2 产出），验收 = 与官方逐文件 diff **内容一致**（目录结构/包名/类名/配置/行为；XML 元素顺序与空行差异放行，缺内容/写错必改——细则见单元 check.md）

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

## 教学协作约定（重要）
- **推进节奏由用户明确确认**：费曼检验回答有错或提出疑问时，老师解答后必须等用户明确说"明白了/继续"，才进入下一知识点；只有用户一次性回答正确且无疑问句时，老师才可直接推进。详见 `learning-specs/learning-mission.md`「教学协作约定」。
- **文档词汇约定**：用户说「<全局文档>」= `learning-specs/` 下的 learning-mission.md / knowledge-map.md / learning-roadmap.md / learning-changelog.md（章程四文档）；说「<局部文档>」= `learning-specs/<日期>_<单元名>/` 下当前学习单元的三件套（plan / learning-goals / check）。
- **知识沉淀（Obsidian 笔记）**：① 知识点切换文件/代码类型时（如 pom.xml → .java）；② 大阶段（roadmap 里程碑）完成时——满足其一即记录进 Obsidian 仓库 `E:\deeplearning\dl\Java`，**记录更新完成后才进入下一步**。
- **新会话 prompt 交接**：当按 sdd-learning 流程（或任何 skill 流程）需要另开新会话推进时，老师直接提供 skill 中的**固定 prompt 模板**（`~/.claude/skills/sdd-learning/SKILL.md`「固定 Prompt 模板」，按阶段选：规划/实施/验证/复盘），用户填占位内容后复制到新会话。**不重复文档已有信息**——背景、进度、约定由 CLAUDE.md 与 learning-specs 文档自动跟踪，prompt 只带阶段与占位参数。

## git 分工（重要约定）
- **所有 git 操作由用户亲自执行**（init / add / commit / branch / merge / push 等），AI 只提供命令建议与讲解——用户正借此学透 git。
- 远程仓库：https://github.com/KeyH-code/scaffold-build（已关联）
- 每单元独立分支、小步提交，git log 即学习轨迹。
- **操作后确认（老师职责）**：用户每次 git 操作（提交/合并/推送/建删分支）后，老师立即核对仓库状态（分支/工作区/提交图/远程同步），确认无误才继续下一步；状态异常（漏推送、跟踪错乱、误操作）当场指出并修正——不积压、不后修。

## 当前状态
- 章程阶段 ✅ 完成：learning-mission / knowledge-map / learning-roadmap / changelog 四文档已生成并审查通过
