create table hns_arenas
(
    id          int,
    name        varchar(255)           not null,
    world       varchar(255)           not null,
    max_seekers int                    not null,
    max_hiders  int                    not null,
    status      int                    not null comment '// 0 - editing, 1 - available, 2 - waiting for players, 3 - playing, 4 - cleaning everything up',
    created_at  datetime default NOW() null
);
create unique index hns_arenas_primary_index on hns_arenas (id);
create unique index hns_arenas_name_unique_index on hns_arenas (name);
alter table hns_arenas
    modify id int auto_increment;