create table caracteristicas
(
    id       int auto_increment
        primary key,
    nombre   varchar(255) null,
    padre_id int          null,
    constraint FKc2rqewsxhiyxeri3s2ovbhsmm
        foreign key (padre_id) references caracteristicas (id)
);

create table usuario
(
    id       int auto_increment
        primary key,
    clave    varchar(255) null,
    tipo     varchar(255) null,
    username varchar(255) null,
    constraint UK863n1y3x0jalatoir4325ehal
        unique (username)
);

create table administrador
(
    usuario_id     int          not null
        primary key,
    correo         varchar(255) null,
    identificacion varchar(255) null,
    nombre         varchar(255) null,
    constraint FK6wreymkne84tlme1l8mf6smi5
        foreign key (usuario_id) references usuario (id)
);

create table empresa
(
    usuario_id   int          not null
        primary key,
    aprobada     bit          not null,
    correo       varchar(255) null,
    descripcion  varchar(255) null,
    localizacion varchar(255) null,
    nombre       varchar(255) null,
    telefono     varchar(255) null,
    constraint FKs12udhh8f7taklesp1phv0ikg
        foreign key (usuario_id) references usuario (id)
);

create table oferente
(
    usuario_id   int          not null
        primary key,
    apellido     varchar(255) null,
    aprobado     bit          not null,
    correo       varchar(255) null,
    nacionalidad varchar(255) null,
    nombre       varchar(255) null,
    residencia   varchar(255) null,
    telefono     varchar(255) null,
    constraint FKriyb02ek8v1plcwft3vdmpu37
        foreign key (usuario_id) references usuario (id)
);

create table oferente_habilidades
(
    id                 int auto_increment
        primary key,
    nivel              int null,
    caracteristicas_id int null,
    oferente_id        int null,
    constraint FKctbxh4bbxvq0qtgubuvbalxkc
        foreign key (caracteristicas_id) references caracteristicas (id),
    constraint FKodxmdfsdmixte2vmxj1ipfu9r
        foreign key (oferente_id) references oferente (usuario_id)
);

create table puesto
(
    id          int auto_increment
        primary key,
    descripcion varchar(255) null,
    estado      varchar(255) null,
    fecha       varchar(255) null,
    salario     int          null,
    tipo        varchar(255) null,
    empresa_id  int          null,
    constraint FKn5jgwg4anw6yj1lrv2ii7es0a
        foreign key (empresa_id) references empresa (usuario_id)
);

create table puesto_habilidades
(
    id                int auto_increment
        primary key,
    nivel             int null,
    caracteristica_id int null,
    puesto_id         int null,
    constraint FK8r1liikko14e6qrv8hdnxi5cx
        foreign key (caracteristica_id) references caracteristicas (id),
    constraint FKq0sbu9ksuwe8xvu8jckwyn5bf
        foreign key (puesto_id) references puesto (id)
);




