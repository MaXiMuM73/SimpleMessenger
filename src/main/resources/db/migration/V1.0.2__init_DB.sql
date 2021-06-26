CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL DEFAULT nextval('hibernate_sequence'),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default",
    system_role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.rooms
(
    id bigint NOT NULL DEFAULT nextval('hibernate_sequence'),
    created_date timestamp without time zone NOT NULL,
    private boolean NOT NULL,
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT rooms_pkey PRIMARY KEY (id),
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT rooms_title UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS public.messages
(
    id bigint NOT NULL DEFAULT nextval('hibernate_sequence'),
    created_date timestamp without time zone NOT NULL,
    text character varying(255) COLLATE pg_catalog."default" NOT NULL,
    room_id bigint,
    user_id bigint,
    CONSTRAINT messages_pkey PRIMARY KEY (id),
    CONSTRAINT room_id FOREIGN KEY (room_id)
        REFERENCES public.rooms (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)