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

## 2026-08-16 — 单元 3：sky-pojo 数据层
**做了什么**
- 任务组 1-9 全部完成：概念先行（三者职责）→ 员工域 6 文件 → 用户+分类域 7 文件 → 批量域三阶段（29 文件：业务域速览→复制→抽查）→ 重点类教学（7 文件：组装VO/关联表/常量归属/报表VO）→ 验证（diff+编译+费曼+审查练习）→ 收尾
- **教学计划中途调整（用户拍板）**：任务组 4-6 合并为"批量域速览+复制+抽查"，7 个高价值类（DishVO/DishFlavor/SetmealDish/Orders/OrdersSubmitDTO/OrderSubmitVO/OrderReportVO）走完整教学模式
- 产出 `sky-pojo/src/main/java/com/sky/` dto(21)/entity(11)/vo(17) 共 **49 文件**，与参照物逐文件 diff 实质内容一致（必改清零；差异均为放行项：空行/注释措辞/import 顺序/注解顺序）；IDEA 编译 BUILD SUCCESS
- Obsidian 笔记沉淀 2 篇：`07. 三层数据模型：DTO、Entity 与 VO 的职责分离` / `08. POJO 类设计模式：配方、字段与关联`

**学会了什么**
- 三者职责（输入/存储/展示三形态；变更隔离、最小暴露、职责单一）；登录双类（输入 2 字段 vs 输出 4 字段，password 只进不出）
- Lombok 体系（@Data/Builder 与构造器三角关系：@AllArgs 使默认无参消失、框架需无参 → 三件套成套）；编译期（Lombok）vs 运行期（Spring）两个世界，数据类不被容器管理
- ORM 映射（表=类、列=字段；bigint→Long、varchar→String、int→Integer、datetime→LocalDateTime；下划线转驼峰；包装类型 vs 基本类型的判断 = 是否可能无值）；**MyBatis 绑定在 Mapper 层**（SQL 写表名 + yml 开关），Entity 零绑定注解（半自动 ORM）
- VO 形态演化：简单展示 → 组装型（初始值 `new ArrayList<>()` 防 NPE）→ 继承型（耦合代价、@Builder 失效）→ 报表型（逗号分隔字符串，按下标配对）
- 字段设计方法论：**字段跟着动作走**（动作+原因/只需定位/全量输入）；定位字段（内部主键 vs 业务编号，跟随"操作者手里有什么"）；**展示形态决定字段**；状态常量归属（业务专属放自己类 / 跨模块通用放 common）
- 关联表设计：子表（1:N 从属）vs 中间表（N:N，外键各一列）；逻辑外键（无物理约束，SQL join）；冗余字段 = 一致性成本换查询速度；@JsonFormat 全局/局部（默认全局，特殊精度局部覆盖）

**踩坑记录**
- **参照物再次被误改**（EmployeeLoginVO description 多出 "hd"）：单元 2 已踩过一次（JwtClaimsConstant），本次**重复踩坑**——教训：参照物只读，改产物前先核对路径（改文件时认准 `sky-take-out/sky-pojo` vs `../sky-take-out/backend/...`）
- 单元 1 遗留：sky-pojo 包结构缺 `com/sky` 层（产物为 `java/dto` 而非 `java/com/sky/dto`），IDE 自动生成 `package dto;` —— 目录层级必须等于包名层级
- 漏类注释（AddressBook/DishFlavor 的 Javadoc）；漏参照物自带的"看似多余的 import"（User 的 LocalDate、OrderPaymentVO 的 LocalDateTime）
- 实体字段写成基本类型 `long`（应为 `Long`——可空语义）
- 注释类型写错：Javadoc `/** */` 写成块注释 `/* */`
- 审查练习暴露：找错要**分层扫描**（先字段层→再注解层→再 import 层）；DTO 不需要 @Builder（Jackson 反序列化创建）

**规格修订确认（已同步各文档）**
- roadmap 单元 3 任务组改为：概念先行 + 业务域 + 批量域 + 重点类（用户拍板，已同步）
- 教学节奏调整：批量域速览+复制+抽查（plan 偏差备注第 2 条）
- 笔记通用化：mission 笔记书写标准新增**第 7 条通用化原则** + 定位补充（通用知识为主体、项目仅举例）；08 重写 + 01-07 共 9 处修改（去复刻语境，子 agent 检查）
- 费曼题设计原则：mission 教学协作约定新增；check 费曼第 4 题通用化（去"参照物"表述）

**下阶段调整**
- 下一单元：单元 4（sky-server 员工登录链路，8 java + 3 resources），建议另开新会话按三件套流程推进
- 待处理（沿用）：PATH 上损坏的 `javapath\java.exe`；Maven 面板"两个 sky-take-out"重复加载

