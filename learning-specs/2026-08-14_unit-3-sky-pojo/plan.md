# 单元 3 学习计划（plan）

> 单元：sky-pojo 数据层　日期：2026-08-14　分支：unit-3-sky-pojo
> 命名约定（沿用单元 1/2）：
> - **参照物** = 官方脚手架工程（只读基准，diff 对照对象）：`../sky-take-out/backend/sky-take-out/sky-pojo/`
> - **产物** = 本工作区复刻出的工程（要建的东西）：`./sky-take-out/sky-pojo/`

## 总目标
产出与官方**逐文件 diff 内容一致**的 sky-pojo 数据层全部 **49 个文件（dto 21 / entity 11 / vo 17）**；理解"数据模型为什么要分成 DTO/Entity/VO 三层"，啃下 4 个核心难点：**三者职责辨析（🔴，概念先行）→ Lombok 注解体系 → @ApiModel 为 knife4j 服务 → entity 与数据库表的对应（ORM 映射入门）**。
**复现与设计并重**：diff 零差异（复现）是验收标准；学习重点在于**设计理解**——为什么输入输出分开建模（登录只传 username/password，返回却有 id/name/token）、为什么 VO 可以比 entity 字段多、为什么这些 POJO 一律 implements Serializable、为什么"该有的没有"（UserLoginVO 无 @ApiModel）也必须照抄。能讲出设计动机才算真正学会。
（验收细则见 check.md 核对 1：Java 文件 diff 放行/必改规则。）

## 实施节奏约定（用户 · 老师共同遵守）
- **git 分支时机（用户执行）**：任务组 1 开始前，先在 **main** 上提交本单元规划变更（三件套 + roadmap 修订，章程演进，沿用单元 1 先例），再 `git checkout -b unit-3-sky-pojo` 从 main 切出分支；实施代码（任务组 2-6）全部小步提交在该分支，任务组 8 验证通过后合并回 main；
- 费曼检验全对且无疑问句 → 老师进入下一知识点；回答有错/有疑问 → 老师解答后**必须等用户明确确认"明白了/继续"**，禁止答完疑问直接跳步；
- **开场抽查**：开始前老师先随机抽查单元 2 的 2-3 个核心知识点（ThreadLocal / Result 契约 / JwtUtil / 配置绑定，不提示不剧透），不过先补学；
- **任务组内大块内容按块细化为学习小阶段**（如下方"块 N"标注）；用户可随时要求进一步细化粒度；
- **知识沉淀（Obsidian 笔记）**：本单元按**设计概念分 2 次**记录进 `E:\deeplearning\dl\Java`（07 已完成；08 合并原触发点②③，编号 09 不再使用），每次记录经用户审阅通过后再继续下一步；笔记侧重设计而非复现（不抄字段清单）；
- 与全局 learning-mission.md「教学协作约定」一致，本单元实施全程适用。

## 任务组清单（概念 → 动手 → 验证，按序实施，每任务组结束等用户确认）

### 任务组 1（概念铺垫 · 🔴 三者职责辨析——概念先行）
- **生活化类比**：点餐场景——顾客递来的**点菜单**（输入：你要什么）→ 后厨的**食材档案/货架**（数据：和仓库表一一对应）→ 端上桌的**摆盘**（输出：你看到什么）
- **技术对照**：DTO（Data Transfer Object，传输对象，接前端请求数据）/ Entity（实体，数据库表的一行记录）/ VO（View Object，视图对象，返回给前端的数据）
- **为什么分开**：三者变化的原因不同（前端改需求 → DTO/VO 变；表结构变 → Entity 变），分开避免互相污染；字段有交集但**不是同一份**
- **登录链路数据流**：前端表单 → `EmployeeLoginDTO`（username/password 2 字段）→ Service 查库比对（`Employee` 实体，12 字段）→ 签发 JWT → `EmployeeLoginVO`（id/userName/name/token 4 字段）→ 前端 —— 一句话讲清"登录接口为什么同时有 DTO 和 VO"
- 读参照物目录结构，数清 49 个文件各自归属 dto/entity/vo 哪个包

### 任务组 2（动手 · 员工域 6 文件 —— 登录链路重点）
**分块推进（每块一个学习小阶段，讲一块 → 费曼确认 → 下一块）：**
- **块 1 · EmployeeLoginDTO + EmployeeLoginVO（🔴 重点讲）**
  - 两个类对照：输入 2 字段 vs 输出 4 字段——"输入要什么给什么，输出按需要组装"；为什么 password 只进不出
  - **Lombok 注解体系（归类讲透 + 验证演示）**：@Data（生成 getter/setter/toString/equals 等）→ @NoArgsConstructor / @AllArgsConstructor（无参/全参构造）→ @Builder（建造者：链式 new 对象）→ **为什么 @Builder 要配 @NoArgsConstructor**（@Builder 会覆盖默认构造，而 MyBatis/序列化等框架要按无参构造实例化——先记结论，单元 4 实践验证）
  - **验证演示**：IDEA 编译后看 target/classes 里的 .class（或反编译），亲眼确认 @Data/@Builder 真的生成了代码——Lombok 是**编译期**干活的注解处理器
  - **@ApiModel / @ApiModelProperty（为 knife4j 服务）**：给接口文档工具看的"数据描述元数据"（类比：商品包装上的配料表），本单元只讲"是干什么的"，工具本身单元 4 装
  - implements Serializable：复习单元 2 Result 已学的序列化接口
