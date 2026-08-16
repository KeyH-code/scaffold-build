# 单元 4 学习计划（plan）

> 单元：sky-server 员工登录链路　日期：2026-08-16　分支：unit-4-sky-server
> 命名约定（沿用单元 1-3）：
> - **参照物** = 官方脚手架工程（只读基准，diff 对照对象）：`../sky-take-out/backend/sky-take-out/sky-server/`
> - **产物** = 本工作区复刻出的工程（要建的东西）：`./sky-take-out/sky-server/`

## 总目标
产出与官方**逐文件 diff 内容一致**的 sky-server 全部 **8 java + 3 resources**（其中 SkyApplication.java 单元 1 已建，本单元仅 diff 核对）；理解"HTTP 请求进来后发生什么"——把单元 1-3 的所有零件（Result / JWT / 异常体系 / 配置绑定 / DTO / VO / Lombok）**组装成一条完整链路**。

本单元是第一个"跑在 Spring 容器里"的单元：**之前 common/pojo 都是纯 Java 类（不归容器管），这里的 7 个新类全部活在 Spring 的世界里**——依赖注入、组件扫描、三层架构、拦截器、全局异常、MyBatis、knife4j 一次全部登场。

**复现与设计并重**：diff 零差异（复现）是验收标准；学习重点在于**链路理解**——一次登录请求从浏览器出发，经过哪些"站点"、每站做什么、失败时走哪条分支、为什么官方这么设计（JWT 在 Controller 签而非 Service、登录路径必须排除在拦截器外、密码明文比对 + TODO）。
（验收细则见 check.md 核对 1。）

## 实施节奏约定（用户 · 老师共同遵守）
- **git 分支时机（用户执行）**：任务组 1 开始前，先在 **main** 上提交本单元规划变更（三件套 + roadmap/knowledge-map 修订，章程演进，沿用单元 3 先例），再 `git checkout -b unit-4-sky-server` 从 main 切出分支；实施代码（任务组 2-5）全部小步提交在该分支，任务组 6 验证通过后合并回 main；
- **开场抽查（单元 2+3 混合，用户拍板）**：开始前老师随机抽查上两个单元的核心知识点——Result 契约 / JWT 生成解析 / ThreadLocal / 配置绑定 + 三者职责 / 登录双类 / Lombok 成套 / @ApiModel（不提示不剧透），不过关先补学；
- 费曼检验全对且无疑问句 → 老师进入下一知识点；回答有错/有疑问 → 老师解答后**必须等用户明确确认"明白了/继续"**，禁止答完疑问直接跳步；
- **任务组内大块内容按块细化为学习小阶段**（如下方"块 N"标注）；用户可随时要求进一步细化粒度；
- **知识沉淀（Obsidian 笔记）**：本单元 **4 篇**（用户拍板，按设计概念划分，编号按主题不按书写顺序）：
  - ① Spring Boot 启动与配置体系（任务组 2 后触发：@SpringBootApplication 组合注解、启动流程、yml 双环境、占位符与配置绑定联动）
  - ③ MyBatis 数据库访问（任务组 3 块 A 后触发：注解 SQL 与 XML 双通道、驼峰映射、@Mapper）
  - ② 三层架构与依赖注入（任务组 3 块 B 后触发：@Autowired、组件家族、调用链、为什么分层）
  - ④ 拦截器与全局异常处理（任务组 5 后触发：守门员与兜底网，含 knife4j 配置小节）
  每篇记入 `E:\deeplearning\dl\Java`，经用户审阅通过后再继续下一步；笔记侧重**通用知识**（Spring 机制、设计动机），项目脚手架仅作示例；
- 与全局 learning-mission.md「教学协作约定」一致，本单元实施全程适用。

## 任务组清单（概念 → 动手 → 验证，按序实施，每任务组结束等用户确认）

