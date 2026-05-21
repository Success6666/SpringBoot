-- 创建用户表
CREATE TABLE IF NOT EXISTS tb_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 创建角色表
CREATE TABLE IF NOT EXISTS tb_role (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    authority VARCHAR(64) NOT NULL
);

-- 创建用户-角色关联表
CREATE TABLE IF NOT EXISTS tb_user_role (
    user_id INT,
    authority_id INT,
    PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES tb_user(id),
    FOREIGN KEY (authority_id) REFERENCES tb_role(role_id)
);

-- 插入默认角色
INSERT INTO tb_role (role_id, authority) VALUES (1, 'ROLE_admin') ON DUPLICATE KEY UPDATE authority = 'ROLE_admin';
INSERT INTO tb_role (role_id, authority) VALUES (2, 'ROLE_common') ON DUPLICATE KEY UPDATE authority = 'ROLE_common';

-- 插入测试用户 admin/123456
INSERT INTO tb_user (id, username, password) VALUES (1, 'admin', '$2b$10$YXT5HuNBmQK6qc/P0ljl2OngG75okh3YLibSl1Oh.8H.QCg4C5pcm') ON DUPLICATE KEY UPDATE password = '$2b$10$YXT5HuNBmQK6qc/P0ljl2OngG75okh3YLibSl1Oh.8H.QCg4C5pcm';

-- 分配admin角色
INSERT INTO tb_user_role (user_id, authority_id) VALUES (1, 1) ON DUPLICATE KEY UPDATE authority_id = 1;

-- 分类表
CREATE TABLE IF NOT EXISTS tb_category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(64) NOT NULL
);

-- 图书表
CREATE TABLE IF NOT EXISTS tb_book (
    bookId INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    author VARCHAR(128),
    price DOUBLE,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES tb_category(category_id)
);

-- 产品表
CREATE TABLE IF NOT EXISTS tb_product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DOUBLE,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES tb_category(category_id)
);

-- 插入分类数据
INSERT INTO tb_category (category_id, category_name) VALUES (1, '编程开发') ON DUPLICATE KEY UPDATE category_name = '编程开发';
INSERT INTO tb_category (category_id, category_name) VALUES (2, '文学小说') ON DUPLICATE KEY UPDATE category_name = '文学小说';
INSERT INTO tb_category (category_id, category_name) VALUES (3, '科学技术') ON DUPLICATE KEY UPDATE category_name = '科学技术';

-- 插入示例产品
INSERT INTO tb_product (id, name, price, category_id) VALUES (1, '笔记本电脑', 5999.00, 3) ON DUPLICATE KEY UPDATE name = '笔记本电脑';
INSERT INTO tb_product (id, name, price, category_id) VALUES (2, '机械键盘', 399.00, 3) ON DUPLICATE KEY UPDATE name = '机械键盘';
INSERT INTO tb_product (id, name, price, category_id) VALUES (3, 'Java核心技术', 128.00, 1) ON DUPLICATE KEY UPDATE name = 'Java核心技术';

-- 记住我持久化令牌表
CREATE TABLE IF NOT EXISTS persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);
