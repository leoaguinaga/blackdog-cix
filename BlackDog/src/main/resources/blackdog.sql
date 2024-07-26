create table client
(
    client_id    bigint auto_increment
        primary key,
    first_name   varchar(50)  not null,
    last_name    varchar(50)  not null,
    phone_number varchar(15)  not null,
    email        varchar(50)  not null,
    pwd          varchar(255) not null,
    constraint email
        unique (email)
);

create table customer_order
(
    customer_order_id bigint auto_increment
        primary key,
    client_id         bigint        not null,
    order_date        datetime      not null,
    address           varchar(300)  not null,
    amount            decimal(6, 2) not null,
    state             varchar(15)   not null,
    evidence_image    varchar(100)  null,
    constraint fk_client_id
        foreign key (client_id) references client (client_id)
);

create index client_id
    on customer_order (client_id);

create table ingredient
(
    ingredient_id bigint auto_increment
        primary key,
    name          varchar(50)   not null,
    price         decimal(4, 2) not null
);

create table product
(
    product_id bigint auto_increment
        primary key,
    name       varchar(50)   not null,
    image      varchar(100)  not null,
    price      decimal(5, 2) not null,
    type       varchar(20)   not null
);

create table order_detail
(
    customer_order bigint not null,
    product_id     bigint not null,
    quantity       int    not null,
    primary key (customer_order, product_id),
    constraint fk_customer_order_id_1
        foreign key (customer_order) references customer_order (customer_order_id),
    constraint fk_product_id_2
        foreign key (product_id) references product (product_id)
);

create index product_id
    on order_detail (product_id);

create table product_ingredient
(
    product_id    bigint not null,
    ingredient_id bigint not null,
    quantity      int    not null,
    primary key (product_id, ingredient_id),
    constraint fk_ingredient_id
        foreign key (ingredient_id) references ingredient (ingredient_id),
    constraint fk_product_id_1
        foreign key (product_id) references product (product_id)
);

create index ingredient_id
    on product_ingredient (ingredient_id);

create table worker
(
    worker_id bigint auto_increment
        primary key,
    full_name varchar(80)  not null,
    email     varchar(50)  not null,
    pwd       varchar(255) not null,
    role      varchar(20)  not null,
    constraint email
        unique (email)
);

create
    definer = root@localhost procedure DeleteIngredient(IN p_ingredient_id bigint)
BEGIN
    DELETE FROM ingredient WHERE ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure DeleteOrder(IN p_customer_order_id bigint)
BEGIN
    DELETE FROM customer_order WHERE customer_order_id = p_customer_order_id;
END;

create
    definer = root@localhost procedure DeleteProduct(IN p_product_id bigint)
BEGIN
    DELETE FROM product WHERE product_id = p_product_id;
END;

create
    definer = root@localhost procedure DeleteProductIngredient(IN p_product_id bigint)
BEGIN
    DELETE FROM product_ingredient WHERE product_id = p_product_id;
END;

create
    definer = root@localhost procedure GetAllIngredients()
BEGIN
    SELECT * FROM ingredient;
END;

create
    definer = root@localhost procedure GetAllOrderDetails()
BEGIN
    SELECT * FROM order_detail;
END;

create
    definer = root@localhost procedure GetAllOrders()
BEGIN
    SELECT * FROM customer_order ORDER BY order_date DESC;
END;

create
    definer = root@localhost procedure GetAllProductIngredients()
BEGIN
    SELECT * FROM product_ingredient;
END;

create
    definer = root@localhost procedure GetAllProducts()
BEGIN
    SELECT * FROM product;
END;

create
    definer = root@localhost procedure GetIngredientById(IN p_ingredient_id bigint)
BEGIN
    SELECT * FROM ingredient WHERE ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure GetIngredientNameById(IN p_ingredient_id bigint)
BEGIN
    SELECT name FROM ingredient WHERE ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure GetLastCustomerOrder()
BEGIN
    SELECT * FROM customer_order ORDER BY customer_order_id DESC LIMIT 1;
END;

create
    definer = root@localhost procedure GetLastProduct()
BEGIN
    SELECT * FROM product ORDER BY product_id DESC LIMIT 1;
END;

create
    definer = root@localhost procedure GetOrderById(IN p_customer_order_id bigint)
BEGIN
    SELECT * FROM customer_order WHERE customer_order_id = p_customer_order_id;
END;

create
    definer = root@localhost procedure GetOrderDetailsByOrderId(IN p_customer_order_id bigint)
BEGIN
    SELECT * FROM order_detail WHERE customer_order = p_customer_order_id;
END;

create
    definer = root@localhost procedure GetOrderDetailsByProductId(IN p_product_id bigint)
BEGIN
    SELECT * FROM order_detail WHERE product_id = p_product_id;
END;

create
    definer = root@localhost procedure GetOrdersByState(IN p_state varchar(15))
BEGIN
    SELECT * FROM customer_order WHERE state = p_state;
