# 单元 5 学习目标：端到端验收

> 生成日期：2026-08-18  
> 本文档定义"学会了"的标准——理解到什么程度、产出什么

## 学习目标分类

### G1：代码完整性验证能力
**能力要求**：能在教学前快速验证代码完整、无遗漏

**达标标准**：
- 能确认文件数量一致：93 源文件（根 pom + sky-common 30 + sky-pojo 50 + sky-server 12）
- 能使用 IDEA Compare With 或 `git diff --no-index` 工具
- 能判断放行项：空行/注释措辞/import 顺序/XML 元素顺序/注解顺序
- 能识别必改项：字段名/方法名/类名错误、字段类型错误、缺少字段/方法、逻辑错误

**验证方式**：任务组 1 全量 diff 必改项清零

---

### G2：Spring Boot 启动全景理解
**能力要求**：能画出启动流程图，讲清楚从 `main()` 到服务就绪的四个阶段

**达标标准**：
- 能说出 `@SpringBootApplication` 三合一注解的作用（`@SpringBootConfiguration` + `@EnableAutoConfiguration` + `@ComponentScan`）
- 能画出启动四阶段：创建容器 → 扫描 Bean → 配置加载 → Tomcat 启动
- 能解释 Bean 生命周期：扫描（`@Component` 等注解）→ 实例化 → 依赖注入 → 放入容器
- 能说出 Tomcat 嵌入式服务器的作用：监听 8080 端口 → 接收 HTTP 请求 → 交给 Spring MVC

**验证方式**：费曼题 1（画出启动流程图并解释）

---

### G2：请求完整旅程理解
**能力要求**：能画出登录请求的八站旅程图，讲清楚每一站的职责与协作机制

**达标标准**：
- 能画出八站流程：浏览器 → Tomcat → 拦截器 → DispatcherServlet → Controller → Service → Mapper → 数据库 → 返回
- 能解释 DispatcherServlet 的作用：Spring MVC 核心调度器，根据 `@RequestMapping` 路由到对应 Controller
- 能说出拦截器介入时机：在 Controller **之前**执行 `preHandle()`，验证 token
- 能解释拦截器排除登录路径的原因：避免鸡生蛋死锁（登录就是为了获取 token，不能要求先提供 token）

**验证方式**：费曼题 2（画出请求旅程八站图）

---

### G3：三层架构协作机制理解
**能力要求**：能讲清楚 Controller/Service/Mapper 如何分工，依赖注入如何串联三层

**达标标准**：
- 能说出三层职责：
  - Controller：接收 HTTP 请求 → 组装返回 → 签发 JWT（关心 HTTP）
  - Service：业务逻辑 → 密码比对 → 抛异常（不关心 HTTP）
  - Mapper：SQL 执行 → 数据库访问（不关心业务规则）
- 能解释 `@Autowired` 的作用：让 Spring 自动注入依赖（从容器中找到对应 Bean 并注入）
- 能说出手动 `new` 的后果：脱离容器管理 → 依赖链断裂 → 注入的字段为 null → NullPointerException
- 能画出依赖关系图：Controller 注入 Service、Service 注入 Mapper（单向依赖）

**验证方式**：费曼题 4（解释依赖注入如何串联三层）

---

### G4：token 完整生命周期理解
**能力要求**：能讲清楚 token 从生成到验证到使用的完整流程

**达标标准**：
- 能说出 token 生成位置：`EmployeeServiceImpl.login()` 调用 `JwtUtil.createJWT()`
- 能说出 token 返回方式：`EmployeeLoginVO` 携带 → 序列化为 JSON → 返回浏览器
- 能说出 token 携带方式：前端下次请求时在请求头带上 `token: eyJhbGci...`
- 能说出拦截器验证流程：解析 token → 验证签名/过期 → 提取 empId → 放入 ThreadLocal → 放行
- 能说出 token 使用方式：Controller/Service 通过 `BaseContext.getCurrentId()` 获取当前登录用户 ID

**验证方式**：费曼题 3（讲解 token 的完整生命周期）

---

### G5：配置体系联动理解
**能力要求**：能讲清楚 yml 配置如何与 `@ConfigurationProperties` 联动，Bean 如何被注入使用