- **块 2 · Employee（entity，🔴 重点讲）**
  - 对照「数据库设计文档」employee 表：一张表 = 一个 entity 类、一列 = 一个字段（username/name/password/phone/sex/idNumber/status/createTime/updateTime/createUser/updateUser）
  - **ORM 映射入门**：Java 类型 ↔ 数据库列类型（LocalDateTime ↔ datetime、Integer ↔ int、Long ↔ bigint）
  - 注意 `@JsonFormat` 被注释掉——为什么？时间格式统一交给单元 2 的 JacksonObjectMapper（common 层全局处理，这里不能再自己格式化）；**对比**：OrdersSubmitDTO（块 3 会见到）里 @JsonFormat 却是启用的——同一注解两种状态，实施时对照讨论
  - **serialVersionUID**：Employee 有而大部分类没有——序列化版本号是什么，照抄为准
- **块 3 · 照模式写 3 个**：EmployeeDTO（新增员工）/ EmployeePageQueryDTO（分页查询：page/pageSize + 条件）/ PasswordEditDTO（改密码）——用户**亲手写出** 6 个文件 → diff 核对
- **📝 笔记触发点①**：07 三层数据模型职责设计（DTO/Entity/VO 为什么分开 + 登录链路实例）

### 任务组 3（动手 · 用户域 4 + 分类域 3 = 7 文件）
- 用户域：User / AddressBook（entity）+ UserLoginDTO / UserLoginVO
  - **照抄纪律教学点**：UserLoginVO **没有** @ApiModel（实测参照物），EmployeeLoginVO 却有——"该有的没有"也要照抄，猜模式会破坏 diff 零差异
  - **Lombok 配方差异教学点**：同一层里的类注解配方可以不同（如 UserLoginVO 无 @ApiModel、部分类只有 @Data）——以参照物逐字为准，不按"层"套模板
- 分类域：Category（entity）+ CategoryDTO / CategoryPageQueryDTO（与员工域同构，练"看模式"）
- 用户**亲手写出** 7 个文件 → diff 核对

### 任务组 4（批量域① · 菜品+套餐 10 文件）
- **业务域速览①（5-10 分钟，讲"菜单业务"）**：分类 → 菜品 → 口味（一对多）/ 套餐 → 套餐-菜品（多对多，SetmealDish 中间表）的表关系；类家族图谱——每个类收什么（DTO）/存什么（entity）/返回什么（VO），字段与业务的对应
- 用户**批量复制** 10 个同质类：菜品域 Dish / DishDTO / DishPageQueryDTO / DishItemVO / DishOverViewVO + 套餐域 Setmeal / SetmealDTO / SetmealPageQueryDTO / SetmealVO / SetmealOverViewVO（复制纪律：整文件一字不改，参照物有则抄、无则不加）
- 我 diff 验收 + **抽查 2 个类**（用户讲业务含义：这张表存什么、这个 DTO 对应哪个动作，防无脑复制）

### 任务组 5（批量域② · 购物车+订单 13 文件）
- **业务域速览②（讲"订单全流程"）**：提交/接单/拒单/付款/派送/完成/取消——**"字段跟着动作走"**：8 个业务动作 → 8 个 DTO（OrdersSubmitDTO 收下单信息、OrdersCancelDTO 只需 id+cancelReason、OrdersConfirmDTO 只需 id…）；orders 主表与 order_detail 明细表的主从关系
- 用户**批量复制** 13 个同质类：ShoppingCart / ShoppingCartDTO + OrderDetail / OrdersDTO / OrdersCancelDTO / OrdersConfirmDTO / OrdersRejectionDTO / OrdersPaymentDTO / OrdersPageQueryDTO / OrderVO / OrderPaymentVO / OrderStatisticsVO / OrderOverViewVO
- 我 diff 验收 + 抽查 2 个类讲业务含义

### 任务组 6（批量域③ · 报表 6 文件）
- **业务域速览③（讲"报表/统计"）**：老板的仪表盘——营业额/订单/用户/销量 Top10/工作台总览，每张报表一个 VO；查询输入类（DataOverViewQueryDTO / GoodsSalesDTO）与分页 DTO 的异同
- 用户**批量复制** 6 个同质类：BusinessDataVO / TurnoverReportVO / UserReportVO / SalesTop10ReportVO / DataOverViewQueryDTO / GoodsSalesDTO
- 我 diff 验收 + 抽查 2 个类讲业务含义

