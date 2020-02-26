/*
Navicat MySQL Data Transfer

Source Server         : ChinaEduction
Source Server Version : 50719
Source Host           : 127.0.0.1:3306
Source Database       : taoshu

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-02-26 10:50:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `admin_password` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `telephone` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'lipi', '111', 'male', '336@qq.com', '13622913758');
INSERT INTO `admin` VALUES ('3', 'ping', '111', 'female', '323@qq.com', '13666663333');
INSERT INTO `admin` VALUES ('6', 'ab', '12', 'female', '11323@qq.com', '13666666666');
INSERT INTO `admin` VALUES ('7', '成龙', '123', 'female', '35656@qq.coom', '13333333333');
INSERT INTO `admin` VALUES ('8', '李连杰', '123', 'male', '3056@qq.com', '13666666666');
INSERT INTO `admin` VALUES ('9', 'ppp', '1111', 'male', '3030@qq.com', '13333333333');
INSERT INTO `admin` VALUES ('10', '平少', '111', 'male', '30488@qq.com', '13333333333');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '小说');
INSERT INTO `category` VALUES ('2', '文艺');
INSERT INTO `category` VALUES ('3', '人文社科');
INSERT INTO `category` VALUES ('4', '经管');
INSERT INTO `category` VALUES ('5', '计算机编程语言');
INSERT INTO `category` VALUES ('6', '生活用书');

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem` (
  `itemid` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `oid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  KEY `fk_0001` (`pid`),
  KEY `fk_0002` (`oid`),
  CONSTRAINT `fk_0001` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `fk_0002` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('60', '1', '28.9', '171', '0ce06905-dbb5-4960-83e5-c3fd322e8e92');
INSERT INTO `orderitem` VALUES ('61', '1', '51', '1', '2bb3b234-effd-4341-b275-2670fb0ec0db');
INSERT INTO `orderitem` VALUES ('62', '1', '115', '162', '2bb3b234-effd-4341-b275-2670fb0ec0db');
INSERT INTO `orderitem` VALUES ('63', '1', '83.5', '172', '105c9148-74fd-4d32-94c1-d2304781a144');
INSERT INTO `orderitem` VALUES ('64', '1', '56.36', '322', 'c7ec2bb8-548c-43b7-89f5-a08747113c69');
INSERT INTO `orderitem` VALUES ('65', '1', '56.36', '322', 'f5f8f016-b886-4f7e-b69d-24090fffc8ce');
INSERT INTO `orderitem` VALUES ('66', '1', '56.36', '322', '0e0482cc-a4fa-4a80-adb2-9d3d447acfee');
INSERT INTO `orderitem` VALUES ('67', '1', '56.36', '322', 'd5b4bbc8-6de1-4af1-a83e-9b0b83cef37c');
INSERT INTO `orderitem` VALUES ('68', '1', '28.9', '171', '14b809d7-a60b-4172-a65a-25c4adcced58');
INSERT INTO `orderitem` VALUES ('69', '1', '51', '1', '1fd9ff33-53a3-4938-bb6d-834b0ebf7781');
INSERT INTO `orderitem` VALUES ('70', '2', '130', '83', '3d9e187d-b29e-4027-bfdb-f8cffba85853');
INSERT INTO `orderitem` VALUES ('71', '1', '28.9', '171', '3d9e187d-b29e-4027-bfdb-f8cffba85853');
INSERT INTO `orderitem` VALUES ('72', '1', '65', '83', '504ac0d6-156a-4b09-bba6-9ca6c62eb300');
INSERT INTO `orderitem` VALUES ('73', '1', '51', '1', 'f02ef73b-0ee7-492a-b7fd-3b0fa50574ea');
INSERT INTO `orderitem` VALUES ('74', '1', '65', '83', '5377f3f3-274d-4972-bfdf-58e7be611603');
INSERT INTO `orderitem` VALUES ('75', '1', '148', '82', '36c8af25-63c5-47de-a2c5-12780028701e');
INSERT INTO `orderitem` VALUES ('76', '1', '65', '83', '9fab3b00-5b35-47b5-91ab-ee51c2cdf221');
INSERT INTO `orderitem` VALUES ('77', '1', '31', '81', '56d6a60c-85fa-41b6-b5b5-df86520c9181');
INSERT INTO `orderitem` VALUES ('78', '1', '65', '83', '6a8c7788-1fa1-4d64-ab46-1232e347a8fd');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `oid` varchar(50) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `payment_type` int(2) DEFAULT NULL COMMENT '支付类型，1、货到付款，2、在线支付',
  `status` int(11) DEFAULT NULL,
  `receiver_name` varchar(20) DEFAULT NULL,
  `receiver_phone` varchar(20) DEFAULT NULL,
  `receiver_address` varchar(30) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('0ce06905-dbb5-4960-83e5-c3fd322e8e92', '2018-12-09 12:14:09', '28.9', '1', '0', '平', '13333333333', '广东阳江', '1');
INSERT INTO `orders` VALUES ('0e0482cc-a4fa-4a80-adb2-9d3d447acfee', '2019-05-02 09:23:12', '56.36', '1', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('105c9148-74fd-4d32-94c1-d2304781a144', '2019-04-30 17:48:00', '83.5', '1', '0', '李耀平', '13622913758', '广东省阳江市新洲镇', '7');
INSERT INTO `orders` VALUES ('14b809d7-a60b-4172-a65a-25c4adcced58', '2019-05-03 10:05:59', '28.9', '1', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('1fd9ff33-53a3-4938-bb6d-834b0ebf7781', '2019-05-03 18:46:27', '51', '1', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('2bb3b234-effd-4341-b275-2670fb0ec0db', '2018-12-09 12:14:46', '166', '1', '0', '平', '13333333333', '广东阳江', '1');
INSERT INTO `orders` VALUES ('36c8af25-63c5-47de-a2c5-12780028701e', '2020-02-25 14:02:40', '148', '1', '0', '胖子', '16666666666', '广东广州', '2');
INSERT INTO `orders` VALUES ('3d9e187d-b29e-4027-bfdb-f8cffba85853', '2019-05-07 06:54:22', '158.9', '1', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('504ac0d6-156a-4b09-bba6-9ca6c62eb300', '2019-05-07 07:06:45', '65', '1', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('5377f3f3-274d-4972-bfdf-58e7be611603', '2019-05-07 11:02:10', '65', '2', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('56d6a60c-85fa-41b6-b5b5-df86520c9181', '2020-02-25 17:38:51', '31', '1', '0', '胖子', '16666666666', '广东广州', '2');
INSERT INTO `orders` VALUES ('6a8c7788-1fa1-4d64-ab46-1232e347a8fd', '2020-02-25 17:39:20', '65', '2', '0', '胖子', '16666666666', '广东广州', '2');
INSERT INTO `orders` VALUES ('9fab3b00-5b35-47b5-91ab-ee51c2cdf221', '2020-02-25 14:05:04', '65', '2', '0', '胖子', '16666666666', '广东广州', '2');
INSERT INTO `orders` VALUES ('c7ec2bb8-548c-43b7-89f5-a08747113c69', '2019-05-02 09:21:54', '56.36', '2', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('d5b4bbc8-6de1-4af1-a83e-9b0b83cef37c', '2019-05-02 09:23:55', '56.36', '1', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('f02ef73b-0ee7-492a-b7fd-3b0fa50574ea', '2019-05-07 10:39:38', '51', '2', '0', '王晓辉', '15079254030', '江西省九江市', '112');
INSERT INTO `orders` VALUES ('f5f8f016-b886-4f7e-b69d-24090fffc8ce', '2019-05-02 09:22:43', '56.36', '1', '0', '王晓辉', '15079254030', '江西省九江市', '112');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) DEFAULT NULL,
  `market_price` double DEFAULT NULL,
  `shop_price` double DEFAULT NULL,
  `pimage` varchar(200) DEFAULT NULL,
  `pdate` date DEFAULT NULL,
  `is_hot` int(11) DEFAULT NULL,
  `pdesc` varchar(255) DEFAULT NULL,
  `pflag` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `sfk_0001` (`cid`),
  CONSTRAINT `sfk_0001` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '永恒的时光之旅', '55', '51', 'img/product/sj/sj001.jpg', '2018-02-11', '1', '永恒的时光之旅', '0', '2');
INSERT INTO `product` VALUES ('2', '走进一座大教堂', '66', '46', 'img/product/sj/sj002.jpg', '2018-01-11', '1', '走进一座大教堂', '1', '2');
INSERT INTO `product` VALUES ('3', '艾略特·厄威特的纽约', '176', '166', 'img/product/sj/sj003.jpg', '2018-01-20', '0', '艾略特·厄威特的纽约', '1', '2');
INSERT INTO `product` VALUES ('41', '三国志', '189', '168', 'img/product/dn/dn001.jpg', '2018-01-15', '1', '三国志（文白对照 套装全3册 京东定制 2018精装版）', '1', '3');
INSERT INTO `product` VALUES ('42', '田一可说弟子规', '89', '88', 'img/product/dn/dn002.jpg', '2018-01-20', '0', '田一可说弟子规', '1', '3');
INSERT INTO `product` VALUES ('43', '华杉讲透 孟子', '76', '75', 'img/product/dn/dn003.jpg', '2018-01-16', '0', '华杉讲透 孟子', '1', '3');
INSERT INTO `product` VALUES ('81', '国民大饭店之暗流', '32', '31', 'img/product/jd/jd001.jpg', '2018-01-15', '0', '国民大饭店之暗流', '1', '1');
INSERT INTO `product` VALUES ('82', '银河帝国', '149', '148', 'img/product/jd/jd002.jpg', '2018-01-15', '1', ' 银河帝国：基地七部曲（新版 套装共7册）', '1', '1');
INSERT INTO `product` VALUES ('83', '倒悬的地平线 ', '68', '65', 'img/product/jd/jd003.jpg', '2018-01-21', '0', '倒悬的地平线 ', '1', '1');
INSERT INTO `product` VALUES ('84', '魔戒', '168', '132', 'img/product/jd/jd004.jpg', '2018-01-20', '0', '魔戒', '1', '1');
INSERT INTO `product` VALUES ('85', '北欧众神', '153', '152', 'img/product/jd/jd005.jpg', '2018-01-15', '0', '北欧众神', '1', '1');
INSERT INTO `product` VALUES ('86', '龙族3：黑月之潮', '143', '123', 'img/product/jd/jd006.jpg', '2018-01-15', '1', '龙族3：黑月之潮', '1', '1');
INSERT INTO `product` VALUES ('87', '古龙经典·绝代双骄', '152', '142', 'img/product/jd/jd007.jpg', '2018-01-20', '1', '古龙经典·绝代双骄（共四册）（热血版）', '1', '1');
INSERT INTO `product` VALUES ('88', '元尊4 蛟龙得水', '88', '48', 'img/product/jd/jd008.jpg', '2018-01-15', '0', '元尊4 蛟龙得水', '1', '1');
INSERT INTO `product` VALUES ('89', '刀背藏身', '89', '76', 'img/product/jd/jd009.jpg', '2018-01-15', '0', '刀背藏身：徐皓峰武侠短篇集（新旧版本，随机发货）', '1', '1');
INSERT INTO `product` VALUES ('90', '四大名捕逆水寒', '145', '125', 'img/product/jd/jd010.jpg', '2018-01-15', '0', '四大名捕逆水寒（套装3册）', '1', '1');
INSERT INTO `product` VALUES ('91', '围城', '153', '152', 'img/product/jd/jd011.jpg', '2018-01-15', '0', '围城（平装）', '1', '1');
INSERT INTO `product` VALUES ('92', '偷影子的人', '168', '143', 'img/product/jd/jd012.jpg', '2018-01-15', '0', '偷影子的人（2018年版）', '1', '1');
INSERT INTO `product` VALUES ('93', '东野圭吾留给孩子的作品系', '198', '189', 'img/product/jd/jd013.jpg', '2018-01-15', '1', '东野圭吾留给孩子的作品系（套装共7册）', '1', '1');
INSERT INTO `product` VALUES ('94', '阿弥陀佛么么哒', '125', '112', 'img/product/jd/jd014.jpg', '2018-01-15', '0', '阿弥陀佛么么哒', '1', '1');
INSERT INTO `product` VALUES ('95', '追风筝的人', '145', '125', 'img/product/jd/jd015.jpg', '2018-01-15', '0', '追风筝的人（中英双语版）', '1', '1');
INSERT INTO `product` VALUES ('121', '数据资本时代', '89', '79', 'img/product/ju/ju001.jpg', '2018-01-15', '0', '数据资本时代', '1', '4');
INSERT INTO `product` VALUES ('122', '读懂中国经济', '139', '129', 'img/product/ju/ju002.jpg', '2018-01-15', '0', '读懂中国经济', '1', '4');
INSERT INTO `product` VALUES ('123', '大破局：中国经济新机遇', '129', '119', 'img/product/ju/ju003.jpg', '2018-01-15', '0', '大破局：中国经济新机遇', '1', '4');
INSERT INTO `product` VALUES ('161', 'Python编程 从入门到实践', '89', '79', 'img/product/qc/qc001.jpg', '2018-01-15', '0', 'Python编程 从入门到实践', '1', '5');
INSERT INTO `product` VALUES ('162', 'Java编程思想（第4版） ', '125', '115', 'img/product/qc/qc002.jpg', '2018-01-15', '0', 'Java编程思想（第4版） [thinking in java]', '1', '5');
INSERT INTO `product` VALUES ('163', '绿联40998数据结构与算法分析', '49', '48', 'img/product/qc/qc003.jpg', '2018-01-15', '0', '数据结构与算法分析：Java语言描述（原书第3版）', '1', '5');
INSERT INTO `product` VALUES ('171', '新编百姓最爱家常菜2888例', '32', '28.9', 'img/product/ts/ts001.jpg', '2018-01-21', '0', '新编百姓最爱家常菜2888例', '1', '6');
INSERT INTO `product` VALUES ('172', '时尚新厨房：花式营养早餐一本全', '85.4', '83.5', 'img/product/ts/ts002.jpg', '2018-01-20', '1', '时尚新厨房：花式营养早餐一本全', '1', '6');
INSERT INTO `product` VALUES ('173', '零起点茶艺全书：从喝茶到懂茶', '47.5', '46.8', 'img/product/ts/ts003.jpg', '2018-01-16', '0', '零起点茶艺全书：从喝茶到懂茶', '1', '6');
INSERT INTO `product` VALUES ('322', '中国好声音', '25.36', '56.36', 'img/uploadpicture/1f06cadc-f034-42ff-89a9-80692cc8c9f9.jpg', '2019-04-30', '1', '这是一本很好的书', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL COMMENT '用户的收货地址',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'pi', '111', '平', 'male', '30678977@qq.com', '1994-01-14', '13333333333', '广东阳江');
INSERT INTO `user` VALUES ('2', 'pangzi', '222', '胖子', 'male', '464678@qq.com', '1995-02-14', '16666666666', '广东广州');
INSERT INTO `user` VALUES ('3', 'liyaoping', '111', '李耀平', 'male', '3046686923@qq.com', '1994-01-14', '13763052642', '广东省阳江市新洲镇葛边上村1号');
INSERT INTO `user` VALUES ('4', '平少', '222', '平少', 'male', '3046687978@qq.com', '1995-02-16', '13468556565', '广东省广州市');
INSERT INTO `user` VALUES ('5', '李伟', '666', '李伟', 'male', '46461313@qq.com', '1956-01-18', '16666666666', '广东省湛江市麻章区学智路2号');
INSERT INTO `user` VALUES ('6', 'ping', '888', '平', 'male', '304646466@qq.com', '1994-02-16', '16666666666', '广东省阳江市大沟镇');
INSERT INTO `user` VALUES ('7', '李耀平', '111', '李耀平', 'male', '888888888@qq.com', '1995-02-01', '13622913758', '广东省阳江市新洲镇');
INSERT INTO `user` VALUES ('8', '小强', '333', '光头强', 'male', '46464697@qq.com', '1994-10-19', '17777777777', '广东省惠州市');
INSERT INTO `user` VALUES ('9', 'xiaoting', '666', '小婷', 'female', '9999999@qq.ccom', '1995-02-10', '16666668888', '广东省阳江市塘围');
INSERT INTO `user` VALUES ('10', '小明', '999', '假小明', 'female', '9896565656@qq.com', '1997-02-12', '13656458799', '广东省江门市');
INSERT INTO `user` VALUES ('11', '蒋梅香', '555', '蒋梅香', 'female', '3046686923@qq.com', '1995-03-16', '13763052642', '广东省阳江市新洲镇表竹村1号');
INSERT INTO `user` VALUES ('12', '褚明艳', '556', '褚明艳', 'female', '3046686924@qq.com', '1995-03-17', '13763052643', '广东省阳江市新洲镇表竹村2号');
INSERT INTO `user` VALUES ('13', '钱程悦', '557', '钱程悦', 'female', '3046686925@qq.com', '1995-03-18', '13763052644', '广东省阳江市新洲镇表竹村3号');
INSERT INTO `user` VALUES ('14', '谢灿灿', '558', '谢灿灿', 'female', '3046686926@qq.com', '1995-03-19', '13763052645', '广东省阳江市新洲镇表竹村4号');
INSERT INTO `user` VALUES ('15', '郁倩倩', '559', '郁倩倩', 'female', '3046686927@qq.com', '1995-03-20', '13763052646', '广东省阳江市新洲镇表竹村5号');
INSERT INTO `user` VALUES ('16', '郑荔', '560', '郑荔', 'female', '3046686928@qq.com', '1995-03-21', '13763052647', '广东省阳江市新洲镇表竹村6号');
INSERT INTO `user` VALUES ('17', '王倩倩', '561', '王倩倩', 'female', '3046686929@qq.com', '1995-03-22', '13763052648', '广东省阳江市新洲镇表竹村7号');
INSERT INTO `user` VALUES ('18', '赵露', '562', '赵露', 'female', '3046686930@qq.com', '1995-03-23', '13763052649', '广东省阳江市新洲镇表竹村8号');
INSERT INTO `user` VALUES ('19', '姜筠', '563', '姜筠', 'male', '3046686931@qq.com', '1995-03-24', '13763052650', '广东省阳江市新洲镇表竹村9号');
INSERT INTO `user` VALUES ('20', '尚安琪', '564', '尚安琪', 'male', '3046686932@qq.com', '1995-03-25', '13763052651', '广东省阳江市新洲镇表竹村10号');
INSERT INTO `user` VALUES ('21', '蒋丹', '565', '蒋丹', 'male', '3046686933@qq.com', '1995-03-26', '13763052652', '广东省阳江市新洲镇表竹村11号');
INSERT INTO `user` VALUES ('22', '卫朱婷', '566', '卫朱婷', 'male', '3046686934@qq.com', '1995-03-27', '13763052653', '广东省阳江市新洲镇表竹村12号');
INSERT INTO `user` VALUES ('23', '杨蓓', '567', '杨蓓', 'male', '3046686935@qq.com', '1995-03-28', '13763052654', '广东省阳江市新洲镇表竹村13号');
INSERT INTO `user` VALUES ('24', '吕淑芬', '568', '吕淑芬', 'male', '3046686936@qq.com', '1995-03-29', '13763052655', '广东省阳江市新洲镇表竹村14号');
INSERT INTO `user` VALUES ('25', '沈琼', '569', '沈琼', 'female', '3046686937@qq.com', '1995-03-30', '13763052656', '广东省阳江市新洲镇表竹村15号');
INSERT INTO `user` VALUES ('26', '韩玉凤', '570', '韩玉凤', 'female', '3046686938@qq.com', '1995-03-31', '13763052657', '广东省阳江市新洲镇表竹村16号');
INSERT INTO `user` VALUES ('27', '沈胜珍', '571', '沈胜珍', 'female', '3046686939@qq.com', '1995-04-01', '13763052658', '广东省阳江市新洲镇表竹村17号');
INSERT INTO `user` VALUES ('28', '魏萍', '572', '魏萍', 'female', '3046686940@qq.com', '1995-04-02', '13763052659', '广东省阳江市新洲镇表竹村18号');
INSERT INTO `user` VALUES ('29', '秦小蝶', '573', '秦小蝶', 'female', '3046686941@qq.com', '1995-04-03', '13763052660', '广东省阳江市新洲镇表竹村19号');
INSERT INTO `user` VALUES ('30', '尤福兰', '574', '尤福兰', 'male', '3046686942@qq.com', '1995-04-04', '13763052661', '广东省阳江市新洲镇表竹村20号');
INSERT INTO `user` VALUES ('31', '郑静欣', '575', '郑静欣', 'male', '3046686943@qq.com', '1995-04-05', '13763052662', '广东省阳江市新洲镇表竹村21号');
INSERT INTO `user` VALUES ('32', '尤向萍', '576', '尤向萍', 'male', '3046686944@qq.com', '1995-04-06', '13763052663', '广东省阳江市新洲镇表竹村22号');
INSERT INTO `user` VALUES ('33', '蒋青槐', '577', '蒋青槐', 'male', '3046686945@qq.com', '1995-04-07', '13763052664', '广东省阳江市新洲镇表竹村23号');
INSERT INTO `user` VALUES ('34', '陶舒', '578', '陶舒', 'male', '3046686946@qq.com', '1995-04-08', '13763052665', '广东省阳江市新洲镇表竹村24号');
INSERT INTO `user` VALUES ('35', '周庆黎', '579', '周庆黎', 'male', '3046686947@qq.com', '1995-04-09', '13763052666', '广东省阳江市新洲镇表竹村25号');
INSERT INTO `user` VALUES ('36', '孙冰彤', '580', '孙冰彤', 'female', '3046686948@qq.com', '1995-04-10', '13763052667', '广东省阳江市新洲镇表竹村26号');
INSERT INTO `user` VALUES ('37', '年小珍', '581', '年小珍', 'female', '3046686949@qq.com', '1995-04-11', '13763052668', '广东省阳江市新洲镇表竹村27号');
INSERT INTO `user` VALUES ('38', '施冷安', '582', '施冷安', 'female', '3046686950@qq.com', '1995-04-12', '13763052669', '广东省阳江市新洲镇表竹村28号');
INSERT INTO `user` VALUES ('39', '姜红', '583', '姜红', 'female', '3046686951@qq.com', '1995-04-13', '13763052670', '广东省阳江市新洲镇表竹村29号');
INSERT INTO `user` VALUES ('40', '褚小珍', '584', '褚小珍', 'female', '3046686952@qq.com', '1995-04-14', '13763052671', '广东省阳江市新洲镇表竹村30号');
INSERT INTO `user` VALUES ('41', '华元珊', '585', '华元珊', 'male', '3046686953@qq.com', '1995-04-15', '13763052672', '广东省阳江市新洲镇表竹村31号');
INSERT INTO `user` VALUES ('42', '施亚萍', '586', '施亚萍', 'male', '3046686954@qq.com', '1995-04-16', '13763052673', '广东省阳江市新洲镇表竹村32号');
INSERT INTO `user` VALUES ('43', '周乐萱', '587', '周乐萱', 'male', '3046686955@qq.com', '1995-04-17', '13763052674', '广东省阳江市新洲镇表竹村33号');
INSERT INTO `user` VALUES ('44', '岑缘双', '588', '岑缘双', 'male', '3046686956@qq.com', '1995-04-18', '13763052675', '广东省阳江市新洲镇表竹村34号');
INSERT INTO `user` VALUES ('45', '何宁', '589', '何宁', 'female', '3046686957@qq.com', '1995-04-19', '13763052676', '广东省阳江市新洲镇表竹村35号');
INSERT INTO `user` VALUES ('46', '蒋宗莉', '590', '蒋宗莉', 'female', '3046686958@qq.com', '1995-04-20', '13763052677', '广东省阳江市新洲镇表竹村36号');
INSERT INTO `user` VALUES ('47', '施翠花', '591', '施翠花', 'female', '3046686959@qq.com', '1995-04-21', '13763052678', '广东省阳江市新洲镇表竹村37号');
INSERT INTO `user` VALUES ('48', '华桂兰', '592', '华桂兰', 'female', '3046686960@qq.com', '1995-04-22', '13763052679', '广东省阳江市新洲镇表竹村38号');
INSERT INTO `user` VALUES ('49', '李发弟', '593', '李发弟', 'female', '3046686961@qq.com', '1995-04-23', '13763052680', '广东省阳江市新洲镇表竹村39号');
INSERT INTO `user` VALUES ('50', '沈欣阳', '594', '沈欣阳', 'female', '3046686962@qq.com', '1995-04-24', '13763052681', '广东省阳江市新洲镇表竹村40号');
INSERT INTO `user` VALUES ('51', '许程悦', '595', '许程悦', 'male', '3046686963@qq.com', '1995-04-25', '13763052682', '广东省阳江市新洲镇表竹村41号');
INSERT INTO `user` VALUES ('52', '张朱娇', '596', '张朱娇', 'male', '3046686964@qq.com', '1995-04-26', '13763052683', '广东省阳江市新洲镇表竹村42号');
INSERT INTO `user` VALUES ('53', '严秀芳', '597', '严秀芳', 'male', '3046686965@qq.com', '1995-04-27', '13763052684', '广东省阳江市新洲镇表竹村43号');
INSERT INTO `user` VALUES ('54', '韩之柔', '598', '韩之柔', 'male', '3046686966@qq.com', '1995-04-28', '13763052685', '广东省阳江市新洲镇表竹村44号');
INSERT INTO `user` VALUES ('55', '赵涛', '599', '赵涛', 'male', '3046686967@qq.com', '1995-04-29', '13763052686', '广东省阳江市新洲镇表竹村45号');
INSERT INTO `user` VALUES ('56', '赵怜云', '600', '赵怜云', 'female', '3046686968@qq.com', '1995-04-30', '13763052687', '广东省阳江市新洲镇表竹村46号');
INSERT INTO `user` VALUES ('57', '施慧娟', '601', '施慧娟', 'female', '3046686969@qq.com', '1995-05-01', '13763052688', '广东省阳江市新洲镇表竹村47号');
INSERT INTO `user` VALUES ('58', '曹成倩', '602', '曹成倩', 'female', '3046686970@qq.com', '1995-05-02', '13763052689', '广东省阳江市新洲镇表竹村48号');
INSERT INTO `user` VALUES ('59', '柴心菲', '603', '柴心菲', 'female', '3046686971@qq.com', '1995-05-03', '13763052690', '广东省阳江市新洲镇表竹村49号');
INSERT INTO `user` VALUES ('60', '冯寒雁', '604', '冯寒雁', 'female', '3046686972@qq.com', '1995-05-04', '13763052691', '广东省阳江市新洲镇表竹村50号');
INSERT INTO `user` VALUES ('61', '蒋秀丽', '605', '蒋秀丽', 'male', '3046686973@qq.com', '1995-05-05', '13763052692', '广东省阳江市新洲镇表竹村51号');
INSERT INTO `user` VALUES ('62', '李聪', '606', '李聪', 'male', '3046686974@qq.com', '1995-05-06', '13763052693', '广东省阳江市新洲镇表竹村52号');
INSERT INTO `user` VALUES ('63', '魏傲文', '607', '魏傲文', 'male', '3046686975@qq.com', '1995-05-07', '13763052694', '广东省阳江市新洲镇表竹村53号');
INSERT INTO `user` VALUES ('64', '何秋', '608', '何秋', 'male', '3046686976@qq.com', '1995-05-08', '13763052695', '广东省阳江市新洲镇表竹村54号');
INSERT INTO `user` VALUES ('65', '姜菁', '609', '姜菁', 'male', '3046686977@qq.com', '1995-05-09', '13763052696', '广东省阳江市新洲镇表竹村55号');
INSERT INTO `user` VALUES ('66', '蒋礼杰', '610', '蒋礼杰', 'female', '3046686978@qq.com', '1995-05-10', '13763052697', '广东省阳江市新洲镇表竹村56号');
INSERT INTO `user` VALUES ('67', '姜妹妹', '611', '姜妹妹', 'female', '3046686979@qq.com', '1995-05-11', '13763052698', '广东省阳江市新洲镇表竹村57号');
INSERT INTO `user` VALUES ('68', '华虹霖', '612', '华虹霖', 'female', '3046686980@qq.com', '1995-05-12', '13763052699', '广东省阳江市新洲镇表竹村58号');
INSERT INTO `user` VALUES ('69', '孙纨', '613', '孙纨', 'female', '3046686981@qq.com', '1995-05-13', '13763052700', '广东省阳江市新洲镇表竹村59号');
INSERT INTO `user` VALUES ('70', '孔半雪', '614', '孔半雪', 'female', '3046686982@qq.com', '1995-05-14', '13763052701', '广东省阳江市新洲镇表竹村60号');
INSERT INTO `user` VALUES ('71', '陈慧娟', '615', '陈慧娟', 'female', '3046686983@qq.com', '1995-05-15', '13763052702', '广东省阳江市新洲镇表竹村61号');
INSERT INTO `user` VALUES ('72', '周问筠', '616', '周问筠', 'male', '3046686984@qq.com', '1995-05-16', '13763052703', '广东省阳江市新洲镇表竹村62号');
INSERT INTO `user` VALUES ('73', '杨洪丽', '617', '杨洪丽', 'male', '3046686985@qq.com', '1995-05-17', '13763052704', '广东省阳江市新洲镇表竹村63号');
INSERT INTO `user` VALUES ('74', '曹瑗', '618', '曹瑗', 'male', '3046686986@qq.com', '1995-05-18', '13763052705', '广东省阳江市新洲镇表竹村64号');
INSERT INTO `user` VALUES ('75', '陈莉莎', '619', '陈莉莎', 'male', '3046686987@qq.com', '1995-05-19', '13763052706', '广东省阳江市新洲镇表竹村65号');
INSERT INTO `user` VALUES ('76', '施纨', '620', '施纨', 'male', '3046686988@qq.com', '1995-05-20', '13763052707', '广东省阳江市新洲镇表竹村66号');
INSERT INTO `user` VALUES ('77', '何问筠', '621', '何问筠', 'male', '3046686989@qq.com', '1995-05-21', '13763052708', '广东省阳江市新洲镇表竹村67号');
INSERT INTO `user` VALUES ('78', '施兰燕', '622', '施兰燕', 'female', '3046686990@qq.com', '1995-05-22', '13763052709', '广东省阳江市新洲镇表竹村68号');
INSERT INTO `user` VALUES ('79', '魏孝洁', '623', '魏孝洁', 'female', '3046686991@qq.com', '1995-05-23', '13763052710', '广东省阳江市新洲镇表竹村69号');
INSERT INTO `user` VALUES ('80', '华乐之', '624', '华乐之', 'female', '3046686992@qq.com', '1995-05-24', '13763052711', '广东省阳江市新洲镇表竹村70号');
INSERT INTO `user` VALUES ('81', '赵亚梅', '625', '赵亚梅', 'female', '3046686993@qq.com', '1995-05-25', '13763052712', '广东省阳江市新洲镇表竹村71号');
INSERT INTO `user` VALUES ('82', '王露', '626', '王露', 'female', '3046686994@qq.com', '1995-05-26', '13763052713', '广东省阳江市新洲镇表竹村72号');
INSERT INTO `user` VALUES ('83', '韩亚萍', '627', '韩亚萍', 'male', '3046686995@qq.com', '1995-05-27', '13763052714', '广东省阳江市新洲镇表竹村73号');
INSERT INTO `user` VALUES ('84', '和夏梅', '628', '和夏梅', 'male', '3046686996@qq.com', '1995-05-28', '13763052715', '广东省阳江市新洲镇表竹村74号');
INSERT INTO `user` VALUES ('85', '周美娟', '629', '周美娟', 'male', '3046686997@qq.com', '1995-05-29', '13763052716', '广东省阳江市新洲镇表竹村75号');
INSERT INTO `user` VALUES ('86', '周丽', '630', '周丽', 'male', '3046686998@qq.com', '1995-05-30', '13763052717', '广东省阳江市新洲镇表竹村76号');
INSERT INTO `user` VALUES ('87', '孔睿婕', '631', '孔睿婕', 'female', '3046686999@qq.com', '1995-05-31', '13763052718', '广东省阳江市新洲镇表竹村77号');
INSERT INTO `user` VALUES ('88', '吴英', '632', '吴英', 'female', '3046687000@qq.com', '1995-06-01', '13763052719', '广东省阳江市新洲镇表竹村78号');
INSERT INTO `user` VALUES ('89', '卫露', '633', '卫露', 'female', '3046687001@qq.com', '1995-06-02', '13763052720', '广东省阳江市新洲镇表竹村79号');
INSERT INTO `user` VALUES ('90', '秦成倩', '634', '秦成倩', 'female', '3046687002@qq.com', '1995-06-03', '13763052721', '广东省阳江市新洲镇表竹村80号');
INSERT INTO `user` VALUES ('91', '孙世兰', '635', '孙世兰', 'female', '3046687003@qq.com', '1995-06-04', '13763052722', '广东省阳江市新洲镇表竹村81号');
INSERT INTO `user` VALUES ('92', '孔玉婷', '636', '孔玉婷', 'male', '3046687004@qq.com', '1995-06-05', '13763052723', '广东省阳江市新洲镇表竹村82号');
INSERT INTO `user` VALUES ('93', '葛二丫', '637', '葛二丫', 'male', '3046687005@qq.com', '1995-06-06', '13763052724', '广东省阳江市新洲镇表竹村83号');
INSERT INTO `user` VALUES ('94', '孔娟', '638', '孔娟', 'male', '3046687006@qq.com', '1995-06-07', '13763052725', '广东省阳江市新洲镇表竹村84号');
INSERT INTO `user` VALUES ('95', '戚春喜', '639', '戚春喜', 'male', '3046687007@qq.com', '1995-06-08', '13763052726', '广东省阳江市新洲镇表竹村85号');
INSERT INTO `user` VALUES ('96', '尤帮菊', '640', '尤帮菊', 'male', '3046687008@qq.com', '1995-06-09', '13763052727', '广东省阳江市新洲镇表竹村86号');
INSERT INTO `user` VALUES ('97', '赵筠', '641', '赵筠', 'female', '3046687009@qq.com', '1995-06-10', '13763052728', '广东省阳江市新洲镇表竹村87号');
INSERT INTO `user` VALUES ('98', '严欢', '642', '严欢', 'female', '3046687010@qq.com', '1995-06-11', '13763052729', '广东省阳江市新洲镇表竹村88号');
INSERT INTO `user` VALUES ('99', '陈千萍', '643', '陈千萍', 'female', '3046687011@qq.com', '1995-06-12', '13763052730', '广东省阳江市新洲镇表竹村89号');
INSERT INTO `user` VALUES ('100', '杨谷山', '644', '杨谷山', 'female', '3046687012@qq.com', '1995-06-13', '13763052731', '广东省阳江市新洲镇表竹村90号');
INSERT INTO `user` VALUES ('101', '钱霄', '645', '钱霄', 'female', '3046687013@qq.com', '1995-06-14', '13763052732', '广东省阳江市新洲镇表竹村91号');
INSERT INTO `user` VALUES ('102', '陶竹', '646', '陶竹', 'male', '3046687014@qq.com', '1995-06-15', '13763052733', '广东省阳江市新洲镇表竹村92号');
INSERT INTO `user` VALUES ('103', '王小蝶', '647', '王小蝶', 'male', '3046687015@qq.com', '1995-06-16', '13763052734', '广东省阳江市新洲镇表竹村93号');
INSERT INTO `user` VALUES ('104', '华翠', '648', '华翠', 'male', '3046687016@qq.com', '1995-06-17', '13763052735', '广东省阳江市新洲镇表竹村94号');
INSERT INTO `user` VALUES ('105', '韩蕊', '649', '韩蕊', 'male', '3046687017@qq.com', '1995-06-18', '13763052736', '广东省阳江市新洲镇表竹村95号');
INSERT INTO `user` VALUES ('106', '金佳丽', '650', '金佳丽', 'female', '3046687018@qq.com', '1995-06-19', '13763052737', '广东省阳江市新洲镇表竹村96号');
INSERT INTO `user` VALUES ('107', '李灵竹', '651', '李灵竹', 'female', '3046687019@qq.com', '1995-06-20', '13763052738', '广东省阳江市新洲镇表竹村97号');
INSERT INTO `user` VALUES ('108', '魏娟', '652', '魏娟', 'female', '3046687020@qq.com', '1995-06-21', '13763052739', '广东省阳江市新洲镇表竹村98号');
INSERT INTO `user` VALUES ('109', '华敏', '653', '华敏', 'female', '3046687021@qq.com', '1995-06-22', '13763052740', '广东省阳江市新洲镇表竹村99号');
INSERT INTO `user` VALUES ('110', '李莎莎', '654', '李莎莎', 'female', '3046687022@qq.com', '1995-06-23', '13763052741', '广东省阳江市新洲镇表竹村100号');
INSERT INTO `user` VALUES ('112', 'wangxiaohui', '123456', '王晓辉', 'male', '263@qq.com', '2019-05-02', '15079254030', '江西省九江市');
