#2017-12-07 15:00:00
CREATE UNIQUE INDEX room_shop_item_uindex ON room_shop (item);#限制房吧品种不可重复，报错的话需要手动删除
ALTER TABLE debt ADD source_room VARCHAR(100) NULL;#增加一个原始房号列，记录换房前的房号
ALTER TABLE debt_history ADD source_room VARCHAR(100) NULL;
ALTER TABLE debt MODIFY room_id VARCHAR(100);#扩大房号长度
#2017-12-11 10:10:00
create OR REPLACE view check_in_integration as
  SELECT
    `ci`.`room_id`          AS `room_id`,
    `ci`.`room_category`    AS `room_category`,
    `ci`.`room_price_category`    AS `room_price_category`,
    `ci`.`final_room_price` AS `final_room_price`,
    `ci`.`reach_time`       AS `reach_time`,
    `ci`.`leave_time`       AS `leave_time`,
    `ci`.`remark`           AS `remark`,
    `ci`.`self_account`     AS `self_account`,
    `ci`.`guest_source`     AS `guest_source`,
    `ci`.`consume`          AS `consume`,
    `ci`.`if_room`          AS `if_room`,
    `ci`.`user_id`          AS `user_id`,
    1                       AS `if_in`
  FROM `hotel`.`check_in` `ci`
  UNION SELECT
          `cihl`.`room_id`          AS `room_id`,
          `cihl`.`room_category`    AS `room_category`,
          `cihl`.`room_price_category`    AS `room_price_category`,
          `cihl`.`final_room_price` AS `final_room_price`,
          `cihl`.`reach_time`       AS `reach_time`,
          `cihl`.`leave_time`       AS `leave_time`,
          `cihl`.`remark`           AS `remark`,
          `cihl`.`self_account`     AS `self_account`,
          `cihl`.`guest_source`     AS `guest_source`,
          `cihl`.`consume`          AS `consume`,
          `cihl`.`if_room`          AS `if_room`,
          `cihl`.`user_id`          AS `user_id`,
          0                         AS `if_in`
        FROM `hotel`.`check_in_history_log` `cihl`;#增加房租方式
#2017-12-14 10:50:00
ALTER TABLE check_out ADD fp_money DOUBLE NULL;#增加手写发票金额功能
#2017-12-15 17:22:10
ALTER TABLE company_category ADD available_pos VARCHAR(200) NULL;#增加单位可用部门
UPDATE company_category SET available_pos =(SELECT group_concat(DISTINCT first_point_of_sale) FROM point_of_sale) WHERE available_pos IS NULL ;#默认值为全部可用
#2017-12-18 11:15:00
CREATE OR REPLACE VIEW company_debt_integration AS
  SELECT
    `hotel`.`company_debt`.`id`             AS `id`,
    `hotel`.`company_debt`.`company`        AS `company`,
    `hotel`.`company_debt`.`lord`           AS `lord`,
    `hotel`.`company_debt`.`pay_serial`     AS `pay_serial`,
    `hotel`.`company_debt`.`debt`           AS `debt`,
    `hotel`.`company_debt`.`do_time`        AS `do_time`,
    `hotel`.`company_debt`.`user_id`        AS `user_id`,
    `hotel`.`company_debt`.`description`    AS `description`,
    `hotel`.`company_debt`.`point_of_sale`  AS `point_of_sale`,
    `hotel`.`company_debt`.`second_point_of_sale`  AS `second_point_of_sale`,
    `hotel`.`company_debt`.`current_remain` AS `current_remain`,
    0                                       AS `company_paid`
  FROM `hotel`.`company_debt`
  UNION SELECT
          `hotel`.`company_debt_history`.`id`             AS `id`,
          `hotel`.`company_debt_history`.`company`        AS `company`,
          `hotel`.`company_debt_history`.`lord`           AS `lord`,
          `hotel`.`company_debt_history`.`pay_serial`     AS `pay_serial`,
          `hotel`.`company_debt_history`.`debt`           AS `debt`,
          `hotel`.`company_debt_history`.`do_time`        AS `do_time`,
          `hotel`.`company_debt_history`.`user_id`        AS `user_id`,
          `hotel`.`company_debt_history`.`description`    AS `description`,
          `hotel`.`company_debt_history`.`point_of_sale`  AS `point_of_sale`,
          `hotel`.`company_debt_history`.`second_point_of_sale`  AS `second_point_of_sale`,
          `hotel`.`company_debt_history`.`current_remain` AS `current_remain`,
          1                                               AS `company_paid`
        FROM `hotel`.`company_debt_history`;#补充二级营业部门
