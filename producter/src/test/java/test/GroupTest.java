package test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Description.
 *
 * @author andy
 * Created at 2019/7/9 5:53 PM
 * @version com.youzan.retail.trademanager.biz.bo.exchange.GroupTest v0.1
 */
public class GroupTest {

    public static void main(String args[]) {

        // 第一个退货单
        SimpleRefundOrder simpleRefundOrder1 = new SimpleRefundOrder();
        List<SimpleExchangeOrderItem> simpleExchangeOrderItemList1 = Lists.newArrayList();
        SimpleExchangeOrderItem simpleExchangeOrderItem01 = new SimpleExchangeOrderItem();

        simpleExchangeOrderItem01.setItemId(001);
        simpleExchangeOrderItem01.setItemName("西瓜");

        SimpleExchangeOrderItem simpleExchangeOrderItem02 = new SimpleExchangeOrderItem();

        simpleExchangeOrderItem02.setItemId(002);
        simpleExchangeOrderItem02.setItemName("苹果");
        simpleExchangeOrderItemList1.add(simpleExchangeOrderItem01);
        simpleExchangeOrderItemList1.add(simpleExchangeOrderItem02);

        simpleRefundOrder1.setRefundOrderId("A01");
        simpleRefundOrder1.setItemList(simpleExchangeOrderItemList1);

        // 第二个退货单
        SimpleRefundOrder simpleRefundOrder2 = new SimpleRefundOrder();
        List<SimpleExchangeOrderItem> simpleExchangeOrderItemList2 = Lists.newArrayList();
        SimpleExchangeOrderItem simpleExchangeOrderItem03 = new SimpleExchangeOrderItem();

        simpleExchangeOrderItem03.setItemId(001);
        simpleExchangeOrderItem03.setItemName("西瓜");

        SimpleExchangeOrderItem simpleExchangeOrderItem04 = new SimpleExchangeOrderItem();

        simpleExchangeOrderItem04.setItemId(003);
        simpleExchangeOrderItem04.setItemName("桃子");
        simpleExchangeOrderItemList2.add(simpleExchangeOrderItem03);
        simpleExchangeOrderItemList2.add(simpleExchangeOrderItem04);

        simpleRefundOrder2.setRefundOrderId("A02");
        simpleRefundOrder2.setItemList(simpleExchangeOrderItemList2);

        // 两个退货单，第一个单子退了西瓜和苹果，第二个退了西瓜和桃子
        // 按商品汇总所有退货单编号
        Map map = rs(simpleRefundOrder1, simpleRefundOrder2);
        System.out.print(map);

    }

    private static Map<Integer, Set<String>> rs(SimpleRefundOrder... hellos) {
        Map<Integer, Set<String>> rs = Maps.newHashMap();
        Arrays.stream(hellos).forEach(t -> {
            t.getItemList().forEach(s -> {
//                Set<String> temp = rs.get(s.getItemId());
//
//                if (Objects.isNull(temp)) {
//                    temp = Sets.newHashSet();
//                    temp.add(t.getRefundOrderId());
//                    rs.put(s.getItemId(), temp);
//                } else {
//                    rs.get(s.getItemId()).add(t.getRefundOrderId());
//                }
                System.out.println(s.getItemId());
                Set<String> temp = Optional.ofNullable(rs.remove(s.getItemId())).orElse(Sets.newHashSet());
                temp.add(t.getRefundOrderId());
                rs.put(s.getItemId(), temp);

            });
        });
        return rs;
    }
}