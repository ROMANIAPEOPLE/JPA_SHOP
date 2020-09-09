package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;
    @Test
    public void 상품주문() throws  Exception{
        //given
        Member member = createMember();

        Book book = createBook(10000, "시골 JPA", 10);
        int orderCount=2;

        //when
        Long orderId=orderService.order(member.getId(),book.getId(),orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품주문시 상태는 ORDER",
                OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("종류 확인", 1, getOrder.getOrderItems().size());
    }



    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given

        Member member = createMember();
        Book book = createBook(10000, "시골 JPA", 10);
        int orderCount =11;

        //when

        orderService.order(member.getId(), book.getId(), orderCount);
        //then
        fail("재고부족.");
    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member =createMember();
        Book book = createBook(10000,"시골 JPA",10);
        int orderCount =2;

        Long orderId = orderService.order(member.getId(),book.getId(),orderCount);
        //when
    orderService.cancleOrder(orderId);
        //then

        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문취소시 상태는 캔슬",OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("수량은 다시 10개", 10, book.getStockQuantity());
    }


    private Book createBook(int price, String name, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }


}