**达标标准**：
- 能说出双环境配置机制：`application.yml`（通用）+ `application-dev.yml`（开发） → `spring.profiles.active: dev` 激活
- 能说出占位符工作原理：`${sky.datasource.host}` 从 application-dev.yml 读值（配置优先级：特定环境 > 通用）
- 能说出配置绑定流程：`@ConfigurationProperties(prefix = "sky.jwt")` → Spring 读取配置 → 创建 `JwtProperties` Bean → 放入容器
- 能说出配置类与配置值分离设计：配置类在 common（可复用）、配置值在 server（环境相关）

**验证方式**：任务组 1 概念铺垫讲解 + 审查练习

---

### G6：接口联调理解能力（观察输出）
**能力要求**：通过观察 curl 演示，理解接口调用的输入输出关系

**达标标准**：
- 能识别 HTTP 请求结构：请求行（`POST /admin/employee/login`）+ 请求头（`Content-Type: application/json`）+ 请求体（JSON 参数）
- 能判断接口调用成功/失败：`code: 1` + `data.token` 存在 → 成功；`code: 0` + `msg` 包含错误原因 → 失败
- 能说出成功返回的关键字段：`id`、`userName`、`name`、`token`（JWT 格式）
- 能说出失败返回的错误信息：账号不存在、密码错误

**验证方式**：任务组 4 观察 curl 演示 + 能说出成功/失败判断标准

---

### G7：启动日志阅读能力
**能力要求**：能看懂 Spring Boot 启动日志，判断启动成功或定位报错原因

**达标标准**：
- 能识别启动成功标志：`Started SkyApplication in X.XXX seconds`
- 能识别 Tomcat 启动标志：`Tomcat started on port(s): 8080 (http)`
- 能识别路由映射标志：`Mapped "{[/admin/employee/login],methods=[POST]}"`
- 能识别常见报错类型：数据库连接失败（`CommunicationsException`）、端口占用（`Address already in use`）、Bean 注入失败（`Could not autowire`）

**验证方式**：任务组 3 启动服务成功 + 观察日志判断

---

### G8：全量 diff 验收能力（已前置到 G1）
**说明**：此目标已合并到 G1（代码完整性验证能力），因任务组顺序调整，diff 提前到第一步。

---

### G9：宏观架构复盘能力
**能力要求**：能从整体视角总结前五单元的学习，识别宏观理解盲区

**达标标准**：
- 能说出前四单元聚焦点：细节复刻（Maven 坐标、公共层 29 文件、数据层 49 文件、登录链路 8 文件）
- 能说出单元 5 补齐点：宏观架构（启动流程、请求旅程、三层协作、token 流转、配置联动）
- 能说出复刻目标达成标准：代码与参照物内容一致 + 登录接口跑通 + 理解宏观架构
- 能说出后续学习方向：过渡单元补充 MyBatis 进阶/Redis/AOP，然后进入 vibecoding 功能开发

**验证方式**：任务组 7 收尾复盘 + 更新 changelog

---

## 产物清单
1. **服务启动成功**：控制台显示 "Started SkyApplication" + knife4j 文档页可访问
2. **登录接口通过**：返回 `code: 1` + `data.token` 存在（JWT 格式）
3. **全量 diff 内容一致**：93 源文件必改项清零，差异均为放行项
4. **Obsidian 笔记**：`13. Spring Boot 启动与请求处理全景.md`（聚焦宏观流程）
5. **changelog 更新**：记录单元 5 完成情况、学会了什么、下阶段调整

---

## 理解达标判断（费曼检验 4 题 + 审查练习 1 题）
- **通过标准**：费曼 4 题回答正确（能画图讲流程，逻辑清晰无混淆）+ 审查练习找出 3 处错误
- **不通过表现**：启动顺序混淆、调用链方向错误、拦截器时机错误、依赖注入原理不清、token 流转环节缺失

---

## 学习策略建议
1. **先建立全景图**：任务组 1 概念铺垫不要跳过，宏观理解是后续学习的地基
2. **动手验证理解**：启动服务看日志、调接口看返回、diff 看差异，理论与实践结合
3. **画图强化记忆**：启动流程图、请求旅程图、依赖关系图，视觉化帮助理解
4. **关联前四单元**：回顾单元 2 的 JwtUtil/BaseContext、单元 3 的 DTO/VO、单元 4 的三层架构，打通知识点
