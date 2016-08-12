
DROP TABLE IF EXISTS "public"."ctrip_inn_record";
create SEQUENCE ctrip_inn_record_id_seq start 100
CREATE TABLE "public"."ctrip_inn_record" (
"ctrip_hotel_id" varchar COLLATE "default",
"ctrip_city" varchar COLLATE "default",
"ctrip_hotel_name" varchar COLLATE "default",
"prepay" varchar COLLATE "default" DEFAULT 0,
"nowpay" varchar COLLATE "default" DEFAULT 0,
"export_status" int4 DEFAULT 0,
"inn_id" int4,
"contact_name" varchar COLLATE "default",
"contact_tel" varchar COLLATE "default",
"create_at" timestamp(6) DEFAULT now(),
"deleted" int4 DEFAULT 0,
"update_at" timestamp(6),
"id" int8 DEFAULT nextval('ctrip_inn_record_id_seq'::regclass) NOT NULL,
"account_id" int4
)
WITH (OIDS=FALSE)

;
COMMENT ON TABLE "public"."ctrip_inn_record" IS '开通携程直连酒店记录表';
COMMENT ON COLUMN "public"."ctrip_inn_record"."prepay" IS '1是   0不是';
COMMENT ON COLUMN "public"."ctrip_inn_record"."nowpay" IS '1是  0不是';
COMMENT ON COLUMN "public"."ctrip_inn_record"."export_status" IS '1已导出   0未导出';
COMMENT ON COLUMN "public"."ctrip_inn_record"."deleted" IS '1删除   0未删除';

-- ----------------------------
-- Table structure for ctrip_roomtype_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."ctrip_roomtype_record";
create SEQUENCE ctrip_roomtype_record_id_seq start 100
CREATE TABLE "public"."ctrip_roomtype_record" (
"id" int4 DEFAULT nextval('ctrip_roomtype_record_id_seq'::regclass) NOT NULL,
"ctrip_city" varchar COLLATE "default",
"ctrip_hotel_name" varchar COLLATE "default",
"ctrip_hotel_id" varchar COLLATE "default",
"ctrip_roomtype_id" varchar COLLATE "default",
"ctrip_roomtype_name" varchar COLLATE "default",
"pay_type" varchar COLLATE "default",
"match_status" varchar COLLATE "default" DEFAULT 0,
"inn_id" int4,
"ota_roomtype_id" int4,
"ctrip_rate_plan_code" varchar COLLATE "default",
"export_status" int4 DEFAULT 0,
"direct_conn_status" int4 DEFAULT 0,
"create_at" timestamp(6) DEFAULT now(),
"deleted" int4 DEFAULT 0,
"update_at" timestamp(6),
"inn_name" varchar(20) COLLATE "default",
"room_type_name" varchar(30) COLLATE "default",
"account_id" int4,
"commission_percent" numeric(4,2)
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."pay_type" IS '1预付  2现付';
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."match_status" IS '1是  0否  匹配';
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."export_status" IS '1已导出  0未导出';
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."direct_conn_status" IS '1是  0否  开通直连';
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."deleted" IS '1删除  0未删除';
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."inn_name" IS 'oms客栈名称';
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."room_type_name" IS 'oms房型名称';
COMMENT ON COLUMN "public"."ctrip_roomtype_record"."commission_percent" IS '佣金比';


-- ----------------------------
CREATE UNIQUE INDEX "ctrip_inn_record_ctrip_hotel_id_inn_id_idx" ON "public"."ctrip_inn_record" USING btree (ctrip_hotel_id, inn_id);

-- ----------------------------
-- Primary Key structure for table ctrip_inn_record
-- ----------------------------
ALTER TABLE "public"."ctrip_inn_record" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ctrip_roomtype_record
-- ----------------------------
CREATE INDEX "ctrip_roomtype_record_ctrip_hotel_id_ctrip_roomtype_id_inn__idx" ON "public"."ctrip_roomtype_record" USING btree (ctrip_hotel_id, ctrip_roomtype_id, inn_id, account_id);

-- ----------------------------
-- Primary Key structure for table ctrip_roomtype_record
-- ----------------------------
ALTER TABLE "public"."ctrip_roomtype_record" ADD PRIMARY KEY ("id");

-- 初始化携程直连渠道
INSERT INTO "public"."tomato_oms_ota_info" ("id", "name", "user_code", "user_password", "authority", "api_type_id", "ota_id", "vendor_type", "logo_path", "pid", "ota_group", "ota_http_url", "ota_type", "push_addr", "push_config", "manager", "proxy_type_config", "common_proxy_strategy") VALUES ('10001', '携程直连', 'CTRIP_CONN', 'ctrip108', '', '1', '108', '1', '/oms/dx.png', '108', '3', '', '', 'http://124.127.242.67/PMSGateway/PMSGatewayService.asmx?wsdl', '', 'doms', '{"DI":0,"MAI2DI":0}', 'MAI');

