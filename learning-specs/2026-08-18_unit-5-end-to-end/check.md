# 单元 5 验证标准：端到端验收

> 生成日期：2026-08-18  
> 本文档定义如何证明"学会了"——验证判定标准与通过条件

## 验证维度总览

| 维度 | 验证方式 | 通过标准 |
|---|---|---|
| 代码完整性 | 全量 diff（任务组 1） | 93 源文件必改项清零 |
| 服务启动 | 控制台日志 + 浏览器访问 | 显示 "Started SkyApplication" + doc.html 可访问 |
| 接口联调 | curl 演示观察 | 能说出成功/失败判断标准 + 理解 token 流转 |
| 宏观理解 | 费曼检验 4 题 | 能画图讲流程，逻辑清晰无混淆 |
| 审查能力 | 审查练习 1 题 | 找出 3 处错误并说明正确答案 |

---

## 一、代码完整性验证（任务组 1 前置）

### 验证操作
1. 统计文件数量：`find sky-take-out -name "*.java" -o -name "*.xml" -o -name "*.yml" | wc -l`
2. 抽查关键文件（10 个）：使用 IDEA Compare With 或 `git diff --no-index`

### 通过标准
**必改项清零**：
- ❌ 字段名/方法名/类名错误
- ❌ 字段类型错误
- ❌ 缺少字段/方法
- ❌ 逻辑错误
- ❌ 缺少必要的 import

**放行项**：
- ✅ 空行数量/位置不同
- ✅ 注释措辞细微差异（语义相同）
- ✅ import 语句顺序不同
- ✅ XML 元素顺序不同
- ✅ 注解顺序不同

**预期结果**：93 个文件 + 抽查 10 个文件内容一致

---

## 二、服务启动验证

### 验证操作
1. IDEA 打开项目，右键 Run 'SkyApplication'
2. 观察控制台日志，等待启动完成
3. 浏览器访问 `http://localhost:8080/doc.html`

### 通过标准
**必须同时满足三条**：
1. 控制台显示 `Started SkyApplication in X.XXX seconds (JVM running for X.XXX)`
2. 控制台显示 `Tomcat started on port(s): 8080 (http) with context path ''`
3. 浏览器能打开 knife4j 文档页，左侧菜单显示"员工相关接口"

### 常见失败场景
| 现象 | 原因 | 是否通过 |
|---|---|---|
| 数据库连接失败 | application-dev.yml 密码/端口/库名错误 | ❌ 不通过，需修正配置 |
| 端口 8080 占用 | 其他服务占用端口 | ❌ 不通过，需改端口或杀进程 |
| Bean 注入失败 | 依赖未下载或代码缺失 | ❌ 不通过，需 Maven Reload 或补代码 |
| 日志有 WARN 但最终启动成功 | 非致命警告（如循环引用警告） | ✅ 通过，WARN 可忽略 |

---

## 三、接口联调验证（curl 演示观察）

### 验证操作
**老师执行 curl 命令，学生观察输出**

**演示场景 1：登录成功**
```bash
curl -X POST http://localhost:8080/admin/employee/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```
**预期返回**：
```json
{
  "code": 1,
  "msg": null,
  "data": {
    "id": 1,
    "userName": "admin",
    "name": "管理员",
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
```

**演示场景 2：用户名错误**
```bash
curl -X POST http://localhost:8080/admin/employee/login \
  -H "Content-Type: application/json" \
  -d '{"username":"wronguser","password":"123456"}'
```
**预期返回**：
```json
{
  "code": 0,
  "msg": "账号不存在",
  "data": null
}
```

**演示场景 3：密码错误**
```bash
curl -X POST http://localhost:8080/admin/employee/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"wrongpass"}'
```
**预期返回**：
```json
{
  "code": 0,
  "msg": "密码错误",
  "data": null
}
```

### 通过标准
**学生能够**：
1. 识别成功标志：`code: 1` + `data.token` 存在
2. 识别失败标志：`code: 0` + `msg` 包含错误原因
3. 说出 token 格式：JWT（三段 base64 用 `.` 分隔）
4. 讲清楚 token 生命周期：生成（Service 层）→ 返回（VO 携带）→ 携带（请求头）→ 验证（拦截器）→ 使用（BaseContext）

