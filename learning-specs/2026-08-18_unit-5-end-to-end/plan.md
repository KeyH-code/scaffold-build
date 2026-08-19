# 单元 5 学习计划：端到端验收

> 生成日期：2026-08-18  
> **当前状态**：任务组 1-2 ✅ 完成，任务组 3 ⏸️ 待开始  
> 目标：证明复刻成功（跑起来 + 登录通 + 零差异）+ 补齐宏观架构理解

## 总目标
- **验收目标**：全量 diff 内容一致 → 环境准备 → 启动服务 + 接口联调 → 宏观架构理解
- **理解目标**：补齐前四单元忽略的宏观架构——Spring Boot 启动全景、请求完整旅程、三层协作机制、配置体系联动
- **产物**：登录接口返回正确结果（含 token）+ diff 内容一致清单确认 + Obsidian 笔记（宏观流程）

## 任务组清单

### 任务组 1：全量 diff（代码完整性验证）⏱️ 20 分钟 ✅ 已完成
**目标**：在开始教学前，先确认代码完整、无遗漏，避免后续教学时发现问题再回头修

**完成情况**：
- ✅ 文件数量：93 个源文件，与官方一致
- ✅ 必改项：发现 1 处（EmployeeServiceImpl.java 缺少 `import org.springframework.util.DigestUtils;`），已修复
- ✅ 其他差异：全部为放行项（空行、注释、空格风格）

**修改记录**：
1. `sky-server/src/main/java/com/sky/service/impl/EmployeeServiceImpl.java`：补充 `import org.springframework.util.DigestUtils;`

---

### 任务组 2：环境准备（AI 全包）⏱️ 10 分钟 ✅ 已完成
**目标**：搭建运行环境（数据库 + 配置检查），确保后续教学不被环境问题打断

**完成情况**：
- ✅ 数据库：sky_take_out 已存在，11 张表已导入
- ✅ 测试数据：employee 表有 admin 账号（密码已改为明文 123456，配合代码 TODO）
- ✅ 配置文件：application-dev.yml 密码已修正为 123456（本地 MySQL 实际密码）
- ✅ 端口检查：8080 端口空闲

**修改记录**：
1. `sky-server/src/main/resources/application-dev.yml`：password 从 root 改为 123456
2. 数据库：`UPDATE employee SET password='123456' WHERE username='admin';`（配合代码中的明文比对）

---

### 任务组 3：启动服务 + 接口联调演示 ⏱️ 15 分钟 ✅ 已完成
**目标**：学生启动服务，老师用 curl 演示接口调用，学生观察输入输出

#### 3.1 启动服务（学生执行）✅ 已完成
**操作步骤**：
1. IDEA 打开项目 `sky-take-out/`
2. 找到 `SkyApplication.java`，右键 → Run 'SkyApplication'
3. 观察控制台日志

**完成情况**：
- ✅ 启动成功：控制台显示 "server started"
- ✅ knife4j 文档页可访问：`http://localhost:8080/doc.html` 返回 200

#### 3.2 接口联调演示（老师执行）✅ 已完成
**演示结果**：
- ✅ 场景 1（登录成功）：返回 `{"code":1, "data":{"token":"eyJhbGci..."}}` 
- ✅ 场景 2（用户名错误）：返回 `{"code":0, "msg":"账号不存在"}`
- ✅ 场景 3（密码错误）：返回 `{"code":0, "msg":"密码错误"}`

**说明**：学生不需要自己操作 knife4j 或 apifox，只需观察老师的 curl 演示输出。

---

### 任务组 4：概念铺垫（宏观架构补课）⏱️ 20 分钟 ⏸️ 待开始
**目标**：建立完整全景图，理解 Spring Boot 启动与请求处理的宏观流程

**重要说明**：
- 本任务组是单元 5 的**核心教学目标**，补齐前四单元忽略的宏观架构理解
- 用户明确反馈：前四单元精力花在细节纠正，整体架构、Spring 工作原理有所忽略
- **下一个 agent 必须从这里开始**，按照以下 4 个知识点逐一讲解

**内容清单**：
1. **Spring Boot 启动全景**（从 `main()` 到服务就绪）
   - `@SpringBootApplication` 三合一注解触发的事件
   - 容器启动四步骤：创建容器 → 扫描 Bean → 配置加载 → 启动 Tomcat
   - Bean 生命周期：扫描（`@Component` 等注解）→ 实例化 → 依赖注入（`@Autowired`）→ 放入容器
   - Tomcat 嵌入式服务器启动 → 监听 8080 端口 → 等待 HTTP 请求

