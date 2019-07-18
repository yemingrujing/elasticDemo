package test.refund;

import java.util.List;

import lombok.Data;

/**
 * Description.
 *
 * @author andy
 * Created at 2019/7/9 5:51 PM
 * @version com.youzan.retail.trademanager.biz.bo.exchange.SimpleRefundOrder v0.1
 */
@Data
public class SimpleRefundOrder {

    private String refundOrderId;

    private List<SimpleExchangeOrderItem> itemList;
}
