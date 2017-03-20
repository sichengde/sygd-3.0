SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE `book_room` ADD COLUMN `total_bed`  int(11) NULL DEFAULT NULL AFTER `room_category`;
ALTER TABLE `check_in` ADD COLUMN `group_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `user_id`;
ALTER TABLE `check_in` ADD COLUMN `if_room`  tinyint(1) NULL DEFAULT NULL AFTER `group_name`;
ALTER TABLE `check_in_guest` MODIFY COLUMN `name`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `card_type`;
ALTER TABLE `check_in_history` MODIFY COLUMN `name`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `card_type`;
ALTER TABLE `check_in_history` ADD COLUMN `num`  int(11) NULL DEFAULT NULL AFTER `country`;
ALTER TABLE `check_in_history_log` ADD COLUMN `if_room`  tinyint(1) NULL DEFAULT NULL AFTER `breakfast`;
CREATE UNIQUE INDEX `company_name_uindex` ON `company`(`name`) USING BTREE ;
ALTER TABLE `company_debt` ADD COLUMN `point_of_sale`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `description`;
ALTER TABLE `company_debt` ADD COLUMN `current_remain`  double NULL DEFAULT NULL AFTER `point_of_sale`;
ALTER TABLE `company_debt` ADD COLUMN `second_point_of_sale`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `current_remain`;
ALTER TABLE `company_debt` DROP COLUMN `deposit`;
ALTER TABLE `company_debt` DROP COLUMN `category`;
ALTER TABLE `company_debt` DROP COLUMN `currency`;
ALTER TABLE `company_debt` DROP COLUMN `pay`;
CREATE TABLE `company_debt_history` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`company`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lord`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pay_serial`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`debt`  double NULL DEFAULT NULL ,
`do_time`  datetime NULL DEFAULT NULL ,
`done_time`  datetime NULL DEFAULT NULL ,
`user_id`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`description`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`point_of_sale`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`company_pay_serial`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`current_remain`  double NULL DEFAULT NULL ,
`second_point_of_sale`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;
CREATE TABLE `company_pay` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`company_pay_serial`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`debt`  double NULL DEFAULT NULL ,
`pay`  double NULL DEFAULT NULL ,
`currency`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`currency_add`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`remark`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`done_time`  datetime NULL DEFAULT NULL ,
`company`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;
ALTER TABLE `cook_room` MODIFY COLUMN `printer`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `cook_name`;
ALTER TABLE `debt` ADD COLUMN `guest_source`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `done_time`;
ALTER TABLE `debt_history` ADD COLUMN `guest_source`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `from_room`;
ALTER TABLE `debt_history` ADD COLUMN `company_paid`  tinyint(1) NULL DEFAULT NULL AFTER `guest_source`;
ALTER TABLE `desk_book` DROP COLUMN `categoryParse`;
ALTER TABLE `desk_book_history` DROP COLUMN `categoryParse`;
ALTER TABLE `desk_detail` ADD COLUMN `cooked`  tinyint(1) NULL DEFAULT NULL AFTER `storage_done`;
ALTER TABLE `desk_detail` DROP COLUMN `categoryParse`;
ALTER TABLE `desk_detail_history` DROP COLUMN `categoryParse`;
ALTER TABLE `desk_detail_history` DROP COLUMN `categoryRoom`;
CREATE TABLE `guest_map_check_in` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`card_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`self_account`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;
ALTER TABLE `guest_source` ADD COLUMN `count_category`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `guest_source`;
CREATE TABLE `interface_door` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`room_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`door_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`tel_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
UNIQUE INDEX `interface_door_room_id_uindex` (`room_id`) USING BTREE ,
UNIQUE INDEX `interface_door_door_id_uindex` (`door_id`) USING BTREE ,
UNIQUE INDEX `interface_door_tel_id_uindex` (`tel_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;
ALTER TABLE `menu` ADD COLUMN `remain`  int(11) NULL DEFAULT NULL AFTER `cargo`;
CREATE TABLE `menu_cost` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`food`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`cargo`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`num`  double NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;
ALTER TABLE `room` MODIFY COLUMN `floor`  int(11) NULL DEFAULT NULL AFTER `today_lock`;
ALTER TABLE `room` ADD COLUMN `if_room`  tinyint(1) NOT NULL AFTER `floor`;
CREATE TABLE `room_state_report` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`category`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`total`  int(11) NULL DEFAULT NULL ,
`empty`  int(11) NULL DEFAULT NULL ,
`repair`  int(11) NULL DEFAULT NULL ,
`self`  int(11) NULL DEFAULT NULL ,
`back_up`  int(11) NULL DEFAULT NULL ,
`report_time`  date NULL DEFAULT NULL ,
`rent`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact
;
ALTER TABLE `sauna_detail` MODIFY COLUMN `sauna_user`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `storage_done`;
ALTER TABLE `sauna_detail_history` MODIFY COLUMN `sauna_user`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `disabled`;
CREATE TABLE `sauna_pay` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`done_time`  datetime NULL DEFAULT NULL ,
`pay_money`  double NULL DEFAULT NULL ,
`currency`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`currency_add`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sauna_serial`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`disabled`  tinyint(1) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;
ALTER TABLE `sauna_ring` DROP INDEX `sauna_ring_number_uindex`;
ALTER TABLE `sauna_ring` MODIFY COLUMN `sex`  varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `number`;
CREATE UNIQUE INDEX `sauna_key_number_uindex` ON `sauna_ring`(`number`) USING BTREE ;
CREATE UNIQUE INDEX `sauna_user_idNumber_uindex` ON `sauna_user`(`id_number`) USING BTREE ;
ALTER TABLE `serial` MODIFY COLUMN `sauna_group_serial`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `storage_out_serial`;
ALTER TABLE `serial` ADD COLUMN `company_pay_serial`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `sauna_out_serial`;
ALTER TABLE `storage_out_detail` ADD COLUMN `my_usage`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `total`;
ALTER TABLE `storage_out_detail` ADD COLUMN `sale_total`  double NULL DEFAULT NULL AFTER `category`;
ALTER TABLE `storage_out_detail` DROP COLUMN `usage`;
CREATE TABLE `tel_detail` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`tel_id`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`tel_time`  datetime NULL DEFAULT NULL ,
`duration`  int(11) NULL DEFAULT NULL ,
`price`  double NULL DEFAULT NULL ,
`target_number`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`total_price`  double NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;
ALTER TABLE `user` ADD COLUMN `max_discount`  int(11) NULL DEFAULT NULL AFTER `point_of_sale_array`;
ALTER TABLE `vip` MODIFY COLUMN `vip_number`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AFTER `id`;
ALTER TABLE `vip_id_number` MODIFY COLUMN `id_number`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `id`;
ALTER
ALGORITHM=UNDEFINED
DEFINER=`hotel`@`%`
SQL SECURITY DEFINER
VIEW `check_in_integration` AS
select `ci`.`room_id` AS `room_id`,`ci`.`room_category` AS `room_category`,`ci`.`final_room_price` AS `final_room_price`,`ci`.`reach_time` AS `reach_time`,`ci`.`leave_time` AS `leave_time`,`ci`.`remark` AS `remark`,`ci`.`self_account` AS `self_account`,`ci`.`guest_source` AS `guest_source`,`ci`.`consume` AS `consume`,`ci`.`if_room` AS `if_room`,`ci`.`user_id` AS `user_id`,1 AS `if_in` from `check_in` `ci` union select `cihl`.`room_id` AS `room_id`,`cihl`.`room_category` AS `room_category`,`cihl`.`final_room_price` AS `final_room_price`,`cihl`.`reach_time` AS `reach_time`,`cihl`.`leave_time` AS `leave_time`,`cihl`.`remark` AS `remark`,`cihl`.`self_account` AS `self_account`,`cihl`.`guest_source` AS `guest_source`,`cihl`.`consume` AS `consume`,`cihl`.`if_room` AS `if_room`,`cihl`.`user_id` AS `user_id`,0 AS `if_in` from `check_in_history_log` `cihl` ;
CREATE
ALGORITHM=UNDEFINED
DEFINER=`hotel`@`%`
SQL SECURITY DEFINER
VIEW `company_debt_integration`AS
select `company_debt`.`id` AS `id`,`company_debt`.`company` AS `company`,`company_debt`.`lord` AS `lord`,`company_debt`.`pay_serial` AS `pay_serial`,`company_debt`.`debt` AS `debt`,`company_debt`.`do_time` AS `do_time`,`company_debt`.`user_id` AS `user_id`,`company_debt`.`description` AS `description`,`company_debt`.`point_of_sale` AS `point_of_sale`,`company_debt`.`current_remain` AS `current_remain`,0 AS `company_paid` from `company_debt` union select `company_debt_history`.`id` AS `id`,`company_debt_history`.`company` AS `company`,`company_debt_history`.`lord` AS `lord`,`company_debt_history`.`pay_serial` AS `pay_serial`,`company_debt_history`.`debt` AS `debt`,`company_debt_history`.`do_time` AS `do_time`,`company_debt_history`.`user_id` AS `user_id`,`company_debt_history`.`description` AS `description`,`company_debt_history`.`point_of_sale` AS `point_of_sale`,`company_debt_history`.`current_remain` AS `current_remain`,1 AS `company_paid` from `company_debt_history` ;
CREATE
ALGORITHM=UNDEFINED
DEFINER=`hotel`@`%`
SQL SECURITY DEFINER
VIEW `company_debt_rich`AS
select distinct ifnull(`dh`.`id`,`cdi`.`id`) AS `id`,`cdi`.`company` AS `company`,`cdi`.`lord` AS `lord`,`cdi`.`pay_serial` AS `pay_serial`,if((`dh`.`consume` > 0),`dh`.`consume`,`cdi`.`debt`) AS `debt`,`cdi`.`do_time` AS `company_do_time`,concat(`cdi`.`description`,',',ifnull(`dh`.`description`,'')) AS `description`,`cdi`.`point_of_sale` AS `point_of_sale`,`dh`.`do_time` AS `debt_do_time`,`dh`.`point_of_sale` AS `second_point_of_sale`,`dh`.`self_account` AS `self_account`,`dh`.`group_account` AS `group_account`,`dh`.`room_id` AS `room_id`,`cdi`.`user_id` AS `user_id`,`dh`.`category` AS `category`,`dh`.`remark` AS `remark`,if(isnull(`dh`.`pay_serial`),`cdi`.`company_paid`,`dh`.`company_paid`) AS `company_paid`,`name`.`name` AS `name` from ((`hotel`.`company_debt_integration` `cdi` left join `hotel`.`debt_history` `dh` on((`cdi`.`pay_serial` = `dh`.`pay_serial`))) left join (select group_concat(`map`.`name` separator ',') AS `name`,`map`.`self_account` AS `self_account` from (select `hotel`.`check_in_history`.`card_type` AS `card_type`,`hotel`.`check_in_history`.`name` AS `name`,`hotel`.`check_in_history`.`birthday_time` AS `birthday_time`,`hotel`.`check_in_history`.`sex` AS `sex`,`hotel`.`check_in_history`.`race` AS `race`,`hotel`.`check_in_history`.`address` AS `address`,`hotel`.`check_in_history`.`phone` AS `phone`,`hotel`.`check_in_history`.`last_time` AS `last_time`,`hotel`.`check_in_history`.`door_id` AS `door_id`,`hotel`.`check_in_history`.`bed` AS `bed`,`hotel`.`check_in_history`.`country` AS `country`,`hotel`.`check_in_history`.`num` AS `num`,`hotel`.`guest_map_check_in`.`self_account` AS `self_account` from (`hotel`.`check_in_history` left join `hotel`.`guest_map_check_in` on((`hotel`.`check_in_history`.`card_id` = `hotel`.`guest_map_check_in`.`card_id`)))) `map` group by `map`.`self_account`) `name` on((`dh`.`self_account` = `name`.`self_account`))) where isnull(`dh`.`deposit`) order by `cdi`.`company` desc,`cdi`.`do_time` desc ;
ALTER
ALGORITHM=UNDEFINED
DEFINER=`hotel`@`%`
SQL SECURITY DEFINER
VIEW `debt_integration` AS
select `debt_history`.`id` AS `id`,`debt_history`.`do_time` AS `do_time`,`debt_history`.`point_of_sale` AS `point_of_sale`,`debt_history`.`consume` AS `consume`,`debt_history`.`deposit` AS `deposit`,`debt_history`.`currency` AS `currency`,`debt_history`.`description` AS `description`,`debt_history`.`self_account` AS `self_account`,`debt_history`.`group_account` AS `group_account`,`debt_history`.`room_id` AS `room_id`,`debt_history`.`pay_serial` AS `pay_serial`,`debt_history`.`protocol` AS `protocol`,`debt_history`.`done_time` AS `done_time`,`debt_history`.`user_id` AS `user_id`,`debt_history`.`remark` AS `remark`,`debt_history`.`bed` AS `bed`,`debt_history`.`vip_number` AS `vip_number`,`debt_history`.`category` AS `category`,`debt_history`.`guest_source` AS `guest_source` from `debt_history` union select `debt`.`id` AS `id`,`debt`.`do_time` AS `do_time`,`debt`.`point_of_sale` AS `point_of_sale`,`debt`.`consume` AS `consume`,`debt`.`deposit` AS `deposit`,`debt`.`currency` AS `currency`,`debt`.`description` AS `description`,`debt`.`self_account` AS `self_account`,`debt`.`group_account` AS `group_account`,`debt`.`room_id` AS `room_id`,`debt`.`pay_serial` AS `pay_serial`,`debt`.`protocol` AS `protocol`,NULL AS `done_time`,`debt`.`user_id` AS `user_id`,`debt`.`remark` AS `remark`,`debt`.`bed` AS `bed`,`debt`.`vip_number` AS `vip_number`,`debt`.`category` AS `category`,`debt`.`guest_source` AS `guest_source` from `debt` ;
CREATE
ALGORITHM=UNDEFINED
DEFINER=`hotel`@`%`
SQL SECURITY DEFINER
VIEW `desk_pay_rich`AS
select `desk_pay`.`pay_money` AS `pay_money`,`desk_pay`.`currency` AS `currency`,`desk_pay`.`currency_add` AS `currency_add`,`desk_in_history`.`id` AS `id`,`desk_in_history`.`ck_serial` AS `ck_serial`,`desk_in_history`.`do_time` AS `do_time`,`desk_in_history`.`done_time` AS `done_time`,`desk_in_history`.`total_price` AS `total_price`,`desk_in_history`.`final_price` AS `final_price`,`desk_in_history`.`discount` AS `discount`,`desk_in_history`.`desk` AS `desk`,`desk_in_history`.`user_id` AS `user_id`,`desk_in_history`.`point_of_sale` AS `point_of_sale`,`desk_in_history`.`num` AS `num`,`desk_in_history`.`disabled` AS `disabled` from (`desk_pay` left join `desk_in_history` on((`desk_pay`.`ck_serial` = `desk_in_history`.`ck_serial`))) ;
ALTER
ALGORITHM=UNDEFINED
DEFINER=`hotel`@`%`
SQL SECURITY DEFINER
VIEW `guest_integration` AS
select `check_in_guest`.`card_id` AS `card_id`,`check_in_guest`.`country` AS `country`,`guest_map_check_in`.`self_account` AS `self_account`,`check_in_integration`.`reach_time` AS `reach_time` from ((`check_in_guest` left join `guest_map_check_in` on((`check_in_guest`.`card_id` = `guest_map_check_in`.`card_id`))) left join `check_in_integration` on((`guest_map_check_in`.`self_account` = `check_in_integration`.`self_account`))) union select `check_in_history`.`card_id` AS `card_id`,`check_in_history`.`country` AS `country`,`guest_map_check_in`.`self_account` AS `self_account`,`check_in_integration`.`reach_time` AS `reach_time` from ((`check_in_history` left join `guest_map_check_in` on((`check_in_history`.`card_id` = `guest_map_check_in`.`card_id`))) left join `check_in_integration` on((`guest_map_check_in`.`self_account` = `check_in_integration`.`self_account`))) ;
SET FOREIGN_KEY_CHECKS=1;