**不通过表现**：
- 不知道如何判断成功/失败
- 不知道 token 是什么格式
- 不清楚 token 从哪里来、到哪里去

---

## 四、费曼检验（宏观理解）

### 第 1 题：画出 Spring Boot 启动流程图，并解释每个阶段发生了什么

**通过标准**：
- 能画出四阶段流程图：创建容器 → 扫描 Bean → 配置加载 → Tomcat 启动
- 能说出 `@SpringBootApplication` 三合一注解的作用：
  - `@SpringBootConfiguration`：标记为配置类
  - `@EnableAutoConfiguration`：自动配置（根据依赖自动加载 Bean）
  - `@ComponentScan`：扫描 `com.sky` 包下的 `@Component` 等注解
- 能说出 Bean 生命周期：扫描 → 实例化 → 依赖注入（`@Autowired`）→ 放入容器
- 能说出 Tomcat 启动时机：最后启动，监听 8080 端口 → 接收 HTTP 请求

**不通过表现**：
- 顺序错误（如先启动 Tomcat 再扫描 Bean）
- Bean 生命周期混淆（如不知道依赖注入发生在哪个阶段）
- 不知道 `@SpringBootApplication` 的组成

---

### 第 2 题：画出登录请求的完整旅程（八站图），标注每一站的职责

**通过标准**：
- 能画出八站流程：浏览器 → Tomcat → 拦截器 → DispatcherServlet → Controller → Service → Mapper → 数据库 → 返回
- 能说出拦截器介入时机：在 Controller **之前**执行 `preHandle()`
- 能说出拦截器排除登录路径的原因：避免鸡生蛋死锁（登录就是为了获取 token，不能要求先提供 token）
- 能说出 DispatcherServlet 的作用：根据 `@RequestMapping` 路由到对应 Controller

**不通过表现**：
- 调用链方向错误（如 Controller → Mapper → Service）
- 拦截器时机错误（如认为在 Controller 之后执行）
- 不知道为什么要排除登录路径

---

### 第 3 题：讲解 token 的完整生命周期

**通过标准**：
- 能说出 token 生成位置：`EmployeeServiceImpl.login()` 调用 `JwtUtil.createJWT(claims, jwtProperties.getAdminTtl())`
- 能说出 token 返回方式：`EmployeeLoginVO` 携带 token → 序列化为 JSON → 返回浏览器
- 能说出 token 携带方式：前端下次请求时在请求头带上 `token: eyJhbGci...`
- 能说出拦截器验证流程：
  1. 从请求头提取 token
  2. `JwtUtil.parseJWT(token)` 解析 → 验证签名/过期/格式
  3. 提取 `empId` → `BaseContext.setCurrentId(empId)` 放入 ThreadLocal
  4. 放行请求到 Controller
- 能说出 token 使用方式：Controller/Service 通过 `BaseContext.getCurrentId()` 获取当前登录用户 ID

**不通过表现**：
- 不知道 token 在哪里生成（如认为在 Controller 生成）
- 不知道拦截器如何验证 token（如不知道验证签名/过期）
- 不知道 `BaseContext.getCurrentId()` 的值从哪里来（如不知道拦截器放入 ThreadLocal）

---

### 第 4 题：解释依赖注入如何串联三层架构

**通过标准**：
- 能画出依赖关系图：EmployeeController 注入 EmployeeService → EmployeeService 注入 EmployeeMapper
- 能说出 `@Autowired` 的作用：让 Spring 从容器中找到对应 Bean 并注入（不需要手动 `new`）
- 能说出手动 `new` 的后果：
  1. `new EmployeeServiceImpl()` 创建的对象不在容器中
  2. 该对象的 `@Autowired EmployeeMapper` 无法注入（Spring 不管理它）
  3. `employeeMapper` 字段为 null
  4. 调用 `employeeMapper.getByUsername()` 时 → `NullPointerException`
- 能说出三层职责分工：
  - Controller：接收 HTTP 请求 → 组装返回（关心 HTTP）
  - Service：业务逻辑 → 密码比对 → 抛异常（不关心 HTTP）
  - Mapper：SQL 执行 → 数据库访问（不关心业务规则）