END;

create
    definer = root@localhost procedure GetProductById(IN p_product_id bigint)
BEGIN
    SELECT * FROM product WHERE product_id = p_product_id;
END;

create
    definer = root@localhost procedure GetProductIngredient(IN p_product_id bigint, IN p_ingredient_id bigint)
BEGIN
    SELECT * FROM product_ingredient WHERE product_id = p_product_id AND ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure GetProductIngredientByIngredientId(IN p_ingredient_id bigint)
BEGIN
    SELECT * FROM product_ingredient WHERE ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure GetProductIngredientsByProductId(IN p_product_id bigint)
BEGIN
    SELECT * FROM product_ingredient WHERE product_id = p_product_id;
END;

create
    definer = root@localhost procedure GetProductsByType(IN p_type varchar(255))
BEGIN
    SELECT * FROM product WHERE type = p_type;
END;

create
    definer = root@localhost procedure GetQuantity(IN p_product_id bigint, IN p_ingredient_id bigint)
BEGIN
    SELECT quantity FROM product_ingredient
    WHERE product_id = p_product_id AND ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure RegisterIngredient(IN p_name varchar(100), IN p_price decimal(10, 2))
BEGIN
    INSERT INTO ingredient (name, price) VALUES (p_name, p_price);
END;

create
    definer = root@localhost procedure RegisterOrder(IN p_client_id bigint, IN p_order_date datetime,
                                                     IN p_address varchar(300), IN p_amount decimal(6, 2),
                                                     IN p_state varchar(15), IN p_evidence_image varchar(100))
BEGIN
    INSERT INTO customer_order (client_id, order_date, address, amount, state, evidence_image)
    VALUES (p_client_id, p_order_date, p_address, p_amount, p_state, p_evidence_image);
END;

create
    definer = root@localhost procedure RegisterOrderDetail(IN p_customer_order bigint, IN p_product_id bigint, IN p_quantity int)
BEGIN
    INSERT INTO order_detail (customer_order, product_id, quantity) VALUES (p_customer_order, p_product_id, p_quantity);
END;

create
    definer = root@localhost procedure RegisterProduct(IN p_name varchar(255), IN p_image varchar(255),
                                                       IN p_price double, IN p_type varchar(255))
BEGIN
    INSERT INTO product (name, image, price, type)
    VALUES (p_name, p_image, p_price, p_type);
END;

create
    definer = root@localhost procedure RegisterProductIngredient(IN p_product_id bigint, IN p_ingredient_id bigint, IN p_quantity int)
BEGIN
    INSERT INTO product_ingredient (product_id, ingredient_id, quantity)
    VALUES (p_product_id, p_ingredient_id, p_quantity);
END;

create
    definer = root@localhost procedure UpdateIngredient(IN p_name varchar(100), IN p_price decimal(10, 2),
                                                        IN p_ingredient_id bigint)
BEGIN
    UPDATE ingredient
    SET name = p_name, price = p_price
    WHERE ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure UpdateOrder(IN p_client_id bigint, IN p_order_date datetime,
                                                   IN p_address varchar(300), IN p_amount decimal(6, 2),
                                                   IN p_state varchar(15), IN p_evidence_image varchar(100),
                                                   IN p_customer_order_id bigint)
BEGIN
    UPDATE customer_order
    SET client_id = p_client_id, order_date = p_order_date, address = p_address, amount = p_amount, state = p_state, evidence_image = p_evidence_image
    WHERE customer_order_id = p_customer_order_id;
END;

create
    definer = root@localhost procedure UpdateProduct(IN p_product_id bigint, IN p_name varchar(255),
                                                     IN p_image varchar(255), IN p_price double, IN p_type varchar(255))
BEGIN
    UPDATE product
    SET name = p_name, image = p_image, price = p_price, type = p_type
    WHERE product_id = p_product_id;
END;

create
    definer = root@localhost procedure UpdateProductIngredient(IN p_product_id bigint, IN p_ingredient_id bigint, IN p_quantity int)
BEGIN
    UPDATE product_ingredient SET quantity = p_quantity
    WHERE product_id = p_product_id AND ingredient_id = p_ingredient_id;
END;

create
    definer = root@localhost procedure deleteWorker(IN p_worker_id bigint)
BEGIN
    DELETE FROM worker WHERE worker_id = p_worker_id;
END;

create
    definer = root@localhost procedure getAllWorkers()
BEGIN
    SELECT * FROM worker;
END;

create
    definer = root@localhost procedure getWorkerByEmail(IN p_email varchar(50))
BEGIN
    SELECT * FROM worker WHERE email = p_email;
END;

create
    definer = root@localhost procedure getWorkerById(IN p_worker_id bigint)
BEGIN
    SELECT * FROM worker WHERE worker_id = p_worker_id;
END;

create
    definer = root@localhost procedure registerWorker(IN p_full_name varchar(80), IN p_email varchar(50),
                                                      IN p_pwd varchar(255), IN p_role varchar(20))
