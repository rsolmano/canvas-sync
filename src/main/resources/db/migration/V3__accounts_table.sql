create or replace function public.trigger_set_synced_time()
    returns trigger as
$$
declare
    now_timestamp timestamptz := now();
begin
    new.synced_at = now_timestamp;
    return new;
end;
$$ language plpgsql;


create table canvas_accounts
(
    id                             integer primary key,
    name                           text                        not null,
    workflow_state                 text,
    parent_account_id              integer,
    root_account_id                integer,
    uuid                           text,
    default_storage_quota_mb       integer,
    default_user_storage_quota_mb  integer,
    default_group_storage_quota_mb integer,
    default_time_zone              text,
    sis_account_id                 text,
    integration_id                 text,
    sis_import_id                  integer,
    lti_guid                       text,

    synced_at                      timestamp without time zone not null
);

create index idx_canvas_accounts_parent_account_id on canvas_accounts (parent_account_id) where parent_account_id is not null;
create index idx_canvas_accounts_root_account_id on canvas_accounts (root_account_id) where root_account_id is not null;

create trigger set_timestamp_update
    before insert or update
    on canvas_accounts
    for each row
execute procedure trigger_set_synced_time();
