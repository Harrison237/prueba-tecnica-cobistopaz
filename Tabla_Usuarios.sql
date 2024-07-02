create database cobisTopazPrueba;

use cobisTopazPrueba;

create table user_tabla (
    id varchar(255) not null primary key unique,
    nombre_usuario varchar(255) not null,
    contrasena varchar(40) not null,
    roles text not null
)