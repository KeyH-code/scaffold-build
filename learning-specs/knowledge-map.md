# 知识地图（knowledge-map）

> 生成日期：2026-08-12　依据：官方脚手架 93 个源文件反推 + 课程资源索引
> 难度标记：🟢 简单（看一眼就懂）🟡 中（需讲解+动手）🔴 难（核心概念，需反复讲/写）

## 一、技术体系总览

```
Maven 多模块工程（父 pom 管版本）🔴
  └── sky-common（公共层）🟡
        ├── constant 常量 / result 统一返回 / enumeration 枚举 🟢
        ├── exception 异常体系 🟢
        ├── context ThreadLocal 用户上下文 🔴
        ├── json Jackson 序列化配置 🟡
        ├── properties 配置绑定 🟡
        └── utils 工具类（JwtUtil🔴 / HttpClientUtil🟡 / AliOssUtil🟢 / WeChatPayUtil🟢）
  └── sky-pojo（数据层）🟡
        ├── entity 实体（表结构映射，Lombok）
        ├── dto 请求参数对象
        ├── vo 响应对象
        └── 核心理解：三者的职责区别 🔴
  └── sky-server（服务层）
        ├── SkyApplication 启动类 🔴（Spring Boot 启动原理）
        ├── controller REST 接口 🟡（HTTP 基础 🔴 前置）
        ├── service 业务逻辑 🟡（三层架构）
        ├── mapper MyBatis 数据库访问 🔴
        ├── interceptor JWT 登录校验拦截器 🔴
        ├── handler 全局异常处理 🟡
        ├── config 配置类（knife4j + 拦截器注册）🟡
        └── resources 配置文件（yml）🟡
```

## 二、逐项知识点（每项含：前置依赖 / 难点 / 资源索引）

### 基础设施（贯穿全程）
| 知识点 | 难度 | 前置依赖 | 说明与难点 |
|---|---|---|---|
| Maven 多模块工程 | 🔴 | 无 | 父 pom 用 `dependencyManagement` 统一版本，子模块只写依赖不写版本；模块依赖方向：common 与 pojo **各自独立**（pojo 不依赖 common），server 依赖两者 |
| HTTP 请求/响应 | 🔴 | 无 | URL、Method(GET/POST)、请求头、状态码；登录接口是全链路第一课 |
| MySQL 数据库 | 🟡 | SQL 基础 | 建库 `sky_take_out`、导入 `sky.sql`；无需深入，够跑通即可 |
| git 分支协作 | 🟡 | git 基础 | 每单元独立分支 + 合并；用户亲自操作 |

### sky-common（公共层，29 文件）
| 包 | 文件数 | 知识点 | 难度 |
|---|---|---|---|
| constant | 5 | `static final` 常量类（JwtClaimsConstant / MessageConstant / StatusConstant 等） | 🟢 |
| result | 2 | 统一返回封装 `Result`（code/msg/data）+ 分页 `PageResult`；前端契约 | 🟢 |
| enumeration | 1 | 枚举 `OperationType`（INSERT/UPDATE，配合自动填充） | 🟢 |
| exception | 12 | 异常体系：`BaseException` 基类 + 11 个业务异常（继承自 RuntimeException） | 🟢 |
| context | 1 | `BaseContext`：ThreadLocal 存当前登录用户 id | 🔴 ThreadLocal 线程隔离概念 |
| json | 1 | `JacksonObjectMapper`：Long→String 序列化（防前端精度丢失） | 🟡 |
| properties | 3 | `@ConfigurationProperties` 配置绑定（JwtProperties / AliOssProperties / WeChatProperties） | 🟡 |
| utils | 4 | `JwtUtil`（生成/解析 JWT，登录链路核心）🔴；`HttpClientUtil`（发 HTTP 请求）🟡；`AliOssUtil` / `WeChatPayUtil` 了解即可 🟢 | 见各文件 |

### sky-pojo（数据层，49 文件）
| 包 | 文件数 | 知识点 | 难度 |
|---|---|---|---|
| entity | 11 | 实体类 = 数据库表结构映射（Employee/Category/Dish/Orders/User…），Lombok `@Data` | 🟡 |
| dto | 21 | 请求参数对象（EmployeeLoginDTO 等）；为什么不全用 entity 收参 | 🟡 |
| vo | 17 | 响应对象（EmployeeLoginVO 等）；DTO/VO/Entity 三者职责 🔴 | 🟡 |

### sky-server（服务层，8 java + 3 resources）
| 位置 | 知识点 | 难度 |
|---|---|---|
| SkyApplication | `@SpringBootApplication`（= 组合注解）+ `@MapperScan`；Spring Boot 启动流程 | 🔴 |
| controller/admin/EmployeeController | `@RestController` / `@RequestMapping`；REST 接口写法；`Result` 包装返回 | 🟡 |
| service/impl | 接口 + 实现分层；登录业务：查库 → 比对密码（MD5）→ 签发 JWT → 返回 VO | 🔴 |
| mapper | MyBatis 接口（`@Select` 注解）+ `EmployeeMapper.xml`（namespace、resultType、动态 SQL 入门） | 🔴 |
| interceptor | 拦截器机制：`preHandle` 校验 token，`HandlerInterceptor` + 注册到 `WebMvcConfiguration` | 🔴 |
| handler | `@RestControllerAdvice` 全局异常处理：捕获 BaseException 统一返回友好错误 | 🟡 |
| config | knife4j 接口文档配置 + 拦截器注册；`@Configuration` | 🟡 |
| resources/yml | application.yml（端口/数据源/MyBatis/JWT 配置）+ application-dev.yml（dev 环境数据源）；`${sky.datasource.*}` 占位符 | 🟡 |

## 三、了解级知识点（脚手架中有依赖/工具类，但登录业务未用）
Redis（starter 已引入，未使用）、阿里云 OSS、POI（Excel）、WebSocket、微信支付 SDK —— **功能开发阶段再学**，此处只需知道"工程里为什么有这些依赖"。

## 四、资源索引（学习时对照查阅）
| 资源 | 路径 |
|---|---|
| 课程根目录（讲义/PPT/资料） | `E:\ACourseLearning\sky-take-out\sky-take-out\` |
| 完整版代码（功能开发阶段审查基准） | `资料/day12/项目完整版代码/sky-take-out/` |
| 接口文档（管理端/用户端） | `资料/day01/项目接口/*.json`（本工作区参照物仓库 `interface/` 也有副本） |
| 数据库设计文档 | `资料/day01/数据库/数据库设计文档.md` |
| 数据库脚本 | 参照物仓库 `database/sky.sql` |
| 官方脚手架（只读参照物） | `E:\ACourseLearning\sky-take-out\sky-take-out-learn\sky-take-out\backend\sky-take-out\` |

## 五、学习顺序建议（按单元）
单元 1 先理解"Maven 多模块为什么这么拆" → 单元 2 common 公共层（工具先行）→ 单元 3 pojo 数据层（区分 DTO/Entity/VO）→ 单元 4 server 登录链路（把前面所有零件串起来的完整故事）→ 单元 5 验收。
