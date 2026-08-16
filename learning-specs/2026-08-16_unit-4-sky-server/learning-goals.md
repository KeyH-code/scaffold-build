# 单元 4 学习要求（learning-goals）

> 单元：sky-server 员工登录链路　日期：2026-08-16
> 原则：写"达到什么能力"，不写实现细节

> **复现（diff 零差异）是验收标准；链路理解（一次登录请求的旅程）是学习重点**，两者并列达标才通过。

## 一、理解（学完能讲出）

| # | 能力 | 验收描述 |
|---|---|---|
| G1 | 讲清 Spring Boot 启动与配置体系（🔴） | 能说出 @SpringBootApplication 是什么（组合注解）；能说出"为什么标了注解的类能被 Spring 自动找到"（组件扫描）；能讲清 yml 为什么拆成 application.yml + application-dev.yml 两个文件（多环境开关 profiles.active）；能说出 `${sky.datasource.host}` 这种占位符的值从哪来（与单元 2 配置绑定的联动关系） |
| G2 | 讲清三层架构与依赖注入（🔴） | 能说出 Controller / Service / Mapper 三层各干什么、请求怎么一层层往下走；能说出 `@Autowired` 注入的对象**是谁创建的**（不是 new，是容器）；能说出为什么接口与实现要分开（EmployeeService + EmployeeServiceImpl） |
| G3 | 讲清登录链路全流程（🔴） | 不看任何文档，画出一次登录请求的完整旅程（时序）：入口 → 拦截器 → Controller → Service → Mapper → MySQL → 三种失败分支 → 或成功 → JWT 签发 → VO 组装 → Result 返回；说清每站做了什么 |
| G4 | 讲清 MyBatis 双通道 | 能说出 SQL 的两种写法（@Select 注解 / XML）各是什么、XML 的 namespace 干什么用、mapper-locations 怎么找到 XML；能说出驼峰映射开关（map-underscore-to-camel-case）解决什么问题；能说出 `@Mapper` 注解的作用、以及它与 @MapperScan 的区别 |
| G5 | 讲清拦截器机制（🔴） | 能说出请求在什么时候经过拦截器（进 Controller 前）；能说出为什么登录路径必须排除在拦截器外（不排除会怎样——推演"死锁"）；能说出校验失败返回 401 的含义 |
| G6 | 讲清全局异常处理（🔴） | 能说出单元 2 抛出的业务异常（AccountNotFoundException 等）**被谁接住、怎么变成 JSON 返回给前端**；能说出为什么不用 try-catch 到处包（统一兜底的设计）；能说出 @RestControllerAdvice 与 @ExceptionHandler 的分工 |
| G7 | 讲清 knife4j 文档 | 能说出单元 3 的 @ApiModel 注解在这里怎么"生效"（Docket 扫描 com.sky.controller 包）；能说出 doc.html 是什么、静态资源映射解决什么问题 |
| G8 | 讲清照抄纪律（沿用单元 3） | 能说出为什么"该有的没有"也要照抄——明文密码 + TODO 注释、空壳 XML、allow-circular-references——验收 = diff 零差异，不能"帮忙修正"、不能自作聪明补实现 |
| G9 | 讲清设计动机 | 能随机指一个文件，说出参照物"为什么这样设计"：JWT 为什么在 Controller 签而不是 Service？登录接口为什么和查询走不同的包（controller/admin）？三处照抄案例的设计意图——设计理解维度，与复现并列 |

## 二、技能（学完能做）

| # | 能力 | 验收描述 |
|---|---|---|
| S1 | 能写出全部 11 个文件 | 产物与参照物逐文件 diff 内容一致；写的过程中能说出每个文件在链路中扮演的角色 |
| S2 | 能画出登录时序图 | 不看文档画出完整旅程，含三种异常分支与 401 分支，标注每步的返回内容 |
| S3 | 能按模式扩展新接口 | 不看参照物，为假想业务（如"按 id 查询员工"）说出：需要动哪些文件（Controller 加方法 / Service 接口加方法 / Impl 加实现 / Mapper 加查询），各文件加什么、调用链怎么连 |
| S4 | 能讲清依赖方向 | 随手指出任一层依赖谁（Controller → Service → Mapper → MySQL），能说出依赖方向搞反会怎样（编译不过/职责混乱） |

## 三、产出物
见 `plan.md` 产出清单：`./sky-take-out/sky-server/` 下 8 个 .java + 3 个 resources 共 11 文件（SkyApplication.java 为 diff 核对）。

## 四、明确不要求（本单元边界）
- 不要求理解 Spring 容器底层原理（Bean 生命周期、循环依赖机制、自动配置源码）——知道"容器创建对象、注解触发机制"即可，功能开发阶段再深入
- 不要求理解 Spring MVC 底层（DispatcherServlet、HandlerMapping 内部机制）——知道"请求先进拦截器再进 Controller"即可
- 不要求动态 SQL / 复杂 XML 写法（XML 是空壳，功能开发阶段写 SQL 时再学）
- 不要求事务机制深入（@EnableTransactionManagement 先装上，功能开发阶段用了再讲）
- 不要求启动服务、不连接数据库（单元 5 建库后统一端到端跑通；本单元"编译通过"即验证）
- 不要求 knife4j / Swagger 原理（配置跟着写，单元 5 打开文档页看效果）
- 不要求编写单元测试
