# 单元 2 学习要求（learning-goals）

> 单元：sky-common 公共层　日期：2026-08-13
> 原则：写"达到什么能力"，不写实现细节

> **复现（diff 零差异）是验收标准；设计理解（为什么这样设计）是学习重点**，两者并列达标才通过。

## 一、理解（学完能讲出）

| # | 能力 | 验收描述 |
|---|---|---|
| G1 | 讲清公共层职责 | 能说出 sky-common 放的是"与业务无关、所有模块共用"的东西；能说出 8 个包各管什么；能解释为什么这些放 common 而不放 server/pojo |
| G2 | 讲清 Result 统一返回契约 | 能说出 code/msg/data 三字段的含义与取值约定（code=1 成功、0 失败）；能说出为什么全项目要统一一种返回格式（前端只认一种"包裹"）；能说出 success() / success(data) / error(msg) 三种静态工厂分别怎么填 |
| G3 | 讲清 BaseContext 为什么用 ThreadLocal | 能说出 ThreadLocal 的"线程隔离"含义；能用场景说明"普通静态变量在并发下会出什么问题"（请求数据串号）；能说出 set / get / remove 各自用途 |
| G4 | 讲清 JwtUtil 生成/解析流程 | 能画出 JWT 的三部分结构（header.payload.signature）；能说出生成步骤（装 claims → 选算法 → 设过期时间 → 签名打包）；能说出解析步骤（验签 → 取 claims）；能说出 HS256 对称密钥意味着什么 |
| G5 | 讲清 @ConfigurationProperties 配置绑定 | 能说出 yml 配置项到 Java 属性的映射规则（前缀 + 中划线转驼峰）；能说出 @Component 的作用（让 Spring 管理这个类，先知道"是开关"）；能说出配置绑定解决什么问题（配置与代码分离，改配置不改代码） |
| G6 | 讲清异常体系 | 能说出 BaseException 为什么继承 RuntimeException（不强制调用方捕获，交给全局异常处理器统一处理）；能说出业务异常类的写法模式（继承 + 构造方法转发） |
| G7 | 讲清枚举 | 能说出 OperationType 是什么、为什么枚举比常量更适合"固定的一组取值" |
| G8 | 讲清工具类各自出处 | 能说出 4 个工具类各对应哪个第三方库/技术（JwtUtil→jjwt、HttpClientUtil→Apache HttpClient、AliOssUtil→阿里云 OSS、WeChatPayUtil→微信支付 SDK） |
| G9 | 讲清设计动机 | 随机指一个文件，能说出参照物"为什么这样设计"（如常量为什么收成类、字段名为什么叫 USERNAME 而非 USER_NAME、Result 为什么用静态工厂、异常为什么继承 RuntimeException）——设计理解维度，与复现并列 |
| G10 | 讲清模块划分设计 | 能说出 8 个包按职责分哪三类（契约 / 配置转换 / 状态能力）；能说出 context 与 utils 的划分标准（有状态 vs 无状态）；能解释为什么这些内容放 common 而非 server/pojo——宏观设计维度 |

## 二、技能（学完能做）

| # | 能力 | 验收描述 |
|---|---|---|
| S1 | 能照参照物写出全部 29 个文件 | 产物与参照物逐文件 diff 零差异；写的过程中能说出每个文件在干什么 |
| S2 | 能新增一个业务异常类 | 不看参照物，按模式写一个继承 BaseException 的新异常类，写法正确 |
| S3 | 能说出每个包/文件的复用场景 | 随机指一个文件，能说出"它在项目里被谁用、什么时候用"（如 MessageConstant 被登录业务用、Result 被所有 controller 用） |
| S4 | 能给配置类加字段 | 给 JwtProperties 加一个新属性，能写出对应的 yml 配置项（驼峰/中划线转换正确） |

## 三、产出物
见 `plan.md` 产出清单：`./sky-take-out/sky-common/` 下 8 包 29 个 .java 文件。

## 四、明确不要求（本单元边界）
- 不要求深入理解 Spring 容器/自动配置原理（@Component、@Autowired 先"会标会用"，原理单元 4 讲）
- 不要求理解微信支付 v3 签名/退款流程细节、Apache HttpClient 内部、阿里云 OSS SDK 细节（均为了解级，固定样板照抄）
- 不要求写 application.yml 配置文件（单元 4；本单元只演示配置绑定怎么对应）
- 不要求理解 ThreadLocal 底层实现原理——会"为什么用 + 怎么用"即可
- 不要求背 JacksonObjectMapper 的 API 清单（会抄、会改日期格式即可）
- 不要求从零设计公共层（能对照参照物写 + 讲出每个文件为什么长这样即可）