#2017-12-19
UPDATE company_debt SET point_of_sale='接待' WHERE point_of_sale IS NULL ;#定额结算冲账之前不记录模块，现在补上，为空的都设置为接待
#2017-12-21
ALTER TABLE check_in_group MODIFY user_id VARCHAR(100);#字段扩容
#2018-01-02
ALTER TABLE check_in MODIFY reach_time DATETIME NOT NULL;
ALTER TABLE check_in MODIFY leave_time DATETIME NOT NULL;
#2018-01-04
ALTER TABLE vip MODIFY category VARCHAR(10) NOT NULL;
#2018-01-06
#大更新，增加单位储值功能
create table company_money
(
  id int not null auto_increment
    primary key,
  company varchar(100) null,
  category varchar(20) null,
  money double null,
  currency varchar(20) null,
  do_time datetime null,
  user_id varchar(100) null,
  remark varchar(100) null,
  company_pay_serial varchar(20) null
);
#2018-01-19 更新一个视图，餐饮区分结账和开台操作员
create OR REPLACE view desk_pay_rich as
  SELECT
    `hotel`.`desk_pay`.`pay_money`            AS `pay_money`,
    `hotel`.`desk_pay`.`currency`             AS `currency`,
    `hotel`.`desk_pay`.`currency_add`         AS `currency_add`,
    `hotel`.`desk_pay`.`user_id`         AS `user_id_out`,
    `hotel`.`desk_in_history`.`id`            AS `id`,
    `hotel`.`desk_in_history`.`ck_serial`     AS `ck_serial`,
    `hotel`.`desk_in_history`.`do_time`       AS `do_time`,
    `hotel`.`desk_in_history`.`done_time`     AS `done_time`,
    `hotel`.`desk_in_history`.`total_price`   AS `total_price`,
    `hotel`.`desk_in_history`.`final_price`   AS `final_price`,
    `hotel`.`desk_in_history`.`discount`      AS `discount`,
    `hotel`.`desk_in_history`.`desk`          AS `desk`,
    `hotel`.`desk_in_history`.`user_id`       AS `user_id_in`,
    `hotel`.`desk_in_history`.`point_of_sale` AS `point_of_sale`,
    `hotel`.`desk_in_history`.`num`           AS `num`,
    `hotel`.`desk_in_history`.`disabled`      AS `disabled`
  FROM (`hotel`.`desk_pay`
    LEFT JOIN `hotel`.`desk_in_history` ON ((`hotel`.`desk_pay`.`ck_serial` = `hotel`.`desk_in_history`.`ck_serial`)));
