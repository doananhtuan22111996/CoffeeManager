package com.tuan.coffeemanager.base;

public class NodeBaseApp {

    public static final String NODE_USER = "USER";
    public static final String NODE_TABLE = "TABLE";
    public static final String NODE_DRINK = "DRINK";
    public static final String NODE_ORDER = "ORDER";
    public static final String NODE_ORDER_DETAIL = "ORDER_DETAIL";

//    public static List<User> userList = new ArrayList<>();
//    public static List<Table> tableList = new ArrayList<>();
//    public static List<Drink> drinkList = new ArrayList<>();
//    public static List<Order> orderList = new ArrayList<>();
//    public static List<DrinkOrder> drinkOrderList = new ArrayList<>();
//    public static List<OrderDetail> orderDetailList = new ArrayList<>();
//
//    public static void createUser() {
//        User user_1 = new User(null, "Jay", "employee", "TP.HCM", "1993", "01673196316");
//        User user_2 = new User(null, "Alan", "manager", "TP.HCM", "1992", "01647929422");
//        User user_3 = new User(null, "Howard", "employee", "TP.HCM", "1995", "0198445678");
//        userList.add(user_1);
//        userList.add(user_2);
//        userList.add(user_3);
//    }
//
//    public static void createTable() {
//        for (int i = 0; i < 3; i++) {
//            Table table = new Table(null, i + 1);
//            tableList.add(table);
//        }
//    }
//
//    public static void createDrink() {
//        Drink drink_1 = new Drink(null, "Americano", "Espresso, hot water", 35000);
//        Drink drink_2 = new Drink(null, "Cappucino", "Espresso, milk, milk foam", 45000);
//        Drink drink_3 = new Drink(null, "Mocha", "Espresso, chocolate, milk, milk foam", 55000);
//        drinkList.add(drink_1);
//        drinkList.add(drink_2);
//        drinkList.add(drink_3);
//    }
//
//    public static void createOrderDetail() {
//        OrderDetail orderDetail_1 = new OrderDetail(null, "2/7/2018", new ArrayList<DrinkOrder>());
//        OrderDetail orderDetail_2 = new OrderDetail(null, "2/7/2018", new ArrayList<DrinkOrder>());
//        OrderDetail orderDetail_3 = new OrderDetail(null, "2/7/2018", new ArrayList<DrinkOrder>());
//        orderDetailList.add(orderDetail_1);
//        orderDetailList.add(orderDetail_2);
//        orderDetailList.add(orderDetail_3);
//    }

// Create Data OrderDetail
//     databaseReference.child(NodeBaseApp.NODE_DRINK).addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                Drink drink = dataSnapshot1.getValue(Drink.class);
//                DrinkOrder drinkOrder = dataSnapshot1.getValue(DrinkOrder.class);
//                NodeBaseApp.drinkList.add(drink);
//                NodeBaseApp.drinkOrderList.add(drinkOrder);
//            }
//
//            for (int i = 0; i < NodeBaseApp.orderDetailList.size(); i++) {
//                String key = databaseReference.child(NodeBaseApp.NODE_ORDER_DETAIL).push().getKey();
//                NodeBaseApp.orderDetailList.get(i).setId(key);
//                NodeBaseApp.orderDetailList.get(i).getDrinkOrderList().add(NodeBaseApp.drinkOrderList.get(0));
//                NodeBaseApp.orderDetailList.get(i).getDrinkOrderList().get(0).setAmount("2");
//                databaseReference.child(NodeBaseApp.NODE_ORDER_DETAIL).child(key).setValue(NodeBaseApp.orderDetailList.get(i));
//            }
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//        }
//    });

// Create Data Order
//     databaseReference.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            for (DataSnapshot dataSnapshot1 : dataSnapshot.child(NodeBaseApp.NODE_TABLE).getChildren()) {
//                Table table = dataSnapshot1.getValue(Table.class);
//                NodeBaseApp.tableList.add(table);
//            }
//
//            for (DataSnapshot dataSnapshot1 : dataSnapshot.child(NodeBaseApp.NODE_USER).getChildren()) {
//                User user = dataSnapshot1.getValue(User.class);
//                NodeBaseApp.userList.add(user);
//            }
//
//            for (DataSnapshot dataSnapshot1 : dataSnapshot.child(NodeBaseApp.NODE_ORDER_DETAIL).getChildren()) {
//                OrderDetail orderDetail = dataSnapshot1.getValue(OrderDetail.class);
//                NodeBaseApp.orderDetailList.add(orderDetail);
//            }
//
//            for (int i = 0; i < NodeBaseApp.tableList.size(); i++) {
//                String table_key = NodeBaseApp.tableList.get(i).getId();
//                Order order = new Order(NodeBaseApp.userList.get(i).getId(), NodeBaseApp.orderDetailList.get(i).getId());
//                databaseReference.child(NodeBaseApp.NODE_ORDER).child(table_key).setValue(order);
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    });
}
