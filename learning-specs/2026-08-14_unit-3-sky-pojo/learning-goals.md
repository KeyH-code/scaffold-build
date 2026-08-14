# 单元 3 学习要求（learning-goals）

> 单元：sky-pojo 数据层　日期：2026-08-14
> 原则：写"达到什么能力"，不写实现细节

> **复现（diff 零差异）是验收标准；设计理解（为什么这样设计）是学习重点**，两者并列达标才通过。

## 一、理解（学完能讲出）

| # | 能力 | 验收描述 |
|---|---|---|
| G1 | 讲清三者职责（🔴） | 能用类比讲出 DTO/Entity/VO 各是什么；能画出数据流"前端 → DTO → Entity → VO → 前端"并说清每一层只做一件事 |
| G2 | 讲清登录链路双类（🔴） | 能回答"登录接口为什么同时有 EmployeeLoginDTO 和 EmployeeLoginVO"；能说出输入 2 字段/输出 4 字段各是什么、为什么 password 只进不出 |
| G3 | 讲清 Lombok 注解体系 | 能说出 @Data/@Builder/@NoArgsConstructor/@AllArgsConstructor 各自生成什么；能说出为什么 @Builder 与 @NoArgsConstructor 要成套出现；能说出 Lombok 是编译期生成代码（验证演示佐证） |
| G4 | 讲清 @ApiModel | 能说出 @ApiModel/@ApiModelProperty 是给谁看的（knife4j 接口文档的元数据）；能说出为什么有些类有、有些类没有（以参照物为准，不是按层统一套） |
| G5 | 讲清 ORM 映射入门 | 能说出"一张表 = 一个 entity、一列 = 一个字段"；能说出常用 Java 类型 ↔ 数据库列类型的对应（LocalDateTime↔datetime 等）；能解释 Employee 里 @JsonFormat 为什么被注释（时间格式统一由 common 层处理）而 OrdersSubmitDTO 里却是启用的 |
| G6 | 讲清 VO 的组装特性 | 能说出为什么 DishVO 比 Dish 实体字段多（categoryName/flavors 是组装出来的展示数据）；能说出报表 VO 为什么用逗号分隔字符串表达图表数据；由此总结 VO"按展示需求组装、不必与表一一对应" |
| G7 | 讲清照抄纪律 | 能说出为什么"该有的没有"（UserLoginVO 无 @ApiModel）也要照抄；能说出连官方笔误（Orders 注释"7 退款"）也要照抄——验收 = diff 零差异，不能猜模式、不能"帮忙修正" |
| G8 | 讲清 Serializable / serialVersionUID | 能说出 POJO 为什么一律 implements Serializable；能说出 serialVersionUID 是什么、为什么只有部分类有 |
| G9 | 讲清常量归属与设计动机 | 能说出常量放哪里的判断标准（只被自己用 → 放自己类里如 Orders 状态常量；跨模块共用 → 放 common 如 StatusConstant）；随机指一个文件，能说出参照物"为什么这样设计"——设计理解维度，与复现并列 |

## 二、技能（学完能做）

| # | 能力 | 验收描述 |
|---|---|---|
| S1 | 能照参照物写出全部 49 个文件 | 产物与参照物逐文件 diff 零差异；写的过程中能说出每个文件在干什么、属于哪一层 |
| S2 | 能按模式新增一个 DTO | 不看参照物，为假想业务写一个新的输入类（DTO），注解、Serializable、字段风格与参照物一致 |
| S3 | 能说清每个类的归属与来源 | 随机指一个文件，能说出：属于 dto/entity/vo 哪一层、字段来自哪里（前端提交 / 数据库列 / 组装拼接）、被谁使用 |
| S4 | 能画出登录链路类流转 | 不看文档画出 EmployeeLoginDTO → Employee → EmployeeLoginVO 的流转图，标注每步做了什么 |

## 三、产出物
见 `plan.md` 产出清单：`./sky-take-out/sky-pojo/` 下 dto(21) / entity(11) / vo(17) 共 49 个 .java 文件。

## 四、明确不要求（本单元边界）
- 不要求深入 knife4j / Swagger 工具原理（@ApiModel 先"会标会用"，工具本身单元 4 配置后见效果）
- 不要求理解 Lombok 注解处理器底层原理（会"注解 → 生成什么"的对应即可）
- 不要求背 49 个文件的字段清单（知道三类文件的模式、会照抄、能说出代表类的大致字段即可）
- 不要求从零设计字段（能对照参照物写 + 讲出为什么长这样即可）
- 不要求 MySQL 实操与建库（单元 5 才建库；本单元读数据库设计文档做"纸上对照"）
- 不要求理解 MyBatis 的实体映射与 SQL（单元 4 讲 mapper 时涉及）
- 不要求理解 @Builder 与框架实例化的底层细节（先记"为什么要成套"的结论，单元 4 实践验证）
