create schema papas;

CREATE TABLE papas.product_liked
(
    product_liked_id serial NOT NULL,
    user_id          serial NOT NULL,
    product_id       serial NOT NULL
);

CREATE TABLE papas.product_qna_mapp
(
    product_qna_mapp_key serial NOT NULL,
    product_id           serial NOT NULL,
    qna_id               serial NOT NULL
);

CREATE TABLE papas.user_social
(
    user_id         serial NOT NULL,
    id_social       boolean NULL,
    social_provider varchar(20) NULL,
    social_id       varchar(100) NULL
);

CREATE TABLE papas.qna
(
    qna_id      serial NOT NULL,
    qna_titla   varchar(200) NULL,
    qna_content varchar(500) NULL,
    is_answered boolean NULL,
    user_id     serial NOT NULL
);

CREATE TABLE papas.product_discount
(
    product_discount_id   serial NOT NULL,
    product_id            serial NOT NULL,
    isDiscount            boolean NULL,
    discount_percentage   double precision NULL,
    discount_price        int NULL,
    discount_period_start timestamp NULL,
    discount_period_end   timestamp NULL
);

CREATE TABLE papas.delivery
(
    delivery_id       serial NOT NULL,
    delivery_num      varchar(64) NULL,
    order_id          serial NOT NULL,
    delivery_status   varchar(10) NULL,
    delivery_begin    timestamp NULL,
    delivery_complete timestamp NULL
);

CREATE TABLE papas.product_tag
(
    hashtag_id serial NOT NULL,
    name     varchar(10) UNIQUE NOT NULL,
    created_at timestamp null
);

CREATE TABLE papas.product
(
    product_id serial      NOT NULL,
    name       varchar(100) NULL,
    contents   varchar(4000) NULL,
    brand      varchar(100) NULL,
    stock      bigint NULL,
    user_id    integer NOT NULL,
    created_at timestamp NULL,
    updated_at timestamp NULL,
    liked      bigint NULL,
    price      int NULL
);

CREATE TABLE papas.user_role
(
    user_id       serial NOT NULL,
    role          varchar(10) NULL,
    seller_rank   varchar(10) NULL,
    customer_rank varchar(10) NULL
);

CREATE TABLE papas.comments
(
    comment_id       serial NOT NULL,
    comment_contents varchar(1000) NULL,
    product_id       serial NOT NULL,
    created_at       timestamp NULL,
    user_id          serial NOT NULL,
    edited_at        timestamp NULL
);

CREATE TABLE papas.order
(
    order_id     serial NOT NULL,
    order_num    varchar(64) NULL,
    user_id      serial NOT NULL,
    order_status varchar(10) NULL,
    created_at   timestamp NULL,
    payment_id   serial NOT NULL,
    order_amount int NULL
);

CREATE TABLE papas.cart
(
    cart_id       serial NOT NULL,
    user_id       serial NOT NULL,
    product_id    serial NOT NULL,
    product_count int NULL
);

CREATE TABLE papas.product_image
(
    img_id     serial NOT NULL,
    origin_name varchar(100) NOT NULL,
    hash_name varchar(100) NOT NULL,
    file_path  varchar(200) NOT NULL,
    extension varchar(100) NOT NULL,
    is_thumbnail boolean NOT NULL,
    product_id serial NOT NULL
);

CREATE TABLE papas.product_order_mapp
(
    product_order_id serial NOT NULL,
    order_id         serial NOT NULL,
    product_id       serial NOT NULL,
    count            int NULL
);

CREATE TABLE papas.payment
(
    payment_id      serial NOT NULL,
    payment_num     varchar(64) NULL,
    payment_status  varchar(10) NULL,
    created_at      timestamp NULL,
    payment_company varchar(30) NULL,
    payment_amount  int NULL
);