#2018-03-05 结账明细增加客户姓名,增加注册号带时间
ALTER TABLE debt_pay ADD guest_name TEXT NULL;
ALTER TABLE other_param MODIFY value TEXT;
#2018-03-26 增加早餐明细表
CREATE TABLE breakfast_detail
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  hotel_id INT,
  room_id VARCHAR(100),
  self_account VARCHAR(100),
  do_time DATETIME,
  user_id INT
);
#2018-04-07 增加操作员和楼栋分组
ALTER TABLE hotel.user ADD group_by VARCHAR(100) NULL;
ALTER TABLE hotel.user_log ADD group_by VARCHAR(100) NULL;
ALTER TABLE hotel.book ADD area VARCHAR(100) NULL;
#2018-04-11 增加夜审房间快照
create table room_snapshot
(
  id int not null auto_increment
    primary key,
  room_id varchar(100) null,
  report_time date null,
  category varchar(100) null,
  area varchar(100) null,
  state varchar(100) null,
  company varchar(100) null,
  empty tinyint(1) null,
  repair tinyint(1) null,
  self tinyint(1) null,
  back_up tinyint(1) null,
  rent tinyint(1) null,
  all_day_room tinyint(1) null,
  hour_room tinyint(1) null,
  add_room tinyint(1) null,
  night_room tinyint(1) null,
  all_day_room_consume double null,
  hour_room_consume double null,
  add_room_consume double null,
  night_room_consume double null,
  self_account varchar(100) null,
  group_account varchar(100) null,
  real_room tinyint(1) null
);
#2018-04-12 给debtPay表增加索引
CREATE INDEX debt_pay_self_account_index ON hotel.debt_pay (self_account);
CREATE INDEX debt_pay_group_account_index ON hotel.debt_pay (group_account);
CREATE INDEX room_snapshot_self_account_index ON hotel.room_snapshot (self_account);
CREATE INDEX room_snapshot_group_account_index ON hotel.room_snapshot (group_account);
#2018-04-15 给单位类别增加客源
ALTER TABLE hotel.company_category ADD guest_source VARCHAR(100) NULL;
ALTER TABLE hotel.guest_source ADD company_not_null BOOLEAN NULL;
#2018-04-16 增加已退款标志
ALTER TABLE hotel.debt ADD back BOOLEAN NULL;
ALTER TABLE hotel.debt_history ADD back BOOLEAN NULL;
CREATE OR REPLACE VIEW debt_integration AS
  SELECT
    `hotel`.`debt_history`.`id`            AS `id`,
    `hotel`.`debt_history`.`do_time`       AS `do_time`,
    `hotel`.`debt_history`.`point_of_sale` AS `point_of_sale`,
    `hotel`.`debt_history`.`consume`       AS `consume`,
    `hotel`.`debt_history`.`deposit`       AS `deposit`,
    `hotel`.`debt_history`.`currency`      AS `currency`,
    `hotel`.`debt_history`.`description`   AS `description`,
    `hotel`.`debt_history`.`self_account`  AS `self_account`,
    `hotel`.`debt_history`.`group_account` AS `group_account`,
    `hotel`.`debt_history`.`room_id`       AS `room_id`,
    `hotel`.`debt_history`.`area`          AS `area`,
    `hotel`.`debt_history`.`pay_serial`    AS `pay_serial`,
    `hotel`.`debt_history`.`protocol`      AS `protocol`,
    `hotel`.`debt_history`.`done_time`     AS `done_time`,
    `hotel`.`debt_history`.`user_id`       AS `user_id`,
    `hotel`.`debt_history`.`remark`        AS `remark`,
    `hotel`.`debt_history`.`bed`           AS `bed`,
    `hotel`.`debt_history`.`vip_number`    AS `vip_number`,
    `hotel`.`debt_history`.`category`      AS `category`,
    `hotel`.`debt_history`.`guest_source`  AS `guest_source`,
    `hotel`.`debt_history`.`company`       AS `company`,
    `hotel`.`debt_history`.`back`       AS `back`
  FROM `hotel`.`debt_history`
  UNION SELECT
          `hotel`.`debt`.`id`            AS `id`,
          `hotel`.`debt`.`do_time`       AS `do_time`,
          `hotel`.`debt`.`point_of_sale` AS `point_of_sale`,
          `hotel`.`debt`.`consume`       AS `consume`,
          `hotel`.`debt`.`deposit`       AS `deposit`,
          `hotel`.`debt`.`currency`      AS `currency`,
          `hotel`.`debt`.`description`   AS `description`,
          `hotel`.`debt`.`self_account`  AS `self_account`,
          `hotel`.`debt`.`group_account` AS `group_account`,
          `hotel`.`debt`.`room_id`       AS `room_id`,
          `hotel`.`debt`.`area`          AS `area`,
          `hotel`.`debt`.`pay_serial`    AS `pay_serial`,
          `hotel`.`debt`.`protocol`      AS `protocol`,
          NULL                           AS `done_time`,
          `hotel`.`debt`.`user_id`       AS `user_id`,
          `hotel`.`debt`.`remark`        AS `remark`,
          `hotel`.`debt`.`bed`           AS `bed`,
          `hotel`.`debt`.`vip_number`    AS `vip_number`,
          `hotel`.`debt`.`category`      AS `category`,
          `hotel`.`debt`.`guest_source`  AS `guest_source`,
          `hotel`.`debt`.`company`       AS `company`,
          `hotel`.`debt`.`back`       AS `back`
        FROM `hotel`.`debt`;
