-- Table: public.game

-- DROP TABLE public.game;

CREATE TABLE public.game
(
    id integer NOT NULL,
    arrangement text COLLATE pg_catalog."default" NOT NULL,
    is_finished boolean NOT NULL,
    scale integer NOT NULL,
    player_name1 character varying(255) COLLATE pg_catalog."default",
    player_name2 character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT game_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.game
    OWNER to postgres;