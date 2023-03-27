package com.zhengqing.mall.web.model.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.zhengqing.mall.common.model.bo.OmsOrderReceiverAddressBO;
import com.zhengqing.mall.common.model.enums.OmsOrderStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>商城-订单信息导出-响应参数</p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/08/30 13:40
 */
@HeadRowHeight(20)
@ContentRowHeight(15)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("web-商城-订单信息导出-响应参数")
public class WebOmsOrderExportVO {

    @ColumnWidth(6)
    @ExcelProperty(value = {"订单明细表", "序号"}, index = 0)
    private Integer id;

    @ColumnWidth(16)
    @ExcelProperty(value = {"订单明细表", "订单编号"}, index = 1)
    private String orderNo;

    /**
     * {@link OmsOrderStatusEnum}
     */
    @ExcelIgnore
    @ApiModelProperty("订单状态")
    private Byte orderStatus;

    @ColumnWidth(8)
    @ExcelProperty(value = {"订单明细表", "订单状态"}, index = 2)
    private String orderStatusName;

    @ColumnWidth(5)
    @ExcelProperty(value = {"订单明细表", "配送方式"}, index = 3)
    private String logisticsCode;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "物流编号"}, index = 4)
    private String logisticsNo;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "买家账号"}, index = 5)
    private String userPhone;

    /**
     * {@link OmsOrderReceiverAddressBO }
     */
    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "收货人"}, index = 6)
    private String receiverName;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "收货电话"}, index = 7)
    private String receiverPhone;

    // ========================== ↓↓↓↓↓↓ 商品信息 ↓↓↓↓↓↓ ==========================

    @ColumnWidth(12)
    @ExcelProperty(value = {"订单明细表", "商品名称"}, index = 8)
    private String spuName;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "商品类型"}, index = 9)
    private String spuTypeName;

    @ColumnWidth(15)
    @ExcelProperty(value = {"订单明细表", "规格"}, index = 10)
    private String spuAttrName;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "单价"}, index = 11)
    private String spuPrice;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "商品数量"}, index = 12)
    private Integer spuNum;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "商品状态"}, index = 13)
    private String spuStatus;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "商品金额"}, index = 14)
    private String spuItemTotalPrice;

    @ColumnWidth(12)
    @ExcelProperty(value = {"订单明细表", "是否正在售后"}, index = 15)
    private String spuIsAfterSale;

    // ========================== ↑↑↑↑↑↑ 商品信息 ↑↑↑↑↑↑ ==========================

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "商品总数量"}, index = 16)
    private Integer spuSumNum;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "商品总额"}, index = 17)
    private String spuTotalPrice;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "运费"}, index = 18)
    private String freight;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "实付金额"}, index = 19)
    private String payPrice;

    @ColumnWidth(10)
    @ExcelProperty(value = {"订单明细表", "应付金额"}, index = 20)
    private String orderTotalPrice;

    @ColumnWidth(20)
    @ExcelProperty(value = {"订单明细表", "下单时间"}, index = 21)
    private String createTime;

    @ColumnWidth(20)
    @ExcelProperty(value = {"订单明细表", "支付时间"}, index = 22)
    private String payTime;

}
