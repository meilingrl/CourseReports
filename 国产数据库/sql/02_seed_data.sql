INSERT INTO kb_department (name, manager, phone)
SELECT '信息中心', '宋老师', '010-10000001'
WHERE NOT EXISTS (SELECT 1 FROM kb_department WHERE name = '信息中心');

INSERT INTO kb_department (name, manager, phone)
SELECT '软件实训室', '李老师', '010-10000002'
WHERE NOT EXISTS (SELECT 1 FROM kb_department WHERE name = '软件实训室');

INSERT INTO kb_department (name, manager, phone)
SELECT '网络实训室', '王老师', '010-10000003'
WHERE NOT EXISTS (SELECT 1 FROM kb_department WHERE name = '网络实训室');

INSERT INTO kb_asset_category (name, description)
SELECT '计算机设备', '台式机、笔记本、工作站等教学终端'
WHERE NOT EXISTS (SELECT 1 FROM kb_asset_category WHERE name = '计算机设备');

INSERT INTO kb_asset_category (name, description)
SELECT '网络设备', '交换机、路由器、无线 AP 等网络设备'
WHERE NOT EXISTS (SELECT 1 FROM kb_asset_category WHERE name = '网络设备');

INSERT INTO kb_asset_category (name, description)
SELECT '实验设备', '实训课程使用的实验箱和专用设备'
WHERE NOT EXISTS (SELECT 1 FROM kb_asset_category WHERE name = '实验设备');

INSERT INTO kb_supplier (name, contact_person, phone, address)
SELECT '北京教学设备有限公司', '陈经理', '13800000001', '北京市海淀区'
WHERE NOT EXISTS (SELECT 1 FROM kb_supplier WHERE name = '北京教学设备有限公司');

INSERT INTO kb_supplier (name, contact_person, phone, address)
SELECT '华北信息技术服务有限公司', '赵经理', '13800000002', '北京市朝阳区'
WHERE NOT EXISTS (SELECT 1 FROM kb_supplier WHERE name = '华北信息技术服务有限公司');

INSERT INTO kb_supplier (name, contact_person, phone, address)
SELECT '国产软硬件集成有限公司', '刘经理', '13800000003', '北京市昌平区'
WHERE NOT EXISTS (SELECT 1 FROM kb_supplier WHERE name = '国产软硬件集成有限公司');
