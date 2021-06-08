-- INSERT TEST USER
INSERT INTO public.users (name, password, system_role)
VALUES ('test',
        '$2a$10$a9.lZuVu26XqQ/eLnADtTO2wfLCzduKQgYbXmDaBKKVrrteKXb7nu',
        'SYSTEM_USER');

-- INSERT TEST ADMIN
INSERT INTO public.users (name, password, system_role)
VALUES ('admin',
        '$2a$10$a9.lZuVu26XqQ/eLnADtTO2wfLCzduKQgYbXmDaBKKVrrteKXb7nu',
        'SYSTEM_ADMIN');

-- INSERT TEST ROOM (OWNER:test)
INSERT INTO public.rooms (created_date, private, title, user_id)
VALUES ('2021-06-08T20:44:56.745+00:00',
        'false',
        'test',
        '1');

-- INSERT ROOM OWNER
INSERT INTO public.users_room_roles (room_role, room_id, user_id, available_login_time)
VALUES ('ROOM_OWNER',
        '3',
        '1',
        '2021-06-08T20:44:56.745+00:00');