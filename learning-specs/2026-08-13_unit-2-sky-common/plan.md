# 单元 2 学习计划（plan）

> 单元：sky-common 公共层　日期：2026-08-13　分支：unit-2-sky-common
> 命名约定（沿用单元 1，避免两个 sky-take-out 目录混淆）：
> - **参照物** = 官方脚手架工程（只读基准，diff 对照对象）：`../sky-take-out/backend/sky-take-out/sky-common/`
> - **产物** = 本工作区复刻出的工程（要建的东西）：`./sky-take-out/sky-common/`

## 总目标
产出与官方**逐文件 diff 内容一致**的 sky-common 公共层全部 **29 个文件（8 包）**；理解"公共层放什么、为什么放这里"，啃下 4 个核心难点：**Result 统一返回契约 → @ConfigurationProperties 配置绑定 → BaseContext 的 ThreadLocal → JwtUtil 生成/解析**。
**复现与设计并重**：diff 零差异（复现）是本单元验收标准；学习重点在于**设计理解**——每个文件为什么这样设计（常量是契约、字段名/值不能随手改、异常为何继承 RuntimeException 等），以及**模块划分设计**（8 包按职责分类：契约 / 配置转换 / 状态能力；有状态与无状态分离；为什么放公共层），能讲出设计动机才算真正学会。
（验收细则见 check.md 核对 1：Java 文件 diff 放行/必改规则。）

## 实施节奏约定（用户 · 老师共同遵守）
- 费曼检验全对且无疑问句 → 老师进入下一知识点；回答有错/有疑问 → 老师解答后**必须等用户明确确认"明白了/继续"**，禁止答完疑问直接跳步；
- **任务组内大块内容按块细化为学习小阶段**（如下方"块 N"标注）；用户可随时要求进一步细化粒度；
- **知识沉淀（Obsidian 笔记）**：本单元按**知识域分 3 次**记录进 `E:\deeplearning\dl\Java`（触发点已在任务组内标注 📝），每次记录经用户审阅通过后再继续下一步；笔记书写标准见全局 learning-mission.md；
- **开场抽查**：开始前老师先随机抽查单元 1 的 2-3 个核心知识点（不提示不剧透），不过先补学；
- **课程总验收（可选）**：全部课程结束后，用户可选择反转课堂式总验收；
- 与全局 learning-mission.md「教学协作约定」一致，本单元实施全程适用。

## 任务组清单（概念 → 动手 → 验证，按序实施，每任务组结束等用户确认）

### 任务组 1（概念铺垫）：公共层是什么
- sky-common 的定位：**所有模块共用的"零件库"**（类比：全家共用的工具箱，谁需要谁来拿，谁都不专属）
- 8 个包各管什么（一句话职责）：constant 常量 / result 统一返回 / enumeration 枚举 / exception 异常 / properties 配置绑定 / json 序列化 / context 线程上下文 / utils 工具类
- 为什么这些"与业务无关、各模块都要用"的东西**单独放一层**（对比：业务相关的放 server，数据相关的放 pojo）
- 读参照物目录结构，数清 29 个文件各自归属哪个包

### 任务组 2（动手·简单包①）：constant(5) + result(2)
**分块推进（每块一个学习小阶段，讲一块 → 费曼确认 → 下一块）：**
- **块 1 · constant 常量类**：常量类是什么（`public static final`，类比：给写死的数字/字符串起个名字，全项目统一引用、改一处全改）
  - MessageConstant（提示消息，**登录链路大量使用**，重点）→ JwtClaimsConstant（JWT 键名）→ StatusConstant（启用/禁用状态）→ PasswordConstant（默认密码/加密方式）→ AutoFillConstant（公共字段名，功能阶段用）
- **块 2 · result：Result\<T\> 统一返回契约（难点①）**
  - 泛型 \<T\>（类比：能装任何东西的盒子）+ 静态工厂方法（类比：预置几种"标准装法"的配方）
  - code / msg / data 三字段，**契约约定**：code=1 成功、code=0 失败
  - `success()` / `success(data)` / `error(msg)` 三种静态方法各自怎么填
  - `implements Serializable`（为什么要实现序列化接口：对象要能被传输/存储）
  - PageResult（分页返回：总记录数 + 数据列表）
- 用户**亲手写出** 8 个文件 → diff 核对修正
- **📝 笔记触发点①**：常量/契约体系知识域（constant + result + enumeration + exception）完成

### 任务组 3（动手·简单包②）：enumeration(1) + exception(12)
- enumeration：枚举 OperationType（INSERT/UPDATE，配合公共字段自动填充用；为什么用枚举不用常量：取值被限定、自带文档）
- exception：**讲一写多**
  - 讲透 **BaseException**（继承 RuntimeException——为什么必须继承"运行时异常"：调用方不用强制 try-catch，交给全局异常处理器统一接住）
  - 讲透 1 个典型：**LoginFailedException**（登录失败专用）
  - 其余 10 个业务异常：用户**照模式自己写**（识别模式：类名 + 构造方法转发）→ diff 核对
