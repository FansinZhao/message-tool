-- use mysql;
-- GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'IDENTIFIED BY 'root' WITH GRANT OPTION;
-- FLUSH PRIVILEGES

SET NAMES utf8;

CREATE DATABASE if not exists `msg`
  CHARACTER SET 'utf8'
  COLLATE 'utf8_general_ci';
use msg;
-- ----------------------------
-- Table structure for t_clr_order
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id`                bigint(19)  NOT NULL AUTO_INCREMENT,
  `name`          varchar(20) NOT NULL
  COMMENT '用户名',
  `id_card`          varchar(20)          DEFAULT NULL
  COMMENT '身份证',
  `bank_card`           varchar(20)          DEFAULT NULL
  COMMENT '银行卡',
  `mobile_phone`      varchar(25)          DEFAULT NULL
  COMMENT '手机号',
  `age`     varchar(11)          DEFAULT NULL
  COMMENT '资金方编码',
  `home` varchar(20)          DEFAULT NULL
  COMMENT '家庭住址',
  `office`         decimal(20, 2)       DEFAULT NULL
  COMMENT '工作地址',
  `token`            varchar(20)          DEFAULT NULL
  COMMENT 'token',
  `create_datetime`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建日期时间',
  `update_datetime`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新日期时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_CREATE_DATETIME` (`create_datetime`),
  KEY `INDEX_UPDATE_DATETIME` (`update_datetime`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8
  COMMENT = '用户信息表';