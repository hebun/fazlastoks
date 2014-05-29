-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 22, 2014 at 06:51 PM
-- Server version: 5.5.37-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `fazlastoklar`
--
-- --------------------------------------------------------

--
-- Table structure for table `activation`
--

CREATE TABLE IF NOT EXISTS `activation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `tarih` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

--
-- Dumping data for table `activation`
--

INSERT INTO `activation` (`id`, `code`, `userid`, `tarih`) VALUES
(1, '4014c85f-bb3a-4cf2-a379-f716c6bf7437', 3, '2014-05-06 01:00:12'),
(3, '93d7bdb5-85fc-4a1b-aae2-2fc6ae7cdaec', 4, '2014-06-06 15:06:34'),
(4, '2d964f6c-fefb-48f5-ab1a-9a987b6dd4dc', 8, '2014-05-13 19:23:31'),
(5, 'ce359877-3748-487a-bcf2-31cf1b3c921e', 9, '2014-05-17 07:14:06');

-- --------------------------------------------------------

--
-- Stand-in structure for view `catcount`
--
CREATE TABLE IF NOT EXISTS `catcount` (
`id` int(11)
,`cname` varchar(255)
,`catCount` bigint(21)
);
-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=12 ;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `cname`) VALUES
(8, 'Test Categoryxx'),
(9, 'Test Category 1yy'),
(10, 'Test Category 2'),
(11, 'kategor-B');

-- --------------------------------------------------------

--
-- Table structure for table `firsatproduct`
--

CREATE TABLE IF NOT EXISTS `firsatproduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `productid_2` (`productid`),
  KEY `productid` (`productid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=24 ;

--
-- Dumping data for table `firsatproduct`
--

INSERT INTO `firsatproduct` (`id`, `productid`) VALUES
(22, 143),
(23, 144);

-- --------------------------------------------------------

--
-- Table structure for table `gridfield`
--

CREATE TABLE IF NOT EXISTS `gridfield` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tableName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `header` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=32 ;

--
-- Dumping data for table `gridfield`
--

INSERT INTO `gridfield` (`id`, `tableName`, `header`, `name`, `state`) VALUES
(1, 'talep', 'Alici adi', 'name', 0),
(2, 'talep', 'E-mail', 'email', 0),
(3, 'talep', 'GSM', 'gsm', 0),
(4, 'talep', 'Urun Adi', 'pname', 0),
(5, 'talep', 'Satici Adi', 'uname', 0),
(6, 'category', 'Kategori Ismi', 'cname', 0),
(7, 'state', 'Durum Ismi', 'sname', 0),
(8, 'talep', 'Notlar', 'notes', 0),
(9, 'user', 'E-mail', 'email', 0),
(10, 'user', 'Sifre', 'password', 0),
(11, 'user', 'Firma Kodu', 'firmaname', 0),
(12, 'user', 'isim', 'uname', 0),
(13, 'user', 'Urun Sayisi', 'productCount', 0),
(14, 'product', 'id', 'id', 1),
(15, 'product', 'userid', 'userid', 1),
(16, 'product', 'Urun Ismi', 'pname', 0),
(17, 'product', 'Detay', 'content', 0),
(18, 'product', 'pstate', 'pstate', 1),
(19, 'product', 'quantity', 'quantity', 0),
(20, 'product', 'Fiyat', 'price', 0),
(21, 'product', 'expiredate', 'expiredate', 0),
(22, 'product', 'keywords', 'keywords', 1),
(23, 'product', 'file', 'file', 1),
(24, 'product', 'adet', 'adet', 1),
(25, 'product', 'kg', 'kg', 1),
(26, 'product', 'm3', 'm3', 1),
(27, 'product', 'kalem', 'kalem', 1),
(28, 'product', 'Piyasa Fiyati', 'pprice', 0),
(29, 'product', 'Kullanici', 'uname', 0),
(30, 'photo', 'Resim', 'file', 0),
(31, 'keyword', 'Anahtar kelime', 'keyword', 0);

-- --------------------------------------------------------

--
-- Table structure for table `keyword`
--

CREATE TABLE IF NOT EXISTS `keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=11 ;

--
-- Dumping data for table `keyword`
--

INSERT INTO `keyword` (`id`, `keyword`, `productid`) VALUES
(5, 'xxxxx', NULL),
(6, 'xxxxx', NULL),
(7, 'fffffff', NULL),
(8, 'fffffff', NULL),
(9, 'fffffff', NULL),
(10, 'fffffff', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `mailcontent`
--

CREATE TABLE IF NOT EXISTS `mailcontent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `mailcontent`
--

INSERT INTO `mailcontent` (`id`, `name`, `content`) VALUES
(1, 'activation', '<style> #tblContainer td{ font-family: Verdana; font-size: 12px; color: #000000; </style> <table id=Table1 cellSpacing=0 cellPadding=0 width="100%" border=0> <tbody> <tr> <td style="BORDER-BOTTOM: #00477c 1px solid"><img src="http://www.fazlastolar.com/images/logo.gif"> </td></tr> <tr> <td><span style="FONT-SIZE: 26pt; COLOR: #336699; FONT-FAMILY: Times New Roman"> <p>FazlaStoklar.com</p></span></td></tr> <tr> <td>Sayın&nbsp;<strong>#fullname#</strong>,<br><br>Kaydınızı Tamamlayabilmek için Lütfen Bu linke Tıklayınız </td></tr> <tr> <td><span><br><br><a style="FONT-SIZE: 10pt; COLOR: #31639c; FONT-FAMILY: Verdana; TEXT-DECORATION: none" href="#link#">#link#</a></span><br>&nbsp;</td></tr> <tr> <td </td></tr></tbody></table>'),
(2, 'resetpassword', '<style> #tblContainer td{ font-family: Verdana; font-size: 12px; color: #000000; </style> <table id=Table1 cellSpacing=0 cellPadding=0 width="100%" border=0> <tbody> <tr> <td style="BORDER-BOTTOM: #00477c 1px solid"><img src="http://www.fazlastoklar.com/images/logo.gif"> </td></tr> <tr> <td><span style="FONT-SIZE: 26pt; COLOR: #336699; FONT-FAMILY: Times New Roman"> <p>FazlaStoklar.com</p></span></td></tr> <tr> <td><strong></strong><br><br>Şıfrenizi değiştirebilmek için Lütfen Bu linke Tıklayınız </td></tr> <tr> <td><span><br><br><a style="FONT-SIZE: 10pt; COLOR: #31639c; FONT-FAMILY: Verdana; TEXT-DECORATION: none" href="#link#">#link#</a></span><br>&nbsp;</td></tr> <tr> <td </td></tr></tbody></table>');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `pname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `pstate` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `quantity` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `expiredate` datetime DEFAULT NULL,
  `keywords` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `file` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `adet` int(11) DEFAULT NULL,
  `kg` int(11) DEFAULT NULL,
  `m3` int(11) DEFAULT NULL,
  `kalem` int(11) DEFAULT NULL,
  `pprice` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=145 ;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `userid`, `pname`, `content`, `pstate`, `quantity`, `price`, `expiredate`, `keywords`, `file`, `adet`, `kg`, `m3`, `kalem`, `pprice`) VALUES
(141, 3, 'f3rrfyyy', 'xxxxxxxxxyyy', NULL, 'xxx', 3333, '2014-05-24 03:00:00', 'bbb,bbb,xxxbbb,xxxbbb,', NULL, NULL, 30, 0, 34, 33),
(143, 8, 'xxxx', 'xxxx', NULL, NULL, 333333, '2014-05-31 00:00:00', 'dfgdfgdfdfgdf,dfgdfgdfdfgdfdfg,', NULL, NULL, 0, 0, 0, 33333),
(144, 10, 'bilgisayar', 'bir laptop bir masaustu bir tablet vs', NULL, NULL, 8000, '2014-05-31 00:00:00', 'laptop,bilgisayar,tablet,', NULL, NULL, 3, 0, 3, 10000);

-- --------------------------------------------------------

--
-- Table structure for table `productcategory`
--

CREATE TABLE IF NOT EXISTS `productcategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(11) NOT NULL,
  `categoryId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ps` (`productId`,`categoryId`),
  KEY `categoryId` (`categoryId`),
  KEY `productId` (`productId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=101 ;

--
-- Dumping data for table `productcategory`
--

INSERT INTO `productcategory` (`id`, `productId`, `categoryId`) VALUES
(92, 141, 8),
(98, 141, 9),
(90, 141, 10),
(78, 141, 11),
(66, 143, 10),
(67, 143, 11),
(100, 144, 11);

-- --------------------------------------------------------

--
-- Table structure for table `productphoto`
--

CREATE TABLE IF NOT EXISTS `productphoto` (
  `productId` int(11) DEFAULT NULL,
  `file` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `file` (`file`),
  KEY `pid` (`productId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=40 ;

--
-- Dumping data for table `productphoto`
--

INSERT INTO `productphoto` (`productId`, `file`, `id`) VALUES
(141, 'pre829874609616892836icn_add_user.png', 24),
(143, 'pre5107387867370364108icn_jump_back.png', 25),
(143, 'pre8290862205006971486icn_security.png', 26),
(143, 'pre1755973043703422411icn_security.png', 27),
(143, 'pre4606244931219986337icn_security.png', 28),
(143, 'pre903882729486726634icn_security.png', 29),
(141, 'pre7398310306940056335icn_jump_back.png', 30),
(141, 'pre7777824152407092961icn_edit_article.png', 32),
(141, 'pre7300325629407409246icn_edit_article.png', 33),
(141, 'pre5866786737758375314secondary_bar_shadow.png', 34),
(141, 'pre543011243703693565secondary_bar_shadow.png', 35),
(141, 'pre6464488551667431090secondary_bar_shadow.png', 36),
(141, 'pre8010366229237608976secondary_bar_shadow.png', 37),
(141, 'pre4044159747796122546Screenshot from 2014-04-14 22:46:41.png', 38),
(144, 'pre8462393101936103113Screenshot from 2014-04-14 22:46:41.png', 39);

-- --------------------------------------------------------

--
-- Table structure for table `prostate`
--

CREATE TABLE IF NOT EXISTS `prostate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `stateid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pc` (`productid`,`stateid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=38 ;

--
-- Dumping data for table `prostate`
--

INSERT INTO `prostate` (`id`, `productid`, `stateid`) VALUES
(2, 4, 2),
(1, 5, 5),
(21, 137, 6),
(20, 140, 5),
(33, 141, 3),
(35, 141, 4),
(22, 143, 3),
(25, 143, 4),
(26, 143, 5),
(37, 144, 3);

-- --------------------------------------------------------

--
-- Table structure for table `resetpassword`
--

CREATE TABLE IF NOT EXISTS `resetpassword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `tarih` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `resetpassword`
--

INSERT INTO `resetpassword` (`id`, `code`, `userid`, `tarih`) VALUES
(1, '8a27a7d7-106d-4a0f-baf3-baf2541284c2', 8, '2014-05-18 10:11:06'),
(2, 'b5bbc447-fe6a-4fc1-b459-e0c5de6a4b82', 8, '2014-05-18 10:34:41');

-- --------------------------------------------------------

--
-- Table structure for table `state`
--

CREATE TABLE IF NOT EXISTS `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sname` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

--
-- Dumping data for table `state`
--

INSERT INTO `state` (`id`, `sname`) VALUES
(3, '2. el kullanılmış iyi durumda'),
(4, '2. el eski malzeme'),
(5, 'Hurda Malzeme');

-- --------------------------------------------------------

--
-- Table structure for table `talep`
--

CREATE TABLE IF NOT EXISTS `talep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gsm` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `notes` varchar(900) COLLATE utf8_bin DEFAULT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  `tarih` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=16 ;

--
-- Dumping data for table `talep`
--

INSERT INTO `talep` (`id`, `productid`, `userid`, `name`, `email`, `gsm`, `notes`, `state`, `tarih`) VALUES
(1, 5, 1, 'alici adi', 'blab@blba.bla', '233455455', 'bu stogu en son kaca birakirsin', 1, NULL),
(3, 32, 1, 'alici adi', 'blab@blba.bla', '233455455', 'bu stogu en son kaca birakirsin', 0, NULL),
(4, 37, 1, 'alici adi', 'blab@blba.bla', '233455455', 'bu stogu en son kaca birakirsin', 1, NULL),
(5, 1, 1, 'alici adi', 'blab@blba.bla', '233455455', 'bu stogu en son kaca birakirsin', 1, NULL),
(6, 5, 2, 'testname', 'email', 'gsm', 'blablab', 0, NULL),
(7, 5, 2, 'testname', 'email', 'gsm', 'blablab', 0, NULL),
(8, 5, 2, 'isim test', 'ffffl@cccc.om', 'ffff', 'ff', 1, NULL),
(10, 137, 10, 'fffffff', 'ffffff@ffff.com', 'ddddddd', '', 0, NULL),
(11, 137, 10, 'ffeee', 'ffeeeee@dd.22', '(0222) 222 22 22', '', 0, NULL),
(12, 137, 10, 'ffeee', 'ffeeeee@dd.22', '(0444) 444 44 44', '', 0, NULL),
(13, 137, 10, 'ffeee', 'ffeeeee@dd.22', '(0333) 333 33 33', '', 0, NULL),
(15, 141, 3, 'xxx', 'erere@gfdf.dfdf', '(0333) 333 33 33', 'dfdf', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `test` varchar(3545) COLLATE utf8_bin NOT NULL,
  `sss` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=20 ;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`id`, `test`, `sss`) VALUES
(2, 'blblba', NULL),
(3, 'updatev', NULL),
(4, 'update', NULL),
(5, 'update', NULL),
(8, 'pppp', NULL),
(9, 'blblba', NULL),
(10, 'pppp', NULL),
(11, 'blblba', NULL),
(12, 'update', NULL),
(13, 'blblba', NULL),
(14, 'update', NULL),
(15, 'blblba', NULL),
(16, 'update', NULL),
(17, 'blblba', NULL),
(18, 'update', NULL),
(19, 'blblba', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `firmaname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `uname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `cepno` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `sabitno` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `website` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `vergidaire` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `vergino` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `state` varchar(50) COLLATE utf8_bin DEFAULT 'PENDING',
  `uuid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=11 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `firmaname`, `uname`, `cepno`, `sabitno`, `address`, `city`, `website`, `vergidaire`, `vergino`, `state`, `uuid`) VALUES
(1, 'bla@ma.com', 'asdf', 'sd', 'asdf', 'asdf', 'asdf', 'asdfxxxx', 'asdf', 'sdf', 'asd', 'asdf', 'PENDING', NULL),
(2, 'bla@ma.com', 'asdf', 'sd', 'asdf', 'asdf', 'asdf', 'asdf', 'asdf', 'sdf', 'asd', 'asdf', 'PENDING', NULL),
(3, 'fff@fff.dff', 'qwer', 'cc', 'ccxy', 'c', 'cc', 'jhlkhlkjh kjlkj', '', '', '', '', 'ACTIVE', '4014c85f-bb3a-4cf2-a379-f716c6bf7437'),
(4, 'dfff@ggg.ddd', '1234', 'fdfdf', 'asdf ', 'sdf', '', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', '93d7bdb5-85fc-4a1b-aae2-2fc6ae7cdaec'),
(8, 'ismettung@gmail.com', '1234', '', '34  2234', '2134234', '', NULL, NULL, NULL, NULL, NULL, 'ADMIN', '2d964f6c-fefb-48f5-ab1a-9a987b6dd4dc'),
(10, 'altug@yildiztekin.com', '6n7924', '', 'Altuğ Yıldıztekin', '05326860240', '02164696680', NULL, NULL, NULL, NULL, NULL, 'ACTIVE', NULL);

-- --------------------------------------------------------

--
-- Structure for view `catcount`
--
DROP TABLE IF EXISTS `catcount`;

CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER VIEW `catcount` AS select `c`.`id` AS `id`,`c`.`cname` AS `cname`,count(`c`.`id`) AS `catCount` from (`category` `c` left join `productcategory` `pc` on((`pc`.`categoryId` = `c`.`id`))) group by `c`.`id` order by count(`c`.`id`) desc limit 0,30;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `firsatproduct`
--
ALTER TABLE `firsatproduct`
  ADD CONSTRAINT `firsatproduct_ibfk_1` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `productcategory`
--
ALTER TABLE `productcategory`
  ADD CONSTRAINT `cid` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `proid` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `productphoto`
--
ALTER TABLE `productphoto`
  ADD CONSTRAINT `pid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
