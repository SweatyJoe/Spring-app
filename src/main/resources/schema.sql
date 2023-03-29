CREATE TABLE IF NOT EXISTS public.imarket_parse_result
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    url character varying COLLATE pg_catalog."default" NOT NULL,
    cost numeric(6,2) NOT NULL DEFAULT '0.00',
    CONSTRAINT imarket_parse_result_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.imarket_parse_result
    OWNER to postgres;