BEGIN
    INSERT INTO worker (full_name, email, pwd, role)
    VALUES (p_full_name, p_email, p_pwd, p_role);
END;

create
    definer = root@localhost procedure sp_delete_client(IN p_client_id bigint)
BEGIN
    DELETE FROM client WHERE client_id = p_client_id;
END;

create
    definer = BlackDog@localhost procedure sp_delete_report(IN p_report_id bigint)
BEGIN
    DELETE FROM report WHERE report_id = p_report_id;
END;

create
    definer = BlackDog@localhost procedure sp_delete_report_log(IN p_worker_id bigint, IN p_report_id bigint)
BEGIN
    DELETE FROM report_log WHERE worker_id = p_worker_id AND report_id = p_report_id;
END;

create
    definer = root@localhost procedure sp_get_all_clients()
BEGIN
    SELECT * FROM client;
END;

create
    definer = BlackDog@localhost procedure sp_get_all_report_logs()
BEGIN
    SELECT * FROM report_log;
END;

create
    definer = BlackDog@localhost procedure sp_get_all_reports()
BEGIN
    SELECT * FROM report;
END;

create
    definer = root@localhost procedure sp_get_client_by_email(IN p_email varchar(50))
BEGIN
    SELECT * FROM client WHERE email = p_email;
END;

create
    definer = root@localhost procedure sp_get_client_by_id(IN p_client_id bigint)
BEGIN
    SELECT * FROM client WHERE client_id = p_client_id;
END;

create
    definer = BlackDog@localhost procedure sp_get_report_by_id(IN p_report_id bigint)
BEGIN
    SELECT * FROM report WHERE report_id = p_report_id;
END;

create
    definer = BlackDog@localhost procedure sp_get_report_log_by_worker_id(IN p_worker_id bigint)
BEGIN
    SELECT * FROM report_log WHERE worker_id = p_worker_id;
END;

create
    definer = root@localhost procedure sp_register_client(IN p_first_name varchar(50), IN p_last_name varchar(50),
                                                          IN p_phone_number varchar(15), IN p_email varchar(50),
                                                          IN p_pwd varchar(255))
BEGIN
    INSERT INTO client (first_name, last_name, phone_number, email, pwd)
    VALUES (p_first_name, p_last_name, p_phone_number, p_email, p_pwd);
END;

create
    definer = BlackDog@localhost procedure sp_register_report(IN p_customer_order_id bigint, IN p_lastUpdate datetime,
                                                              IN p_lastState varchar(50))
BEGIN
    INSERT INTO report (customer_order_id, lastUpdate, lastState)
    VALUES (p_customer_order_id, p_lastUpdate, p_lastState);
END;

create
    definer = BlackDog@localhost procedure sp_register_report_log(IN p_worker_id bigint, IN p_report_id bigint,
                                                                  IN p_date datetime, IN p_state varchar(50))
BEGIN
    INSERT INTO report_log (worker_id, report_id, date, state)
    VALUES (p_worker_id, p_report_id, p_date, p_state);
END;

create
    definer = root@localhost procedure sp_update_client(IN p_first_name varchar(50), IN p_last_name varchar(50),
                                                        IN p_phone_number varchar(15), IN p_email varchar(50),
                                                        IN p_new_email varchar(50))
BEGIN
    UPDATE client
    SET first_name = p_first_name,
        last_name = p_last_name,
        phone_number = p_phone_number,
        email = p_new_email
    WHERE email = p_email;
END;

create
    definer = BlackDog@localhost procedure sp_update_report(IN p_report_id bigint, IN p_customer_order_id bigint,
                                                            IN p_lastUpdate datetime, IN p_lastState varchar(50))
BEGIN
    UPDATE report
    SET customer_order_id = p_customer_order_id,
        lastUpdate = p_lastUpdate,
        lastState = p_lastState
    WHERE report_id = p_report_id;
END;

create
    definer = BlackDog@localhost procedure sp_update_report_log(IN p_worker_id bigint, IN p_report_id bigint,
                                                                IN p_date datetime, IN p_state varchar(50),
                                                                IN p_old_worker_id bigint, IN p_old_report_id bigint)
BEGIN
    UPDATE report_log
    SET worker_id = p_worker_id, report_id = p_report_id, date = p_date, state = p_state
    WHERE worker_id = p_old_worker_id AND report_id = p_old_report_id;
END;

create
    definer = root@localhost procedure updateWorker(IN p_worker_id bigint, IN p_full_name varchar(80),
                                                    IN p_email varchar(50), IN p_phone_number varchar(15),
                                                    IN p_pwd varchar(255), IN p_pwd varchar(255), IN p_role varchar(3),
                                                    IN p_role varchar(3))
BEGIN
    UPDATE worker
    SET full_name = p_full_name, email = p_email, pwd = p_pwd, role = p_role
    WHERE worker_id = p_worker_id;
END;

