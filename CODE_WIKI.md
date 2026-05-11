# RuoYi-WMS 仓库管理系统 - Code Wiki

## 目录

- [1. 项目概述](#1-项目概述)
- [2. 技术栈](#2-技术栈)
- [3. 项目架构](#3-项目架构)
  - [3.1 模块划分](#31-模块划分)
  - [3.2 模块依赖关系](#32-模块依赖关系)
- [4. 核心业务模块 (ruoyi-admin-wms)](#4-核心业务模块-ruoyi-admin-wms)
  - [4.1 实体领域模型](#41-实体领域模型)
  - [4.2 控制器层 (Controller)](#42-控制器层-controller)
  - [4.3 服务层 (Service)](#43-服务层-service)
  - [4.4 数据访问层 (Mapper)](#44-数据访问层-mapper)
  - [4.5 值对象层 (VO)](#45-值对象层-vo)
  - [4.6 业务对象层 (BO)](#46-业务对象层-bo)
- [5. 通用模块 (ruoyi-common)](#5-通用模块-ruoyi-common)
- [6. 业务模块 (ruoyi-modules)](#6-业务模块-ruoyi-modules)
- [7. 数据库设计](#7-数据库设计)
- [8. 项目运行与部署](#8-项目运行与部署)
  - [8.1 环境要求](#81-环境要求)
  - [8.2 本地运行](#82-本地运行)
  - [8.3 Docker 部署](#83-docker-部署)
  - [8.4 脚本启动](#84-脚本启动)
- [9. 关键配置说明](#9-关键配置说明)
- [10. 核心业务流程](#10-核心业务流程)
- [11. 开发规范与约定](#11-开发规范与约定)
- [12. 常见问题排查](#12-常见问题排查)

---

## 1. 项目概述

**项目名称**: RuoYi-WMS (Warehouse Management System)

**版本**: 5.2.0

**描述**: RuoYi-WMS 是一套基于若依框架的仓库管理系统（WMS），支持多仓库管理、入库/出库/移库/盘库等核心仓储业务流程。系统支持网页打印和 LODOP 打印入库单、出库单，面向个人及企业免费开源可商用。

**主要功能**:
- **首页看板**: 库存预警、到期提醒、基础数据报表
- **仓库/库区管理**: 维护仓库基础数据
- **物料管理**: 维护物料及 SKU 基础数据
- **客户/供应商管理**: 维护往来单位联系人
- **入库管理**: 支持采购入库、外协入库、退货入库，单据支持网页打印
- **出库管理**: 支持销售出库、外协出库、调拨出库，单据支持网页打印
- **移库管理**: 物料在不同仓库/库区之间的转移
- **盘库管理**: 库存盘点，支持账实核对
- **库存看板**: 按仓库、库区、商品维度查看实时库存
- **库存记录**: 追踪物料库存操作流水
- **库存明细**: 按仓库库区、商品维度查看存放明细

**前端项目**:
- Gitee: [https://gitee.com/zccbbg/ruo-yi-wms-vue](https://gitee.com/zccbbg/ruo-yi-wms-vue)
- GitHub: [https://github.com/zccbbg/RuoYi-WMS-VUE](https://github.com/zccbbg/RuoYi-WMS-VUE)

---

## 2. 技术栈

| 类别 | 技术 |
|------|------|
| **JDK** | 17 |
| **框架** | Spring Boot 3.2.6 |
| **ORM** | MyBatis-Plus 3.5.6 |
| **权限认证** | Sa-Token 1.37.0 + JWT |
| **缓存** | Redis + Redisson 3.29.0 |
| **数据库** | MySQL 8.x |
| **连接池** | HikariCP |
| **多数据源** | dynamic-datasource 4.3.0 |
| **分布式锁** | Lock4j 2.2.7 |
| **工具库** | Hutool 5.8.27 |
| **对象映射** | MapStruct-Plus 1.3.6 |
| **Excel** | EasyExcel 3.3.4 / POI 5.2.3 |
| **API 文档** | SpringDoc (OpenAPI 3) |
| **日志** | Logback-Plus |
| **SQL 监控** | P6Spy |
| **代码生成** | Velocity 2.3 |
| **构建工具** | Maven |

---

## 3. 项目架构

### 3.1 模块划分

```
ruoyi-wms (根项目)
├── ruoyi-admin-wms          # Web 服务入口，WMS 核心业务
├── ruoyi-common             # 通用工具模块集合
│   ├── ruoyi-common-bom     # BOM 依赖管理
│   ├── ruoyi-common-core    # 核心工具、异常、常量
│   ├── ruoyi-common-web     # Web 基础封装（全局异常、响应体）
│   ├── ruoyi-common-mybatis # MyBatis-Plus 配置与基类
│   ├── ruoyi-common-satoken # Sa-Token 权限认证
│   ├── ruoyi-common-security# 安全相关
│   ├── ruoyi-common-redis   # Redis & Redisson 配置
│   ├── ruoyi-common-excel   # Excel 导入导出
│   ├── ruoyi-common-log     # 操作日志
│   ├── ruoyi-common-mail    # 邮件发送
│   ├── ruoyi-common-oss     # 对象存储
│   ├── ruoyi-common-sms     # 短信服务
│   ├── ruoyi-common-doc     # API 文档 (SpringDoc)
│   ├── ruoyi-common-encrypt # 数据加密
│   ├── ruoyi-common-sensitive# 敏感词过滤
│   ├── ruoyi-common-json    # JSON 处理
│   ├── ruoyi-common-translation# 翻译组件
│   ├── ruoyi-common-idempotent# 幂等性控制
│   └── ruoyi-common-ratelimiter# 限流
└── ruoyi-modules            # 业务功能模块
    ├── ruoyi-system         # 系统管理（用户、角色、菜单、部门等）
    ├── ruoyi-generator      # 代码生成器
    └── ruoyi-demo           # 示例演示模块
```

### 3.2 模块依赖关系

```
ruoyi-admin-wms (入口)
├── ruoyi-system
│   └── ruoyi-common-mybatis (依赖 common-core, common-redis, common-satoken)
├── ruoyi-common-oss
├── ruoyi-common-mail
├── ruoyi-common-ratelimiter
└── ruoyi-generator
```

**依赖方向**: `ruoyi-admin-wms` → `ruoyi-modules` → `ruoyi-common`

---

## 4. 核心业务模块 (ruoyi-admin-wms)

### 4.1 实体领域模型

**包路径**: `com.ruoyi.wms.domain.entity`

#### 4.1.1 基础单据实体

| 类名 | 说明 | 表名 |
|------|------|------|
| `BaseOrder` | 所有单据的基类，包含单号、总金额、订单状态等公共字段 | - |

#### 4.1.2 单据实体

| 类名 | 说明 | 表名 | 继承关系 |
|------|------|------|----------|
| `ReceiptOrder` | 入库单 | `wms_receipt_order` | BaseOrder |
| `ShipmentOrder` | 出库单 | `wms_shipment_order` | BaseOrder |
| `MovementOrder` | 移库单 | `wms_movement_order` | BaseOrder |
| `CheckOrder` | 盘库单 | `wms_check_order` | BaseOrder |

#### 4.1.3 单据明细实体

| 类名 | 说明 | 表名 |
|------|------|------|
| `BaseOrderDetail` | 单据明细基类 | - |
| `ReceiptOrderDetail` | 入库单明细 | `wms_receipt_order_detail` |
| `ShipmentOrderDetail` | 出库单明细 | `wms_shipment_order_detail` |
| `MovementOrderDetail` | 移库单明细 | `wms_movement_order_detail` |
| `CheckOrderDetail` | 盘库单明细 | `wms_check_order_detail` |

#### 4.1.4 基础数据实体

| 类名 | 说明 | 表名 |
|------|------|------|
| `Warehouse` | 仓库 | `wms_warehouse` |
| `Item` | 物料（商品） | `wms_item` |
| `ItemSku` | 物料规格 | `wms_item_sku` |
| `ItemBrand` | 物料品牌 | `wms_item_brand` |
| `ItemCategory` | 物料分类 | `wms_item_category` |
| `Merchant` | 往来单位（客户/供应商） | `wms_merchant` |

#### 4.1.5 库存相关实体

| 类名 | 说明 | 表名 |
|------|------|------|
| `Inventory` | 当前库存 | `wms_inventory` |
| `InventoryHistory` | 库存流水记录 | `wms_inventory_history` |

#### 4.1.6 领域模型类图关系

```
BaseOrder (抽象基类)
├── id, orderNo, totalQuantity, totalAmount, orderStatus, remark
│
├── ReceiptOrder (入库单)
│   └── warehouseId, optType, bizOrderNo, merchantId
│       └── ReceiptOrderDetail [] (明细)
│
├── ShipmentOrder (出库单)
│   └── warehouseId, optType, bizOrderNo, merchantId
│       └── ShipmentOrderDetail [] (明细)
│
├── MovementOrder (移库单)
│   └── sourceWarehouseId, targetWarehouseId
│       └── MovementOrderDetail [] (明细)
│
└── CheckOrder (盘库单)
    └── warehouseId
        └── CheckOrderDetail [] (明细)

Inventory (库存)
└── skuId, warehouseId, quantity

InventoryHistory (库存流水)
└── orderId, orderNo, orderType, skuId, quantity, beforeQuantity, afterQuantity, warehouseId
```

### 4.2 控制器层 (Controller)

**包路径**: `com.ruoyi.wms.controller`

所有 Controller 均继承 `BaseController`，使用以下统一约定：

- **权限注解**: `@SaCheckPermission("wms:xxx:xxx")`
- **日志注解**: `@Log(title = "xxx", businessType = BusinessType.XXX)`
- **防重复提交**: `@RepeatSubmit()`
- **参数校验**: `@Validated(AddGroup.class)` / `@Validated(EditGroup.class)`

| Controller | 路径前缀 | 职责 |
|------------|----------|------|
| `ReceiptOrderController` | `/wms/receiptOrder` | 入库单 CRUD、暂存、入库 |
| `ShipmentOrderController` | `/wms/shipmentOrder` | 出库单 CRUD、暂存、出库 |
| `MovementOrderController` | `/wms/movementOrder` | 移库单 CRUD、移库操作 |
| `CheckOrderController` | `/wms/checkOrder` | 盘库单 CRUD、盘点操作 |
| `InventoryController` | `/wms/inventory` | 库存查询、库存看板 |
| `InventoryHistoryController` | `/wms/inventoryHistory` | 库存流水记录查询 |
| `ItemController` | `/wms/item` | 物料 CRUD |
| `ItemSkuController` | `/wms/itemSku` | 物料规格 CRUD |
| `ItemBrandController` | `/wms/itemBrand` | 物料品牌 CRUD |
| `ItemCategoryController` | `/wms/itemCategory` | 物料分类 CRUD |
| `MerchantController` | `/wms/merchant` | 往来单位 CRUD |
| `WarehouseController` | `/wms/warehouse` | 仓库 CRUD |
| `ReceiptOrderDetailController` | `/wms/receiptOrderDetail` | 入库单明细 CRUD |
| `ShipmentOrderDetailController` | `/wms/shipmentOrderDetail` | 出库单明细 CRUD |
| `MovementOrderDetailController` | `/wms/movementOrderDetail` | 移库单明细 CRUD |
| `CheckOrderDetailController` | `/wms/checkOrderDetail` | 盘库单明细 CRUD |

#### 典型 Controller 方法模式

```java
// 分页查询
@GetMapping("/list")
public TableDataInfo<XxxVo> list(XxxBo bo, PageQuery pageQuery)

// 导出
@PostMapping("/export")
public void export(XxxBo bo, HttpServletResponse response)

// 详情
@GetMapping("/{id}")
public R<XxxVo> getInfo(@PathVariable Long id)

// 新增/暂存
@PostMapping()
public R<Long> add(@Validated(AddGroup.class) @RequestBody XxxBo bo)

// 核心业务操作（入库/出库/移库/盘库）
@PostMapping("/warehousing")
public R<Void> doWarehousing(@Validated(AddGroup.class) @RequestBody XxxBo bo)

// 修改
@PutMapping()
public R<Void> edit(@Validated(EditGroup.class) @RequestBody XxxBo bo)

// 删除
@DeleteMapping("/{id}")
public R<Void> remove(@PathVariable Long id)
```

### 4.3 服务层 (Service)

**包路径**: `com.ruoyi.wms.service`

| Service | 职责 | 核心方法 |
|---------|------|----------|
| `ReceiptOrderService` | 入库单业务处理 | `receive()` - 入库；`insertByBo()` - 暂存；`updateByBo()` - 修改；`editToInvalid()` - 作废 |
| `ShipmentOrderService` | 出库单业务处理 | `shipment()` - 出库；`insertByBo()` - 暂存；`updateByBo()` - 修改 |
| `MovementOrderService` | 移库单业务处理 | `move()` - 移库；`insertByBo()` - 暂存；`updateByBo()` - 修改 |
| `CheckOrderService` | 盘库单业务处理 | `check()` - 盘点；`insertByBo()` - 暂存；`updateByBo()` - 修改 |
| `InventoryService` | 库存管理 | `add()` - 增加库存；`subtract()` - 扣减库存；`updateInventory()` - 盘点更新库存 |
| `InventoryHistoryService` | 库存流水记录 | `saveInventoryHistory()` - 保存库存变更流水 |
| `ItemService` | 物料管理 | 标准 CRUD |
| `ItemSkuService` | 物料规格管理 | 标准 CRUD；`queryItemSkuMapVo()` - 查询规格映射 |
| `ItemBrandService` | 物料品牌管理 | 标准 CRUD |
| `ItemCategoryService` | 物料分类管理 | 标准 CRUD |
| `MerchantService` | 往来单位管理 | 标准 CRUD |
| `WarehouseService` | 仓库管理 | 标准 CRUD |

#### 核心业务方法详解

**ReceiptOrderService.receive() - 入库流程**:
1. 校验：商品明细不能为空、入库单号唯一
2. 保存/更新入库单及明细
3. 调用 `InventoryService.add()` 增加库存
4. 调用 `InventoryHistoryService.saveInventoryHistory()` 记录流水

**ShipmentOrderService.shipment() - 出库流程**:
1. 校验：商品明细不能为空
2. 保存/更新出库单及明细
3. 调用 `InventoryService.subtract()` 扣减库存（含库存不足校验）
4. 调用 `InventoryHistoryService.saveInventoryHistory()` 记录流水

**MovementOrderService.move() - 移库流程**:
1. 校验：商品明细不能为空
2. 保存/更新移库单及明细
3. 从源仓库扣减库存
4. 向目标仓库增加库存
5. 分别记录扣减和增加的库存流水

**CheckOrderService.check() - 盘点流程**:
1. 保存/更新盘库单及明细
2. 调用 `InventoryService.updateInventory()` 更新账面库存（含账实核对校验）
3. 过滤有差异的明细，记录库存流水

**InventoryService.add() - 增加库存**:
- 按 `warehouseId + skuId` 查询现有库存
- 存在则更新数量，不存在则新增库存记录
- 设置 `beforeQuantity` 和 `afterQuantity`

**InventoryService.subtract() - 扣减库存**:
- 按 `warehouseId + skuId` 查询现有库存
- 无库存或库存不足时抛出 `ServiceException`
- 更新库存数量，设置 `beforeQuantity` 和 `afterQuantity`

### 4.4 数据访问层 (Mapper)

**包路径**: `com.ruoyi.wms.mapper`

所有 Mapper 继承 `BaseMapperPlus<Entity, VO>`，自动具备以下能力：
- 基础 CRUD
- `selectVoById()` / `selectVoList()` / `selectVoPage()` - 直接返回 VO 对象

| Mapper | 对应实体 | XML 映射文件 |
|--------|----------|-------------|
| `ReceiptOrderMapper` | `ReceiptOrder` | `ReceiptOrderMapper.xml` |
| `ShipmentOrderMapper` | `ShipmentOrder` | `ShipmentOrderMapper.xml` |
| `MovementOrderMapper` | `MovementOrder` | `MovementOrderMapper.xml` |
| `CheckOrderMapper` | `CheckOrder` | `CheckOrderMapper.xml` |
| `ReceiptOrderDetailMapper` | `ReceiptOrderDetail` | `ReceiptOrderDetailMapper.xml` |
| `ShipmentOrderDetailMapper` | `ShipmentOrderDetail` | `ShipmentOrderDetailMapper.xml` |
| `MovementOrderDetailMapper` | `MovementOrderDetail` | `MovementOrderDetailMapper.xml` |
| `CheckOrderDetailMapper` | `CheckOrderDetail` | `CheckOrderDetailMapper.xml` |
| `InventoryMapper` | `Inventory` | `InventoryMapper.xml` |
| `InventoryHistoryMapper` | `InventoryHistory` | `InventoryHistoryMapper.xml` |
| `ItemMapper` | `Item` | `ItemMapper.xml` |
| `ItemSkuMapper` | `ItemSku` | `ItemSkuMapper.xml` |
| `ItemBrandMapper` | `ItemBrand` | `ItemBrandMapper.xml` |
| `ItemCategoryMapper` | `ItemCategory` | `ItemCategoryMapper.xml` |
| `MerchantMapper` | `Merchant` | `MerchantMapper.xml` |
| `WarehouseMapper` | `Warehouse` | `WarehouseMapper.xml` |

**XML 映射文件路径**: `src/main/resources/mapper/wms/`

### 4.5 值对象层 (VO)

**包路径**: `com.ruoyi.wms.domain.vo`

VO (View Object) 用于接口响应数据返回，与数据库表结构解耦。

| VO | 说明 |
|----|------|
| `ReceiptOrderVo` | 入库单视图对象（含 details 明细列表） |
| `ShipmentOrderVo` | 出库单视图对象（含 details 明细列表） |
| `MovementOrderVo` | 移库单视图对象（含 details 明细列表） |
| `CheckOrderVo` | 盘库单视图对象（含 details 明细列表） |
| `InventoryVo` | 库存视图对象 |
| `InventoryHistoryVo` | 库存流水视图对象 |
| `ItemVo` | 物料视图对象 |
| `ItemSkuVo` | 物料规格视图对象 |
| `ItemSkuMapVo` | 规格映射视图对象（含 Item + ItemSku） |
| `ItemTypeTreeSelectVo` | 物料分类树形选择对象 |
| `MerchantVo` | 往来单位视图对象 |
| `WarehouseVo` | 仓库视图对象 |
| `ItemBrandVo` | 物料品牌视图对象 |
| `ItemCategoryVo` | 物料分类视图对象 |

### 4.6 业务对象层 (BO)

**包路径**: `com.ruoyi.wms.domain.bo`

BO (Business Object) 用于接收前端请求参数，包含校验注解。

| BO | 说明 |
|----|------|
| `ReceiptOrderBo` | 入库单业务对象（含 details 明细列表） |
| `ShipmentOrderBo` | 出库单业务对象（含 details 明细列表） |
| `MovementOrderBo` | 移库单业务对象（含 details 明细列表） |
| `CheckOrderBo` | 盘库单业务对象（含 details 明细列表） |
| `BaseOrderBo<T>` | 单据业务对象泛型基类 |
| `InventoryBo` | 库存业务对象 |
| `InventoryHistoryBo` | 库存流水业务对象 |
| `ItemBo` | 物料业务对象 |
| `ItemSkuBo` | 物料规格业务对象 |
| `MerchantBo` | 往来单位业务对象 |
| `WarehouseBo` | 仓库业务对象 |

**数据流转**: `BO (请求) → Service (业务处理, MapStruct 转换) → Entity (数据库操作) → VO (响应返回)`

---

## 5. 通用模块 (ruoyi-common)

| 模块 | 职责 | 关键依赖 |
|------|------|----------|
| `ruoyi-common-core` | 核心工具类、异常体系、常量定义、统一响应体 `R<T>` | Hutool, Spring Context, Sa-Token |
| `ruoyi-common-web` | Web 层封装：全局异常处理器、基础控制器 `BaseController` | Spring Web |
| `ruoyi-common-mybatis` | MyBatis-Plus 配置：`BaseMapperPlus`, `BaseEntity`, 分页插件 | MyBatis-Plus, P6Spy |
| `ruoyi-common-satoken` | Sa-Token 权限认证配置 | Sa-Token |
| `ruoyi-common-redis` | Redis 和 Redisson 配置 | Redisson |
| `ruoyi-common-security` | 安全相关配置 | Spring Security |
| `ruoyi-common-excel` | Excel 导入导出（EasyExcel） | EasyExcel |
| `ruoyi-common-log` | 操作日志注解 `@Log` 和切面 | Spring AOP |
| `ruoyi-common-doc` | SpringDoc OpenAPI 3 文档配置 | SpringDoc |
| `ruoyi-common-mail` | 邮件发送 | Jakarta Mail |
| `ruoyi-common-oss` | 对象存储（AWS S3） | AWS SDK S3 |
| `ruoyi-common-sms` | 短信服务（阿里云、腾讯云等） | SMS4J |
| `ruoyi-common-encrypt` | 数据加密（AES、SM4、RSA 等） | Bouncy Castle |
| `ruoyi-common-sensitive` | 敏感词过滤 | Hutool |
| `ruoyi-common-json` | JSON 处理 | Jackson |
| `ruoyi-common-translation` | 翻译组件（字典翻译等） | - |
| `ruoyi-common-idempotent` | 幂等性控制（防重复提交） | Redis |
| `ruoyi-common-ratelimiter` | 限流（Redis RateLimiter） | Redis |

---

## 6. 业务模块 (ruoyi-modules)

### 6.1 ruoyi-system - 系统管理模块

**包路径**: `com.ruoyi.system`

负责系统基础管理功能：

| 功能 | Service | Mapper |
|------|---------|--------|
| 用户管理 | `SysUserService` | `SysUserMapper` |
| 角色管理 | `SysRoleService` | `SysRoleMapper` |
| 菜单管理 | `SysMenuService` | `SysMenuMapper` |
| 部门管理 | `SysDeptService` | `SysDeptMapper` |
| 岗位管理 | `SysPostService` | `SysPostMapper` |
| 字典管理 | `SysDictTypeService` / `SysDictDataService` | `SysDictTypeMapper` / `SysDictDataMapper` |
| 参数配置 | `SysConfigService` | `SysConfigMapper` |
| 通知公告 | `SysNoticeService` | `SysNoticeMapper` |
| 操作日志 | `SysOperLogService` | `SysOperLogMapper` |
| 登录日志 | `SysLogininforService` | `SysLogininforMapper` |
| 文件管理 | `SysOssService` / `SysOssConfigService` | `SysOssMapper` / `SysOssConfigMapper` |
| 登录服务 | `SysLoginService` | - |
| 注册服务 | `SysRegisterService` | - |
| 权限服务 | `SysPermissionService` | - |
| 数据权限 | `SysDataScopeService` | - |
| 敏感词服务 | `SysSensitiveService` | - |

### 6.2 ruoyi-generator - 代码生成器

**包路径**: `com.ruoyi.generator`

基于 Velocity 模板的代码生成器，支持一键生成：
- Java: Controller, Service, Mapper, Domain, BO, VO
- Vue: index.vue, index-tree.vue, api.js
- SQL: 建表语句
- XML: MyBatis Mapper XML

| 类 | 职责 |
|----|------|
| `GenController` | 代码生成控制器 |
| `GenTable` / `GenTableColumn` | 代码生成表域模型 |
| `GenTableServiceImpl` | 代码生成服务实现 |
| `GenUtils` | 代码生成工具类 |
| `VelocityUtils` | Velocity 模板工具 |

### 6.3 ruoyi-demo - 示例模块

**包路径**: `com.ruoyi.demo`

提供各通用功能的使用示例：
- 邮件发送、短信发送
- Redis 缓存、Redis 分布式锁、Redis 发布订阅、Redis 限流
- Excel 导入导出
- 数据加密/解密
- 批量操作
- 树形结构操作
- 敏感词过滤
- Swagger 3 示例
- 国际化示例

---

## 7. 数据库设计

### 7.1 核心业务表

| 表名 | 说明 | 主键策略 |
|------|------|----------|
| `wms_warehouse` | 仓库表 | 雪花 ID |
| `wms_item` | 物料表 | 雪花 ID |
| `wms_item_sku` | 物料规格表 | 雪花 ID |
| `wms_item_brand` | 物料品牌表 | 雪花 ID |
| `wms_item_category` | 物料分类表 | 雪花 ID |
| `wms_merchant` | 往来单位表（客户/供应商） | 雪花 ID |
| `wms_receipt_order` | 入库单表 | 雪花 ID |
| `wms_receipt_order_detail` | 入库单明细表 | 雪花 ID |
| `wms_shipment_order` | 出库单表 | 雪花 ID |
| `wms_shipment_order_detail` | 出库单明细表 | 雪花 ID |
| `wms_movement_order` | 移库单表 | 雪花 ID |
| `wms_movement_order_detail` | 移库单明细表 | 雪花 ID |
| `wms_check_order` | 盘库单表 | 雪花 ID |
| `wms_check_order_detail` | 盘库单明细表 | 雪花 ID |
| `wms_inventory` | 库存表 | 雪花 ID |
| `wms_inventory_history` | 库存流水表 | 雪花 ID |

### 7.2 关键实体关系

```
Warehouse (1) ←→ (*) Inventory (*) ←→ (1) ItemSku
                                              ↓
                                            (1) Item
                                              ↓
                                        ItemCategory
                                        ItemBrand

Warehouse (1) ←→ (*) ReceiptOrder (1) ←→ (*) ReceiptOrderDetail
ShipmentOrder (1) ←→ (*) ShipmentOrderDetail
MovementOrder (1) ←→ (*) MovementOrderDetail
CheckOrder (1) ←→ (*) CheckOrderDetail

Merchant (1) ←→ (*) ReceiptOrder / ShipmentOrder

InventoryHistory ← ReceiptOrder / ShipmentOrder / MovementOrder / CheckOrder
```

### 7.3 初始化 SQL

数据库初始化脚本位于: `script/sql/wms.sql`

---

## 8. 项目运行与部署

### 8.1 环境要求

- **JDK**: 17+
- **Maven**: 3.8+
- **MySQL**: 8.0+
- **Redis**: 5.0+
- **Node.js**: 16+ (前端项目)

### 8.2 本地运行

#### 1. 数据库初始化

```bash
# 创建数据库
CREATE DATABASE `ry-vue` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# 执行初始化脚本
mysql -u root -p ry-vue < script/sql/wms.sql
```

#### 2. 修改配置

编辑 `ruoyi-admin-wms/src/main/resources/application-dev.yml`：

```yaml
spring:
  datasource:
    dynamic:
      datasource:
        master:
          url: jdbc:mysql://localhost:3306/ry-vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&autoReconnect=true&rewriteBatchedStatements=true
          username: root
          password: your_password
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
```

#### 3. 构建并启动

```bash
# Maven 构建
mvn clean install -DskipTests

# 启动服务
java -jar ruoyi-admin-wms/target/ruoyi-admin-wms.jar
```

或者在 IDE 中直接运行 `RuoYiApplication.main()`。

#### 4. 访问

- 后端服务: `http://localhost:8080`
- API 文档: `http://localhost:8080/v3/api-docs`

### 8.3 Docker 部署

项目提供 `Dockerfile`：

```bash
# 构建
mvn clean package -DskipTests

# Docker 构建
docker build -t ruoyi-wms .

# 运行
docker run -d -p 8080:8080 --name ruoyi-wms ruoyi-wms
```

### 8.4 脚本启动

项目提供启动脚本 `script/bin/ry.sh`：

```bash
# 将 JAR 包放置到脚本同级目录
cp ruoyi-admin-wms/target/ruoyi-admin.jar ./ruoyi-admin.jar

# 启动
./ry.sh start

# 停止
./ry.sh stop

# 重启
./ry.sh restart

# 查看状态
./ry.sh status
```

Windows 用户可使用 `script/bin/ry.bat`。

---

## 9. 关键配置说明

### 9.1 应用配置 (application.yml)

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `server.port` | 8080 | 服务端口 |
| `sa-token.token-name` | Authorization | Token 请求头名称 |
| `sa-token.timeout` | 86400 | Token 有效期（24小时） |
| `sa-token.is-concurrent` | true | 是否允许并发登录 |
| `sa-token.jwt-secret-key` | abcdefghijklmnopqrstuvwxyz | JWT 密钥 |
| `mybatis-plus.mapperPackage` | com.ruoyi.**.mapper | Mapper 扫描包 |
| `mybatis-plus.mapperLocations` | classpath*:mapper/**/*Mapper.xml | XML 文件位置 |
| `mybatis-plus.dbConfig.idType` | ASSIGN_ID | 主键策略（雪花 ID） |
| `mybatis-plus.dbConfig.logicDeleteValue` | 1 | 逻辑删除值 |
| `springdoc.api-docs.enabled` | true | 是否开启接口文档 |

### 9.2 多环境配置

| Profile | 配置文件 | 说明 |
|---------|----------|------|
| `dev` | `application-dev.yml` | 开发环境（默认激活） |
| `local` | `application-local.yml` | 本地环境 |
| `prod` | `application-prod.yml` | 生产环境 |

### 9.3 密码安全配置

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| `user.password.maxRetryCount` | 5 | 密码最大错误次数 |
| `user.password.lockTime` | 10 | 密码锁定时间（分钟） |

---

## 10. 核心业务流程

### 10.1 入库流程

```
1. 前端创建入库单 (ReceiptOrderBo)
   ↓
2. Controller 接收请求
   ├─ POST /wms/receiptOrder         → 暂存 (insertByBo)
   └─ POST /wms/receiptOrder/warehousing → 入库 (receive)
   ↓
3. Service 层处理
   ├─ 校验入库单号唯一性
   ├─ 保存/更新入库单
   ├─ 保存入库单明细
   ├─ InventoryService.add()       → 增加库存
   └─ InventoryHistoryService.saveInventoryHistory() → 记录流水
   ↓
4. 更新入库单状态为"已完成"
```

### 10.2 出库流程

```
1. 前端创建出库单 (ShipmentOrderBo)
   ↓
2. Controller 接收请求
   ├─ POST /wms/shipmentOrder         → 暂存 (insertByBo)
   └─ POST /wms/shipmentOrder/shipment → 出库 (shipment)
   ↓
3. Service 层处理
   ├─ 校验商品明细不为空
   ├─ 保存/更新出库单
   ├─ 保存出库单明细
   ├─ InventoryService.subtract()    → 扣减库存（含库存不足校验）
   └─ InventoryHistoryService.saveInventoryHistory() → 记录流水
   ↓
4. 更新出库单状态为"已完成"
```

### 10.3 移库流程

```
1. 前端创建移库单 (MovementOrderBo)，指定源仓库和目标仓库
   ↓
2. Controller 接收请求
   ├─ POST /wms/movementOrder         → 暂存 (insertByBo)
   └─ POST /wms/movementOrder/move    → 移库 (move)
   ↓
3. Service 层处理
   ├─ 校验商品明细不为空
   ├─ 保存/更新移库单及明细
   ├─ 从源仓库扣减库存 (InventoryService.subtract)
   ├─ 向目标仓库增加库存 (InventoryService.add)
   ├─ 记录源仓库扣减流水
   └─ 记录目标仓库增加流水
```

### 10.4 盘库流程

```
1. 前端创建盘库单 (CheckOrderBo)，填写实际盘点数量
   ↓
2. Controller 接收请求
   ├─ POST /wms/checkOrder         → 暂存 (insertByBo)
   └─ POST /wms/checkOrder/check   → 盘点 (check)
   ↓
3. Service 层处理
   ├─ 保存/更新盘库单及明细
   ├─ InventoryService.updateInventory() → 更新账面库存
   │  ├─ 校验账面库存是否匹配
   │  ├─ 数量变化 → 更新库存
   │  └─ 新物料 → 新增库存记录
   └─ 过滤差异明细，记录库存流水
```

### 10.5 库存变更与流水记录

所有库存变更操作（入库、出库、移库、盘库）都会同步记录到 `wms_inventory_history` 表：

- **orderType**: 标识操作类型（入库、出库、移库、盘库）
- **quantity**: 正数表示增加，负数表示减少
- **beforeQuantity**: 变更前数量
- **afterQuantity**: 变更后数量

---

## 11. 开发规范与约定

### 11.1 分层架构

```
Controller → Service → Mapper
   ↓           ↓         ↓
   BO         Entity    XML
   ↓
   VO
```

### 11.2 命名规范

- **Controller**: `XxxController`
- **Service**: `XxxService`
- **Mapper**: `XxxMapper`
- **Entity**: `Xxx`（对应数据库表 `wms_xxx`）
- **VO**: `XxxVo`
- **BO**: `XxxBo`

### 11.3 对象转换

使用 **MapStruct-Plus** 进行对象转换：

```java
// BO → Entity
Xxx entity = MapstructUtils.convert(bo, Xxx.class);
// Entity → VO（由 BaseMapperPlus.selectVoXxx 自动处理）
```

### 11.4 事务管理

涉及多表操作的方法使用 `@Transactional` 注解：

```java
@Transactional
public void receive(ReceiptOrderBo bo) { ... }
```

### 11.5 异常处理

- `ServiceException`: 业务异常，可携带 HTTP 状态码和详细信息
- `BaseException`: 基础异常

### 11.6 主键策略

统一使用 **雪花 ID**（ASSIGN_ID），在 `mybatis-plus.dbConfig.idType` 中配置。

### 11.7 逻辑删除

- 逻辑删除值: `1`
- 逻辑未删除值: `0`
- 字段名: `del_flag`（默认约定）

---

## 12. 常见问题排查

### 12.1 启动失败

| 现象 | 原因 | 解决方式 |
|------|------|----------|
| MySQL 连接失败 | 数据库未启动或配置错误 | 检查 `application-dev.yml` 数据库配置 |
| Redis 连接失败 | Redis 未启动 | 启动 Redis 服务 |
| 端口被占用 | 8080 端口被占用 | 修改 `server.port` 配置 |

### 12.2 运行时问题

| 现象 | 原因 | 解决方式 |
|------|------|----------|
| 库存不足 | 出库时库存数量不够 | 检查 `wms_inventory` 表数据 |
| 单据号重复 | 入库单/出库单号已存在 | 更换唯一单号 |
| 权限不足 | 用户无对应权限 | 在系统管理中分配角色权限 |

### 12.3 SQL 监控

开发环境启用了 P6Spy，SQL 执行日志输出到控制台，便于调试：

```yaml
spring:
  datasource:
    dynamic:
      p6spy: true  # 开发环境开启
```

### 12.4 日志查看

- 控制台日志: 标准输出
- 文件日志: `./logs/sys-console.log`
- 日志配置: `logback-plus.xml`

---

## 附录

### A. 项目目录结构

```
ruoyi-wms/
├── ruoyi-admin-wms/           # Web 入口模块
│   ├── src/main/java/com/ruoyi/
│   │   ├── RuoYiApplication.java    # 启动类
│   │   └── wms/                     # WMS 核心业务
│   │       ├── controller/          # 控制器
│   │       ├── domain/              # 领域模型
│   │       │   ├── bo/              # 业务对象
│   │       │   ├── entity/          # 实体
│   │       │   └── vo/              # 视图对象
│   │       ├── mapper/              # 数据访问
│   │       └── service/             # 业务逻辑
│   ├── src/main/resources/
│   │   ├── mapper/wms/              # MyBatis XML
│   │   ├── application.yml          # 主配置
│   │   └── application-dev.yml      # 开发环境配置
│   ├── Dockerfile                   # Docker 构建文件
│   └── pom.xml
├── ruoyi-common/               # 通用模块
│   └── ruoyi-common-*/          # 各通用子模块
├── ruoyi-modules/              # 业务模块
│   ├── ruoyi-system/           # 系统管理
│   ├── ruoyi-generator/        # 代码生成
│   └── ruoyi-demo/             # 示例演示
├── script/
│   ├── bin/                    # 启动脚本
│   │   ├── ry.sh              # Linux 启动脚本
│   │   └── ry.bat             # Windows 启动脚本
│   └── sql/
│       └── wms.sql             # 数据库初始化脚本
├── docs/                       # 文档与截图
├── pom.xml                     # Maven 父 POM
├── README.md                   # 项目说明
└── .editorconfig               # 编辑器配置
```

### B. 关键类索引

| 类名 | 路径 | 职责 |
|------|------|------|
| `RuoYiApplication` | `ruoyi-admin-wms/src/main/java/com/ruoyi/RuoYiApplication.java` | Spring Boot 启动入口 |
| `BaseController` | `ruoyi-common-web` | 控制器基类 |
| `BaseMapperPlus` | `ruoyi-common-mybatis` | Mapper 基类 |
| `BaseEntity` | `ruoyi-common-mybatis` | 实体基类 |
| `MapstructUtils` | `ruoyi-common-core` | 对象转换工具 |
| `TableDataInfo` | `ruoyi-common-mybatis` | 分页响应封装 |
| `R` | `ruoyi-common-core` | 统一响应体 |

### C. 分支说明

| 分支 | JDK | 前端 | 特性 |
|------|-----|------|------|
| `lite` | 17 | Vue 3 | 支持多仓库，无库区概念，操作简单 |
| `advance` | 17 | Vue 3 | 支持多仓库、多库区，记录生产日期、过期日期、SN |
| `v1` | 8 | Vue 2 | 旧版本 |
| `master` | 17 | Vue 3 | 同步 lite 分支 |
