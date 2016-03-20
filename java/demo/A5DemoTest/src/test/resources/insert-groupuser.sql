
SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `act_id_group` VALUES ('erp', '1', 'erp部门', 'assignment');
INSERT INTO `act_id_group` VALUES ('hr', '1', '人事', 'assignment');

INSERT INTO `act_id_membership` VALUES ('huyang', 'erp');
INSERT INTO `act_id_membership` VALUES ('junhui', 'erp');
INSERT INTO `act_id_membership` VALUES ('shaowei', 'erp');
INSERT INTO `act_id_membership` VALUES ('wangbin', 'erp');
INSERT INTO `act_id_membership` VALUES ('wangqin', 'erp');
INSERT INTO `act_id_membership` VALUES ('hruser', 'hr');
INSERT INTO `act_id_membership` VALUES ('hruser1', 'hr');

INSERT INTO `act_id_user` VALUES ('hruser', '1', 'Lili', 'Zhang', 'hr@gmail.com', '000000', '');
INSERT INTO `act_id_user` VALUES ('hruser1', '1', 'Hr', 'User1', 'huuser1@gmail.com', '000000', null);
INSERT INTO `act_id_user` VALUES ('huyang', '1', 'Yang', 'Hu', 'huyang@gmail.com', '000000', null);
INSERT INTO `act_id_user` VALUES ('junhui', '1', 'Hui', 'Jun', 'junhui@gmail.com', '000000', null);
INSERT INTO `act_id_user` VALUES ('shaowei', '1', 'Jin', 'ShaoWei', 'shaowei@gmail.com', '000000', null);
INSERT INTO `act_id_user` VALUES ('wangbin', '1', 'Bin', 'Wang', 'wangbin@gmail.com', '000000', null);
INSERT INTO `act_id_user` VALUES ('wangqin', '1', 'Qin', 'Wang', 'wangqin@gmail.com', '000000', null);