CREATE TABLE papas.user_info
(
    user_id       serial       NOT NULL,
    login_id      varchar(50)  NOT NULL,
    password      varchar(100) NOT NULL,
    name          varchar(30)  NOT NULL,
    email         varchar(100) NOT NULL,
    phone_number  varchar(20)  NOT NULL,
    created_at    timestamp    NOT NULL,
    edited_at     timestamp,
    is_alive_user boolean      NOT NULL
);

CREATE TABLE papas.product_hashtag_mapp
(
    product_hashtag_id serial       NOT NULL,
    product_id         integer       NOT NULL,
    hashtag_id        integer NOT NULL
);

CREATE TABLE papas.enroll_seller_process
(
    user_id             serial      NOT NULL,
    bank_validation     boolean NULL,
    business_validation boolean NULL,
    final_validation    boolean NULL,
    request_date        timestamp            DEFAULT CURRENT_TIMESTAMP,
    reviewer_id         varchar(50) NULL,
    reviewed_date       timestamp null,
    status              varchar(20) NOT NULL DEFAULT 'PENDING',
    reject_reason       varchar(255) NULL
);

ALTER TABLE papas.product_liked
    ADD CONSTRAINT PK_PRODUCT_LIKED PRIMARY KEY (product_liked_id);

ALTER TABLE papas.product_qna_mapp
    ADD CONSTRAINT PK_PRODUCT_QNA_MAPP PRIMARY KEY (product_qna_mapp_key);

ALTER TABLE papas.user_social
    ADD CONSTRAINT PK_USER_SOCIAL PRIMARY KEY (user_id);

ALTER TABLE papas.qna
    ADD CONSTRAINT PK_QNA PRIMARY KEY (qna_id);

ALTER TABLE papas.product_discount
    ADD CONSTRAINT PK_PRODUCT_DISCOUNT PRIMARY KEY (product_discount_id);

ALTER TABLE papas.delivery
    ADD CONSTRAINT PK_DELIVERY PRIMARY KEY (delivery_id);

ALTER TABLE papas.product_tag
    ADD CONSTRAINT PK_PRODUCT_TAG PRIMARY KEY (hashtag_id);

ALTER TABLE papas.product
    ADD CONSTRAINT PK_PRODUCT PRIMARY KEY (product_id);

ALTER TABLE papas.user_role
    ADD CONSTRAINT PK_USER_ROLE PRIMARY KEY (user_id);

ALTER TABLE papas.comments
    ADD CONSTRAINT PK_COMMENTS PRIMARY KEY (comment_id);

ALTER TABLE papas.order
    ADD CONSTRAINT PK_ORDER PRIMARY KEY (order_id);

ALTER TABLE papas.cart
    ADD CONSTRAINT PK_CART PRIMARY KEY (cart_id);

ALTER TABLE papas.product_image
    ADD CONSTRAINT PK_PRODUCT_IMAGE PRIMARY KEY (img_id);

ALTER TABLE papas.product_order_mapp
    ADD CONSTRAINT PK_PRODUCT_ORDER_MAPP PRIMARY KEY (product_order_id);

ALTER TABLE papas.payment
    ADD CONSTRAINT PK_PAYMENT PRIMARY KEY (payment_id);

ALTER TABLE papas.user_info
    ADD CONSTRAINT PK_USER_INFO PRIMARY KEY (user_id);

ALTER TABLE papas.product_hashtag_mapp
    ADD CONSTRAINT PK_PRODUCT_HASHTAG_MAPP PRIMARY KEY (product_hashtag_id);

ALTER TABLE papas.user_social
    ADD CONSTRAINT FK_user_info_TO_user_social_1 FOREIGN KEY (user_id) REFERENCES papas.user_info (user_id);

ALTER TABLE papas.user_role
    ADD CONSTRAINT FK_user_info_TO_user_role_1 FOREIGN KEY (user_id) REFERENCES papas.user_info (user_id);

ALTER TABLE papas.enroll_seller_process
    ADD CONSTRAINT FK_user_info_TO_enroll_seller_process FOREIGN KEY (user_id) REFERENCES papas.user_info (user_id);