- **📝 笔记触发点①继续**：异常体系并入同篇（或独立小节，看篇幅）

### 任务组 4（动手·中难包）：properties(3) + json(1)
- properties：**@ConfigurationProperties 配置绑定（难点②）**
  - yml 里 `sky.jwt.admin-secret-key` 是怎么**自动变成** `JwtProperties.adminSecretKey` 的（前缀 `sky.jwt` + 中划线转驼峰）
  - @Component（让 Spring 认识并管理这个类，先"会标"，原理单元 4 讲）+ @Data（Lombok 生成 getter/setter）
  - **JwtProperties**（重点，登录链路用）→ AliOssProperties → WeChatProperties（同构复制）
- json：JacksonObjectMapper
  - 它解决什么问题：Java 时间类型（LocalDateTime）默认被序列化成数字/数组，前端看不懂 → 自定义格式 `yyyy-MM-dd HH:mm`
  - 实现方式：继承 ObjectMapper + SimpleModule 注册序列化器（了解 API 用法，会抄即可）
- 用户**亲手写出** 4 个文件 → diff 核对
- **📝 笔记触发点②**：配置绑定 + 序列化知识域完成

### 任务组 5（动手·难点包）：context(1) + utils(4)
- context：**BaseContext + ThreadLocal（难点③，概念最抽象）**
  - ThreadLocal：线程隔离（类比：每个人自己的小抽屉，别人够不着）
  - **为什么不用普通静态变量**：多线程并发下 A 请求存的数据会被 B 请求覆盖（串号）——这是本单元验收核心
  - 三个方法 set / get / remove（存/取/清理），remove 为什么重要（用完清空，防内存泄漏）
  - 本单元先"认识它"；它被谁 set（拦截器）、谁 get（service）在单元 4 讲
- utils：**JwtUtil（难点④，流程最长）** + 三个了解级工具
  - **JwtUtil**：JWT 是什么（类比：一封带防伪章的加密信）→ header.payload.signature 三部分 → **生成流程**（claims 装数据 → 选 HS256 → 设过期时间 → 签名打包）→ **解析流程**（验签 → 取出 claims）→ 为什么用对称密钥 HS256（签发和验证共用同一把钥匙）
  - **AliOssUtil**（68 行）：阿里云 OSS 文件上传封装——**用途 + 主干 + 照抄**（SDK 官方示例，固定样板）
  - **HttpClientUtil**（179 行）：Apache HttpClient 的 GET/POST 封装——**用途 + 主干 + 照抄**（固定样板，HTTP 细节单元 4 再讲）
  - **WeChatPayUtil**（235 行）：微信支付 API v3 调用（下单/查单/退款）——**用途 + 主干 + 照抄**（固定样板，无真实商户号跑不了，纯了解级）
- 用户**亲手写出** 5 个文件 → diff 核对
- **📝 笔记触发点③**：ThreadLocal + JWT + 工具层知识域完成

### 任务组 6（验证）：见 `check.md` 逐条执行
- 全量 diff 核对（29 文件）
- IDEA 编译 sky-common 模块（compile）BUILD SUCCESS
- 费曼检验（用户讲，老师提问，重点 4 项：ThreadLocal、JwtUtil、Result 契约、配置绑定）
- 审查练习（老师给 AI 风格错误代码，用户独立判断）

## 产出清单（产物 `./sky-take-out/sky-common/`，本单元结束时应存在）
```
sky-common/src/main/java/com/sky/
├── constant/   AutoFillConstant / JwtClaimsConstant / MessageConstant / PasswordConstant / StatusConstant（5）
├── context/    BaseContext（1）
├── enumeration/OperationType（1）
├── exception/  BaseException + 11 个业务异常（12）
├── json/       JacksonObjectMapper（1）
├── properties/ AliOssProperties / JwtProperties / WeChatProperties（3）
├── result/     PageResult / Result（2）
└── utils/      AliOssUtil / HttpClientUtil / JwtUtil / WeChatPayUtil（4）
```
合计 29 个文件（任务组 2：7 个 → 任务组 3：13 个 → 任务组 4：4 个 → 任务组 5：5 个）

## 规格偏差备注（审查时确认是否同步修订 roadmap/knowledge-map）
1. **knowledge-map 偏差**：知识地图描述 json 包为"Long→String 序列化（防前端精度丢失）"，**实际参照物 JacksonObjectMapper 注册的是 LocalDateTime/LocalDate/LocalTime 的时间格式序列化**（`yyyy-MM-dd HH:mm` 等），没有 Long→String → 建议同步修订 knowledge-map
2. （实施中若发现新的规格偏差，追加到本节）
