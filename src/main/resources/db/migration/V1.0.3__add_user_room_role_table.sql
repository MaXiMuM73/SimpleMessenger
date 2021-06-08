CREATE TABLE IF NOT EXISTS public.users_room_roles
(
    id bigint NOT NULL DEFAULT nextval('hibernate_sequence'),
    room_role character varying(255) COLLATE pg_catalog."default",
    room_id bigint,
    user_id bigint,
    available_login_time timestamp without time zone,
    CONSTRAINT users_room_roles_pkey PRIMARY KEY (id),
    CONSTRAINT room_id FOREIGN KEY (room_id)
        REFERENCES public.rooms (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)