## 2026-08-18 — 单元 4：sky-server 员工登录链路
**做了什么**
- 任务组 1-7 全部完成：概念先行（八站旅程）→ 配置（application.yml + application-dev.yml）→ 登录链路三层（Mapper+XML → Service+Impl → Controller）→ 拦截器+Web配置中心 → 全局异常处理器 → 验证（前置抽查+diff+编译+费曼4项+模式复用+审查练习）→ 收尾
- 产出 `sky-server/src/main/` java 7 文件（config/controller/service/mapper/interceptor/handler 各包）+ resources 3 文件（application.yml/application-dev.yml/EmployeeMapper.xml 空壳），与参照物逐文件 diff 实质内容一致（SkyApplication.java 仅空格差异放行）；IDEA 编译 BUILD SUCCESS
- Obsidian 笔记沉淀 4 篇：`09. Spring Boot 配置体系：双环境配置与占位符联动` / `10. MyBatis 半自动 ORM：注解 SQL 与 XML 双通道` / `11. 三层架构与依赖注入：Bean 生命周期与调用链` / `12. 拦截器与全局异常处理器：请求过滤与统一异常捕获`

**学会了什么**
- **Spring 容器核心**：Bean（Spring 创建并管理生命周期的对象）vs 普通对象（如 DTO/VO）；`@Component`/`@Service`/`@Controller`/`@Mapper` 四注解标记 Bean；`@SpringBootApplication` 组合注解触发组件扫描；依赖注入（`@Autowired` 自动注入，不手动 new；手动 new 脱离容器管理 → 依赖链断裂 → NullPointerException）
- **三层架构调用链**：Controller（前后端联系、签发 JWT、组装 Result）→ Service（业务逻辑、抛异常）→ Mapper（数据库访问）；单向依赖、接口与实现分离（可替换性 + Spring AOP 需要接口）
- **配置体系**：双环境（`application.yml` 通用 + `application-dev.yml` 开发环境，`spring.profiles.active` 激活）；占位符 `${sky.datasource.host}` 从 application-dev.yml 读值；与单元 2 的 `@ConfigurationProperties` 联动（配置类在 common、配置值在 server）
- **MyBatis 双通道**：`@Select` 注解 SQL（直接写在接口方法上）+ XML（EmployeeMapper.xml 空壳，只有 namespace）；`@Mapper` 接口标注（不用 `@MapperScan`）；MyBatis 动态代理生成实现类；`map-underscore-to-camel-case: true` 驼峰映射；`#{username}` 预编译（防 SQL 注入）vs `${username}` 字符串拼接（有注入风险）
- **拦截器机制**：请求 → 拦截器 `preHandle` → Controller；`JwtUtil.parseJWT(...)` 验证 token（签名 + 过期 + 格式，验不过抛异常 → 401）；`excludePathPatterns("/admin/employee/login")` 排除登录路径（否则鸡生蛋死锁）；WebMvcConfiguration 注册拦截器（拦截器有 `@Component` 但需配置路径规则）
- **全局异常处理**：`@RestControllerAdvice` + `@ExceptionHandler(BaseException.class)` 捕获 Service 抛出的异常；Controller 不需要 try-catch（统一处理、避免代码污染）
- **knife4j 生效**：单元 3 的 `@ApiModel` / `@ApiModelProperty` + 单元 4 的 WebMvcConfiguration（knife4j 配置）→ doc.html 接口文档
- **照抄案例**：明文密码比对 + `TODO 后期md5加密`（EmployeeServiceImpl.java:42）、`allow-circular-references: true`（容器启动开关）、EmployeeMapper.xml 空壳（只有 namespace）

**踩坑记录**
- 无重大踩坑（单元 2-3 踩坑训练有效，零差异纪律已内化）

**规格修订确认（已同步各文档）**
- knowledge-map.md:66 修正：SkyApplication 描述从"@SpringBootApplication + @MapperScan" → "@SpringBootApplication（不用 @MapperScan，Mapper 接口直接标 @Mapper）"
- roadmap.md:42 修正：单元 4 学习点从"动态 SQL 入门" → "注解 + XML 双通道并存（XML 空壳、注解 SQL、驼峰映射）"；任务组顺序调整（文件视角 → 故事线视角）

**下阶段调整**
- 下一单元：单元 5（启动验证 + 接口联调），建议另开新会话按三件套流程推进
- 待处理（沿用）：PATH 上损坏的 `javapath\java.exe`；Maven 面板"两个 sky-take-out"重复加载

---

## 2026-08-19 — 单元 5：端到端验收完成