### 任务组 1（概念先行 · 🔴 完整故事线——一次登录请求的旅程）
- **生活化类比**：餐厅点餐全流程——顾客（浏览器）→ 门卫查会员证（拦截器）→ 迎宾点菜（Controller）→ 后厨备菜（Service）→ 食材库管理员取货（Mapper）→ 仓库（MySQL）→ 上菜装盘（组装 VO 返回）
- **技术对照**：HTTP 请求 → JwtTokenAdminInterceptor（门卫，查 token）→ EmployeeController（收请求）→ EmployeeServiceImpl（业务判断）→ EmployeeMapper（查库）→ MySQL → 回程：比对结果 → 抛异常（被 GlobalExceptionHandler 接住）或继续 → JWT 签发 → EmployeeLoginVO 组装 → Result 返回
- **八站全览**：用一张时序图把 8 个 java + 3 个 resources 全部"点一次名"，说明每个零件在哪一站出场、干什么——先有全局地图，再逐个进城
- 读参照物 sky-server 目录结构，数清 8 java + 3 resources 各自归属（注意：pom.xml 单元 1 已建，不属于本单元；产物中 config/controller/handler/interceptor/mapper/service 包的 .gitkeep 会被新文件替换）

### 任务组 2（动手 · 配置文件 2 个）
- **块 1 · application.yml**：逐块讲——`server.port`（端口）/ `spring.profiles.active`（多环境开关）/ `spring.main.allow-circular-references`（照抄案例①）/ druid 数据源（**占位符 `${sky.datasource.*}` 从哪来**——和单元 2 配置绑定联动，值在 application-dev.yml）/ mybatis（mapper-locations 指向 mapper/*.xml、type-aliases-package、驼峰映射开关）/ logging 分级 / `sky.jwt`（与 JwtProperties 字段对齐：admin-secret-key / admin-ttl / admin-token-name）
- **块 2 · application-dev.yml**：开发环境数据源实际值（driver / host / port / database / username / password）——**为什么拆两个文件**（环境隔离）
- 用户**亲手写出** 2 个文件 → diff 核对 → 费曼确认
- **📝 笔记触发点①**：Spring Boot 启动与配置体系（任务组 2 完成后写，含 SkyApplication 启动类回顾：@SpringBootApplication 组合注解、@EnableTransactionManagement、@Slf4j）

### 任务组 3（动手 · 登录链路三层 5 文件，🔴 本单元主线）
**分块推进（每块一个学习小阶段：讲 → 用户手写 → diff → 费曼确认 → 下一块；按依赖方向写：先底层后上层）：**
- **块 A · Mapper 层（2 文件）**：EmployeeMapper（`@Mapper` 注解——**本单元重点**：为什么不用 @MapperScan？两种方式区别与官方取舍；`@Select` 注解 SQL——单元 3 学的"绑定在 Mapper 层"落地）+ EmployeeMapper.xml（namespace 空壳、mapper-locations 怎么找到它）——**注解与 XML 双通道并存**，本单元 XML 是空壳（照抄案例②）
  - **📝 笔记触发点③**：MyBatis 数据库访问（半自动 ORM 落地）
- **块 B · Service 层（2 文件）**：EmployeeService 接口 + EmployeeServiceImpl——`@Service` / `@Autowired` 依赖注入（**对象不是 new 出来的**，容器给的）；登录业务四步：① getByUsername 查库 ② 判空 → 抛 AccountNotFoundException ③ 密码比对（**明文 + TODO md5，照抄案例③**）→ 抛 PasswordErrorException ④ 状态检查 → 抛 AccountLockedException——**单元 2 的异常体系第一次被抛出来**（"只描述不处理"，处理在哪？任务组 5 揭晓）
  - **📝 笔记触发点②**：三层架构与依赖注入（@Autowired、组件家族、调用链、为什么分层）
- **块 C · Controller 层（1 文件）**：EmployeeController——`@RestController` / `@RequestMapping("/admin/employee")` / `@PostMapping("/login")` / `@RequestBody`；调 Service；**JWT 签发在 Controller 而非 Service**（官方设计：claims 放 EMP_ID + JwtProperties 取密钥/有效期 + JwtUtil.createJWT——单元 2 的工具在此启用）；`EmployeeLoginVO.builder()` 链式组装（单元 3 @Builder 伏笔回收）；Result.success 包装返回；顺带 logout 接口（什么都不做，照抄）

### 任务组 4（动手 · 拦截器 + Web 配置中心 2 文件）
- **块 1 · JwtTokenAdminInterceptor**：HandlerInterceptor 接口 / preHandle 时机（请求进 Controller **之前**）；HandlerMethod 判断（只拦动态方法）；从请求头取 token（`request.getHeader(jwtProperties.getAdminTokenName())`）；JwtUtil.parseJWT 校验（单元 2 复用）；失败 → 401 + 不放行
- **块 2 · WebMvcConfiguration**：`@Configuration` 配置类 / `addInterceptors` 注册拦截器（`/admin/**` 全部拦截 **排除 `/admin/employee/login`**——**为什么必须排除：不排除 = 没登录就没法登录（死锁推演）**）；knife4j 文档（`@Bean Docket` + ApiInfo + 静态资源映射 doc.html/webjars）；`@Bean` 方法注册组件
- 用户**亲手写出** 2 个文件 → diff → 费曼确认

### 任务组 5（动手 · 全局异常 1 文件）
- GlobalExceptionHandler：`@RestControllerAdvice` + `@ExceptionHandler`——**单元 2 异常体系"谁来处理"的答案**（抛出的 BaseException 被这里一把接住，转成 Result.error 返回前端）；为什么不用 try-catch 到处包（异常冒泡 + 统一兜底的设计）
- 用户**亲手写出** 1 个文件 → diff → 费曼确认
- **📝 笔记触发点④**：拦截器与全局异常处理（守门员与兜底网，含 knife4j 接口文档配置小节）

### 任务组 6（验证）：见 `check.md` 逐条执行
- 开场抽查（单元 2+3 混合，已在任务组 1 前执行）→ 全量 diff 核对（11 文件）→ IDEA 编译 BUILD SUCCESS → 费曼检验（用户讲，老师提问，重点 4 项：完整旅程 / 三层架构与依赖注入 / 拦截器排除路径 / 全局异常接住异常）→ 审查练习（2 场景）→ 对照 learning-goals 逐条核对

### 任务组 7（收尾）
- 更新 `learning-specs/learning-changelog.md`（学会了什么/踩了什么坑/规格修订记录）+ 更新 roadmap 单元 4 状态（用户执行）
- 合并分支 unit-4-sky-server → main（用户执行）
- **教学最后提醒（老师口头）**：本单元完成后，后续还有补充学习单元（过渡单元：MyBatis 进阶 / Redis / AOP 等 vibecoding 高频知识点），届时另行规划——仅提醒，本单元不展开

## 产出清单（产物 `./sky-take-out/sky-server/`，本单元结束时应存在）
```
sky-server/src/main/java/com/sky/
├── SkyApplication.java                  （已有，diff 核对：仅空格差异属放行项）
├── controller/admin/EmployeeController.java
├── service/EmployeeService.java
├── service/impl/EmployeeServiceImpl.java
├── mapper/EmployeeMapper.java
├── interceptor/JwtTokenAdminInterceptor.java
├── handler/GlobalExceptionHandler.java
└── config/WebMvcConfiguration.java
sky-server/src/main/resources/
├── application.yml
├── application-dev.yml
└── mapper/EmployeeMapper.xml
```
合计 11 个文件（新建 10 + 核对 1）；config/controller/handler/interceptor/mapper/service 包原 .gitkeep 随新文件移除。

## 规格偏差备注（审查时确认是否同步修订 roadmap/knowledge-map）
1. **knowledge-map 偏差修正（用户拍板 ✅ 2026-08-16）**：knowledge-map.md:66 写 SkyApplication 为"@SpringBootApplication + @MapperScan"——**参照物实际无 @MapperScan**（Mapper 接口用 `@Mapper` 注解，见 EmployeeMapper.java:7）。已确认：修订 knowledge-map 为实际做法，任务组 3 块 A 顺带讲两种方式区别与官方取舍。
2. **roadmap 偏差（用户拍板，需同步 roadmap）**：roadmap 单元 4 任务组原为按包顺序（yml → config → interceptor → handler → mapper → service → controller）；本计划改为**故事线视角**（概念先行 → 配置 → 三层链路 → 拦截器 → 全局异常），且学习点"动态 SQL 入门"与实际不符（XML 为**空壳**，SQL 在 @Select 注解）——roadmap 单元 4 任务组与学习点需同步修订。
3. **教学决策（用户拍板 2026-08-16）**：① 8 文件全部精讲（本单元概念密度高，不用批量模式）；② 笔记 4 篇（编号按主题：①配置体系 ②三层架构 ③MyBatis ④拦截器异常，触发顺序为 ① → ③ → ② → ④）；③ 开场抽查单元 2+3 混合。
4. （实施中若发现新的规格偏差，追加到本节）
