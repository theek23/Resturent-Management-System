DROP DATABASE batakade;
CREATE DATABASE batakade;
USE batakade;

CREATE TABLE employee(
                         emp_id VARCHAR(6),
                         name VARCHAR(30) NOT NULL,
                         phone VARCHAR(12),
                         type VARCHAR(10),
                         date DATE,
                         CONSTRAINT PRIMARY KEY (emp_id)
);
CREATE TABLE user(
                     user_id VARCHAR(6),
                     emp_id VARCHAR(6),
                     username VARCHAR(12),
                     password VARCHAR(12),
                     CONSTRAINT PRIMARY KEY (user_id),
                     CONSTRAINT FOREIGN KEY(emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE salary(
                       salary_id VARCHAR(6),
                       dateTime DATETIME,
                       amount DECIMAL(8,2),
                       OT DECIMAL(8,2),
                       emp_id VARCHAR(6),
                       CONSTRAINT PRIMARY KEY (salary_id),
                       CONSTRAINT FOREIGN KEY(emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE suplier(
                        sup_id VARCHAR(6),
                        name VARCHAR(30),
                        phone VARCHAR(12) NOT NULL,
                        CONSTRAINT PRIMARY KEY (sup_id)
);
CREATE TABLE itemCategory(
                             cat_id VARCHAR(6),
                             cat_type VARCHAR(15),
                             CONSTRAINT PRIMARY KEY (cat_id)
);
CREATE TABLE item(
                     item_id VARCHAR(6),
                     name VARCHAR(30) NOT NULL,
                     unit_price DECIMAL(8,2),
                     QTYonHand INT,
                     cat_id VARCHAR(6) NOT NULL,
                     CONSTRAINT PRIMARY KEY (item_id),
                     CONSTRAINT FOREIGN KEY (cat_id) REFERENCES itemCategory(cat_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE suplier_bill(
                             bill_id VARCHAR(6),
                             item_id VARCHAR(6) NOT NULL,
                             sup_id VARCHAR(6),
                             date DATETIME,
                             payment_status VARCHAR(10),
                             price DECIMAL(8,2),
                             CONSTRAINT PRIMARY KEY (item_id,bill_id),
                             CONSTRAINT FOREIGN KEY (item_id) REFERENCES item(item_id) ON UPDATE CASCADE ON DELETE CASCADE,
                             CONSTRAINT FOREIGN KEY (sup_id) REFERENCES suplier(sup_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE bench(
                      bench_id VARCHAR(6),
                      capacity INT(3),
                      bench_status VARCHAR(10),
                      CONSTRAINT PRIMARY KEY (bench_id)
);
CREATE TABLE takeaway(
                         takeaway_id VARCHAR(6),
                         takeaway_status VARCHAR(10),
                         CONSTRAINT PRIMARY KEY (takeaway_id)
);
CREATE TABLE delivery(
                         del_id VARCHAR(6),
                         type VARCHAR(10),
                         del_address VARCHAR(20),
                         date DATETIME,
                         phone VARCHAR(12),
                         CONSTRAINT PRIMARY KEY (del_id)
);
CREATE TABLE orders(
                       order_id VARCHAR(6),
                       cust_name VARCHAR(20) NOT NULL,
                       cust_phone VARCHAR(12) NOT NULL,
                       order_status VARCHAR(20) NOT NULL,
                       order_price DECIMAL (8,2) NOT NULL,
                       qty INT not null ,
                       emp_id VARCHAR(6) NOT NULL,
                       bench_id VARCHAR(6) NOT NULL,
                       takeaway_id VARCHAR(6) NOT NULL,
                       del_id VARCHAR(6) NOT NULL,
                       time DATETIME,
                       CONSTRAINT PRIMARY KEY (order_id),
                       CONSTRAINT FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON UPDATE CASCADE ON DELETE CASCADE,
                       CONSTRAINT FOREIGN KEY (bench_id) REFERENCES bench(bench_id) ON UPDATE CASCADE ON DELETE CASCADE,
                       CONSTRAINT FOREIGN KEY (takeaway_id) REFERENCES takeaway(takeaway_id) ON UPDATE CASCADE ON DELETE CASCADE,
                       CONSTRAINT FOREIGN KEY (del_id) REFERENCES delivery(del_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE OrderDetail(
                            order_id VARCHAR(6) NOT NULL,
                            item_id VARCHAR(6) NOT NULL,
                            qty INT,
                            date DATETIME,
                            unitPrice DECIMAL(8,2),
                            CONSTRAINT PRIMARY KEY (order_id,item_id),
                            CONSTRAINT FOREIGN KEY (order_id) REFERENCES orders(order_id) on Delete Cascade on Update Cascade,
                            CONSTRAINT FOREIGN KEY (item_id) REFERENCES item(item_id) on Delete Cascade on Update Cascade
);
insert into bench values('B001',4,'available');
insert into employee values('E01','kamala','0136956465','waiter');
insert into itemCategory values ('C001', 'food', 'food');
insert into item values('I001','bath',150.00 ,10 ,'C001','food');
insert into delivery values ('D001','srdelivery','galle','25.10.2003');
insert into takeaway values ('T001','done');
insert into orders values ('C001','susil','0765895457','pending',500.00,'E01','B001','T001','D001');
use batakade;
DROP TABLE IF EXISTS delivery;
select * from orders;