**不通过表现**：
- 不知道为什么不能手动 `new`（如认为 `new` 也能注入依赖）
- 依赖方向错误（如认为 Service 注入 Controller）
- 不清楚三层职责（如认为 Controller 直接操作数据库）

---

## 五、审查练习

### 题目：找出 3 处宏观理解错误

**场景**：小白同学写了一段启动日志解读，请找出 3 处错误：

> "Spring Boot 启动时，先启动 Tomcat 监听 8080 端口，然后扫描 `@Component` 注解创建 Bean，最后加载 application.yml 配置文件。Controller 收到请求后，会先调用 Mapper 查数据库，再调用 Service 处理业务逻辑，最后返回 JSON。拦截器在 Controller 之后执行，用来验证返回结果是否合法。"

### 通过标准
**必须找出以下 3 处错误并说明正确答案**：

**错误 1**：启动顺序错误
- 错误表述："先启动 Tomcat 监听 8080 端口，然后扫描 `@Component` 注解创建 Bean，最后加载 application.yml 配置文件"
- 正确答案：应该是 创建容器 → 扫描 Bean → 加载配置 → 启动 Tomcat（Tomcat 是最后启动的）

**错误 2**：调用链方向错误
- 错误表述："Controller 收到请求后，会先调用 Mapper 查数据库，再调用 Service 处理业务逻辑"
- 正确答案：Controller → Service → Mapper（三层架构，逐层调用）

**错误 3**：拦截器时机错误
- 错误表述："拦截器在 Controller 之后执行，用来验证返回结果是否合法"
- 正确答案：拦截器在 Controller **之前**执行（`preHandle()`），用来验证请求（如 token），不是验证返回结果

### 评分标准
- 找出全部 3 处错误 + 说明正确答案 → ✅ 通过
- 找出 2 处或以下 → ❌ 不通过，需重新学习对应知识点

---

## 六、综合通过条件

### 最终判定
**必须同时满足以下五项**：
1. ✅ 服务启动验证通过（日志显示成功 + doc.html 可访问）
2. ✅ 接口联调验证通过（返回 `code: 1` + token 存在）
3. ✅ 全量 diff 验证通过（93 文件必改项清零）
4. ✅ 费曼检验通过（4 题回答正确，能画图讲流程）
5. ✅ 审查练习通过（找出 3 处错误并说明正确答案）

### 不通过处理
| 未通过项 | 补救措施 |
|---|---|
| 服务启动失败 | 排查日志错误 → 修正配置/代码 → 重新启动 |
| 接口联调失败 | 检查数据库数据 → 检查拦截器配置 → 重新测试 |
| diff 有必改项 | 修正差异 → 重新 diff → 确认清零 |
| 费曼题回答错误 | 回顾对应知识点 → 重新画图/讲解 → 再次检验 |
| 审查练习失败 | 分析为什么没找出错误 → 补充学习 → 重做练习 |

### 复盘要求
**通过全部验证后**，需在 Obsidian 笔记中回答以下复盘问题：
1. 单元 5 补齐了哪些宏观理解（与前四单元的细节复刻有什么不同）？
2. 启动流程、请求旅程、三层协作，哪个概念最难理解？为什么？
3. 如果让你向新人讲解"为什么不能手动 new"，你会怎么讲？
4. 你认为后续学习（过渡单元 / vibecoding）需要重点关注什么？

---

## 七、验证时间预估
- 服务启动验证：5 分钟
- 接口联调验证：5 分钟
- 全量 diff 验证：20 分钟
- 费曼检验（4 题）：15 分钟
- 审查练习（1 题）：5 分钟
- **总计**：50 分钟（含互动答疑）

---

## 八、常见误判提醒
1. **不要跳过费曼题**：即使接口调通了，宏观理解没建立 = 不通过（单元 5 核心目标是补齐宏观理解）
2. **不要放行必改项**：字段名写错、类型写错、逻辑错误，即使"功能能跑" = 不通过（零差异纪律）
3. **不要背答案**：费曼题要求"画图讲流程"，死记硬背讲不清楚 = 不通过（理解 > 记忆）
4. **不要忽略审查练习**：找错题训练"识别宏观理解错误"的能力，关键能力点
