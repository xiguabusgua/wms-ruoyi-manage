package com.ruoyi.common.core.constant;

public class ServiceConstants {
    /**
     * 入库单状态
     */
    public class ReceiptOrderStatus {
        public static final Integer INVALID = -1;
        public static final Integer PENDING = 0;
        public static final Integer FINISH = 1;
    }

    /**
     * 出库单状态
     */
    public class ShipmentOrderStatus {
        public static final Integer INVALID = -1;
        public static final Integer PENDING = 0;
        public static final Integer FINISH = 1;
    }

    /**
     * 库存记录操作类型
     */
    public class InventoryHistoryOrderType {
        public static final Integer RECEIPT = 1;
        public static final Integer SHIPMENT = 2;
        public static final Integer MOVEMENT = 3;
        public static final Integer CHECK = 4;
    }

    /**
     * 移库单状态
     */
    public class MovementOrderStatus {
        public static final Integer INVALID = -1;
        public static final Integer PENDING = 0;
        public static final Integer FINISH = 1;
    }

    /**
     * 盘库单状态
     */
    public class CheckOrderStatus {
        public static final Integer INVALID = -1;
        public static final Integer PENDING = 0;
        public static final Integer FINISH = 1;
    }

    /**
     * 报价单状态
     */
    public class QuotationOrderStatus {
        public static final Integer DRAFT = 0;
        public static final Integer PENDING_APPROVAL = 1;
        public static final Integer APPROVED = 2;
        public static final Integer REJECTED = 3;
        public static final Integer CONVERTED = 4;
        public static final Integer EXPIRED = 5;
    }

    public class CustomerStatus {
        public static final Integer DISABLED = 0;
        public static final Integer ENABLED = 1;
    }

    public class SalesOrderStatus {
        public static final Integer PENDING_APPROVAL = 0;
        public static final Integer APPROVED = 1;
        public static final Integer IN_PRODUCTION = 2;
        public static final Integer SHIPPED = 3;
        public static final Integer COMPLETED = 4;
        public static final Integer CANCELLED = 5;
    }

    public class ProductionPlanStatus {
        public static final Integer PENDING = 0;
        public static final Integer SCHEDULED = 1;
        public static final Integer IN_PRODUCTION = 2;
        public static final Integer COMPLETED = 3;
        public static final Integer CLOSED = 4;
    }

    public class WorkOrderStatus {
        public static final Integer PENDING = 0;
        public static final Integer IN_PROGRESS = 1;
        public static final Integer PAUSED = 2;
        public static final Integer COMPLETED = 3;
        public static final Integer CANCELLED = 4;
    }

    public class MoldStatus {
        public static final Integer NORMAL = 0;
        public static final Integer MAINTENANCE = 1;
        public static final Integer SCRAPPED = 2;
        public static final Integer BORROWED = 3;
    }

    public class MoldType {
        public static final Integer PROGRESSIVE = 1;
        public static final Integer ENGINEERING = 2;
        public static final Integer COMPOUND = 3;
        public static final Integer OTHER = 4;
    }

    public class InspectionStatus {
        public static final Integer PENDING = 0;
        public static final Integer INSPECTING = 1;
        public static final Integer PASSED = 2;
        public static final Integer FAILED = 3;
        public static final Integer CONCEDED = 4;
    }

    public class EquipmentStatus {
        public static final Integer RUNNING = 0;
        public static final Integer STOPPED = 1;
        public static final Integer MAINTENANCE = 2;
        public static final Integer FAULT = 3;
        public static final Integer SEALED = 4;
    }

    public class PurchaseRequisitionStatus {
        public static final Integer PENDING = 0;
        public static final Integer APPROVED = 1;
        public static final Integer REJECTED = 2;
        public static final Integer CLOSED = 3;
    }

    public class PurchaseOrderStatus {
        public static final Integer PENDING = 0;
        public static final Integer APPROVED = 1;
        public static final Integer PARTIAL_RECEIVED = 2;
        public static final Integer FULLY_RECEIVED = 3;
        public static final Integer CLOSED = 4;
        public static final Integer CANCELLED = 5;
    }

    public class BomStatus {
        public static final Integer DRAFT = 0;
        public static final Integer FORMAL = 1;
        public static final Integer EXPIRED = 2;
    }
}
