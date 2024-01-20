create table canvas_courses
(
    id                                   integer primary key,
    name                                 text                        not null,
    workflow_state                       text,
    account_id                           integer,
    root_account_id                      integer,
    uuid                                 text,
    course_code                          text,
    sis_course_id                        integer,
    integration_id                       integer,
    sis_import_id                        integer,
    default_view                         text,
    license                              text,
    start_at                             timestamp without time zone,
    end_at                               timestamp without time zone,
    public_syllabus                      boolean,
    public_syllabus_to_auth              boolean,
    storage_quota_mb                     integer,
    is_public                            boolean,
    is_public_to_auth_users              boolean,
    hide_final_grades                    boolean,
    apply_assignment_group_weights       boolean,
    calendar                             jsonb,
    time_zone                            text,
    blueprint                            boolean,
    enrollment_term_id                   integer,
    grading_standard_id                  integer,
    created_at                           timestamp without time zone not null,
    restrict_enrollments_to_course_dates boolean,
    overridden_course_visibility         text,

    synced_at                            timestamp without time zone not null
);

create index idx_canvas_courses_on_account_id on canvas_courses (account_id) where account_id is not null;
create index idx_canvas_courses_on_root_account_id on canvas_courses (root_account_id) where root_account_id is not null;

create trigger set_timestamp_update
    before insert or update
    on canvas_courses
    for each row
execute procedure trigger_set_synced_time();


create table canvas_enrollments
(
    course_id        integer                     not null references canvas_courses (id),
    user_id          integer                     not null,
    role             text,
    enrollment_state text,
    role_id          integer,
    type             text,

    synced_at        timestamp without time zone not null,

    primary key (course_id, user_id)
);

create trigger set_timestamp_update
    before insert or update
    on canvas_enrollments
    for each row
execute procedure trigger_set_synced_time();
