BEGIN;

SET client_encoding = 'LATIN1';

CREATE TABLE public.systemlogin
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username character varying(250) COLLATE pg_catalog."default" NOT NULL,
    password character varying(250) COLLATE pg_catalog."default" NOT NULL
);


CREATE TABLE public.subscription
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    subscriber_address character varying(250) COLLATE pg_catalog."default",
    sensor integer,
    sub_time date,
    report_interval bigint
);

COMMIT;

ANALYZE public.systemlogin;
ANALYZE public.subscription;
