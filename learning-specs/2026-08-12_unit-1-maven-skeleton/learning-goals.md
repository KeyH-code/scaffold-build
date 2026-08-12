# 单元 1 学习要求（learning-goals）

> 单元：Maven 多模块骨架　日期：2026-08-12
> 原则：写"达到什么能力"，不写实现细节

## 一、理解（学完能讲出）

| # | 能力 | 验收描述 |
|---|---|---|
| G1 | 讲清 Maven 坐标 | 能说出坐标三要素（groupId/artifactId/version）各自是什么，类比身份证维度解释"为什么缺一个都找不到这个依赖" |
| G2 | 讲清 dependencyManagement 与 dependencies 的区别 | 能说出：父 pom 的 dependencyManagement **只管定版本不引入依赖**；子模块的 dependencies **只写依赖不写版本**；两者配合才生效 |
| G3 | 讲清模块依赖方向 | 能画出本工程真实依赖图：common / pojo 各自独立，server 依赖 common 和 pojo；能说出"为什么 pojo 不依赖 common 也合理" |
| G4 | 讲清 spring-boot-maven-plugin | 能说出：只有 server（最终可启动模块）需要它，作用是把工程打成可运行的 jar |
| G5 | 讲清为什么子模块能省版本号 | 能说出：版本号集中在根 pom 的 properties + dependencyManagement，子模块不写时向上找父工程要 |

## 二、技能（学完能做）

| # | 能力 | 验收描述 |
|---|---|---|
| S1 | 能照着参照物亲手写出 4 个 pom | 产出与参照物 diff 零差异；写的过程中能说出每个标签在干什么 |
| S2 | 能写出 SkyApplication 启动类 | 产出与参照物 diff 零差异；能说出三个注解各自作用 |
| S3 | 能新建 server 包结构骨架 | 6 个包 + .gitkeep 齐全，路径与参照物一致 |
| S4 | 能查坐标加新依赖 | 给一个不在受管列表的依赖（如某个 JSON 库），能去 Maven 中央仓库（mvnrepository.com）查出坐标三要素，知道版本号写在哪里（子模块 pom 的 dependencies 里）、怎么写 |

## 三、产出物
见 `plan.md` 产出清单：`sky-take-out/` 下 4 个 pom + SkyApplication.java + 6 个 .gitkeep 包骨架。

## 四、明确不要求（本单元边界）
- 不要求理解 Spring Boot 启动原理细节（`@SpringBootApplication` 组合注解内部、自动配置机制——单元 4 再深入）
- 不要求背出 15 个受管依赖的名称版本（会用 `<properties>` 变量管理即可，能查文档）
- 不要求配置 application.yml（单元 4）
- 不要求从零设计 pom（能对照参照物写 + 讲出道理即可）
