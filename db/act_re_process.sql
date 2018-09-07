DROP TABLE IF EXISTS `act_re_process`;
CREATE TABLE `act_re_process` (
  `ID` varchar(64) NOT NULL COMMENT '流程ID',
  `MODEL_ID` varchar(64) NOT NULL COMMENT '模型ID',
  `NAME` varchar(255) DEFAULT NULL COMMENT '流程名称',
  `DESCRIPTION` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程描述信息',
  `CREATOR` varchar(50) DEFAULT NULL COMMENT '流程创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '流程创建时间',
  `VERSION` int(11) DEFAULT NULL COMMENT '流程版本',
  `STATUS` varchar(11) DEFAULT NULL COMMENT '流程状态',
  `CATEGORY` varchar(100) DEFAULT NULL COMMENT '流程分类',
  `CONTENT` longblob COMMENT '流程内容',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `act_re_node`;
CREATE TABLE `act_re_node` (
  `ID` varchar(64) NOT NULL COMMENT '节点ID',
  `PROCESS_ID` varchar(64) NOT NULL COMMENT '流程ID',
  `NAME` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `DESCRIPTION` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '节点描述信息',
  `STATUS` varchar(11) DEFAULT NULL COMMENT '节点状态',
  `NODE_TYPE` varchar(20) DEFAULT NULL COMMENT '节点类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `act_re_sequence`;
CREATE TABLE `act_re_sequence` (
  `ID` varchar(64) NOT NULL COMMENT '顺序流ID',
  `PROCESS_ID` varchar(64) NOT NULL COMMENT '流程ID',
  `NAME` varchar(255) DEFAULT NULL COMMENT '顺序流名称',
  `DESCRIPTION` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '顺序流描述信息',
  `SOURCE_REF` varchar(64) DEFAULT NULL COMMENT '源节点',
  `TARGET_REF` varchar(64) DEFAULT NULL COMMENT '目标节点',
  `CONDITION_EXPRESS` varchar(500) DEFAULT NULL COMMENT '流条件',
  `IS_DEFAULT` boolean COMMENT '是否默认流',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `act_re_node_properties`;
CREATE TABLE `act_re_node_properties` (
  `NODE_ID` varchar(64) NOT NULL COMMENT '节点ID',
  `PROCESS_ID` varchar(64) NOT NULL COMMENT '流程ID',
  `FORM_KEY` varchar(64) DEFAULT NULL COMMENT '表单编号',
  `INITIATOR` varchar(255) DEFAULT NULL COMMENT '启动器',
  `PRIORITY` varchar(10) DEFAULT NULL COMMENT '优先级',
  `IS_ASYNC` boolean COMMENT '是否异步',
  `IS_EXCLUSIVE` boolean COMMENT '是否互斥',
  `IS_FORCOMPENSATION` boolean COMMENT '是否补偿',
  `ASSIGNEE` varchar(64) DEFAULT NULL COMMENT '分配用户',
  `DUE_DATE` varchar(50) COMMENT '到期时间',
  `FLOW_ORDER` varchar(25) DEFAULT NULL COMMENT '流序',
  `ERROR_REF` varchar(25) DEFAULT NULL COMMENT '错误引用',
  PRIMARY KEY (`NODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `act_re_task_Multi`;
CREATE TABLE `act_re_task_Multi` (
  `ID` varchar(64) NOT NULL COMMENT '多实例ID',
  `TASK_NODE_ID` varchar(64) NOT NULL COMMENT '任务节点ID',
  `CARDINALITY` int(11) COMMENT '基数',
  `COMPLETION` varchar(255) DEFAULT NULL COMMENT '完成条件',
  `ELEMENT_VARIABLE` varchar(255) DEFAULT NULL COMMENT '元素变量',
  `type` varchar(20) DEFAULT NULL COMMENT '多实例类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `act_re_excute_listener`;
CREATE TABLE `act_re_excute_listener` (
  `ID` varchar(64) NOT NULL COMMENT '执行监听器ID',
  `TASK_NODE_ID` varchar(64) NOT NULL COMMENT '节点ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `act_re_task_listener`;
CREATE TABLE `act_re_task_listener` (
  `ID` varchar(64) NOT NULL COMMENT '任务监听器ID',
  `TASK_NODE_ID` varchar(64) NOT NULL COMMENT '节点ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `act_re_event_listener`;
CREATE TABLE `act_re_event_listener` (
  `ID` varchar(64) NOT NULL COMMENT '事件监听器ID',
  `TASK_NODE_ID` varchar(64) NOT NULL COMMENT '节点ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