#2018-04-17 增加两个索引
CREATE INDEX debt_room_id_index ON debt (room_id);
CREATE INDEX debt_history_room_id_index ON debt_history (room_id);
#2018-04-19 修复库存盘点错误
CREATE OR REPLACE VIEW storage_remain AS
  SELECT
    `hotel`.`storage_in_detail`.`house`                    AS `house`,
    `hotel`.`storage_in_detail`.`cargo`                    AS `cargo`,
    `hotel`.`storage_in_detail`.`unit`                     AS `unit`,
    sum(`hotel`.`storage_in_detail`.`remain`)              AS `remain`,
    round((sum((`hotel`.`storage_in_detail`.`remain` * `hotel`.`storage_in_detail`.`price`)) /
           sum(`hotel`.`storage_in_detail`.`remain`)), 2) AS `price`
  FROM `hotel`.`storage_in_detail`
  GROUP BY `hotel`.`storage_in_detail`.`house`, `hotel`.`storage_in_detail`.`cargo`, `hotel`.`storage_in_detail`.`unit`;
CREATE OR REPLACE VIEW guest_integration AS
  SELECT
    `hotel`.`check_in_guest`.`card_id`          AS `card_id`,
    `hotel`.`check_in_guest`.`name`          AS `name`,
    `hotel`.`check_in_guest`.`country`          AS `country`,
    `hotel`.`guest_map_check_in`.`self_account` AS `self_account`,
    `check_in_integration`.`reach_time`         AS `reach_time`,
    `check_in_integration`.`guest_source`       AS `guest_source`,
    `check_in_integration`.`room_category`      AS `room_category`,
    1                                           AS `if_in`
  FROM ((`hotel`.`check_in_guest`
    LEFT JOIN `hotel`.`guest_map_check_in`
      ON ((`hotel`.`check_in_guest`.`card_id` = `hotel`.`guest_map_check_in`.`card_id`))) LEFT JOIN
    `hotel`.`check_in_integration`
      ON ((`hotel`.`guest_map_check_in`.`self_account` = `check_in_integration`.`self_account`)))
  UNION SELECT
          `hotel`.`check_in_history`.`card_id`        AS `card_id`,
          `hotel`.`check_in_history`.`name`        AS `name`,
          `hotel`.`check_in_history`.`country`        AS `country`,
          `hotel`.`guest_map_check_in`.`self_account` AS `self_account`,
          `check_in_integration`.`reach_time`         AS `reach_time`,
          `check_in_integration`.`guest_source`       AS `guest_source`,
          `check_in_integration`.`room_category`      AS `room_category`,
          0                                           AS `if_in`
        FROM ((`hotel`.`check_in_history`
          LEFT JOIN `hotel`.`guest_map_check_in`
            ON ((`hotel`.`check_in_history`.`card_id` = `hotel`.`guest_map_check_in`.`card_id`))) LEFT JOIN
          `hotel`.`check_in_integration`
            ON ((`hotel`.`guest_map_check_in`.`self_account` = `check_in_integration`.`self_account`)));
