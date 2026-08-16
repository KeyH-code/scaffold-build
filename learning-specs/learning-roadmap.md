# 学习路线图（learning-roadmap）

> 生成日期：2026-08-12　活文档：每单元合并后更新进度
> 总目标：从零复刻官方脚手架（93 源文件 / 3 模块），验收 = 逐文件 diff 内容一致 + 登录链路跑通 + 理解达成

## 阶段总览

| 阶段 | 内容 | 状态 |
|---|---|---|
| Phase 0 | 环境核查（JDK 21 先试 / IDEA 内置 Maven / MySQL 在跑 / Redis 后装） | ✅ 完成 |
| 章程 | 本文档体系 + environment.md | ✅ 完成 |
| 单元 1 | Maven 多模块骨架 | ✅ 完成 |
| 单元 2 | sky-common 公共层（29 文件） | ✅ 完成 |
| 单元 3 | sky-pojo 数据层（49 文件） | ✅ 完成 |
| 单元 4 | sky-server 员工登录链路（8 java + 3 resources） | ⏳ |
| 单元 5 | 端到端验收（建库 → 启动 → 登录接口 → 零差异 diff） | ⏳ |

## 单元详情

### 单元 1：Maven 多模块骨架
- **目标**：理解 Maven 多模块工程的组织方式；产出根 pom + 3 子模块 pom + 启动类 + 空包结构
- **任务组**：① 根 pom（parent/dependencyManagement/15 个受管依赖版本）② sky-common / sky-pojo / sky-server 三个子 pom（依赖关系、插件）③ SkyApplication 启动类 ④ 包结构骨架（com.sky.* 各包目录先建好）
- **验收**：pom ×4 + 启动类与官方 diff 零差异；能讲出"父 pom 管版本、子模块只管依赖"的道理
- **学习点**：Maven 坐标（groupId/artifactId/version）、dependencyManagement 与 dependencies 区别、模块依赖方向、spring-boot-maven-plugin

### 单元 2：sky-common 公共层（29 文件）
- **目标**：理解"公共层放什么"；产出 constant/context/enumeration/exception/json/properties/result/utils 8 包全部文件
- **任务组**：① 简单包：constant（5）→ result（2）→ enumeration（1）→ exception（12）② 中难包：properties（3 配置绑定）→ json（1 序列化）→ context（1 ThreadLocal）→ utils（4，JwtUtil 重点讲解）
- **验收**：29 文件与官方 diff 零差异（复现达标）；能讲出 BaseContext 为什么用 ThreadLocal、JwtUtil 的生成/解析流程，并说明各类的设计动机（设计达标）
- **学习点**：常量/枚举/异常体系、统一返回契约（Result）、@ConfigurationProperties、ThreadLocal、JWT 结构（header.payload.signature）；**各包"为什么这样设计"（契约与取舍）**；**模块划分设计**（8 包职责分类、有状态/无状态分离、公共层归属判断）

### 单元 3：sky-pojo 数据层（49 文件）
- **目标**：理解 DTO/Entity/VO 三者职责区别；产出 dto(21)/entity(11)/vo(17) 全部文件
- **任务组**：① 三者职责辨析（概念先行）② 员工域 6 文件（登录链路重点）③ 用户域+分类域 7 文件 ④-⑥ 批量域（菜品+套餐 10 / 购物车+订单 13 / 报表 6，业务域速览→复制→抽查）⑦ 重点类教学 7 文件（组装VO/关联表/常量归属/报表VO）⑧ 验证 ⑨ 收尾
- **验收**：49 文件与官方 diff 零差异；能回答"登录接口为什么同时有 EmployeeLoginDTO 和 EmployeeLoginVO"
- **学习点**：ORM 映射、Lombok 注解（@Data/@Builder 等）、DTO/Entity/VO 的分层职责、类设计（@ApiModel 注解为 knife4j 服务）

### 单元 4：sky-server 员工登录链路（8 java + 3 resources）
- **目标**：打通完整故事——HTTP 请求进来后发生什么；产出全部代码与配置
- **任务组**：① application.yml + application-dev.yml（配置讲解）② config（knife4j + 拦截器注册）③ interceptor（JWT 校验）④ handler（全局异常）⑤ mapper（接口 + XML）⑥ service/impl（登录业务）⑦ controller（登录接口）
- **验收**：与官方 diff 零差异；能完整讲出登录请求的旅程（Controller → Service → Mapper → MySQL → 密码比对 → JWT 签发 → 拦截器下次校验）
- **学习点**：Spring Boot 自动配置与启动流程、三层架构、MyBatis XML mapper、拦截器机制、全局异常处理、knife4j 文档

### 单元 5：端到端验收
- **目标**：证明"复刻成功"——跑起来、登录通、零差异
- **任务组**：① MySQL 建库 sky_take_out + 导入 sky.sql ② IDEA 启动 SkyApplication（JDK 21 试跑）③ 打开 knife4j 文档页，调员工登录接口 ④ 逐文件 diff 官方参照物核对零差异（排除 .idea/target）⑤ 复盘 + 更新 changelog
- **验收**：登录接口返回正确结果（含 token）；diff 内容一致清单签字确认
- **学习点**：跑通验证方法（看日志/看返回）、diff 工具用法、环境问题排查

## 里程碑与分支策略
- 每单元独立 git 分支（如 `unit-1-maven-skeleton`），单元内小步提交，验证通过后合并到 main
- 所有 git 操作由**用户亲自执行**，老师只给命令与讲解
- 每单元三件套文档（plan / learning-goals / check）在规划阶段生成于 `learning-specs/<日期>_<单元名>/`

## 复盘机制
- 每完成一个单元：更新本路线图进度 + 更新 learning-changelog.md
- 每完成一个里程碑（如 5 单元全通）：新开会话做整体复盘，对照本路线图检查达成度