### 任务组 7（重点类教学 · 7 个类，按当前教学模式：讲 → 手写 → diff → 费曼确认）
- **组① DishVO（组装型 VO）**：categoryName + `List<DishFlavor> flavors = new ArrayList<>()`（初始值防 NPE）——VO 按展示需求组装，不必与表一一对应
- **组② DishFlavor + SetmealDish（关联表实体）**：从属口味 / 套餐-菜品中间表的结构与含义
- **组③ Orders + OrdersSubmitDTO + OrderSubmitVO（状态常量归属 + 下单三形态）**：内嵌 9 个状态常量 vs common 的 StatusConstant——"只被自己用放自己类、跨模块共用放 common"；同业务三种形态收官对比；Orders 注释"7 退款"官方笔误照抄案例
- **组④ OrderReportVO（报表型 VO）**：逗号分隔字符串表达图表数据（dateList = `"2022-10-01,2022-10-02"`，不是 List）——展示形态决定字段类型
- 每组讲完 → 用户手写 → diff → 费曼确认 → 下一组
- **📝 笔记触发点②（合并原②③）**：08 POJO 类设计模式与字段设计（三类配方表 + 4 个新知识点 + 字段与数据库对照）

### 任务组 8（验证）：见 `check.md` 逐条执行
- 全量 diff 核对（49 文件）
- IDEA 编译 sky-pojo 模块（compile）BUILD SUCCESS
- 费曼检验（用户讲，老师提问，重点 4 项：三者职责、登录双类、Lombok 成套、@ApiModel）
- 审查练习（老师给 AI 风格错误代码，用户独立判断）

### 任务组 9（收尾）
- 更新 `learning-specs/learning-changelog.md`（含本次教学计划调整记录 + 参照物误改重复坑）+ 更新 roadmap 单元 3 状态（用户执行）
- 合并分支 unit-3-sky-pojo → main（用户执行）

## 产出清单（产物 `./sky-take-out/sky-pojo/`，本单元结束时应存在）
```
sky-pojo/src/main/java/com/sky/
├── dto/   21 个：EmployeeLoginDTO / EmployeeDTO / EmployeePageQueryDTO / PasswordEditDTO / UserLoginDTO /
│           CategoryDTO / CategoryPageQueryDTO / DishDTO / DishPageQueryDTO / SetmealDTO / SetmealPageQueryDTO /
│           ShoppingCartDTO / OrdersDTO / OrdersSubmitDTO / OrdersCancelDTO / OrdersConfirmDTO /
│           OrdersRejectionDTO / OrdersPaymentDTO / OrdersPageQueryDTO / DataOverViewQueryDTO / GoodsSalesDTO
├── entity/ 11 个：Employee / User / AddressBook / Category / Dish / DishFlavor / Setmeal / SetmealDish /
│           ShoppingCart / Orders / OrderDetail
└── vo/     17 个：EmployeeLoginVO / UserLoginVO / DishVO / DishItemVO / DishOverViewVO / SetmealVO /
            SetmealOverViewVO / OrderVO / OrderSubmitVO / OrderPaymentVO / OrderStatisticsVO / OrderOverViewVO /
            OrderReportVO / TurnoverReportVO / UserReportVO / SalesTop10ReportVO / BusinessDataVO
```
合计 49 个文件（任务组 2：6 → 任务组 3：7 → 批量域：10+13+6 → 重点类：7）

## 规格偏差备注（审查时确认是否同步修订 roadmap/knowledge-map）
1. **roadmap 偏差（用户拍板，已同步 ✅ 2026-08-14）**：roadmap 单元 3 任务组原为"① entity ② dto ③ vo ④ 辨析"（按包顺序）；本计划改为**概念先行 + 登录链路优先 + 按业务域混合写**（员工域 → 用户/分类 → 菜品/套餐 → 购物车/订单 → 报表），三者辨析提前到任务组 1 —— roadmap 单元 3 任务组描述已同步修订，knowledge-map 为知识结构不含顺序，无需修改
2. **教学节奏调整（用户拍板 2026-08-15）**：任务组 4-6 由"逐任务组讲解+亲手写"调整为**批量域三阶段**（业务域速览 → 批量复制 → diff+抽查，29 个同质类）+ **任务组 7 重点类完整教学**（7 个类：DishVO / DishFlavor / SetmealDish / Orders / OrdersSubmitDTO / OrderSubmitVO / OrderReportVO）。动因：模块内容同质化高、配方已被任务组 2-3 验证；用户已确认结构模式与复制纪律。**验证环节不减**：费曼总清单、审查练习、编译、批量抽查照旧。笔记触发点②③合并为 08 一篇（编号 09 不再使用）。
3. （实施中若发现新的规格偏差，追加到本节）
