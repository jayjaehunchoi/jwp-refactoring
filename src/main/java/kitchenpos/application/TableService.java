package kitchenpos.application;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableService {
    private final OrderDao orderDao;
    private final OrderTableDao orderTableDao;

    public TableService(final OrderDao orderDao, final OrderTableDao orderTableDao) {
        this.orderDao = orderDao;
        this.orderTableDao = orderTableDao;
    }

    @Transactional
    public OrderTable create(final OrderTable orderTable) {
        orderTable.setId(null);
        orderTable.setTableGroupId(null);

        return orderTableDao.save(orderTable);
    }

    public List<OrderTable> list() {
        return orderTableDao.findAll();
    }

    @Transactional
    public OrderTable changeEmpty(final Long orderTableId, final OrderTable orderTable) {
        final OrderTable savedOrderTable = orderTableDao.findById(orderTableId)
                .orElseThrow(() -> new IllegalArgumentException("주문 테이블을 찾을 수 없습니다."));

        if (Objects.nonNull(savedOrderTable.getTableGroupId())) {
            throw new IllegalArgumentException("이미 테이블 그룹에 속해있습니다.");
        }

        if (orderDao.existsByOrderTableIdAndOrderStatusIn(
                orderTableId, Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name()))) {
            throw new IllegalArgumentException("현재 조리 / 식사 중입니다.");
        }

        savedOrderTable.setEmpty(orderTable.isEmpty());

        return orderTableDao.save(savedOrderTable);
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final Long orderTableId, final OrderTable orderTable) {
        final int numberOfGuests = orderTable.getNumberOfGuests();

        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("테이블 인원은 음수일 수 없습니다.");
        }

        final OrderTable savedOrderTable = orderTableDao.findById(orderTableId)
                .orElseThrow(() -> new IllegalArgumentException("주문 테이블을 찾을 수 없습니다."));

        if (savedOrderTable.isEmpty()) {
            throw new IllegalArgumentException("테이블이 비어있을 수 없습니다.");
        }

        savedOrderTable.setNumberOfGuests(numberOfGuests);

        return orderTableDao.save(savedOrderTable);
    }
}
