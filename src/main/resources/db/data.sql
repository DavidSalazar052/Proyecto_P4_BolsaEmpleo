create table caracteristicas
(
    id       varchar(255) not null
        primary key,
    nombre   varchar(255) null,
    padre_id varchar(255) null,
    constraint FKc2rqewsxhiyxeri3s2ovbhsmm
        foreign key (padre_id) references caracteristicas (id)
);

create table usuario
(
    id       varchar(255) not null
        primary key,
    clave    varchar(255) null,
    tipo     varchar(255) null,
    username varchar(255) null
);

create table administrador
(
    id             varchar(255) not null
        primary key,
    correo         varchar(255) null,
    identificacion varchar(255) null,
    nombre         varchar(255) null,
    usuario_id     varchar(255) null,
    constraint UK41f19w50uvnqar18j6gr9jepp
        unique (usuario_id),
    constraint FK6wreymkne84tlme1l8mf6smi5
        foreign key (usuario_id) references usuario (id)
);

create table empresa
(
    id           varchar(255) not null
        primary key,
    aprobada     bit          not null,
    correo       varchar(255) null,
    descripcion  varchar(255) null,
    localizacion varchar(255) null,
    nombre       varchar(255) null,
    telefono     varchar(255) null,
    usuario_id   varchar(255) null,
    constraint UKj3jswfc8tdw98h0i4emswuy9u
        unique (usuario_id),
    constraint FKs12udhh8f7taklesp1phv0ikg
        foreign key (usuario_id) references usuario (id)
);

create table oferente
(
    id           varchar(255) not null
        primary key,
    apellido     varchar(255) null,
    aprobado     bit          not null,
    correo       varchar(255) null,
    nacionalidad varchar(255) null,
    nombre       varchar(255) null,
    residencia   varchar(255) null,
    telefono     varchar(255) null,
    usuario_id   varchar(255) null,
    constraint UKnyv3rxwhqv3wjv7b84msnouax
        unique (usuario_id),
    constraint FKriyb02ek8v1plcwft3vdmpu37
        foreign key (usuario_id) references usuario (id)
);

create table oferente_habilidades
(
    id                 varchar(255) not null
        primary key,
    nivel              int          null,
    caracteristicas_id varchar(255) null,
    oferente_id        varchar(255) null,
    constraint UK5igbop8kn8eppxe6oges8rh54
        unique (oferente_id),
    constraint UK8njij82pqbo1xrjgk5b9k1ole
        unique (caracteristicas_id),
    constraint FKctbxh4bbxvq0qtgubuvbalxkc
        foreign key (caracteristicas_id) references caracteristicas (id),
    constraint FKodxmdfsdmixte2vmxj1ipfu9r
        foreign key (oferente_id) references oferente (id)
);

create table puesto
(
    id          varchar(255) not null
        primary key,
    descripcion varchar(255) null,
    estado      varchar(255) null,
    fecha       varchar(255) null,
    salario     int          null,
    tipo        varchar(255) null,
    empresa_id  varchar(255) null,
    constraint FKn5jgwg4anw6yj1lrv2ii7es0a
        foreign key (empresa_id) references empresa (id)
);

create table puesto_habilidades
(
    id                varchar(255) not null
        primary key,
    nivel             int          null,
    caracteristica_id varchar(255) null,
    puesto_id         varchar(255) null,
    constraint UKn74538b1477cfc1q8mr8suhxk
        unique (caracteristica_id),
    constraint FK8r1liikko14e6qrv8hdnxi5cx
        foreign key (caracteristica_id) references caracteristicas (id),
    constraint FKq0sbu9ksuwe8xvu8jckwyn5bf
        foreign key (puesto_id) references puesto (id)
);