### 做了什么
- ✅ **任务组 1-6 全部完成**：全量 diff（1 处必改项修复）→ 环境准备（数据库+配置+端口检查）→ 启动服务+接口联调（curl 演示 3 场景）→ 概念铺垫（4 个宏观知识点）→ 费曼检验（4 题画图讲流程）→ 收尾（Obsidian 笔记+changelog+git 提交）
- **产出验证**：93 个源文件与官方脚手架 diff 内容一致（必改清零）；服务启动成功（Tomcat 8080）；登录接口联调通过（成功/用户名错误/密码错误三场景）
- **宏观理解达成**：掌握 Spring Boot 启动四阶段、请求旅程八站图、三层架构协作机制、token 完整生命周期、配置体系联动
- **Obsidian 笔记沉淀**：`13. Spring Boot 启动与请求处理全景.md`（涵盖五大核心知识点：启动流程、请求旅程、三层架构、token 生命周期、配置体系）

### 学会了什么
- **Spring Boot 启动全景**：四阶段（创建容器 → 扫描 Bean → 加载配置 → 启动 Tomcat）；`@SpringBootApplication` 三合一注解（`@SpringBootConfiguration` + `@EnableAutoConfiguration` + `@ComponentScan`）；Bean 创建时序（扫描完后统一创建，按依赖顺序从底层到顶层）
- **请求完整旅程（八站图）**：浏览器 → Tomcat → DispatcherServlet → 拦截器 preHandle → Controller → Service → Mapper → 数据库，再原路返回；DispatcherServlet 先找 Controller，再执行拦截器；JSON → DTO 转换由 Spring MVC + Jackson 在 Controller 执行前完成
- **三层架构协作机制**：Controller（处理 HTTP、生成 token）→ Service（业务逻辑、抛异常）→ Mapper（执行 SQL）；依赖注入串联三层（Spring 启动时按依赖顺序创建 Bean 并注入）；手动 new 对象脱离容器 → 依赖链断裂 → NullPointerException；Spring 按类型匹配（多个实现时用 `@Qualifier` 或 `@Primary`）
- **token 完整生命周期**：生成（Controller 层，`JwtUtil.createJWT()`）→ 返回（封装进 VO）→ 存储（前端 localStorage）→ 携带（请求头）→ 验证（拦截器，只验证签名+过期，不查数据库）→ 使用（Service 层 `BaseContext.getCurrentId()` 从 ThreadLocal 读取用户 ID）
- **配置体系联动**：`application.yml`（通用配置+占位符）+ `application-dev.yml`（开发环境配置，覆盖通用配置）；`@ConfigurationProperties` 绑定配置到 Java 对象（配置类在 common，配置值在 server）；占位符 `${sky.datasource.host}` 从 application-dev.yml 读值替换
- **关键概念**：IoC 容器（对象仓库）、Bean（被 Spring 管理的对象）、依赖注入（`@Autowired` 自动注入）、ThreadLocal（线程本地变量，拦截器存用户 ID、Controller/Service 读取）、登录路径排除（否则"要 token 才能获取 token"死循环）

### 踩坑记录
1. **EmployeeServiceImpl.java 缺少 import**：虽然代码中暂未用到 DigestUtils，但官方脚手架有就必须补（diff 验证不能只靠"编译通过"）
2. **application-dev.yml 密码配置不一致**：配置写 root，实际本地 MySQL 密码是 123456，导致启动时数据库连接失败
3. **employee 表密码需改为明文**：数据库中 admin 密码原为 MD5 加密值，代码中 TODO 注释"后期需要进行 md5 加密"，当前是明文比对，需 `UPDATE employee SET password='123456'`

### 规格修订确认（已同步各文档）
- **任务组顺序调整**：原计划（diff → 环境 → 概念 → 启动联调）→ 实际执行（diff → 环境 → 启动联调 → 概念铺垫）——先跑通实践、看到 curl 演示结果，再讲宏观架构，避免理解断层
- **新增 handoff.md**：交接文档模板，明确当前进度、下一步行动、用户偏好、避免重复错误（已同步 CLAUDE.md "新会话 prompt 交接"）
- **接口联调方式固化**：用户明确反馈"不需要在 knife4j 页面手动点"，改为 AI 用 curl 演示、用户观察输出
- **环境准备 AI 全包**：任务组 2 原计划"用户操作+老师指导"，实际 AI 全包（数据库检查/配置修正/端口检查/预启动验证）——效率提升，用户无需手动操作

### 下阶段调整
- **单元 5 验收完成**：五单元学习目标（Maven 骨架 → 公共层 → 数据层 → 登录链路 → 端到端验收）全部达成
- **建议整体复盘**：另开新会话，对照 roadmap 检查五单元达成度（知识点覆盖、验收标准、宏观理解）
- **考虑过渡单元**：评估是否需要补充 MyBatis 进阶（动态 SQL、一对多查询）、Redis 缓存、Spring AOP（切面编程）等知识点，再进入功能开发阶段
- **git 提交**：待用户执行（下一步给出具体命令）