#2018-04-21 增加锁定结账功能
ALTER TABLE hotel.check_in ADD disable_check_out TEXT NULL;
#2018-04-24 重写转房客
ALTER TABLE debt ADD not_part_in BOOLEAN NULL;
ALTER TABLE debt_history ADD not_part_in BOOLEAN NULL;
CREATE or replace VIEW debt_integration AS
  SELECT
    `hotel`.`debt_history`.`id`            AS `id`,
    `hotel`.`debt_history`.`do_time`       AS `do_time`,
    `hotel`.`debt_history`.`point_of_sale` AS `point_of_sale`,
    `hotel`.`debt_history`.`consume`       AS `consume`,
    `hotel`.`debt_history`.`deposit`       AS `deposit`,
    `hotel`.`debt_history`.`currency`      AS `currency`,
    `hotel`.`debt_history`.`description`   AS `description`,
    `hotel`.`debt_history`.`self_account`  AS `self_account`,
    `hotel`.`debt_history`.`group_account` AS `group_account`,
    `hotel`.`debt_history`.`room_id`       AS `room_id`,
    `hotel`.`debt_history`.`area`          AS `area`,
    `hotel`.`debt_history`.`pay_serial`    AS `pay_serial`,
    `hotel`.`debt_history`.`protocol`      AS `protocol`,
    `hotel`.`debt_history`.`done_time`     AS `done_time`,
    `hotel`.`debt_history`.`user_id`       AS `user_id`,
    `hotel`.`debt_history`.`remark`        AS `remark`,
    `hotel`.`debt_history`.`bed`           AS `bed`,
    `hotel`.`debt_history`.`vip_number`    AS `vip_number`,
    `hotel`.`debt_history`.`category`      AS `category`,
    `hotel`.`debt_history`.`guest_source`  AS `guest_source`,
    `hotel`.`debt_history`.`company`       AS `company`,
    `hotel`.`debt_history`.`not_part_in`       AS `not_part_in`
  FROM `hotel`.`debt_history`
  UNION SELECT
          `hotel`.`debt`.`id`            AS `id`,
          `hotel`.`debt`.`do_time`       AS `do_time`,
          `hotel`.`debt`.`point_of_sale` AS `point_of_sale`,
          `hotel`.`debt`.`consume`       AS `consume`,
          `hotel`.`debt`.`deposit`       AS `deposit`,
          `hotel`.`debt`.`currency`      AS `currency`,
          `hotel`.`debt`.`description`   AS `description`,
          `hotel`.`debt`.`self_account`  AS `self_account`,
          `hotel`.`debt`.`group_account` AS `group_account`,
          `hotel`.`debt`.`room_id`       AS `room_id`,
          `hotel`.`debt`.`area`       AS `area`,
          `hotel`.`debt`.`pay_serial`    AS `pay_serial`,
          `hotel`.`debt`.`protocol`      AS `protocol`,
          NULL                           AS `done_time`,
          `hotel`.`debt`.`user_id`       AS `user_id`,
          `hotel`.`debt`.`remark`        AS `remark`,
          `hotel`.`debt`.`bed`           AS `bed`,
          `hotel`.`debt`.`vip_number`    AS `vip_number`,
          `hotel`.`debt`.`category`      AS `category`,
          `hotel`.`debt`.`guest_source`  AS `guest_source`,
          `hotel`.`debt`.`company`       AS `company`,
          `hotel`.`debt`.`not_part_in`  AS `not_part_in`
        FROM `hotel`.`debt`;