2. **请求完整旅程**（八站全景图）
   - 站 1：浏览器/API 工具发送 HTTP 请求 → `POST http://localhost:8080/admin/employee/login`
   - 站 2：Tomcat 接收请求 → 交给 DispatcherServlet（Spring MVC 核心调度器）
   - 站 3：拦截器 `JwtTokenAdminInterceptor.preHandle()` 验证 token（登录路径排除）
   - 站 4：DispatcherServlet 根据 `@RequestMapping` 路由到 `EmployeeController.login()`
   - 站 5：Controller 调用 `EmployeeService.login()` 处理业务逻辑
   - 站 6：Service 调用 `EmployeeMapper.getByUsername()` 查询数据库
   - 站 7：MyBatis 执行 SQL → MySQL 返回结果 → 逐层返回（Mapper → Service → Controller）
   - 站 8：Controller 组装 `Result.success(employeeLoginVO)` → 序列化为 JSON → 返回浏览器

3. **三层架构协作机制**
   - Controller（前后端联系）：接收 HTTP 请求 → 组装返回 → 签发 JWT
   - Service（业务逻辑）：密码比对 → 抛异常 → 不关心 HTTP
   - Mapper（数据访问）：SQL 执行 → 不关心业务规则
   - 依赖注入串联：`@Autowired` 让 Spring 自动注入依赖（Controller 注入 Service、Service 注入 Mapper），不手动 `new`（手动 new 脱离容器 → 依赖链断裂 → NullPointerException）

4. **配置体系联动**
   - `application.yml`（通用配置）+ `application-dev.yml`（开发环境） → `spring.profiles.active: dev` 激活
   - 占位符 `${sky.datasource.host}` 从 application-dev.yml 读值
   - `@ConfigurationProperties(prefix = "sky.jwt")` 绑定配置 → `JwtProperties` Bean 放入容器 → `JwtTokenAdminInterceptor` 注入使用
   - **配置类在 common（可复用）、配置值在 server（环境相关）**

**验证标准**：能画出启动流程图 + 请求旅程八站图，讲清楚各层职责与依赖注入原理

**教学方法**：
- 每个知识点讲完后，让学生用自己的话复述一遍（费曼学习法）
- 如果学生复述有误或提出疑问，立即解答并等学生明确"明白了"才继续
- 如果学生复述正确且无疑问，直接推进下一个知识点

---

### 任务组 5：费曼检验（4 题）⏱️ 20 分钟 ⏸️ 待开始
**目标**：通过费曼检验与审查练习，确认宏观理解达成

#### 费曼检验（4 题，侧重画图讲流程）

**1. 画出 Spring Boot 启动流程图，并解释每个阶段发生了什么**
- 要求：从 `SpringApplication.run()` 开始，画出容器创建 → 扫描 Bean → 配置加载 → Tomcat 启动四个阶段
- 追问：`@SpringBootApplication` 三合一注解分别触发了什么？Bean 是什么时候被创建并放入容器的？

**2. 画出登录请求的完整旅程（八站图），标注每一站的职责**
- 要求：从浏览器发送请求到返回 JSON，画出八站流程图
- 追问：拦截器在哪一站介入？为什么登录路径要排除？如果不排除会发生什么？

**3. 讲解 token 的完整生命周期**
- 要求：从生成 → 返回 → 存储 → 携带 → 验证 → 使用，讲清楚每个环节
- 追问：token 是在哪一层生成的？拦截器验证 token 时做了哪些检查？`BaseContext.getCurrentId()` 的值是从哪里来的？

**4. 解释依赖注入如何串联三层架构**
- 要求：以 EmployeeController → EmployeeService → EmployeeMapper 为例，讲清楚 `@Autowired` 的作用
- 追问：如果手动 `new EmployeeServiceImpl()` 会发生什么？为什么会 NullPointerException？

#### 审查练习（1 题，找出宏观理解错误）

**场景**：小白同学写了一段启动日志解读，请找出 3 处错误：

> "Spring Boot 启动时，先启动 Tomcat 监听 8080 端口，然后扫描 `@Component` 注解创建 Bean，最后加载 application.yml 配置文件。Controller 收到请求后，会先调用 Mapper 查数据库，再调用 Service 处理业务逻辑，最后返回 JSON。拦截器在 Controller 之后执行，用来验证返回结果是否合法。"

**参考答案**：
1. 启动顺序错误：应该是 创建容器 → 扫描 Bean → 加载配置 → 启动 Tomcat（Tomcat 是最后启动的）
2. 调用链错误：Controller → Service → Mapper（不是 Controller → Mapper → Service）
3. 拦截器时机错误：拦截器在 Controller **之前**执行（preHandle），用来验证请求（如 token），不是验证返回结果

**验证标准**：费曼 4 题回答正确 + 审查练习找出 3 处错误

---

### 任务组 6：收尾 ⏱️ 10 分钟 ⏸️ 待开始
**目标**：沉淀笔记 + 更新 changelog + git 提交

