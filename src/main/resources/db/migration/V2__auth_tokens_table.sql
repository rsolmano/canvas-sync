create table oauth_tokens
(
    id            uuid default gen_random_uuid() primary key,
    canvas_host   text                        not null,
    access_token  text                        not null,
    refresh_token text                        not null,

    created_at    timestamp without time zone not null,
    updated_at    timestamp without time zone not null
);

create trigger set_timestamp_update
    before update
    on oauth_tokens
    for each row
execute procedure trigger_set_timestamps_for_updated_entry();

create trigger set_timestamp_insert
    before insert
    on oauth_tokens
    for each row
execute procedure trigger_set_timestamps_for_new_entry();