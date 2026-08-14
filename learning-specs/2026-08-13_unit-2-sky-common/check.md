# 单元 2 验证判定标准（check）

> 单元：sky-common 公共层　日期：2026-08-13
> 验证三件套：费曼检验 + 亲手验证 + 审查练习，全部通过才算学会
> 命名约定同 plan.md：**参照物** = `../sky-take-out/backend/sky-take-out/sky-common/`；**产物** = `./sky-take-out/sky-common/`

## 一、费曼检验（用户讲，老师提问）

**老师提问清单**（用户需不用看文档讲出来；标【深入】的重点项由老师追问到逻辑闭合）：

1. sky-common 里放什么？8 个包各管什么？为什么这些放 common 而不放 server/pojo？
2. Result 的 code/msg/data 各是什么？success() / success(data) / error(msg) 分别怎么填？为什么全项目统一返回格式？【深入】
3. BaseContext 为什么用 ThreadLocal 而不是普通静态变量？多线程并发下普通变量会出什么问题？set/get/remove 分别在什么时候用？【深入】
4. 画一下 JWT 的三部分结构；生成流程、解析流程各几步？claims 里一般存什么？HS256 对称密钥意味着什么？【深入】
5. yml 的 `sky.jwt.admin-secret-key` 是怎么变成 JwtProperties 的 `adminSecretKey` 的？【深入】
6. BaseException 为什么继承 RuntimeException？继承 Exception 会怎样？
7. OperationType 枚举为什么比常量好？
8. 4 个工具类各对应哪个第三方库？各自在项目里管什么？
9. 设计判断：为什么常量字段名（USERNAME）和值（"userId"）必须与参照物严格一致、不能随手改名？这类"契约"在工程里的作用是什么？
10. 模块设计：8 个包按职责分哪几类？context（ThreadLocal）和 utils（JwtUtil）为什么不能合并成一个包？划分的判断标准是什么？为什么放 common 而非 server/pojo？

**判定**：能用自己的话讲通（允许类比、允许小卡壳，但逻辑要对）；讲不出/讲错 = 未达标，重讲后再验证。

## 二、亲手验证（对照参照物逐文件核对）

### 核对 1：diff 内容一致（29 个 .java 文件）
对产物 8 包全部文件执行 diff 到参照物对应文件，**实质内容一致即通过**。

**Java 文件 diff 验收细则（用户 · 老师共同认可）**：
- **放行**（判通过，不修改）：空行/注释措辞/换行符差异、import 顺序不同——不影响编译与成品效果；
- **必改**（判失败）：包名/类名/字段/方法逻辑/注解写错、缺内容——任何可能导致成品与模板不一致的差异。

### 核对 2：编译通过（"跑通"的替代验证）
- IDEA 以 Maven 工程打开 `sky-take-out/`（产物）
- Maven 面板执行 sky-common 模块（或整个工程）Lifecycle → compile，**BUILD SUCCESS**
- 无红色报错/红叉（依赖均已就位：单元 1 的 sky-common/pom.xml 已含所需依赖）

### 核对 3：模式复用抽查
- 不看参照物，口头/纸面写出一个新的业务异常类（如 CategoryNotFoundException），与现有模式比对写法正确

**判定**：以上 3 项核对全部通过。

## 三、审查练习（老师出题，用户独立判断）

老师给出 1 段故意写错的代码（**场景一**：BaseContext 被错写成普通静态变量 `static Long currentId` + get/set，没有 ThreadLocal），用户：
1. 找出问题
2. 说明为什么错（并发场景下会发生什么）
3. 说出正确写法

老师给出另 1 段（**场景二**：Result 的 error() 忘记设置 code，或 JwtUtil 生成时漏了设置过期时间），用户同样找错 + 说理 + 改对。

**判定**：错误全找对、理由全说对。

## 四、对照学习目标检查
逐条核对 learning-goals.md：G1-G8 费曼达标、S1-S4 验证达标；未达标项记录在 changelog 并安排补学。

## 五、收尾
- 更新 `learning-specs/learning-changelog.md`（学会了什么/踩了什么坑）
- 合并分支 unit-2-sky-common → main（用户执行）
