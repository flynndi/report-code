CREATE TABLE "public"."book" (
                                 "id" text COLLATE "pg_catalog"."default" NOT NULL,
                                 "name" text COLLATE "pg_catalog"."default",
                                 "content" text COLLATE "pg_catalog"."default",
                                 CONSTRAINT "book_pkey" PRIMARY KEY ("id")
)
;

ALTER TABLE "public"."book"
    OWNER TO "postgres";



INSERT INTO "public"."book"("id", "name", "content") VALUES ('1', '1', '1');
INSERT INTO "public"."book"("id", "name", "content") VALUES ('2', '2', '2');
