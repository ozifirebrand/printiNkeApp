package ozi.app.printApp.services.userService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ozi.app.printApp.data.dtos.requests.OrderCreationRequest;
import ozi.app.printApp.data.dtos.requests.OrderUpdateRequest;
import ozi.app.printApp.data.dtos.requests.UserCreationRequest;
import ozi.app.printApp.data.dtos.responses.OrderCreationResponse;
import ozi.app.printApp.data.dtos.responses.UserCreationResponse;
import ozi.app.printApp.data.models.OrderStatus;
import ozi.app.printApp.data.models.PrintOrder;
import ozi.app.printApp.data.models.PrintUser;
import ozi.app.printApp.data.models.Role;
import ozi.app.printApp.exceptions.BusinessLogicException;
import ozi.app.printApp.exceptions.OrderException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class UserServicesImplTest {

    @Autowired
    @Mock
    private UserServices userServices;

    private UserCreationRequest request;

    @BeforeEach
    void setUp() {

        orderCreationRequest = new OrderCreationRequest();
        orderCreationRequest.setSize(14.0);
        orderCreationRequest.setQuantity(12);
        orderCreationRequest.setImageUrl("imageurl.com");
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
        
       request = new UserCreationRequest();

        request.setFirstName("firstname");
        request.setLastName("lastname");
        request.setPassword("password");
    }

    @Test
    void createUser() throws BusinessLogicException {

        //given
        request.setEmail("firstname@mail.com");
        //when
        UserCreationResponse response=userServices.createUser(request);
        //assert
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("firstname@mail.com");
        assertThat(response.getFirstName()).isEqualTo("firstname");
        assertThat(response.getLastName()).isEqualTo("lastname");
        assertThat(response.getOrders().size()).isEqualTo(0);
        userServices.deleteAllUsers();
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingPassword() {
        //given
        request.setEmail("firstname@mail1.com");
        request.setPassword(null);
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingLastname() {
        //given
        request.setEmail("firstname@mail2.com");
        request.setLastName(null);
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    public void assert_IncompleteUserDetails_BeforeSavingUser_MissingFirstname(){
        //given
        request.setEmail("firstname@mail3.com");
        request.setFirstName(null);
        //when
        //then
        assertThatThrownBy(()-> userServices.createUser(request)).isInstanceOf(BusinessLogicException.class).hasMessage("Incomplete details");
    }

    @Test
    void getUserById() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail4.com");
        UserCreationResponse response = userServices.createUser(request);
        //when
        PrintUser user = userServices.getUserById(response.getId());
        //assert
        assertThat(user.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(user.getLastName()).isEqualTo(response.getLastName());
        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertThat(user.getId()).isEqualTo(response.getId());
        userServices.deleteAllUsers();

    }

    @Test
    public void test_ifIdIsEmpty_FindByIdThrows_UserDoesNotExistErrorMessage() {
        //given no condition
        //when
        //assert
        assertThatThrownBy(()->userServices.getUserById("just_any_value")).isInstanceOf(BusinessLogicException.class).hasMessage("This user does not exist!");
    }

    @Test
    void getUserByEmail() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail5.com");
        UserCreationResponse response = userServices.createUser(request);

        //when
        PrintUser user = userServices.getUserByEmail(response.getEmail());

        //assert

        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertThat(user.getId()).isEqualTo(response.getId());
        assertThat(user.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(user.getLastName()).isEqualTo(response.getLastName());
        userServices.deleteAllUsers();

    }

    @Test
    public void test_ifEmailIsEmpty_FindByEmailThrows_UserWithEmailDoesNotExistErrorMessage(){
        //given no condition
        //when
        //assert
        assertThatThrownBy(()->userServices
                .getUserByEmail("invalidemail@mail.com"))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("This user with email \"invalidemail@mail.com\" does not exist!");
    }


                                    //todo TEST FOR INVALID EMAIL VALUE - REGEX

    @Test
    public void updateUser() {
    }

    @Test
    public void getAllUsers() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail6.com");

        UserCreationRequest request1 = new UserCreationRequest();
        request1.setEmail("firstname1@mail7.com");
        request1.setFirstName("firstname1");
        request1.setLastName("lastname1");
        request1.setPassword("password1");


        //when
       userServices.createUser(request);
       userServices.createUser(request1);

        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(2);
        userServices.deleteAllUsers();

    }


    @Test
    public void deleteUserById() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail8.com");
        UserCreationResponse response = userServices.createUser(request);

        //when
        boolean userIsDeleted = userServices.deleteUserById(response.getId());

        //assert
        assertThat(userIsDeleted).isTrue();
    }

    @Test
    public void deleteAllUsers() throws BusinessLogicException {
        //given
        request.setEmail("firstname@mail9.com");
        UserCreationRequest request1 = new UserCreationRequest();
        request1.setEmail("firstname1@mail01.com");
        request1.setFirstName("firstname2");
        request1.setLastName("lastname2");
        request1.setPassword("password3");
        //when
        userServices.createUser(request);
        userServices.createUser(request1);
        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(2);

        //when
        boolean isAllDeleted = userServices.deleteAllUsers();

        //assert
        assertThat(userServices.getAllUsers().size()).isEqualTo(0);
        assertThat(isAllDeleted).isTrue();
    }

    @Test
    public void test_ThrowException_WhenDeleteAllOnEmptyDB() throws BusinessLogicException {
        userServices.deleteAllUsers();
        //given
        //when
        //assert
        assertThatThrownBy(()->userServices.deleteAllUsers()).isInstanceOf(BusinessLogicException.class).hasMessage("There are no users in here!");
    }


    @Test
    public void test_UserRoleIsUser() throws BusinessLogicException {

        //given
        request.setEmail("firstname@mail02.com");
        //when
        UserCreationResponse response = userServices.createUser(request);

        //assert
        assertThat(response).isNotNull();
        assertThat(response.getRole()).isEqualTo(Role.USER);
        assertThat(response.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(response.getLastName()).isEqualTo(request.getLastName());
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testUpdateUserRole() throws BusinessLogicException {
        //given...
        request.setEmail("firstname@mail03.com");
        UserCreationResponse response = userServices.createUser(request);

        //when
        Role role = response.getRole();

        //assert
        assertThat(role).isEqualTo(Role.USER);

        //when
        userServices.updateUserRole(response.getId(), Role.ADMIN);

        //assert
        assertThat(userServices.getUserById(response.getId()).getRole()).isEqualTo(Role.ADMIN);

    }



    @Mock
    private UserServices mockUserServices;

    private OrderCreationRequest orderCreationRequest;



    @AfterEach
    void tearDown() {
    }

    @Test
    public void createOrder() throws BusinessLogicException {
        //given

        OrderCreationResponse orderCreationResponse = new OrderCreationResponse();

        //when
        when(mockUserServices.createOrder(orderCreationRequest)).thenReturn(orderCreationResponse);
        mockUserServices.createOrder(orderCreationRequest);
        verify(mockUserServices, times(1)).createOrder(orderCreationRequest);
    }

    @Test
    public void testIncompleteDetails_ThrowException(){

        //given
        orderCreationRequest.setUserId(null);

        //when
        //assert
        assertThatThrownBy
                (()->userServices.createOrder(orderCreationRequest))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("The given details are incomplete");
    }

    @Test
    public void deleteOrderById() throws BusinessLogicException {
        //given
        OrderCreationResponse response =userServices.createOrder(orderCreationRequest);

        //when
        boolean orderIsDeleted = userServices.deleteOrderById(response.getId());

        //assert
        assertThat(orderIsDeleted).isTrue();

    }

    @Test
    public void testInvalidId_ThrowException() {
        //given
        //when
        //assert
        assertThatThrownBy(()->userServices
                .deleteOrderById("mine"))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("No such order exists!");

    }

    @Test
    public void deleteOrderByUserId() {
        //given
        String userId = "aUserId";
        //when
        when(mockUserServices.deleteOrderByUserId(userId)).thenReturn(true);
        boolean isDeleted = mockUserServices.deleteOrderByUserId(userId);
        verify(mockUserServices, times(1)).deleteOrderByUserId(userId);
        assertThat(isDeleted).isTrue();
    }

    @Test
    public void testInvalidUserId_ThrowException() {
        //given
        //when
        boolean isDeleted = userServices.deleteOrderByUserId("anInvalidId");
        //assert
        assertThat(isDeleted).isFalse();
    }

    @Test
    public void updateOrderDetails() throws BusinessLogicException {
        //given
        OrderCreationResponse response = userServices.createOrder(orderCreationRequest);

        //when
        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
        orderUpdateRequest.setQuantity(9);
        orderUpdateRequest.setSize(22);
        OrderCreationResponse response1 = userServices.updateOrderDetails(response.getId(), orderUpdateRequest);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(9);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(22);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void if_updateDetailIsNullOrEmpty_DetailDoesNotChangeInDB()
            throws BusinessLogicException {
        //given
        OrderCreationResponse response = userServices.createOrder(orderCreationRequest);

        //when
        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
        orderUpdateRequest.setQuantity(9);
        OrderCreationResponse response1 = userServices.updateOrderDetails(response.getId(), orderUpdateRequest);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(9);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(14);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void test_if_updateDetailIsNullOrEmpty_DetailDoesNotChangeInDB()
            throws BusinessLogicException {
        //given
        OrderCreationResponse response = userServices.createOrder(orderCreationRequest);

        //when
        OrderUpdateRequest orderUpdateRequest2 = new OrderUpdateRequest();
        orderUpdateRequest2.setSize(88);
        OrderCreationResponse response1 = userServices.updateOrderDetails(response.getId(), orderUpdateRequest2);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(12);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(88);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void test_AllUpdateDetailIsNullOrEmpty_DetailDoesNotChangeInDB()
            throws BusinessLogicException {
        //given
        OrderCreationResponse response = userServices.createOrder(orderCreationRequest);

        //when

        OrderUpdateRequest orderUpdateRequest3 = new OrderUpdateRequest();

        orderUpdateRequest3.setQuantity(0);
        orderUpdateRequest3.setSize(0);
        OrderCreationResponse response1 = userServices.updateOrderDetails(response.getId(), orderUpdateRequest3);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(12);
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(14);
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());
    }

    @Test
    public void updateOrderStatus() throws BusinessLogicException {
        //given
        OrderCreationResponse response = userServices.createOrder(orderCreationRequest);

        //when
        OrderCreationResponse response1 = userServices.updateOrderStatus(response.getId(), OrderStatus.DELIVERED);

        //assert
        assertThat(response1.getQuantity()).isEqualTo(response.getQuantity());
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getOrderDate()).isEqualTo(response.getOrderDate());
        assertThat(response1.getSize()).isEqualTo(response.getSize());
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getDeliveryDate());


    }

    @Test
    public void updateOrderDeliverDate() throws BusinessLogicException {
        //given
        OrderCreationResponse response = userServices.createOrder(orderCreationRequest);

        //when
        OrderCreationResponse response1 = userServices.updateOrderDeliverDate(response.getId(),
                response.getOrderDate().plusDays(5).truncatedTo(ChronoUnit.DAYS));

        //assert
        assertThat(response1.getQuantity()).isEqualTo(response.getQuantity());
        assertThat(response1.getId()).isEqualTo(response.getId());
        assertThat(response1.getSize()).isEqualTo(response.getSize());
        assertThat(response1.getImageUrl()).isEqualTo(response.getImageUrl());
        assertThat(response1.getOrderStatus()).isEqualTo(response.getOrderStatus());
        assertThat(response1.getDeliveryDate()).isEqualTo(response.getOrderDate().plusDays(5).truncatedTo(ChronoUnit.DAYS));

    }

    @Test
    public void deleteAllOrders() throws BusinessLogicException {
        //given
        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);

        userServices.createOrder(orderCreationRequest1);
        userServices.createOrder(orderCreationRequest);

        //when
        boolean isDeleted = userServices.deleteAllOrders();

        //assert
        assertThat(isDeleted).isTrue();
        assertThatThrownBy(()->userServices.getAllOrders())
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no orders here!");
    }

    @Test
    public void getOrderById() throws BusinessLogicException {
        //given
        OrderCreationResponse response = userServices.createOrder(orderCreationRequest);

        //when
        OrderCreationResponse response1 = userServices.getOrderById(response.getId());

        //assert
        assertThat(response.getId()).isEqualTo(response1.getId());
        assertThat(response.getOrderDate()).isEqualTo(response1.getOrderDate());
        assertThat(response.getDeliveryDate()).isEqualTo(response1.getDeliveryDate());
        assertThat(response.getQuantity()).isEqualTo(response1.getQuantity());
    }

    @Test
    public void getOrdersByDate() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
        userServices.deleteOrderByUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");

        OrderCreationResponse response =userServices.createOrder(orderCreationRequest);
        OrderCreationResponse response1 =userServices.createOrder(orderCreationRequest1);

        //when
        List<PrintOrder> orders = userServices.getOrdersByDate(response.getOrderDate());

        //assert
        assertThat(response.getId()).isEqualTo(orders.get(0).getId());
        assertThat(response.getQuantity()).isEqualTo(orders.get(0).getQuantity());
        assertThat(response1.getId()).isEqualTo(orders.get(1).getId());
        assertThat(response1.getQuantity()).isEqualTo(orders.get(1).getQuantity());

    }

    @Test
    public void test_EmptyOrdersBy_Date_SendThereAreNoOrdersWithThisDate() throws OrderException {
        //assert
        userServices.deleteAllOrders();
        assertThatThrownBy(()->userServices.getOrdersByDate(LocalDateTime.now()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no orders with this date "
                        +LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)+"!");
    }

    @Test
    public void getOrdersByStatus() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");

        OrderCreationResponse response =userServices.createOrder(orderCreationRequest);
        OrderCreationResponse response1 =userServices.createOrder(orderCreationRequest1);

        OrderCreationResponse response2 =userServices.updateOrderStatus(response.getId(), OrderStatus.PENDING);

        //when
        List<PrintOrder> ordersByStatus1 = userServices.getOrdersByStatus(response1.getOrderStatus());
        List<PrintOrder> ordersByStatus2 = userServices.getOrdersByStatus(response2.getOrderStatus());

        //assert
        assertThat(ordersByStatus1.size()).isEqualTo(1);
        assertThat(ordersByStatus1.get(0).getId()).isEqualTo(response1.getId());
        assertThat(ordersByStatus1.get(0).getOrderStatus()).isEqualTo(response1.getOrderStatus());

        assertThat(ordersByStatus2.get(0).getOrderStatus()).isEqualTo(response2.getOrderStatus());
        assertThat(ordersByStatus2.get(0).getId()).isEqualTo(response.getId());
        assertThat(response2.getId()).isEqualTo(response.getId());
        assertThat(ordersByStatus2.size()).isEqualTo(1);

    }

    @Test
    public void test_EmptyOrdersBy_Status_SendThereAreNoOrdersInThisStatus(){
        //assert
        assertThatThrownBy(()->userServices.getOrdersByStatus(OrderStatus.DELIVERED))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("There are no orders in this state "+OrderStatus.DELIVERED);
    }

    @Test
    public void getOrdersByUserId() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
        userServices.deleteOrderByUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");
        userServices.deleteAllOrders();
        OrderCreationResponse response =userServices.createOrder(orderCreationRequest);
        System.out.println(response.getOrderDate());

        OrderCreationResponse response1 =userServices.createOrder(orderCreationRequest1);

        //when
        List<PrintOrder> ordersByUserId = userServices.getOrdersByUserId(userId);
        List<PrintOrder> ordersByUserId1 = userServices.getOrdersByUserId("anId");

        //assert
        assertThat(ordersByUserId.size()).isEqualTo(1);
        assertThat(ordersByUserId.get(0).getId()).isEqualTo(response.getId());
        assertThat(ordersByUserId.get(0).getQuantity()).isEqualTo(response.getQuantity());
        assertThat(ordersByUserId.get(0).getSize()).isEqualTo(response.getSize());


        assertThat(ordersByUserId1.size()).isEqualTo(1);
        assertThat(ordersByUserId1.get(0).getId()).isEqualTo(response1.getId());
        assertThat(ordersByUserId1.get(0).getQuantity()).isEqualTo(response1.getQuantity());
        assertThat(ordersByUserId1.get(0).getSize()).isEqualTo(response1.getSize());
    }

    @Test
    public void test_InvalidUserId_ThrowsBusinessException(){
        //given
        String invalidId = "anInvalidId";

        //assert
        assertThatThrownBy(()->userServices.getOrdersByUserId(invalidId))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("This user, anInvalidId, does not have any orders!");

    }

    @Test
    public void getAllOrders() throws BusinessLogicException {
        //given
        String userId = "myUserId";
        orderCreationRequest.setUserId(userId);
        userServices.deleteOrderByUserId(userId);

        OrderCreationRequest orderCreationRequest1 = new OrderCreationRequest();
        orderCreationRequest1.setSize(1.0);
        orderCreationRequest1.setQuantity(1);
        orderCreationRequest1.setImageUrl("imaeurl.com");
        orderCreationRequest1.setUserId("anId");
        userServices.deleteAllOrders();


        OrderCreationRequest orderCreationRequest2 = new OrderCreationRequest();
        orderCreationRequest2.setSize(10);
        orderCreationRequest2.setQuantity(100);
        orderCreationRequest2.setImageUrl("myimageurleurl.com");
        orderCreationRequest2.setUserId("anotherId");
        OrderCreationResponse response =userServices.createOrder(orderCreationRequest);
        OrderCreationResponse response1 =userServices.createOrder(orderCreationRequest1);
        OrderCreationResponse response2 =userServices.createOrder(orderCreationRequest2);

        //when
        List<PrintOrder> ordersList = userServices.getAllOrders();

        //assert
        assertThat(ordersList.size()).isEqualTo(3);
        assertThat(response.getQuantity()).isEqualTo(ordersList.get(0).getQuantity());
        assertThat(response.getId()).isEqualTo(ordersList.get(0).getId());
        assertThat(response.getId()).isEqualTo(ordersList.get(0).getId());
        assertThat(response1.getQuantity()).isEqualTo(ordersList.get(1).getQuantity());
        assertThat(response1.getId()).isEqualTo(ordersList.get(1).getId());
        assertThat(response1.getId()).isEqualTo(ordersList.get(1).getId());
        assertThat(response2.getQuantity()).isEqualTo(ordersList.get(2).getQuantity());
        assertThat(response2.getId()).isEqualTo(ordersList.get(2).getId());
        assertThat(response2.getId()).isEqualTo(ordersList.get(2).getId());
    }
}