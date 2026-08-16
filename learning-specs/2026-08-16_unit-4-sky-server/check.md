# 单元 4 验证判定标准（check）

> 单元：sky-server 员工登录链路　日期：2026-08-16
> 验证三件套：费曼检验 + 亲手验证 + 审查练习，全部通过才算学会
> 命名约定同 plan.md：**参照物** = `../sky-take-out/backend/sky-take-out/sky-server/`；**产物** = `./sky-take-out/sky-server/`

## 〇、开场抽查（单元 2+3 混合，不达标先补学）
老师随机抽查**上两个单元**的 4-5 个核心知识点（Result 契约 code=1/0、JWT 生成/解析流程、ThreadLocal 为什么用 ThreadLocal、配置绑定规则 / DTO/Entity/VO 三者职责、登录双类为什么存在、Lombok 为什么 @Builder 配 @NoArgsConstructor、@ApiModel 给谁看），用户不看文档回答；答错或答不出 → 先补学对应知识，再进入任务组 1。

## 一、费曼检验（用户讲，老师提问）

**老师提问清单**（用户需不用看文档讲出来；标【深入】的重点项由老师追问到逻辑闭合）：

1. 一次登录请求的完整旅程：从浏览器 POST 到返回 JSON，经过哪些"站点"？每个站点干什么？【深入】
2. @SpringBootApplication 是什么？为什么标了 `@Component`/`@Service`/`@Mapper` 的类能被 Spring 自动找到？
3. 三层架构：Controller / Service / Mapper 各干什么？`@Autowired` 注入的对象是谁创建的？接口和实现为什么要分开？【深入】
4. Service 里抛出的三种异常（账号不存在/密码错误/账号锁定）被谁接住？怎么变成 JSON 返回给前端？为什么不写 try-catch？【深入】
5. 拦截器：请求什么时候经过它？为什么 `/admin/employee/login` 必须排除？如果不排除会发生什么？【深入】
6. MyBatis：`@Select` 注解和 XML 是什么关系？XML 的 namespace 干什么用？驼峰映射开关解决什么问题？`@Mapper` 和 @MapperScan 有什么区别？
7. `application.yml` 里 `${sky.datasource.host}` 的值从哪来？和单元 2 的 JwtProperties 是什么关系？为什么要拆 application.yml 和 application-dev.yml 两个文件？【深入】
8. 单元 3 的 @ApiModel 注解在 knife4j 文档里怎么"生效"的？doc.html 是什么？
9. 设计判断：JWT 为什么在 Controller 签而不是 Service？密码为什么明文比对还留着 TODO？（照抄 vs 设计动机）【深入】
10. 随机指一个文件，说出它属于链路哪一站、字段/逻辑从哪来、被谁调用。（老师至少抽查 3 个：Controller / Interceptor / application.yml 各一）

**判定**：能用自己的话讲通（允许类比、允许小卡壳，但逻辑要对）；讲不出/讲错 = 未达标，重讲后再验证。

## 二、亲手验证（对照参照物逐文件核对）

### 核对 1：diff 内容一致（11 个文件）
对产物全部 11 个文件执行 diff 到参照物对应文件，**实质内容一致即通过**。

**diff 验收细则（用户 · 老师共同认可，沿用单元 1-3）**：
- **放行**（判通过，不修改）：空行/注释措辞/换行符差异、import 顺序不同——不影响编译与成品效果；
- **必改**（判失败）：包名/类名/字段/方法逻辑/注解写错、缺内容、yml 键名或缩进层级错误、XML namespace 写错——任何可能导致成品与模板不一致的差异。
- 特别注意：SkyApplication.java 仅空格差异（`SkyApplication{`、`(args)` 前的空格）→ 放行项，确认即可不用改。

### 核对 2：编译通过（"跑通"的替代验证）
- IDEA 以 Maven 工程打开 `sky-take-out/`（产物），Maven 面板执行整个工程 Lifecycle → compile，**BUILD SUCCESS**
- 无红色报错/红叉（依赖在单元 1 pom 已就位；.gitkeep 被新文件替换属正常）
- 观察编译日志确认 8 个 java 文件全部编译通过（新建的 7 个 + 已有 1 个）

### 核对 3：模式复用抽查
- 不看参照物，为假想业务"按 id 查询员工"说出扩展清单：Controller / Service / Impl / Mapper 各加什么方法、调用链怎么连、返回类型用哪个（Result<Employee>）
- 老师验证其清单与官方脚手架中同构模式（如后续功能的写法）一致

**判定**：以上 3 项核对全部通过。

## 三、审查练习（老师出题，用户独立判断）

老师给出 2 段故意写错的代码，用户对每段：① 找出问题 ② 说明为什么错（后果推演）③ 说出正确写法。

**场景一 · 依赖注入错误**：Controller 里写了 `EmployeeService employeeService = new EmployeeServiceImpl();`（或 `new EmployeeService()`），或改写了 `@Autowired` 到错误类型——找出问题，说清"对象为什么必须由容器注入"（谁负责创建、new 出来会怎样：脱离容器管理、依赖链断裂、无法使用容器功能）。

**场景二 · 拦截器配置错误**：`WebMvcConfiguration` 的注册里 `excludePathPatterns` 写错（如漏掉 `/admin/employee/login`，或把整个 `/admin/**` 排除）——找出问题，推演后果（漏排除 = 登录请求也被拦 → 401 → 永远无法登录；全排除 = 拦截器形同虚设，token 校验失效）。

**判定**：错误全找对、理由全说对（含后果推演）。

## 四、对照学习目标检查
逐条核对 learning-goals.md：G1-G9 费曼达标、S1-S4 验证达标；未达标项记录在 changelog 并安排补学。

## 五、收尾
- 更新 `learning-specs/learning-changelog.md`（学会了什么/踩了什么坑/规格修订：knowledge-map @MapperScan 修正 + roadmap 任务组顺序与学习点修正）
- 更新 roadmap 单元 4 状态为 ✅ 完成
- 合并分支 unit-4-sky-server → main（用户执行）
