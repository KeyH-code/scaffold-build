# 单元 3 验证判定标准（check）

> 单元：sky-pojo 数据层　日期：2026-08-14
> 验证三件套：费曼检验 + 亲手验证 + 审查练习，全部通过才算学会
> 命名约定同 plan.md：**参照物** = `../sky-take-out/backend/sky-take-out/sky-pojo/`；**产物** = `./sky-take-out/sky-pojo/`

## 〇、开场抽查（单元 2 知识，不达标先补学）
老师随机抽查单元 2 的 2-3 个核心知识点（ThreadLocal 为什么用 ThreadLocal / Result 契约 code=1、0 / JwtUtil 生成解析流程 / 配置绑定规则），用户不看文档回答；答错或答不出 → 先补学单元 2 对应知识，再进入任务组 1。

## 一、费曼检验（用户讲，老师提问）

**老师提问清单**（用户需不用看文档讲出来；标【深入】的重点项由老师追问到逻辑闭合）：

1. DTO / Entity / VO 三者各是什么？用你的类比讲一遍。一笔登录请求的数据，在这三者之间怎么流转？【深入】
2. 登录接口为什么同时有 EmployeeLoginDTO 和 EmployeeLoginVO？它们的字段为什么不一样？password 为什么只进不出？【深入】
3. @Data / @Builder / @NoArgsConstructor / @AllArgsConstructor 各自生成什么？为什么 @Builder 要配 @NoArgsConstructor？怎么证明 Lombok 真的生成了代码？【深入】
4. @ApiModel / @ApiModelProperty 是给谁看的？为什么 UserLoginVO 没有、EmployeeLoginVO 有？遇到"参照物与你的猜测不一致"时怎么办？
5. 随机指一个 entity（如 Orders）：它对应哪张表？逐字段说列类型与 Java 类型对应；时间字段为什么不在类里写 @JsonFormat？那 OrdersSubmitDTO 里为什么有？
6. 为什么 DishVO 比 Dish 实体字段多？flavors 字段为什么有个 `new ArrayList<>()` 初始值？报表 VO 为什么用逗号分隔字符串存图表数据？这说明了 VO 的什么职责特点？【深入】
7. Orders 类里为什么内嵌状态常量而不放 common 的 StatusConstant？"常量放哪里"的判断标准是什么？【深入】
8. POJO 为什么一律 implements Serializable？serialVersionUID 是干什么的？为什么只有部分类有？
9. 设计判断：登录 DTO 为什么不把员工所有字段都带上？"最小输入"原则对工程意味着什么？为什么 PageQueryDTO 都带 page/pageSize 两个字段？
10. 随机指一个文件，说出它属于哪一层、字段从哪来、被谁用。（老师至少抽查 3 个：dto/entity/vo 各一）

**判定**：能用自己的话讲通（允许类比、允许小卡壳，但逻辑要对）；讲不出/讲错 = 未达标，重讲后再验证。

## 二、亲手验证（对照参照物逐文件核对）

### 核对 1：diff 内容一致（49 个 .java 文件）
对产物 dto/entity/vo 全部文件执行 diff 到参照物对应文件，**实质内容一致即通过**。

**Java 文件 diff 验收细则（用户 · 老师共同认可，沿用单元 2）**：
- **放行**（判通过，不修改）：空行/注释措辞/换行符差异、import 顺序不同——不影响编译与成品效果；
- **必改**（判失败）：包名/类名/字段/方法逻辑/注解写错、缺内容——任何可能导致成品与模板不一致的差异。

### 核对 2：编译通过（"跑通"的替代验证）
- IDEA 以 Maven 工程打开 `sky-take-out/`（产物）
- Maven 面板执行 sky-pojo 模块（或整个工程）Lifecycle → compile，**BUILD SUCCESS**
- 无红色报错/红叉（依赖在单元 1 pom 已就位：swagger 注解、Lombok、jackson）

### 核对 3：模式复用抽查
- 不看参照物，口头/纸面写出一个新 DTO（老师指定假想业务，如"新增优惠券 CouponDTO"），与现有模式比对：@Data/@ApiModel/Serializable/字段命名风格正确

**判定**：以上 3 项核对全部通过。

## 三、审查练习（老师出题，用户独立判断）

老师给出 1 段故意写错的代码（**场景一**：EmployeeLoginVO 被错写成 entity 风格——字段抄成数据库全列（含 createTime/createUser 等）+ 没有 @Builder，或把 @ApiModel 误加到 UserLoginVO），用户：
1. 找出问题
2. 说明为什么错（层次混用会怎样：把不该暴露的字段返回给前端 / 与参照物 diff 不一致）
3. 说出正确写法

老师给出另 1 段（**场景二**：Lombok 组合错误——类上写了 @Builder 但没配 @NoArgsConstructor，或写了手写 getter/setter 又加 @Data），用户同样找错 + 说理 + 改对。

**判定**：错误全找对、理由全说对。

## 四、对照学习目标检查
逐条核对 learning-goals.md：G1-G9 费曼达标、S1-S4 验证达标；未达标项记录在 changelog 并安排补学。

## 五、收尾
- 更新 `learning-specs/learning-changelog.md`（学会了什么/踩了什么坑）+ 更新 roadmap 单元 3 状态
- 合并分支 unit-3-sky-pojo → main（用户执行）