**操作清单**：
1. **Obsidian 笔记沉淀**：生成 `13. Spring Boot 启动与请求处理全景.md`，聚焦：
   - 启动流程四阶段（容器 → Bean → 配置 → Tomcat）
   - 请求旅程八站图（从 HTTP 到数据库再返回）
   - 三层架构协作（依赖注入串联）
   - token 生命周期（生成 → 验证 → 使用）
   - 配置体系联动（yml → Properties → Bean）

2. **更新 learning-changelog.md**：
   - 做了什么：任务组 1-6 完成，全量 diff 内容一致 + 环境就绪 + 服务启动成功 + 接口联调演示 + 宏观理解达成
   - 学会了什么：Spring Boot 启动全景、请求旅程八站、三层协作机制、token 完整流程、配置联动
   - 踩坑记录：EmployeeServiceImpl.java 缺少 import、application-dev.yml 密码配置不一致、employee 表密码需改为明文
   - 规格修订：任务组顺序调整（diff 提前 + 环境准备 AI 全包 + 接口联调改为 curl 演示 + 启动服务与接口联调合并为任务组 3 + 概念铺垫改为任务组 4）
   - 下阶段调整：单元 5 验收完成，建议另开会话做整体复盘（对照 roadmap 检查五单元达成度）+ 考虑过渡单元（MyBatis 进阶/Redis/AOP）

3. **git 提交**（用户执行，老师给命令）：
   ```bash
   # 1. 查看当前状态
   git status
   
   # 2. 提交单元 5 修改（EmployeeServiceImpl import + application-dev.yml 密码）
   git add sky-server/src/main/java/com/sky/service/impl/EmployeeServiceImpl.java
   git add sky-server/src/main/resources/application-dev.yml
   git commit -m "单元5：修复 diff 必改项（补充 DigestUtils import + 修正数据库密码配置）"
   
   # 3. 提交单元 5 规格文档
   git add learning-specs/2026-08-18_unit-5-end-to-end/
   git commit -m "单元5：规格三件套（plan/learning-goals/check）+ 交接文档"
   
   # 4. 更新 roadmap 与 changelog
   git add learning-specs/learning-roadmap.md learning-specs/learning-changelog.md
   git commit -m "单元5：端到端验收完成，更新 roadmap 与 changelog"
   
   # 5. 推送到远程
   git push origin main
   ```

4. **老师确认 git 状态**（重要约定）：
   - 检查工作区干净：`git status` 显示 "working tree clean"
   - 检查提交图：`git log --oneline -5` 确认三次提交存在
   - 检查远程同步：`git branch -vv` 确认 main 分支已推送

**验证标准**：Obsidian 笔记生成 + changelog 更新 + git 提交推送成功

---

## 时间预估
- 任务组 1（全量 diff）：20 分钟 ✅ 已完成
- 任务组 2（环境准备）：10 分钟 ✅ 已完成
- 任务组 3（启动服务 + 接口联调）：15 分钟 ✅ 已完成
- 任务组 4（概念铺垫）：20 分钟 ⏸️ **下一步从这里开始**
- 任务组 5（费曼检验）：20 分钟 ⏸️ 待开始
- 任务组 6（收尾）：10 分钟 ⏸️ 待开始
- **总计**：95 分钟，已完成 45 分钟，剩余 50 分钟

---

## 教学节奏约定
1. **推进确认**：费曼题回答有错或提出疑问 → 老师解答 → 等用户明确"明白了/继续" → 下一知识点；回答正确且无疑问句 → 老师直接推进
2. **知识沉淀触发**：任务组 6 收尾时统一记录 Obsidian 笔记（大阶段完成）
3. **git 操作后确认**：用户每次 git 操作后，老师立即核对仓库状态（分支/工作区/提交图/远程同步），确认无误才继续

---

## 任务组顺序调整说明
**原计划**（plan.md 生成时）：
1. 全量 diff
2. 环境准备
3. 概念铺垫（宏观架构）← 理论先行
4. 启动服务 + 接口联调 ← 实践验证

**调整后**（实际执行）：
1. 全量 diff ✅
2. 环境准备 ✅
3. 启动服务 + 接口联调 ✅ ← 先跑通实践
4. 概念铺垫（宏观架构）⏸️ ← 再补理论（**当前位置**）
5. 费曼检验 ⏸️
6. 收尾 ⏸️

**调整原因**：
- 环境准备完成后，直接跑通实践（启动 + 接口调用）可以快速验证"复刻成功"
- 用户看到接口返回结果后，再补充宏观架构理解，有具体场景作为参照，更容易理解
- 但导致上下文混乱：用户看到 curl 演示结果，但不清楚"请求是怎么走到 Controller 的"等背景知识

**下一步行动**：
从任务组 4 开始，按照 4 个知识点（启动全景 / 请求旅程 / 三层协作 / 配置联动）逐一讲解，每个知识点讲完让用户复述，确认理解无误后再推进。