#2018-04-26 增加全单备注功能
ALTER TABLE hotel.desk_in ADD remark VARCHAR(100) NULL;
#2018-04-27 增加房吧营业部门
CREATE TABLE point_of_sale_shop
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(100),
  type VARCHAR(100)
);
ALTER TABLE room_shop ADD point_of_sale_shop VARCHAR(100) NULL;
ALTER TABLE room_shop_detail ADD point_of_sale_shop VARCHAR(100) NULL;
#2018-04-30 小数点保留两位
ALTER TABLE hotel.desk_in MODIFY consume DOUBLE(16,3);
#2018-05-02 哑房挂账全部设置为不参与统计，为华苑发生额报表做准备，以后所有账务必须带客源，没有客源会报错
UPDATE debt SET not_part_in=TRUE WHERE category='哑房挂账';
UPDATE debt_history SET not_part_in=TRUE WHERE category='哑房挂账';
UPDATE check_in set guest_source='未定义' WHERE guest_source is NULL;
ALTER TABLE hotel.check_in MODIFY guest_source VARCHAR(100) NOT NULL;
UPDATE check_in_history_log set guest_source='未定义' WHERE guest_source is NULL;
ALTER TABLE hotel.check_in_history_log MODIFY guest_source VARCHAR(100) NOT NULL;
UPDATE debt set guest_source='未定义' WHERE guest_source is NULL;
ALTER TABLE hotel.debt MODIFY guest_source VARCHAR(100) NOT NULL;
UPDATE debt_history set guest_source='未定义' WHERE guest_source is NULL;
ALTER TABLE hotel.debt_history MODIFY guest_source VARCHAR(100) NOT NULL;
ALTER TABLE hotel.room_snapshot ADD available BOOLEAN NULL;
ALTER TABLE hotel.room_snapshot ADD free BOOLEAN NULL;
#2018-05-03 华苑大报表
CREATE TABLE guest_snapshot
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  report_time DATE,
  come INT,
  exist INT
);
ALTER TABLE room_snapshot ADD guest_source VARCHAR(100) NULL;
#2018-05-04 准备华苑餐饮大报表
CREATE TABLE desk_guest_source
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(100)
);
ALTER TABLE desk_in ADD guest_source VARCHAR(100) NULL;
ALTER TABLE desk_in_history ADD guest_source VARCHAR(100) NULL;
ALTER TABLE desk_in ADD sub_desk_num INT NULL;
ALTER TABLE desk_in_history ADD sub_desk_num INT NULL;
#2018-05-07 增加在店户籍快照
-- auto-generated definition
create table check_in_snapshot
(
  id int not null auto_increment
    primary key,
  report_date DATE null,
  guest_name varchar(100) null,
  room_id varchar(5) null,
  room_category varchar(20) null,
  self_account varchar(20) null,
  group_account varchar(20) null,
  reach_time datetime not null,
  leave_time datetime not null,
  guest_source varchar(100) not null,
  important varchar(20) null,
  vip tinyint(1) null,
  breakfast varchar(1) null,
  remark varchar(200) null,
  final_room_price double null,
  company varchar(20) null,
  protocol varchar(100) null,
  vip_number varchar(20) null,
  deposit double null,
  consume double null,
  pay double null,
  room_price_category varchar(10) null,
  user_id varchar(10) null,
  group_name varchar(20) null,
  if_room tinyint(1) null,
  real_protocol varchar(20) null,
  area varchar(100) null,
  disable_check_out text null
);
#2018-05-08 增加整单退桌功能
CREATE TABLE desk_in_cancel_all
(
  id            INT AUTO_INCREMENT
    PRIMARY KEY,
  desk          VARCHAR(20)   NULL,
  do_time       DATETIME      NULL,
  num           VARCHAR(20)   NULL,
  consume       DOUBLE(16, 3) NULL,
  user_id       VARCHAR(20)   NULL,
  point_of_sale VARCHAR(20)   NULL,
  remark        VARCHAR(100)  NULL,
  guest_source  VARCHAR(100)  NULL,
  sub_desk_num  INT           NULL,
  CONSTRAINT desk_in_desk_uindex
  UNIQUE (desk)
)
  ENGINE = InnoDB;
-- auto-generated definition
CREATE TABLE desk_detail_cancel_all
(
  id            INT AUTO_INCREMENT
    PRIMARY KEY,
  food_name     VARCHAR(20)  NULL,
  price         DOUBLE       NULL,
  num           DOUBLE       NULL,
  desk          VARCHAR(20)  NULL,
  user_id       VARCHAR(20)  NULL,
  point_of_sale VARCHAR(20)  NULL,
  do_time       DATETIME     NULL,
  food_sign     VARCHAR(20)  NULL,
  category      VARCHAR(20)  NOT NULL,
  wait_call     TINYINT(1)   NULL,
  remark        VARCHAR(100) NULL,
  call_up       TINYINT(1)   NULL,
  unit          VARCHAR(10)  NULL,
  if_discount   TINYINT(1)   NULL,
  food_set      TINYINT(1)   NULL,
  cook_room     VARCHAR(200) NULL,
  cargo         TINYINT(1)   NULL,
  storage_done  TINYINT(1)   NULL,
  cooked        TINYINT(1)   NULL
)
  ENGINE = InnoDB;
ALTER TABLE desk_in_cancel_all ADD done_time DATETIME NULL;
ALTER TABLE desk_detail_cancel_all ADD done_time DATETIME NULL;
ALTER TABLE desk_in_cancel_all ADD user_id_done VARCHAR(100) NULL;
ALTER TABLE hotel.guest_map_check_in MODIFY self_account VARCHAR(20) NOT NULL;